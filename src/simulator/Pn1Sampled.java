/*
 * Pn1Sampled.java
 *
 * Created on 18 August 2007, 12:53
 */

package simulator;

/**
 * Suboptimal (maybe?) algorithm for finding the nearest lattice point in Pn1.
 * Based on the pes.SamplingLLS period estimator.  Runs in 0(n^2 logn) assuming
 * that 0(n) samples are used.
 * <p>
 * This currently only works for odd n.
 * @author Robby McKilliam
 */
public class Pn1Sampled extends Pn1 implements LatticeNearestPointAlgorithm {
    
    protected int num_samples;
    Anstar anstar;
    
    protected double[] g, vt, ut; 
    
    /** Default constructor.  Uses 100 samples */
    public Pn1Sampled() {
        num_samples = 100;
    }
    
    /** Default constructor sets the number of samples */
    public Pn1Sampled(int samples) {
        num_samples = samples;
    }
    
    public void setDimension(int n){
        this.n = n;
        
        anstar = new Anstar();
        anstar.setDimension(n+1);
        
        u = new double[n+2];
        v = new double[n+2];
        vt = new double[n+2];
        g = new double[n+2];
    }
    
    public void nearestPoint(double[] y){
        if (n != y.length-2)
	    setDimension(y.length-2);
        
        double d = (Math.floor(n/2.0) + 1)*(Math.floor(n/2.0) + 2)
            *(2*Math.floor(n/2.0) + 3)/3.0; 
        
        double bestdist = Double.POSITIVE_INFINITY;
        double step = Math.sqrt(d)/num_samples;
        for( double f = 0; f < Math.sqrt(d); f+=step ){
            
            //calculate the next point on line to test
            for(int i=0; i<n+2; i++)
                g[i] = y[i] + (i+1.0-(n+3.0)/2.0)*f;
            
            anstar.nearestPoint(g);         
            project(anstar.getLatticePoint(), vt);
            
            double dist = VectorFunctions.distance_between(y, vt);
            
            if(dist < bestdist){
                bestdist = dist;
                VectorFunctions.copy(vt, v);
                VectorFunctions.copy(anstar.getIndex(), u);
            } 
            
            System.out.println("samp dist = " + bestdist);
            
        }
    }
    
}
