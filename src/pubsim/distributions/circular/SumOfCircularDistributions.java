/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.distributions.circular;

import flanagan.integration.IntegralFunction;
import flanagan.integration.Integration;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;
import pubsim.distributions.RealRandomVariable;

/**
 * Circular distribution that is the weighted sum of others.
 * @author Robby McKilliam
 */
public class SumOfCircularDistributions extends CircularRandomVariable {

    protected final Collection<RealRandomVariable> distributions;
    protected final Collection<Double> weights;

    protected double totalweight = 0.0;
    protected double mean = 0.0;
    protected double variance = 0.0;

    /** Initialize with a collection of distributions and weights */
    public SumOfCircularDistributions(Collection<RealRandomVariable> dist, Collection<Double> whts){
        if( dist.size() != whts.size() )
            throw new ArrayIndexOutOfBoundsException("You can't have a different number of distributions and weights!");
        distributions = dist;
        weights = whts;

        Iterator<RealRandomVariable> distitr = distributions.iterator();
        Iterator<Double> witr = weights.iterator();
        while( witr.hasNext() ){
            double w = witr.next();
            RealRandomVariable d = distitr.next();
            mean += w*d.mean();
            variance += w*d.variance();
            totalweight += w;
        }

    }

    public SumOfCircularDistributions(){
        distributions = new Vector<RealRandomVariable>();
        weights = new Vector<Double>();
    }

    /** Adds a distribution and weight to the current list */
    public void addDistribution( RealRandomVariable dist, double weight ){
        distributions.add(dist);
        weights.add(weight);
        totalweight += weight;
        mean += weight*dist.mean();
        variance += weight*dist.variance();
    }

    @Override
    public Double noise() {
        Iterator<RealRandomVariable> distitr = distributions.iterator();
        Iterator<Double> witr = weights.iterator();
        double wsum = 0.0;
        double r = new Random().nextDouble();
        double noise = 0.0;
        while( witr.hasNext() ){
            wsum += witr.next().doubleValue();
            double rv = distitr.next().noise();
            if(wsum/totalweight > r){
                noise = rv;
                break;
            }
        }
        return noise;
    }
    
    @Override
    public double pdf(Double x) {
        Iterator<RealRandomVariable> distitr = distributions.iterator();
        Iterator<Double> witr = weights.iterator();
        double pdf = 0.0;
        while( witr.hasNext() ){
            double f = distitr.next().pdf(x);
            double w = witr.next().doubleValue();
            pdf += f*w;
        }
        return pdf/totalweight;
    }

    @Override
    public Double mean() {
        return mean;
    }

    @Override
    public Double variance() {
        return variance;
    }

    /** Does nothing. */
    @Override
    public void randomSeed() {
    }

    /** Does nothing. */
    @Override
    public void setSeed(long seed) {
    }

    /**
     * integrate the pdf by default
     */
    @Override
    public double cdf(Double x){
        double startint = mean - 100*Math.sqrt(variance);
        final int INTEGRAL_STEPS = 1000;
        double cdfval = (new Integration(new IntegralFunction() {
                public double function(double x) {
                    return pdf(x);
                }
            }, startint, x)).trapezium(INTEGRAL_STEPS);
        return cdfval;
    }

    /**
     * Default is a binary search of the cdf to find the inverse cdf.
     * This might fail for really weird looking cdfs and is highly non
     * optimised.
     */
    @Override
    public Double icdf(double x){
        double TOL = 1e-9;
        double high = mean + 100*Math.sqrt(variance) + 0.5;
        double low = mean - 100*Math.sqrt(variance) - 0.5;
        double cdfhigh = cdf(high);
        double cdflow = cdf(low);
        while(Math.abs(high - low) > TOL){

            double half = (high + low)/2.0;
            double cdfhalf = cdf(half);

            //System.out.println("half = " + half + ", cdfhalf = " + cdfhalf);

            if(Math.abs(cdfhalf - x) < TOL ) return half;
            else if(cdfhalf <= x){
                low = half;
                cdflow = cdfhalf;
            }
            else{
               high = half;
               cdfhigh = cdfhalf;
            }

        }
        return (high + low)/2.0;
    }



}
