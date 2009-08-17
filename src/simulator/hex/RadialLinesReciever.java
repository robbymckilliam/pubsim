/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.hex;

import java.util.Map.Entry;
import java.util.TreeMap;
import lattices.Hexagonal;
import simulator.Point2;
import simulator.Complex;
import simulator.VectorFunctions;
import static simulator.qam.NonCoherentReceiver.createPlane;
import static simulator.qam.NonCoherentReceiver.toRealImag;

/**
 * Noncoherent reciever for Hexagonal QAM.  Assumes hexagonal constellation
 * is a Voronoi code.
 * @author Robby McKilliam
 */
public class RadialLinesReciever implements HexReciever {

    protected final double[] ur, ui;
    protected final Complex[] x;
    protected final double[] y1, y2;
    protected final double[] d1, d2;
    protected final int N;
    protected final HexagonalCode hex;
    protected final int numL = 4;

    //these get used during decode operation.
    private final Hexagonal hexnp = new Hexagonal();

    public RadialLinesReciever(int N, HexagonalCode hex){
        this.hex = hex;
        ur = new double[N];
        ui = new double[N];
        y1 = new double[2*N];
        y2 = new double[2*N];
        d1 = new double[N];
        d2 = new double[N];
        x = new Complex[N];
        this.N = N;
    }

    public RadialLinesReciever(int N, int scale){
        this.hex = new HexagonalCode(scale);
        ur = new double[N];
        ui = new double[N];
        y1 = new double[2*N];
        y2 = new double[2*N];
        d1 = new double[N];
        d2 = new double[N];
        x = new Complex[N];
        this.N = N;
    }

    public void decode(double[] rreal, double[] rimag) {
        if( rreal.length != N )
            throw new RuntimeException("rreal and rimag are the wrong length.");

        //get the plane that we are searching in.  Stored in y1, y2.
        createPlane(rreal, rimag, y1, y2);

        double Lopt = Double.NEGATIVE_INFINITY;
        double thetaopt = 0.0, ropt = 0.0;
        double thetastep = 2*Math.PI/(N*numL);

        //codeword for hex constellation point closest to origin
        double[] startcode = hex.decode(0.0,0.0);
        //hex constellation point closest to origin
        double[] starthex = hex.encode(startcode);

        for(double theta = 0.0; theta < 2*Math.PI; theta+=thetastep)
        //double theta = 0.0;
        {

            //construct the sorted map for this line.
            TreeMap<Double, DoubleAndPoint2AndIndex> map
                    = new TreeMap<Double, DoubleAndPoint2AndIndex>();

            double ar = 0, ai = 0, b = 0;
            //largest value of r allowed.
            double rmax = Double.POSITIVE_INFINITY;

            double costheta = Math.cos(theta);
            double sintheta = Math.sin(theta);
            for(int n = 0; n < N; n++){
                //calculate the search line
                d1[n] = costheta*y1[2*n] + sintheta*y2[2*n];
                d2[n] = costheta*y1[2*n+1] + sintheta*y2[2*n+1];

                rmax = Math.min(rmax, nextHexangonalNearPoint(d1[n],
                            d2[n], 0.0, 0.0).value);

                //insert points into map.
                if(d1[n] != 0.0 || d2[n] != 0.0 ){
                    DoubleAndPoint2AndIndex dp = nextHexangonalNearPoint(d1[n],
                            d2[n], starthex[0], starthex[1]);
                    dp.index = n;
                    map.put(dp.value, dp);
                    //System.out.println(dp);
                }

                //array of Complex numbers representing constellation points.
                x[n] = new Complex(starthex[0], starthex[1]);

                //compute likelihood variables
                ar += starthex[0]*rreal[n] + starthex[1]*rimag[n];
                ai += starthex[0]*rimag[n] - starthex[1]*rreal[n];
                b += x[n].abs2();

            }

            //make rmax go out to the boundary
            rmax *= hex.getScale();

            System.out.println();

            //test the point current point (it's at the origin)
            double L = (ar*ar + ai*ai)/b;
            if(L > Lopt){
                Lopt = L;
                thetaopt = theta;
                ropt = 0.0;
            }
            
            //compute all the points intersecting the line.
            while(!map.isEmpty()){

                DoubleAndPoint2AndIndex dp = map.pollFirstEntry().getValue();
                //System.out.println(dp);
                int n = dp.index.intValue();
                double r = dp.value.doubleValue();
                Point2 p = dp.point;

                //System.out.println(dp);

                //update likelihood variables
                double pr = p.getX();
                double pi = p.getY();
                ar += pr*rreal[n] + pi*rimag[n];
                ai += pr*rimag[n] - pi*rreal[n];
                b += 2*pr*rreal[n] + 2*pi*rimag[n] + pr*pr + pi*pi;

                //update x
                //System.out.println(VectorFunctions.print(x));
                x[n] = x[n].plus(new Complex(pr, pi));

                //compute next intersected boundary
                DoubleAndPoint2AndIndex dpnext = nextHexangonalNearPoint(d1[n],
                        d2[n], x[n].re(), x[n].im());
                dpnext.index = n;
                //If this is within the outer Voronoi code boundary add the
                //next point to the map
                double nextx = (x[n].re() + dpnext.point.getX())/hex.getScale();
                double nexty = (x[n].im() + dpnext.point.getY())/hex.getScale();
                hexnp.nearestPoint(nextx, nexty);
                if(dpnext.value < rmax
                    && VectorFunctions.sum2(hexnp.getLatticePoint()) <= 0.01){
                        map.put(dpnext.value, dpnext);
                }

                //test if this was the best point.  Save theta and d if it was
                L = (ar*ar + ai*ai)/b;
                if(L > Lopt){
                    Lopt = L;
                    thetaopt = theta;
                    //set dopt half way between this boundary and the next.
                    //this garautees it is within this region.
                    ropt = r + 0.00000001;
                    //if(map.isEmpty()) dopt = d + 0.00000001;
                    //else dopt = (d + map.firstEntry().getValue().value)/2.0;
                }
                          
            }

        }

        //reconstruct best point from angle and magnitude
        double costheta = Math.cos(thetaopt);
        double sintheta = Math.sin(thetaopt);
        for(int n = 0; n < N; n++){
            double dd1 = ropt*(costheta*y1[2*n] + sintheta*y2[2*n]);
            double dd2 = ropt*(costheta*y1[2*n+1] + sintheta*y2[2*n+1]);
            double[] cpoint = hex.decode(dd1,dd2);
            ur[n] = cpoint[0];
            ui[n] = cpoint[1];
        }

        System.out.println(thetaopt);
        System.out.println(ropt);
        System.out.println(Lopt);

    }

    /**
     * Array containing 6 boundaries of a hexagon.  This is the position
     * of the relavant vectors of the hexagonal lattice
     */
    protected static final Point2[] hexagonalBoundaries = {
                        new Point2(1.0,0.0),
                        new Point2(-1.0,0.0),
                        new Point2( 0.5, Math.sqrt(3.0)/2.0 ),
                        new Point2( -0.5, Math.sqrt(3.0)/2.0 ),
                        new Point2( -0.5, -Math.sqrt(3.0)/2.0 ),
                        new Point2( 0.5, -Math.sqrt(3.0)/2.0 )
                    };

    /** Class to contain an Integer index a Double and a Point2 */
    public static class DoubleAndPoint2AndIndex{
        public Integer index;
        public Double value;
        public Point2 point;

        @Override
        public String toString() {
            return index + ", " + value + ", " + point;
        }
    }

    /**
     * Compute the next crossed point in the hexagonal code.  Line is d1, d2
     * and current hexagonal code point is c1, c2.
     */
    protected static DoubleAndPoint2AndIndex nextHexangonalNearPoint(
            double d1, double d2,
            double c1, double c2){

        Point2 c = new Point2(c1, c2);
        Point2 d = new Point2(d1, d2);

        double rmin = Point2.dot(c,d)/Point2.dot(d,d);

        DoubleAndPoint2AndIndex dp = new DoubleAndPoint2AndIndex();
        dp.value = Double.POSITIVE_INFINITY;
        for(int n = 0; n < hexagonalBoundaries.length; n++){
            Point2 v = hexagonalBoundaries[n];
            double cv = Point2.dot(c, v);
            double dv = Point2.dot(d, v);
            double vv = Point2.dot(v, v);
            double rnextp = (cv + vv)/dv;
            double r = (cv + vv/2.0)/dv;
            if(rnextp > rmin && r < dp.value){
                dp.value = r;
                dp.point = v;
            }
        }
        return dp;       
    }


    public double[] getReal() {
        return ur;
    }

    public double[] getImag() {
        return ui;
    }



}
