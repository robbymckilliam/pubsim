/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.decoder;

import pubsim.lattices.Lattice;
import pubsim.VectorFunctions;

/**
 * Returns the kissing number of a lattice.  This finds all the short vector
 * by sphere decoding.  It is exponential time, so you can only run it
 * for small dimensions, less than 60 or so.
 * @author Robby McKilliam
 */
public class KissingNumber {

    protected final ModSphereDecoder ssd;
    protected long kissingNumber;

    public KissingNumber(Lattice L){

        ShortVectorSphereDecoded sv = new ShortVectorSphereDecoded(L);
        double D = VectorFunctions.sum2(sv.getShortestVector());

        ssd = new ModSphereDecoder(L);
        ssd.countVectorsShorterThan(D);

    }

    public long kissingNumber(){
        return kissingNumber;
    }

    protected class ModSphereDecoder extends SphereDecoder{

        public ModSphereDecoder(){
            super();
        }

        public ModSphereDecoder(Lattice L){
            super(L);
        }

        public void countVectorsShorterThan(double d){

            //compute the radius squared of the sphere we are decoding in.
            //Add DELTA to avoid numerical error causing the
            //Babai point to be rejected.
            D = d + DELTA;

            kissingNumber = 0;

            //current element being decoded
            int k = n-1;

            decode(k, 0);
        }

        /**
         * Recursive decode function to test nearest plane
         * for a particular dimension/element
         */
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

            //set least possible ut[k]
            ut[k] = Math.ceil((-Math.sqrt(D - d) + yr[k] - rsum)/R.get(k,k));

            while(ut[k] <= (Math.sqrt(D - d) + yr[k] - rsum)/R.get(k,k) ){
                double kd = R.get(k, k)*ut[k] + rsum - yr[k];
                double sumd = d + kd*kd;

                //if this is not the first element then recurse
                if( k > 0)
                    decode(k-1, sumd);
                //otherwise check if this is the best point so far encounted
                //and update if required.  This is modified so that it doesn't
                else{
                    if(sumd <= D && sumd > DELTA){
                        kissingNumber++;
                        //System.out.println(VectorFunctions.print(VectorFunctions.matrixMultVector(G.times(U), ut)));
                        System.out.println(VectorFunctions.print(VectorFunctions.matrixMultVector(U, ut)));
                    }
                }
                ut[k]++;
            }

        }


    }

}
