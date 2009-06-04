/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.util;

import lattices.LatticeAndNearestPointAlgorithm;
import simulator.VectorFunctions;

/**
 * This computes approximations of various second
 * moments and the outradius of a lattice.
 * @author Robby McKilliam
 */
public class PropertyCalculator {

    private PointInVoronoi points;
    private double outradius = Double.NEGATIVE_INFINITY;
    private double sm = 0.0;
    private double vol, N;
    private int numpoints = 0;

    public PropertyCalculator(LatticeAndNearestPointAlgorithm L, int samples){
        points = new UniformInVornoi(L, samples);
        vol = L.volume();
        N = L.getDimension();
        while(points.hasMoreElements()){
            calculateProperty(points.nextElementDouble());
        }
    }

    protected void calculateProperty(double[] p){
        //System.out.println(VectorFunctions.print(p));
        double mag2 = VectorFunctions.sum2(p);
        if(mag2 > outradius) outradius = mag2;
        sm += mag2;
        numpoints++;
    }

    public double outRadius() {return Math.sqrt(outradius);}

    public double coveringRadius() {return outRadius();}

     public double normalisedSecondMoment() {
        return secondMoment()/vol;
    }

    public double secondMoment() {return sm/numpoints;}

    public double dimensionalessSecondMoment() {
        double pvol = Math.pow(vol, 2.0/N);
        return normalisedSecondMoment()/pvol/N;
    }


}
