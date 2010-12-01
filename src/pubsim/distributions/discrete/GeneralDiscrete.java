/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.distributions.discrete;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import rngpack.RandomElement;
import rngpack.RandomSeedable;
import rngpack.Ranlux;
import pubsim.distributions.RandomVariable;

/**
 * General discrete distribution. Uses a map to set the pdf.
 * @author Robby McKilliam
 */
public class GeneralDiscrete implements RandomVariable{

    protected final TreeMap<Integer, Double> pdf;
    protected final double mean, variance;

    protected RandomElement random = new Ranlux(RandomSeedable.ClockSeed());

    /**
     * Will rescale the pdf if it doesn't sum to one.
     * 
     * @param pdf, a Map from Integer to Double that describes the
     * non-zero part of the pdf.
     */
    public GeneralDiscrete(Map<Integer, Double> pdf){

        //compute the mean and pdfsum first.
        double msum = 0.0, pdfsum = 0.0;
        for( Entry<Integer, Double> e : pdf.entrySet()) {
            double p = e.getValue(); int k = e.getKey();
            msum += k*p;
            pdfsum += p;
            if(p < 0) throw new RuntimeException("pdf can't be negative");
        }
        //set the mean now that the pdf can be rescaled.
        mean = msum / pdfsum;

        //compute the variance and also fill sorted, rescaled pdf.
        this.pdf = new TreeMap<Integer, Double>();
        double varsum = 0.0;
        for( Entry<Integer, Double> e : pdf.entrySet()) {
            double p = e.getValue()/pdfsum; int k = e.getKey();
            this.pdf.put(k, p);
            varsum += (k - mean)*(k - mean)*p;
        }
        //set the variance.
        variance = varsum;
    }

    public double getMean() {
        return mean;
    }

    public double getVariance() {
        return variance;
    }

    public double getNoise() {
        double r = random.raw();
        double pdfsum = 0;
        int k = 0;
        for( Entry<Integer, Double> e : pdf.entrySet()) {
            k = e.getKey();
            pdfsum += e.getValue();
            if(r < pdfsum) return k;
        }
        return k;
    }

    /** Randomise the seed for the internal Random */
    public void randomSeed(){ random = new Ranlux(RandomSeedable.ClockSeed()); }


    /** Set the seed for the internal Random */
    public void setSeed(long seed) { random = new Ranlux(seed); }

    public double pdf(double x) {
        int k = (int)Math.round(x);
        if(pdf.containsKey(k)) return pdf.get(k);
        else return 0.0;
    }

    public double cdf(double x) {
        int k = (int)Math.round(x);
        double pdfsum = 0.0;
        for( Entry<Integer, Double> e : pdf.headMap(k+1).entrySet())
            pdfsum += e.getValue();
        return pdfsum;
    }

    public double icdf(double x) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
