/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.hex;

import Jama.Matrix;
import pubsim.lattices.util.IntegerVectors;
import pubsim.Complex;

/**
 * Tests all codewords to find the ML estimate.  This has
 * exponential complexity in the block length.
 * @author Robby McKilliam
 */
public class BruteForceHexReciever implements HexReciever{

    private final int N, r;
    private final double[] ur, ui;

    /** Hexagonal Voronoi code used */
    protected final HexagonalCode hex;

    public BruteForceHexReciever(int N, int scale) {
        this.hex = new HexagonalCode(scale);
        this.N = N;
        r = scale;
        ur = new double[N];
        ui = new double[N];
    }

    public void decode(double[] rreal, double[] rimag) {
        double Lopt = Double.NEGATIVE_INFINITY;
        for(Matrix ud : new IntegerVectors(2*N, r) ){
            double L = computeLikelihood(ud, rreal, rimag);
            //System.out.println(L);
            if(L > Lopt){
                Lopt = L;
                for(int n = 0; n < N; n++){
                    ur[n] = ud.get(2*n,0);
                    ui[n] = ud.get(2*n+1,0);
                }
            }
        }
    }

    public double[] getReal() {
        return ur;
    }

    public double[] getImag() {
        return ui;
    }

    private double computeLikelihood(Matrix ud,
                        double[] rreal, double[] rimag){
        double im = 0;
        double re = 0;
        double xmag = 0;
        for(int n = 0; n < N; n++){
            double[] x = hex.encode(ud.get(2*n,0), ud.get(2*n+1, 0));
            re += x[0]*rreal[n] + x[1]*rimag[n];
            im += x[0]*rimag[n] - x[1]*rreal[n];
            xmag += x[0]*x[0] + x[1]*x[1];
        }
        return (re*re + im*im)/xmag;
    }

    public void setChannel(Complex h) {
        //do nothing.  This is a noncoherent detector.  The channel will
        //be estimated.
    }

}
