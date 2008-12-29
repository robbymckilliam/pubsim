/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.poly;

import Jama.Matrix;
import lattices.GeneralLattice;
import lattices.decoder.SphereDecoder;
import simulator.VectorFunctions;

/**
 * This uses a nearest lattice point approach to remove the
 * ambiguities inherent in polynomial phase estimation.
 * @author Robby McKilliam
 */
public class AmbiguityRemover {

    protected int a;
    protected Matrix M;
    double[] p;
    SphereDecoder sd;

    protected AmbiguityRemover() {
    }

    /**
     * Public constructor.  Set the order polynomail phase estimator.
     * @param a = the number of parameters ie. order of polynomial + 1
     */
    public AmbiguityRemover(int a) {
        setSize(a);
    }

    protected void setSize(int a) {
        this.a = a;
        p = new double[a];
        M = constructBasisMatrix();
        GeneralLattice lattice = new GeneralLattice(M);
        sd = new SphereDecoder();
        sd.setLattice(lattice);
    }

    protected Matrix constructBasisMatrix() {
        Matrix B = new Matrix(a, a);
        double[] c = VectorFunctions.eVector(0, 1);
        for (int j = 0; j < a; j++) {
            for (int i = 0; i <= j; i++) {
                B.set(i, j, c[i]);
            }
            c = getNextColumn(c);
        }

        //System.out.println("M = " + VectorFunctions.print(B));

        return B;
    }

    /**
     * Recursively generates the columns of the abiguity lattice
     * generator matrix.
     * @param c previous column.
     * @return next column
     */
    protected static double[] getNextColumn(double[] c) {
        double[] r = new double[c.length + 1];

        //shift c
        for (int i = 1; i < r.length; i++) {
            r[i] = c[i - 1];
        }

        for (int i = 0; i < c.length; i++) {
            r[i] += c[i] * (c.length - 1);
        }

        for (int i = 0; i < r.length; i++) {
            r[i] /= c.length;
        }

        return r;
    }

    /**
     * This function removes the ambiguities from polynomial
     * phase signals.
     * @param p the parameter to remove ambibuity from
     * @return parameter in indentifiabile range.
     */
    public double[] disambiguate(double[] p) {
        if (a != p.length) {
            throw new RuntimeException("Parameter vector p is not the correct size.");
        }
        sd.nearestPoint(p);
        double[] np = sd.getLatticePoint();

        for (int i = 0; i < p.length; i++) {
            this.p[i] = p[i] - np[i];
        }
        return this.p;
    }
}
