/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.location.twod;

import simulator.Point2;
import simulator.Util;
import static simulator.Range.range;

/**
 * NearSet version of the algorithm that assumes the estimate
 * is in a sphere about the origin.
 * @author Robby McKilliam
 */
public class NearSetLocationEstimatorInSphere
            extends PhaseBasedLocationEstimatorNumenclature
            implements PhaseBasedLocationEstimator{

    private final double R;
    private final int N;

    public NearSetLocationEstimatorInSphere(Transmitter[] trans, double radius){
        R = radius;
        this.trans = trans;
        N = trans.length;
    }

    private final Point2 origin = new Point2(0,0);
    public Point2 estimateLocation(double[] d) {

        double Lbest = Double.NEGATIVE_INFINITY;

        for(Transmitter t : trans){

                for(double k : transmitterRange(t)){
                    
                    //computeSortedTransitions
                }

        }

        return loc;
    }

    protected Iterable<Double> transmitterRange(Transmitter t){
        final double w = t.wavelength();
        final double dist = t.point().magnitude();
        final double kmin = Math.max( Util.ceilToHalfInt( (dist - R)/w ), 0.5 );
        final double kmax = Util.floorToHalfInt( (dist + R)/w );
        return range(kmin, kmax, 1.0);
    }

    /*
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
                //double Amaxk = Math.max(Util.floorToHalfInt(D/t.wavelength()), 0.5);
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
    */

}
