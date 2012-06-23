/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.leech;

import pubsim.lattices.LatticeAndNearestPointAlgorithm;
import pubsim.lattices.decoder.SphereDecoderSchnorrEuchner;

/**
 * Leech lattice with a sphere decoder (Schnorr and Euchner) that is seeded with
 * the covering radius sqrt(2.0).  This should be much faster than a sphere decoder seeded
 * with the Babai point.
 * @author Robby McKilliam
 */
public class LeechSeededSphereDecoder extends Leech implements LatticeAndNearestPointAlgorithm {
    
    protected final SphereDecoderSchnorrEuchner decoder;
    
    public LeechSeededSphereDecoder(){
        decoder = new SphereDecoderSchnorrEuchner(this);
    }

    @Override
    public void nearestPoint(double[] y) {
        decoder.nearestPoint(y,coveringRadius());
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
    
    private double[] yDoubletoy = new double[24];
    @Override
    public void nearestPoint(Double[] y) {
        for(int i = 0; i < y.length; i++) yDoubletoy[i] = y[i];
        this.nearestPoint(yDoubletoy);
    }

    
}
