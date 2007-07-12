/*
 * AnstarOn.java
 *
 * Created on 7 July 2007, 09:21
 */

package simulator;

/**
 * This is an AnStar nearestPoint implementation that runs
 * in o(n).  Testing at the moment, I only believe strongly
 * that it will work, but I not certain.
 * This does not work, there is a difference between teh vector
 * an the least squares (in angle) estimate!
 * @author Robby McKilliam
 */
public class AnstarOn extends Anstar{
   
    
    /** Overided the standard nearest point algorithm */
    void nearestPoint(double[] y) {
        
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
        
    }
    
        
        
    
}
