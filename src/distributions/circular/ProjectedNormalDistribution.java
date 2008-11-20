/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package distributions.circular;

import distributions.GaussianNoise;
import simulator.NoiseGenerator;

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

    
    
}
