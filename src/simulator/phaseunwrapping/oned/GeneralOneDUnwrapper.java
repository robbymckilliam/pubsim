/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.phaseunwrapping.oned;

import Jama.Matrix;
import lattices.GeneralLattice;
import lattices.decoder.BabaiNoLLL;
import lattices.decoder.GeneralNearestPointAlgorithm;
import simulator.VectorFunctions;

/**
 * The most general of the 1D unwrappers.  You can set
 * window size and polynomial order.  The appropriate basis
 * matrix is then created.  One issue is that the matrix
 * operations generally contain numerical errors that make
 * LLL reduction do strange things.  Currently this uses
 * BabaiNoLLL to approximate the nearest point.
 *
 * UNDER CONTRUCTION.  Matrices are properly generated for
 * all parameters yet.
 *
 * @author Robby McKilliam
 */
public class GeneralOneDUnwrapper implements OneDUnwrapperInterface{

    int m, w, N;
    Matrix B;
    GeneralNearestPointAlgorithm decoder;

    protected GeneralOneDUnwrapper(){
    }


    public GeneralOneDUnwrapper(int approx_order, int approx_size){
        m = approx_order;
        w = approx_size;
        if( (w & 1) == 0 ) throw new Error("approx_size must be odd for now");
    }

    public double[] unwrap(double[] y){
        if(N != y.length) setSize(y.length);
        double[] By = VectorFunctions.matrixMultVector(B, y);
        double[] fts = VectorFunctions.matrixMultVector(Proj, y);
        System.out.println(VectorFunctions.print(fts));
        decoder.nearestPoint(By);
        return decoder.getIndex();
    }

    private Matrix Proj;

    public void setSize(int N){
        this.N = N;

        int num_params = m*(N-w+1);
        Matrix M = new Matrix(num_params, num_params);
        Matrix K = new Matrix(num_params, N);
        Matrix P = new Matrix((N-w+1)*w, num_params);
        Matrix Y = new Matrix((N-w+1)*w, N);

        //construct matricies K and M such that
        // K(y - u) = M p
        constructK(N, K);
        constructM(N, M);

        System.out.println("K = \n" + VectorFunctions.print(K));
        System.out.println("M = \n" + VectorFunctions.print(M));

        //construct matricies Y and P such that sum
        //of squares fucntion is
        // || Y(y-u) - P p ||^2
        constructP(N, num_params, P);
        constructY(N, num_params, Y);

        System.out.println("P = \n" + VectorFunctions.print(P));
        System.out.println("Y = \n" + VectorFunctions.print(Y));

        //compute the matrix B = Y - P inv(M'M) M' K
        Matrix Mt = M.transpose();
        Matrix MtMinv = Mt.times(M).inverse();
        B = Y.minus(P.times(MtMinv).times(Mt).times(K));

        decoder = new BabaiNoLLL(new GeneralLattice(B));

        Proj = MtMinv.times(Mt).times(K);
        //System.out.println(VectorFunctions.print(Proj));
        //System.out.println(VectorFunctions.print(M));
        //System.out.println(VectorFunctions.print(MtM));
        //System.out.println(VectorFunctions.print(MtM.inverse()));
        //System.out.println(VectorFunctions.print(B));
        
    }


    /**
     * Return the sum of interger from M to N to the power P ie.
     * M^P + (M+1)^P + ... + N^2
     */
    public static double sumMtoNpow(int M, int N, double P){
        double sum = 0;
        for(int m = M; m <= N; m++){
            sum += Math.pow(m, P);
        }
        return sum;
    }

    protected void constructP(int N, int num_params, Matrix P) {
        int foff = w / 2;
        int rowi = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i - foff - 1; j < i + foff; j++) {
                if (2 * j >= 0 && 2 * j + 1 < num_params) {
                    P.set(rowi, 2 * j, 1.0);
                    P.set(rowi, 2 * j + 1, i + 1);
                    rowi++;
                }
            }
        }
    }

    protected void constructK(int N, Matrix K) {
        //compute K matrix
        for (int p = 0; p < m; p++) {
            for (int i = p * (N - w + 1); i < (p + 1) * (N - w + 1); i++) {
                for (int j = i - p * (N - w + 1); j < i - p * (N - w + 1) + w; j++) {
                    //System.out.println(i + ", " + j);
                    K.set(i, j, Math.pow(j + 1, p));
                }
            }
            //System.out.println(VectorFunctions.print(K));
        }
    }

    protected void constructM(int N, Matrix M) {
        //compute M matrix
        for (int p = 0; p < m; p++) {
            for (int i = 0; i < (N - w + 1); i++) {
                int jp = p;
                for (int j = 2 * i; j < 2 * i + m; j++) {
                    //System.out.println((i + 1) + ", " + j);
                    M.set(i + p * (N - w + 1), j, sumMtoNpow(i + 1, i + w, jp));
                    jp++;
                }
            }
            //System.out.println(VectorFunctions.print(M));
        }
    }

    protected void constructY(int N, int num_params, Matrix Y) {
        int foff = w / 2;
        int rowi = 0;
        int coli = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i - foff - 1; j < i + foff; j++) {
                if (2 * j >= 0 && 2 * j + 1 < num_params) {
                    Y.set(rowi, coli, 1.0);
                    rowi++;
                }
            }
            coli++;
            //System.out.println(VectorFunctions.print(Y));
        }
    }

}
