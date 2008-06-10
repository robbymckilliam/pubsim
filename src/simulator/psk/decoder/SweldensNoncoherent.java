/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.psk.decoder;

import java.util.Arrays;
import simulator.Complex;
import simulator.IndexedDouble;

/**
 * This is an implementation of Sweldens' noncoherent detector
 * for PSK.  This is essentially the O(nlogn) An* algorithm.
 * @author Robby McKilliam
 */
public class SweldensNoncoherent implements PSKReceiver{
    
    double[] arg, g;
    int T, M;
    IndexedDouble[] sorted;
    Complex[] p;
    
    public SweldensNoncoherent(){
    }

    public void setM(int M) {
        this.M = M;
    }

    public void setT(int T) {
        this.T = T;
        arg = new double[T];
        g = new double[T];
        sorted = new IndexedDouble[T];
        for(int i = 0; i < T; i++)
            sorted[i] = new IndexedDouble();
        p = new Complex[T];
        for(int i = 0; i < T; i++)
            p[i] = new Complex();
    }

    /** 
     * Implements the ML Sweldens/Mackenthun Noncoherent decoder O(nlogn)
     * @param y the PSK symbols
     * @return the index of the nearest lattice point
     */
    public double[] decode(Complex[] y) {
        if(y.length != T) setT(y.length);
        
        Complex sump = new Complex();
      
        for(int i = 0; i < T; i++){
            arg[i] = M/(2*Math.PI)*y[i].phase();
            g[i] = Math.round(arg[i]);
            sorted[i].index = i;
            sorted[i].value = g[i] - arg[i];
            double etap = 2*Math.PI/M*g[i];
            p[i] = y[i].conjugate().times(
                    new Complex(Math.cos(etap), Math.sin(etap)));
            sump = sump.plus(p[i]);
        }
        
        Arrays.sort(sorted);
        
        double ea = 2*Math.PI/M;
        Complex etam1 = new Complex(Math.cos(ea) - 1, Math.sin(ea));
        
        double bestL = sump.abs();
        int besti = -1;
        for(int i = 0; i < T; i++){
            int u = sorted[i].index;
            sump = sump.plus(p[u].times(etam1));
            double L = sump.abs();
            if(L > bestL){
                bestL = L;
                besti = i;
            }
        }
        
        for(int i = 0; i <= besti; i++)
            g[sorted[i].index] +=  1;
       
        return g;
        
    }

    public int bitErrors(double[] x) {
        return Util.differentialEncodedBitErrors(g, x, M);
    }

    /** This is a noncoherent reciever so setting the channel does nothing*/
    public void setChannel(Complex h) {  }
    
    public int bitsPerCodeword() {
        //return (int)Math.round((T-1)*Math.log(M)/Math.log(2));
        return (int)Math.round(T*Math.log(M)/Math.log(2));
    }

}
