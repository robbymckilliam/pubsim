/*
 * T2LogTOptimal.java
 *
 * Created on 17 September 2007, 14:10
 */

package pubsim.qam;

import java.util.TreeMap;
import pubsim.Complex;

/**
 * The faster O(T^2 log(T)) GLRT-optimal non-coherent QAM
 * receiver. 
 * @author Robby McKilliam
 */
public class T2LogTOptimal extends NonCoherentReceiver implements  QAMReceiver {
    
    protected double[] y1, y2;
    protected double[] x, xopt;
    protected double[] c;
    protected double[] d;
    protected double[] dreal;
    protected double[] dimag;
    
    private TreeMap map;
    
    /** Set the size of the QAM array */
    public void setQAMSize(int M){ this.M = M; }
    
    /** 
     * Set number of QAM signals to use for
     * estimating the channel
     */
    public void setT(int T){
        this.T = T;
        
        y1 = new double[2*T];
        y2 = new double[2*T];
        x = new double[2*T];
        xopt = new double[2*T];
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
        
        createPlane(rreal, rimag, y1, y2);
     
        double Lopt = Double.NEGATIVE_INFINITY;
        double aopt = 0.0, bopt = 0.0; 
        int topt = 0, kopt = 0;
        //for every second line type.  We remove half
        //the ambiguities by not testing perpedicular lines
        for(int t = 0; t < 2*T; t+=2){
            
            //calculate gradient for the 
            //line we are searching.
            for(int j = 0; j < 2*T; j++)
                d[j] = y1[j] - y1[t]*y2[j]/y2[t];
            
            //for the parallel lines of this type
            //only use the non-negative lines, this
            //removes half the ambiguities.
            for(int k = 0; k <= M-2; k+=2){

                //calculate offset for the 
                //line we are searching.
                for(int j = 0; j < 2*T; j++)
                    c[j] = k*y2[j]/y2[t];

                //calculate the start point
                //for the line search
                double bmin = Double.POSITIVE_INFINITY;
                int minT = 0;
                for(int j = 0; j < 2*T; j++){
                    if(j!=t && d[j] != 0.0){
                        double bpos = (M - c[j])/d[j];
                        double bneg = (-M - c[j])/d[j];
                        double thismin = Math.min(bpos, bneg);
                        if(thismin < bmin){
                            minT = j;
                            bmin = thismin;
                        }
                    }
                }

                //setup start point
                for(int j = 0; j < 2*T; j++)
                    x[j] = -Math.signum(d[j])*(M-1);
                x[t] = k + 1;

                //setup sorted map
                map.clear();
                for(int j = 0; j < 2*T; j++){
                    if(t!=j && d[j] != 0.0)
                        map.put(new Double((x[j]+Math.signum(d[j])-c[j])/d[j]),
                                new Integer(j));
                }

                //setup dot product variables for updating 
                //likelihood in constant time.
                double y1tv = 0.0, y2tv = 0.0, y1ty2 = 0.0, vtv = 0.0,
                        y1tvn = 0.0, y2tvn = 0.0, vtvn = 0.0,
                        y1ty1 = 0.0, y2ty2 = 0.0;
                for(int j = 0; j < 2*T; j++){
                    y1tv += y1[j]*x[j];
                    y2tv += y2[j]*x[j];
                    y1ty2 += y1[j]*y2[j];
                    vtv += x[j]*x[j];
                    y1ty1 += y1[j]*y1[j];
                    y2ty2 += y2[j]*y2[j];
                }
                y1tvn = y1tv - 2*y1[t];
                y2tvn = y2tv - 2*y2[t];
                vtvn = vtv - 4*x[t] + 4;

                //run the search loop
                do{                   
                    //test the likelihood of the codeword on each
                    //side of the line, runs in constant time.
                    double L = vtv/(vtv - y1tv*y1tv/y1ty1 - y2tv*y2tv/y2ty2
                            + y1tv*y2tv*y1ty2/(y1ty1*y2ty2));       
                    if(L > Lopt){
                        Lopt = L;
                        System.arraycopy(x, 0, xopt, 0, 2*T);
                    }
                    double Ln = vtvn/(vtvn - y1tvn*y1tvn/y1ty1 - y2tvn*y2tvn/y2ty2
                        + y1tvn*y2tvn*y1ty2/(y1ty1*y2ty2));
                    if(Ln > Lopt){
                        Lopt = Ln;
                        System.arraycopy(x, 0, xopt, 0, 2*T);
                        xopt[t] -= 2;    
                    }

                    Double key = ((Double) map.firstKey());
                    int n = ((Integer)map.get(key)).intValue();
                    double s = Math.signum(d[n]);

                    //update the dot products
                    y1tv += 2*s*y1[n];
                    y2tv += 2*s*y2[n];
                    vtv += 4*s*x[n] + 4;
                    y1tvn += 2*s*y1[n];
                    y2tvn += 2*s*y2[n];
                    vtvn += 4*s*x[n] + 4;

                    x[n] += 2*s;
                    map.remove(key);
                    if((x[n] > -M + 1 ) && (x[n] < M - 1 ))
                        map.put(new Double((x[n]+s-c[n])/d[n]), new Integer(n));

                }while(!map.isEmpty());

            }
       
        }
        
        //Write the best codeword into real and
        //imaginary vectors
        toRealImag(xopt, dreal, dimag);
        
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

    public void setChannel(Complex h) {
        //do nothing.  This is a noncoherent detector.  The channel will
        //be estimated.
    }
 
}
