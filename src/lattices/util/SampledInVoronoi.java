/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.util;

import Jama.Matrix;
import java.util.Enumeration;
import lattices.Lattice;
import lattices.LatticeAndNearestPointAlgorithm;
import lattices.NearestPointAlgorithmInterface;
import lattices.decoder.SphereDecoder;
import simulator.VectorFunctions;

/**
 * This returns points uniformly distributed in the Voronoi region.
 * This uses a sampling length and samples evenly along basis vectors
 * performing the nearest point algorithm on each point.
 * It may be that using uniformly distributed points using a random
 * number generator will work better than this.
 * @author Robby McKilliam
 */
public class SampledInVoronoi implements PointEnumerator{


    protected NearestPointAlgorithmInterface decoder;
    protected PointInParallelepiped ppoints;


    protected SampledInVoronoi() {}

    /**
     * Default to using the sphere decoder to compute nearest points
     * @param L is the lattice
     * @param samples is the number of samples used per dimension
     */
    public SampledInVoronoi(Lattice L, int samples){
        ppoints = new PointInParallelepiped(L, samples);
        decoder = new SphereDecoder(L);
    }

    /**
     * Using the nearest point algorithm supplied.
     * @param L is the lattice with included nearest point algorithm.
     * @param samples is the number of samples used per dimension
     */
    public SampledInVoronoi(LatticeAndNearestPointAlgorithm L, int samples){
        ppoints = new PointInParallelepiped(L, samples);
        decoder = L;
    }

    public boolean hasMoreElements() {
        return ppoints.hasMoreElements();
    }

    public Matrix nextElement() {
        return VectorFunctions.columnMatrix(nextElementDouble());
    }

    /**
     * @return return the next element as a double[] rather than a Matrix
     */
    public double[] nextElementDouble() {
        double[]  p = ppoints.nextElement().getColumnPackedCopy();
        decoder.nearestPoint(p);
        return  VectorFunctions.subtract(p, decoder.getLatticePoint());
    }

    public double percentageComplete() {
        return ppoints.percentageComplete();
    }
}
