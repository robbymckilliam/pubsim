/*
 * ZnLLS.java
 *
 * Created on 12 August 2007, 12:32
 */

package simulator.fes;

import lattices.NearestPointAlgorithmInterface;
import lattices.Phin2StarZnLLS;

/**
 * Use the faster O(n^3 log(n)) Pn2 nearest point algorithm that
 * is a specialisation of the ZnLLS algorithm for the period
 * estimation problem.
 * 
 * @author Tim Mason and Robby McKilliam
 */
public class ZnLLS implements FrequencyEstimator{
    
    protected double[] ya;
    protected int n;
    protected NearestPointAlgorithmInterface lattice;
    
    // Used for phase estimation
    protected double f;
    protected double[] real;
    protected double[] imag;
    
    /** Set the number of samples */
    public void setSize(int n){
        lattice = new Phin2StarZnLLS();
        lattice.setDimension(n-2);
        ya = new double[n];
        real = new double[n];
        imag = new double[n];
        this.n = n;
    }
    
    /** Run the estimator on recieved data, @param y */
    public double estimateFreq(double[] real, double[] imag) {
        if (n != real.length) {
            setSize(real.length);
        }
        
        for(int i = 0; i < real.length; i++) {
            ya[i] = Math.atan2(imag[i],real[i])/(2*Math.PI);
        }
        
        lattice.nearestPoint(ya);
        
        // copied directly from GlueAnstarEstimator
        double f = 0, gtg = 0;
        double meann = (n-1)/2.0;
        double[] u = lattice.getIndex();
        for(int i = 0; i < n; i++){
            f += (i - meann)*(ya[i]-u[i]);
            gtg += (i - meann)*(i - meann);
        }
        f /= gtg;
        
        // Keep a copy in case phase is requested later
        for (int i = 0; i < n; i++) {
            this.real[i] = real[i];
            this.imag[i] = imag[i];
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
        
        for (int i = 0; i < n; i++) {
            if (this.real[i] != real[i] || this.imag[i] != imag[i]) {
                estimateFreq(real, imag);
                break;
            }
        }
        // By this point, we know that f is set to the frequency for this input
        
        for(int i = 0; i < real.length; i++) {
            ya[i] = Math.atan2(imag[i],real[i])/(2*Math.PI);
        }
        
        lattice.nearestPoint(ya);

        double phase = 0;
        double[] u = lattice.getIndex();
        for(int i = 0; i < n; i++){
            phase += (ya[i]-u[i]);
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
