/*
 * AnstarGradientAccent.java
 *
 * Created on 9 December 2007, 12:49
 */

package lattices;

import simulator.VectorFunctions;

/**
 * Newton-Raphson version of the Anstar nearest point algorithm.
 * Uses a psuedo-periodogram function that can be shown to be related
 * to the Anstar nearest point algorithm.
 * <p>
 * It's likely that this will fail at the edges of the Voronoi region.
 * <p> 
 * This probably has O(n) running time.  It would be interesting
 * if similar functions exist for the Pna lattices.
 * @author Robby McKilliam
 */
public class AnstarGradientAccent extends Anstar 
        implements LatticeNearestPointAlgorithm{
    
    double[] lastv, fd;
    
    /**Max number of iterations for the Newton step */
    static final int MAX_ITER = 15;
    
    /**Finish variable for the Newton step */
    static final double EPSILON = 1e-7;
    
    public void setDimension(int n) {
	this.n = n;     
	//u = new double[n+1];
	v = new double[n+1];
        lastv = new double[n+1];
        fd = new double[n+1];
    }
    
    public void nearestPoint(double[] y) {
        
        project(y,v);
        
        int numIter = 0;
        while(VectorFunctions.distance_between(v,lastv) > EPSILON 
                && numIter <= MAX_ITER){
            System.arraycopy(v, 0, lastv, 0, n+1);
            numIter++;
            
            double sumc = 0.0, sums = 0.0;
            for(int i = 0; i < n+1; i++){
                sumc += Math.cos(2*Math.PI*v[i]);
                sums += Math.sin(2*Math.PI*v[i]);
            }
            
            for(int i = 0; i < n+1; i++){
                fd[i] = -4*Math.PI*Math.sin(2*Math.PI*v[i])*sumc
                        + 4*Math.PI*Math.cos(2*Math.PI*v[i])*sums;
            }
            
            project(fd, fd);
            
            for(int i = 0; i < n+1; i++)
                v[i] = v[i] - 0.5*fd[i];
            
        }
        
    }
    
}
