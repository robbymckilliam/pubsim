/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.distributions.complex;

import pubsim.Complex;
import pubsim.distributions.Chi;
import pubsim.distributions.Gaussian;
import pubsim.distributions.RealRandomVariable;
import pubsim.distributions.circular.CircularRandomVariable;
import pubsim.distributions.circular.CircularUniform;

/**
 * The circularly symmetric complex normal distribution.
 * @author Robby McKilliam
 */
public class SymmetricComplexNormal implements ComplexRandomVariable {

    final protected Gaussian rv;

    /** Set variance of real and imaginary parts */
    public SymmetricComplexNormal(double variance) {
        rv = new Gaussian(0.0, variance);
    }

    @Override
    public Complex mean() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Complex variance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double pdf(Complex x) {
        return rv.pdf(x.re())*rv.pdf(x.im());
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
        return new Complex(rv.noise(), rv.noise());
    }

    @Override
    public void randomSeed() {
        rv.randomSeed();
    }

    @Override
    public void setSeed(long seed) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RealRandomVariable realMarginal() {
        return rv;
    }

    @Override
    public RealRandomVariable imaginaryMarginal() {
        return rv;
    }

    @Override
    public RealRandomVariable magnitudeMarginal() {
        return new Chi.Chi2(rv.variance());
    }

    @Override
    public CircularRandomVariable angleMarginal() {
        return new CircularUniform();
    }
}
