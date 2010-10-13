/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package distributions.circular;

import distributions.RandomVariable;
import static simulator.Util.fracpart;

/**
 * Implements a wrapped circular random variable from
 * a random variable on the real line.
 * @author Robby McKilliam
 */
public class WrappedCircularRandomVariable extends CircularRandomVariable{

    protected final RandomVariable dist;
    protected final UnwrappedMeanAndVariance unwrpedmv;
    protected final CircularMeanVariance argmv;

    public WrappedCircularRandomVariable(RandomVariable realdist){
        dist = realdist;
        unwrpedmv = new UnwrappedMeanAndVariance(this);
        argmv = new CircularMeanVariance(this);
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

    @Override
    public double unwrappedVariance() {
        return unwrpedmv.getUnwrappedVariance();
    }

    @Override
    public double unwrappedMean() {
        return unwrpedmv.getUnwrappedMean();
    }

    @Override
    public double circularMean() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double circularVariance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    

}
