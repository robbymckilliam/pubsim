/*
 *
 */

package lattices.reduction;

import Jama.Matrix;
import simulator.VectorFunctions;

/**
 * The Lenstra, Lenstra and Lovas lattice reduction algorithm.
 * @author Robby McKilliam
 */
public class LLL implements LatticeReduction{
    
    protected LatticeReduction hermite = new Hermite();
    
    /** Unimodular matrix such that reduce(B) = BM */
    protected Matrix M;

    public Matrix reduce(Matrix B){
        if(B == null){
            M = null;
            return null;
        }
        
        Matrix Bcopy = B.copy();
        
        //this is the dimension of the lattice.  Need to check this!
        int n = B.getColumnDimension();
        int m = B.getRowDimension();
        
        //set the unimodular matrix
        M = Matrix.identity(n, n);
        
        Jama.QRDecomposition QR = new Jama.QRDecomposition(Bcopy);
        Matrix R = QR.getR();
        int j = 0;
        while( j < n-1 ){
            
            double rjj = R.get(j,j);
            double rj1j1 = R.get(j+1, j+1);
            
            if( rjj*rjj > 2 * rj1j1*rj1j1 ){
                
                double k = Math.round( R.get(j, j+1) / rjj );
                
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

        //System.out.println("THIS SHOULD NOT BE CALLED!");
        
        return Bcopy;
    }

    /** {@inheritDoc} */
    public Matrix getUnimodularMatrix() {
        return M;
    }
    
}
