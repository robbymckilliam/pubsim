/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.distributions.circular;

import pubsim.distributions.Uniform;

/**
 * 
 * @author Robert McKilliam
 */
public class WrappedUniform extends WrappedCircularRandomVariable{

    protected final double umean, uvar;

    public WrappedUniform(double mean, double var){
        super(new Uniform(mean, var));
        umean = mean; uvar = var;
    }

    /**
     * Return the unwrapped variance.
     */
    @Override
    public Double intrinsicVariance(){
        if( uvar < 1.0/12.0 ) return uvar;
        else if(unwrped == null) unwrped = new InstrinsicMeanAndVariance(this);
        return unwrped.getIntrinsicVariance();
    }

    /**
     * Return the unwrapped variance assuming that the mean is true mean.
     * This is much faster and more accurate if you know the mean in advance.
     */
    @Override
    public Double intrinsicVariance(double truemean){
        if( uvar < 1.0/12.0 ) return uvar;
        else if(unwrped == null || unwrped.getIntrinsicMean() != truemean)
            unwrped = new InstrinsicMeanAndVariance(this,truemean);
        return unwrped.getIntrinsicVariance();
    }

    /**
     * Return the wrapped mean
     */
    @Override
    public Double intrinsicMean(){
        if( uvar < 1.0/12.0 ) return umean;
        else if(unwrped == null) unwrped = new InstrinsicMeanAndVariance(this);
        return unwrped.getIntrinsicMean();
    }

}
