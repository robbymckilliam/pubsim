/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.nearset;

import Jama.Matrix;
import java.util.Map.Entry;
import java.util.TreeMap;
import static simulator.VectorFunctions.print;
import static simulator.VectorFunctions.packRowiseToArray;
import static simulator.VectorFunctions.subtract;
import static simulator.VectorFunctions.copy;
import static simulator.VectorFunctions.add;
import static simulator.VectorFunctions.round;
import static simulator.VectorFunctions.matrixMultVector;
import static simulator.VectorFunctions.columnMatrix;
import static simulator.Range.range;
import static simulator.Util.floorToHalfInt;
import static simulator.Util.ceilToHalfInt;


/**
 * Compute the nearest point in the integer lattice Zn to an
 * affine surface that lies in a specified region.
 * @author Robby McKilliam
 */
public class NearestInZnToAffineSurface
        extends NearestToAffineSurface {

    private final int N;
    private final Double[] u;
    private final double[] ubest;
    private final double[] pbest;
    private final Matrix invP;

    private double[] ccopy;
    private final Matrix Pcopy;
    private double Lbest;

    public NearestInZnToAffineSurface(Matrix P, RegionForLines R){
        super(P, R);
        N = P.getRowDimension();
        u = new Double[N];
        ubest = new double[N];
        pbest = new double[P.getColumnDimension()];
        invP = P.inverse();
        Pcopy = P;
    }

    public void compute(double[] c) {
        Lbest = Double.POSITIVE_INFINITY;
        ccopy = c;
        decode(c, P);
        round( add( matrixMultVector(P, pbest) , c ), ubest );
    }

    protected void decode(double[] c, Matrix P){

        if(P.getColumnDimension() == 1){
             (new NearestToLine(P, R)).compute(c);
        }else{
            
            for(int n = 0; n < N; n++){
                if(u[n] == null){

                    double kmin = ceilToHalfInt(R.minInCoordinate(n));
                    double kmax = floorToHalfInt(R.maxInCoordinate(n));

                    //System.out.println("kmin = " + kmin + ", kmax = " + kmax);

                    Matrix Pnew = P.getMatrix(0, N-1, 1, P.getColumnDimension()-1).minus(
                    P.getMatrix(0, N-1, 0, 0).times(
                    P.getMatrix(n,n, 1, P.getColumnDimension()-1)).times(
                    1.0/P.get(n, 0)));

                    for( double k : range(kmin, kmax) ) {

                        double[] cnew = columnMatrix(c).plus(
                                P.getMatrix(0, N-1, 0, 0).times(
                                (k - c[n])/P.get(n,0))).getColumnPackedCopy();

                        //System.out.println(print(cnew));
                        //System.out.println(print(Pnew));

                        u[n] = k + 0.5;
                        decode(cnew, Pnew);
                        u[n] = k - 0.5;
                        decode(cnew, Pnew);

                        //System.out.println(print(u));

                    }                   
                    u[n] = null;                 
                }
            }
        }       
    }

    protected class NearestToLine
            extends NearestToAffineSurface {

        private final double[] m;
        private final double[] ut;

        public NearestToLine(Matrix P, RegionForLines R){
            super(P, R);
            m = P.getColumnPackedCopy();
            ut = new double[N];
        }

        public void compute(double[] c) {
            if( R.linePassesThrough(m, c) == false ) return;
            compute(c, m, R.minParam(), R.maxParam());
        }

        protected void compute(double[] c, double[] m, double rmin, double rmax) {

            //compute dot products and map
            TreeMap<Double, Integer> map = new TreeMap<Double, Integer>();
            for(int n = 0; n < N; n++){
                if(u[n] == null){
                    ut[n] = Math.rint(rmin*m[n] + c[n]);
                    double r = (ut[n] + 0.5*Math.signum(m[n]) - c[n])/m[n];
                    map.put(new Double(r), new Integer(n));
                }else{
                    ut[n] = u[n];
                }
            }

            //compute best (thus far) parameter p and distance L
            //this is a slow version at the moment.

            //System.out.println(print(Pcopy));

            double[] uminusc = subtract(ut, ccopy);
            double[] p = matrixMultVector(invP, uminusc);
            
            //System.out.println(print(p));
            
            double L = (Pcopy.times(columnMatrix(p)).
                                    minus(columnMatrix(uminusc))).normF();

            //System.out.println(print(ut));
            //System.out.println((L));
            //System.out.println("L = " + L );

            if(L < Lbest){
                    Lbest = L;
                    copy(p, pbest);
            }

            do{

                Entry<Double, Integer> entry = map.pollFirstEntry();
                int n = entry.getValue().intValue();

                //update dot products
                double s = Math.signum(m[n]);
                ut[n] += s;
                
                //compute the parameter p and distance L
                //this is a slow version at the moment.
                uminusc = subtract(ut, ccopy);
                p = matrixMultVector(invP, uminusc);
                L = (Pcopy.times(columnMatrix(p)).
                                    minus(columnMatrix(uminusc))).normF();

                //System.out.println(print(ut));
                //System.out.println((L));
                //System.out.println("L = " + L );

                if(L < Lbest){
                    Lbest = L;
                    copy(p, pbest);
                }

                double r = (ut[n] + 0.5*s - c[n])/m[n];
                if( r < rmax )
                    map.put(new Double(r), new Integer(n));

            }while(!map.isEmpty());

        }

        public double[] nearestPoint() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public double[] nearestParams() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    public double[] nearestPoint() {
        return ubest;
    }

    public double[] nearestParams() {
        return pbest;
    }

}
