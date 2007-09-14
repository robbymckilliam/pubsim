/*
 * Pn2Glued.java
 *
 * Created on 12 August 2007, 20:15
 */

package simulator;

/**
 * O(n^4 log(n)) nearest point algorithm for the lattice Pn.  This just runs the
 * An* nearest point algorithm for all O(n^3) glue vectors.  Pn2 is a faster
 * O(n^3 log(n)) algorithm.  
 * <p>
 * This currently only works for odd n.  The glue vectors are slightly different
 * for even n.  There are also 4 times more of them for even n!
 * 
 * 
 * @author Robby McKilliam
 */
public class Pn2Glued extends Pn2 implements LatticeNearestPointAlgorithm {
    
    private Anstar anstar;
    
    protected double[] g, vt, yt, ut;
    
    public void setDimension(int n){
        this.n = n;
        
        anstar = new Anstar();
        anstar.setDimension(n+1);
        
        g = new double[n+2];
        v = new double[n+2];
        u = new double[n+2];
        yt = new double[n+2];
        
    }
    
    /**
     * Find the nearest point in Pn2 by searching the o(n^3)
     * translates/glues of An*.  Currently, this only works
     * for odd n.
     */
    public void nearestPoint(double[] y){
        if (n != y.length-2)
	    setDimension(y.length-2);
        
        double d = (Math.floor(n/2.0) + 1)*(Math.floor(n/2.0) + 2)
                    *(2*Math.floor(n/2.0) + 3)/3.0; 
               
        for (int j = 0; j < n+2; j++)
            g[j] = -1.0/(n+2) + (j + 1.0 - (n+3.0)/2.0)/d;
        
        g[(n+2)/2 - 1] += 1.0;
        
        double bestdist = Double.POSITIVE_INFINITY;
        //iterate over all glue vectors
        for(int i = 0; i < d; i++){
            
            for (int j = 0; j < n+2; j++)
                yt[j] = y[j] - i*g[j];
            
            //solve the nearestPoint algorithm in An* for this glue
            anstar.nearestPoint(yt);
            vt = anstar.getLatticePoint();
            ut = anstar.getIndex();
            
            double dist = 0.0;
            for (int j = 0; j < n+2; j++)
                dist += Math.pow( vt[j] + i*g[j] - y[j], 2);
            
            if(dist < bestdist){
                bestdist = dist;
                for (int j = 0; j < n+2; j++){
                    v[j] = vt[j] + i*g[j];
                    u[j] = ut[j];
                }
                u[(n+2)/2 - 1] += i;
                
                /*
                System.out.println("y = " + VectorFunctions.print(y));
                System.out.println("v = " + VectorFunctions.print(v));
                System.out.println("vt = " + VectorFunctions.print(vt));
                System.out.println("u = " + VectorFunctions.print(u));
                 */
            }
        }
        //System.out.println("glue dist = " + bestdist);
    }
    
}
