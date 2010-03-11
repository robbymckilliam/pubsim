/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.bearing.phase;

import distributions.NoiseGenerator;
import simulator.SignalGenerator;
import simulator.Complex;

/**
 * Returns a noisy contant phase complex signal.
 * @author Robby McKilliam
 */
public class ConstantPhaseSignal implements SignalGenerator.Generic<Complex>{

    protected Complex mean;
    protected Complex[] signal;
    protected int n;
    protected NoiseGenerator noise;

    public ConstantPhaseSignal(){
        this.mean = new Complex();
    }

    public ConstantPhaseSignal(Complex mean){
        this.mean = mean;
    }

    public void setMean(Complex mean){
        this.mean = mean;
    }

    public Complex[] generateReceivedSignal() {
        for(int i = 0; i<n; i++){
            Complex nc = new Complex(noise.getNoise(), noise.getNoise());
            signal[i] = mean.add(nc);
        }
        return signal;
    }

    public void setNoiseGenerator(NoiseGenerator noise) {
        this.noise = noise;
    }

    public NoiseGenerator getNoiseGenerator() {
        return noise;
    }

    public void setLength(int n) {
        this.n = n;
        signal = new Complex[n];
    }

    public int getLength() {
        return n;
    }

}
