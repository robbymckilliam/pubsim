/*
 * NoiseVector.java
 *
 * Created on 5 November 2007, 13:24
 */

package pubsim.distributions.processes;

import pubsim.distributions.RandomVariable;
import pubsim.SignalGenerator;

/**
 * Generates a vector of iid noise
 * @author Robby McKilliam
 */
public class IIDNoiseVector implements SignalGenerator{
    
    protected int n;
    protected double[] iidsignal;
    protected RandomVariable noise;

    /** Default constructor set length of vector */ 
    public IIDNoiseVector(int length){
        n = length;
        iidsignal = new double[n];
    }

    /** Default constructor set length of vector to 1 */
    public IIDNoiseVector(RandomVariable noise, int length){
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
    
    public void setNoiseGenerator(RandomVariable noise){
        this.noise = noise;
    }
    
    public RandomVariable getNoiseGenerator(){
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
