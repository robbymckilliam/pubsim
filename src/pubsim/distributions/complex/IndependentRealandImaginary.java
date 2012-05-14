/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.distributions.complex;

import pubsim.Complex;
import pubsim.distributions.AbstractRealRandomVariable;
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

        mag = new AbstractRealRandomVariable() {
            public Double getMean() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            public Double getVariance() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
            public double pdf(Double x) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
                
        angle = new CircularRandomVariable() {
            public double pdf(Double x) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };  
        
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
        return mag;
    }

    @Override
    public RealRandomVariable phaseMarginal() {
        return angle;
    }

    @Override
    public Complex getMean() {
        return new Complex(real.getMean(), imag.getMean());
    }

    @Override
    public Complex getVariance() {
        return new Complex(real.getVariance(), imag.getVariance());
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
    public Complex getNoise() {
        return new Complex(real.getNoise(),imag.getNoise());
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
}
