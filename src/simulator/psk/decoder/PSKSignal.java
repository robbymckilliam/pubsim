/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.psk.decoder;

import java.util.Random;
import simulator.Complex;
import distributions.NoiseGenerator;
import simulator.SignalGenerator;

/**
 * Generates T PSK signals that have been passed through a Rayleigh
 * fading channel with added iid Gaussian noise.
 * @author robertm
 */
public class PSKSignal implements SignalGenerator{
    
    /** Block length */
    protected int T;
    
    /** M for M-PSK */
    protected int M;
    
    /** The channel coefficient */
    protected Complex h;
    
    /** The transmitted data.  Integers in range {0, 1, ..., M-1} */
    protected double[] x;
    
    /** Received complex signal */
    protected Complex[] y;
    
    protected NoiseGenerator noise;
    protected Random random;
    
    /** Default constructor using 8-ary QAM */
    public PSKSignal() {
        random = new Random();
        h = new Complex();
        setLength(0);
        this.M = 8;
    }
    
    /** Parameter is the QAM size */
    public PSKSignal(int M) {
        random = new Random();
        h = new Complex();
        setLength(0);
        this.M = M;
    }
         
    /**
     * Set the seed for the random generator used
     * to generate the transmitted QPSK signal.  
     */
    public void setSeed(long seed){
        random.setSeed(seed);
    }
    
    /** Randomise the seed */ 
    public void randomSeed(){ random = new Random(); }
    
    /** Set M for M-PSK. */
    public void setM(int M){ this.M = M; }

    public double[] generateReceivedSignal() {
        //Complex xc = new Complex();
        //Complex nos = new Complex();
        for(int i=0; i < y.length; i++){
            Complex xc = new Complex(Math.cos(2*Math.PI*x[i]/M), 
                                     Math.sin(2*Math.PI*x[i]/M));
            Complex nos = new Complex(noise.getNoise(), noise.getNoise());
            //y[i].copy(h).timesP(xc).plusP(nos);
            //System.out.println(xc + ", " + nos);
            y[i] = h.times(xc).plus(nos);
        }
        return null;
    }
    
    public Complex[] getReceivedSignal(){
        return y;
    }
    
    /**
     * Generate a random PSK signal of specifed length.  
     * This sets the length of the signal if required.
     */
    public void generatePSKSignal(int length){
        if(T != length) setLength(length); 
        generatePSKSignal();
    }
    
    /**
     * Generate a random PSK signal of the currently
     * specified length.
     */
    public void generatePSKSignal(){
        for(int i=0; i < T; i++) x[i] = random.nextInt(M);
    }
    
    /** Set the transmitted PSK signal */
    public void setPSKSignal(double[] x) {
        if( T != x.length) setLength(x.length);
        System.arraycopy(x, 0, this.x, 0, T); 
    }
    
    /** Return the transmitted PSK signal */
    public double[] getPSKSignal() { return x; }

    /** Set the noise distribution */
    public void setNoiseGenerator(NoiseGenerator noise){
        this.noise = noise;
    }
    public NoiseGenerator getNoiseGenerator(){ return noise; }

    public void setLength(int n) {
        y = new Complex[n];
        //for(int i = 0; i < n; i++)
        //    y[i] = new Complex();
        x = new double[n];
        T = n;
        
    }

    public int getLength() { return T; }
    
    
    /** Generate the phase unknow, power constant, fading channel */
    public void generateChannel(){
        h = new Complex(random.nextGaussian(), random.nextGaussian());
        h = h.times(1/h.abs());
    }

    /** Generate the Rayleighyl fading channel */
    public void generateRayeighlyChannel(){
        h = new Complex(random.nextGaussian(), random.nextGaussian());
    }
    
    /**
     * Set the real and imaginary parts of the
     * Rayleigh fading channel.
     */
    public void setChannel(double Hr, double Hi){
        h = new Complex(Hr, Hi);
    }
    
    /**
     * Set the real and imaginary parts of the
     * Rayleigh fading channel.
     */
    public void setChannel(Complex h){
        this.h = h;
    }
    
    /**
     * Get the current channel being used.
     */
    public Complex getChannel(){
        return h;
    }

}
