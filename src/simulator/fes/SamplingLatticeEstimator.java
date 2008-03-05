/*
 * SamplingLatticeEstimator.java
 *
 * Created on 18 August 2007, 12:47
 */

package simulator.fes;
import lattices.NearestPointAlgorithmInterface;
import lattices.Pn2Sampled;

/**
 * Simple and fast suboptimal (but perharps can be made optimal)
 * lattice frequency estimator based on the pes.SamplingLLS 
 * period estimator.
 * @author Robby McKilliam
 */
public class SamplingLatticeEstimator implements FrequencyEstimator {
    
    protected int n;
    protected NearestPointAlgorithmInterface lattice;
    protected double[] y;
    protected int samples;
    
    public SamplingLatticeEstimator() { }
    
    /**Constructor that sets the number of samples used */
    public SamplingLatticeEstimator(int samples) { this.samples = samples; }
    
    /** Set the number of samples */
    @Override
    public void setSize(int n){
        lattice = new Pn2Sampled(samples);
        lattice.setDimension(n-2);  
        y = new double[n];
        this.n = n;
    }
    
        /** Run the estimator on recieved data, @param y */
    @Override
    public double estimateFreq(double[] real, double[] imag){
        if(n+2 != real.length)
            setSize(real.length);
        
        for(int i = 0; i < real.length; i++)
            y[i] = Math.atan2(imag[i],real[i])/(2*Math.PI);
        
        lattice.nearestPoint(y);
        
        //calculate f from the nearest point
        double f = 0;
        double sumn = n*(n+1)/2;
        double sumn2 = n*(n+1)*(2*n+1)/6;
        double[] u = lattice.getIndex();
        for(int i = 0; i < n; i++)
            f += (n*(i+1) - sumn)*(y[i]-u[i]);
        
        f /= (sumn2*n - sumn*sumn);

        //System.out.println("f = " + f);
        
        return f;
        
    }
   
}
