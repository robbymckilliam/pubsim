/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.psk.decoder;

import simulator.Complex;
import simulator.VectorFunctions;

/**
 * PSK reviever with perfect channel information
 * @author Robby McKilliam
 */
public class KnownCSIReciever implements PSKReceiver{

    int M, T;
    /** The known channel */
    Complex h;
    /** The decoded signal */
    double[] x;
    
    public KnownCSIReciever(){
        setM(4);
    }
    
    public KnownCSIReciever(int M){
        setM(M);
    }
    
    
    public void setM(int M) {
        this.M = M;
    }

    public void setT(int T) {
        this.T = T;
        x = new double[T];
    }
    
    public void setChannel(Complex h){
        this.h = h;
    }

    public double[] decode(Complex[] y) {
        
        for(int i = 0; i < T; i++){
            double phase = (y[i].divides(h)).phase();
            //System.out.println(M/(2*Math.PI)*phase);
            x[i] = Util.mod((int)Math.round(M/(2*Math.PI)*phase), M);
        }
        
        return x;
    }

    public int bitErrors(double[] x) {
        return Util.bitErrors(this.x, x, M);
    }

    public int bitsPerCodeword() {
        return (int)Math.round(T*Math.log(M)/Math.log(2));
    }
    
    public int symbolErrors(double[] x) {
        return Util.SymbolErrors(this.x, x, M);
    }

    public boolean codewordError(double[] x) {
        return Util.codewordError(this.x, x, M);
    }
    
    

}
