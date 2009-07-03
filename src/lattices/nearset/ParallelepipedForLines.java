/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.nearset;

import Jama.Matrix;
import lattices.util.region.Parallelepiped;
import static simulator.VectorFunctions.matrixMultVector;
import static simulator.VectorFunctions.subtract;

/**
 * A Parallelepiped that is able to compute the positions
 * that lines intersect it.
 * @author Robby McKilliam
 */
public class ParallelepipedForLines 
        extends Parallelepiped
        implements RegionForLines {

    /**
     * Parallelepiped defined by the column vectors in matrix M.
     * i.e. Mb where b \in [0, 1].
     * The matrix may be m x n where m >= n.
     * If m > n then the region is an infinite cylinder with
     * the parallelepiped as a cross-section.
     *
     * This Parallelepiped has one vertex at the origin.
     */
    public ParallelepipedForLines(Matrix M){
        super(M);
        init();
    }

    /**
     * Parallelepiped defined by the column vectors in matrix M
     * and a translation.
     * i.e. Mb + t where b \in [0, 1].
     * The matrix may be m x n where m >= n.
     * If m > n then the region is an infinite cylinder with
     * the parallelepiped as a cross-section.
     *
     */
    public ParallelepipedForLines(Matrix M, double[] t){
        super(M, t);
        init();
    }

    private double[] diff, ct, mt;
    
    private void init(){
        diff = new double[M.getRowDimension()];
        ct = new double[M.getColumnDimension()];
        mt = new double[M.getColumnDimension()];
    }

    private double min, max;
    
    public boolean linePassesThrough(double[] m, double[] c) {
        if(m.length != rowDimension() || c.length != rowDimension())
            throw new ArrayIndexOutOfBoundsException("y.length and c.length " +
                    "must be equal to the row dimension");

        subtract(c, t, diff);
        matrixMultVector(invM, diff, ct);
        matrixMultVector(invM, m, mt);

        min = Double.NEGATIVE_INFINITY;
        max = Double.POSITIVE_INFINITY;
        for(int n = 0; n < dimension(); n++){
            if(m[n] != 0.0){
                double sol1 =  -ct[n]/mt[n];
                double sol2 = (1.0 - ct[n])/mt[n];
                double rmin = Math.min(sol1, sol2);
                double rmax = Math.max(sol1, sol2);
                if(rmax < max)
                    max = rmax;
                if(rmin > min)
                    min = rmin;
            }else{
                if(ct[n] < 0.0 || ct[n] > 1.0) return false;
            }
        }

        //System.out.println(min + ", " + max);

        if(min > max) return false;
        else return true;
    }

    public double minParam() {
        return min;
    }

    public double maxParam() {
        return max;
    }



}
