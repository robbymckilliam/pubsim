/*
 * SamplingLatticeCarrierEstimator.java
 *
 * Created on 5 December 2007, 15:54
 */

package simulator.qpsk;

import simulator.fes.FrequencyEstimator;
import simulator.fes.SamplingLatticeEstimator;

/**
 *
 * @author Robby
 */
public class SamplingLatticeCarrierEstimator extends SamplingLatticeEstimator
            implements FrequencyEstimator {
    
    protected int M;
    
    public SamplingLatticeCarrierEstimator() { super(); }
    
    /**Constructor that sets the number of samples used */
    public SamplingLatticeCarrierEstimator(int samples) { super(samples); }
    
    public void setM(int M){
        this.M = M;
    }
    
    /** Run the estimator on recieved data, @param y */
    @Override
    public double estimateFreq(double[] real, double[] imag){
        if(n+2 != real.length)
            setSize(real.length);
        
        for(int i = 0; i < real.length; i++)
            y[i] = M * Math.atan2(imag[i],real[i])/(2*Math.PI);
        
        lattice.nearestPoint(y);
        
        /*
        System.out.println("y antan = " + VectorFunctions.print(y));
        System.out.println("v = " + VectorFunctions.print(v));
        System.out.println("u = " + VectorFunctions.print(u));
        */
        
        //calculate f from the nearest point
        double f = 0;
        double sumn = n*(n+1)/2;
        double sumn2 = n*(n+1)*(2*n+1)/6;
        double[] u = lattice.getIndex();
        for(int i = 0; i < n; i++)
            f += (n*(i+1) - sumn)*(y[i]-u[i]);
        
        f /= M*(sumn2*n - sumn*sumn);

        //System.out.println("f = " + f);
        
        return f;
        
    }
    
}
