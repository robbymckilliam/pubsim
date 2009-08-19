/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator;

import Jama.Matrix;
import lattices.LatticeAndNearestPointAlgorithm;
import lattices.util.IntegerVectors;
import static simulator.VectorFunctions.matrixMultVector;
import static simulator.VectorFunctions.multiplyInPlace;
import static simulator.VectorFunctions.modInPlace;
import static simulator.VectorFunctions.round;
import static simulator.VectorFunctions.subtract;
import static simulator.VectorFunctions.add;
import static simulator.VectorFunctions.sum2;

/**
 * Implements Conway and Sloane's Voronoi codes.
 * @author Robby McKilliam
 */
public class VoronoiCode implements java.io.Serializable{

    LatticeAndNearestPointAlgorithm lattice;

    /** translation */
    protected final double[] a;

    /** scale */
    protected final int r;

    /** */
    protected final int N, M;

    //generator matrix for the lattice
    private final Matrix B, invB;

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
        B = lattice.getGeneratorMatrix();
        M = B.getRowDimension();
        N = B.getColumnDimension();
        invB = B.inverse();
        x = new double[B.getRowDimension()];
        c = new double[B.getRowDimension()];
        u = new double[B.getColumnDimension()];
    }

    /** Voronoi code without a translation */
    protected VoronoiCode(LatticeAndNearestPointAlgorithm lattice, int scale){
        this.lattice = lattice;
        r = scale;
        B = lattice.getGeneratorMatrix();
        M = B.getRowDimension();
        N = B.getColumnDimension();
        a = new double[B.getRowDimension()];
        invB = B.inverse();
        x = new double[B.getRowDimension()];
        c = new double[B.getRowDimension()];
        u = new double[B.getColumnDimension()];
    }

    /**
     * @param u codeword (index) with elements in  {0,1, 2, ..., r-1}
     * @return code that is a translated lattice point.
     */
    public double[] encode(double[] u){
        matrixMultVector(B, u, x);
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
       matrixMultVector(invB, c, u);
       round(u, u);
       modInPlace(u, r);
       return u;
    }

    /** Compute the average power of the codewords */
    public double averagePower(){
        double pow = 0.0;
        IntegerVectors ints = new IntegerVectors(B.getColumnDimension(), r);
        for( Matrix U : ints){
            double[] x= encode(U.getColumnPackedCopy());
            pow += sum2(x);
        }
        return pow/Math.pow(r, M);
    }

    public double[] getTranslation() {
        return a;
    }

    /**
     * Return the scale used for this Voronoi code.
     * This is the multiplier for codeword boundary.
     */
    public double getScale(){ return r; }

    /** Return the lattice used for this Voronoi code */
    public LatticeAndNearestPointAlgorithm getLattice(){ return lattice; }

    /**
     * Dimension that the constellation lies in.  This is the same as
     * the dimension of the lattice or rank of it's generator matrix.
     */
    public int getDimension() { return N; }

}
