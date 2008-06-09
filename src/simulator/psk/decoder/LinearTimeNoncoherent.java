/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.psk.decoder;

import simulator.Complex;

/**
 *
 * @author Robby McKilliam
 */
public class LinearTimeNoncoherent implements PSKReceiver{
    
    double[] arg, g;
    int T, M;
    Complex[] p;
    protected int numBuckets;
    private int[] bucket;
    private int[] link;
    
    public LinearTimeNoncoherent(){
    }

    public void setM(int M) {
        this.M = M;
    }

    public void setT(int T) {
        this.T = T;
        arg = new double[T];
        g = new double[T];
        bucket = new int[T];
        link = new int[T];
        for(int i = 0; i < T; i++){
            bucket[i] = -1;
            link[i] = 0;
        }
        p = new Complex[T];
        for(int i = 0; i < T; i++)
            p[i] = new Complex();
    }

    /** 
     * Implements the linear time noncoherent decoder using 
     * the real likelihood function rather than the phase based
     * likelihood function that arises when An* is used. 
     * @param y the PSK symbols
     * @return the index of the nearest lattice point
     */
    public double[] decode(Complex[] y) {
        if(y.length != T) setT(y.length);
        
         //make sure that the buckets are empty!
        for(int i = 0; i < T; i++)
            bucket[i] = -1;
        
        Complex sump = new Complex(0,0);
        
        for(int t = 0; t < T; t++){
            arg[t] = M/(2*Math.PI)*y[t].phase();
            g[t] = Math.round(arg[t]);
            int i = (int)(Math.floor(T*(g[t] - arg[t] + 0.5)));
            link[t] = bucket[i];
            bucket[i] = t;
            double etap = 2*Math.PI/M*g[t];
            p[t] = y[t].conjugate().times(
                    new Complex(Math.cos(etap), Math.sin(etap)));
            sump = sump.plus(p[t]);
        }
        
        double ea = 2*Math.PI/M;
        Complex etam1 = new Complex(Math.cos(ea) - 1, Math.sin(ea));
        
        double bestL = sump.abs();
        int besti = -1;
        for(int i = 0; i < T; i++){
            int t = bucket[i];
            while(t != -1){
                sump = sump.plus(p[t].times(etam1));
                t = link[t];
            }
            double L = sump.abs();
            if(L > bestL){
                bestL = L;
                besti = i;
            }
        }
        
        for(int i = 0; i <= besti; i++){
            int t = bucket[i];
            while(t != -1){
                g[t] +=  1;
                t = link[t];
            }
        }
       
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
