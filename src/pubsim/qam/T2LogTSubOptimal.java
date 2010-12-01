/*
 * T2LogTSubOptimal.java
 *
 * Created on 1 October 2007, 09:01
 */

package pubsim.qam;

import java.util.TreeMap;
import pubsim.VectorFunctions;
import pubsim.Complex;

/**
 * Dan's O(T^2 log(T)) suboptimal noncoherent QAM
 * receiver.
 * @author Robby McKilliam
 */
public class T2LogTSubOptimal extends NonCoherentReceiver implements  QAMReceiver{
    
    /**
     * Default, numL = 1.0
     */
    public T2LogTSubOptimal() { numL = 1.0; }
    
    /**
     * Set number of lines used in the line search
     */
    public T2LogTSubOptimal(double L) { this.numL = L; }
    
    /**
     * The number of line searches is floor(T*numL)
     */ 
    protected double numL;
    
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
        d = new double[2*T];
        
        dreal = new double[T];
        dimag = new double[T];
        
        map = new TreeMap();
    }

    public void setChannel(Complex h) {
        //do nothing.  This is a noncoherent detector.  The channel will
        //be estimated.
    }
    
    /** Decode the QAM signal */
    public void decode(double[] rreal, double[] rimag){
        if( rreal.length != T )
            setT(rreal.length);
        
        createPlane(rreal, rimag, y1, y2);
        
        double Lbest = Double.NEGATIVE_INFINITY;
        double thetastep = Math.PI/(2*T*numL);
        for(double theta = 0.0; theta < Math.PI/2; theta+=thetastep){
            
            //calculate the search line
            double a = Math.cos(theta);
            double b = Math.sin(theta);
            for(int i = 0; i < 2*T; i++)
                d[i] = a*y1[i] + b*y2[i];
            
            //setup the sorted map
            map.clear();
            for(int i = 0; i < 2*T; i++)
                map.put(new Double(2*Math.signum(d[i])/d[i]), new Integer(i));
            
            //calculate the start point
            for(int i = 0; i < 2*T; i++)
                x[i] = Math.signum(d[i]);
            
            //setup likelihood variables
             double y1tv = 0.0, y2tv = 0.0, y1ty2 = 0.0, vtv = 0.0,
                        y1ty1 = 0.0, y2ty2 = 0.0;
            for(int j = 0; j < 2*T; j++){
                y1tv += y1[j]*x[j];
                y2tv += y2[j]*x[j];
                y1ty2 += y1[j]*y2[j];
                vtv += x[j]*x[j];
                y1ty1 += y1[j]*y1[j];
                y2ty2 += y2[j]*y2[j];
            }
             
            int n = 0;
            do{
                double L = vtv/(vtv - y1tv*y1tv/y1ty1 - y2tv*y2tv/y2ty2
                        + y1tv*y2tv*y1ty2/(y1ty1*y2ty2));    
                if(L > Lbest){
                    Lbest = L;
                    System.arraycopy(x, 0, xopt, 0, 2*T);
                }
                
                Double key = ((Double) map.firstKey());
                n = ((Integer)map.get(key)).intValue();
                double s = Math.signum(d[n]);

                //update the dot products
                y1tv += 2*s*y1[n];
                y2tv += 2*s*y2[n];
                vtv += 4*s*x[n] + 4;

                x[n] += 2*s;
                map.remove(key);
                if((x[n] > -M + 1) && (x[n] < M - 1))
                    map.put(new Double((x[n]+s)/d[n]), new Integer(n));

            }while(!map.isEmpty());
            
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
    
}
