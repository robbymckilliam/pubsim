/*
 * T3OptimalV3.java
 *
 * Created on 11 October 2007, 11:53
 */

package pubsim.qam;

import pubsim.VectorFunctions;

/**
 * Same as T3Optimal but uses Dan's simpler calculation of the
 * likelihood function.
 * @author Robby McKilliam
 */
public class T3OptimalV3 extends T3Optimal implements  QAMReceiver {
    
    /** Creates a new instance of T3OptimalNonCoherentReciever */
    public T3OptimalV3() {
    }
    
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
        dreal = new double[T];
        dimag = new double[T];
        
    }
    
    /**Decode the QAM signal*/
    @Override
    public void decode(double[] rreal, double[] rimag){
        if( rreal.length != T )
            setT(rreal.length);
        
        createPlane(rreal, rimag, y1, y2);
        
        //Dan's small offset to ensure we translate off a nearest
        //neighbour boundry.
        double e = 0.000001;
        double Lbest = Double.NEGATIVE_INFINITY;
        
        for(int i = 0; i < 2*T-1; i++){
            for(int j = i+1; j < 2*T; j++){
                for(int k = -M; k <= M; k+=2){
                    for(int n = -M; n <= M; n+=2){
                        
                        //2x2 matrix inversion 
                        double det = y1[i]*y2[j] - y1[j]*y2[i];
                        double a = (y2[j]*k - y2[i]*n)/det;
                        double b = (-y1[j]*k + y1[i]*n)/det;
                        
                        if( a > 0.0 && b > 0.0 ){
                        
                            //run for positive and negative e
                            for(double ve = e; ve >= -1.1*e; ve-=2*e){
                                for(int ii=0; ii < 2*T; ii++)
                                    x[ii] = (a+ve)*y1[ii] + (b+ve)*y2[ii];
                                NN(x,x);

                                double ar = 0.0, ai = 0.0;
                                for(int ii = 0; ii < T; ii++){
                                    ar += x[2*ii]*rreal[ii] + x[2*ii+1]*rimag[ii];
                                    ai += x[2*ii]*rimag[ii] - x[2*ii+1]*rreal[ii];
                                }
                                double L = (ar*ar + ai*ai)/VectorFunctions.sum2(x);
                                //double L = VectorFunctions.angle_between(x,vp);
                                //double L = VectorFunctions.distance_between(x,vp);
                                if(L > Lbest){
                                    Lbest = L;
                                    System.arraycopy(x, 0, xopt, 0, 2*T);
                                }
                            }
                            
                        }
                        
                    }
                }     
            }
        }
        
        //Write the best codeword into real and
        //imaginary vectors
        toRealImag(xopt, dreal, dimag);
        
    }
    
}