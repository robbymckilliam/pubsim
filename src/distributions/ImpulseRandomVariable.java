/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package distributions;

/**
 * A `impulse' random variable, or dirac delta etc.
 * @author harprobey
 */
public class ImpulseRandomVariable extends NoiseGeneratorFunctions implements NoiseGenerator {

    protected final double dval;

    /**
     * dval is the value that the random variable always returns,
     *  i.e. it is where the impulse is.
     */
    public ImpulseRandomVariable(double dval){
        this.dval = dval;
    }

    @Override
    public double getNoise() {
        return dval;
    }

    /**
     * Return 1.0 at the impulse.  This makes plotting it easy and is
     * correct for discrete rv's but isn't strictly correct for continous rv's.
     */
    public double pdf(double x) {
        if(x == dval) return 1.0;
        else return 0;
    }

}
