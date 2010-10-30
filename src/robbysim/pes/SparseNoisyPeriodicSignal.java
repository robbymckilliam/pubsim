/*
 * SparseNoisyPeriodicSignal.java
 *
 * Created on 13 April 2007, 14:55
 */

package robbysim.pes;

import robbysim.distributions.RandomVariable;
import java.util.Random;
import robbysim.*;

/**
 * Generates a set of recieved times that are sparse and
 * noisy versions of the recieved signal.
 * <p>
 * The specific sparse pulses that will be transmitted can
 * be specefied by @func setSparseSignal.
 * 
 * @author Robby McKilliam
 */
public class SparseNoisyPeriodicSignal implements SignalGenerator {
    
    protected double[] transmittedSignal;
    protected double[] recievedSignal;
    protected RandomVariable noise;
    protected RandomVariable sparsenoise;
    protected double T = 1.0;
    protected int n;
    protected double phase = 0.0;
    /** geometic variable for sparse signal */
    protected Random random;
    
    
    public SparseNoisyPeriodicSignal(){
            random = new Random();
            transmittedSignal = new double[0];
            recievedSignal = new double[0];
    }
    
    public void setSparseSignal(double[] transmitted){
        transmittedSignal = transmitted;
    }
    
    public void setPeriod(double T){  this.T = T; }
    public double getPeriod(){ return T; }
    public void setPhase(double p){  phase = p; }
    public double getPhase(){ return phase; }
    
    /** {@inheritDoc} */
    public void setLength(int n){
        this.n = n;
        transmittedSignal = new double[n];
        recievedSignal = new double[n];
    }
    
    /** {@inheritDoc} */
    public int getLength() {return n; }
    
    public double[] generateSparseSignal(){
        int sum = 0;
        for(int i = 0; i < n; i++){
            sum += sparsenoise.getNoise();
            transmittedSignal[i] = sum;
        }
        return transmittedSignal;
    }
    
    /**
     * Generate a binomial sequence typical of a transmitted
     * sparse signal.
     */
    public double[] generateSparseSignal(int length){
        if( n != length ) setLength(length);
        return generateSparseSignal();
    }
    
    /**
     * Generate sparse noisy signal
     */
    public double[] generateReceivedSignal() {
          if(transmittedSignal == null )
              throw new java.lang.NullPointerException
                      ("transmitted signal has not been allocated\n" +
                      "call generateSparseSignal(length) first ");
          
          for(int i = 0; i< transmittedSignal.length; i++){
              recievedSignal[i] = T * transmittedSignal[i]
                                    + noise.getNoise() + phase;
          }
          
          return recievedSignal;
    }

    /** Set the noise type for the signal */
    public void setSparseGenerator(RandomVariable sparsenoise){
        this.sparsenoise = sparsenoise;
    }
    public RandomVariable getSparseGenerator(){ return sparsenoise; }

    /** Set the noise type for the signal */
    public void setNoiseGenerator(RandomVariable noise){
        this.noise = noise;
    }
    public RandomVariable getNoiseGenerator(){ return noise; }
    
    /**
     * Set the seed for the random generator used
     * to create the sparse signal
     */
    public void setSeed(long seed){
        sparsenoise.setSeed(seed);
    }
    
    /** Randomise the seed for the sparse signal */ 
    public void randomSeed(){
        sparsenoise.randomSeed();
        noise.randomSeed();
    }
}
