/*
 * FadingNoisyQAM.java
 *
 * Created on 13 September 2007, 18:08
 */

package pubsim.qam;

import pubsim.SignalGenerator;
import java.util.Random;
import pubsim.Complex;
import pubsim.distributions.NoiseGenerator;

/**
 * Creates a Rayleigh fading QAM symbol with Gaussian noise.
 * @author Robby McKilliam
 */
public class FadingNoisyQAM implements SignalGenerator<Double>{
    
    protected int T;
    protected int M;
    protected double Hr, Hi;
    protected double[] xr, xi;
    protected double[] yr, yi;
    protected NoiseGenerator<Double> noise;
    protected Random random;
    
    /** Default constructor using 8-ary QAM */
    public FadingNoisyQAM() {
        random = new Random();
        setLength(0);
        this.M = 8;
    }
    
    /** Parameter is the QAM size */
    public FadingNoisyQAM(int M) {
        random = new Random();
        setLength(0);
        this.M = M;
    }
    
    /** Set the number of QAM symbols transmitted, i.e. block length. */
    public void setLength(int n){
        xr = new double[n];
        xi = new double[n];
        yr = new double[n];
        yi = new double[n];
        T = n;
    }
    
    /** Set the number of QAM symbols transmitted, i.e. block length. */
    public int getLength() { return T; }
    
    /** Set the size of the QAM array */
    public void setQAMSize(int M){this.M = M;}
    
    /**
     * Generate a random QAM signal.  The QAM signal
     * has only odd integer components for both the
     * real and imaginary parts.  This is what Dan used
     * in his GLRT non-coherent QAM paper.  This resets
     * the length of the signal if required.
     */
    public void generateQAMSignal(int length){
        if(T != length) setLength(length);
        
        generateQAMSignal();
    }
    
    /**
     * Generate a random QAM signal of the currently
     * specified length.
     */
    public void generateQAMSignal(){
        for(int i=0; i < T; i++){
            xr[i] = 2*random.nextInt(M) - M + 1;
            xi[i] = 2*random.nextInt(M) - M + 1;
        }
    }
    
    public void setTransmittedSignal(double[] xr, double[] xi){
        if(this.xr.length != xr.length) setLength(xr.length);
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
     * Generate the received QAM signal.  The signal has
     * Rayleigh fading and noise.  Returns null, use
     * getInphase and getQuadrature to get the signal
     */
    public Double[] generateReceivedSignal(){
        for(int i=0; i < yr.length; i++){
            yr[i] = xr[i]*Hr - xi[i]*Hi + noise.getNoise();
            yi[i] = xr[i]*Hi + xi[i]*Hr + noise.getNoise();
        }
        return null;
    }
    
    /** Return the inphase (real) part of the received QAM signal */
    public double[] getInphase() { return yr; }
    public double[] getReal() { return yr; }
    
    /** Return the quadrature (imaginary) part of the QAM signal */
    public double[] getQuadrature() { return yi; }
    public double[] getImag() { return yi; }
    
    /** Set the noise distribution */
    public void setNoiseGenerator(NoiseGenerator noise){
        this.noise = noise;
    }
    public NoiseGenerator getNoiseGenerator(){ return noise; }
    
    /**
     * Set the seed for the random generator used
     * to create the channel noise and generate
     * the QAM signal.  Potentially this should also
     * set the seed for the Gaussian noise?
     */
    public void setSeed(long seed){
        random.setSeed(seed);
    }
    
    /** Randomise the seed */ 
    public void randomSeed(){ random = new Random(); }
    
    /** Generate the Rayleigh fading channel */
    public void generateChannel(){
        Hr = random.nextGaussian();
        Hi = random.nextGaussian();
    }
    
    /**
     * Set the real and imaginary parts of the
     * Rayleigh fading channel.
     */
    public void setChannel(double Hr, double Hi){
        this.Hr = Hr;
        this.Hi = Hi;
    }

    /** Get the complex channel coefficient */
    public Complex getChannel(){
        return new Complex(Hr, Hi);
    }
    
    /** 
     * Return the number of symbol errors between 
     * two QAM blocks x and y. 
     * PRE: xr.length == xi.length == yr.length == yi.length
     */
    public static double symbolErrorRate(double[] xr, double[] xi,
                                    double[] yr, double[] yi){
        double ers = 0;
        for(int i = 0; i < xr.length; i++)
            if( Math.round(xr[i] - yr[i]) != 0.0 
                || Math.round(xi[i] - yi[i]) != 0.0 ) ers++;
        
        return ers/xr.length;
    }
    
    /** 
     * Returns the number of bit errors between 
     * M^2-ary QAM symbols x and y.  
     * Assumes gray coding.
     * UNDER CONSTRUCTION
     */
    public static int bitErrors(double xr, double xi, 
            double yr, double yi, int M){
        
        int er = (int)(Math.abs(xr - yr)/2)%(M/2+1);
        int ei = (int)(Math.abs(xi - yi)/2)%(M/2+1);
        
        //System.out.println("xr = " + xr + ", xi = " + xi + ", yr = " + yr + ", yi = " + yi);
        //System.out.println("er = " + er + ", ei = " + ei);
        
        return er + ei;
    }
    
        /** 
     * Returns the number of bit errors between 
     * M^2-ary QAM symbols x and y.  
     * Assumes gray coding.
     * UNDER CONSTRUCTION
     */
    public static int bitErrors(double xr[], double xi[], 
            double yr[], double yi[], int M){
        
        int errors = 0;
        for(int i = 0; i < xr.length; i++)
            errors += bitErrors(xr[i], xi[i], yr[i], yi[i], M);
        
        return errors;
    }
    
}
