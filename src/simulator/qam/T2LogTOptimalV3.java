/*
 * T2LogTOptimalV3.java
 *
 * Created on 11 October 2007, 11:53
 */

package simulator.qam;

import java.util.Arrays;
import simulator.Complex;
import simulator.VectorFunctions;

/**
 * Same as T2LogTOptimalV2 but uses Dan's simpler calculation of the 
 * likelihood function.
 * @author Robby
 */
public class T2LogTOptimalV3 extends T2LogTOptimal implements  QAMReceiver {
    
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
     
        double Lopt = Double.NEGATIVE_INFINITY;
        double aopt = 0.0, bopt = 0.0; 
        int topt = 0, kopt = 0;
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
                double b = VectorFunctions.sum2(x);
                double ar = 0.0, ai = 0.0;
                for(int j = 0; j < T; j++){
                    ar += x[2*j]*rreal[j] + x[2*j+1]*rimag[j];
                    ai += x[2*j]*rimag[j] - x[2*j+1]*rreal[j];
                }
                
                //test the likelihood of the codeword on each
                //side of the line, runs in constant time.
                double L = (ar*ar + ai*ai)/b;       
                if(L > Lopt){
                    Lopt = L;
                    aopt = (sorted[0].value - 1.0)/2;
                    bopt = k/y2[t] - aopt*y1[t]/y2[t];
                    topt = t;
                    kopt = k+1;
                    //System.arraycopy(x, 0, xopt, 0, 2*T);
                }
                double ard = ar - 2*y1[t];
                double aid = ai + 2*y2[t];
                double bd = b - 4*x[t] + 4;
                double Ln = (ard*ard + aid*aid)/bd;
                if(Ln > Lopt){
                    Lopt = L;
                    aopt = (sorted[0].value - 1.0)/2;
                    bopt = k/y2[t] - aopt*y1[t]/y2[t];
                    topt = t;
                    kopt = k-1;
                    //System.arraycopy(x, 0, xopt, 0, 2*T);
                    //xopt[t] -= 2;    
                }

                //run the search loop
                for(int m = 0; m < sorted.length-1; m++){                

                    int n = sorted[m].index;
                    double s = Math.signum(d[n]);

                    //update likelihood variables
                    b += 4*s*x[n] + 4;
                    ar += 2*s*y1[n];
                    ai -= 2*s*y2[n];
                    x[n] += 2*s;
                    
                    //test the likelihood of the codeword on each
                    //side of the line, runs in constant time.
                    L = (ar*ar + ai*ai)/b;       
                    if(L > Lopt){
                        Lopt = L;
                        aopt = (sorted[m].value + sorted[m+1].value)/2;
                        bopt = k/y2[t] - aopt*y1[t]/y2[t];
                        topt = t;
                        kopt = k+1;
                        //System.arraycopy(x, 0, xopt, 0, 2*T);
                    }
                    ard = ar - 2*y1[t];
                    aid = ai + 2*y2[t];
                    bd = b - 4*x[t] + 4;
                    Ln = (ard*ard + aid*aid)/bd;
                    if(Ln > Lopt){
                        Lopt = L;
                        aopt = (sorted[m].value + sorted[m+1].value)/2;
                        bopt = k/y2[t] - aopt*y1[t]/y2[t];
                        topt = t;
                        kopt = k-1;
                        //System.arraycopy(x, 0, xopt, 0, 2*T);
                        //xopt[t] -= 2;    
                    }
                }
                
                int m = sorted.length-1;
                int n = sorted[m].index;
                double s = Math.signum(d[n]);
                
                //update likelihood variables
                b += 4*s*x[n] + 4;
                ar += 2*s*y1[n];
                ai -= 2*s*y2[n];
                x[n] += 2*s;

                //test the likelihood of the codeword on each
                //side of the line, runs in constant time.
                L = (ar*ar + ai*ai)/b;       
                if(L > Lopt){
                    Lopt = L;
                    aopt = (sorted[m].value + 1.0)/2;
                    bopt = k/y2[t] - aopt*y1[t]/y2[t];
                    topt = t;
                    kopt = k+1;
                    //System.arraycopy(x, 0, xopt, 0, 2*T);
                }
                ard = ar - 2*y1[t];
                aid = ai + 2*y2[t];
                bd = b - 4*x[t] + 4;
                Ln = (ard*ard + aid*aid)/bd;
                if(Ln > Lopt){
                    Lopt = L;
                    aopt = (sorted[m].value + 1.0)/2;
                    bopt = k/y2[t] - aopt*y1[t]/y2[t];
                    topt = t;
                    kopt = k-1;
                    //System.arraycopy(x, 0, xopt, 0, 2*T);
                    //xopt[t] -= 2;    
                }

            }
            
        }
        
        //calculate the closest point from stored
        //variables.  This must be dont this way
        //to gaurantee 0(T^2 log(T)) running time.
        for(int j = 0; j<2*T; j++)
            xopt[j] = aopt*y1[j] + bopt*y2[j];
        NN(xopt, xopt);
        xopt[topt] = kopt;
        
        //Write the best codeword into real and
        //imaginary vectors
        toRealImag(xopt, dreal, dimag);
        
    }
    
}