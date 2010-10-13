/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package distributions.discrete;

import distributions.AbstractRandomVariable;

/**
 * Implementation of the geometric distribution.
 * This is the variant that can not return 0 i.e. the pdf is
 * P(k) = (1-p)^(k-1)p
 * @author Robby McKilliam
 */
public class GeometricDistribution extends AbstractRandomVariable {
    private final double p;


    /** 
     * Constructor sets the parameter for this geometric distribution.
     * p must be between 0 and 1.
     */
    public GeometricDistribution(double p){
        if(p <= 0 || p > 1)
            throw new RuntimeException("p must be between 0 and 1.");
        this.p = p;
    }

    @Override
    public double getNoise() {
        double v = 1;
        while(random.raw() > p) v++;
        return v;
    }

    public double pdf(double x) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double icdf(double x) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
