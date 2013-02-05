/*
 * Simple-minded implementation of Hermite-Korkin-Zolotarev reduction.
 * Uses Siegel-LLL with Schnorr-Euchner sphere decoding.
 * @author Vaughan Clarkson
 */

package pubsim.lattices.reduction;

import Jama.Matrix;
import Jama.QRDecomposition;
import pubsim.VectorFunctions;
import pubsim.lattices.decoder.ShortVectorSphereDecoded;
import pubsim.lattices.reduction.LLL;
import pubsim.lattices.Lattice;

public class HKZ implements TriangularLatticeReduction {
    protected BasisCompletion bc = new BasisCompletion();
    protected ShortVectorSphereDecoded svsd;
    protected Matrix R, M;

    @Override
    public Matrix reduce(Matrix B) {
	int m = B.getRowDimension();
	int n = B.getColumnDimension();
	QRDecomposition qrd = new QRDecomposition(B);
	R = qrd.getR();
	LLL lll = new LLL();
	lll.reduce(R);
	R = lll.getR();
	M = lll.getUnimodularMatrix();
	for (int j = 0; j < n-1; j++) {
	    Matrix Rsub = R.getMatrix(j, n-1, j, n-1);
	    svsd = new ShortVectorSphereDecoded(new Lattice(Rsub));
	    Matrix sv = VectorFunctions.columnMatrix(svsd.getShortestVector());
	    bc.completeBasis(sv, Rsub);
	    Rsub = bc.getR();
	    R.setMatrix(j, n-1, j, n-1, Rsub);
	    Matrix Mdash = bc.getUnimodularMatrix();
	    M.setMatrix(0, n-1, j, n-1, 
			M.getMatrix(0, n-1, j, n-1).times(Mdash));
	}
	Hermite hermite = new Hermite();
	R = hermite.reduce(R);
	M = M.times(hermite.getUnimodularMatrix());
	return B.times(M);
    }

    @Override
    public Matrix getUnimodularMatrix() {
	return M;
    }

    @Override
    public Matrix getR() {
	return R;
    }

    // Test harness
    public static void main(String args[]) {
	int dim = 5;
	Matrix B = Matrix.random(dim, dim);
	System.out.println("B = ");
	B.print(8, 2);
	HKZ hkz = new HKZ();
	Matrix Bred = hkz.reduce(B);
	System.out.println("Bred = ");
	Bred.print(8, 2);
	Matrix Rred = hkz.getR();
	System.out.println("Rred = ");
	Rred.print(8, 2);
	System.out.println("Mred = ");
	hkz.getUnimodularMatrix().print(8, 2);
	hkz.reduce(Bred);
	Matrix Rredred = hkz.getR();
	double sqtr = 0;
	for (int j = 0; j < dim; j++) {
	    double dd = Rred.get(j, j) - Rredred.get(j, j);
	    sqtr += dd * dd;
	}
	System.out.println("error = " + sqtr);
    }
}