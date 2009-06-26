/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.location.twod;

import java.util.Arrays;
import java.util.Vector;
import optimisation.NewtonRaphson;
import simulator.IndexedDouble;
import simulator.Point2;
import simulator.Util;
import simulator.VectorFunctions;

/**
 * Near set (aka Bresenham set) version of the location estimator.
 * The gaurantees that it will test the correct unwrapping.  Whether
 * Newton's method will converge correctly is another matter.
 * @author Robby McKilliam
 */
public class NearSetLocationEstimator 
        extends PhaseBasedLocationEstimatorNumenclature
        implements PhaseBasedLocationEstimator{

    protected double D;
    protected int N;

    /**
     *
     * @param trans Array of transmitters
     * @param maxDistance maximum distance location can be from any transmitter
     */
    public NearSetLocationEstimator(Transmitter[] trans, double maxDistance){
        this.trans = trans;
        D = maxDistance;
        N = trans.length;
    }

    /** Compute the location given phases from each transmitter */
    public Point2 estimateLocation(double[] phi) {

        double Lbest = Double.NEGATIVE_INFINITY;

        for( int n = 0; n < N; n++){
            Transmitter t = trans[n];
            //System.out.println(t);
            double maxk = Util.floorToHalfInt(D/t.wavelength());
            //System.out.println("maxk = " + maxk + ", t.w = " + t.wavelength());
            for(double k = 0.5; k < maxk; k += 1.0){

                Point2 p = t.point();
                double T = t.wavelength();
                //This is the starting point for the iteration
                Point2 x = new Point2(p.getX()+k*T, p.getY());
                double[] u = computeUnwrapping(x);
                u[n] = k+0.5;
                Lbest = testPoint(phi, u, x, Lbest);
                u[n] = k-0.5;
                Lbest = testPoint(phi, u, x, Lbest);

                IndexedDouble[] sorted = computeSortedTransitions(t, k*T);
                //System.out.println(VectorFunctions.print(sorted));
                for( IndexedDouble s : sorted){
                    //u[s.index]

                    //phony test
                    Point2 tx = new Point2(p.getX()+k*T*Math.cos(s.value), p.getY()+k*T*Math.sin(s.value));
                    u = computeUnwrapping(tx);
                    u[n] = k+0.5;
                    Lbest = testPoint(phi, u, tx, Lbest);
                    u[n] = k-0.5;
                    Lbest = testPoint(phi, u, tx, Lbest);

                }
            }
        }

        return loc;
    }

    private double testPoint(double[] phi, double[] u, Point2 x, double Lbest) {
        //climb the objective function using Newton's method with this unwrapping
        ObjectiveFunctionFixedUnwrapping ofunc = new ObjectiveFunctionFixedUnwrapping(trans, phi, u);
        NewtonRaphson opt = new NewtonRaphson(ofunc);
        Point2 xopt = null;
        try{
            xopt = new Point2(opt.maximise(x));
        }catch(Exception e){
            //if there is an exception, just use the point x
            xopt = new Point2(x);
        }
        double L = ofunc.value(xopt);
        double maxD = maxDistanceToTransmitters(new Point2(xopt), trans);
        if (L > Lbest && maxD < D) {
            Lbest = L;
            loc = new Point2(xopt);
            //System.out.print("L = " + Lbest + "loc = " + VectorFunctions.print(loc));
        }
        return Lbest;
    }

    protected IndexedDouble[] computeSortedTransitions(Transmitter tran, double rad){
        Vector<IndexedDouble> ids = new Vector<IndexedDouble>();

        //compute the angles for which all the other tranmitter circles intersect
        //this transmitter circle
        for( int n = 0; n < N; n++){
            Transmitter t = trans[n];
            if( t != tran ){
                //distance between the transmitters
                double tdist = tran.point().minus(t.point()).normF();
                double T = t.wavelength();

                //compute the min and max radius circles that intersect
                double mink = Util.ceilToHalfInt( (tdist - rad)/T );
                double maxk = Util.floorToHalfInt( (tdist + 2*rad)/T );

                for(double k = mink; k <= maxk; k += 1.0){
                    double[] angles = Util.circleIntersectionAngles(tran.point(), rad, t.point(), k*T);
                    if(angles != null){
                        //System.out.println("angles = " + VectorFunctions.print(angles));
                        ids.add(new IndexedDouble(Util.convertAtan2Angle(angles[0]), n));
                        ids.add(new IndexedDouble(Util.convertAtan2Angle(angles[1]), n));
                    }
                }
            }
        }
        //this is probably the lamest method ever!  Stupid java generics!
        IndexedDouble[] ret = ids.toArray(new IndexedDouble[0]);
        Arrays.sort(ret);
        return ret;
    }


}
