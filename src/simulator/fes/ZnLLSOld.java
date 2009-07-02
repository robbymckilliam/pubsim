/*
 * ZnLLSOld.java
 *
 * Created on 12 August 2007, 12:32
 */

package simulator.fes;

import lattices.Phin2star.Phin2Star;
import lattices.Phin2star.Phin2StarZnLLSOld;

/**
 * Use the faster O(n^3 log(n)) Pn2 nearest point algorithm that
 * is a specialisation of the ZnLLSOld algorithm for the period
 * estimation problem.
 * 
 * @author Tim Mason and Robby McKilliam
 */
public class ZnLLSOld implements FrequencyEstimator{
    
    protected double[] ya;
    protected int n;
    protected Phin2Star lattice;
    
    // Used for phase estimation
    protected double f;
    protected double[] phase_samples;
    
    public ZnLLSOld() {
        lattice = new Phin2StarZnLLSOld();
    }
    
    /** Set the number of samples */
    public void setSize(int n){
        lattice.setDimension(n-2);
        ya = new double[n];
        phase_samples = new double[n];
        this.n = n;
    }
    
    public double getFrequency() {
        return f;
    }
    
    /**
     * Sets the minimum frequency to look for
     * @param f Minimum frequency as a fraction of fs/2 to search for.  Valid
     * range: -0.5 to 0.5
     */
    public void setMinFreq(double f) {
        ((Phin2StarZnLLSOld)lattice).setMinFreq(f);
    }
    
    /**
     * Sets the maximum frequency to look for
     * @param f Maximum frequency as a fraction of fs/2 to search for.  Valid
     * range: -0.5 to 0.5
     */
    public void setMaxFreq(double f) {
        ((Phin2StarZnLLSOld)lattice).setMaxFreq(f);
    }
    
    /** Run the estimator on recieved data, @param y */
    public double estimateFreq(double[] real, double[] imag) {
        if (n != real.length) {
            setSize(real.length);
        }
        
        for(int i = 0; i < real.length; i++) {
            ya[i] = Math.atan2(imag[i],real[i])/(2*Math.PI);
        }
        
        return estimateFreq(ya);
    }
    
    /** Run the estimator on phase data */
    public double estimateFreq(double[] phase_samples) {
        if (n != phase_samples.length) {
            setSize(phase_samples.length);
        }
        
        lattice.nearestPoint(phase_samples);
        
        // copied directly from GlueAnstarEstimator
        double f = 0, gtg = 0;
        double meann = (n-1)/2.0;
        double[] u = lattice.getIndex();
        for(int i = 0; i < n; i++){
            f += (i - meann)*(phase_samples[i]-u[i]);
            gtg += (i - meann)*(i - meann);
        }
        f /= gtg;
        
        // Keep a copy in case phase is requested later
        for (int i = 0; i < n; i++) {
            this.phase_samples[i] = phase_samples[i];
            this.f = f;
        }
        
        // f is normalised to fs, so it should be between -0.5 and 0.5
        while (f < -0.5) {
            f++;
        }
        while (f >= 0.5) {
            f--;
        }
        
        return f;
    }
    
    public double estimatePhase(double[] real, double[] imag) {
        if (n != real.length) {
            setSize(real.length);
        }
        
        for(int i = 0; i < real.length; i++) {
           ya[i] = Math.atan2(imag[i],real[i])/(2*Math.PI);
        }
        
        return estimatePhase(ya);
    }
    
    public double estimatePhase(double[] phase_samples) {
        for (int i = 0; i < n; i++) {
            if (this.phase_samples[i] != phase_samples[i] 
                  || phase_samples.length != this.phase_samples.length) {
                estimateFreq(phase_samples);
                break;
            }
        }
        // By this point, we know that f is set to the frequency for this input

        double phase = 0;
        double[] u = lattice.getIndex();
        for(int i = 0; i < n; i++){
            phase += (phase_samples[i]-u[i]);
        }
        for (int i = 0; i < n; i++) {
            phase -= f * i;
        }
        phase /= n;
        
        // initial phase is between -0.5*2*pi and 0.5*2*pi
        while (phase < 0.5) {
            phase++;
        }
        while (phase >= 0.5) {
            phase--;
        }
        
        return phase;
    }
    
}
