/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices;

import Jama.Matrix;
import pubsim.Util;
import static pubsim.Util.*;
import pubsim.VectorFunctions;
import pubsim.lattices.An.AnFastSelect;

/**
 * Class for the lattice Vnm, ie the integer lattice that is that
 * duel of the polynomial phase estimation lattices Vnm*.  There is
 * no nearest point algorithms for these lattices (yet?).  Currently
 * only the volume method is implemented.
 * @author Robby McKilliam
 */
public class Vnm extends AbstractLattice{
    
    protected int a;
    protected int n;
    
    public Vnm(int a,int n){
        this.a = a;
        this.n = n;
    }

    /**
     * Uses nifty binomial formula to compute the volume.
     * @return
     */
    @Override
    public double volume() {
        return volume(a, n);
    }

    public static double volume(int a, int n){
        return Math.pow(2, logVolume(a, n));
    }

    /**
     * Uses nifty binomial formula to compute the log of the volume.
     * @return
     */
    @Override
    public double logVolume() {
        return logVolume(a, n);
    }

    /**
     * Uses nifty binomial formula to compute the log of the volume.
     * @return
     */
    public static double logVolume(int a, int n) {
        double vol = 0.0;
        for(int k = 0; k < a; k++){
            vol += ( Util.log2Binom(n+a+k, 2*k+1) - Util.log2Binom(2*k, k) );
        }
        return vol/2.0;
    }

    @Override
    public Matrix getGeneratorMatrix() {
        
        if( a == 0 ) return Matrix.identity(n, n);
        
        double[] cv  = {1, -1};
        double[] bv = {1, -1};
        for(int i = 0; i < a-1; i++){
            bv = VectorFunctions.conv(cv, bv);
        }
        
        Matrix gen = new Matrix(n+a, n);
        for(int i = 0; i < n; i++){
            for(int j = 0; j < i; j++)
                gen.set(j,i,0.0);
            for(int j = i; j < i + bv.length; j++)
                gen.set(j,i,bv[j-i]);
            for(int j = i + bv.length; j < n + a; j++)
                gen.set(j,i,0.0);
        }
        
        return gen;
    }

    @Override
    public double coveringRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getDimension() {
        return n;
    }

    /**
     * When m = 0, this is the integer lattice
     */
    public static class Vn0 extends Zn {
        public Vn0(int n){ super(n); }
    }
    
     /**
     * When m = 1, this is the root An
     */
    public static class Vn1 extends AnFastSelect {
        public Vn1(int n){ super(n); }
    }
    
    /** 
     * When m = 2, we know the kissing number
     */
    public static class Vn2 extends Vnm {
        public Vn2(int n) { super(2, n); }
        
        //public long kissingNumber() {
        //    reuturn 0.0;
        //}
    }
    
}
