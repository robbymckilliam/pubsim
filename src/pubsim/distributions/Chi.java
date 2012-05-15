/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.distributions;

import static pubsim.Util.gamma;

/**
 * The Chi distribution, square root of the sum of square normal random variables
 * @author Robby McKilliam
 */
public class Chi extends AbstractRealRandomVariable {

    final protected int k;
    final protected double var;
    final protected double stdev;
    final protected double mu;
    protected double gammak2;
    final protected GaussianNoise noise;
    
    public Chi(int k){
        this.k = k;
        var = 1.0;
        stdev = 1.0;
        noise = new GaussianNoise(0, var);
        gammak2 = gamma(k/2.0);
        mu = Math.sqrt(2.0)*gamma((k+1.0)/2.0)/gammak2;
    }
    
    public Chi(int k, double var){
        this.k = k;
        this.var = var;
        stdev = Math.sqrt(var);
        noise = new GaussianNoise(0, var);
        gammak2 = gamma(k/2.0);
        mu = stdev*Math.sqrt(2.0)*gamma((k+1.0)/2.0)/gammak2;
    }
    
    @Override
    public Double getMean() {
        return mu;
    }

    @Override
    public Double getVariance() {
        return var*k - mu*mu;
    }

    @Override
    public double pdf(Double x) {
        double xs = x/stdev;
        double num = Math.pow(2,1-k/2.0)*Math.pow(xs,k-1)*Math.exp(-xs*xs/2.0);
        return num/gammak2/stdev;
    }
    
    @Override
    public Double getNoise() {
        double sum = 0.0;
        for(int i = 0; i < k; i++) {
            double X = noise.getNoise();
            sum += X*X;
        }
        return Math.sqrt(sum);
    }
    
 /**
  * Sum of square of 2 independent normal random variables
  */
 public static class Chi2 extends Chi {
     
     public Chi2(){
         super(2);
     }
     
     public Chi2(double var){
         super(2,var);
     }
     
    @Override
    public double pdf(Double x) {
        double xs = x/stdev;
        return xs*Math.exp(-xs*xs/2.0)/stdev;
    }
        
 }
    
    
}
