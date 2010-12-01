/*
 * T2LogTSubOptimalV3.java
 *
 * Created on 11 October 2007, 11:53
 */

package pubsim.qam;

import java.util.Arrays;
import pubsim.IndexedDouble;
import pubsim.VectorFunctions;

/**
 * Same as T2LogTSubOptimalV2 but uses Dan's simpler calculation of the
 * likelihood function.
 * @author Robby McKilliam
 */
public class T2LogTSubOptimalV3 extends T2LogTSubOptimal implements  QAMReceiver{
    
    /**
     * Default, numL = 1.0
     */
    public T2LogTSubOptimalV3() { numL = 1.0; }
    
    /**
     * Set number of lines used in the line search
     */
    public T2LogTSubOptimalV3(double numL) { this.numL = numL; }
    
    
    protected IndexedDouble[] sorted;
    
    /** Set the size of the QAM array */
    @Override
    public void setQAMSize(int M){ this.M = M; }
    
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
    @Override
    public void decode(double[] rreal, double[] rimag){
        if( rreal.length != T )
            setT(rreal.length);
        
        createPlane(rreal, rimag, y1, y2);
        
        double Lopt = Double.NEGATIVE_INFINITY;
        double thetaopt = 0.0, dopt = 0.0;
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
            double beta = VectorFunctions.sum2(x);
            double ar = 0.0, ai = 0.0;
            for(int j = 0; j < T; j++){
                ar += x[2*j]*rreal[j] + x[2*j+1]*rimag[j];
                ai += x[2*j]*rimag[j] - x[2*j+1]*rreal[j];
            }
            
            //test the likelihood at this point
            double L = (ar*ar + ai*ai)/beta;
            if(L > Lopt){
                Lopt = L;
                thetaopt = theta;
                dopt = sorted[0].value/2.0;
                //System.arraycopy(x,0,xopt,0,2*T);
            }
            
            
            //run the search loop
            for(int m = 0; m < sorted.length; m++){
                
                int n = sorted[m].index;
                double s = Math.signum(d[n]);
                
                //update likelihood variables
                beta += 4*s*x[n] + 4;
                ar += 2*s*y1[n];
                ai -= 2*s*y2[n];
                x[n] += 2*s;
                
                //test the likelihood at this point
                L = (ar*ar + ai*ai)/beta;
                if(L > Lopt){
                    Lopt = L;
                    thetaopt = theta;
                    if(m != sorted.length-1)
                        dopt = (sorted[m].value + sorted[m+1].value)/2;
                    else
                        dopt = sorted[m].value + 1.0;
                    //System.arraycopy(x,0,xopt,0,2*T);
                }
                
            }
        }
        
        double a = Math.cos(thetaopt);
        double b = Math.sin(thetaopt);
        for(int i = 0; i < 2*T; i++){
            d[i] = a*y1[i] + b*y2[i];
            xopt[i] = dopt*d[i];
        }
        NN(xopt,xopt);
        
        //Write the best codeword into real and
        //imaginary vectors
        toRealImag(xopt, dreal, dimag);
        
    }
    
}