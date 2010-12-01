/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.location.twod;

import Jama.Matrix;
import pubsim.lattices.util.AbstractPointEnumerator;
import pubsim.lattices.util.PointEnumerator;
import pubsim.lattices.util.RestartablePointEnumerator;
import pubsim.optimisation.NewtonRaphson;
import pubsim.Point2;

/**
 * A brute force, test lots of points and run Newton's
 * method approach.  This is not particularly interesting.
 * @author Robby McKilliam
 */
public class BruteLocationEstimator 
        extends PhaseBasedLocationEstimatorNumenclature
        implements PhaseBasedLocationEstimator{

    RestartablePointEnumerator points;

    /**
     * Uses a point enumerator that is a unit square centered at
     * the origin with step length 0.01.
     * @param trans array of transmitters
     */
    public BruteLocationEstimator(Transmitter[] trans){
        init(trans, new PointsInSquare(-0.5, 0.5, 0.01));
    }

    public BruteLocationEstimator(Transmitter[] trans,
            RestartablePointEnumerator points){
        init(trans, points);
    }

    private void init(Transmitter[] trans, RestartablePointEnumerator points){
        this.points = points;
        this.trans = trans;
    }

    public Point2 estimateLocation(double[] phi) {
        loc = null;
        points.restart();
        double Lbest = Double.NEGATIVE_INFINITY;
        ObjectiveFunction ofunc = new ObjectiveFunction(trans, phi);
        for(Matrix p : points){
            double L = ofunc.value(p);
            //System.out.print("L = " + Lbest + "\np = " + VectorFunctions.print(p));
            if (L > Lbest) {
                Lbest = L;
                loc = new Point2(p);
                //System.out.print("L = " + Lbest + "loc = " + VectorFunctions.print(loc));
            }
        }
        //climb the objective function using Newton's method with this unwrapping
        NewtonRaphson opt = new NewtonRaphson(ofunc);
        try{
            loc = new Point2(opt.maximise(loc));
        }catch(Exception e){
            //if Newton's method fails just return the best quantised point
        }
        return loc;
    }

    @Override
    public Point2 getLocation() {
        return loc;
    }


    /** Generate points inside a square of given size */
    public static class PointsInSquare 
            extends AbstractPointEnumerator
            implements RestartablePointEnumerator{

        protected double min, max, step;
        protected double x, y;

        public PointsInSquare(double min, double max, double step){
            if(min > max)
                throw new RuntimeException("min must be less than max");
            if(step <= 0)
                throw new RuntimeException("step must be greater than 0");
            this.min = min;
            this.max = max;
            this.step = step;
            x = min;
            y = min;
        }

        public double[] nextElementDouble() {
            double[] ret = new double[2];
            ret[0] = x; ret[1] = y;
            if(x < max){
                x+=step;
            }else{
                x = min;
                y+=step;
            }
            return ret;
        }

        public double percentageComplete() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public boolean hasMoreElements() {
            return x < max || y < max;
        }

        public Matrix nextElement() {
            return new Point2(nextElementDouble());
        }

        public void restart() {
            x = min;
            y = min;
        }

    }

}
