package pubsim;

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;

/**
 * Wraps the JTransforms FFT library
 *
 * @author Robby McKilliam
 */
public class FFT {

    ///Length of this FFT
    public final int N;
    private final DoubleFFT_1D jtFFT;
    private final double[] mem;

    public FFT(int N) {
        this.N = N;
        mem = new double[2 * N];
        jtFFT = new DoubleFFT_1D(N);
        edu.emory.mathcs.utils.ConcurrencyUtils.setNumberOfThreads(1); //just use one thread for FFTs
    }

    /**
     * Compute the discrete Fourier transform of x and place output into y. If
     * x.size is smaller than N then the zero padded Fourier transform is
     * computed.
     */
    public void forward(Complex[] x, Complex[] y) {
        if (x.length > N) throw new ArrayIndexOutOfBoundsException("x is larger than the FFT size");
        if (y.length != N) throw new ArrayIndexOutOfBoundsException("y must be same size as this FFT");
        tomem(x);
        jtFFT.complexForward(mem);
        frommem(y);
    }

    /**
     * Compute the discrete Fourier transform of x. If x.size is smaller than N
     * then the zero padded Fourier transform is computed
     *
     * @param x
     */
    public Complex[] forward(Complex[] x) {
        Complex[] y = new Complex[N];
        forward(x, y);
        return y;
    }

    public void inverse(Complex[] x, Complex[] y) {
        if (x.length != N) throw new ArrayIndexOutOfBoundsException("x must be same size as this FFT");
        if (y.length != N) throw new ArrayIndexOutOfBoundsException("y must be same size as this FFT");
        tomem(x);
        jtFFT.complexInverse(mem, true);
        frommem(y);
    }
    
    public Complex[] inverse(Complex[] x) {
        Complex[] y = new Complex[N];
        inverse(x, y);
        return y;
    }

    //fill internal memory with x, zero pad if required
    private void tomem(Complex[] x) {
        for (int i = 0; i < x.length; i++) { //would probably need a while loop to make this fast?
            mem[2*i] = x[i].re();
            mem[2*i+1] = x[i].im();
        }
        for (int i = x.length; i < N; i++) {
            mem[2*i] = 0;
            mem[2*i+1] = 0;
        }
    }

    //write internal double[2*N] memory to Complex[N]
    private void frommem(Complex[] y) {
        for (int i = 0; i < N; i++) y[i] = new Complex(mem[2*i], mem[2*i+1]);
    }
    
    /** Returns the FFT of x into y */
    public static void fft(Complex[] x, Complex[] y) {
        if(x.length != y.length) throw new ArrayIndexOutOfBoundsException("Arrays into fft(x,y) must be the same length");
        else new FFT(x.length).forward(x, y);
    }
    
    /** Return the FFT of x */
    public static Complex[] fft(Complex[] x) {
        Complex[] y = new Complex[x.length];
        fft(x,y);
        return y;
    }
    
  /** Compute a convolution using the fft.  The output is identical to Matlab's conv command */
  public static Complex[] conv(Complex[] a, Complex[] b){
    int L = a.length + b.length - 1;
    FFT fft = new FFT(L);
    Complex[] afft = fft.forward(a);
    Complex[] bfft = fft.forward(b);
    Complex[] cfft = new Complex[L];
    for(int i = 0; i < L; i++) cfft[i] = afft[i] * bfft[i];
    return fft.inverse(cfft);
  }
    
   /** 
   * Compute a convolution using the fft with the convolution `tails' removed.  
   * The output is identical to Matlab's:
   * conv(a,b,`valid')  when the length(a) >= length(b),  and 
   * conv(b,a,`valid') when length(a) =< length(b).
   */
    public static Complex[] conv_valid(Complex[] a, Complex[] b){
        int L = a.length + b.length - 1;
        int M = Math.min(a.length,b.length);
        Complex[] c = conv(a,b);
        Complex[] cslice = new Complex[2+L-2*M];
        for(int i = M-1; i <= L-M; i++) cslice[i-M+1] =c[i];
        return cslice;
  }
    
}
