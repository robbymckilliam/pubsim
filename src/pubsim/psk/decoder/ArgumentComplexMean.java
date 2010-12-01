/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.psk.decoder;

import pubsim.Complex;

/**
 *
 * @author Robby McKilliam
 */
public class ArgumentComplexMean implements PSKReceiver{

    double[] u;
    int T, M;

    public ArgumentComplexMean(){
        setM(4);
    }

    public ArgumentComplexMean(int M){
        setM(M);
    }

    public void setM(int M) {
        this.M = M;
    }

    public void setT(int T) {
        this.T = T;
        u = new double[T];
    }

    
     /** Complute the standard circular mean to find the codewords
     * @param y the PSK symbols
     * @return the index of the nearest lattice point
     */
    public double[] decode(Complex[] y) {
        if(y.length != T) setT(y.length);

        //compute the estimate of the angle
        double real = 0.0, imag = 0.0;
        for(int i = 0; i < T; i++){
            double phase = M*y[i].phase();
            real += Math.cos(phase);
            imag += Math.sin(phase);
        }
        double Mtheta = Math.atan2(imag, real);

        //compute the codewords
        double twopi = 2*Math.PI;
        for(int i = 0; i < T; i++){
            u[i] = Math.round( M*y[i].phase()/twopi - Mtheta/twopi);
        }
        return u;
    }

    public int bitErrors(double[] x) {
        return Util.differentialEncodedBitErrors(u, x, M);
    }

    /** This is a noncoherent reciever so setting the channel does nothing*/
    public void setChannel(Complex h) {  }

    public int bitsPerCodeword() {
        return (int)Math.round((T-1)*Math.log(M)/Math.log(2));
    }

    public int symbolErrors(double[] x) {
        return Util.differentialEncodedSymbolErrors(u, x, M);
    }

    public boolean codewordError(double[] x) {
        return !Util.differentialEncodedEqual(u, x, M);
    }

}
