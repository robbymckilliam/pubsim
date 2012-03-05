/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.pat;

import pubsim.Complex;
import pubsim.SignalGenerator;
import pubsim.qam.FadingNoisyQAM;

/**
 * Returns a set of QAM symbols that have each been translated
 * by a predetermined sequence of pilot symbols.
 * @author Robby
 */
public class PilotSequencedFadingNoisyQAM extends FadingNoisyQAM {
    
    public PilotSequencedFadingNoisyQAM() {
        super();
    }
    
    public PilotSequencedFadingNoisyQAM(int M) {
        super(M);
    }
    
    /** 
     * Generate a random QAM signal of the currently
     * specified length.  The pilot is used to translate each.
     * Note that the pilot does not need to be a QAM symbol!
     * It can be anything.  In fact, analysis suggests that the
     * power of this symbol can be very small.
     */
    @Override
    public void generateQAMSignal(){
        PATseq.setPosition(0);
        Complex pat = PATseq.current();
        for(int i=0; i < T; i++){
            xr[i] = 2*random.nextInt(M) - M + 1 + pat.re();
            xi[i] = 2*random.nextInt(M) - M + 1 + pat.im();
            pat = PATseq.next();
        }
    }
    
    protected PilotSequence PATseq;
    
    public void setPilotSequence(PilotSequence ps){
        PATseq = ps;
    }

}
