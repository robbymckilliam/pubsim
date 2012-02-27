/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.distributions.circular;

import pubsim.distributions.UniformNoise;

/**
 * 
 * @author Robert McKilliam
 */
public class WrappedUniform extends WrappedCircularRandomVariable{

    protected final double umean, uvar;

    public WrappedUniform(double mean, double var){
        super(new UniformNoise(mean, var));
        umean = mean; uvar = var;
    }

    /**
     * Return the unwrapped variance.
     */
    @Override
    public double unwrappedVariance(){
        if( uvar < 1.0/12.0 ) return uvar;
        else if(unwrped == null) unwrped = new UnwrappedMeanAndVariance(this);
        return unwrped.getUnwrappedVariance();
    }

    /**
     * Return the unwrapped variance assuming that the mean is true mean.
     * This is much faster and more accurate if you know the mean in advance.
     */
    @Override
    public double unwrappedVariance(double truemean){
        if( uvar < 1.0/12.0 ) return uvar;
        else if(unwrped == null || unwrped.getUnwrappedMean() != truemean)
            unwrped = new UnwrappedMeanAndVariance(this,truemean);
        return unwrped.getUnwrappedVariance();
    }

    /**
     * Return the wrapped mean
     */
    @Override
    public double unwrappedMean(){
        if( uvar < 1.0/12.0 ) return umean;
        else if(unwrped == null) unwrped = new UnwrappedMeanAndVariance(this);
        return unwrped.getUnwrappedMean();
    }

}
