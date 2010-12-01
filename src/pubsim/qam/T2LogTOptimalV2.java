/*
 * T2LogTOptimalV2.java
 *
 * Created on 10 October 2007, 13:10
 */

package pubsim.qam;

import java.util.Arrays;
import pubsim.IndexedDouble;

/**
 * Version of the O(T^2 LogT) optimal algorithm that does not
 * use a Red/Black tree (TreeMap) and instead does one large sort.
 * <p>
 * In theory you would expect this algorithm to be slightly slower than the
 * T2LogTOptimal, particularly for large M.  In practice the algorithm is
 * faster.  This is likely due to java implementation of Arrays.sort 
 * (quick sort) being faster than the TreeMap.
 * <p>
 * This algorithm is probably shorter and easier to understand and
 * code than T2LogTOptimal
 *
 * @author Robby McKilliam
 */
public class T2LogTOptimalV2 extends T2LogTOptimal implements  QAMReceiver {
    
    private IndexedDouble[] sorted;
    
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
        
        sorted = new IndexedDouble[(2*T-1)*(M-1)];
        for(int m = 0; m < sorted.length; m++)
            sorted[m] = new IndexedDouble();
        
    }
    
    /**Decode the QAM signal*/
    public void decode(double[] rreal, double[] rimag){
        if( rreal.length != T )
            setT(rreal.length);
        
        createPlane(rreal, rimag, y1, y2);
     
        double Lbest = Double.NEGATIVE_INFINITY;
        //for each type of line
        for(int t = 0; t < 2*T; t+=2){
            
            //calculate gradient for the 
            //line we are searching
            for(int j = 0; j < 2*T; j++)
                d[j] = y1[j] - y1[t]*y2[j]/y2[t];
            
            //for the parallel lines of this type
            //only use the non-negative lines, this
            //removes half the ambiguities.
            for(int k = 0; k <= M-2; k+=2){

                //calculate offest for the 
                //line we are searching
                for(int j = 0; j < 2*T; j++)
                    c[j] = k*y2[j]/y2[t];

                //setup sorted map
                int count = 0;
                for(int j = 0; j < 2*T; j++){
                    if(j!=t){
                        for(int m = -M+2; m <= M-2; m+=2){
                            sorted[count].value = (m-c[j])/d[j];
                            sorted[count].index = j;
                            count++;
                        }   
                    }
                }
                Arrays.sort(sorted);

                //setup start point
                for(int j = 0; j < 2*T; j++)
                    x[j] = -Math.signum(d[j])*(M-1);
                x[t] = k + 1;

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
                for(int m = 0; m < sorted.length; m++){                
                    //test the likelihood of the codeword on each
                    //side of the line, runs in constant time.
                    double L = vtv/(vtv - y1tv*y1tv/y1ty1 - y2tv*y2tv/y2ty2
                            + y1tv*y2tv*y1ty2/(y1ty1*y2ty2));       
                    if(L > Lbest){
                        Lbest = L;
                        System.arraycopy(x, 0, xopt, 0, 2*T);
                    }
                    double Ln = vtvn/(vtvn - y1tvn*y1tvn/y1ty1 - y2tvn*y2tvn/y2ty2
                        + y1tvn*y2tvn*y1ty2/(y1ty1*y2ty2));
                    if(Ln > Lbest){
                        Lbest = Ln;
                        System.arraycopy(x, 0, xopt, 0, 2*T);
                        xopt[t] -= 2;    
                    }

                    int n = sorted[m].index;
                    double s = Math.signum(d[n]);

                    //update the dot products
                    y1tv += 2*s*y1[n];
                    y2tv += 2*s*y2[n];
                    vtv += 4*s*x[n] + 4;
                    y1tvn += 2*s*y1[n];
                    y2tvn += 2*s*y2[n];
                    vtvn += 4*s*x[n] + 4;

                    x[n] += 2*s;
                }

            }
            
        }
        
        //Write the best codeword into real and
        //imaginary vectors
        toRealImag(xopt, dreal, dimag);
        
    }
    
}
