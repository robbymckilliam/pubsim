/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.location.twod;

import Jama.Matrix;
import lattices.util.PointEnumerator;
import optimisation.NewtonRaphson;
import simulator.Point2;
import simulator.VectorFunctions;

/**
 * A brute force, test lots of points and run Newton's
 * method approach.  This is not particularly interesting.
 * @author Robby McKilliam
 */
public class BruteLocationEstimator implements PhaseBasedLocationEstimator{

    PointEnumerator points;
    Transmitter[] trans;

    public BruteLocationEstimator(Transmitter[] trans, PointEnumerator points){
        this.points = points;
        this.trans = trans;
    }

    public Point2 computeLocation(double[] d) {
            throw new UnsupportedOperationException("Not supported yet.");
//        while(points.hasMoreElements()){
//            Point2 p = new Point2(points.nextElement());
//            //climb the objective function using Newton's method with this unwrapping
//            ObjectiveFunctionFixedUnwrapping ofunc = new ObjectiveFunctionFixedUnwrapping(trans, phi, u);
//            NewtonRaphson opt = new NewtonRaphson(ofunc);
//            Point2 xopt = new Point2(opt.maximise(x));
//            double L = ofunc.value(xopt);
//            if (L > Lbest) {
//                Lbest = L;
//                loc = new Point2(xopt);
//                System.out.print("L = " + Lbest + "loc = " + VectorFunctions.print(loc));
//            }
//        }
    }

    public Point2 getLocation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
