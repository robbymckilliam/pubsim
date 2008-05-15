/*
 * SymbolCorrection.java
 *
 * Created on 13 December 2007, 11:26
 */

package simulator.psk;

/**
 * Corrects the symbols for the frequency
 * and phase of the carrier.  Assuming that
 * the carrier frequency and phase are correctly
 * estimated then this returns u + eta where u
 * are the transmitted signals and eta is ataned
 * (wrapped) noise in teh range [-0.5, 0.5]. 
 * @author Robby McKilliam
 */
public class SymbolCorrection {
    
    protected double f, p;
    protected double[] cor;
    
    /** Set the correction frequency */
    public void setF(double f) { this.f = f; }
    
    /** Set the correction phase */
    public void setP(double p) { this.p = p; }
    
    /** 
     * Correct for the frequency and phase and 
     * return the corrected array u + eta.
     */
    public double[] getCorrection(double[] s){
        if( cor == null || cor.length != s.length)
            cor = new double[s.length];
        
        for(int i = 0; i < s.length; i++){
            double d = f*i + p;
            cor[i] = s[i] - (d - Math.rint(d));
        }
        
        return cor;
    }
    
}
