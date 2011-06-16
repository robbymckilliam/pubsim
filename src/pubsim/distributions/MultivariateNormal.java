/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.distributions;

import Jama.Matrix;

/**
 * Class describes the multivariate normal distribution
 * @author Robby McKilliam
 */
public class MultivariateNormal {
    
    protected final Matrix mean, cov, invcov;
    protected final double coeff;

    public MultivariateNormal(Matrix mean, Matrix cov) {
        this.mean = mean;
        this.cov = cov;
        invcov = cov.inverse();
        int k = cov.getColumnDimension();
        coeff = Math.pow(2*Math.PI,-k/2.0)/Math.sqrt(cov.det());
    }
    
    public double pdf(Matrix x){
        Matrix y = x.minus(mean);
        double ySy = (y.transpose().times(invcov).times(y)).get(0,0);
        //System.out.println(ySy);
        return coeff*Math.exp(-ySy/2.0);
    }
    
}
