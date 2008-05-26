/*
 * GlueAnstarCarrierEstimator.java
 *
 * Created on 5 December 2007, 14:13
 */

package simulator.psk;

import lattices.Phin2Star;
import lattices.Phin2StarGlued;

/**
 * Uses the Glued Pn2 lattice point algorithm.  There is no way to
 * efficiently remove the 
 * @author Robby McKilliam
 */
public class GlueAnstarCarrierEstimator implements CarrierEstimator{
    
    protected int M;
    protected double[] marg;
    protected int N;
    
    protected double fmin, fmax;
    protected double phase, frequency;
    
    Phin2Star lattice;
    
    public GlueAnstarCarrierEstimator(){
        lattice = new Phin2StarGlued();
    }
    
    /** Return the estimated phase */
    public double getPhase(){
        return phase;
    }
    
    /** Return the estiamted freqenucy */
    public double getFreqency(){
        return frequency;
    }
    
    /** Set the maximum allowed frequency */
    public void setFmin(double fmin) {
        this.fmin = fmin;
    }
    
    /** 
     * Set the minimum allowed frequency 
     * Set this to M*fmin
     */
    public void setFmax(double fmax){
        this.fmax = fmax;
    }
    
    /** Set the number of samples */
    public void setSize(int n){
        lattice.setDimension(n-2);  
        marg = new double[n];
        N = n;
    }
    
    /** Set to M-ary QPSK */
    public void setM(int M){
        this.M = M;
    }
    
    /** 
     * This ignores fmin and fmax.  This causes it's performance
     * to be bad for low SNR.  Need to use a line search based
     * solution to the nearest lattice point algorithm to really
     * make this work.
     */
    public void estimateCarrier(double[] arg){
        
        if(N != arg.length)
            setSize(arg.length);
        
        for(int i = 0; i < arg.length; i++)
            marg[i] = M*arg[i];
        
        lattice.nearestPoint(marg);
        
        double[] u = lattice.getIndex();
        
        //calculate f from the nearest point
        double f = 0;
        double sumn = N*(N+1)/2;
        double sumn2 = N*(N+1)*(2*N+1)/6;
        for(int i = 0; i < N; i++)
            f += (N*(i+1) - sumn)*(marg[i]-u[i]);
        
        frequency = f/(M*(sumn2*N - sumn*sumn));
        
        
        /*double f = 0;
        double sumn2 = N*(N+1)*(2*N+1)/6;
        double sumn = N*(N+1)/2;
        for(int i = 0; i < N; i++)
            f += (i - (N-1)/2.0) * (ya[i]-u[i]);
        
        f /= M*(sumn2 - sumn*sumn/N);*/
        
    }
    
     /*
    public void nearestPoint(double[] y){
        if (n != y.length-2)
	    setDimension(y.length-2);
        
        project(y, this.y);
        
        double d = (Math.floor(n/2.0) + 1)*(Math.floor(n/2.0) + 2)
                    *(2*Math.floor(n/2.0) + 3)/3.0; 
        if(n%2 == 0) d = 2*d;
               
        for (int j = 0; j < n+2; j++)
            g[j] = -1.0/(n+2) + (j + 1.0 - (n+3.0)/2.0)/d;
        
        g[(n+2)/2 - 1] += 1.0;
        
        double bestdist = Double.POSITIVE_INFINITY;
        //iterate over all glue vectors
        for(int i = 0; i < d; i++){
            
            for (int j = 0; j < n+2; j++)
                yt[j] = this.y[j] - i*g[j];
            
            //solve the nearestPoint algorithm in An* for this glue
            anstar.nearestPoint(yt);
            vt = anstar.getLatticePoint();
            ut = anstar.getIndex();
            
            double dist = 0.0;
            for (int j = 0; j < n+2; j++)
                dist += Math.pow( vt[j] + i*g[j] - this.y[j], 2);
            
            ut[(n+2)/2 - 1] += i;
            
            double f = 0;
            double sumn = N*(N+1)/2;
            double sumn2 = N*(N+1)*(2*N+1)/6;
            for(int j = 0; j < N; j++)
                f += (N*(j+1) - sumn)*(y[j]-ut[j]);
        
            f /= M*(sumn2*N - sumn*sumn);
            
            if(dist < bestdist && Math.abs(f) >= fmin && Math.abs(f) <= fmax){
                bestdist = dist;
                for (int j = 0; j < n+2; j++){
                    v[j] = vt[j] + i*g[j];
                    u[j] = ut[j];
                }
            }
        }
    }*/
    
}
