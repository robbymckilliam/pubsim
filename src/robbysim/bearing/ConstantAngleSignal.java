/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.bearing;

import robbysim.distributions.processes.IIDNoise;
import robbysim.SignalGenerator;
import static robbysim.Util.fracpart;

/**
 * Generate some angular data.
 * Assumes that angles are measure in interval [-1/2, 1/2).
 * @author Robby McKilliam
 */
public class ConstantAngleSignal extends IIDNoise implements SignalGenerator{

    protected double angle = 0.0;

    public ConstantAngleSignal(int length){ super(length); }

    public void setAngle(double angle){
        this.angle = angle;
    }

    public double getAngle(){
        return angle;
    }
    
    /** 
     * Generate the iid noise of length n.
     */
    @Override
    public double[] generateReceivedSignal(){
        if( iidsignal.length != n )
            iidsignal = new double[n];
        for(int i = 0; i < n; i++)
            iidsignal[i] = fracpart(noise.getNoise() + angle);
        return iidsignal;
    }



}
