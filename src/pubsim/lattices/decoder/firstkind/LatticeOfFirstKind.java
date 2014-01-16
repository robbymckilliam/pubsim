package pubsim.lattices.decoder.firstkind;

import Jama.Matrix;
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
    
    public LatticeOfFirstKind(Matrix B){
        super(B);
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
