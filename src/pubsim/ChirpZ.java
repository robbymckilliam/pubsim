package pubsim;

/**
 * The chirp z-transform.  Calculates the z-transform of a vector of length N along a 
 * discrete set of points on a spiral arc in the z plane represented by AW^k where A and W 
 * are complex numbers and k = 0,1,...,M-1.
 * 
 * Described in the classic paper 
 * L. Rabiner, R. Schafer and C. Rader, "The Chirp z-Transform algorithm and its application",
 * Bell Systems Technical Journal, May-June 1969
 * 
 * @author Robby McKilliam
 */
public class ChirpZ {
    
    ///The starting point on the z plane for the transform
    public final Complex A;
    
    ///The 'gradient' of the arc traverse
    public final Complex W;
    
    ///The length of the transform
    public final int M;
    
    ///The length of the input vector to apply the transform to.
    public final int N;
    
    ///The length of the forward and inverse transform used.
    public final int L;
    
    protected final FFT fft; //fft algorithm we use
    protected final Complex[] y; //working memory
    
    public ChirpZ(Complex A, Complex W, int M, int N){
        this.A = A;
        this.W = W;
        this.M = M;
        this.N = N;
        L = N+M-1;
        fft = new FFT(L);
        y = new Complex[L];
    }
    
    /** Returns the FFT of x into X */
    public void forward(Complex[] x, Complex[] X) {
        if(x.length != N) throw new ArrayIndexOutOfBoundsException("Length of input vector x must be " + N);
        if(X.length != M) throw new ArrayIndexOutOfBoundsException("Length of output vector X must be " + M);
        
        //fill the vector y
        //for(int n = 0; n < N-1; n++) y[n] = A.
        
        
    }
    
}
