/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.psk.decoder;

import pubsim.Complex;
import pubsim.lattices.Anstar.Anstar;
import pubsim.lattices.Anstar.AnstarLinear;

/**
 * Noncoherent receiver using the linear time nearest point algorithm
 * for An*.
 * @author Robby McKilliam
 */
public class AnstarNoncoherent implements PSKReceiver{
    
    final double[] argy;
    final Anstar anstar;
    final int T, M;
    
    public AnstarNoncoherent(int T, int M){
        this.T = T;
        this.M = M;
        argy = new double[T];
        anstar = new AnstarLinear(T-1); //this is the O(n) An* algorithm
    }

    /** Implements the noncoherent decoder using the lattice An*.
     * nearest point algorithm for An*.
     * @param y the PSK symbols
     * @return the index of the nearest lattice point
     */
    public final double[] decode(Complex[] y) {
        if(y.length != T) throw new RuntimeException("y is the wrong length");
        
        //calculate the argument of of y and scale
        //so that the symbols are given by integers
        //in the range {0,1,...,M-1}
        for(int i = 0; i < T; i++)
            argy[i] = M/(2*Math.PI)*y[i].phase();
        
        anstar.nearestPoint(argy);
        
        return anstar.getIndex();
        
    }

    public int bitErrors(double[] x) {
        return Util.differentialEncodedBitErrors(anstar.getIndex(), x, M);
    }

    /** This is a noncoherent receiver so setting the channel does nothing*/
    public void setChannel(Complex h) {  }
    
    public int bitsPerCodeword() {
        return (int)Math.round((T-1)*Math.log(M)/Math.log(2));
        //return (int)Math.round(T*Math.log(M)/Math.log(2));
    }

    public int symbolErrors(double[] x) {
        return Util.differentialEncodedSymbolErrors(anstar.getIndex(), x, M);
    }

    public boolean codewordError(double[] x) {
        return !Util.differentialEncodedEqual(anstar.getIndex(), x, M);
    }

}
