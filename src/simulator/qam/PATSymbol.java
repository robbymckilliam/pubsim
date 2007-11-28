/*
 * PATSymbol.java
 *
 * Created on 27 November 2007, 11:59
 */

package simulator.qam;

/**
 *
 * @author Robby
 */
public interface PATSymbol {
    
    /** Set the pilot symbol used */
    public void setPATSymbol(double real, double imag);
    
    public double getImagPatSymbol();
    public double getRealPatSymbol();
    
}
