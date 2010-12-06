/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.distributions.circular;

/**
 * Circular uniform distribution. This is really the same of the wrapped
 * uniform set to have unwrapped variance 1/12.  But doing that leads to
 * numerical imprecision when trying the plot the pdf.  This sorts that out.
 * @author Robby McKilliam
 */
public class CircularUniform extends CircularRandomVariable {

    public double pdf(double x) {
        return 1.0;
    }

    /**
     * Returns 1/12
     */
    @Override
    public double unwrappedVariance(){
        return 1.0/12.0;
    }

    /**
     * The circular uniform has NO unwrapped mean!
     */
    @Override
    public double unwrappedMean(){
        System.out.println("Warning: Circular uniform has no unwrapped mean. Returning zero");
        return 0.0;
    }

    /**
     * The circular uniform has NO circular mean!
     */
    @Override
    public double circularMean(){
        System.out.println("Warning: Circular uniform has no circular mean. Returning zero");
        return 0.0;
    }

    /**
     * Return 1.0
     */
    @Override
    public double circularVariance(){
        return 1.0;
    }

    @Override
    public double cdf(double x){
        return x + 0.5;
    }
    
    @Override
    public double icdf(double x){
        return x - 0.5;
    }

    /**
     * return zero.
     */
    @Override
    public double getMean(){
        return 0.0;
    }

    /**
     * returns 1/12.
     */
    @Override
    public double getVariance(){
        return 1.0/12.0;
    }

}
