/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.psk.decoder;

import simulator.psk.decoder.*;
import simulator.Complex;

/**
 * Standard differential noncoherent PSK decoder.  This is not
 * maximum likelihood like the LinearTimeNocoherent and 
 * SweldensNoncoherent.
 * @author Robby McKilliam
 */
public class DifferentialDecoder implements PSKReceiver {
    
    double[] x;
    int T, M;

    public void setM(int M) {
        this.M = M;
    }

    public void setT(int T) {
        this.T = T;
        x = new double[T];
    }

    public double[] decode(Complex[] y) {
        if(y.length != T) setT(y.length);
        
        x[0] = 0.0;
        for(int i = 1; i < T; i++){
            int d = (int)Math.round(M/(2*Math.PI)*(y[i].phase() - y[i-1].phase())); 
            x[i] = x[i-1] + Util.mod(d, M);
        }
        
        return x;
    }

    public int bitErrors(double[] x) {
        return Util.differentialEncodedBitErrors(this.x, x, M);
    }

    public int bitsPerCodeword() {
        return (int)Math.round((T-1)*Math.log(M)/Math.log(2));
    }

    public void setChannel(Complex h) {
    }

}
