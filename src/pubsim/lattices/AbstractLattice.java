/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices;

import Jama.Matrix;
import pubsim.lattices.decoder.KissingNumber;
import pubsim.lattices.decoder.ShortVectorSphereDecoded;
import pubsim.VectorFunctions;
import static pubsim.Util.log2;
import static pubsim.Util.pow2;
import static pubsim.Util.erf;
import static pubsim.Util.erfc;
import static pubsim.Util.Q;
import static pubsim.Util.log2HyperSphereVolume;
import static pubsim.Util.hyperSphereVolume;

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
    

    /** 
     * This is Conway and Sloane's approximation for high SNR
     * probability of error in lattice coding.
     */
    @Override
    public double probCodingError(double S) {
        if(S < 0) throw new RuntimeException("S cannot be negative input for probCodingError");
        int n = getDimension();
        double deln = Math.pow(hyperSphereVolume(n)/volume(), 1.0/n)*inradius();
        double erfcSdel = erfc( Math.sqrt(n*S/2.0) * deln );     
        return 0.5*kissingNumber()*erfcSdel;
    }

    /**
     * This is Conway and Sloane's approximation for high SNR
     * probability of error in lattice coding.  Returrns log
     * base 10 of the probability of error.
     */
    public double log10ProbCodingError(double S) {
        int n = getDimension();
        double nS2 = n*S/2.0;
        double del2n = Math.pow(hyperSphereVolume(n)/volume(), 2.0/n)*inradius()*inradius();
        double logdel = Math.log10(hyperSphereVolume(n)/volume()) + n*Math.log10(inradius());
        double t2 = Math.log10(kissingNumber()/(2.0*Math.sqrt(Math.PI)));
        double loge = Math.log10(Math.exp(1));
        return t2 - Math.log10(nS2)/2 - logdel/n - loge*nS2*del2n;
    }

    public double unshapedProbCodingError(double S){
        int n = getDimension();
        double nomgain = inradius()*inradius()*4.0/Math.pow(volume(),2.0/n);
        double Ks = 2.0*kissingNumber()/n;
        return Ks*Q(Math.sqrt(3*nomgain*S));
    }

    public double unshapedProbCodingErrorUseLogVolume(double S){
        int n = getDimension();
        double den = Math.pow(2.0, logVolume()*2.0/n);
        double nomgain = inradius()*inradius()*4.0/den;
        double Ks = 2.0*kissingNumber()/n;
        return Ks*Q(Math.sqrt(3*nomgain*S));
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
    
    /**
     * Returns the signal to noise ratio corresponding to a particular noise
     * variance. This uses a standard normalisation that makes it possible
     * to compare lattices of different dimension.
     */
    public double noiseVarianceToSNR(double v){
        int n = getDimension();
        double num = Math.pow(2, n+1)*Math.pow(n+1,(n+1.0)/(2.0*n));
        double den = (n+2)*Math.pow(hyperSphereVolume(n), 1.0/n)*v;
        return num/den;
    }

    /**
     * Returns the signal to noise ratio corresponding to a particular noise
     * variance in dB. This uses a standard normalisation that makes it possible
     * to compare lattices of different dimension.
     */
    public double noiseVarianceToSNRdB(double v){
        int n = getDimension();
        double num = Math.log10(2)*(n+1) + Math.log10(n+1)*(n+1.0)/(2.0*n);
        double den = Math.log10(n+2) + Math.log10(hyperSphereVolume(n))/n 
                            + Math.log10(v);
        return 10*( num - den );
    }
    
    @Override
    public String name() { 
        throw new UnsupportedOperationException("This lattice doesn't have a name!"); 
    }


    
}
