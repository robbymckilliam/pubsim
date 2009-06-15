/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder.banded;

import lattices.GeneralLattice;
import lattices.Lattice;
import lattices.NearestPointAlgorithm;
import lattices.decoder.SphereDecoder;
import simulator.SubMatrix;

/**
 * Decoder for lattices with banded generator matrices.
 * This requires at most only O(N^log(N)) operations,
 * an improvement on the sphere decoder.
 *
 * This class assumes that the generator matrix is
 * upper triangular, square and banded.  Note that
 * any banded matrix can be put in this form by
 * a QR decompostion, so this is general.
 * EXPERIMENTAL
 * @author Robby McKilliam
 */
public class BandedDecoder implements NearestPointAlgorithm{


    protected SubMatrix M;
    protected int band;
    protected double[] u, x;
    protected int n, b1n, b2n;

    protected NearestPointAlgorithm b1, b2;

    /**
     * Assumes column and row bands are the same length.
     * @param L The lattice to decode
     * @param band The length of the band
     */
    public BandedDecoder(Lattice L, int band){
        init(new SubMatrix(L.getGeneratorMatrix()), band);
    }

    /**
     * Internal constructor that takes a submatrix rather
     * than a lattice.  This is to avoid making multiple copies
     * of matrix elements.
     * @param L Submatrix that is the generator for the lattice
     * @param band The length of the band
     */
    protected BandedDecoder(SubMatrix L, int band){
        init(L, band);
    }

    private void init(SubMatrix L, int band){
        M = L;
        n = M.getColumnDimension();
        this.band = band;
        u = new double[n];
        x = new double[n];

        b1n = (int)Math.floor((n + band - 1)/2.0);
        b2n = (int)Math.ceil((n + band - 1)/2.0);

        System.out.println(b1n);
        System.out.println(b2n);

    }

    public void nearestPoint(double[] y) {
        
        double D = Double.POSITIVE_INFINITY;
        //get the three relavant sublattices matrices.
        SubMatrix sm1 = M.getSubMatrix(0, b1n-1, 0, b1n-1);
        SubMatrix sm2 = M.getSubMatrix(n - b2n, n-1, n - b2n, n-1);
        SubMatrix midM = M.getSubMatrix(n - b2n, b1n-1, n - b2n, b1n-1);
        
        //System.out.println(VectorFunctions.print(M.getJamaMatrix()));
        //System.out.println(VectorFunctions.print(sm1.getJamaMatrix()));
        //System.out.println(VectorFunctions.print(sm2.getJamaMatrix()));
        //System.out.println(VectorFunctions.print(midM.getJamaMatrix()));

        Double[] constaints1 = new Double[sm1.getColumnDimension()];
        Lattice L1 = new GeneralLattice(sm1.getJamaMatrix());
        ConstrainedSphereDecoder cd1 = new ConstrainedSphereDecoder(L1, constaints1, D);

        Double[] constaints2 = new Double[sm2.getColumnDimension()];
        Lattice L2 = new GeneralLattice(sm2.getJamaMatrix());
        ConstrainedSphereDecoder cd2 = new ConstrainedSphereDecoder(L1, constaints1, D);
    }

    /**
     * Find the nearest point given constraints on index vector.
     * Returns the distance squared to the nearest point.
     */
    protected double nearestPoint(double[] y, Double[] c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double[] getLatticePoint() {
        return x;
    }

    public double[] getIndex() {
        return u;
    }

}
