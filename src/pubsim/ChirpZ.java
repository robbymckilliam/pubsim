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
    protected final Complex[] y, v, Y, V, G, g; //working memory
    
    public ChirpZ(Complex A, Complex W, int M, int N){
        this.A = A;
        this.W = W;
        this.M = M;
        this.N = N;
        L = N+M-1;
        fft = new FFT(L);
        y = new Complex[L];
        v = new Complex[L];
        Y = new Complex[L];
        V = new Complex[L];
        G = new Complex[L];
        g = new Complex[L];
    }
    
    /// Returns the Chirp-Z transform of x into X
    public void compute(Complex[] x, Complex[] X) {
        if(x.length != N) throw new ArrayIndexOutOfBoundsException("Length of input vector x must be " + N);
        if(X.length != M) throw new ArrayIndexOutOfBoundsException("Length of output vector X must be " + M);
        
        //fill the vectors y and v
        for(int n = 0; n < N; n++) y[n] = A.pow(-n).times(W.pow(n*n/2.0)).times(x[n]);
        for(int n = N; n < L; n++) y[n] = Complex.zero;
        for(int n = 0; n < M; n++) v[n] = W.pow(-n*n/2.0);
        for(int n = L-N+1; n < L; n++) v[n] = W.pow(-(L-n)*(L-n)/2.0);
        
        //compute the Fourier transforms of y and v
        fft.forward(y,Y);
        fft.forward(v,V);
        
        //compute elementwise product of the transforms followed by the inverse Fourier transform
        for(int r = 0; r < L; r++) G[r] = Y[r].multiply(V[r]);
        fft.inverse(G, g);
        
        //fill output
        for(int k = 0; k < M; k++) X[k] = W.pow(k*k/2.0).multiply(g[k]);    
    }
    
    /// Returns the Chirp-Z transform.  Allocates memory 
    public Complex[] compute(Complex[] x){
        Complex[] X = new Complex[M];
        compute(x,X);
        return X;
    }
    
    /// Return chirp z-transform of x containing M points along the spiral defined by A and W
    public static Complex[] compute(Complex A, Complex W, int M, Complex[] x) {
        return new ChirpZ(A,W,M,x.length).compute(x);
    }
    
}
