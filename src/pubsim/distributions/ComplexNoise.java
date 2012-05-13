/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.distributions;

import Jama.Matrix;
import pubsim.Complex;
import pubsim.VectorFunctions;

/**
 * Abstract class for correlated complex noise distribution.
 * UNTESTED!!!!!! 
 * @author Robby McKilliam
 */
public abstract class ComplexNoise implements GenericRandomVariable<Complex> {
    
    final Complex mean;
    final Matrix variance;
    final Matrix L;
    final ContinuousRandomVariable noise;

    public ComplexNoise(Complex mean, Matrix variance, ContinuousRandomVariable doubleNoise){
        this.mean = mean;
        this.variance = variance;
        L = variance.chol().getL();
        noise = doubleNoise;
    }

    /**
     * Constructor assume the covariance matrix is just variance times
     * by the identity matrix.
     */
    public ComplexNoise(Complex mean, double variance, ContinuousRandomVariable doubleNoise){
        this.mean = mean;
        this.variance = Matrix.identity(2,2).times(variance);
        L = Matrix.identity(2,2).times(Math.sqrt(variance));
        noise = doubleNoise;
    }

    public Complex getMean() {
        return mean;
    }

    public Matrix getVariance() {
        return variance;
    }

    public Complex getNoise() {
        double[] x = new double[2];
        x[0] = noise.getNoise();
        x[1] = noise.getNoise();
        double[] y = VectorFunctions.matrixMultVector(L, x);
        return new Complex(y[0], y[1]);
    }

    public void randomSeed() {
        noise.randomSeed();
    }

    public void setSeed(long seed) {
        noise.setSeed(seed);
    }

    public static class Gaussian extends ComplexNoise{

        public Gaussian(Complex mean, Matrix variance){
            super(mean, variance, new GaussianNoise(0,1));
        }

        public Gaussian(Complex mean, double variance){
            super(mean, variance, new GaussianNoise(0,1));
        }

        public double pdf(Complex x) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public double cdf(Complex x) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Complex icdf(double x) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

}
