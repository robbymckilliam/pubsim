/*
 * SignalGenerator.java
 *
 * Created on 13 April 2007, 14:48
 */

package simulator;

/**
 * Interafece for the generation recieved signals
 * @author Robby McKillam
 */
public interface SignalGenerator {
    public void setTransmittedSignal(double[] transmitted);
    public double[] generateReceivedSignal();
    public void setNoise(NoiseGenerator noise);
}
