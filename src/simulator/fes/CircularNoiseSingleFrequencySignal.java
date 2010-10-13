/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.fes;

import simulator.poly.*;
import distributions.RandomVariable;
import distributions.circular.CircularRandomVariable;

/**
 * Like PolynommialPhaseSignal but the noise gets added to the phase.
 * @author Robby McKilliam
 */
public class CircularNoiseSingleFrequencySignal extends NoisyComplexSinusoid{

    @Override
    public double[] generateReceivedSignal() {
        for(int t = 0; t < n; t++){
            double phi = t*f + p;
            double pnoise = noise.getNoise();
            real[t] = Math.cos(2*Math.PI*(phi + pnoise));
            imag[t] = Math.sin(2*Math.PI*(phi + pnoise));
        }
        return null;
    }

}
