/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.decoder.nearset;

/**
 * Interface for optimisation problems with lattices and surfaces such
 * as lines, planes, surfaces, etc.
 * @author Robby McKilliam
 */
public interface NearSetDecoder<T> {

    
    double[] getLatticePoint();

    /**
     * T is the parameter/s coresponding to the nearest point on the surface.
     * For a line, T is likely to be a Double.
     * In general T is likely to be a collection of Doubles.
    */
    T getLambda();

}
