/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.decoder.nearset;

import java.util.Map.Entry;
import java.util.TreeMap;
import pubsim.lattices.Dn;
//import simulator.IntegerPair;

/**
 * Computes the nearest point to a line in the Dn lattice.
 * I have commented out chunks of this for compiler reasons.  Seems
 * the IntegerPair class got missed in subversion.
 * @author Robby McKilliam
 */
public class DnLine implements NearSetDecoder<Double>{

    protected final double[] u;
    protected double lambda;

    /**
     * Locate nearest lattice point in Zn to line ry + c.
     * @param y line direction vector
     * @param c line translation
     * @param rmin smallest parameter
     * @param rmax largest parameter
     */
    public DnLine(double[] y, double[] c, double rmin, double rmax){
        if(y.length != c.length)
            throw new RuntimeException("y and c must be the same length");

        final int N = y.length;
        u = new double[N];
        TreeMap map = new TreeMap();

        //dot product variables
        double ztz = 0, yty = 0, zty = 0;

        //compute the first point in the lattice
        double[] yp = new double[N];
        for(int n = 0; n < N; n++) yp[n] = rmin*y[n] + c[n];
        Dn lattice = new Dn(N);
        lattice.nearestPoint(yp);
        double[] ut = lattice.getLatticePoint();

          //initialise dot products and sorted map.
        for(int n = 0; n < N; n++){
            double z = ut[n] - c[n];
            ztz += z*z;
            zty += z*y[n];
            yty += y[n]*y[n];
        }

        //array to store all the crossings
        Double[] crossing_array = new Double[4*N*N];

        //construct the map of sorted boundary crossings
//        for(int i = 0; i < N; i++){
//            for(int j = i+1; j < N; j++){
//                addCrossingToMap(i, j, c, ut, y, crossing_array, map, 1, 1);
//                addCrossingToMap(i, j, c, ut, y, crossing_array, map, 1, -1);
//                addCrossingToMap(i, j, c, ut, y, crossing_array, map, -1, 1);
//                addCrossingToMap(i, j, c, ut, y, crossing_array, map, -1, -1);
//            }
//        }

                //parameter of the first point.
        double r = zty/yty;
        lambda = r;

        double Dmin = Double.POSITIVE_INFINITY;
        while(r < rmax){

            //test current points distance
            double D = r*r*yty - 2*r*zty + ztz;
            if( D < Dmin ){
                lambda = r;
                Dmin = D;
            }

//            //remove and return enrty corresponding to next transition.
//            Entry<Double, IntegerPairAndSigns> entry = map.pollFirstEntry();
//            IntegerPairAndSigns s = entry.getValue();
//
//            double zi = ut[s.i] - c[s.i];
//            double zj = ut[s.j] - c[s.j];
//            zty += s.s1*y[s.i] + s.s2*y[s.j];
//            ztz += 2*( s.s1*zi + s.s2*zj ) + 2;
//            ut[s.i] += s.s1;
//            ut[s.j] += s.s2;
//
//            //update all i elements in map
//            for(int i = 0; i < N; i++){
//                replaceCrossingInMap(i, s.j, map, c, ut, y, crossing_array, 1, 1);
//                replaceCrossingInMap(i, s.j, map, c, ut, y, crossing_array, 1, -1);
//                replaceCrossingInMap(i, s.j, map, c, ut, y, crossing_array, -1, 1);
//                replaceCrossingInMap(i, s.j, map, c, ut, y, crossing_array, -1, -1);
//            }
//
//            //update all i elements in map
//            for(int j = s.i+1; j < N; j++){
//                replaceCrossingInMap(s.i, j, map, c, ut, y, crossing_array, 1, 1);
//                replaceCrossingInMap(s.i, j, map, c, ut, y, crossing_array, 1, -1);
//                replaceCrossingInMap(s.i, j, map, c, ut, y, crossing_array, -1, 1);
//                replaceCrossingInMap(s.i, j, map, c, ut, y, crossing_array, -1, -1);
//            }

            //update r
            r = zty/yty;
        }


    }

//    private final void addCrossingToMap(int i, int j, double[] c, double[] ut,
//            double[] y, Double[] crossing_array, TreeMap map,
//            int s1, int s2) {
//        IntegerPairAndSigns s = new IntegerPairAndSigns(i, j, s1, s2);
//        Double l = (1 - ( s1*(c[i] - ut[i]) + s2*(c[j] - ut[j]) ) ) / (s1*y[i] + s2*y[j]);
//        crossing_array[arrayIndex(s)] = l;
//        if (l > 0) {
//            map.put(l, s);
//        }
//    }
//
//    /** Get a unique index into the crossing array from integer and signs */
//    private final int arrayIndex(int i, int j, int s1, int s2){
//        int index = i*j;
//
//        if(s1==1 && s2==1) index += 0;
//        else if(s1==1 && s2==-1) index += 1;
//        else if(s1==-1 && s2==1) index += 2;
//        else index += 3;
//
//        return index;
//    }
//
//    /** Get a unique index into the crossing array from integer and signs */
//    private final int arrayIndex(IntegerPairAndSigns s){
//        return arrayIndex(s.i, s.j, s.s1, s.s2);
//    }
//
//
    public double[] getLatticePoint() {
        return u;
    }

    public Double getLambda() {
        return lambda;
    }
//
//    //** Class to contain transition indicies and directions for Dn lattice */
//    public static class IntegerPairAndSigns extends IntegerPair{
//        public final int s1, s2;
//        public IntegerPairAndSigns(int i, int j, int s1, int s2){
//            super(i, j);
//            this.s1 = s1;
//            this.s2 = s2;
//        }
//    }

//    private void replaceCrossingInMap(int i, int j, TreeMap map, double[] c,
//            double[] ut, double[] y, Double[] crossing_array,
//            int s1, int s2) {
//        Double l = crossing_array[arrayIndex(i, j, s1, s2)];
//        if (l > 0) {
//            map.remove(l);
//            addCrossingToMap(i, j, c, ut, y, crossing_array, map, s1, s2);
//        }
//    }

}
