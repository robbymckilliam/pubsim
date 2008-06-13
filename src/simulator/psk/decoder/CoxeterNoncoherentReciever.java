/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.psk.decoder;

import java.util.Vector;
import lattices.Anm;
import lattices.AnstarBucketVaughan;
import simulator.Complex;
import simulator.VectorFunctions;

/**
 *
 * @author robertm
 */
public class CoxeterNoncoherentReciever implements PSKReceiver{
    
    double[] argy, u, v;
    Anm lattice;
    int T, M;
    
    public CoxeterNoncoherentReciever(){
    }

    public void setM(int M) {
        this.M = M;
        lattice = new Anm(M);
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
        lattice.setDimension(T-1);
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
            argy[i] = M/(2*Math.PI)*y[i].phase();
        }
        
        //must project to ensure that ambiguities are not found
        Anm.project(argy, argy);
        
        double D = Double.POSITIVE_INFINITY;
        for(int i = 0; i < M; i++){
            for(int j = 0; j < T; j++)
                v[j] = argy[j] + i + 0.5;
            lattice.nearestPoint(v);
            double d = VectorFunctions.distance_between2(v, lattice.getLatticePoint());
            if(d < D){
                for(int j = 0; j < T; j++)
                    u[j] = Util.mod((int)Math.round(lattice.getIndex()[j]),M);
            }
        }
        
        return u;
        
    }

    /** This is a noncoherent reciever so setting the channel does nothing*/
    public void setChannel(Complex h) {  }
    
    public int bitsPerCodeword() {
        return (int)Math.round((T-1)*Math.log(M)/Math.log(2));
    }

    /** 
     * We simply ignore the last symbol when calculating the bit errors.
     * It is only used to ensure that parity occurs.
     */
    public int bitErrors(double[] x) {
        if(x.length != T) 
             throw new Error("vectors must have length T");
         
         int errors = 0;
         for(int i = 0; i<T-1; i++){
            int xmod = Util.mod((int)Math.round(x[i]), M);
            int lmod = Util.mod((int)Math.round(lattice.getIndex()[i]), M);
            errors += Util.mod((int)Math.round(xmod-lmod), M/2+1);
         }
         return errors;
    }

    public int symbolErrors(double[] x) {
        return Util.SymbolErrors(lattice.getIndex(), x, M);
    }

    public boolean codewordError(double[] x) {
        return Util.codewordError(x, lattice.getIndex(), M);
    }

}
