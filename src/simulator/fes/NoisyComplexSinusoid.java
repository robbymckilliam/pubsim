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
 * @author Robby McKilliam
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
    @Override
    public void setNoiseGenerator(NoiseGenerator noise){
        this.noise = noise;
    }
    
    public NoiseGenerator getNoiseGenerator(){ return noise; }
    
    /** Set the number of samples */
    public void setSize(int n){
        this.n = n;
        real = new double[n];
        imag = new double[n];
        phase = new double[n];
    }
    
    /** default constructor: f = 0.1, rate = 1, n = 10, p = 0.0 */
    public NoisyComplexSinusoid() {
        f = 0.1;
        rate = 1.0;
        n = 10;
        p = 0.0;
        setSize(n);
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
    public double getFrequency(){ return f; }
    
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
    
    /** Generates the real and imaginary components and returns null.
     * Use getReal and getImag to get the individual components.
     */
    @Override
    public double[] generateReceivedSignal(){
        for(int i = 0; i < n; i++){
            real[i] = Math.cos(i*2*Math.PI*f/rate + p) + noise.getNoise();
            imag[i] = Math.sin(i*2*Math.PI*f/rate + p) + noise.getNoise();
        }
        return null;
    }
    
    /** Return the noisy real component of the signal */
    public double[] getReal() { return real; }
    
    /** Return the noisy imaginary component of the signal */
    public double[] getImag() { return imag; }

    @Override
    public void setLength(int n) {
        setSize(n);
    }

    @Override
    public int getLength() {
        return n;
    }
    
}
