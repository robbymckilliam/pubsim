/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.nearset;

import Jama.Matrix;
import java.util.Map.Entry;
import java.util.TreeMap;
import static simulator.VectorFunctions.min;

/**
 * Compute the nearest point in the integer lattice to the line
 * rm + c.
 * @author Robby McKilliam
 */
public class NearestToLineInRectangularLattice
        extends NearestToAffineSurface {

    private final double[] m, u, ubest, rret, d;
    private final int N;
    private double rbest;

    /**
     * Computes the nearest point to a line in the rectangular lattice.  The generator
     * matrix is diagonal with entries given in the vector d.
     * @param d Entries in diagonal generator matrix.  All entries must be positive
     */
    public NearestToLineInRectangularLattice(Matrix P, RegionForLines R, double[] d){
        super(P,R);
        if(P.getColumnDimension() != 1 )
            throw new ArrayIndexOutOfBoundsException("P must be a column vector.");
        if(min(d) < 0)
            throw new RuntimeException("Entries in d must be positive.");
        N = P.getRowDimension();
        m = P.getRowPackedCopy();
        u = new double[N];
        ubest = new double[N];
        rret = new double[1];
        this.d = d;
    }

    public void compute(double[] c) {
        R.linePassesThrough(m, c);
        compute(c, m, R.minParam(), R.maxParam());
    }

    protected void compute(double[] c, double[] m, double rmin, double rmax) {

        //compute dot products and map
        TreeMap<Double, Integer> map = new TreeMap<Double, Integer>();
        double alpha = 0;
        double beta = 0;
        double gamma = 0;
        for(int n = 0; n < N; n++){
            u[n] = Math.round((rmin*m[n] + c[n])/d[n]);
            double r = ( d[n]*(u[n] + 0.5*Math.signum(m[n])) - c[n])/m[n];
            map.put(new Double(r), new Integer(n));
            double dumc = d[n]*u[n] - c[n];
            alpha += m[n]*dumc;
            beta += dumc*dumc;
            gamma += m[n]*m[n];
        }

        //compute best (thus far) parameter r and distance L
        rbest = alpha/gamma;
        double Lbest = rbest*rbest*gamma - 2*rbest*alpha + beta;

        do{

            Entry<Double, Integer> entry = map.pollFirstEntry();
            int n = entry.getValue().intValue();

            //update dot products
            double s = Math.signum(m[n]);
            alpha += s*d[n]*m[n];
            beta += 2*s*d[n]*(d[n]*u[n] - c[n]) + d[n]*d[n];
            u[n] += s;

            //compute new parameter r and distance L
            double r = alpha/gamma;
            double L = r*r*gamma - 2*r*alpha + beta;

            if(L < Lbest){
                Lbest = L;
                rbest = r;
            }

            r = ( d[n]*(u[n] + 0.5*s) - c[n])/m[n];
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
