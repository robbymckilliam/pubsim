/*
 * T2LogTSubOptimal.java
 *
 * Created on 1 October 2007, 09:01
 */

package simulator.qam;

import java.util.TreeMap;
import simulator.VectorFunctions;

/**
 * Dan's O(T^2 log(T)) suboptimal noncoherent QAM
 * receiver.
 * @author Robby
 */
public class T2LogTSubOptimal extends NonCoherentReceiver implements  QAMReceiver{
    
    /** Default, L = 1.0 */
    public T2LogTSubOptimal() { L = 1.0; }
    
    /** Set L */
    public T2LogTSubOptimal(double L) { this.L = L; }
    
    protected int M;
    protected int T;
    
    /** The number of line searches is floor(T*L) */ 
    protected double L;
    
    protected double[] y1, y2;
    protected double[] v, vbest;
    protected double[] c;
    protected double[] d;
    protected double[] dreal;
    protected double[] dimag;
    
    protected TreeMap map;
    
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
        v = new double[2*T];
        vbest = new double[2*T];
        d = new double[2*T];
        
        dreal = new double[T];
        dimag = new double[T];
        
        map = new TreeMap();
    }
    
    /** Decode the QAM signal */
    public void decode(double[] rreal, double[] rimag){
        if( rreal.length != T )
            setT(rreal.length);
        
        createPlane(rreal, rimag, y1, y2);
        
        double Lbest = Double.NEGATIVE_INFINITY;
        double thetastep = Math.PI/(2*T*L);
        for(double theta = 0.0; theta < Math.PI/2; theta+=thetastep){
            
            //calculate the search line
            double a = Math.cos(theta);
            double b = Math.sin(theta);
            for(int i = 0; i < 2*T; i++)
                d[i] = a*y1[i] + b*y2[i];
            
            //setup the sorted map
            map.clear();
            for(int i = 0; i < 2*T; i++)
                map.put(new Double(Math.signum(d[i])/d[i]), new Integer(i));
            
            //calculate the start point
            for(int i = 0; i < 2*T; i++)
                v[i] = Math.signum(d[i]);
            
            //setup likelihood variables
             double y1tv = 0.0, y2tv = 0.0, y1ty2 = 0.0, vtv = 0.0,
                        y1ty1 = 0.0, y2ty2 = 0.0;
            for(int j = 0; j < 2*T; j++){
                y1tv += y1[j]*v[j];
                y2tv += y2[j]*v[j];
                y1ty2 += y1[j]*y2[j];
                vtv += v[j]*v[j];
                y1ty1 += y1[j]*y1[j];
                y2ty2 += y2[j]*y2[j];
            }
             
            int n = 0;
            do{
                //test the likelihood of the codeword on each
                //side of the line, runs in constant time.
                //double vp = 2*y1tv*y2tv*y1ty2/(y1ty1*y2ty2) 
                //        + y1tv*y1tv/y1ty1 + y2tv*y2tv/y2ty2;
                double L = vtv/(vtv - y1tv*y1tv/y1ty1 - y2tv*y2tv/y2ty2
                        + y1tv*y2tv*y1ty2/(y1ty1*y2ty2));    
                if(L > Lbest){
                    Lbest = L;
                    for(int j = 0; j < 2*T; j++)
                        vbest[j] = v[j];
                    //System.out.println("L = " + L);
                    //System.out.println("bv = " + VectorFunctions.print(vbest));
                }
                
                Double key = ((Double) map.firstKey());
                n = ((Integer)map.get(key)).intValue();
                double s = Math.signum(d[n]);

                //update the dot products
                y1tv += 2*s*y1[n];
                y2tv += 2*s*y2[n];
                vtv += 4*s*v[n] + 4;

                v[n] += 2*s;
                map.remove(key);
                map.put(new Double((v[n]+s)/d[n]), new Integer(n));

            }while( (v[n] >= -M + 1 ) && (v[n] <= M - 1 ) );
            
        }
        
        //Write the best codeword into real and
        //imaginary vectors
        toRealImag(vbest, dreal, dimag);
         
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
