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
}
