/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.location.twod;

import distributions.NoiseGenerator;
import simulator.Point2;
import simulator.VectorFunctions;

/**
 * A Point2 and wavelength T.  This defines the
 * position and wavelength of a transmitter.
 */
public class Transmitter extends Object {
    protected Point2 p;
    protected double w;
    public Transmitter(Point2 p, double w){
        this.p = p;
        this.w = w;
    }

    public Point2 point(){
        return p;
    }

    public double wavelength(){
        return w;
    }

    /**
     * Generator a random Transmitter.
     * @param pgen noise generator for positions
     * @param wgen noise generator for wavelength
     */
    public Transmitter(NoiseGenerator pgen, NoiseGenerator wgen){
        p = new Point2(pgen.getNoise(), pgen.getNoise());
        w = wgen.getNoise();
    }

    @Override
    public String toString() {
        return "w = " + w + ",\np = " + VectorFunctions.print(p);
    }

}
