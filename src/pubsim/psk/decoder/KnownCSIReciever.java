/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.psk.decoder;

import pubsim.Complex;

/**
 * PSK receiver with perfect channel information
 * @author Robby McKilliam
 */
public class KnownCSIReciever implements PSKReceiver{

    int M, T;
    /** The known channel */
    Complex h;
    /** The decoded signal */
    double[] x;
    
    public KnownCSIReciever(int M){
        setM(M);
    }
    
    
    private void setM(int M) {
        this.M = M;
    }

    public void setT(int T) {
        this.T = T;
        x = new double[T];
    }
    
    @Override
    public void setChannel(Complex h){
        this.h = h;
    }

    @Override
    public double[] decode(Complex[] y) {
        
        for(int i = 0; i < T; i++){
            double phase = (y[i].divides(h)).phase();
            //System.out.println(M/(2*Math.PI)*phase);
            x[i] = Util.mod((int)Math.round(M/(2*Math.PI)*phase), M);
        }
        
        return x;
    }

    @Override
    public int bitErrors(double[] x) {
        return Util.bitErrors(this.x, x, M);
    }

    @Override
    public int bitsPerCodeword() {
        return (int)Math.round(T*Math.log(M)/Math.log(2));
    }
    
    @Override
    public int symbolErrors(double[] x) {
        return Util.SymbolErrors(this.x, x, M);
    }

    @Override
    public boolean codewordError(double[] x) {
        return Util.codewordError(this.x, x, M);
    }
    
    

}
