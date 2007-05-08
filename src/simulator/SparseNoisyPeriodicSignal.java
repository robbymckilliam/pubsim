/*
 * SparseNoisyPeriodicSignal.java
 *
 * Created on 13 April 2007, 14:55
 */

package simulator;

import java.util.Random;

/**
 * Generates a set of recieved times that are sparse and
 * noisy versions of the recieved signal.
 * <p>
 * The specific sparse pulses that will be transmitted can
 * be specefied by @func setTransmittedSignal.
 * @author Robby
 */
public class SparseNoisyPeriodicSignal implements SignalGenerator {
    
    private double[] transmittedSignal;
    private NoiseGenerator noise;
    private double T;
    
    public void setTransmittedSignal(double[] transmitted){
        transmittedSignal = transmitted;
    }
    
    public void setPeriod(double T){  this.T = T; }
    public double getPeriod(){ return T; }
    
    /**
     * Generate a binomial sequence typical of a transmitted
     * sparse signal.
     */
    public double[] generateTransmittedSignal(int length){
        Random rand = new Random();
        double[] trans = new double[length];
        double count = 0.0;
        int added = 0;
        while(added < length){
            if(rand.nextBoolean()){
                trans[added] = count;
                added++;
            }
            count++;
        }
        return trans;
    }
    
     /**
     * Generate a binomial sequence typical of a transmitted
     * sparse signal.  Seed the random generator so that well always get
     * the same answer.
     */
    public double[] generateTransmittedSignal(int length, long seed){
        Random rand = new Random(seed);
        double[] trans = new double[length];
        double count = 0.0;
        int added = 0;
        while(added < length){
            if(rand.nextBoolean()){
                trans[added] = count;
                added++;
            }
            count++;
        }
        return trans;
    }
    
    /**
     * Generate a binomial sequence typical of a transmitted
     * sparse signal.
     */
    public double[] generateReceivedSignal(){
          if(transmittedSignal == null)
              throw new Error("No transmitted signal has been specified");
          if(noise == null)
              throw new Error("No noise generator has been specified");
          
          double[] gensig = new double[transmittedSignal.length];
          for(int i = 0; i< transmittedSignal.length; i++){
              gensig[i] = T * transmittedSignal[i] + noise.getNoise();
          }
          
          return gensig;
    }
    
    public void setNoise(NoiseGenerator noise){
        this.noise = noise;
    }
    
}
