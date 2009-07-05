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
    private double[] ccopy;
    private double Lbest = Double.POSITIVE_INFINITY;
    private NearestToLine lineSearch;

    protected RegionForLines R;
    protected Matrix P;

    public NearestInZnToAffineSurface(int N){
        this.N = N;
        u = new Double[N];
        ubest = new double[N];
    }

    public void compute(double[] c, Matrix P, RegionForLines R) {
        this.R = R;
        this.P = P;
        ccopy = c;
        pbest = new double[P.getColumnDimension()];
        lineSearch = new NearestToLine(N);
        decode(c, P);
    }

    protected void decode(double[] c, Matrix P){

        if(P.getColumnDimension() == 1){
             lineSearch.compute(c, P, R);
        }else{
            
            for(int n = 0; n < N; n++){
                if(u[n] == null){

                    double kmin = ceilToHalfInt(R.minInCoordinate(n));
                    double kmax = floorToHalfInt(R.maxInCoordinate(n));

                    for( double k : range(kmin, kmax) ) {

                        double[] cnew = columnMatrix(c).plus(
                                P.getMatrix(0, N-1, 0, 0).times(
                                (k - c[n])/P.get(n,0))).getColumnPackedCopy();

                        Matrix Pnew = P.getMatrix(0, N-1, 1, P.getColumnDimension()-1).minus(
                                P.getMatrix(0, N-1, 0, 0).times(
                                P.getMatrix(n,n, 1, P.getColumnDimension()-1)).times(
                                1.0/P.get(n, 0)));

                        //System.out.println(print(cnew));
                        //System.out.println(print(Pnew));

                        u[n] = k + 0.5;
                        decode(cnew, Pnew);
                        u[n] = k - 0.5;
                        decode(cnew, Pnew);

                        //System.out.println(print(u));

                    }
                }

                u[n] = null;

            }

        }

        round( add( matrixMultVector(P, pbest) , c ), ubest );
        
    }

    protected class NearestToLine
            implements NearestToAffineSurface {

        private final double[] m;
        private final double[] ut;
        private final Matrix invP;

        public NearestToLine(int N){
            m = new double[N];
            ut = new double[N];
            invP = P.inverse();
        }

        public void compute(double[] c, Matrix P, RegionForLines R) {
            if(P.getColumnDimension() != 1 )
                throw new ArrayIndexOutOfBoundsException("P must be a column vector.");
            packRowiseToArray(P, m);
            if( R.linePassesThrough(m, c) == false ) return;
            compute(c, m, R.minParam(), R.maxParam());
        }

        public void compute(double[] c, double[] m, double rmin, double rmax) {

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
            double[] uminusc = subtract(ut, ccopy);
             double[] p = matrixMultVector(invP, uminusc);
            double L = (P.times(columnMatrix(p)).
                                    minus(columnMatrix(uminusc))).normF();

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
                L = (P.times(columnMatrix(p)).
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
