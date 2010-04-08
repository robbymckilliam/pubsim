/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

import distributions.NoiseGenerator;
import distributions.circular.CircularDistribution;

/**
 * Like PolynommialPhaseSignal but the noise gets added to the phase.
 * @author Robby McKilliam
 */
public class CircularNoisePolynomialPhaseSignal extends PolynomialPhaseSignal{

    @Override
    public double[] generateReceivedSignal() {
        for(int t = 0; t < n; t++){
            double phase = 0.0;
            for(int j = 0; j < params.length; j++)
                phase += Math.pow(t+1,j)*params[j];
            double pnoise = noise.getNoise();
            real[t] = Math.cos(2*Math.PI*(phase + pnoise));
            imag[t] = Math.sin(2*Math.PI*(phase + pnoise));
        }
        return null;
    }

}
