/*
 * NormalisedZnLLS.java
 *
 * Created on 17 July 2007, 07:44
 */

package robbysim.snpe;

import robbysim.lattices.Anstar.AnstarVaughan;

/**
 * ZnLLS with the unormalised log-likelihood function, ie. it finds the
 * closest point to the line rather than the point of minimal angle.
 * @author Robby McKilliam
 */
public class NormalisedZnLLS extends ZnLLS implements PRIEstimator {

    public NormalisedZnLLS(int N) {super(N);}

    @Override
    public void estimate(double[] y, double Tmin, double Tmax){
        double fmin = 1/Tmax; double fmax = 1/Tmin;

        AnstarVaughan.project(y, z);

        double bestf = 0.0;
        double mindist = Double.POSITIVE_INFINITY;

        for(int i = 0; i <=n ; i++){
            map.clear();
            glueVector(i);

            //setup map and variables for this glue vector
            double ztz = 0.0, ztv = 0.0, vtv = 0.0;
            for(int j=0; j<=n; j++){
                v[j] = Math.round(fmin*z[j] - g[j]) + g[j];
                map.put(new Double((Math.signum(z[j])*0.5 + v[j])/z[j]), new Integer(j));
                ztz += z[j]*z[j];
                ztv += z[j]*v[j];
                vtv += v[j]*v[j];
            }

            //double f = vtv/ztv;
            double f = ztv / ztz;
            //line search loop
            while(f < fmax){

                double dist = f*f*ztz - 2*f*ztv + vtv;

                if(dist < mindist /*&& f > fmin && f < fmax*/){
                    mindist = dist;
                    bestf = f;
                }

                Double key = ((Double) map.firstKey());
                int k = ((Integer)map.get(key)).intValue();
                double d = Math.signum(z[k]);
                v[k] += d;
                map.remove(key);
                map.put(new Double((d*0.5 + v[k])/z[k]), new Integer(k));

                ztv += d*z[k];
                vtv += 2*d*(v[k]-d) + 1;

                //update f
                //f = vtv/ztv;
                f = ztv / ztz;
            }
        }
        That = 1.0/bestf;

        //now compute the phase estimate
        phat = phasestor.getPhase(y, That);


    }
    
}
