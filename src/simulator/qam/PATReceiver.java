/*
 * PATReceiver.java
 *
 * Created on 27 November 2007, 11:35
 */

package simulator.qam;

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
        double absp = realPATSymbol*realPATSymbol + imagPATSymbol*imagPATSymbol;
        double Hreal = (realPATSymbol*rreal[0] + imagPATSymbol*rimag[0])/absp;
        double Himag = (realPATSymbol*rimag[0] - imagPATSymbol*rreal[0])/absp;
        
        //calculate inverse of the channel
        double absH = Hreal*Hreal + Himag*Himag;
        double invHreal =  Hreal/absH;
        double invHimag = -Himag/absH;
        
        dreal[0] = realPATSymbol;
        dimag[0] = imagPATSymbol;
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
    
    
    protected double realPATSymbol, imagPATSymbol;
    
    /** {@inheritDoc} */
    public void setPATSymbol(double real, double imag){
        realPATSymbol = real;
        imagPATSymbol = imag;
    }
    
    public double getImagPatSymbol() { return imagPATSymbol; }
    public double getRealPatSymbol() { return realPATSymbol; }
    
}
