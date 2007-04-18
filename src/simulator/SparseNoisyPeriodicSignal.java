/*
 * SparseNoisyPeriodicSignal.java
 *
 * Created on 13 April 2007, 14:55
 */

package simulator;

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
    
    public double[] generateReceivedSignal(){
          return null;
    }
    
    public void setNoise(NoiseGenerator noise){
        this.noise = noise;
    }
    
}
