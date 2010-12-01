/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.pat;

import pubsim.Complex;
import pubsim.qam.*;
import pubsim.VectorFunctions;

/**
 * This is the O(T^3) version of the Pilot Tranlated non coherent reciever.
 * @author Robby McKilliam
 */
public class PilotTranslatedT3Optimal extends T3OptimalV3 
        implements  QAMReceiver, PATSymbol {

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
    
    /** Overide Nearest Neigbour to work with the pilot translation */
    @Override
    protected void NN(double[] x, double[] y){
        for(int i = 0; i < x.length; i+=2){
            y[i] = Math.max(Math.min(M-1, 2*Math.round(((x[i]-PAT.re())+1)/2) - 1),-M+1);
            y[i] += PAT.re();
        }
        for(int i = 1; i < x.length; i+=2){
            y[i] = Math.max(Math.min(M-1, 2*Math.round(((x[i]-PAT.im())+1)/2) - 1),-M+1);
            y[i] += PAT.im();
        }
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
                
                double pk, pn;
                if(i%2 == 0) pk = PAT.re();
                else pk = PAT.im();
                if(j%2 == 0) pn = PAT.re();
                else pn = PAT.im();
                
                for(double k = -M + pk; k <= M + pk; k+=2){
                    for(double n = -M + pn; n <= M + pn; n+=2){
                        
                        //2x2 matrix inversion 
                        double det = y1[i]*y2[j] - y1[j]*y2[i];
                        double a = (y2[j]*k - y2[i]*n)/det;
                        double b = (-y1[j]*k + y1[i]*n)/det;
                        
                        //if( a > 0.0 && b > 0.0 ){
                        
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
                            
                        //}
                        
                    }
                }     
            }
        }
        
        //Write the best codeword into real and
        //imaginary vectors
        toRealImag(xopt, dreal, dimag);
        
    }

}
