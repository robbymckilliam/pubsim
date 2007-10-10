/*
 * T2LogTOptimalV2.java
 *
 * Created on 10 October 2007, 13:10
 */

package simulator.qam;

import java.util.Arrays;

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
 * @author robertm
 */
public class T2LogTOptimalV2 extends NonCoherentReceiver implements  QAMReceiver {
    
    protected int M;
    protected int T;
    
    protected double[] y1, y2;
    protected double[] v, vbest;
    protected double[] c;
    protected double[] d;
    protected double[] dreal;
    protected double[] dimag;
    protected IndexedDouble[] sorted;
    
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
        for(int i = 0; i < 2*T; i+=2){
            
            //calculate gradient for the 
            //line we are searching
            for(int j = 0; j < 2*T; j++)
                d[j] = y1[j] - y1[i]*y2[j]/y2[i];
            
            //for the parallel lines of this type
            //only use the non-negative lines, this
            //removes half the ambiguities.
            for(int k = 0; k <= M-2; k+=2){

                //calculate offest for the 
                //line we are searching
                for(int j = 0; j < 2*T; j++)
                    c[j] = k*y2[j]/y2[i];

                //setup sorted map
                int count = 0;
                for(int j = 0; j < 2*T; j++){
                    if(j!=i){
                        for(int m = -M+2; m <= M-2; m+=2){
                            sorted[count].value = (m-c[j])/d[j];
                            sorted[count].index = j;
                            count++;
                        }   
                    }
                }
                Arrays.sort(sorted);

                //setup start point
                double b = sorted[0].value;
                int minT = sorted[0].index;
                for(int j = 0; j < 2*T; j++)
                    v[j] = b*d[j]+c[j];
                NN(v,v,M);
                v[i] = k + 1;
                v[minT] = -Math.signum(d[minT])*(M - 1);

                //setup dot product variables for updating 
                //likelihood in constant time.
                double y1tv = 0.0, y2tv = 0.0, y1ty2 = 0.0, vtv = 0.0,
                        y1tvn = 0.0, y2tvn = 0.0, vtvn = 0.0,
                        y1ty1 = 0.0, y2ty2 = 0.0;
                for(int j = 0; j < 2*T; j++){
                    y1tv += y1[j]*v[j];
                    y2tv += y2[j]*v[j];
                    y1ty2 += y1[j]*y2[j];
                    vtv += v[j]*v[j];
                    y1ty1 += y1[j]*y1[j];
                    y2ty2 += y2[j]*y2[j];
                }
                y1tvn = y1tv - 2*y1[i];
                y2tvn = y2tv - 2*y2[i];
                vtvn = vtv - 4*v[i] + 4;

                //run the search loop
                for(int m = 0; m < sorted.length; m++){                
                    //test the likelihood of the codeword on each
                    //side of the line, runs in constant time.
                    double L = vtv/(vtv - y1tv*y1tv/y1ty1 - y2tv*y2tv/y2ty2
                            + y1tv*y2tv*y1ty2/(y1ty1*y2ty2));       
                    if(L > Lbest){
                        Lbest = L;
                        for(int j = 0; j < 2*T; j++)
                            vbest[j] = v[j];
                    }
                    double Ln = vtvn/(vtvn - y1tvn*y1tvn/y1ty1 - y2tvn*y2tvn/y2ty2
                        + y1tvn*y2tvn*y1ty2/(y1ty1*y2ty2));
                    if(Ln > Lbest){
                        Lbest = Ln;
                        for(int j = 0; j < 2*T; j++)
                            vbest[j] = v[j];
                        vbest[i] -= 2;    
                    }

                    int n = sorted[m].index;
                    double s = Math.signum(d[n]);

                    //update the dot products
                    y1tv += 2*s*y1[n];
                    y2tv += 2*s*y2[n];
                    vtv += 4*s*v[n] + 4;
                    y1tvn += 2*s*y1[n];
                    y2tvn += 2*s*y2[n];
                    vtvn += 4*s*v[n] + 4;

                    v[n] += 2*s;
                }

            }
            
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
