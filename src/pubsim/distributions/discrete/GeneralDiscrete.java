/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.distributions.discrete;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * General discrete distribution. Uses a map to set the pdf.
 * @author Robby McKilliam
 */
public class GeneralDiscrete extends AbstractDiscreteRandomVariable implements DiscreteRandomVariable{

    protected final TreeMap<Integer, Double> pdf;
    protected final double mean, variance;

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

    @Override
    public double getMean() {
        return mean;
    }

    @Override
    public double getVariance() {
        return variance;
    }

    @Override
    public Integer getNoise() {
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

    @Override
    public double pmf(Integer k) {
        if(pdf.containsKey(k)) return pdf.get(k);
        else return 0.0;
    }

    @Override
    public double cmf(Integer k) {
        double pdfsum = 0.0;
        for( Entry<Integer, Double> e : pdf.headMap(k+1).entrySet())
            pdfsum += e.getValue();
        return pdfsum;
    }

    @Override
    public Integer icmf(double x) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
