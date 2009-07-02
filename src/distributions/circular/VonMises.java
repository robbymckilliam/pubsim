/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package distributions.circular;

import java.util.Random;
import distributions.NoiseGenerator;

/**
 * von Mises distribution of
 * @author Robby McKilliam
 */
public class VonMises implements NoiseGenerator{

    double mu, kappa;
    Random U;
    
    public VonMises(){
        U = new Random();
        mu = 0.0;
        kappa = 1.0;
    }
    
    public VonMises(double mu, double kappa){
        U = new Random();
        this.mu = mu;
        this.kappa = kappa;
    }
    
    
    public void setMean(double mean) {
        mu = mean;
    }

    /** 
     * The actually sets the von Mises parameter (usually denoted kappa)
     * which is a dispersion parameter similar to variance.
     * @param variance
     */
    public void setVariance(double variance) {
        kappa = variance;
    }

    public double getMean() {
        return mu;
    }

    /** 
     * The actually sets the von Mises parameter (usually denoted kappa)
     * which is a dispersion parameter similar to variance.
     */
    public double getVariance() {
        return kappa;
    }

    /**
     * Generates von Mises noise using an algorithm of Best and Fisher.
     * See Mardia, Directional Statistics, p43.
     */
    public double getNoise() {
        double a = 1 + Math.sqrt(1 + 4*kappa*kappa);
        double b = (a - Math.sqrt(2*a))/(2*kappa);
        double r = (1 + b*b)/(2*b);
        
        while(true){
        
            double z = Math.cos(Math.PI*U.nextDouble());
            double f = (1 + r*z)/(r + z);
            double c = kappa*(r - f);
            
            double U2 = U.nextDouble();
            if(c*(2-c) - U2 > 0 || Math.log(c/U2) + 1 - c > 0)
                return mu + Math.signum(U.nextDouble() - 0.5)*Math.acos(f);
            
        }
           
    }

    public void randomSeed() {
        U = new Random();
    }

    public void setSeed(long seed) {
        U.setSeed(seed);
    }

}
