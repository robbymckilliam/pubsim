/*
 *
 */

package pubsim.lattices.reduction;

import Jama.Matrix;
import pubsim.VectorFunctions;

/**
 * The Lenstra, Lenstra and Lovas lattice reduction algorithm.
 * @author Robby McKilliam
 * Modified by Vaughan Clarkson to make it extensible for the purpose
 * of lattice basis completion.
 */
public class LLL implements LatticeReduction{
    
    protected LatticeReduction hermite = new Hermite();
    
    /** Unimodular matrix such that reduce(B) = BM */
    protected Matrix M;
    protected Matrix R; // triangularised basis
    protected int j; // loop variable
    protected int m; // embedded dimension
    protected int n; // lattice rank

    protected boolean notDone() {
	return j < n-1;
    }

    @Override
    public Matrix reduce(Matrix B){
        if(B == null){
            M = null;
            return null;
        }
        
        Matrix Bcopy = B.copy();
        
        //this is the dimension of the lattice.  Need to check this!
        n = B.getColumnDimension();
        m = B.getRowDimension();
        
        //set the unimodular matrix
        M = Matrix.identity(n, n);
        
        Jama.QRDecomposition QR = new Jama.QRDecomposition(Bcopy);
        R = QR.getR();
	j = 0;
	int iter = 0;
        while(notDone()){
	    if (iter < 40) {
		System.out.println("j = " + j);
		System.out.println("R = ");
		R.print(8, 2);
		System.out.println("M = ");
		M.print(8, 2);
	    }
	    iter++;


            double rjj = R.get(j,j);
            double rj1j1 = R.get(j+1, j+1);
            
            if( rjj*rjj > 2 * rj1j1*rj1j1 ){
                
                double k = Math.round( R.get(j, j+1) / rjj );
		System.out.println("k = " + k);
                
                for(int t = 0; t < n; t++){
                    double rval = R.get(t,j+1) - k*R.get(t,j);
                    R.set(t, j+1, rval);
                    double mval = M.get(t,j+1) - k*M.get(t,j);
                    M.set(t, j+1, mval);
                }
                for(int t = 0; t < m; t++){
                    double bval = Bcopy.get(t,j+1) - k*Bcopy.get(t,j);
                    Bcopy.set(t, j+1, bval);
                }
                
                VectorFunctions.swapColumns(Bcopy, j, j+1);
                VectorFunctions.swapColumns(M, j, j+1);
                               
                //QR decomposition version.  Superceeded by Given's rotation
//                Jama.QRDecomposition QRt = new Jama.QRDecomposition(Bcopy);
//                R = QRt.getR();
                            
                //Swap columns and Given's rotate R to make it triangular.
                VectorFunctions.swapColumns(R, j, j+1);
                VectorFunctions.givensRotate(R, j, j+1, j);
                
                        
                if(j > 0) j--;
                
            }else{
                j++;
            }
        }
        
        Bcopy = hermite.reduce(Bcopy);
        M = M.times(hermite.getUnimodularMatrix());
        //M = hermite.getUnimodularMatrix().times(M);
        
        return Bcopy;
    }

    /** {@inheritDoc} */
    @Override
    public Matrix getUnimodularMatrix() {
        return M;
    }
    
}
