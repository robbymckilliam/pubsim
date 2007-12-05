/*
 * QPSKSignal.java
 *
 * Created on 5 December 2007, 09:56
 */

package simulator.qpsk;

import java.util.Random;
import simulator.NoiseGenerator;
import simulator.SignalGenerator;

/**
 * Generates a baseband sampled QPSK signal.
 * @author Robby McKilliam
 */
public class QPSKSignal implements SignalGenerator{
    
    protected NoiseGenerator noise;
    protected Random random;
    protected double symF;
    protected double transF;
    protected double sampF;
    protected double phase;
    
    protected int n;
    protected int M;
    
    //transmitted signals
    protected double[] trans;
    
    //recieved signals
    protected int numSymbols;
    protected double[] recReal;
    protected double[] recImag;
    
    public QPSKSignal(){
        n = 10;
        random = new Random();
        phase = 0;
    }
    
    /**
     * Generates the received QPSK signal.  The baseband 
     * transmission frequency is transF and the phase
     * offsets are in trans.
     * <p>
     * Use getReal() and getImag() to get the real and 
     * imaginary part of the signal.
     */
    public double[] generateReceivedSignal(){
        for(int i = 0; i < n; i++){
            double phase = 
                2*Math.PI*(0.5 + trans[(int)Math.floor(i*symF/sampF)])/M;
            double t = 2*Math.PI*transF*i/sampF + phase + this.phase;
            recReal[i] = Math.cos(t) + noise.getNoise();
            recImag[i] = Math.sin(t) + noise.getNoise();
        }
        return null;
    }
    
    /** 
     * Generates a random M-ary QPSK signal.  The transmitted
     * signals contain the values {0, .., M-1}.  These
     * correspond to phase offsets.
     * <p> 
     * Call this before calling generate recieved signal.
     */
    public void generateTransmittedQPSKSignal(){
        for(int i = 0; i < numSymbols; i++)
            trans[i] = random.nextInt(M);
    }
    
    public void setNoiseGenerator(NoiseGenerator noise){
        this.noise = noise;
    }
    public NoiseGenerator getNoiseGenerator(){ return noise; }
    
    /** Set the number of samples that are used per 'block' */
    public void setLength(int n) {
        this.n = n;
        setTransmittedSignalLength();
        recReal = new double[n];
        recImag = new double[n];               
    }
    
    protected void setTransmittedSignalLength(){
        numSymbols = (int) Math.floor(n*symF/sampF) + 1;
        trans = new double[numSymbols];
    }
    
    /** Return the length of the signal generated */
    public int getLength() {return n;} 
    
    /** 
     * Set the symbol timing. 
     * This is the period of time, in seconds, 
     * that each QPSK symbol is transmitted.
     */
    public void setSymbolRate(double F) { 
        symF = F;
        setTransmittedSignalLength();
    }
    
    /** Set the phase of the carrier wave */
    public void setCarrierPhase(double p) { phase = p; }
    
    /**
     * Set the transmission frequency for the
     * QPSK symbols.
     */
    public void setCarrierFrequency(double f){ transF = f; }
    
    /** 
     * Set the period of the samples that are made
     * of the QPSK signal.  This is the
     * sample rate of the ADC used in practice.
     */ 
    public void setSampleRate(double f){
        sampF = f;
        setTransmittedSignalLength();
    }
    
    /** 
     * Set the number of QPSK symbols that are in 
     * the constellation.  ie M-ary QPSK.
     */
    public void setM(int M){
        this.M = M;
    }
    
    /** 
     * Return the real transmitted QPSK signal. 
     * This contains the numbers {0,...M-1} representing
     * each of the QPSK signals.
     */
    public double[] getTransmittedQPSKSignal(){ return trans; }
    
    /** Return the inphase (real) part of the recived QPSK signal */
    public double[] getReal() { return recReal; }
    
    /** Return the quadrature (imaginary) part of the QPSK signal */
    public double[] getImag() { return recImag; }
    
        
    /**
     * Set the seed for the random generator used
     * to generate the transmitted QPSK signal.  
     */
    public void setSeed(long seed){
        random.setSeed(seed);
    }
    
    /** Randomise the seed */ 
    public void randomSeed(){ random = new Random(); }
    
}
