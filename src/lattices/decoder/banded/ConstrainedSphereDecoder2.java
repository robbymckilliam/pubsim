/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder.banded;

import Jama.Matrix;
import lattices.Lattice;
import lattices.decoder.GeneralNearestPointAlgorithm;
import lattices.decoder.SphereDecoder;
import simulator.VectorFunctions;

/**
 * This is another constrained sphere decoder.  It is a more simple
 * (and sensible) way to do this.
 * @author Robby McKilliam
 */
public class ConstrainedSphereDecoder2 extends SphereDecoder
        implements GeneralNearestPointAlgorithm{

    Double[] c;
    double[] ysub;

    /**
     * @param L The lattice to decode
     * @param radius Sphere radius
     * @param constraints Array of constraints
     */
    public ConstrainedSphereDecoder2(Lattice L,
            Double[] constraints, double radius){
        D = radius*radius + DELTA;
        c = constraints;

        u = new double[n];
        x = new double[m];
        yr = new double[n];
        ubest = new double[n];
        ysub = new double[n];

        //strip the matrix based on the constraints.
        Matrix M = L.getGeneratorMatrix();
        G = M.getMatrix(0, M.getRowDimension()-1, getNonNullIndices(c));
        m = G.getRowDimension();
        n = G.getColumnDimension();

        //CAREFULL!  This version of the sphere decoder requires R to
        //have positive diagonal entries.
        simulator.QRDecomposition QR = new simulator.QRDecomposition(G);
        R = QR.getR();
        Q = QR.getQ();

    }

    /** Return the indices of this array that are non null */
    public static int[] getNonNullIndices(Object[] d){
        int[] r = new int[countNonNull(d)];
        int count = 0;
        for(int i = 0; i < d.length; i++){
            if(d[i] != null){
                r[count] = i;
                count++;
            }
        }
        return r;
    }


    /**
     * Strips the nulls from a Double[] and returns a compacted
     * vector with only the non null elements
     */
    public static double[] stripNulls(Double[] d){
        double[] r = new double[countNonNull(d)];
        int count = 0;
        for(int i = 0; i < d.length; i++){
            if(d[i] != null){
                r[count] = d[i].doubleValue();
                count++;
            }
        }
        return r;
    }

    /** Returns the number of nulls in an array */
    public static int countNull(Object[] c){
        int count = 0;
        for(int i = 0; i < c.length; i++){
            if(c[i]==null) count++;
        }
        return count;
    }

    /** Returns the number of non nulls in an array */
    public static int countNonNull(Object[] c){
        return c.length - countNull(c);
    }

    @Override
    public void nearestPoint(double[] y) {
        if(m != y.length)
            throw new RuntimeException("Point y and Generator" +
                    " matrix are of different dimension!");

        //compute y in the triangular frame
        VectorFunctions.matrixMultVector(Q.transpose(), y, yr);

        //current element being decoded
        int k = n-1;

        decode(k, 0);

        //compute nearest point
        VectorFunctions.matrixMultVector(G, ubest, x);

    }

}
