/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam;

import pubsim.Complex;
/**
 *
 * @author Robby McKilliam
 */
public class CoherentQAM implements QAMReceiver{

    private int M, T;

    private double[] ur, ui;
    
    private Complex invh;

    public CoherentQAM(int T, int M){
        setQAMSize(M);
        setT(T);
    }

    public void setQAMSize(int M) {
        this.M = M;
    }

    public void setT(int T) {
        this.T = T;

        ur = new double[T];
        ui = new double[T];    

    }

    public void decode(double[] rreal, double[] rimag) {
        for(int i = 0; i < T; i++){
            Complex sym = invh.times(new Complex(rreal[i], rimag[i]));
            ur[i] = Math.max(Math.min(M-1, 2*Math.round((sym.re()+1)/2) - 1),-M+1);
            ui[i] = Math.max(Math.min(M-1, 2*Math.round((sym.im()+1)/2) - 1),-M+1);
        }
    }

    public double[] getReal() {
        return ur;
    }

    public double[] getImag() {
        return ui;
    }

    public void setChannel(Complex h) {
        invh = h.reciprocal();
    }



}
