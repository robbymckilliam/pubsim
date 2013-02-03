/*
 * Schnorr's 'trick' to complete a basis from a shortest vector,
 * see Step (5) of Algorithm C, p. 213, in
 *   C. P. Schnorr, 'A hierarchy of polynomial time lattice basis reduction
 *     algorithms', Theoretical Computer Science, vol. 53, pp. 201-224, 1987.
 *   doi: 10.1016/0304-3975(87)90064-8
 * @author Vaughan Clarkson
 */

package pubsim.lattices.reduction;

import Jama.Matrix;
import pubsim.VectorFunctions;
import pubsim.lattices.decoder.ShortVectorSphereDecoded;
import pubsim.lattices.reduction.LLL;
import pubsim.lattices.GeneralLattice;

public class BasisCompletion extends LLL {
    protected double shortest;

    @Override
    protected boolean notDone() {
	return R.getMatrix(0, m-1, 0, 0).normF() >= shortest / 2;
    }

    // Pre: v is a column vector containing the shortest vector of
    // the lattice basis in B
    public Matrix completeBasis(Matrix v, Matrix B) {
	shortest = v.normF();
	m = v.getRowDimension();
	n = B.getColumnDimension()+1;
	Matrix Bnew = new Matrix(m, n);
	Bnew.setMatrix(0, m-1, 0, 0, v);
	Bnew.setMatrix(0, m-1, 1, n-1, B);
	System.out.println("Bnew = ");
	Bnew.print(8, 2);
	Matrix Bred = reduce(Bnew);
	return Bred.getMatrix(0, m-1, 1, n-1);
    }

    @Override
    public Matrix getUnimodularMatrix() {
	return M.getMatrix(1, n-1, 1, n-1);
    }

    // Test harness
    public static void main(String args[]) {
	Matrix B = Matrix.random(3, 3);
	ShortVectorSphereDecoded svsd
	    = new ShortVectorSphereDecoded(new GeneralLattice(B));
	BasisCompletion cb = new BasisCompletion();
	cb.completeBasis(VectorFunctions.columnMatrix(svsd.getShortestVector()), B).print(8, 2);
	cb.getUnimodularMatrix().print(8, 2);
    }
}

    