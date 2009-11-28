/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.phaseunwrapping.oned;

import Jama.Matrix;
import lattices.GeneralLattice;
import lattices.decoder.Babai;
import lattices.decoder.BabaiNoLLL;
import lattices.decoder.GeneralNearestPointAlgorithm;
import lattices.decoder.SphereDecoder;
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
    double[] u;

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
        decoder.nearestPoint(By);
        System.arraycopy(decoder.getIndex(), 0, u, 0, decoder.getIndex().length);
        u[N-1] = 0.0;
        u[N-2] = 0.0;
        return u;
    }

    private Matrix Proj;

    public void setSize(int N){
        this.N = N;
        u = new double[N];

        int num_params = m*(N-w+1);
        Matrix M = new Matrix(num_params, num_params);
        Matrix K = new Matrix(num_params, N);
        Matrix P = new Matrix((N-w+1)*w, num_params);
        Matrix Y = new Matrix((N-w+1)*w, N);

        //construct matricies Y and P such that sum
        //of squares fucntion is
        // || Y(y-u) - P p ||^2
        constructP(N, num_params, P);
        constructY(N, num_params, Y);

        Matrix Pt = P.transpose();
        Matrix PtPinv = Pt.times(P).inverse();
        B = Y.minus(P.times(PtPinv).times(Pt).times(Y));

        System.out.println("B = \n" + VectorFunctions.print(B));
        
        Jama.QRDecomposition QR = new Jama.QRDecomposition(B);
        System.out.println("R = \n" + VectorFunctions.print(QR.getR()));
        decoder = new SphereDecoder(new GeneralLattice(
                B.getMatrix(0,B.getRowDimension()-1, 0, N-3)));
        
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
