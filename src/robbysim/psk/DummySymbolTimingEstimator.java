/*
 * DummySymbolTimingEstimator.java
 *
 * Created on 13 December 2007, 14:12
 */

package robbysim.psk;

/**
 * Fake symbol timing estimator.
 * @author Robby
 */
public class DummySymbolTimingEstimator implements SymbolTimingEstimator {
    
    protected double f, p;
    protected int n;
    
    public void setPhase(double p){
        this.p = p;
    }
    
    public void setFrequency(double f){
        this.f = f;
    }
    
    public double getFrequency(){
        return f;
    }
    
    public double getPhase(){
        return p;
    }
    
    public void setSize(int n){
        this.n = n;
    }
    
    /** Just updates the phase for the next block */
    public void estimateTiming(double[] s){
        double d = (n - p)*f;
        p = (Math.ceil(d) - d)/f;
    }
    
}
