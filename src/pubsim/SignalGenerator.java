/*
 * SignalGenerator.java
 *
 * Created on 13 April 2007, 14:48
 */

package pubsim;

import pubsim.distributions.ContinuousRandomVariable;
import java.io.Serializable;
import pubsim.distributions.NoiseGenerator;

/**
 * Interface for the generation received signals
 * @author Robby McKillam
 */
public interface SignalGenerator extends Serializable {
    public double[] generateReceivedSignal();
    public void setNoiseGenerator(NoiseGenerator noise);
    public NoiseGenerator getNoiseGenerator();
    
    /** Return the length of the signal generated */
    public int getLength();


    /** Generic version of the signal generator */
    public static interface Generic<T> extends Serializable{

        public T[] generateReceivedSignal();

        public void setNoiseGenerator(NoiseGenerator noise);
        public NoiseGenerator getNoiseGenerator();

        /** Return the length of the signal generated */
        public int getLength();
    }

}
