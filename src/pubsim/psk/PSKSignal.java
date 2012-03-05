/*
 * PSKSignal.java
 *
 * Created on 5 December 2007, 09:56
 */

package pubsim.psk;

import java.util.LinkedList;
import java.util.Random;
import pubsim.distributions.ContinuousRandomVariable;
import pubsim.SignalGenerator;
import pubsim.distributions.NoiseGenerator;

/**
 * Generates a baseband sampled QPSK signal.
 * @author Robby McKilliam
 */
public class PSKSignal implements SignalGenerator<Double>{
    
    protected NoiseGenerator<Double> noise;
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
    
    public PSKSignal(){
        n = 10;
        // By default, expect the transmission frequency and symbol rate to
        // be specified as a fraction of the sampling frequency.
        sampF = 1;
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
    @Override
    public Double[] generateReceivedSignal(){
        for(int i = 0; i < n; i++){
            // phase offset associated with each of the M symbols
            double pha = 
                2*Math.PI*(0.5 + trans[(int)Math.floor(i*symF/sampF)])/M;
            // phase at the transmitter's end (no noise yet) at each of the
            // receiver's samples
            double t = 2*Math.PI*transF/sampF*i + pha + this.phase;
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
        numSymbols = (int) Math.ceil(n*symF/sampF);
        trans = new double[numSymbols];
    }
    
    /** Return the length of the signal generated */
    public int getLength() {return n;} 
    
    /** 
     * Set the symbol timing. 
     * This is the frequency at which the symbol changes.
     */
    public void setSymbolRate(double F) { 
        symF = F;
        setTransmittedSignalLength();
    }
    
    /**
     * Set the sampling timing at the receiver end.
     * This is the sampling frequency used by the receiver.
     */
    public void setSampleRate(double f) { 
        sampF = f;
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
    
    /** 
     * Generates, stores and compares lists of QPSK symbols.
     * Stores Differnetially encoded symbols.  ie. the 
     * difference between consecutive symbols rather than
     * the symbols themselves.
     * <p>
     * Comparisons between two lists return the symbol error
     * rate for the differentially encoded symbols.
     */
    public static class SymbolGenerator {
        private LinkedList<Integer> slist;
        private int M;
        private Random random;
        
        int lastsym;
        
        public SymbolGenerator(){
            slist = new LinkedList<Integer>();
            setM(4);
        }
        
        public void setM(int M){
            this.M = M;
            lastsym = M+1;
        }
        
        public void addSymbol(int sym){
            slist.add(new Integer(sym));
        }
        
        public LinkedList<Integer> getList(){ return slist; }
        
        /** 
         * Generate and return another symbol.
         * Also add the symbol to the list.
         */
        public int generateNextSymbol(){
            int sym =  random.nextInt(M);
            //this is not good practice.  M+1 is used
            //so that I know this is the first symbol generated
            if(lastsym != M+1)
                addSymbol(sym - lastsym);
            lastsym = sym;
            return sym;
        }
        
        /** 
         * Calcualat the number of symbol errors between two lists
         * of symbols.
         */
        public long errors(SymbolGenerator s1){
            LinkedList<Integer> slist1 = s1.getList();
            long errs = 0;
            while(!slist1.isEmpty() && !slist.isEmpty()){
                if(slist1.remove().intValue() != slist.remove().intValue())
                    errs++;
            }
            return errs;
        }
            
    }
    
}
