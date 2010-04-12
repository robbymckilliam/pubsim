/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder;

import simulator.VectorFunctions;

/**
 * This is a sphere decoder that searches the 'planes' in a greedy
 * order.  This tends to go a little faster.  See Agrell et. al.
 * 'CLOSEST POINT SEARCH IN LATTICES'
 * @author Robby McKilliam
 */
public class SphereDecoderSchnorrEuchner extends SphereDecoder{

    @Override
    public void nearestPoint(double[] y) {
        if(m != y.length)
            throw new RuntimeException("Point y and Generator matrix are of different dimension!");

        //don't need to compute the Babai point.  Thsi strategy automattically computes
        //the Babai point.
        VectorFunctions.matrixMultVector(Qtrans, y, yr);
        D = Double.POSITIVE_INFINITY;

        //current element being decoded
        int k = n-1;

        decode(k, 0);

        //compute index u = Uuh so that Gu is nearest point
        VectorFunctions.matrixMultVector(U, ubest, u);

        //compute nearest point
        VectorFunctions.matrixMultVector(G, u, x);

    }

    @Override
    protected void decode(int k, double d){
        //return if this is already not the closest point
        if(d > D){
            return;
        }

        //compute the sum of R[k][k+i]*uh[k+i]'s
        //and the distance so far
        double rsum = 0.0;
        for(int i = k+1; i < n; i++ ){
            rsum += ut[i]*R.get(k, i);
        }


        //this is the first point to test
        ut[k] = Math.round((yr[k] - rsum)/R.get(k,k));

        //this update the ut[k] in the order of Schnorr and Euchner.
        double del = Math.signum( 
                simulator.Util.fracpart( (yr[k] - rsum)/R.get(k,k) )  );
        
        while( Math.abs(ut[k]*R.get(k,k) + rsum - yr[k]) <= Math.sqrt(D - d) ){
            double kd = R.get(k, k)*ut[k] + rsum - yr[k];
            double sumd = d + kd*kd;

            //if this is not the first element then recurse
            if( k > 0)
                decode(k-1, sumd);
            //otherwise check if this is the best point so far encounted
            //and update if required
            else{
                if(sumd <= D){
                    System.arraycopy(ut, 0, ubest, 0, n);
                    D = sumd;
                }
            }
            ut[k] += del;
            del = -Math.signum(del)*(Math.abs(del) + 1);
        }

    }

}
