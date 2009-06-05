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
        points = new SampledInVoronoi(L, samples);
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

    /**
     * Carefull here, the normalised second moment does not need to
     * be divided by the volume due to way that the points are generated
     * (i.e. using the fundamental parralelipided).  If you write the integral
     * down it becomes clear that the change of variables occurs with a
     * Jacobian that is equal to the volume, hence cancellation.
     *
     * Really, I think that Conway and Sloane got this a little wrong.  The
     * normalised second moment should be I = U/vol^(2/n + 1) then it is scale
     * independent.  The the dimensionless second moment is then I/n
     */
     public double normalisedSecondMoment() {
        return sm/numpoints/Math.pow(vol, 2.0/N);
    }

    public double secondMoment() {return sm/numpoints*vol;}

    public double dimensionalessSecondMoment() {
        return normalisedSecondMoment()/N;
    }


}
