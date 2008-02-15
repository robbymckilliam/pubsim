/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

import simulator.NoiseGenerator;
import simulator.SignalGenerator;

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
                phase += Math.pow(t,j)*params[j];
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

}
