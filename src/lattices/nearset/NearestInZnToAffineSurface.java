/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.nearset;

import Jama.Matrix;
import static simulator.VectorFunctions.columnMatrix;
import static simulator.VectorFunctions.print;

/**
 * Compute the neares point in the integer lattice Zn to an
 * affine surface that lies in a specified region.
 * @author Robby McKilliam
 */
public class NearestInZnToAffineSurface
        implements NearestToAffineSurface {

    private final int N;
    private final Double[] u;

    protected RegionForLines R;

    public NearestInZnToAffineSurface(int N){
        this.N = N;
        u = new Double[N];
    }

    public void compute(double[] c, Matrix P, RegionForLines R) {
        this.R = R;
        decode(c, P);
    }

    protected void decode(double[] c, Matrix P){

        if(P.getColumnDimension() == 1){
            //solve line problem
        }else{
            
            for(int n = 0; n < N; n++){
                if(u[n] == null){

                    //for halfint k
                    double k = 0.5;

                    double[] cnew = columnMatrix(c).plus(
                            P.getMatrix(0, N-1, 0, 0).times(
                            (k - c[n])/P.get(n,0))).getColumnPackedCopy();

                    Matrix Pnew = P.getMatrix(0, N-1, 1, P.getColumnDimension()-1).minus(
                            P.getMatrix(0, N-1, 0, 0).times(
                            P.getMatrix(n,n, 1, P.getColumnDimension()-1)).times(
                            1.0/P.get(n, 0)));

                    System.out.println(print(cnew));
                    System.out.println(print(Pnew));
                    
                    u[n] = k + 0.5;
                    decode(cnew, Pnew);
                    //u[n] = k - 0.5;
                    //decode(cnew, Pnew);
                    
                    u[n] = null;
                }
            }

        }

    }

    public double[] nearestPoint() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double[] nearestParams() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
