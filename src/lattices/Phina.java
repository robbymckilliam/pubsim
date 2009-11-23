/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import Jama.Matrix;
import lattices.decoder.ShortestVector;
import simulator.Util;
import simulator.VectorFunctions;

/**
 * Class for the lattice Phina, ie the integer lattice that is that
 * duel of the polynomial phase estimation lattices Phina*.  There is 
 * no nearest point algorithms for these lattices (yet?).  Currently
 * only the volume method is implemented.
 * @author Robby McKilliam
 */
public class Phina extends AbstractLattice{
    
    protected int a;
    protected int n;
    
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

    @Override
    public double volume() {

        Matrix V = new Matrix(a,a);
        for(int m = 0; m < a; m++){
            for(int j = m; j < a; j++){
                double s = 0;
                for(int i = 1; i <= n+a; i++){
                    s += Math.pow(i, m)*Math.pow(i, j);
                }
                V.set(m, j, s);
                V.set(j, m, s);
            }
        }

//        System.out.println(VectorFunctions.print(V));

        double p = 1.0;
        for(int k = 0; k < a; k++){
            p *= Util.factorial(k);
        }
        
        return Math.sqrt(V.det())/p;
    }

    /**
     * Inradius is known for sufficiently large n and a less than 7.
     * Otherwise compute it by brute force.
     */
    @Override
    public double inradius() {
        if(a > 6 || n < 27){
            ShortestVector sv = new ShortestVector(this);
            double norm = VectorFunctions.sum2(sv.getShortestVector());
            return Math.sqrt(norm)/2.0;
        }
        else return Math.sqrt(2*a)/2.0;
    }

    public Matrix getGeneratorMatrix() {
        
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

    public double coveringRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Using n^2 as an approximation.  Really it's not this.
     */
    @Override
    public double kissingNumber() {
        return n*n;
    }

    public int getDimension() {
        return n;
    }
    

}
