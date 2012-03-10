/*
 * SparseNoisyPeriodicSignal.java
 *
 * Created on 13 April 2007, 14:55
 */

package pubsim.snpe;

import pubsim.SignalGenerator;
import pubsim.distributions.NoiseGenerator;

/**
 * Generates a set of recieved times that are sparse and
 * noisy versions of the received signal.
 * <p>
 * The specific sparse pulses that will be transmitted can
 * be specified by @func setSparseSignal.
 * 
 * @author Robby McKilliam
 */
public class SparseNoisyPeriodicSignal implements SignalGenerator<Double> {
    
    protected Integer[] sparseSignal;
    protected Double[] recievedSignal;
    protected NoiseGenerator<Double> noise;
    protected NoiseGenerator<Integer> sparsenoise;
    protected double T = 1.0;
    protected int N;
    protected double phase = 0.0;
    
    public SparseNoisyPeriodicSignal(int N, NoiseGenerator<Integer> sparsenoise, NoiseGenerator<Double> noise){
        this.N = N;
        sparseSignal = new Integer[N];
        recievedSignal = new Double[N];
        this.noise = noise;
        this.sparsenoise = sparsenoise;
        generateSparseSignal();   
    }
    
    public void setSparseSignal(Integer[] S){
        sparseSignal = S;
    }
    
    public void setPeriod(double T){  this.T = T; }
    public double getPeriod(){ return T; }
    public void setPhase(double p){  phase = p; }
    public double getPhase(){ return phase; }
    
    
    /** {@inheritDoc} */
    @Override
    public int getLength() {return N; }
    
    public Integer[] generateSparseSignal(){
        int sum = 0;
        for(int i = 0; i < N; i++){
            sum += sparsenoise.getNoise();
            sparseSignal[i] = sum;
        }
        return sparseSignal;
    }

    /**
     * Generate sparse noisy signal
     */
    @Override
    public Double[] generateReceivedSignal() {
          
          for(int i = 0; i< sparseSignal.length; i++){
              recievedSignal[i] = T * sparseSignal[i]
                                    + noise.getNoise() + phase;
          }
          return recievedSignal;
    }

    /** Set the discrete noise type for sparse signal */
    public void setSparseGenerator(NoiseGenerator<Integer> sparsenoise){
        this.sparsenoise = sparsenoise;
        generateSparseSignal();
    }
    public NoiseGenerator<Integer> getSparseGenerator(){ return sparsenoise; }

    /** Set the noise type for the signal */
    @Override
    public void setNoiseGenerator(NoiseGenerator<Double> noise){
        this.noise = noise;
    }
    @Override
    public NoiseGenerator<Double> getNoiseGenerator(){ return noise; }
    
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
