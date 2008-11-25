/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder;

import lattices.Lattice;
import simulator.VectorFunctions;

/**
 * Sphere decoder that has a fixed sphere radius
 * calculated with the Babai point.
 * @author Robby McKilliam
 */
public class FixedSphereDecoder extends Babai
        implements GeneralNearestPointAlgorithm {


    @Override
    public void nearestPoint(double[] y) {
        if(m != y.length)
            throw new RuntimeException("Point y and Generator matrix are of different dimension!");
        
        computeBabaiPoint(y);
        
        //compute the Babai point in the triangular frame
        double[] xr = new double[m];
        VectorFunctions.matrixMultVector(R, u, xr);
        
        //compute the radius of the sphere we are decoding in
        double D = VectorFunctions.distance_between2(yr, xr);
        
    }


}
