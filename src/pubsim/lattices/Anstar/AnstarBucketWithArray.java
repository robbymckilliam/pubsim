/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.Anstar;

/**
 * This is a version of the bucket sort algorithm that uses an
 * (n+1) x (n+1) array to store the buckets.  This is o(n^2) memory
 * but should avoid the memory allocation slow down that occurs in
 * java.  This should give a reasonable impression of how fast the
 * algorithm would go if written in C with decent memory allocation
 * speed.
 * @author Robby McKilliam
 */
public class AnstarBucketWithArray extends Anstar {

    private int[][] buckets;
    private int[] bucketlen;
    private double[] z;

    @Override
    public void setDimension(int n) {
        this.n = n;
        // Allocate some space for arrays
        u = new double[n + 1];
        v = new double[n + 1];
        z = new double[n + 1];
        buckets = new int[n+1][n+1];
        bucketlen = new int[n+1];

    }

    @Override
    public void nearestPoint(double[] y) {
        if (n != y.length-1)
	    setDimension(y.length-1);
        
        //make sure that the buckets are empty!
        for(int i = 0; i < n + 1; i++)
            bucketlen[i] = 0;
        
        double a = 0, b = 0;
        for(int i = 0; i < n + 1; i++){
            z[i] = y[i] - Math.round(y[i]);
            int bi = n - (int)(Math.floor((n+1)*(z[i]+0.5)));
            buckets[bi][bucketlen[bi]] = i;
            bucketlen[bi]++;
            a += z[i];
            b += z[i] * z[i];
        }
        
        double D = b - a*a/(n+1);
        int m = 0;
        for(int i = 0; i < n+1; i++){
            double dist = b - a*a/(n+1);
            if(dist < D){
                D = dist;
                m = i;
            }
            for(int j = 0; j < bucketlen[i]; j++){
                int ind = buckets[i][j];
                a -= 1;
                b += -2*z[ind] + 1; 
            }
        }
        
        for(int i = 0; i < n + 1; i++)
            u[i] = Math.round(y[i]);
        
        for(int i = 0; i < m; i++){
            for(int j = 0; j < bucketlen[i]; j++){
                int ind = buckets[i][j];
                u[ind] += 1;
            }
        }
        
        project(u, v);
        
    }
}
