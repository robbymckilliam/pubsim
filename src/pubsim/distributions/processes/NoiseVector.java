/*
 * NoiseVector.java
 *
 * Created on 5 November 2007, 13:24
 */

package pubsim.distributions.processes;

import pubsim.distributions.ContinuousRandomVariable;
import pubsim.SignalGenerator;
import pubsim.distributions.NoiseGenerator;

/**
 * Class for outputting vectors of noise.
 * @author Robby McKilliam
 */
public class NoiseVector implements SignalGenerator<Double> {
    
    protected int n;
    protected Double[] iidsignal;
    protected NoiseGenerator<Double> noise;

    /** Default constructor set length of vector */ 
    public NoiseVector(int length){
        n = length;
        iidsignal = new Double[n];
    }

    /** Default constructor set length of vector to 1 */
    public NoiseVector(NoiseGenerator<Double> noise, int length){
        this.noise = noise;
        this.n = length;
        iidsignal = new Double[n];
    }
    
    /** {@inheritDoc} */
    @Override
    public int getLength() { return n; }
    
    @Override
    public void setNoiseGenerator(NoiseGenerator<Double> noise){
        this.noise = noise;
    }
    
    @Override
    public NoiseGenerator<Double> getNoiseGenerator(){
        return noise;
    }
    
    /** 
     * Generate the iid noise of length n.
     */
    @Override
    public Double[] generateReceivedSignal(){
        for(int i = 0; i < n; i++)
            iidsignal[i] = noise.getNoise();
        return iidsignal;
    }
    
}
