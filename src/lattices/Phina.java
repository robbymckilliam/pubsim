/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import Jama.Matrix;
import simulator.VectorFunctions;

/**
 * Class for the lattice Phina, ie the integer lattice that is that
 * duel of the polynomial phase estimation lattices Phina*.  There is 
 * no nearest point algorithms for these lattices (yet?).  Currently
 * only the volume method is implemented.
 * @author Robby McKilliam
 */
public class Phina extends NearestPointAlgorithm{
    
    protected int a;
    
    public Phina(int a){
        this.a = a;
    }
    
    public Phina(int a,int n){
        this.a = a;
        this.n = n;
    }

    public void setDimension(int n) {
        this.n = n;
    }

    public void nearestPoint(double[] y) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double volume() {
        
        double[] cv  = {1, -1};
        double[] bv = {1, -1};
        for(int i = 0; i < a-1; i++){
            bv = VectorFunctions.conv(cv, bv);
        }
        
        //System.out.println(VectorFunctions.print(bv));
        
        Matrix gen = new Matrix(n,n + a);
        for(int i = 0; i < n; i++){
            for(int j = 0; j < i; j++)
                gen.set(i,j,0.0);
            for(int j = i; j < i + bv.length; j++)
                gen.set(i,j,bv[j-i]);
            for(int j = i + bv.length; j < n + a; j++)
                gen.set(i,j,0.0);
        }
        
        //System.out.println(VectorFunctions.print(gen));
        
        Matrix gram = gen.times(gen.transpose());
        
        return Math.sqrt(gram.det());
                
    }

    /** 
     * This is lower bound on the inradius that is 
     * valid when n+a is prime and a is less than (n+a)/2.
     * This is the same as the bound on the Craig lattices.
     * See page 222-224 of SPLAG.
     */   
    public double inradius() {
        return Math.sqrt(2*a)/2.0;
    }
    

}
