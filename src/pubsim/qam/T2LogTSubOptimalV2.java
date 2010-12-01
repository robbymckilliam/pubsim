/*
 * T2LogTSubOptimalV2.java
 *
 * Created on 10 October 2007, 15:24
 */

package pubsim.qam;

import java.util.Arrays;
import pubsim.IndexedDouble;

/**
 * Version of the O(T^2 LogT) sub optimal algorithm that does not
 * use a Red/Black tree (TreeMap) and instead does one large sort.
 * <p>
 * In theory you would expect this algorithm to be slightly slower than the
 * T2LogTSubOptimal, particularly for large M.  In practice the algorithm is
 * faster.  This is likely due to java implementation of Arrays.sort 
 * (merge sort) being faster than the TreeMap.
 * <p>
 * This algorithm is probably shorter and easier to understand and
 * code than T2LogTSubOptimal
 *
 * @author Robby McKilliam
 */
public class T2LogTSubOptimalV2 extends T2LogTSubOptimal implements  QAMReceiver{
    
    /** Default, L = 1.0 */
    public T2LogTSubOptimalV2() { numL = 1.0; }
    
    /** Set number of lines used in the line search */
    public T2LogTSubOptimalV2(double numL) { this.numL = numL; }
    
    protected IndexedDouble[] sorted;
    
    /** 
     * Set number of QAM signals to use for
     * estimating the channel
     */
    @Override
    public void setT(int T){
        this.T = T;
        
        y1 = new double[2*T];
        y2 = new double[2*T];
        x = new double[2*T];
        xopt = new double[2*T];
        d = new double[2*T];
        
        dreal = new double[T];
        dimag = new double[T];
        
        sorted = new IndexedDouble[2*T*(M/2-1)];
        for(int m = 0; m < sorted.length; m++)
            sorted[m] = new IndexedDouble();
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
            
            //setup sorted map
            int count = 0;
            for(int j = 0; j < 2*T; j++){
                for(int m = 2; m <= M-2; m+=2){
                    sorted[count].value = Math.signum(d[j])*m/d[j];
                    sorted[count].index = j;
                    count++;
                }
            }
            Arrays.sort(sorted);
            
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
             
            //test the likelihood at this point
            double L = vtv/(vtv - y1tv*y1tv/y1ty1 - y2tv*y2tv/y2ty2
                    + y1tv*y2tv*y1ty2/(y1ty1*y2ty2));    
            if(L > Lbest){
                Lbest = L;
                System.arraycopy(x, 0, xopt, 0, 2*T);
            }
             
            //run the search loop
            for(int m = 0; m < sorted.length; m++){ 
                
                int n = sorted[m].index;
                double s = Math.signum(d[n]);

                //update the dot products
                y1tv += 2*s*y1[n];
                y2tv += 2*s*y2[n];
                vtv += 4*s*x[n] + 4;

                x[n] += 2*s;
                
                 //test the likelihood at this point
                L = vtv/(vtv - y1tv*y1tv/y1ty1 - y2tv*y2tv/y2ty2
                        + y1tv*y2tv*y1ty2/(y1ty1*y2ty2));    
                if(L > Lbest){
                    Lbest = L;
                    System.arraycopy(x, 0, xopt, 0, 2*T);
                }

            }
            
        }
        
        //Write the best codeword into real and
        //imaginary vectors
        toRealImag(xopt, dreal, dimag);
         
    }
    
}
