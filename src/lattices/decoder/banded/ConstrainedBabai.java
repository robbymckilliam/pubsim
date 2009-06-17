/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder.banded;

import Jama.Matrix;
import lattices.Lattice;
import simulator.VectorFunctions;

/**
 * Constrained Babai decoder that can enforce some indicies to
 * take particular values.  This does not use LLL, because of
 * the constraints. Really, this is a utility class for the
 * BandedDecoder, it's unlikely to have other uses.
 * @author Robby McKilliam
 */
public class ConstrainedBabai implements ConstrainedNearestPointAlgorithm{

    /** Generator matrix of the lattice */
    protected Matrix G;

    /** Index of the Babai point. x = Gu */
    protected double[] u;

    /** The Babai point */
    protected double[] x;

    /**
     * Point y in triangular reference frame.
     * yr = Q'y
     */
    protected double[] yr;


    /** R component of B = QR */
    protected Matrix R;

    /** Q component of B = QR */
    protected Matrix Q;

    protected int n, m;

    /** Array of contraints */
    protected Double[] c;

    public ConstrainedBabai(Lattice L){
        setLattice(L);
        setConstraints(new Double[L.getDimension()]);
    }

    public ConstrainedBabai(Lattice L, Double[] c){
        setLattice(L);
        setConstraints(c);
    }

    /** Set the constraints for this decoder */
    public void setConstraints(Double[] c){
        if(n != c.length)
            throw new RuntimeException("The constraints are not the same" +
                    "dimension as the lattice!");
        this.c = c;
    }

    protected void setLattice(Lattice L) {
        G = L.getGeneratorMatrix().copy();
        m = G.getRowDimension();
        n = G.getColumnDimension();
        x = new double[m];
        yr = new double[n];

        Jama.QRDecomposition QR = new Jama.QRDecomposition(G);
        R = QR.getR();
        Q = QR.getQ();

    }

    //compute the babai
    protected void computeBabaiPoint(double[] y) {

         VectorFunctions.matrixMultVector(Q.transpose(), y, yr);

        for (int i = n - 1; i >= 0; i--) {
            double rsum = 0.0;
            for (int j = n - 1; j > i; j--) {
                rsum += R.get(i, j) * u[j];
            }
            if(c[i] == null)
                u[i] = Math.round((yr[i] - rsum) / R.get(i, i));
            else
                u[i] = c[i].doubleValue();
        }

        //System.out.println(VectorFunctions.print(R));
        //System.out.println(VectorFunctions.print(Q));
        //System.out.println(VectorFunctions.print(Q));

        //compute Babai point
        VectorFunctions.matrixMultVector(G, u, x);

    }

    public void nearestPoint(double[] y) {
        if(m != y.length)
            throw new RuntimeException("Point y and Generator matrix are of different dimension!");
        computeBabaiPoint(y);
    }

    public double[] getLatticePoint() {
        return x;
    }

    public double[] getIndex() {
        return u;
    }

    public void nearestPoint(double[] y, double D) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
