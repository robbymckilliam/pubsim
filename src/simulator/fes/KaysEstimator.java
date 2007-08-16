/*
 * KaysEstimator.java
 *
 * Created on 12 August 2007, 20:06
 */

package simulator.fes;

/**
 * Implementation of Kay's phase unwrapping frequency estimator
 * @author Robby McKilliam
 */
public class KaysEstimator implements FrequencyEstimator{
    
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
        for(int i=0; i<=n-2; i++){
            double re = real[i]*real[i+1] + imag[i]*imag[i+1];
            double im = real[i]*imag[i+1] - real[i+1]*imag[i];
            ks += (i + 1)*(n - 1 - i)*Math.atan(im/re);
        }
        return ks*6.0/(2*Math.PI*n*(n*n - 1));
    }
    
}
