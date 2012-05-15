/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.distributions.complex;

import pubsim.Complex;
import pubsim.distributions.RealRandomVariable;
import pubsim.distributions.circular.CircularRandomVariable;
import pubsim.distributions.circular.CircularUniform;

/**
 * Implement a circularly symmetric complex random variable.  Defined by its
 * magnitude probability density function.
 * @author Robby McKilliam
 */
public class CircularlySymmetric implements ComplexRandomVariable {
    
    final protected RealRandomVariable mag;
    final protected CircularUniform angle = new CircularUniform();

    /** Constructor takes a random variable representing the magnitude. */
    public CircularlySymmetric(RealRandomVariable mag) {
        this.mag = mag;
    }

    /** 
     * The mean of a circularly symmetric complex random variable is
     * always zero.
     * @return 
     */
    @Override
    public Complex getMean() {
        return new Complex(0.0,0.0);
    }

    @Override
    public Complex getVariance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double pdf(Complex x) {
        return mag.pdf(x.abs())/2*Math.PI;
    }

    @Override
    public double cdf(Complex x) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Complex icdf(double x) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Complex getNoise() {
        return Complex.constructComplexExp(mag.getNoise(), 
                                            2*Math.PI*angle.getNoise());
    }

    @Override
    public void randomSeed() {
        mag.randomSeed();
        angle.randomSeed();
    }

    @Override
    public void setSeed(long seed) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RealRandomVariable realMarginal() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RealRandomVariable imaginaryMarginal() {
        return mag;
    }

    @Override
    public RealRandomVariable magnitudeMarginal() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /** 
     * Angles are considered in [-0.5, 0.5), rather than [-pi,pi) or
     * [0,2pi).
     */
    @Override
    public CircularRandomVariable angleMarginal() {
        return angle;
    }
    
    
    
}
