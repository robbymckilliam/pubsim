/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.hex;

import java.util.Arrays;
import pubsim.Complex;
import static pubsim.VectorFunctions.fill;
import static pubsim.VectorFunctions.times;
import static pubsim.VectorFunctions.dot;
import static pubsim.VectorFunctions.magnitude2;

/**
 *
 * @author Robby McKilliam
 */
public class RadialLinesSorted extends LineHexReciever
        implements HexReciever {

    private final Complex[] x, y, z;
    private final int numL;

    private final DoubleAndPoint2AndIndex[] S;

    public RadialLinesSorted(int N, int scale){
        super(N, scale);
        numL = N*scale;
        x = new Complex[N];
        y = new Complex[N];
        z = new Complex[N];
        //allocate some memory for S.  It can never need more that r*r*N
        S = new DoubleAndPoint2AndIndex[scale*scale*N];
    }
    public void decode(double[] rreal, double[] rimag) {
        if( rreal.length != N )
            throw new RuntimeException("rreal and rimag are the wrong length.");

        pubsim.ComplexMatrix.toComplexArray(rreal, rimag, y);

        double Lopt = Double.NEGATIVE_INFINITY;
        double thetaopt = 0.0, ropt = 0.0;
        double thetastep = 2*Math.PI/(numL);

        //codeword for hex constellation point closest to origin
        double[] startcode = hex.decode(0.0,0.0);
        //hex constellation point closest to origin
        double[] starthex = hex.encode(startcode);

        for(double theta = 0.0; theta < 2*Math.PI; theta+=thetastep)
        //double theta = 0.0;
        {

            fill(x, new Complex(starthex[0], starthex[1]));
            times(y, new Complex(Math.cos(theta), Math.sin(theta)), z);
            Complex alpha = dot(y,x);
            double beta = magnitude2(x);

            int scount = 0;
            for(int n = 0; n < N; n++){
                Complex v = x[n];

                while(inScaledVor(v)){
                    DoubleAndPoint2AndIndex dp = nextHexangonalNearPoint(z[n].re(),
                            z[n].im(), v.re(), v.im());
                    dp.index = n;
                    Complex g = new Complex(dp.point.getX(), dp.point.getY());
                    v = v.plus(g);
                    S[scount] = dp;
                    scount++;
                }

            }

            Arrays.sort(S,0,scount-1);

            //test the point current point (it's at the origin)
            double L = alpha.abs2()/beta;
            //System.out.println("L = " + L);
            if(L > Lopt){
                Lopt = L;
                thetaopt = theta;
                ropt = 0.0;
            }

            //compute all the points intersecting the line.
            for( int t = 0; t < scount; t++ ){

                DoubleAndPoint2AndIndex dp = S[t];
                int n = dp.index.intValue();
                double r = dp.value.doubleValue();
                Complex g = new Complex(dp.point.getX(), dp.point.getY());

                alpha = alpha.plus(y[n].conjugate().times(g));
                beta = beta + 2*(x[n].conjugate().times(g)).re() + 1;
                x[n] = x[n].plus(g);

                if( !inScaledVor(x[n]) ) break;

                //test if this was the best point.  Save theta and d if it was
                L = alpha.abs2()/beta;
                if(L > Lopt){
                    Lopt = L;
                    thetaopt = theta;
                    //set dopt half way between this boundary and the next.
                    //this garautees it is within this region.
                    //ropt = (r + S[t+1].value)/2.0;
                    ropt = r + 0.0000001;
                }
            }
        }


        times(y, new Complex(ropt*Math.cos(thetaopt), ropt*Math.sin(thetaopt)), z);

        for(int n = 0; n < N; n++){
            double[] cpoint = hex.decode(z[n]);
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

