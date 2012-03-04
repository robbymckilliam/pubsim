/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.pat;

import pubsim.Complex;
import pubsim.qam.*;
import pubsim.qam.pat.PATSymbol;
import pubsim.SignalGenerator;

/**
 * This sends a block of fading noisy QAM symbols with each symbol
 * translated by a pilot symbol.  This can then be decoced by a noncoherent
 * reciever.  I am tentatively calling this scheme this Pilot Translated QAM.
 * @author Robby McKilliam
 */
public class PilotTranslatedFadingNoisyQAM extends FadingNoisyQAM
        implements PATSymbol {
    
    public PilotTranslatedFadingNoisyQAM() {
        super();
    }
    
    public PilotTranslatedFadingNoisyQAM(int M) {
        super(M);
    }
    
    /** 
     * Generate a random QAM signal of the currently
     * specified length.  The pilot is used to translate each.
     * Note that the pilot does not need to be a QAM symbol!
     * It can be anything.  Infact, analysis suggests that the
     * power of this symbol can be very small.
     */
    @Override
    public void generateQAMSignal(){
        for(int i=0; i < T; i++){
            xr[i] = 2*random.nextInt(M) - M + 1 + PAT.re();
            xi[i] = 2*random.nextInt(M) - M + 1 + PAT.im();
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
