/*
 *
 */

package lattices.reduction;

import Jama.Matrix;
import Jama.QRDecomposition;
import javax.vecmath.Vector2d;
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
                for(int t = 0; t < B.getRowDimension(); t++){
                    double bval = Bcopy.get(t,j+1) - k*Bcopy.get(t,j);
                    Bcopy.set(t, j+1, bval);
                }
                
                VectorFunctions.swapColumns(Bcopy, j, j+1);
                VectorFunctions.swapColumns(M, j, j+1);
                
                //this should be replaced with a Givens rotation
                Jama.QRDecomposition QRt = new QRDecomposition(Bcopy);
                R = QRt.getR();
//                
//                System.out.println(VectorFunctions.print(R));
                
//                Matrix G = Matrix.identity(n, n);
//                double d = Math.sqrt(rj1j1*rj1j1 + R.get(j, j+1)*R.get(j, j+1));
//                G.set(j, j, R.get(j+1,j+1)/d);
//                G.set(j, j+1, -R.get(j,j+1)/d);
//                G.set(j+1, j, R.get(j,j+1)/d);
//                G.set(j+1, j+1, R.get(j+1,j+1)/d);
//                
//                R = G.times(R);
//                VectorFunctions.swapRows(R, j, j+1);
//                VectorFunctions.swapColumns(R, j, j+1);                
                
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
    public Matrix getUnimodularMatrix() {
        return M;
    }
    
}
