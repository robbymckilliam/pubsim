/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.location.twod;

import pubsim.Point2;
import pubsim.SignalGenerator;
import pubsim.VectorFunctions;
import pubsim.distributions.NoiseGenerator;

/**
 * Generates a set of noisy phase signal given the position
 * and frequency of some transmitters and the position of a sensor
 * @author Robby McKilliam
 */
public class NoisyPhaseSignals implements SignalGenerator{
    
    NoiseGenerator noise;
    double[] d;
    Point2 x;
    int N;

    /** Array of transmitters */
    Transmitter[] trans;

    /**
     * @param x the sensor position
     * @param trans the position and frequency of transmitters.
     */
    public NoisyPhaseSignals(Point2 x, Transmitter[] trans){
        init(x, trans);
    }

    protected void init(Point2 x,
            Transmitter[] trans){
        this.trans = trans;
        this.x = x;
        N = trans.length;
        d = new double[N];

    }

    /** Return the transmitters used */
    public Transmitter[] getTransmitters(){
        return trans;
    }

    public double[] generateReceivedSignal() {
        for(int n = 0; n < N; n++){
            double dist = VectorFunctions.distance_between(
                    x.getColumnPackedCopy(), trans[n].point().getColumnPackedCopy());
            double T = trans[n].wavelength();
            double nd = dist/T + noise.getNoise();
            d[n] = T*(nd - Math.rint(nd));
        }
        return d;
    }

    /** Generate the recived signal for location x */
    public double[] generateReceivedSignal(Point2 x) {
        for(int n = 0; n < N; n++){
            double dist = VectorFunctions.distance_between(
                    x.getColumnPackedCopy(), trans[n].point().getColumnPackedCopy());
            double T = trans[n].wavelength();
            double nd = dist/T + noise.getNoise();
            d[n] = T*(nd - Math.rint(nd));
        }
        return d;
    }

    /*public void setLocation(Point2 x){
        this.x = x;
    }*/

    public void setNoiseGenerator(NoiseGenerator noise) {
        this.noise = noise;
    }

    public NoiseGenerator getNoiseGenerator() {
        return noise;
    }

    public void setLength(int n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /** Returns the number of transmitters */
    public int getLength() {
        return trans.length;
    }

    /** Return the true location of the sensor. */
    public Point2 getLocation(){
        return x;
    }


}
