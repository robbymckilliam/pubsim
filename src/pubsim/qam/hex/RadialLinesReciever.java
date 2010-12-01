/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.hex;

import java.util.Map.Entry;
import java.util.TreeMap;
import pubsim.lattices.Hexagonal;
import pubsim.Point2;
import pubsim.Complex;
import pubsim.VectorFunctions;
import static pubsim.qam.NonCoherentReceiver.createPlane;
import static pubsim.qam.NonCoherentReceiver.toRealImag;

/**
 * Noncoherent reciever for Hexagonal QAM.  Assumes hexagonal constellation
 * is a Voronoi code.
 * @author Robby McKilliam
 */
public class RadialLinesReciever extends LineHexReciever
        implements HexReciever {

    private final Point2[] x;
    private final int numL;

    //these get used during decode operation.
    private final Hexagonal hexnp = new Hexagonal();

    public RadialLinesReciever(int N, int scale){
        super(N, scale);
        numL = scale;
        x = new Point2[N];
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
                x[n] = new Point2(starthex[0], starthex[1]);

                //compute likelihood variables
                ar += starthex[0]*rreal[n] + starthex[1]*rimag[n];
                ai += starthex[0]*rimag[n] - starthex[1]*rreal[n];
                b += x[n].magnitude2();

            }

            //make rmax go out to the boundary
            rmax *= hex.getScale();

            //test the point current point (it's at the origin)
            double L = (ar*ar + ai*ai)/b;
            //System.out.println("L = " + L);
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

                //update likelihood variables
                double pr = p.getX();
                double pi = p.getY();
                ar += pr*rreal[n] + pi*rimag[n];
                ai += pr*rimag[n] - pi*rreal[n];
                b += 2*pr*x[n].getX() + 2*pi*x[n].getY() + pr*pr + pi*pi;

                //update x
                x[n].plusEquals(new Point2(pr, pi));

                //compute next intersected boundary
                DoubleAndPoint2AndIndex dpnext = nextHexangonalNearPoint(d1[n],
                        d2[n], x[n].getX(), x[n].getY());
                dpnext.index = n;
                //If this is within the outer Voronoi code boundary add the
                //next point to the map
                double nextx = (x[n].getX() + dpnext.point.getX())/hex.getScale();
                double nexty = (x[n].getY() + dpnext.point.getY())/hex.getScale();
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
                    ropt = r + 0.00001;
                    //if(map.isEmpty()) ropt = r + 0.00001;
                    //else ropt = r + (map.firstEntry().getValue().value - r)/2.0;
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
//        System.out.println();
//////
////        System.out.println();
//        System.out.println(thetaopt);
//        System.out.println(ropt);
////        System.out.println(Lopt);
//        System.out.println();

    }


}
