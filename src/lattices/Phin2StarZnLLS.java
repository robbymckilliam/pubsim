/*
 * Phin2StarZnLLS.java
 *
 * Created on 12 August 2007, 21:11
 */

package lattices;

import simulator.*;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * Nearest point algorithm for the Phin2StarZnLLS lattice.
 * Works as a specialised version on the period estimation ZnLLS algorithm.
 * O(n^3log(n)).
 * UNDER CONSTRUCTION BY TIM!
 * 
 * @author Tim Mason and Robby McKilliam
 */
public class Phin2StarZnLLS extends Phin2Star implements NearestPointAlgorithmInterface{
    
    // dimension of the integer lattice we're searching in
    int N;
    
    // Current glue vector
    protected double[] glue;
    
    // Current translation for the line search
    protected double[] translate;
    
    // Beginning and end of the vector we're currently searching along
    protected double[] vstart, vend;
    
    // First and last lattice point in the Bresenham set we're currently
    // checking
    protected double[] lstart, lend;
    
    // Lattice points for the previous and current iteration within a line
    // search
    protected double[] lprev, lcurr;
    
    // Lattice point relative to vstart for the current iteration within a line 
    // search
    protected double[] z;
    
    // Distance along the line that we need to search
    protected int fmin, fmax;
    
    // g vector as defined in Robby's confirmation paper, Chapter 6
    protected double[] g;
    
    // Distance for current iteration, best distance found so far, best point
    // found so far.
    protected double dist;
    protected double bestdist;
    protected double[] bestpoint;
    
    protected double gtz, gtg, ztz;
    
    // Current distance along the line g
    protected double k;
    
    @Override
    public void setDimension(int n){
        this.n = n;
        N = n+2;
        u = new double[N];
        v = new double[N];
        glue = new double[N];
        translate = new double[N];
        vstart = new double[N];
        vend = new double[N];
        lstart = new double[N];
        lend = new double[N];
        lprev = new double[N];
        lcurr = new double[N];
        z = new double[N];
        g = new double[N];
        bestpoint = new double[N];
        g[0] = -(N/2); // Note: integer division contains implicit floor
        if (N % 2 == 0) {
            g[0] += 0.5;
        }
        for (int i = 1; i < N; i++) {
            g[i] = g[i-1] + 1;
        }
        fmin = 0;
        fmax = 1;
    }
    
    /** Find the nearest lattice point.  TIM, this is the function
     * you need to fill in! */
    @Override
    public void nearestPoint(double[] y) {
        if (y.length != N) {
            setDimension(y.length - 2);
        }
        
        bestdist = Double.POSITIVE_INFINITY;
        
        for (int i = 0; i < N; i++) {
            glueVector(i);
            for (int j = 0; j < N; j++) {
                translate[j] = y[j] + glue[j];
                
                vstart[j] = translate[j];
                vend[j] = translate[j] + fmax * g[j];
                
                lstart[j] = Math.round(vstart[j]);
                lend[j] = Math.round(vend[j]);
            }
            
            TreeMap crosses = new TreeMap();
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < Math.abs(lend[j] - lstart[j]); k++) {
                    // This shows how far along g relative to the
                    // translated beginning of g the voronoi boundaries occur
                    crosses.put(
                                  new Double(
                                    ((((2*k + 1) * Math.signum(g[j]))
                                     / 2) - (vstart[j] - lstart[j]))/g[j]
                                  ),
                                  new Integer(j)
                               );
                }
            }
            
            gtz = 0;
            gtg = 0;
            ztz = 0;
           
            for (int j = 0; j < N; j++) {
                z[j] = lstart[j] - vstart[j];
                gtz += g[j] * z[j];
                gtg += g[j] * g[j];
                ztz += z[j] * z[j];
            }
            k = gtz / gtg;
            // This is the first voronoi point in the line search, so the
            // projection onto g might be outside the line segment.  Check for
            // this.
            if (k < fmin) {
                k = fmin;
            }
            dist = k*k*gtg - 2*k*gtz + ztz;
            
            if (dist < bestdist) {               
                bestdist = dist;
                for (int j = 0; j < N; j++) {
                    // bestpoint is rounded at the very end of the function so
                    // that the rounding is only performed on the final best
                    // point found.
                    bestpoint[j] = z[j] + vstart[j] - glue[j];
                    v[j] = k*g[j] + vstart[j] - glue[j];
                }
            }
            
            Iterator valIter = crosses.values().iterator();
            while (valIter.hasNext()) {
                int nextDim = ((Integer)(valIter.next())).intValue();
                double sign = Math.signum(g[nextDim]);
                gtz += sign * g[nextDim];
                ztz += 2 * sign * z[nextDim] + 1;
                z[nextDim] += sign;
                
                k = gtz / gtg;
                // Need to make sure the projection isn't past the ends of
                // the line segment
                if (k < fmin) {
                    k = fmin;
                }
                if (k > fmax) {
                    k = fmax;
                }
                dist = k*k*gtg - 2*k*gtz + ztz;
                
                if (dist < bestdist) {
                    bestdist = dist;
                    for (int j = 0; j < N; j++) {
                        // See comment earlier regarding this line.
                        bestpoint[j] = z[j] + vstart[j] - glue[j];
                        v[j] = k*g[j] + vstart[j] - glue[j];
                    }
                }
            }
        }
        
        for (int i = 0; i < N; i++) {
            // This rounding is a source of considerable numerical error
            // in the presence of floating point noise if it's rounding
            // values with a fractional component of about 0.5, which is
            // the case for one glue vector for even N (and potentially
            // others for large enough N).
            //
            // bestpoint[i] = z[i] + vstart[i] - glue[i]:
            // z[i] + vstart[i] is always an integer and glue[i] is an
            // integer divided by N, so we'll try to round
            // z[i] + vstart[i] - glue[i] to the correct value (in a
            // maximum likelihood sense) first.
            bestpoint[i] = Math.round
                           (
                             N * (bestpoint[i])
                           ) / (double)N;
            u[i] = Math.round(bestpoint[i]);
        }
    }
    
    /** {@inheritDoc} */
    @Override
    public double volume(){ return 0;}
    
    /** 
     * Sets protected variable g to the glue
     * vector [i].  See SPLAG pp109.
     */
    protected void glueVector(double i){
        double j = N - i;
        for(int k = 0; k < j; k++)
            glue[k] = i/N;
        for(int k = (int)j; k < N; k++)
            glue[k] = -j/N;
    }
    
}
