/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.distributions.circular;

/**
 * Circular uniform distribution. This is the same as the wrapped
 * uniform set to have unwrapped variance 1/12.  But doing that leads to
 * numerical imprecision when trying the plot the pdf.  This sorts that out.
 * @author Robby McKilliam
 */
public class CircularUniform extends CircularRandomVariable {

    @Override
    public double pdf(Double x) {
        return 1.0;
    }

    /**
     * Returns 1/12
     */
    @Override
    public Double intrinsicVariance(){
        return 1.0/12.0;
    }

    /**
     * The circular uniform has NO unwrapped mean!
     */
    @Override
    public Double intrinsicMean(){
        System.out.println("Warning: Circular uniform has no unwrapped mean. Returning zero");
        return 0.0;
    }

    /**
     * The circular uniform has NO circular mean!
     */
    @Override
    public Double circularMean(){
        System.out.println("Warning: Circular uniform has no circular mean. Returning zero");
        return 0.0;
    }

    /**
     * Return 1.0
     */
    @Override
    public Double circularVariance(){
        return 1.0;
    }

    @Override
    public double cdf(Double x){
        return x + 0.5;
    }
    
    @Override
    public Double icdf(double x){
        return x - 0.5;
    }

    /**
     * return zero.
     */
    @Override
    public Double getMean(){
        return 0.0;
    }

    /**
     * returns 1/12.
     */
    @Override
    public Double getVariance(){
        return 1.0/12.0;
    }

}
