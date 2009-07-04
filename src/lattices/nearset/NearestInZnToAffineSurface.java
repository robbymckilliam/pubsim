/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.nearset;

import Jama.Matrix;
import java.util.Map.Entry;
import java.util.TreeMap;
import static simulator.VectorFunctions.columnMatrix;
import static simulator.VectorFunctions.print;
import static simulator.VectorFunctions.packRowiseToArray;

/**
 * Compute the neares point in the integer lattice Zn to an
 * affine surface that lies in a specified region.
 * @author Robby McKilliam
 */
public class NearestInZnToAffineSurface
        implements NearestToAffineSurface {

    private final int N;
    private final Double[] u;
    private final double[] ubest;
    private double[] pbest;

    protected RegionForLines R;

    public NearestInZnToAffineSurface(int N){
        this.N = N;
        u = new Double[N];
        ubest = new double[N];
    }

    public void compute(double[] c, Matrix P, RegionForLines R) {
        this.R = R;
        pbest = new double[P.getColumnDimension()];
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

    protected class NearestToLine
            implements NearestToAffineSurface {

        private final double[] m;
        private final double[] ut;

        public NearestToLine(int N){
            m = new double[N];
            ut = new double[N];
        }

        public void compute(double[] c, Matrix P, RegionForLines R) {
            if(P.getColumnDimension() != 1 )
                throw new ArrayIndexOutOfBoundsException("P must be a column vector.");
            packRowiseToArray(P, m);
            R.linePassesThrough(m, c);
            compute(c, m, R.minParam(), R.maxParam());
        }

        public void compute(double[] c, double[] m, double rmin, double rmax) {

            //compute dot products and map
            TreeMap<Double, Integer> map = new TreeMap<Double, Integer>();
            for(int n = 0; n < N; n++){
                if(u[n] == null){
                    double y = rmin*m[n] + c[n];
                    ut[n] = Math.rint(y);
                    double r = (ut[n] + 0.5*Math.signum(m[n]) - c[n])/m[n];
                    map.put(new Double(r), new Integer(n));
                }else{
                    ut[n] = u[n];
                }
            }

            //compute best (thus far) parameter r and distance L
//            rbest = (utm - mtc)/mtm;
//            double Lbest = utu - 2*rbest*utm - 2*utc + rbest*rbest*mtm
//                            + 2*rbest*mtc + ctc;

            //System.out.println("L = " + Lbest +  ", r = " + rbest);

            do{

                Entry<Double, Integer> entry = map.pollFirstEntry();
                int n = entry.getValue().intValue();

                //update dot products
                double s = Math.signum(m[n]);
                ut[n] += s;

                //compute new parameter r and distance L
                //System.out.println("L = " + L + ",  n = " + n + ", r = " + r);

//                if(L < Lbest){
//                    Lbest = L;
//                    rbest = r;
//                }

                double r = (ut[n] + 0.5*s - c[n])/m[n];
                if( r < rmax )
                    map.put(new Double(r), new Integer(n));

            }while(!map.isEmpty());

            //record best point and parameter r.
//            rret[0] = rbest;
//            for(int n = 0; n < N; n++)
//                ubest[n] = Math.round( rbest*m[n] + c[n]);

        }

        public double[] nearestPoint() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public double[] nearestParams() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    public double[] nearestPoint() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double[] nearestParams() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
