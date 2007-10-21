/*
 * FadingNoisyQAM.java
 *
 * Created on 13 September 2007, 18:08
 */

package simulator.qam;

import simulator.SignalGenerator;
import simulator.GaussianNoise;
import simulator.NoiseGenerator;
import java.util.Random;

/**
 * Creates a Rayleigh fading QAM symbol with gaussian noise.
 * @author Robby
 */
public class FadingNoisyQAM implements SignalGenerator{
    
    protected int T;
    protected int M;
    protected double Hr, Hi;
    protected double[] xr, xi;
    protected double[] yr, yi;
    protected NoiseGenerator noise;
    Random rand;
    
    /** Default constructor using 8-ary QAM */
    public FadingNoisyQAM() {
        rand = new Random();
        setSize(0);
        this.M = 8;
    }
    
    /** Parameter is the QAM size */
    public FadingNoisyQAM(int M) {
        rand = new Random();
        setSize(0);
        this.M = M;
    }
    
    public void setSize(int n){
        xr = new double[n];
        xi = new double[n];
        yr = new double[n];
        yi = new double[n];
        T = n;
    }
    
    /** Set the size of the QAM array */
    public void setM(int M){this.M = M;}
    
    /** 
     * Generate a random QAM signal.  The QAM signal
     * has only odd integer components for both the
     * real and imaginary parts.  This is what Dan used
     * in his GLRT non-coherent QAM paper.  This resets
     * the length of the signal if required.
     */
    public void generateQAMSignal(int length){
        if(T != length) setSize(length);
        
        generateQAMSignal();
    }
    
    /** 
     * Generate a random QAM signal of the currently
     * specified length.
     */
    public void generateQAMSignal(){
        for(int i=0; i < T; i++){
            xr[i] = 2*rand.nextInt(M) - M + 1;
            xi[i] = 2*rand.nextInt(M) - M + 1;
        }
    }
    
    public void setTransmittedSignal(double[] xr, double[] xi){
        if(this.xr.length != xr.length) setSize(xr.length);
        for(int i = 0; i < xr.length; i++){
            this.xr[i] = xr[i];
            this.xi[i] = xi[i];
        }
    }
    
    /** Return the real transmitted signal */
    public double[] getTransmittedRealQAMSignal(){ 
        return xr; 
    }
    
    /** Return the imaginary transmitted signal */
    public double[] getTransmittedImagQAMSignal(){ 
        return xi; 
    }
    
    /** 
     * Generate the recived QAM signal.  The signal has 
     * Rayleigh fading and noise.  Returns null, use
     * getInphase and getQuadrature to get the signal
     */
    public double[] generateReceivedSignal(){
        if( yr.length != xr.length ) setSize(xr.length);
        
        for(int i=0; i < yr.length; i++){
            yr[i] = xr[i]*Hr - xi[i]*Hi + noise.getNoise();
            yi[i] = xr[i]*Hi + xi[i]*Hr + noise.getNoise();
        }
        return null;
    }
    
    /** Return the inphase (real) part of the recived QAM signal */
    public double[] getInphase() { return yr; }
    public double[] getReal() { return yr; }
    
    /** Return the quadrature (imaginary) part of the QAM signal */
    public double[] getQuadrature() { return yi; }
    public double[] getImag() { return yi; }
    
    /** Set the noise distribution */
    public void setNoise(NoiseGenerator noise){
        this.noise = noise;
    }
    
    /** 
     * Set the seed for the random generator used
     * to create the channel noise and generate
     * the QAM signal.  Potentially this should also
     * set the seed for the Gaussian noise?
     */ 
    public void setSeed(long seed){
        rand.setSeed(seed);
    }
    
    /** Generate the Rayleigh fading channel */
    public void generateChannel(){
        Hr = rand.nextGaussian();
        Hi = rand.nextGaussian();
    }
    
    /** 
     * Set the real and imaginary parts of the
     * Rayleigh fading channel.
     */
    public void setChannel(double Hr, double Hi){
        this.Hr = Hr;
        this.Hi = Hi;
    }
    
}
