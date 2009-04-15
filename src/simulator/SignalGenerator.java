/*
 * SignalGenerator.java
 *
 * Created on 13 April 2007, 14:48
 */

package simulator;

import distributions.NoiseGenerator;
import java.io.Serializable;

/**
 * Interface for the generation recieved signals
 * @author Robby McKillam
 */
public interface SignalGenerator extends Serializable {
    public double[] generateReceivedSignal();
    public void setNoiseGenerator(NoiseGenerator noise);
    public NoiseGenerator getNoiseGenerator();
    
    /** Set the length of the signal generated */
    public void setLength(int n);
    
    /** Return the length of the signal generated */
    public int getLength();
}
