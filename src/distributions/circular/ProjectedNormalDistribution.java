/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package distributions.circular;

import distributions.GaussianNoise;
import distributions.NoiseGenerator;
import flanagan.integration.IntegralFunction;
import flanagan.integration.Integration;
import simulator.Util;

/**
 * This is a circular distribution associated with projecting bivariate
 * Gaussian noise onto the unit circle.
 * @author Robby McKilliam
 */
public class ProjectedNormalDistribution implements NoiseGenerator{
    
    protected NoiseGenerator gauss;
    double cmean, smean, mean;
    
    public ProjectedNormalDistribution(){
        gauss = new GaussianNoise();
        gauss.setMean(0.0);
        setMean(0.0);
        setVariance(1.0);
    }
    
    public ProjectedNormalDistribution(double mean, double var){
        gauss = new GaussianNoise();
        gauss.setMean(0.0);
        setMean(mean);
        setVariance(var);
    }
    

    public void setMean(double mean) {
        this.mean = mean;
        cmean = Math.cos(mean);
        smean = Math.sin(mean);
    }

    public void setVariance(double variance) {
        gauss.setVariance(variance);
    }

    public double getMean() {
        return mean;
    }

    public double getVariance() {
        return gauss.getVariance();
    }

    public double getNoise() {
        
        double c = gauss.getNoise() + cmean;
        double s = gauss.getNoise() + smean;
        return Math.atan2(s, c);
        
    }

    public void randomSeed() {
        gauss.randomSeed();
    }

    public void setSeed(long seed) {
        gauss.setSeed(seed);
    }

    /**
     * Returns the value of the PDF of this distribution
     * v^2 is the SNR of the gaussian distribution used.
     * v = p/sigma.
     * See Quinn, "Estimating the mode of a phase distribution", Asilomar, 2007
     */
    public static double Pdf(double x, double v){
            double pi = Math.PI;
            double cx = Math.cos(2*pi*x);
            double sx = Math.sin(2*pi*x);

            double p1 = v*cx;
            double p2 = Math.exp( -v*v/2 * sx*sx);
            double p3 = Math.sqrt(pi/2) * ( 1.0 + Util.erf( v/Math.sqrt(2)*cx ) );

            return Math.exp(-v*v/2) + p1*p2*p3;
    }

    /**
     * Returns the derivative of the PDF of this distribution
     * v^2 is the SNR of the gaussian distribution used.
     * v = p/sigma.
     * See Quinn, "Estimating the mode of a phase distribution", Asilomar, 2007
     */
    public static double dPdf(double x, double v){
            double pi = Math.PI;
            double cx = Math.cos(2*pi*x);
            double sx = Math.sin(2*pi*x);

            double p1 = v*cx;
            double p2 = Math.exp( -v*v/2 * sx*sx);
            double p3 = Math.sqrt(pi/2) * ( 1.0 + Util.erf( v/Math.sqrt(2)*cx ) );

            double dp1 = -v*2*pi*sx;
            double dp2 = -2*pi*v*v * cx * sx * Math.exp(-v*v/2 * sx*sx);
            double dp3 = -v*2* pi * sx * Math.exp(-v*v/2 * cx*cx);

            return dp1*p2*p3 + p1*dp2*p3 + p1*p2*dp3;
    }

    /**
     * Numerically computes the effective wrapped variance.
     * This is monotonically increases with v and takes
     * values in the range [0,1/4).
     * v^2 is the SNR of the gaussian distribution used.
     * v = p/sigma.
     * See Quinn, "Estimating the mode of a phase distribution", Asilomar, 2007
     */
    public static double getWrappedVariance(double v){
        int INTEGRAL_STEPS = 1000;
        Integration intg = new Integration(new VarianceCalculator(v), -0.5, 0.5);
        return intg.gaussQuad(INTEGRAL_STEPS);
    }

    protected static class VarianceCalculator implements IntegralFunction {

        private double v;
        public VarianceCalculator(double v){
            this.v = v;
        }

        public double function(double x) {
            return x*x*Pdf(x, v);
        }

    }
    
}
