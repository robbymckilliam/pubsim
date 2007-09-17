/*
 * T2LogTOptimalNonCoherentReciever.java
 *
 * Created on 17 September 2007, 14:10
 */

package simulator.qam;

import java.util.TreeMap;

/**
 * The faster O(T^2 log(T)) GLRT-optimal non-coherent QAM
 * receiver. 
 * @author Robby
 */
public class T2LogTOptimalNonCoherentReciever implements  QAMReceiver {
    
    protected int M;
    protected int T;
    
    protected double[] y1, y2;
    protected double[] v, vbest;
    protected double[] c;
    protected double[] d;
    protected double[] dreal;
    protected double[] dimag;
    
    protected TreeMap map;
    
    /** Set the size of the QAM array */
    public void setQAMSize(int M){
        this.M = M;
    }
    
    /** 
     * Set number of QAM signals to use for
     * estimating the channel
     */
    public void setT(int T){
        this.T = T;
        
        y1 = new double[2*T];
        y2 = new double[2*T];
        v = new double[2*T];
        vbest = new double[2*T];
        c = new double[2*T];
        d = new double[2*T];
        
        dreal = new double[T];
        dimag = new double[T];
        
        map = new TreeMap();
    }
    
    /**Decode the QAM signal*/
    public void decode(double[] rreal, double[] rimag){
        if( rreal.length != T )
            setT(rreal.length);
        
        //Dan's underline operator
        for(int i = 0; i < T; i++){
            y1[2*i] = rreal[i];
            y1[2*i+1] = rimag[i];
            y2[2*i] = -rimag[i];
            y2[2*i+1] = rreal[i];
        }
        
        //for each type of line
        for(int i = 0; i < 2*T; i++){
            
            //for the parallel lines of this type
            for(int k = -M+2; k <= M-2; k+=2){
                
                //calculate parameters for the 
                //line we are searching.  d can be
                //calculated outside of k loop but
                //it's neater to have it here
                for(int j = 0; j < 2*T; j++){
                    c[j] = k*y1[j]/y1[i];
                    d[j] = -y2[i]*y1[j] - y2[j];
                }
                
                //calculate thestart point
                //for the line search
                double bmin = Double.NEGATIVE_INFINITY;
                int minT = 0;
                for(int j = 0; j < 2*T; j++){
                    if(j!=i){
                        double bpos = (M - 2 - c[j])/d[j];
                        double bneg = (-M + 2 - c[j])/d[j];
                        double thismin = Math.min(bpos, bneg);
                        if(bmin < thismin){
                            minT = j;
                            bmin = thismin;
                        }
                    }
                }
                
                //setup start point
                map.clear();
                for(int j = 0; j < 2*T; j++)
                        v[j] = 2*Math.round((bmin*d[j]+c[j]+1)/2)-1;
                v[i] = k + 1;
                
                //setup map
                map.clear();
                for(int j = 0; j < 2*T; j++){
                    if(i!=j)
                        map.put((v[j]+Math.signum(d[j])-c[j])/d[j], j);
                }
                
                
                //run the search loop
                int n = 0;
                double Lbest = Double.POSITIVE_INFINITY;
                while( (v[n] >= -M + 1) && (v[n] <= M - 1)){
                    
                    //under construction
                    
                }
                
                
            }
            
        }
        
        
    }
    
    /** 
     * Nearest neighbour algorithm for this form of QAM.
     * See Dan's paper. x input, y output.
     * pre: x.length == y.length.
     */
    public void NN(double[] x, double[] y){
        for(int i = 0; i < x.length; i++)
            y[i] = 2*Math.round((x[i]+1)/2) - 1;
    }
    
    /** 
     * Get the real part of the decoded QAM signal.
     * Call decode first.
     */
    public double[] getReal(){
        return dreal;
    }
    
    /** 
     * Get the imaginary part of the decoded QAM signal.
     * Call decode first.
     */
    public double[] getImag(){
        return dimag;
    }
    
    
}
