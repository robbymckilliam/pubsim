/*
 * DummyCarrierEstimator.java
 *
 * Created on 13 December 2007, 12:39
 */

package simulator.qpsk;

/**
 *
 * @author Robby
 */
public class DummyCarrierEstimator implements CarrierEstimator{
    
    protected double f, p;
    protected int n;
    
    /** Set the number of samples */
    public void setSize(int n){
        this.n = n;
    }
    
    /** 
     * This does nothing other that calculate the phase
     * for the next block.
     */
    public void estimateCarrier(double[] y){
        double d = f*n + p;
        p = d - Math.rint(d);
    }
    
    public void setPhase(double p){ this.p = p; };
    
    /** Return the estimated phase */
    public double getPhase(){
        return p;
    }
    
    public void setFreqency(double f){ this.f = f; };
    
    /** Return the estiamted freqenucy */
    public double getFreqency(){
        return f;
    }
    
    /** Set the maximum allowed frequency */
    public void setFmin(double fmin){ }
    
    /** Set the minimum allowed frequency */
    public void setFmax(double fmax){ }
    
}
