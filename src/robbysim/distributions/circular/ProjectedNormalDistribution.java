/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.distributions.circular;

import robbysim.distributions.GaussianNoise;
import robbysim.distributions.RandomVariable;
import robbysim.Util;

/**
 * This is a circular distribution associated with projecting bivariate
 * Gaussian noise onto the unit circle.
 * This is modified so that it returns values about -[0.5, 0.5]
 * @author Robby McKilliam
 */
public class ProjectedNormalDistribution extends CircularRandomVariable{
    
    protected RandomVariable gauss;
    double cmean, smean;
    
    public ProjectedNormalDistribution(double mean, double var){
        gauss = new GaussianNoise(0.0, var);
        cmean = Math.cos(mean);
        smean = Math.sin(mean);
    }


    @Override
    public double getNoise() {       
        double c = gauss.getNoise() + cmean;
        double s = gauss.getNoise() + smean;
        return Math.atan2(s, c);        
    }

    @Override
    public void randomSeed() {
        gauss.randomSeed();
    }

    @Override
    public void setSeed(long seed) {
        gauss.setSeed(seed);
    }

    public double pdf(double x){
        double v = 1.0/Math.sqrt(gauss.getVariance());
        return Pdf(x,v);
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

            //System.out.print(" " + p1 + ", " + p2 + ", " + p3 + ", " + (v/Math.sqrt(2)*cx) + ", ");

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

    
}
