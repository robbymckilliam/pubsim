/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder.banded;

import lattices.Lattice;
import lattices.decoder.GeneralNearestPointAlgorithm;
import simulator.SubMatrix;

/**
 * Decoder for lattices with banded generator matrices.
 * This requires at most only O(N^log(N)) operations,
 * an improvement on the sphere decoder.
 * EXPERIMENTAL
 * @author Robby McKilliam
 */
public class BandedDecoder implements GeneralNearestPointAlgorithm{


    protected SubMatrix M;
    protected int cband, rband;
    protected double[] u, x;
    protected int n, m;
    

    /**
     *
     * @param L The lattice to decode
     * @param colband The length of the band along the columns
     * @param rowband The length of the band along the rows
     */
    public BandedDecoder(Lattice L, int colband, int rowband){
        init(L, colband, rowband);
    }

    /**
     * Assumes column and row bands are the same length.
     * @param L The lattice to decode
     * @param band The length of the band
     */
    public BandedDecoder(Lattice L, int band){
        init(L, band, band);
    }

    private void init(Lattice L, int colband, int rowband){
        M = new SubMatrix(L.getGeneratorMatrix());
        n = M.getColumnDimension();
        m = M.getRowDimension();
        cband = colband;
        rband = rowband;
        u = new double[n];
        x = new double[m];
    }

    public void setLattice(Lattice G) {
        throw new UnsupportedOperationException("This is not supported. " +
                "Set the lattice in the constructor");
    }

    public void nearestPoint(double[] y) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double[] getLatticePoint() {
        return x;
    }

    public double[] getIndex() {
        return u;
    }

}
