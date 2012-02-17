/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.distributions.circular;

import pubsim.distributions.RandomVariable;
import static pubsim.Util.fracpart;

/**
 * Implements a wrapped circular random variable from
 * a random variable on the real line.
 * @author Robby McKilliam
 */
public class WrappedCircularRandomVariable extends CircularRandomVariable{

    protected final RandomVariable dist;

    public WrappedCircularRandomVariable(RandomVariable realdist){
        dist = realdist;
    }

    @Override
    public double getNoise(){
        return fracpart(dist.getNoise());
    }
    
    /** pdf is computed by wrapping and summing */
    public double pdf(double x) {
        double PDF_TOLERANCE = 1e-15;
        double pdf = 0.0;
        int n = 1;
        double tolc = Double.POSITIVE_INFINITY;
        pdf += dist.pdf(x);
        while( tolc > PDF_TOLERANCE && n < 10000){
            double v1 = dist.pdf(x + n);
            double v2 = dist.pdf(x - n);
            tolc = v1 + v2;
            pdf += tolc;
            n++;
        }
        return pdf;
    }
    
    /**
     * cdf is computed by wrapping and summing
     */
    public double cdf(double x){
        double CDF_TOL = 1e-15;
        double cdfval = dist.cdf(x) - dist.cdf(-0.5); 
        double toadd = 1.0;
        int n = 1;
        while(toadd > CDF_TOL){
            double v1 = dist.cdf(x + n) - dist.cdf(-0.5 + n);
            double v2 = dist.cdf(x - n) - dist.cdf(-0.5 - n);
            toadd = v1 + v2;
            cdfval += toadd;
            n++;
        }
        return cdfval;
    }

}
