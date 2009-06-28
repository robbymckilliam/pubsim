/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.location.twod;

import Jama.Matrix;
import distributions.NoiseGenerator;
import java.util.Vector;
import lattices.Lattice;
import lattices.util.PointInSphere;
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

    /**
     * Return an array of transmitters that are positioned at lattice
     * points.
     * @param L a lattice
     * @param y translation of the lattice
     * @param radius radius of the sphere
     * @param wgen generator for transmitter wavelengths.
     * @return
     */
    public static Transmitter[] getLatticeArray(Lattice L, double radius, Point2 y, NoiseGenerator wgen){
        PointInSphere points = new PointInSphere(L, radius, y.getColumnPackedCopy());
        Vector<Transmitter> tvec = new Vector<Transmitter>();
        while(points.hasMoreElements()){
            tvec.add(new Transmitter(new Point2(points.nextElement().minus(y)), wgen.getNoise()));
        }
        return tvec.toArray(new Transmitter[0]);
    }

    /**
     * Return an array of transmitters that are positioned at lattice
     * points plus some noise
     * @param L a lattice
     * @param y translation of the lattice
     * @param radius radius of the sphere
     * @param ngen generator for position noise.
     * @param wgen generator for transmitter wavelengths.
     * @return
     */
    public static Transmitter[] getNoisyLatticeArray(Lattice L, double radius, Point2 y, NoiseGenerator ngen, NoiseGenerator wgen){
        PointInSphere points = new PointInSphere(L, radius, y.getColumnPackedCopy());
        Vector<Transmitter> tvec = new Vector<Transmitter>();
        while(points.hasMoreElements()){
            Matrix ps = points.nextElement().minus(y);
            tvec.add(new Transmitter(new Point2(ps.get(0,0) + ngen.getNoise(), ps.get(1,0) + ngen.getNoise()), wgen.getNoise()));
        }
        return tvec.toArray(new Transmitter[0]);
    }

    /**
     * Generate an array of N random transmitters
     * @param N number of transmitters to generate
     * @param pgen generator for transmitter position.
     * @param wgen generator for transmitter wavelengths.
     * @return
     */
    public static Transmitter[] getRandomArray(int N, NoiseGenerator pgen, NoiseGenerator wgen){
        Transmitter[] tvec = new Transmitter[N];
        for(int n = 0; n < N; n++)
            tvec[n] = new Transmitter(pgen, wgen);
        return tvec;
    }

}
