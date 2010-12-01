/*
 * PATReceiver.java
 *
 * Created on 27 November 2007, 11:35
 */

package pubsim.qam.pat;

import pubsim.Complex;
import pubsim.qam.*;

/**
 *
 * @author Robby McKilliam
 */
public class PATReceiver implements QAMReceiver, PATSymbol {
    
    /** size of the QAM array will be M^2 */
    protected int M;
    
    /** block length used be the receiver */
    protected int T;
    
    protected double[] dreal, dimag;
    
    /** {@inheritDoc} */
    public void setQAMSize(int M){ this.M = M; }
    
    /** {@inheritDoc} */
    public void setT(int T){
        this.T = T;
        dreal = new double[T];
        dimag = new double[T];
    }
    
    /** {@inheritDoc} */
    public void decode(double[] rreal, double[] rimag){
        
        //calculate channel estimate from PAT sybol
        double absp = PAT.re()*PAT.re() + PAT.im()*PAT.im();
        double Hreal = (PAT.re()*rreal[0] + PAT.im()*rimag[0])/absp;
        double Himag = (PAT.re()*rimag[0] - PAT.im()*rreal[0])/absp;
        
        //calculate inverse of the channel
        double absH = Hreal*Hreal + Himag*Himag;
        double invHreal =  Hreal/absH;
        double invHimag = -Himag/absH;
        
        dreal[0] = PAT.re();
        dimag[0] = PAT.im();
        for(int i = 1; i < T; i++){
            double xr = invHreal*rreal[i] - invHimag*rimag[i];
            double xi = invHreal*rimag[i] + invHimag*rreal[i];
            dreal[i] = Math.max(Math.min(M-1, 2*Math.round((xr+1)/2) - 1),-M+1);
            dimag[i] = Math.max(Math.min(M-1, 2*Math.round((xi+1)/2) - 1),-M+1);
        }
        
    }
    
    /** {@inheritDoc} */
    public double[] getReal() { return dreal; }
    
    /** {@inheritDoc} */
    public double[] getImag() { return dimag; }
    
    
    
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

    public void setChannel(Complex h) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
