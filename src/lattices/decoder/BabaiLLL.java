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
    
    Matrix R, U;
    double[] uh;
    LatticeReduction lll;
    
    public BabaiLLL(){
        
    }
    
    public BabaiLLL(Lattice L){
        setLattice(L);
    }
    
    @Override
    public void setLattice(Lattice L) {
        G = L.getGeneratorMatrix();
        m = G.getRowDimension();
        n = G.getColumnDimension();
        u = new double[n];
        uh = new double[n];
        x = new double[m];
        
        lll = new LLL();
        R = lll.reduce(G);
        U = lll.getUnimodularMatrix();
        
       // System.out.println("R = \n" + VectorFunctions.print(R));
        //System.out.println("U = \n" + VectorFunctions.print(U));
        
    }
    
    @Override
    public void nearestPoint(double[] y) {
        if(m != y.length)
            throw new RuntimeException("Point y and Generator matrix are of different dimension!");
        
        for(int i = 0; i < n; i ++){
            double ytb = 0.0, btb = 0.0;
            for(int j = 0; j < m; j ++){
                ytb += y[j]*R.get(j, i);
                btb += R.get(j, i)*R.get(j, i);
            }
            uh[i] = Math.round(ytb/btb);    
        }
        
        //System.out.println("uh + " + VectorFunctions.print(uh));
        
        //u = U.times(new Matrix(uh, n)).getRowPackedCopy();
        //x = R.times(new Matrix(uh, n)).getRowPackedCopy();
        VectorFunctions.matrixMultVector(U, uh, u);
        VectorFunctions.matrixMultVector(G, u, x);
              
    }

}
