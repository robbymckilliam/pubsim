/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.poly;

import robbysim.distributions.RandomVariable;
import robbysim.distributions.circular.CircularRandomVariable;

/**
 * Like PolynommialPhaseSignal but the noise gets added to the phase.
 * @author Robby McKilliam
 */
public class CircularNoisePolynomialPhaseSignal extends PolynomialPhaseSignal{

    double[] phasearray = new double[0];

    /**
     * Returns the phase signal and places the real and imaginary parts
     * of the complex signal into real and imag arrays that can be retrieved
     * with getReal() and getImag()
     * @return
     */
    @Override
    public double[] generateReceivedSignal() {
        if(phasearray.length != n) phasearray = new double[n];

        for(int t = 0; t < n; t++){
            double phase = 0.0;
            for(int j = 0; j < params.length; j++)
                phase += Math.pow(t+1,j)*params[j];
            double pnoise = noise.getNoise();
            phasearray[t] = phase + pnoise;
            real[t] = Math.cos(2*Math.PI*(phase + pnoise));
            imag[t] = Math.sin(2*Math.PI*(phase + pnoise));
        }
        return phasearray;
    }

}
