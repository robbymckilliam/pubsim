/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.util;

import java.io.Serializable;
import pubsim.lattices.LatticeAndNearestPointAlgorithm;
import pubsim.VectorFunctions;

/**
 * This computes approximations of various second
 * moments and the outradius of a lattice.
 * @author Robby McKilliam
 */
public class PropertyCalculator implements Serializable{

    private double outradius = Double.NEGATIVE_INFINITY;
    private double sm = 0.0;
    private final double vol, N;
    private int numpoints = 0;
    private final LatticeAndNearestPointAlgorithm L;

    public PropertyCalculator(LatticeAndNearestPointAlgorithm L){
        this.L = L;
        vol = L.volume();
        N = L.getDimension();
    }

    /**
     * Spreads points evenly in Voronoi region to compute moments.
     * @param samples per dimension
     */
    public void evenlySampled(int samples){
        PointEnumerator points = new SampledInVoronoi(L, samples);
        while(points.hasMoreElements()){
            calculateProperty(points.nextElementDouble());

        }
    }

    /**
     * Print percentage complete to System.out while running.  Value of print boolean
     * does not matter.
     */
    public void evenlySampledPrintPercentage(int samples){
        PointEnumerator points = new SampledInVoronoi(L, samples);
        int oldper = 0;
        while(points.hasMoreElements()){
            calculateProperty(points.nextElementDouble());
            int p = (int)points.percentageComplete();
            if(p == oldper){
                System.out.println(p + "%");
                System.out.flush();
                oldper = p + 5;
            }
        }
    }

    /**
     * Runs with points generated in uniformly (psuedoranomly) in the Voronoi region.
     * Stops after 100 consecutive points don't change the dimensionless second moment
     * by more than the tolerance.  This is unlikely to perform sensibly though, the convergence
     * of calculating the Voronoi region this way is very slow!  If we could somehow compute
     * something above and something below (but both converge) it would be better.
     */
    public void uniformWithTolerance(LatticeAndNearestPointAlgorithm L, double tolerance){
        final PointEnumerator points = new UniformInVornoi(L, Integer.MAX_VALUE);
        double oldG = 0;
        int count = 0;
        while(count < 100){
            calculateProperty(points.nextElementDouble());
            double G = dimensionalessSecondMoment();
            System.out.println(Math.abs(G - oldG));
            if(Math.abs(G - oldG) < tolerance){
                    count++;
            }else{
                oldG = G;
                count = 0;
            }
        }
    }

    /**
     * Runs with points generated in uniformly (psuedoranomly) in the Voronoi region.
     * @param samples number of points generated.
     */
    public void uniformlyDistributed(int samples){
        final PointEnumerator points = new UniformInVornoi(L, Integer.MAX_VALUE);
        double oldG = 0;
        int count = 0;
        for(int n = 0; n < samples; n++)
            calculateProperty(points.nextElementDouble());
    }

    /**
     * Runs with points generated in uniformly (psuedoranomly) in the Voronoi region.
     * @param samples number of points generated.
     */
    public void uniformlyDistributedPrintPercentage(int samples){
        final PointEnumerator points = new UniformInVornoi(L, Integer.MAX_VALUE);
        double oldG = 0;
        int count = 0;
        int lastpercent = 0;
        for(int n = 0; n < samples; n++){
            calculateProperty(points.nextElementDouble());

            int percentComplete = (int)(100*(((double)n)/samples));
            if( (percentComplete%10) == 0 && percentComplete != lastpercent){
                System.out.print(percentComplete + "% ");
                lastpercent = percentComplete;
            }

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
     * independent.  The dimensionless second moment is then I/n
     */
     public double normalisedSecondMoment() {
        return sm/numpoints/Math.pow(vol, 2.0/N);
    }

    public double secondMoment() {return sm/numpoints;}

    public double dimensionalessSecondMoment() {
        return normalisedSecondMoment()/N;
    }


    /**
     * Return the computed error, this is not normalised by the number
     * of iterations actually run.
     */
    public double rawError() { return sm; }

}
