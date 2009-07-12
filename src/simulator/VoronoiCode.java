/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator;

import Jama.Matrix;
import lattices.LatticeAndNearestPointAlgorithm;
import static simulator.VectorFunctions.matrixMultVector;
import static simulator.VectorFunctions.multiplyInPlace;
import static simulator.VectorFunctions.times;
import static simulator.VectorFunctions.modInPlace;
import static simulator.VectorFunctions.round;
import static simulator.VectorFunctions.subtract;
import static simulator.VectorFunctions.add;

/**
 * Implements Conway and Sloane's Voronoi codes.
 * @author Robby McKilliam
 */
public class VoronoiCode {

    LatticeAndNearestPointAlgorithm lattice;

    /** translation */
    protected final double[] a;

    /** scale */
    protected int r;

    //generator matrix for the lattice
    private final Matrix M, invM;

    //some memory
    private final double[] x, u, c;

    /**
     *
     * @param lattice lattice that this code is made from
     * @param trans translation of the lattice
     * @param scale scale factor for the code boundary
     */
    public VoronoiCode(LatticeAndNearestPointAlgorithm lattice, double[] trans, int scale){
        this.lattice = lattice;
        a = trans;
        r = scale;
        M = lattice.getGeneratorMatrix();
        invM = M.inverse();
        x = new double[M.getRowDimension()];
        c = new double[M.getRowDimension()];
        u = new double[M.getColumnDimension()];
    }

    /** Voronoi code without a translation */
    protected VoronoiCode(LatticeAndNearestPointAlgorithm lattice, int scale){
        this.lattice = lattice;
        r = scale;
        M = lattice.getGeneratorMatrix();
        a = new double[M.getRowDimension()];
        invM = M.inverse();
        x = new double[M.getRowDimension()];
        c = new double[M.getRowDimension()];
        u = new double[M.getColumnDimension()];
    }

    /**
     * @param u codeword (index) with elements in  {0,1, 2, ..., r-1}
     * @return code that is a translated lattice point.
     */
    public double[] encode(double[] u){
        matrixMultVector(M, u, x);
        subtract(x, a, x);
        multiplyInPlace(x, 1.0/r);
        lattice.nearestPoint(x);
        subtract(x, lattice.getLatticePoint(), x);
        multiplyInPlace(x, (double)r);
        return x;
    }

    /**
     * Given a recieved vector, decode it to a codeword
     * @param x reciveved vector
     * @return codeword
     */
    public double[] decode(double[] x){
       lattice.nearestPoint(x);
       add(lattice.getLatticePoint(), a, c);
       matrixMultVector(invM, c, u);
       round(u, u);
       modInPlace(u, r);
       return u;
    }

}
