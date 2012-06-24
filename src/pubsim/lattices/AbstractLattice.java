/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices;

import Jama.Matrix;
import static pubsim.Util.*;
import pubsim.VectorFunctions;
import pubsim.lattices.decoder.KissingNumber;
import pubsim.lattices.decoder.ShortVectorSphereDecoded;

/**
 * Abstract lattice that contains default operations for many of the
 * methods in interface Lattice.
 * @author Robby McKilliam
 */
public abstract class AbstractLattice implements Lattice {
    
    @Override
    public double secondMoment() {throw new UnsupportedOperationException(); }

    @Override
    public double centerDensity() {
        return pow2(logCenterDensity());
    }

    @Override
    public double logCenterDensity() {
        return getDimension()*log2(inradius()) - logVolume();
    }

    @Override
    public double logVolume(){
        return log2(volume());
    }

    @Override
    public double logPackingDensity() {
        return logCenterDensity() + log2HyperSphereVolume(getDimension());
    }

    @Override
    public double packingDensity() {
        return pow2(logPackingDensity());
    }
    
    /*
     * Hermite parameter, also known as nomial coding gain
     */
    @Override
    public double hermiteParameter(){
        double rho = inradius();
        double logv = 2.0*logVolume()/getDimension(); //using logarithm tests to be more stable
        return rho*rho/Math.pow(2.0,logv);
    }
    @Override
    public double nominalCodingGain() { return hermiteParameter(); }

    /*
     * Effective coding gain.  See page 2396 of 
     * FORNEY AND UNGERBOECK: MODULATION AND CODING FOR LINEAR GAUSSIAN CHANNELS.
     * The argument S is a 'normalised' signal to noise ratio.
     * This assumes no shaping gain, i.e. rectangular shaping region
     */
    @Override
    public double probCodingError(double S) {
        return kissingNumber() * Q(Math.sqrt(nominalCodingGain() * 3.0 * S));
    }

    @Override
    public double volume(){
        Matrix B = getGeneratorMatrix();
        return Math.sqrt((B.transpose().times(B)).det());
    }

    //make consecutive calls to inradius and kissing number run fast.
    private double inradius;
    private long kissingnumber;

    /**
     * Default way to compute the inradius is to compute
     * short vector is by sphere decoding.  This is going to
     * be very slow for large dimensions.
     */
    @Override
    public double inradius(){
        if(inradius == 0){
            ShortVectorSphereDecoded sv = new ShortVectorSphereDecoded(this);
            double norm = VectorFunctions.sum2(sv.getShortestVector());
            inradius = Math.sqrt(norm)/2.0;
        }
        return inradius;
    }

    /**
     * By default this brute forces the kissing number by sphere decoding.
     * Lattices with known kissing numbers can override this.
     */
    @Override
    public long kissingNumber() {
        if(kissingnumber == 0){
            pubsim.lattices.decoder.KissingNumber k = new KissingNumber(this);
            kissingnumber = k.kissingNumber();
        }
        return kissingnumber;
    }
    
    @Override
    public String name() { 
        throw new UnsupportedOperationException("This lattice doesn't have a name!"); 
    }


    
}
