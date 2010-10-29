/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.distributions.circular;

import robbysim.distributions.RandomVariable;
import static robbysim.Util.fracpart;

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

    public double pdf(double x) {
        double PDF_TOLERANCE = 1e-9;
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
    

}
