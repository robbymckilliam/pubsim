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
public class NoiseVector implements SignalGenerator {
    
    protected int n;
    protected double[] iidsignal;
    protected NoiseGenerator<Double> noise;

    /** Default constructor set length of vector */ 
    public NoiseVector(int length){
        n = length;
        iidsignal = new double[n];
    }

    /** Default constructor set length of vector to 1 */
    public NoiseVector(NoiseGenerator<Double> noise, int length){
        this.noise = noise;
        this.n = length;
        iidsignal = new double[n];
    }
    
    /** {@inheritDoc} */
    public void setLength(int n){
        this.n = n;
        iidsignal = new double[n];
    }
    
    /** {@inheritDoc} */
    public int getLength() { return n; }
    
    public void setNoiseGenerator(NoiseGenerator noise){
        this.noise = noise;
    }
    
    @Override
    public NoiseGenerator<Double> getNoiseGenerator(){
        return noise;
    }
    
    /** 
     * Generate the iid noise of length n.
     */
    public double[] generateReceivedSignal(){
        for(int i = 0; i < n; i++)
            iidsignal[i] = noise.getNoise();
        return iidsignal;
    }
    
}
