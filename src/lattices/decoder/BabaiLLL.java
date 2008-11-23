/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder;

import Jama.Matrix;
import lattices.Lattice;
import lattices.reduction.LLL;
import lattices.reduction.LatticeReduction;
import simulator.VectorFunctions;

/**
 * The is Babai's nearest plane algorithm.  The LLL algorithm is used
 * first to make the basis matrix further reduced.
 * @author Robby McKilliam
 */
public class BabaiLLL extends Babai{
    
    Matrix G, R, U;
    double[] u, uh, x;
    int n, m;
    LatticeReduction lll;
    
    public BabaiLLL(){
        
    }
    
    public BabaiLLL(Lattice L){
        setLattice(L);
    }
    
    @Override
    public void setLattice(Lattice L) {
        G = L.getGeneratorMatrix().copy();
        n = G.getRowDimension();
        m = G.getColumnDimension();
        u = new double[m];
        uh = new double[m];
        x = new double[n];
        
        lll = new LLL();
        R = lll.reduce(G);
        U = lll.getUnimodularMatrix();
        
        System.out.println("R = \n" + VectorFunctions.print(R));
        System.out.println("U = \n" + VectorFunctions.print(U));
        
    }
    
    @Override
    public void nearestPoint(double[] y) {
        if(n != y.length)
            throw new RuntimeException("Point y and Generator matrix are of different dimension!");
        
        for(int i = 0; i < m; i ++){
            double ytb = 0.0, btb = 0.0;
            for(int j = 0; j < n; j ++){
                ytb += y[j]*R.get(j, i);
                btb += R.get(j, i)*R.get(j, i);
            }
            uh[i] = Math.round(ytb/btb);    
        }
        
        u = U.times(new Matrix(uh, n)).getRowPackedCopy();
        x = R.times(new Matrix(uh, n)).getRowPackedCopy();
              
    }

}
