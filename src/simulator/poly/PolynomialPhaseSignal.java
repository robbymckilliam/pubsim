/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

import java.util.Random;
import distributions.NoiseGenerator;
import simulator.SignalGenerator;
import simulator.VectorFunctions;

/**
 * Generates a polynomial phase signal with parameters specified
 * by the setParameters method.
 * @author Robby McKilliam
 */
public class PolynomialPhaseSignal implements SignalGenerator{
    
    protected double[] params;
    protected double[] real;
    protected double[] imag;
    
    protected int n;
    
    protected NoiseGenerator noise;

    @Override
    public double[] generateReceivedSignal() {
        for(int t = 0; t < n; t++){
            double phase = 0.0;
            for(int j = 0; j < params.length; j++)
                phase += Math.pow(t+1,j)*params[j];
            real[t] = Math.cos(2*Math.PI*(phase)) + noise.getNoise();
            imag[t] = Math.sin(2*Math.PI*(phase)) + noise.getNoise();
        }
        return null;
    }

    @Override
    public void setNoiseGenerator(NoiseGenerator noise) {
        this.noise = noise;
    }

    @Override
    public NoiseGenerator getNoiseGenerator() {
        return noise;
    }

    @Override
    public void setLength(int n) {
        this.n = n;
        real = new double[n];
        imag = new double[n];
        
    }

    @Override
    public int getLength() {
        return n;
    }
    
    /** Return the noisy real component of the signal */
    public double[] getReal() { return real; }
    
    /** Return the noisy imaginary component of the signal */
    public double[] getImag() { return imag; }
    
    /** 
     * Set the parameters for the polynomial phase signal with phase
     * p[0] + p[1]t + p[2]t^2 + ...
     */
    public void setParameters(double[] p){
        this.params = p;
    }

    RandomParameterGenerator pgen = new RandomParameterGenerator(0);
    /**
     * Generates parameters of a polynomial phase signal of order a
     * that are uniformly distributed in the identifiable range.  See:
     * R. G. McKilliam, I. V. L. Clarkson, "Identifiability and aliasing
     * of polynomial phase signals", working paper.
     */
    public void generateRandomParameters(int a){
        if(pgen.getOrder() != a)
            pgen = new RandomParameterGenerator(a);
        this.params = pgen.generateParameters();
    }

    public double[] getParameters(){
        return params;
    }
    
    /** Chirp signal have a number of abiguities.  This calcuates
     * the mse for each parameter after removing ambiguities.
     * This only works for linear Chirp, use AmbiguityRemover
     * for the general case.
     * @param t is the true parameter values
     * @param e is the estimated values
     * @return MSE between t and e for each parameter in an array
     * @deprecated Use AmbiguityRemover instead
     */
    public static double[] disambiguateMSE(double[] t, double[] e){
        double[] p = new double[t.length];
        double[] MSE = new double[t.length];
        System.arraycopy(e, 0, p, 0, e.length);
        
        double mseB = VectorFunctions.distance_between2(t, p);
        for(int i = 0; i < t.length; i++)
            MSE[i] = (t[i] - p[i])*(t[i] - p[i]);
        
        if(t.length == 3){
            //fix up quadratic phase ambiguitiy
            for(int i = 1; i < t.length; i++){
                p[i] += 0.5;
                p[i] -= Math.round(p[i]);
            }

            double mse = VectorFunctions.distance_between2(t, p);
            if( mse < mseB ){
                for(int i = 0; i < t.length; i++)
                    MSE[i] = (t[i] - p[i])*(t[i] - p[i]);
            }
        }
        
        return MSE;
    }

    public static class RandomParameterGenerator{
        protected AmbiguityRemover ambr;
        protected int a;
        protected double[] p;
        protected Random rand;

        public RandomParameterGenerator(int a){
            this.a = a;
            ambr = new AmbiguityRemover(a);
            p = new double[a];
            rand = new Random();
        }

        public int getOrder() { return a; }

        public double[] generateParameters(){
            for(int i = 0; i < a; i++)
                p[i] = rand.nextDouble();

            return ambr.disambiguate(p);
        }


    }

}
