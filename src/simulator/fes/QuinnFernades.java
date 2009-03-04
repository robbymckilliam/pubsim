/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.fes;

/**
 * Implementation of the Quinn and Fernandes frequency estimator
 *
 * @author Robby McKilliam
 */
public class QuinnFernades implements FrequencyEstimator{

    int n;

    int num_samples;

    /**Max number of iterations for the Newton step */
    static final int MAX_ITER = 15;

    /**Step variable for the Newton step */
    static final double EPSILON = 1e-10;

    protected QuinnFernades(){
    }

    /**
     * Contructor that sets the number of samples to be taken of
     * of the periodogram for the initial coarse search.
     */
    public QuinnFernades(int samples){
        num_samples = samples;
    }

    /** Set the number of samples */
    public void setSize(int n){
        this.n = n;
    }

    /** Run the estimator on recieved data, @param y */
    public double estimateFreq(double[] real, double[] imag){
        if (n != real.length)
            setSize(real.length);

        // Coarse search
        double maxp = 0;
        double fhat= 0.0;
            double fstep = 0.5/num_samples;
        for (double f = -0.5; f <= 0.5; f += fstep) {
            double p = PeriodogramEstimator.calculatePeriodogram(real, imag, f);
            if (p > maxp) {
            maxp = p;
            fhat = f;
            }
        }

        //UNDER CONSTRUCTION

        return fhat;
    }


}
