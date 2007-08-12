/*
 * PnoneGlued.java
 *
 * Created on 12 August 2007, 20:15
 */

package simulator;

/**
 * O(n^4 log(n)) nearest point algorithm for the lattice Pn.  This just runs the
 * An* nearest point algorithm for all O(n^3) glue vectors.  Pnone is a faster
 * O(n^3 log(n)) algorithm.  This one is for testing.
 * NEEDS TESTING.
 * @author Robby McKilliam
 */
public class PnoneGlued extends Pnone implements LatticeNearestPointAlgorithm {
    
    private Anstar anstar;
    
    protected double[] g, vt, yt;
    
    public void setDimension(int n){
        this.n = n;
        
        anstar = new Anstar();
        anstar.setDimension(n+1);
        
        g = new double[n+2];
        v = new double[n+2];
        yt = new double[n+2];
        
    }
    
    public void nearestPoint(double[] y){
        if (n != y.length-2)
	    setDimension(y.length-2);
        
        double d = (Math.floor(n/2.0) + 1)*(Math.floor(n/2.0) + 2)*(2*Math.floor(n/2.0) + 3)/3.0; 
               
        for (int j = 0; j < n+2; j++)
            g[j] = -1.0/(n+2) + 1.0/d;
        
        //note that there in an implicit floor happening here!
        g[n/2 + 2] += 1.0;
        
        double bestdist = Double.POSITIVE_INFINITY;
        //iterate over all glue vectors
        for(int i = 0; i < d; i++){
            
            for (int j = 0; j < n+2; j++)
                yt[j] = y[j] - i*g[j];
            
            //solve the nearestPoint algorithm in An* for this glue
            anstar.nearestPoint(yt);
            vt = anstar.getLatticePoint();
            
            double dist = 0.0;
            for (int j = 0; j < n+2; j++)
                dist += Math.pow( vt[j] + i*g[j] - y[j], 2);
            
            if(dist < bestdist){
                bestdist = dist;
                for (int j = 0; j < n+2; j++)
                    v[j] = vt[j];
            }
            
        }
    }
    
    /**Getter for the nearest point. */
    public double[] getLatticePoint() { return v; }
    
    /**Getter for the interger vector. Not implemented*/
    public double[] getIndex() { return null; }
    
}
