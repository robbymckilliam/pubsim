package pubsim.lattices;

import Jama.Matrix;
import pubsim.lattices.decoder.SphereDecoderSchnorrEuchner;

/**
 * General lattice with a nearest point algorithm included with it.
 * By default the sphere decoder is used but you can use other
 * algorithms by using the appropriate constructor
 * @author Robby McKilliam
 */
public class GeneralLatticeAndNearestPointAlgorithm extends GeneralLattice implements LatticeAndNearestPointAlgorithm {

    private NearestPointAlgorithm decoder;

    public GeneralLatticeAndNearestPointAlgorithm(Matrix B){
        this.B = B;
        decoder = new SphereDecoderSchnorrEuchner(this);
    }

    public GeneralLatticeAndNearestPointAlgorithm(double[][] B){
        this.B = new Matrix(B);
        decoder = new SphereDecoderSchnorrEuchner(this);
    }

     public GeneralLatticeAndNearestPointAlgorithm(Matrix B, NearestPointAlgorithm np){
        this.B = B;
        decoder = np;
    }

    @Override
    public void nearestPoint(double[] y) {
        decoder.nearestPoint(y);
    }
    
    @Override
    public void nearestPoint(Double[] y) {
        decoder.nearestPoint(y);
    }

    @Override
    public double[] getLatticePoint() {
        return decoder.getLatticePoint();
    }

    @Override
    public double[] getIndex() {
        return decoder.getIndex();
    }

    @Override
    public double distance() {
        return decoder.distance();
    }

}
