/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.phaseunwrapping.twod;

import Jama.Matrix;
import robbysim.VectorFunctions;

/**
 *
 * @author Robby
 */
public class ZerothOrderUnwrapper implements TwoDUnwrapperInterface{

    protected int M, N;
    Matrix B;

    public double[][] unwrap(double[][] y) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double[][] unwrapIntegers(double[][] y) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setSize(int M, int N) {
        this.M = M;
        this.N = N;

        int num_data = M*N;
        int num_params = num_data - 1;

        int num_sq_eqs = 3*(N-1)*(M-1) + 2*(N+M-2);
        Matrix Y = new Matrix(num_sq_eqs, num_data);
        Matrix P = new Matrix(num_sq_eqs, num_params);
        int row = 0;
        for(int m = 0; m < M; m++){
            for(int n = 0; n < N; n++){
                int tind = returnArrayIndex(m,n);
                int ind = returnArrayIndex(m,n);
                System.out.println(ind);
                matrixSet(P, row, tind, 1.0);
                row += matrixSet(Y, row, ind, 1.0);
                if(n+1 < N){
                    ind = returnArrayIndex(m,n+1);
                    System.out.println(ind);
                    matrixSet(P, row, tind, 1.0);
                    row += matrixSet(Y, row, ind, 1.0);
                }
                if(m+1 < M){
                    ind = returnArrayIndex(m+1,n);
                    System.out.println(ind);
                    matrixSet(P, row, tind, 1.0);
                    row += matrixSet(Y, row, ind, 1.0);
                }
            //System.out.println(VectorFunctions.print(Y));
            }
        }

        System.out.println("P = \n" + VectorFunctions.print(P));
        System.out.println("Y = \n" + VectorFunctions.print(Y));

        Matrix Pt = P.transpose();
        Matrix PtPinv = Pt.times(P).inverse();
        B = Y.minus(P.times(PtPinv).times(Pt).times(Y));

        System.out.println("B = \n" + VectorFunctions.print(B));
        //Matrix BtB = B.transpose().times(B);
        //System.out.println(VectorFunctions.print(B.transpose().times(B)));
        Jama.QRDecomposition QR = new Jama.QRDecomposition(B);
        B = QR.getR();
        //B = B.getMatrix(0, M*N-3, 0, M*N-3);
        System.out.println("R = \n" + VectorFunctions.print(B));



    }

    /**
     * Returns the array index corresponding to matrix index (m, n)
     */
    protected int returnArrayIndex(int m, int n){
        return m*N + n;
    }

    /**
     * Special matrix set that ignores indicies outside size.
     * Returns 1 if added val to array, otherwise 0.
     */
    public static int matrixSet(Matrix M, int m, int n, double val){
        int p = 0;
        if(m >= 0 && m < M.getRowDimension()
                && n >= 0 && n < M.getColumnDimension()){
            M.set(m,n,val);
            p = 1;
        }
        return p;
    }

}
