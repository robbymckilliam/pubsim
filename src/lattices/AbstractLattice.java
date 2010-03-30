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
import static simulator.Util.erfc;
import static simulator.Util.Q;
import static simulator.Util.log2HyperSphereVolume;
import static simulator.Util.hyperSphereVolume;

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
        return getDimension()*log2(inradius()) - logVolume();
    }

    public double logVolume(){
        return log2(volume());
    }

    public double logPackingDensity() {
        return logCenterDensity() + log2HyperSphereVolume(getDimension());
    }

    public double packingDensity() {
        return pow2(logPackingDensity());
    }
    

    /** 
     * This is Conway and Sloane's approximation for high SNR
     * probability of error in lattice coding.
     */
    public double probCodingError(double S) {
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
//        System.out.println("nS2 = " + nS2);
//        System.out.println("del2n = " + del2n);
//        System.out.println("logdel = " + logdel);
//        System.out.println("t2 = " + t2);
//        System.out.println("loge = " + loge);
//        System.out.println("Math.log10(nS2)/2 = " + Math.log10(nS2)/2);
        return t2 - Math.log10(nS2)/2 - logdel/n - loge*nS2*del2n;
    }

    public double unshapedProbCodingError(double S){
        int n = getDimension();
        double nomgain = inradius()*inradius()*4.0/Math.pow(volume(),2.0/n);
        double Ks = 2.0*kissingNumber()/n;
        return Ks*Q(Math.sqrt(3*nomgain*S));
    }

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
    public double inradius(){
        if(inradius == 0){
            ShortestVector sv = new ShortestVector(this);
            double norm = VectorFunctions.sum2(sv.getShortestVector());
            inradius = Math.sqrt(norm)/2.0;
        }
        return inradius;
    }

    /**
     * By default this brute forces the kissing number by sphere decoding.
     * Lattices with known kissing numbers can override this.
     */
    public long kissingNumber() {
        if(kissingnumber == 0){
            lattices.decoder.KissingNumber k = new KissingNumber(this);
            kissingnumber = k.kissingNumber();
        }
        return kissingnumber;
    }

}
