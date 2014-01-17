package pubsim.lattices.decoder.firstkind;

import Jama.Matrix;
import pubsim.VectorFunctions;
import pubsim.lattices.Lattice;
import pubsim.lattices.LatticeAndNearestPointAlgorithmInterface;

/**
 * Class describes lattices of Voronoi's first kind.  These lattices have an obtuse superbasis.
 * Many usually hard problems, such as computing a vector or finding a nearest point are polynomial
 * time for this class of lattices.  See the papers:
 * 
 * R. McKilliam, A. Grant, "Finding short vectors in a lattice of Voronoi's first kind", ISIT 2012, pages 2157 - 2160
 * 
 * R. McKilliam, A. Grant, I. V. L. Clarkson, "Finding a closest lattice point in a lattice of Voronoi's first kind" submitted to SIAM Journal of Discrete Mathematics, Jan 2014.
 * 
 * @author Robby McKilliam
 */
public class LatticeOfFirstKind extends Lattice implements LatticeAndNearestPointAlgorithmInterface {
    
    /** Matrix with columns contains the obtuse superbasis */
    final protected Matrix sB;
    /** The extended Gram matrix */
    final protected Matrix eQ;

    /** Construct a lattice of first kind with basis B.  Will check whether the basis can
     * be extended to an obtuse superbasis and will throw a runtime exception if it cannot.  To
     * avoid potential problem with numerical precision during this check, you can set the
     * tolerance.  Entries with absolute values less that tolerance are set to zero
     * in the extended Gram matrix.
     * @param B
     * @param tolerance 
     */
    public LatticeOfFirstKind(Matrix B, double tolerance) {
        super(B);
        final int M = B.getRowDimension();
        final int N = B.getColumnDimension();
        
        //setup obtuse superbasis matrix 
        sB = new Matrix(M,N+1);
        for(int m = 0; m < M; m++)
            for(int n = 0; n < N; n++)
                sB.set(m,n, B.get(m, n));
        for(int m = 0; m < M; m++){
            double b = 0.0;
            for(int n = 0; n < N; n++) b += sB.get(m, n);
            sB.set(m,N,-b);
        }
        
        //compute extended gram matrix and assert that this is a lattice of first type
        //i.e., assert that off diagonal entries are zero
        eQ = sB.transpose().times(sB);
        for(int i = 0; i < N+1; i++)
            for(int j = i+1; j < N+1; j++) {
                if(Math.abs(eQ.get(i,j)) < tolerance) eQ.set(i,j,0.0);
                if(eQ.get(i,j) > 0) throw new RuntimeException("Not an obtuse superbasis!");
            }
                //System.out.println(VectorFunctions.print(eQ));
    }
    
    
    /** Default tolerance is 1e-12 */
    public LatticeOfFirstKind(Matrix B) {
        this(B,1e-12);
    }
    
    /** Returns a LatticeOfFirstKind given extended Gram matrix eQ.  
     * Currently does not compute the obtuse superbasis from Q and might break other code
     * It is possible be compute the basis from this Q, use a reduced rank Cholesky
     */
    public static LatticeOfFirstKind constructFromExtendedGram(Matrix eQ){
        final int N = eQ.getColumnDimension()-1;
        //fill entries of the standard Gram matrix (just chop off the last row and column of eQ)
        final Matrix Q = eQ.getMatrix(0, N-1, 0, N-1);
        //System.out.println(VectorFunctions.print(Q));
        final Matrix B = Q.chol().getL().transpose(); //Cholesky decomposition to get back generator
        //System.out.println(VectorFunctions.print(B));
        //System.out.println(VectorFunctions.print(B.transpose().times(B)));
        return new LatticeOfFirstKind(B, 1e-10);
    }
    
    /** Return a matrix with columns continaing the superbasis vectors */
    public Matrix superbasis() {
        return sB;
    }
    
    /** Return the extended Gram matrix */
    public Matrix extendedGram() {
        return eQ;
    }
    

    @Override
    public void nearestPoint(double[] y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nearestPoint(Double[] y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double[] getLatticePoint() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double[] getIndex() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double distance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
