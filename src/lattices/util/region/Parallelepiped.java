/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.util.region;

import Jama.Matrix;
import static simulator.VectorFunctions.matrixMultVector;
import static simulator.VectorFunctions.subtract;

/**
 *
 * @author Robby McKilliam
 */
public class Parallelepiped implements Region {

    protected Matrix M;
    protected Matrix invM;
    protected double[] t;

    /**
     * Parallelepiped defined by the column vectors in matrix M.
     * i.e. Mb where b \in [0, 1].
     * The matrix may be m x n where m >= n.
     * If m > n then the region is an infinite cylinder with
     * the parallelepiped as a cross-section.
     *
     * This Parallelepiped has one vertex at the origin.
     */
    public Parallelepiped(Matrix M){
        init(M, new double[M.getRowDimension()]);
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
    public Parallelepiped(Matrix M, double[] t){
        init(M, t);
    }

    private double[] diff, p;
    private void init(Matrix M, double[] t){
        if(M.getRowDimension() != t.length)
            throw new ArrayIndexOutOfBoundsException("t.length must be equal to the row dimension");
        this.M = M;
        invM = M.inverse();
        this.t = t;
        diff = new double[M.getRowDimension()];
        p = new double[M.getColumnDimension()];
    }

    public boolean within(double[] y) {
        if(y.length != rowDimension())
            throw new ArrayIndexOutOfBoundsException("y.length must be equal to the row dimension");

        double[][] mat = M.getArray();

        subtract(y, t, diff);

        //System.out.print(print())

        matrixMultVector(invM, diff, p);
        for(int n = 0; n < dimension(); n++){
            if(Math.floor(p[n]) != 0.0) return false;
        }
        return true;
    }

    public int dimension() {
        return M.getColumnDimension();
    }

    /**
     * Number of rows in the matrix.
     * This is greater than dimension if the matrix
     * is rectangular.
     */
    public int rowDimension() {
        return M.getRowDimension();
    }

    /** Same as dimension. */
    public int columnDimension() {
        return dimension();
    }

}
