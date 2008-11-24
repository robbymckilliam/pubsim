/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder;

import Jama.Matrix;
import lattices.Lattice;
import simulator.VectorFunctions;

/**
 * Implements the Babai nearest plane algorithm.
 * This gives a rough approximation to the nearest point.
 * This does not perform the LLL reduction first.
 * Use BabaiLLL for that.
 * @author Robby McKilliam
 */
public class Babai implements GeneralNearestPointAlgorithm {

    protected Matrix G;
    protected double[] u, x;
    protected int n, m;
    
    public Babai(){
        
    }
    
    public Babai(Lattice L){
        setLattice(L);
    }
    
    public void setLattice(Lattice L) {
        G = L.getGeneratorMatrix().copy();
        m = G.getRowDimension();
        n = G.getColumnDimension();
        u = new double[n];
        x = new double[m];
    }

    public void nearestPoint(double[] y) {
        if(m != y.length)
            throw new RuntimeException("Point y and Generator matrix are of different dimension!");
        
        System.arraycopy(y, 0, x, 0, m);
        
        for(int i = 0; i < n; i ++){
            double ytb = 0.0, btb = 0.0;
            for(int j = 0; j < m; j ++){
                ytb += x[j]*G.get(j, i);
                btb += G.get(j, i)*G.get(j, i);
            }
            u[i] = Math.round(ytb/btb);  
            for(int j = 0; j < m; j ++)
                x[j] -= u[i]*G.get(j, i);
        }
        
        //x = G.times(new Matrix(u, m)).getRowPackedCopy();
        VectorFunctions.matrixMultVector(G, u, x);
              
    }

    public double[] getLatticePoint() {
        return x;
    }

    public double[] getIndex() {
        return u;
    }

    

}
