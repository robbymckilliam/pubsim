/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.qam.pat;

import simulator.Complex;
import simulator.SignalGenerator;
import simulator.qam.FadingNoisyQAM;

/**
 * Returns a set of QAM symbols that have each been translated
 * by a predetermined sequence of pilot symbols.
 * @author Robby
 */
public class PilotSequencedFadingNoisyQAM extends FadingNoisyQAM
        implements SignalGenerator {
    
     /** {@inheritDoc} */
    public PilotSequencedFadingNoisyQAM() {
        super();
    }
    
     /** {@inheritDoc} */
    public PilotSequencedFadingNoisyQAM(int M) {
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
