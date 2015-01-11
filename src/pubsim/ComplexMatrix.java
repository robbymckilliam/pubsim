package pubsim;

/**
 * Operations for complex matricies.  This just uses and underlying
 * double size real matrix for doing basically everything.
 * @author Robby McKilliam
 */
public class ComplexMatrix {

    protected final double[][] D;
    protected final Complex[][] C;
    protected final Jama.Matrix J;

    /** Number of columns */
    public final int N;

    /** Number of rows*/
    public final int M;

    public ComplexMatrix(Complex[][] C){
        N = C[0].length; //number of columns
        M = C.length; //number of rows
        this.C = C;
        D = complexToDouble(C);
        J = new Jama.Matrix(D);
    }
    
    public ComplexMatrix times(Complex c){
        Complex[][] Cnew = new Complex[M][N];
        for(int m = 0; m < M; m++)
            for(int n = 0; n < N; n++)
                Cnew[n][m] = C[n][m].multiply(c);
        return new ComplexMatrix(Cnew);
    }
    
    public static ComplexMatrix kroneckerProduct(ComplexMatrix A, ComplexMatrix B){
        Complex[][] C = new Complex[A.M*B.M][A.N*B.N];
        for(int am = 0; am < A.M; am++){
            for(int an = 0; an < A.N; an++){
                Complex a = A.getComplexArray()[am][an];
                for(int bm = 0; bm < B.M; bm++){
                    for(int bn = 0; bn < B.N; bn++){
                        C[am*B.M + bm][an*B.N + bn] = a.times(B.getComplexArray()[bm][bn]);
                    }
                }
            }
        }
        return new ComplexMatrix(C);
    }

    /**
     * Multiply two complex matrices.
     * Just uses the underlying Jama matrix.
     */
    public ComplexMatrix times(ComplexMatrix B){
        return new ComplexMatrix(
                doubleToComplex( J.times(B.getJamaMatrix()).getArray() ));
    }
    
    public Complex[][] getComplexArray() { return C; }
    public double[][] getDoubleArray() { return D; }
    public Jama.Matrix getJamaMatrix() { return J; }

    @Override
    public String toString() {
        return VectorFunctions.print(C);
    }

    public static double[][] complexToDouble(Complex[][] C){
        int N = C[0].length; //number of columns
        int M = C.length; //number of rows
        double[][] D = new double[2*M][2*N];
        for(int m = 0; m < M; m++){
            for(int n = 0; n < N; n++){
                Complex c = C[m][n];
                D[2*m][2*n] = c.re(); D[2*m][2*n+1] = c.im();
                D[2*m+1][2*n] = -c.im(); D[2*m+1][2*n+1] = c.re();
            }
        }
        return D;
    }

    public static Complex[][] doubleToComplex(double[][] D){
        if(D.length % 2 != 0 || D[0].length % 2 != 0)
            throw new ArrayIndexOutOfBoundsException("D must have "
                    + "even column and row dimensions");
        int N = D[0].length/2; //number of columns
        int M = D.length/2; //number of rows
        Complex[][] C = new Complex[M][N];
        for(int m = 0; m < M; m++){
            for(int n = 0; n < N; n++) 
                C[m][n] = new Complex(D[2*m][2*n], D[2*m][2*n+1]);
        }
        return C;
    }

    /**
     * Convert two double[] into Complex[] y
     * Requires memory to be previously allocated.
     */
    public static void toComplexArray(double[] rreal, double[] rimag, Complex[] y) {
        for(int n = 0; n < y.length; n++){
            y[n] = new Complex(rreal[n], rimag[n]);
        }
    }

    /**
     * Convert two double[] into Complex[] y, allocates memory and
     * returns the array.
     */
    public static Complex[] toComplexArray(double[] rreal, double[] rimag) {
        Complex[] y = new Complex[rreal.length];
        for(int n = 0; n < y.length; n++){
            y[n] = new Complex(rreal[n], rimag[n]);
        }
        return y;
    }


    public static double[][] toDoubleMatrix(Complex c){
        double[][] D = new double[2][2];
        D[0][0] = c.re(); D[0][1] = c.im();
        D[1][0] = -c.im(); D[1][1] = c.re();
        return D;
    }

    /** Return complex number corresponding a 2x2 linear transformation */
    public static Complex toComplex(double[][] D){
        return new Complex(D[0][0], D[0][1]);
    }


}
