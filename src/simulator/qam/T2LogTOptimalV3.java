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
        xbest = new double[2*T];
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
                for(int j = 0; j < 2*T; j++)
                    x[j] = -Math.signum(d[j])*(M-1);
                x[i] = k + 1;

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
                if(L > Lbest){
                    Lbest = L;
                    System.arraycopy(x, 0, xbest, 0, 2*T);
                }
                double ard = ar - 2*y1[i];
                double aid = ai + 2*y2[i];
                double bd = b - 4*x[i] + 4;
                double Ln = (ard*ard + aid*aid)/bd;
                if(Ln > Lbest){
                    Lbest = Ln;
                    System.arraycopy(x, 0, xbest, 0, 2*T);
                    xbest[i] -= 2;    
                }

                //run the search loop
                for(int m = 0; m < sorted.length; m++){                

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
                    if(L > Lbest){
                        Lbest = L;
                        System.arraycopy(x, 0, xbest, 0, 2*T);
                    }
                    ard = ar - 2*y1[i];
                    aid = ai + 2*y2[i];
                    bd = b - 4*x[i] + 4;
                    Ln = (ard*ard + aid*aid)/bd;
                    if(Ln > Lbest){
                        Lbest = Ln;
                        System.arraycopy(x, 0, xbest, 0, 2*T);
                        xbest[i] -= 2;    
                    }
                }

            }
            
        }
        
        //Write the best codeword into real and
        //imaginary vectors
        toRealImag(xbest, dreal, dimag);
        
    }
    
}