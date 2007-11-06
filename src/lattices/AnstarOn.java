/*
 * AnstarOn.java
 *
 * Created on 7 July 2007, 09:21
 */

package lattices;

/**
 * This is just a template for testing other ways of 
 * solving the An* nearest point problem.  A test 
 * interface already exists for this class to make
 * things easy.
 * @author Robby McKilliam
 */
public class AnstarOn extends Anstar implements LatticeNearestPointAlgorithm{
   
    
    /** Overided the standard nearest point algorithm */
    public void nearestPoint(double[] y) {
        
        /*
        project(y, x);
        //x = y.clone();
        
        //step 1, round
        for(int i = 0; i <= n; i++){
            u[i] = Math.round(x[i]);
            x[i] -= u[i];
        }
        
        //step 2, find mean bearing
        double cs = 0;
        double ss = 0;
        for(int i = 0; i <= n; i++){
            cs += Math.cos(2*Math.PI*x[i]);
            ss += Math.sin(2*Math.PI*x[i]);
        }
        double phi = Math.atan2(ss,cs)/(2*Math.PI);
        
        //step 3, adjust nearest lattice point
        for(int i = 0; i <= n; i++){
            u[i] += Math.round(x[i] - phi);
        }
        
        //calculate Qu, the nearest lattice point
        project(u,v);
         */
        
        /*
        //check this point
        for(int i = 0; i <= n; i++)
            u[i] = Math.round(y[i]);
        project(u,v);
        project(y,x);
        double dist = VectorFunctions.distance_between(v,x);
        
        System.out.println("v1 = " + VectorFunctions.print(v) + ", dist1 = " + dist);
        
        //check this point +0.5
        for(int i = 0; i <= n; i++){
            y[i] +=  0.5;
            u[i] = Math.round(y[i]);
        }
        project(u,v);
        project(y,x); 
        double dist2 = VectorFunctions.distance_between(v,x);
        
        System.out.println("v1 = " + VectorFunctions.print(v) + ", dist2 = " + dist2);
        
        if( dist < dist2){
            for(int i = 0; i <= n; i++){
                y[i] -= 0.5;
                u[i] = Math.round(y[i]);
            }
            project(u,v);  
        }
        */
        
        //absurdly simple idea from Tim Veitch.  It makes some
        //sense geometrically, but it doesn't work.  There might
        //however be a way of adjusting this so that it does work
        for(int i = 0; i <= n; i++){
            u[i] = Math.round(y[i]);
            x[i] = y[i] - u[i];
            v[i] = u[i] + Math.round(-x[0] + x[i]);
        }
        
        project(v,v);
        
    }
    
        
        
    
}
