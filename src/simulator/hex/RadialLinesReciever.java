/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.hex;

/**
 *
 * @author Robby McKilliam
 */
public class RadialLinesReciever implements HexReciever {

    protected final double[] ur, ui;
    protected final int N;

    public RadialLinesReciever(int N){
        ur = new double[N];
        ui = new double[N];
        this.N = N;
    }

    public void decode(double[] rreal, double[] rimag) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double[] getReal() {
        return ur;
    }

    public double[] getImag() {
        return ui; 
    }



}
