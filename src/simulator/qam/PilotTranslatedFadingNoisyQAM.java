/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.qam;

import simulator.SignalGenerator;

/**
 * This sends a block of fading noisy QAM symbols with each symbol
 * translated by a pilot symbol.  This can then be decoced by a noncoherent
 * reciever.  I am tentatively calling this scheme this Pilot Translated QAM.
 * @author Robby McKilliam
 */
public class PilotTranslatedFadingNoisyQAM extends PilotAssistedFadingNoisyQAM
        implements SignalGenerator, PATSymbol{
    
     /** {@inheritDoc} */
    public PilotTranslatedFadingNoisyQAM() {
        super();
    }
    
     /** {@inheritDoc} */
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
            xr[i] = 2*random.nextInt(M) - M + 1 + realPATSymbol;
            xi[i] = 2*random.nextInt(M) - M + 1 + imagPATSymbol;
        }
    }

}
