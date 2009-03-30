/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.phaseunwrapping.twod;

import Jama.Matrix;
import java.util.Vector;
import lattices.GeneralLattice;
import lattices.decoder.Babai;
import lattices.decoder.GeneralNearestPointAlgorithm;
import simulator.VectorFunctions;

/**
 * First order two dimensional unwrapper with window size 3x3
 * @author Robby McKilliam
 */
public class FirstOrderUnwrapper implements TwoDUnwrapperInterface{

    protected int N, M;
    Vector<Double[]> Yv, Pv;
    Matrix Y, P, B;
    GeneralNearestPointAlgorithm decoder;

    public double[][] unwrap(double[][] y) {
        double[][] u = unwrapIntegers(y);
        double[][] yunwrapped = new double[M][N];
        for(int m = 0; m < M; m++){
            for(int n = 0; n < N; n++){
                yunwrapped[m][n] = y[m][n] - u[m][n];
            }
        }
        return yunwrapped;
    }

    public double[][] unwrapIntegers(double[][] y) {
        Matrix D = new Matrix(y);
        if(M != y.length || N != y[0].length) setSize(y.length, y[0].length);
        double[] ypacked = D.getRowPackedCopy();
        //System.out.println(VectorFunctions.print(ypacked));
        double[] By = VectorFunctions.matrixMultVector(B, ypacked);
        //System.out.println(VectorFunctions.print(By));
        decoder.nearestPoint(By);
        double[] u = new double[M*N];
        System.arraycopy(decoder.getIndex(), 0, u, 1, decoder.getIndex().length);
        //u[0] = 0.0;
        //u[u.length-1] = 0.0;
        //u[u.length-2] = 0.0;
        //u[u.length-3] = 0.0;
        System.out.println("u = " + VectorFunctions.print(VectorFunctions.packRowiseToMatrix(u, M)));
        return VectorFunctions.packRowiseToMatrix(u, M);
    }

    public void setSize(int M, int N) {
        this.N = N;
        this.M = M;
        Yv = new Vector<Double[]>();
        Pv = new Vector<Double[]>();

        for(int m = 0; m < M-2; m++){
            for(int n = 0; n < N-2; n++){
                constructMatricesForParameter(m,n);
            }
        }

        Y = copyVectorToMatrix(Yv);
        P = copyVectorToMatrix(Pv);

        //System.out.println(VectorFunctions.print(Y));
        //System.out.println(VectorFunctions.print(P));

        Matrix Pt = P.transpose();
        Matrix PtPinv = Pt.times(P).inverse();
        B = Y.minus(P.times(PtPinv).times(Pt).times(Y));

        //System.out.println(VectorFunctions.print(B));
        //Matrix BtB = B.transpose().times(B);
        //System.out.println(VectorFunctions.print(B.transpose().times(B)));
        Jama.QRDecomposition QR = new Jama.QRDecomposition(B);
        B = QR.getR();
        B = B.getMatrix(0, M*N-3, 0, M*N-1);
        //System.out.println(VectorFunctions.print(B));

        //System.out.println(VectorFunctions.print(B.getMatrix(0, M*N-3, 1, M*N-3)));
        //Removed the first and last two columns from B.  This
        //is a little abitrary.  I am only doing this after
        //observing the rref, and am not sure why the matrix
        //has this form.
        decoder = new Babai(new GeneralLattice(
                B.getMatrix(0, M*N-3, 1, M*N-3)));

    }


    public void constructMatricesForParameter(int m, int n){

        int y_length = N*M;
        int p_length = (N-2)*(M-2);
        
        for(int i = m-1; i <= m+1; i++){
            for(int j = n-1; j <= n+1; j++){

                //add row to Y matrix
                Double[] yrow = new Double[y_length];
                yrow[returnYIndex(i,j)] = 1.0;
                Yv.add(yrow);

                //add row to P matrix
                Double[] prow = new Double[3*p_length];
                prow[3*returnPIndex(m,n)] = (double)i;
                prow[3*returnPIndex(m,n)+1] = (double)j;
                prow[3*returnPIndex(m,n)+2] = 1.0;
                Pv.add(prow);

            }
        }

    }

    /*
     * Copy Vector<Double[]> into a matrix.  Assumes that the
     * Double[] are all the same length.
     */
    protected static Matrix copyVectorToMatrix(Vector<Double[]> v){
        int M = v.size();
        int N = v.get(0).length;
        Matrix B = new Matrix(M, N);
        for(int m = 0; m < M; m++){
            for(int n = 0; n < N; n++){
                Double val = v.get(m)[n];
                if(val == null)
                    val = 0.0;
                B.set(m, n, val.doubleValue());
            }
        }
        return B;
    }

    /**
     *
     */
    protected int returnPIndex(int m, int n){
        return m*(N-2) + n;
    }

    /**
     * 
     */
    protected int returnYIndex(int m, int n){
        return (m+1)*N + (n+1);
    }

}
