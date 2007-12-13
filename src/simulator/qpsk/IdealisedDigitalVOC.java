/*
 * IdealisedDigitalVOC.java
 *
 * Created on 13 December 2007, 10:43
 */

package simulator.qpsk;

/**
 * This emulates an idealised VOC and heterodyning
 * filter on a real QPSK reciever.  Really this should
 * take a real signal as input and use low pass filters.
 * Which is practice would cause Gibbs effect of the
 * transmitted QPSK signals.
 * <p>
 * Returns the phase of the signal normalised between
 * [-0.5, 0.5]
 * @author Robby McKilliam
 */
public class IdealisedDigitalVOC {
    
    protected double fc;
    protected double[] arg;
    
    /**
     * Creates a new instance of IdealisedDigitalVOC
     * Can specify the frequency of the VOC.
     */
    public IdealisedDigitalVOC(double fc) {
        this.fc = fc;
    }
    
    /** Default constructor */
    public IdealisedDigitalVOC() {}
    
    /** Set the fixed frequency used for the VOC */
    public void setFc(double fc){ this.fc = fc; }
  
    /**
     * Shift the signal down -fc and then takes the argument.
     * Note that fc is the assumed frequency of the signal.
     * In reality the fc will be altered by doppler and clock skew.
     * This is accounted for by latter proccessing (ie frequency
     * estimation via the Pn2 lattice).
     */
    public double[] getPhaseSignal(double[] real, double[] imag){
        if( arg == null || arg.length != real.length)
            arg = new double[real.length];
        
        for(int i = 0; i < real.length; i++){
            double re = real[i]*Math.cos(-Math.PI*2*fc*i) 
                        - imag[i]*Math.sin(-Math.PI*2*fc*i);
            double im = real[i]*Math.sin(-Math.PI*2*fc*i)
                        + imag[i]*Math.cos(-Math.PI*2*fc*i);
            arg[i] = Math.atan2(im, re)/(2*Math.PI);
        }
        return arg;
    }
      
}
