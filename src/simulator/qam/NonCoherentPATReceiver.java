/*
 * NonCoherentPATReceiver.java
 *
 * Created on 27 November 2007, 11:36
 */

package simulator.qam;

import java.util.Arrays;
import simulator.VectorFunctions;

/**
 * Noncoherent receiver with a PAT symbol to remove ambiguities.
 * This is a slow version of the receiver that simply checks all
 * codewords on the plane and only returns those that satisfy the
 * PAT symbol.  A faster version would only check codewords that
 * are going to satisfy the PAT symbol.
 * @author Robby McKilliam
 */
public class NonCoherentPATReceiver extends T2LogTOptimalV3 
        implements QAMReceiver, PATSymbol {
    
    /** {@inheritDoc} */
    public void setQAMSize(int M){ this.M = M; }
    
    /** {@inheritDoc} */
    public void decode(double[] rreal, double[] rimag){
        if( rreal.length != T )
            setT(rreal.length);
        
        createPlane(rreal, rimag, y1, y2);
     
        //set initial value likelihood
        Lopt = Double.NEGATIVE_INFINITY;
        aopt = 0.0; 
        topt = 0; sopt = 0; kopt = 0;
        
        //for each type of line
        for(int t = 0; t < 2*T; t++){
            
            //calculate gradient for the 
            //line we are searching
            for(int j = 0; j < 2*T; j++)
                d[j] = y1[j] - y1[t]*y2[j]/y2[t];
            
            //for the parallel lines of this type
            //only use the non-negative lines, this
            //removes half the ambiguities.
            for(int k = -M+2; k <= M-2; k+=2){

                //calculate offset for the 
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
                
                updateL(sorted[0].value - 1.0, t, k, ar, ai, b);

                //run the search loop
                for(int m = 0; m < sorted.length-1; m++){                

                    int td = sorted[m].index;
                    double s = Math.signum(d[td]);

                    //update likelihood variables
                    b += 4*s*x[td] + 4;
                    ar += 2*s*y1[td];
                    ai -= 2*s*y2[td];
                    x[td] += 2*s;
                    
                    if(!(k==0 && td==0 && sorted[m+1].index==0))
                        updateL((sorted[m].value + sorted[m+1].value)/2, t, k, 
                            ar, ai, b);

                }
                
                int m = sorted.length - 1;
                int td = sorted[m].index;
                double s = Math.signum(d[td]);

                //update likelihood variables
                b += 4*s*x[td] + 4;
                ar += 2*s*y1[td];
                ai -= 2*s*y2[td];
                x[td] += 2*s;
                
                updateL(sorted[m].value + 1.0, t, k, ar, ai, b);

            }
            
        }
        
        //calculate the closest point from stored
        //variables.  This must be done this way
        //to gaurantee 0(T^2 log(T)) running time.
        for(int j = 0; j<2*T; j++){
            d[j] = y1[j] - y1[topt]*y2[j]/y2[topt];
            c[j] = kopt*y2[j]/y2[topt];
            xopt[j] = aopt*d[j] + c[j];
        }
        NN(xopt, xopt);
        xopt[topt] = sopt;
        
        //Write the best codeword into real and
        //imaginary vectors
        toRealImag(xopt, dreal, dimag);
    }
    
    /** 
     * {@inheritDoc} 
     * Only update the best codeword is the PAT symbol is satisfied
     */
    protected void updateL(double aL, int t, int k,
            double ar, double ai, double b){
        
        //only update if the PAT symbol is satisfied
        if(x[0] == realPATSymbol && x[1] == imagPATSymbol){        
            //test the likelihood of the codeword on each
            //side of the line, runs in constant time.
            double L = (ar*ar + ai*ai)/b;       
            if(L > Lopt){
                Lopt = L;
                aopt = aL;
                topt = t;
                kopt = k;
                sopt = k+1;
                //System.arraycopy(x,0,xopt,0,2*T);
                //xopt[t] = k+1;
            }
            double ard = ar - 2*y1[t];
            double aid = ai + 2*y2[t];
            double bd = b - 4*x[t] + 4;
            double Ln = (ard*ard + aid*aid)/bd;
            if(Ln > Lopt){
                Lopt = Ln;
                aopt = aL;
                topt = t;
                kopt = k;
                sopt = k-1;
                //System.arraycopy(x,0,xopt,0,2*T);
                //xopt[t] = k-1;
            }         
        }
    }
    
    
    protected double realPATSymbol, imagPATSymbol;
    
    /** {@inheritDoc} */
    public void setPATSymbol(double real, double imag){
        realPATSymbol = real;
        imagPATSymbol = imag;
    }
    
    public double getImagPatSymbol() { return imagPATSymbol; }
    public double getRealPatSymbol() { return realPATSymbol; }
    
}
