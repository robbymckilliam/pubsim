/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.psk.decoder;

import simulator.Complex;

/**
 * Generates a a PSK signal with each symbol translated by
 * a pilot symbol.
 * @author Robby McKilliam
 */
public class TranslatedPSKSignal extends PSKSignal 
        implements simulator.qam.pat.PATSymbol{
    
    /** 
     * Generates the received QAM symbols with the pilot symbol
     * added to each symbol.
     */
    @Override
    public double[] generateReceivedSignal() {
        for(int i=0; i < y.length; i++){
            Complex xc = new Complex(Math.cos(2*Math.PI*x[i]/M), 
                                     Math.sin(2*Math.PI*x[i]/M));
            Complex nos = new Complex(noise.getNoise(), noise.getNoise());
            y[i] = h.times(xc).plus(nos).plus(PAT);
        }
        return null;
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
