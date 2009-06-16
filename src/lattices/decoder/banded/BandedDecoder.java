/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder.banded;

import lattices.GeneralLattice;
import lattices.Lattice;
import lattices.util.PointInSphere;
import simulator.SubMatrix;
import simulator.VectorFunctions;

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
public class BandedDecoder implements ConstrainedNearestPointAlgorithm{


    protected SubMatrix M;
    protected int band;
    protected double[] u, x;
    protected int n, b1n, b2n;

    protected ConstrainedNearestPointAlgorithm b1, b2;
    protected PointInSphere Cpoints;

    //constraints.
    Double[] c;

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
        n = M.getRowDimension();
        this.band = band;
        u = new double[n];
        x = new double[n];

        b1n = (int)Math.floor((n + band - 1)/2.0);
        b2n = (int)Math.ceil((n + band - 1)/2.0);

        System.out.println(b1n);
        System.out.println(b2n);

        //set no constraints by default
        c = new Double[n];

    }

    public void nearestPoint(double[] y) {
        
        double D = Double.POSITIVE_INFINITY;
        //get the three relavant sublattices matrices.
        SubMatrix sm1 = M.getSubMatrix(0, b1n-1, 0, b1n-1);
        SubMatrix sm2 = M.getSubMatrix(n - b2n, n-1, n - b2n, n-1);
        SubMatrix midM = M.getSubMatrix(n - b2n, b1n-1, n - b2n, b1n-1);

        //if(sm1.getColumnDimension() == band){
            b1 = new ConstrainedSphereDecoder(
                    new GeneralLattice(sm1.getJamaMatrix()));
        //}else{
        //    b1 = new BandedDecoder(sm1, band);
        //}

        //if(sm2.getColumnDimension() == band){
            b1 = new ConstrainedSphereDecoder(
                    new GeneralLattice(sm2.getJamaMatrix()));
        //}else{
        //    b1 = new BandedDecoder(sm2, band);
        //}

        double[] y1 = VectorFunctions.getSubVector(y, 0, b1n-1);
        b1.nearestPoint(y1);
        double d1 = VectorFunctions.distance_between2(y1, b1.getLatticePoint());
        double[] y2 = VectorFunctions.getSubVector(y, n - b2n, n-1);
        b2.nearestPoint(y2);
        double d2 = VectorFunctions.distance_between2(y2, b2.getLatticePoint());

        Cpoints = new PointInSphere(new GeneralLattice(midM.getJamaMatrix()),
                Math.sqrt(d1 + d2));

        while(Cpoints.hasMoreElements()){
            Double[] c1 = VectorFunctions.getSubVector(c, 0, b1n-1);

            Double[] c2 = VectorFunctions.getSubVector(c, n - b2n, n-1);
        }

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

    public void setConstraints(Double[] c) {
        if(n != c.length)
            throw new RuntimeException("The constraints are not the same" +
                    "dimension as the lattice!");
        this.c = c;
    }

    

}
