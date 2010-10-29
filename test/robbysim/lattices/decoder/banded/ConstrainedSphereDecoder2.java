/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.lattices.decoder.banded;

import Jama.Matrix;
import robbysim.lattices.Lattice;
import robbysim.lattices.decoder.GeneralNearestPointAlgorithm;
import robbysim.lattices.decoder.SphereDecoder;
import robbysim.VectorFunctions;

/**
 * This is another constrained sphere decoder.  Conceptually this
 * is more simple, but it's probably less flexible.
 * It's real use is just to test the other ConstrainedSphereDecoder.
 * The sphere radius works differently between this and the other one.
 * The distance here is the sphere radius of a smaller size decoder.
 * The other one includes the constraints in the sphere distance.
 * Because of this the other one is probably easier to use.
 * @author Robby McKilliam
 */
public class ConstrainedSphereDecoder2 extends SphereDecoder
        implements GeneralNearestPointAlgorithm{

    Double[] c;
    double[] ysub;
    double[] uret;
    Matrix M;

    /**
     * @param L The lattice to decode
     * @param radius Sphere radius
     * @param constraints Array of constraints
     */
    public ConstrainedSphereDecoder2(Lattice L,
            Double[] constraints, double radius){
        D = radius*radius + DELTA;
        c = constraints;

        //strip the matrix based on the constraints.
        M = L.getGeneratorMatrix();
        G = M.getMatrix(0, M.getRowDimension()-1, getNullIndices(c));
        m = G.getRowDimension();
        n = G.getColumnDimension();

        u = new double[M.getColumnDimension()];
        ut = new double[n];
        x = new double[m];
        yr = new double[n];
        ubest = new double[n];

        //compute the vector we want to subtract from y each time
        if(stripNulls(c).length > 0){
            Matrix Md = M.getMatrix(0, M.getRowDimension()-1, getNonNullIndices(c));
            ysub = VectorFunctions.matrixMultVector(Md, stripNulls(c));
        }else{
            ysub = new double[m];
        }
        //System.out.print(VectorFunctions.print(G));

        //CAREFULL!  This version of the sphere decoder requires R to
        //have positive diagonal entries.
        robbysim.QRDecomposition QR = new robbysim.QRDecomposition(G);
        R = QR.getR();
        Q = QR.getQ();

        System.out.println(VectorFunctions.print(R));
        System.out.println(VectorFunctions.print(Q));

    }

     @Override
    public void nearestPoint(double[] y) {
        if(m != y.length)
            throw new RuntimeException("Point y and Generator" +
                    " matrix are of different dimension!");

        //System.out.print(y.length);
        double[] yt = VectorFunctions.subtract(y, ysub);
        System.out.println(VectorFunctions.print(yt));

        //compute y in the triangular frame
        VectorFunctions.matrixMultVector(Q.transpose(), yt, yr);
        System.out.println(VectorFunctions.print(yr));

        //current element being decoded
        int k = n-1;

        //start the normal sphere decoder but
        decode(k, 0);

        //combine the constraints and u
        copyIntoConstraints(c, ubest, u);
        //compute nearest point
        VectorFunctions.matrixMultVector(M, u, x);

    }

     /**
      * Copy a vector of values into the nulls in the constraints.
      * The results is stored in u.
      */
    public static void copyIntoConstraints(Double[] c, double[] v, double[] u){
        int count = 0;
        for(int i = 0; i < c.length; i++){
            if(c[i]==null){
                u[i] = v[count];
                count++;
            }else{
                u[i] = c[i].doubleValue();
            }
        }
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

    /** Return the indices of this array that are non null */
    public static int[] getNullIndices(Object[] d){
        int[] r = new int[countNull(d)];
        int count = 0;
        for(int i = 0; i < d.length; i++){
            if(d[i] == null){
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

}
