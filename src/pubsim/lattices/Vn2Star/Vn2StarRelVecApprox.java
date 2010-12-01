/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.Vn2Star;

import java.util.Arrays;
import pubsim.lattices.Anstar.Anstar;
import pubsim.lattices.Anstar.AnstarBucketVaughan;
import pubsim.IndexedDouble;
import pubsim.VectorFunctions;

/**
 * An approximate algorithm based on greedily searching relavant vectors.
 * This does not work very well, which is not really that surprising!
 * @author Robby McKilliam
 */
public class Vn2StarRelVecApprox extends Vn2Star {

    Anstar anstar;

    double[] yp, ut, yt;

    private IndexedDouble[] z;

    @Override
    public void setDimension(int n){
        this.n = n;
        u = new double[n+2];
        ut = new double[n+2];
        v = new double[n+2];
        yp = new double[n+2];
        yt = new double[n+2];
        z = new IndexedDouble[n+2];
        for(int i = 0; i < n + 2; i++) z[i] = new IndexedDouble();
        anstar = new AnstarBucketVaughan(n+1);
    }

    public void nearestPoint(double[] y) {

        project(y,yp);
        anstar.nearestPoint(yp);
        for(int i = 0; i < n+2; i++){
            yt[i] = yp[i] - anstar.getLatticePoint()[i];
        }
        project(yt,yt);
        for(int i = 0; i < n+2; i++){
            z[i].index = i;
            z[i].value = yt[i];
        }

        //sorts z in increasing order.
        Arrays.sort(z);

        //System.out.println(VectorFunctions.print(z));

        //first implementation is brute force, there is no smart update
        double D = VectorFunctions.sum2(yt);
        //System.out.println(D);
        System.arraycopy(anstar.getIndex(), 0, u, 0, u.length);
        for(int j = 0; j < 10; j++){
            //System.out.println("run #" + j);
            System.arraycopy(u, 0, ut, 0, ut.length);
            for(int i = 0; i < n+1; i++){
                ut[z[n+1-i].index] += 1;
                project(ut, yt);
                double d = VectorFunctions.distance_between2(yp, yt);
                if(d < D){
                    //System.out.println("closer! " + d );
                    D = d;
                    System.arraycopy(ut, 0, u, 0, u.length);
                }
            }
            project(u, yt);
            for(int i = 0; i < n+2; i++){
                yt[i] = yp[i] - yt[i];
                z[i].index = i;
                z[i].value = yt[i];
            }
            //sorts z in increasing order.
            Arrays.sort(z);

        }

        project(u, v);


    }


}
