/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.psk.decoder;

import java.util.Arrays;
import pubsim.lattices.Anm.AnmSorted;
import pubsim.Complex;
import pubsim.IndexedDouble;
import pubsim.VectorFunctions;

/**
 *
 * @author robertm
 */
public class CoxeterNoncoherentReciever implements PSKReceiver{
    
    double[] argy, u, v;
    IndexedDouble[] z;
    int T, M;
    int k;
    
    public CoxeterNoncoherentReciever(){
        setM(4);
        this.k = 1;
    }
    
    public CoxeterNoncoherentReciever(int M){
        setM(M);
        this.k = 1;
    }
    
    public CoxeterNoncoherentReciever(int M, int k){
        setM(M);
        this.k = k;
    }

    public void setM(int M) {
        this.M = M;
        setT(T);
    }

    /** 
     * You must set T and M such
     * they are relatively prime.
     * @param T the bock length
     */
    public void setT(int T) {
        this.T = T;
        argy = new double[T];
        u = new double[T];
        v = new double[T];
        z = new IndexedDouble[T];
        for(int i = 0; i < T; i++)
            z[i] = new IndexedDouble();
    }

    /** Implements the Sweldens Noncoherent decoder using the O(nlogn)
     * nearest point algorithm for An*.
     * @param y the PSK symbols
     * @return the index of the nearest lattice point
     */
    public double[] decode(Complex[] y) {
        if(y.length != T) setT(y.length);
        
        //calculate the argument of of y and scale
        //so that the symbols are given by integers
        //in the range {0,1,...,M-1}
        for(int i = 0; i < T; i++){
            //System.out.print(y[i].phase());
            //double p = M/(2*Math.PI)*y[i].phase();
            argy[i] = M/(2*Math.PI)*y[i].phase() + M/2.0;
        }
        
        //must project to ensure that ambiguities are not found
        //Anm.project(argy, argy);
        
        int sumM = 0, sumMod = 0;
        double a = 0, b = 0;
        for(int i = 0; i < T; i++){
            u[i] = Math.round(argy[i]);
            sumM += u[i];
            sumMod += Util.mod((int)u[i], M);
            z[i].value = argy[i] - u[i];
            z[i].index = i;
            a += z[i].value;
            b += z[i].value * z[i].value;
        }
        
        Arrays.sort(z);
        
        int mod = k*M - k + 1;
       // System.out.println(mod);
        double D = Double.POSITIVE_INFINITY;
        int m = 0;
        for(int i = 0; i < M*T; i++){
            double dist = b - a*a/T;
            if(dist < D && sumMod%mod == 0){
//                System.out.println();
//                System.out.println("sumMod = " + sumMod);
//                System.out.println("sumMod%mod = " + sumMod%mod);
//                System.out.println("sumMod/mod = " + sumMod/mod);
//                System.out.println("mod = " + mod);
//                System.out.println("dist = " + dist);
//                System.out.println("u = " + VectorFunctions.print(u));
                D = dist;
                m = i;
            }
            sumM++;
            double uc = Util.mod((int)u[z[T - 1 - i%T].index]+1, M) - Util.mod((int)u[z[T - 1 - i%T].index], M);
            sumMod += uc;
            a -= 1.0;
            b += -2*z[T - 1 - i%T].value + 1.0;
            z[T - 1 - i%T].value -= 1.0;
            u[z[T - 1 - i%T].index] += 1.0;
            
            //System.out.println("numloops");
            //System.out.println("uc = " + uc);
            //System.out.println("t = " + (T - 1 - i%T));
            //System.out.println("a = " + a + ", b = " + b + ", dist = " + dist);
        }
        
        for(int i = 0; i < T; i++)
            u[i] = Math.round(argy[i]);
        
        for(int i = 0; i < m; i++)
            u[z[T - 1 - i%T].index] += 1.0;
        
        return u;
        
    }

    /** This is a noncoherent reciever so setting the channel does nothing*/
    public void setChannel(Complex h) {  }
    
    public int bitsPerCodeword() {
        return (int)Math.round((T-k)*Math.log(M)/Math.log(2));
        //return (int)Math.round((T)*Math.log(M)/Math.log(2));
    }

    /** 
     * We simply ignore the last symbol when calculating the bit errors.
     * It is only used to ensure that parity occurs.
     */
    public int bitErrors(double[] x) {
        if(u.length != x.length) 
             throw new Error("x and y must have equal length");
         
         int errors = 0;
         for(int i = 0; i<x.length-k-1; i++){
            int xdiff = Util.mod((int)(x[i+1]-x[i]),M);
            int udiff = Util.mod((int)(u[i+1]-u[i]),M);
            //System.out.println(" xdiff = " + xdiff + ", ydiff = " + ydiff + ", errors = " + mod(xdiff-ydiff, M/2+1));
            errors += Util.mod(xdiff-udiff, M/2+1);
         }
         return errors;
    }

    public int symbolErrors(double[] x) {
        //return Util.SymbolErrors(u, x, M);
        return Util.differentialEncodedSymbolErrors(x, u, M);
    }

    public boolean codewordError(double[] x) {
        return Util.codewordError(x, u, M);
        //return !Util.differentialEncodedEqual(x, u, M);
    }

}
