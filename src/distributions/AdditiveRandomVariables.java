/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package distributions;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

/**
 * Random variables that have been added together.
 * @author Robby McKilliam
 */
public class AdditiveRandomVariables extends NoiseGeneratorFunctions implements NoiseGenerator {

    protected final Collection<NoiseGenerator> distributions;
    protected final Collection<Double> weights;

    protected double totalweight = 0.0;

    public AdditiveRandomVariables(Collection<NoiseGenerator> dist, Collection<Double> whts){
        if( dist.size() != whts.size() )
            throw new ArrayIndexOutOfBoundsException("You can't a different number of distributions and weights!");
        distributions = dist;
        weights = whts;
        for( Double w: weights ) totalweight += w;
    }

    public AdditiveRandomVariables(){
        distributions = new Vector<NoiseGenerator>();
        weights = new Vector<Double>();
    }

    @Override
    public double getNoise() {
        Iterator<NoiseGenerator> distitr = distributions.iterator();
        Iterator<Double> witr = weights.iterator();
        double wsum = 0.0;
        double r = new Random().nextDouble();
        double noise = 0.0;
        while( witr.hasNext() ){
            wsum += witr.next().doubleValue();
            double rv = distitr.next().getNoise();
            if(wsum/totalweight > r){
                noise = rv;
                break;
            }
        }
        return noise;
    }

    public double pdf(double x) {
        throw new UnsupportedOperationException("Not supported yet.");
    }



}
