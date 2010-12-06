/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.snpe.bounds;

import pubsim.distributions.RandomVariable;
import pubsim.distributions.circular.UnwrappedMeanAndVariance;
import pubsim.distributions.circular.WrappedCircularRandomVariable;

/**
 * Barry's remarkable central limit theorem!
 * @author Robby McKilliam
 */
public class CLT {

    protected final double scalefac;
    protected final double dmean;

    /**
     * Computes all the bits needed (like the unwrapped variance etc) for
     * the CLT.
     * @param noise: A version of the noise distribution that is scaled by 1/T0.
     *              This is u_n in Barry's writeup.
     * @param discretemean: The mean of the discrete r.v. that des
     *                      described the sparseness of the signal
     * @param T0: The `true' period of the signal
     */
    public CLT(RandomVariable noise, double discretemean, double T0){

        dmean = discretemean;
        WrappedCircularRandomVariable wrped = new WrappedCircularRandomVariable(noise);
        double h = wrped.pdf(-0.5);

        //Is is the wrapped variance or not?  Need to check Barry's
        //CLT a little more closely.
        double wrappedvar = UnwrappedMeanAndVariance.computeWrappedVarianceAbout(0, wrped, 10000);
        //double wrappedvar = noise.getVariance();

        //this is the the scale factor contructed from these numbers;
        scalefac = 12.0*T0*T0*wrappedvar/( (1-h)*(1-h)*dmean*dmean );
        //System.out.println(scalefac);
    }

    public double phaseVar(int N){
        return scalefac * dmean * dmean / 3.0 / N;
    }

    public double periodVar(int N){
        return scalefac / (N*N*N);
    }

    public double periodPhaseCoVar(int N){
        return - scalefac * dmean / 2.0 / (N*N);
    }

}
