/*
 * SamplingLatticeEstimator.java
 *
 * Created on 18 August 2007, 12:47
 */

package simulator.fes;
import simulator.Pn2Sampled;

/**
 * Simple and fast suboptimal (but perharps can be made optimal)
 * lattice frequency estimator based on the pes.SamplingLLS 
 * period estimator.
 * @author Robby McKilliam
 */
public class SamplingLatticeEstimator extends Pn2Sampled implements FrequencyEstimator {
    
    protected int N;
    
    protected double[] x, y;
    
    public SamplingLatticeEstimator() { super(); }
    
    /**Constructor that sets the number of samples used */
    public SamplingLatticeEstimator(int samples) { super(samples); }
    
        /** Set the number of samples */
    public void setSize(int n){
        setDimension(n-2);  
        x = new double[n];
        y = new double[n];
        N = n;
    }
    
        /** Run the estimator on recieved data, @param y */
    public double estimateFreq(double[] real, double[] imag){
        if(n+2 != real.length)
            setSize(real.length);
        
        for(int i = 0; i < real.length; i++)
            y[i] = Math.atan2(imag[i],real[i])/(2*Math.PI);
        
        project(y, x);
        nearestPoint(x);
        
        //calculate f from the nearest point
        double f = 0;
        double sumn = N*(N+1)/2;
        double sumn2 = N*(N+1)*(2*N+1)/6;
        for(int i = 0; i < N; i++)
            f += (N*(i+1) - sumn)*(y[i]-u[i]);
        
        f /= (sumn2*N - sumn*sumn);

        //System.out.println("f = " + f);
        
        return f;
        
    }
   
}
