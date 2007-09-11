/*
 * PSCFDEstimator.java
 *
 * Created on 11 September 2007, 08:51
 */

package simulator.fes;

/**
 * The Parabolic Smoothed Central Finite Difference Estimator
 * @author Robby
 */
public class PSCFDEstimator implements FrequencyEstimator{
       
    int n;
    
    /** Set the number of samples */
    public void setSize(int n){
        this.n = n;
    }
    
    /** Run the estimator on recieved data, @param y */
    public double estimateFreq(double[] real, double[] imag){
        if(n != real.length)
            setSize(real.length);
        
        double ks = 0.0;
        double sumre = 0.0;
        double sumim = 0.0;
        for(int i=0; i<=n-2; i++){
            double re = real[i]*real[i+1] + imag[i]*imag[i+1];
            double im = real[i]*imag[i+1] - real[i+1]*imag[i];
            double mag = Math.sqrt((real[i]*real[i] + imag[i]*imag[i])*(real[i+1]*real[i+1] + imag[i+1]*imag[i+1]));
            sumre += 6.0*(i+1)*(n-1-i)/(n*(n*n-1))*re/mag;
            sumim += 6.0*(i+1)*(n-1-i)/(n*(n*n-1))*im/mag;
        }
        return Math.atan2(sumim,sumre)/(2*Math.PI);
    }
    
}
