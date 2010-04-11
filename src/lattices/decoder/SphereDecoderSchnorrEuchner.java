/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder;

/**
 * This is a sphere decoder that searches the 'planes' in a greedy
 * order.  This tends to go a little faster.  See Agrell et. al.
 * 'CLOSEST POINT SEARCH IN LATTICES'
 * @author Robby McKilliam
 */
public class SphereDecoderSchnorrEuchner extends SphereDecoder{

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

        double del = 1;
        while( Math.abs(ut[k]*R.get(k,k) + rsum - yr[k]) <= Math.sqrt(D - d) ||
               Math.abs((ut[k] + del)*R.get(k,k) + rsum - yr[k]) <= Math.sqrt(D - d)){
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
