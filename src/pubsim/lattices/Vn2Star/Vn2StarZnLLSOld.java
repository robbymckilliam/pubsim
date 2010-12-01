/*
 * Vn2StarZnLLSOld.java
 *
 * Created on 12 August 2007, 21:11
 */

package pubsim.lattices.Vn2Star;

import java.util.Iterator;
import java.util.TreeMap;
import pubsim.lattices.NearestPointAlgorithm;

/**
 * Nearest point algorithm for the Vn2StarZnLLSOld lattice.
 * Works as a specialised version on the period estimation ZnLLS algorithm.
 * O(n^3log(n)).
 *
 * This is an older version.  It's recommended that you use Phin2StarZnLLS
 * 
 * @author Tim Mason and Robby McKilliam
 */
public class Vn2StarZnLLSOld extends Vn2Star implements NearestPointAlgorithm{
    
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
    protected double fmin, fmax;
    
    // g vector as defined in Robby's confirmation paper, Chapter 6
    protected double[] g;
    
    // Distance for current iteration, best distance found so far, and the glue
    // vector number and k value associated with the best point found.
    protected double dist;
    protected double bestdist;
    protected int bestGlue;
    protected double bestk;
    
    protected double gtz, gtg, ztz;
    
    // Current distance along the line g
    protected double k;
    
    public Vn2StarZnLLSOld() {
        super();
        
        // Distance along g to search.  This is equivalent to searching
        // frequencies from 0 to -fs/2 and then from fs/2 to 0.  (In terms of
        // the z domain, think of searching from 1 clockwise around the unit
        // circle once.)
        // This has two implications:
        // 1) The estimated frequency can be determined directly from the
        //    distance along the line segment that the projection of the nearest
        //    point is found.  Im other words, if the projection of the nearest 
        //    point found onto translate + g is at translate + k*g, then k
        //    uniquely determines the estimated frequency between -fs/2 and
        //    fs/2.
        // 2) If some bounds are known on the frequency to be estimated, these
        //    bounds correspond directly to bounds on the line segment to be
        //    searched.  Eg, if the frequency is known to be between 0.1 * fs/2
        //    and 0.2 * fs/2, then we can search between fmin = 0.8 and
        //    fmax = 0.9
        fmin = 0;
        fmax = 1;
    }
    
    // This is a constructer to allow settings of the fmin and fmax variables
    // without going via the setMinFreq() and setMaxFreq() functions.  This is
    // an easy way to set fmin and fmax to -0.5 and 0.5 respective, which is
    // the branch required by the PSK decoder to work directly from the index.
    // (This is a bit hacky -- refactoring needs to be done.)
    public Vn2StarZnLLSOld(double fmin, double fmax) {
        super();
        
        this.fmin = fmin;
        this.fmax = fmax;
    }
    
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
        g[0] = -(N/2); // Note: integer division contains implicit floor
        if (N % 2 == 0) {
            g[0] += 0.5;
        }
        for (int i = 1; i < N; i++) {
            g[i] = g[i-1] + 1;
        }
    }
    
    /**
     * Sets the minimum frequency to look for in the lattice
     * @param f Minimum frequency as a fraction of fs/2 to search for.  Valid
     * range: -0.5 to 0.5
     */
    public void setMinFreq(double f) {
        assert(f >= -0.5 && f < 0.5);
        fmax = 1 - f;
    }
    
    /**
     * Sets the maximum frequency to look for in the lattice
     * @param f Maximum frequency as a fraction of fs/2 to search for.  Valid
     * range: -0.5 to 0.5
     */
    public void setMaxFreq(double f) {
        assert(f >= -0.5 && f < 0.5);
        fmin = 1 - f;
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
                
                vstart[j] = translate[j] + fmin * g[j];
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
                z[j] = lstart[j] - translate[j];
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
                bestGlue = i;
                bestk = k;
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
                    bestGlue = i;
                    bestk = k;
                }
            }
        }
        
        //Determine index associated with the closest point.
        glueVector(bestGlue);
        for (int i = 0; i < N; i++) {
            u[i] = Math.round(
                     y[i] + glue[i] + bestk*g[i]
                   );
        }
        u[0] -= bestGlue;
        
        // Get the lattice point by projecting onto Vn2Star
        project(u, v);
    }
    
    /** 
     * Sets protected variable g to the glue
     * vector [i].  See SPLAG pp109.
     */
    protected void glueVector(double i){
       /* double j = N - i;
        for(int k = 0; k < j; k++)
            glue[k] = i/N;
        for(int k = (int)j; k < N; k++)
            glue[k] = -j/N;
        */
        //this is another set of glue vectors that can be
        //used.  These are in a line and are sometimes
        //more convenient than Conway and Sloane's.
        glue[0] = i*(1.0 - 1.0/(n+2));
        for(int j = 1; j < n+2; j++)
            glue[j] = -i*1.0/(n+2);
    }
    
}
