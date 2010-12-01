/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.poly;

import java.util.Random;
import pubsim.distributions.RandomVariable;
import java.io.Serializable;
import pubsim.SignalGenerator;
import pubsim.VectorFunctions;

/**
 * Generates m polynomial phase signal with parameters specified
 * by the setParameters method.
 * @author Robby McKilliam
 */
public class PolynomialPhaseSignal implements SignalGenerator{
    
    protected double[] params;
    protected double[] real;
    protected double[] imag;
    
    protected int n;
    
    protected RandomVariable noise;

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
    public void setNoiseGenerator(RandomVariable noise) {
        this.noise = noise;
    }

    @Override
    public RandomVariable getNoiseGenerator() {
        return noise;
    }

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
     * Generates parameters of m polynomial phase signal of order m
     * that are uniformly distributed in the identifiable range.  See:
     * R. G. McKilliam, I. V. L. Clarkson, "Identifiability and aliasing
     * of polynomial phase signals", working paper.
     */
    public void generateRandomParameters(int a){
        if(pgen.getOrder() != a)
            pgen = new RandomParameterGenerator(a);
        this.params = pgen.generateParameters();
    }

    public void generateRandomDPTParameters(){
        if(params == null) throw new RuntimeException("Length of params not defined");
        Random dptrand = new Random();
        int m = params.length - 1;
        params[0] = dptrand.nextDouble() - 0.5;
        params[1] = 0.49*(dptrand.nextDouble() - 0.5);
        for(int k = 2; k <= m; k++){
            params[k] = (dptrand.nextDouble() - 0.5)*Math.pow( (2/n), k-1 )/pubsim.Util.factorial(k);
        }
    }

    public void generateRandomKitchenParameters(double s){
        if(params == null) throw new RuntimeException("Length of params not defined");
        Random dptrand = new Random();
        int m = params.length - 1;
        params[0] = (dptrand.nextDouble() - 0.5);
        params[1] = s*(dptrand.nextDouble() - 0.5);
        for(int k = 2; k <= m; k++){
            params[k] = s*(dptrand.nextDouble() - 0.5)/pubsim.Util.factorial(k);
        }
    }


    public void generateRandomParameters(){
        if(params == null) throw new RuntimeException("Length of params not defined");
        if(pgen.getOrder() != params.length)
            pgen = new RandomParameterGenerator(params.length);
        this.params = pgen.generateParameters();
    }

    /** Return the true value of the parameters */
    public double[] getParameters(){
        return params;
    }
    
    /** Chirp signal have m number of abiguities.  This calcuates
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

    public static class RandomParameterGenerator implements Serializable{
        protected AmbiguityRemover ambr;
        protected int m;
        protected double[] p;
        protected Random rand;

        public RandomParameterGenerator(int m){
            this.m = m;
            ambr = new AmbiguityRemover(this.m);
            p = new double[this.m+1];
            rand = new Random();
        }

        public int getOrder() { return m; }

        public double[] generateParameters(){
            for(int i = 0; i < m+1; i++)
                p[i] = rand.nextDouble();

//            VectorFunctions.matrixMultVector(ambr.getBasisMatrix(), p);
//            System.out.println("p = " + VectorFunctions.print(p));
//            System.out.println("B = " + VectorFunctions.print(ambr.getBasisMatrix()));
            return ambr.disambiguate(VectorFunctions.matrixMultVector(
                                                ambr.getBasisMatrix(), p));
        }


    }

}
