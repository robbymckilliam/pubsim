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
        if (N % 2 == 0) {
            fmax = 2;
        } else {
            fmax = 1;
        }
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
            //System.out.println("glue vector " + i + ": " + VectorFunctions.print(glue));
            //System.out.println("|glue vector " + i + "|: " + VectorFunctions.magnitude(glue));
            int numCrosses = 0;
            for (int j = 0; j < N; j++) {
                translate[j] = y[j] + glue[j];
                
                vstart[j] = translate[j];
                vend[j] = translate[j] + fmax * g[j];
                
                lstart[j] = Math.round(vstart[j]);
                lend[j] = Math.round(vend[j]);

                numCrosses += Math.abs(lend[j] - lstart[j]);
            }
            //[][0] == distance from vstart that the crossing occurs
            //[][1] == dimension in which the crossing occurs
            double[][] unsorted_crosses = new double[numCrosses][2];
            TreeMap crosses = new TreeMap();
            int count = 0;
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < Math.abs(lend[j] - lstart[j]); k++) {
                    /*
                    // This one shows where the voronoi boundaries occur
                    // relative to the origin in Zn
                    crosses[count][0] = (2*lstart[j] + (2*k+1)*Math.signum(g[j]))
                                         / 2;
                    */
                    /*
                    // This one shows where the voronoi boundaries occur
                    // relative to the beginning of the line we're searching
                    // along
                    crosses[count][0] = ((2*lstart[j] + (2*k+1)*Math.signum(g[j]))
                                         / 2) - vstart[j];
                    */
                    /*
                    // This one shows how far along g STARTING AT THE FIRST
                    // LATTICE POINT the voronoi boundaries occur
                    crosses[count][0] = ((2*k + 1) * Math.signum(g[j]))
                                         / (2*g[j]);
                    */
                    // This one shows how far along g relative to the
                    // translated beginning of g the voronoi boundaries occur
                    crosses.put(
                                  new Double(
                                    ((((2*k + 1) * Math.signum(g[j]))
                                     / 2) - (vstart[j] - lstart[j]))/g[j]
                                  ),
                                  new Integer(j)
                               );
                    unsorted_crosses[count][0] = ((((2*k + 1) * Math.signum(g[j]))
                                                 / 2) - (vstart[j] - lstart[j]))/g[j];
                    unsorted_crosses[count][1] = j;
                    count++;
                }
            }
            
            if (count != numCrosses) {
                //complain loudly
                System.err.println("You've somehow miscounted the number of " +
                                   "voronoi crossings in Phin2StarZnLLS");
            }
            
            //System.out.println("iteration " + i);
            //System.out.println("vstart is " + VectorFunctions.print(vstart));
            //System.out.println("vend is " + VectorFunctions.print(vend));
            //System.out.println("lstart is " + VectorFunctions.print(lstart));
            //System.out.println("lend is " + VectorFunctions.print(lend));
            //System.out.println("g is " + VectorFunctions.print(g));
            //System.out.println("unsorted_crosses is " + VectorFunctions.print(unsorted_crosses));

            //System.out.println("sorted crosses is " + crosses.keySet() + "\n" + crosses.values());
            
            gtz = 0;
            gtg = 0;
            ztz = 0;
           
            for (int j = 0; j < N; j++) {
                z[j] = lstart[j] - vstart[j];
                // Is it a problem that I'm taking everything wrt vstart rather
                // than the origin?  Check this.
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
                    //bestpoint[j] = Math.round(z[j] + vstart[j] - glue[j]);
                    bestpoint[j] = z[j] + vstart[j] - glue[j];
                    v[j] = k*g[j] + vstart[j] - glue[j];
                }
            }
            
            Iterator keyIter = crosses.keySet().iterator();
            Iterator valIter = crosses.values().iterator();
            while (valIter.hasNext()) {
                int nextDim = ((Integer)(valIter.next())).intValue();
                gtz += Math.signum(g[nextDim]) * g[nextDim];
                ztz += 2 * Math.signum(g[nextDim]) * z[nextDim] + 1;
                z[nextDim] += Math.signum(g[nextDim]);
                
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
                double k_plus = k+0.001;
                double k_minus = k-0.001;
                double dist_plus = k_plus*k_plus*gtg - 2*k_plus*gtz + ztz;
                double dist_minus = k_minus*k_minus*gtg - 2*k_minus*gtz + ztz;
                if (dist_plus < dist || dist_minus < dist) {
                    //System.out.println("This oughtn't happen -- the distance" +
                    //                   "should be minimised already.");
                }
                
                double[] temp = new double[N];
                double[] temp2 = new double[N];
                for (int j = 0; j < N; j++) {
                    temp[j] = z[j] + vstart[j];
                    temp2[j] = k*g[j] + vstart[j];
                }
                
                //System.out.println("checking " + VectorFunctions.print(temp) + ", dist is " + dist + ", vect is " + VectorFunctions.print(temp2));
                
                if (dist < bestdist) {
                    //System.out.println("best point found!  With dist of " + dist);
                    bestdist = dist;
                    for (int j = 0; j < N; j++) {
                        //bestpoint[j] = Math.round(z[j] + vstart[j] - glue[j]);
                        bestpoint[j] = z[j] + vstart[j] - glue[j];
                        v[j] = k*g[j] + vstart[j] - glue[j];
                    }
                }
                
                double key = ((Double)(keyIter.next())).doubleValue();
                //System.out.println("k is " + k + "; key was " + key);
                //System.out.println("k is " + k);
                /*
                double[] boundary_on_g = new double[N];
                double[] pos_on_g = new double[N];
                double[] vor_point = new double[N];
                for (int j = 0; j < N; j++) {
                    boundary_on_g[j] = key * g[j] + vstart[j];
                    pos_on_g[j] = k * g[j] + vstart[j];
                    vor_point[j] = z[j] + vstart[j];
                }
                System.out.println("Boundary on g: " + VectorFunctions.print(boundary_on_g));
                System.out.println("Pos on g: " + VectorFunctions.print(pos_on_g));
                System.out.println("Voronoi point: " + VectorFunctions.print(vor_point));
                System.out.println();
                */
            }
            
            //System.out.println("best dist is " + bestdist);
            //System.out.println("--");
        }
        
        for (int i = 0; i < N; i++) {
            u[i] = bestpoint[i];
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
