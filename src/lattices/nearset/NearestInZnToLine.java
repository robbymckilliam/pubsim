/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.nearset;

import Jama.Matrix;
import java.util.Map.Entry;
import java.util.TreeMap;
import static simulator.VectorFunctions.packRowiseToArray;

/**
 * Compute the nearest point in the integer lattice to the line
 * rm + c.
 * @author Robby McKilliam
 */
public class NearestInZnToLine
        extends NearestToAffineSurface {

    private final double[] m, u, ubest, rret;
    private final int N;
    private double rbest;

    public NearestInZnToLine(Matrix P, RegionForLines R){
        super(P,R);
        if(P.getColumnDimension() != 1 )
            throw new ArrayIndexOutOfBoundsException("P must be a column vector.");
        N = P.getRowDimension();
        m = P.getRowPackedCopy();
        u = new double[N];
        ubest = new double[N];
        rret = new double[1];
    }

    public void compute(double[] c) {
        R.linePassesThrough(m, c);
        compute(c, m, R.minParam(), R.maxParam());
    }

    protected void compute(double[] c, double[] m, double rmin, double rmax) {

        //compute dot products and map
        TreeMap<Double, Integer> map = new TreeMap<Double, Integer>();
        double mtm = 0;
        double utm = 0;
        double utu = 0;
        double mtc = 0;
        double ctc = 0;
        double utc = 0;
        for(int n = 0; n < N; n++){
            u[n] = Math.round(rmin*m[n] + c[n]);
            double r = (u[n] + 0.5*Math.signum(m[n]) - c[n])/m[n];
            map.put(new Double(r), new Integer(n));
            utu += u[n]*u[n];
            mtm += m[n]*m[n];
            ctc += c[n]*c[n];
            utm += u[n]*m[n];
            utc += u[n]*c[n];
            mtc += m[n]*c[n];
        }

        //compute best (thus far) parameter r and distance L
        rbest = (utm - mtc)/mtm;
        double Lbest = utu - 2*rbest*utm - 2*utc + rbest*rbest*mtm
                        + 2*rbest*mtc + ctc;

        //System.out.println("L = " + Lbest +  ", r = " + rbest);

        do{

            Entry<Double, Integer> entry = map.pollFirstEntry();
            int n = entry.getValue().intValue();

            //update dot products
            double s = Math.signum(m[n]);
            utu += s*2*u[n] + 1;
            utm += s*m[n];
            utc += s*c[n];
            u[n] += s;

            //compute new parameter r and distance L
            double r = (utm - mtc)/mtm;
            double L = utu - 2*r*utm - 2*utc + r*r*mtm
                        + 2*r*mtc + ctc;

            //System.out.println("L = " + L + ",  n = " + n + ", r = " + r);

            if(L < Lbest){
                Lbest = L;
                rbest = r;
            }

            r = (u[n] + 0.5*s - c[n])/m[n];
            if( r < rmax )
                map.put(new Double(r), new Integer(n));

        }while(!map.isEmpty());

        //record best point and parameter r.
        rret[0] = rbest;
        for(int n = 0; n < N; n++)
            ubest[n] = Math.round( rbest*m[n] + c[n]);

    }

    public double[] nearestPoint() {
        return ubest;
    }

    public double[] nearestParams() {
        return rret;
    }
    
    public double nearestParam() {
        return rbest;
    }

}
