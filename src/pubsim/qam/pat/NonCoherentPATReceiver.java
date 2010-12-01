/*
 * NonCoherentPATReceiver.java
 *
 * Created on 27 November 2007, 11:36
 */

package pubsim.qam.pat;

import pubsim.qam.*;
import java.util.Arrays;
import pubsim.Complex;
import pubsim.VectorFunctions;

/**
 * Noncoherent receiver with a PAT symbol to remove ambiguities.
 * This is a slow version of the receiver that simply checks all
 * codewords on the plane and only returns those that satisfy the
 * PAT symbol.  A faster version would only check codewords that
 * are going to satisfy the PAT symbol.
 * <p>
 * This wont actually work all that well anyway.  Just improving
 * the estimate of the channel doesn't give that much gain.  What
 * you can do is remove the ambiguities in such a way as to increase
 * the separation between codewords (ie coding).  This is what Dan
 * does for his RA tranmission scheme.  Whether this can be made
 * more general (ie not just 16-QAM) I don't know.
 * @author Robby McKilliam
 */
public class NonCoherentPATReceiver extends T2LogTOptimalV3 
        implements QAMReceiver, PATSymbol {
    
    /** {@inheritDoc} */
    @Override
    public void setQAMSize(int M){ this.M = M; }
    
    /** {@inheritDoc} */
    @Override
    public void decode(double[] rreal, double[] rimag){
        if( rreal.length != T )
            setT(rreal.length);
        
        createPlane(rreal, rimag, y1, y2);
     
        //set initial value likelihood
        Lopt = Double.NEGATIVE_INFINITY;
        aopt = 0.0; 
        topt = 0; sopt = 0; kopt = 0;
        
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
        
        //rotate so that the first symbol is the pilot
        double absd0 = dreal[0]*dreal[0] + dimag[0]*dimag[0];
        double invd0r = dreal[0]/absd0;
        double invd0i = -dimag[0]/absd0;
        
        double mr = PAT.re()*invd0r - PAT.im()*invd0i;
        double mi = PAT.re()*invd0i + PAT.im()*invd0r;
        
        //System.out.println("m = " + mr + " + i" + mi);
        //System.out.println("d0 = " + dreal[0] + " + i" + dimag[0]);
        //System.out.println((dreal[0]*mi + dimag[0]*mr));
        
        
        for(int i = 0; i < T; i++){
            double drtemp = dreal[i];
            double ditemp = dimag[i];
            dreal[i] = drtemp*mr - ditemp*mi;
            dimag[i] = drtemp*mi + ditemp*mr;
        }
          
        
    }
    
    /** The PAT symbol used */
    protected Complex PAT;

    public void setPATSymbol(double real, double imag) {
        PAT = new Complex(real, imag);
    }

    public void setPATSymbol(Complex c) {
        PAT = new Complex(c);
    }

    public Complex getPATSymbol() {
        return PAT;
    }

    
}
