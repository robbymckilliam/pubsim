/*
 * SignalGenerator.java
 *
 * Created on 13 April 2007, 14:48
 */

package pubsim;

import java.io.Serializable;
import pubsim.distributions.NoiseGenerator;

/**
 * Interface for the generation received signals
 * @author Robby McKillam
 */
public interface SignalGenerator<T> extends Serializable {

        public T[] generateReceivedSignal();

        public void setNoiseGenerator(NoiseGenerator<T> noise);
        public NoiseGenerator<T> getNoiseGenerator();

        /** Return the length of the signal generated */
        public int getLength();

}
