/*
 * PilotAssistedFadingNoisyQAM.java
 *
 * Created on 27 November 2007, 11:20
 */

package simulator.qam;

import simulator.SignalGenerator;

/**
 * A fading noisy M^2-ary QAM symbol with a single pilot symbol.  The pilot
 * symbol is the first transmitted symbol
 * @author Robby McKilliam
 */
public class PilotAssistedFadingNoisyQAM extends FadingNoisyQAM 
        implements SignalGenerator, PATSymbol {
    
    protected double realPATSymbol, imagPATSymbol;
    
    /** Default constructor using 8-ary QAM */
    public PilotAssistedFadingNoisyQAM() {
        super();
    }
    
    /** Parameter is the QAM size */
    public PilotAssistedFadingNoisyQAM(int M) {
        super(M);
    }
    
    /** Set the pilot symbol used */
    public void setPATSymbol(double real, double imag){
        realPATSymbol = real;
        imagPATSymbol = imag;
    }
    
    public double getImagPatSymbol() { return imagPATSymbol; }
    public double getRealPatSymbol() { return realPATSymbol; }
    
    /** 
     * Generate a random QAM signal of the currently
     * specified length.  The first symbol is the pilot
     */
    @Override
    public void generateQAMSignal(){
        xr[0] = realPATSymbol;
        xi[0] = imagPATSymbol;
        for(int i=1; i < T; i++){
            xr[i] = 2*random.nextInt(M) - M + 1;
            xi[i] = 2*random.nextInt(M) - M + 1;
        }
    }
    
}
