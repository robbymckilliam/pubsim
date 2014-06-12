/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.distributions.complex;

import pubsim.Complex;
import pubsim.distributions.RealRandomVariable;
import pubsim.distributions.circular.CircularRandomVariable;

/**
 * A complex random variable constructed from two independent real random
 * variables representing the real and imaginary parts.
 *
 * @author Robby McKilliam
 */
public class IndependentRealandImaginary implements ComplexRandomVariable {

    final protected RealRandomVariable real;
    final protected RealRandomVariable imag;
    final protected RealRandomVariable mag;
    final protected CircularRandomVariable angle;

    public IndependentRealandImaginary(RealRandomVariable real, RealRandomVariable imag) {
        this.real = real;
        this.imag = imag;
        
        mag = null;
        angle = null;
    }

    @Override
    public Complex mean() {
        return new Complex(real.mean(), imag.mean());
    }

    @Override
    public Complex variance() {
        return new Complex(real.variance(), imag.variance());
    }

    @Override
    public double pdf(Complex x) {
        return real.pdf(x.re())*imag.pdf(x.im());
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
    public Complex noise() {
        return new Complex(real.noise(),imag.noise());
    }

    @Override
    public void randomSeed() {
        real.randomSeed();
        imag.randomSeed();
    }

    @Override
    public void setSeed(long seed) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RealRandomVariable realMarginal() {
        return real;
    }

    @Override
    public RealRandomVariable imaginaryMarginal() {
        return imag;
    }

    @Override
    public RealRandomVariable magnitudeMarginal() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CircularRandomVariable angleMarginal() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
