/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import Jama.Matrix;
import lattices.decoder.KissingNumber;
import lattices.decoder.ShortestVector;
import simulator.VectorFunctions;
import static simulator.Util.log2;
import static simulator.Util.pow2;
import static simulator.Util.erf;
import static simulator.Util.log2HyperSphereVolume;

/**
 * Abstract lattice that contains default operations for many of the
 * methods in interface Lattice.
 * @author Robby McKilliam
 */
public abstract class AbstractLattice implements Lattice {

    public double centerDensity() {
        return pow2(logCenterDensity());
    }

    public double logCenterDensity() {
        return getDimension()*log2(inradius()) - log2(volume());
    }

    public double logPackingDensity() {
        return logCenterDensity() + log2HyperSphereVolume(getDimension());
    }

    public double packingDensity() {
        return pow2(logPackingDensity());
    }
    

    /** 
     * This is Conway and Sloane's approximation for high SNR
     * probability of error in lattice coding.  Returrns log
     * base 10 of the probability of error.
     */
    public double log10ProbCodingError(double S) {
        double loge = Math.log10(Math.exp(1));
        int n = getDimension();
        double p = inradius();
        double Vn = pow2(2.0/n*log2HyperSphereVolume(n));
        double Vol = Math.pow( volume(), 2.0/n);
        return -0.5*n*p*p*Vn*loge*S/Vol;
    }

    public double probCodingError(double S) {
        return kissingNumber()/2.0 * (1.0 - erf(
                Math.sqrt( getDimension() * S / 2.0) *
                pow2( logPackingDensity()/getDimension() ) ) );
    }

    public double volume(){
        Matrix B = getGeneratorMatrix();
        return Math.sqrt((B.transpose().times(B)).det());
    }

    /**
     * Default way to compute the inradius is to compute
     * short vector is by sphere decoding.  This is going to
     * be very slow for large dimensions.
     */
    public double inradius(){
        ShortestVector sv = new ShortestVector(this);
        double norm = VectorFunctions.sum2(sv.getShortestVector());
        return Math.sqrt(norm)/2.0;
    }

    /**
     * By default this brute forces the kissing number by sphere decoding.
     * Lattices with known kissing numbers can override this.
     */
    public long kissingNumber() {
        lattices.decoder.KissingNumber k = new KissingNumber(this);
        return k.kissingNumber();
    }

}
