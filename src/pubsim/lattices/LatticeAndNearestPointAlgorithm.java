package pubsim.lattices;

import Jama.Matrix;
import pubsim.lattices.decoder.SphereDecoderSchnorrEuchner;

/**
 * General lattice with a nearest point algorithm included with it.
 * By default the sphere decoder is used but you can use other
 * algorithms by using the appropriate constructor
 * @author Robby McKilliam
 */
public class LatticeAndNearestPointAlgorithm extends Lattice implements LatticeAndNearestPointAlgorithmInterface {

    private NearestPointAlgorithmInterface decoder;

    public LatticeAndNearestPointAlgorithm(Matrix B){
        super(B);
        decoder = new SphereDecoderSchnorrEuchner(this);
    }

    public LatticeAndNearestPointAlgorithm(double[][] B){
        super(new Matrix(B));
        decoder = new SphereDecoderSchnorrEuchner(this);
    }

     public LatticeAndNearestPointAlgorithm(Matrix B, NearestPointAlgorithmInterface np){
        super(B);
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
