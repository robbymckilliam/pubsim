/*
 * SparseNoisyPeriodicSignal.java
 *
 * Created on 13 April 2007, 14:55
 */

package simulator.pes;

import java.util.Random;
import simulator.*;

/**
 * Generates a set of recieved times that are sparse and
 * noisy versions of the recieved signal.
 * <p>
 * The specific sparse pulses that will be transmitted can
 * be specefied by @func setSparseSignal.
 * 
 * @author Robby
 */
public class SparseNoisyPeriodicSignal implements SignalGenerator {
    
    private double[] transmittedSignal;
    private double[] recievedSignal;
    private NoiseGenerator noise;
    private Random rand;
    private double T;
    
    public SparseNoisyPeriodicSignal(){
            rand = new Random();
            transmittedSignal = new double[0];
            recievedSignal = new double[0];
    }
    
    public void setSparseSignal(double[] transmitted){
        transmittedSignal = transmitted;
    }
    
    public void setPeriod(double T){  this.T = T; }
    public double getPeriod(){ return T; }
    
    /**
     * Generate a binomial sequence typical of a transmitted
     * sparse signal.
     */
    public double[] generateSparseSignal(int length){
        if( transmittedSignal.length != length )
            transmittedSignal = new double[length];
            
        double count = 0.0;
        int added = 0;
        while(added < length){
            if(rand.nextBoolean()){
                transmittedSignal[added] = count;
                added++;
            }
            count++;
        }  
        return transmittedSignal;
    }
    
     /**
     * Generate a binomial sequence typical of a transmitted
     * sparse signal.  Seed the random generator so that well always get
     * the same answer.
     */
    public double[] generateSparseSignal(int length, long seed){
        rand.setSeed(seed);
        
        if( transmittedSignal.length != length )
            transmittedSignal = new double[length];
            
        double count = 0.0;
        int added = 0;
        while(added < length){
            if(rand.nextBoolean()){
                transmittedSignal[added] = count;
                added++;
            }
            count++;
        }
        return transmittedSignal;
    }
    
    /**
     * Generate a binomial sequence typical of a transmitted
     * sparse signal.
     */
    public double[] generateReceivedSignal(){

          if( transmittedSignal.length != recievedSignal.length )
                recievedSignal = new double[transmittedSignal.length];
          
          for(int i = 0; i< transmittedSignal.length; i++){
              recievedSignal[i] = T * transmittedSignal[i] + noise.getNoise();
          }
          
          return recievedSignal;
    }
    
    /** Set the noise type for the signal */
    public void setNoiseGenerator(NoiseGenerator noise){
        this.noise = noise;
    }
    public NoiseGenerator getNoiseGenerator(){ return noise; }
    
}
