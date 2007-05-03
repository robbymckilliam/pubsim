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
    
    public void setTransmittedSignal(double[] transmitted){
        transmittedSignal = transmitted;
    }
    
    /**
     * Generate a binomial sequence typical of a transmitted
     * sparse signal.
     */
    public double[] generateTransmittedSignal(int length){
        Random rand = new Random();
        double[] trans = new double[length];
        double count = 0.0;
        for(int i = 0; i < length; i++){
            if(rand.nextBoolean())
                trans[i] = count;
            count++;
        }
        return trans;
    }
    
    public double[] generateReceivedSignal(){
          if(transmittedSignal == null)
              throw new Error("No transmitted signal has been specified");
          if(noise == null)
              throw new Error("No noise generator has been specified");
          
          double[] gensig = new double[transmittedSignal.length];
          for(int i = 0; i< transmittedSignal.length; i++){
              gensig[i] = transmittedSignal[i] + noise.getNoise();
          }
          
          return gensig;
    }
    
    public void setNoise(NoiseGenerator noise){
        this.noise = noise;
    }
    
}
