/*
 * NoisyComplexSinusoid.java
 *
 * Created on 9 August 2007, 12:00
 */

package simulator.fes;

import simulator.SignalGenerator;
import simulator.GaussianNoise;
import simulator.NoiseGenerator;
import java.util.Random;

/**
 * Generator for a noisy sinusoid.
 * <p>
 * Notes: Probably need to refactor SignalGenerator.
 * <p>
 * @author Robby
 */
public class NoisyComplexSinusoid implements SignalGenerator{
    
    protected double f;
    protected double rate;
    protected double p;
    protected int n;
    
    protected double[] real;
    protected double[] imag;
    protected double[] phase;
    
    protected NoiseGenerator noise;
    
    /** Set the noise type for the signal */
    public void setNoise(NoiseGenerator noise){
        this.noise = noise;
    }
    
    /** Set the number of samples */
    public void setSize(int n){
        this.n = n;
        real = new double[n];
        imag = new double[n];
        phase = new double[n];
    }
    
    /** default constructor: f = 1, rate = 10*f, n = 10 */
    public NoisyComplexSinusoid() {
        f = 1.0;
        rate = 10*f;
        n = 10;
    }
    
    /** construct and set variables */
    public NoisyComplexSinusoid(double f, double rate, double p, int n) {
        this.f = f;
        this.rate = rate;
        this.p = p;
        setSize(n);
    }
    
    /** Set the freqency of the csiod in Hz */
    public void setFrequency(double f){
        this.f = f;
    }
    
   /** Set the phase of the sinusoid */
    public void setPhase(double p){
        this.p = p;
    }
    
    /** Set the rate at which this sinusiod is sampled in Hz
     * Should set the rate at or above the nyquist rate ie,
     * 2f.  However, this is not strictly required by the code.
     */
    public void setSampleRate(double rate){
        this.rate = rate;
    }
    
    /** Set the sample rate to k times the frequency f */
    public void setSampleRateMultiple(double k){
        this.rate = k * f;
    }
    
    /** Generates the real and imaginary components and returns phase.
     * Use getReal and getImag to get the individual components.
     */
    public double[] generateReceivedSignal(){
        for(int i = 0; i < n; i++){
            real[i] = Math.cos(i*2*Math.PI*f/rate + p) + noise.getNoise();
            imag[i] = Math.sin(i*2*Math.PI*f/rate + p) + noise.getNoise();
            phase[i] = Math.atan(imag[i]/real[i]);
        }
        return phase;
    }
    
    /** Return the noisy phase signal */
    public double[] getPhase() { return phase; }
    
    /** Return the noisy real component of the signal */
    public double[] getReal() { return real; }
    
    /** Return the noisy imaginary component of the signal */
    public double[] getImag() { return imag; }
    
}
