/*
 * PilotAssistedFadingNoisyQAM.java
 *
 * Created on 27 November 2007, 11:20
 */

package pubsim.qam.pat;

import pubsim.Complex;
import pubsim.qam.*;
import pubsim.qam.pat.PATSymbol;
import pubsim.SignalGenerator;

/**
 * A fading noisy M^2-ary QAM symbol with a single pilot symbol.  The pilot
 * symbol is the first transmitted symbol
 * @author Robby McKilliam
 */
public class PilotAssistedFadingNoisyQAM extends FadingNoisyQAM 
        implements PATSymbol {
    
    /** Default constructor using 8-ary QAM */
    public PilotAssistedFadingNoisyQAM() {
        super();
    }
    
    /** Parameter is the QAM size */
    public PilotAssistedFadingNoisyQAM(int M) {
        super(M);
    }

    
    /** 
     * Generate a random QAM signal of the currently
     * specified length.  The first symbol is the pilot
     */
    @Override
    public void generateQAMSignal(){
        xr[0] = PAT.re();
        xi[0] = PAT.im();
        for(int i=1; i < T; i++){
            xr[i] = 2*random.nextInt(M) - M + 1;
            xi[i] = 2*random.nextInt(M) - M + 1;
        }
    }
    
    /** 
     * Return the number of symbol errors between 
     * two QAM blocks x and y.  This ignores the first symbol
     * which is a pilot symbol.
     * PRE: xr.length == xi.length == yr.length == yi.length
     */
    public static double symbolErrorRate(double[] xr, double[] xi,
                                    double[] yr, double[] yi){
        double ers = 0;
        for(int i = 1; i < xr.length; i++)
            if( Math.round(xr[i] - yr[i]) != 0 
                || Math.round(xi[i] - yi[i]) != 0 ) ers++;
        
        return ers/(xr.length-1);
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
