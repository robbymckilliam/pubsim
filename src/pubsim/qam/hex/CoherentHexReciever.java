/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.hex;

import pubsim.Complex;

/**
 *
 * @author Robby McKilliam
 */
public class CoherentHexReciever implements HexReciever{

    private Complex invh;
    private final int N;
    private final HexagonalCode hex;

    private double[] ur, ui;

    /**
     *
     * @param N block length
     * @param scale Voronoi code scale. The constelation size is scale^2
     * @param h channel coefficient
     */
    public CoherentHexReciever(int N, int scale){
        this.N = N;
        invh = new Complex(1.0,0.0);
        hex = new HexagonalCode(scale);
        ur = new double[N];
        ui = new double[N];
    }

    public void decode(double[] rreal, double[] rimag) {
        for(int n = 0; n < N; n++){
            Complex sym = invh.times(new Complex(rreal[n], rimag[n]));
            double[] u = hex.decode(sym);
            ur[n] = u[0];
            ui[n] = u[1];
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
