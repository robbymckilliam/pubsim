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
 * Implements the Babai nearest plane algorithm.
 * This gives a rough approximation to the nearest point.
 * This does not perform the LLL reduction first.
 * Use BabaiLLL for that.
 * @author Robby McKilliam
 */
public class Babai implements GeneralNearestPointAlgorithm {

    /** Generator matrix of the lattice */
    protected Matrix G;
    
    /** Index of the Babai point. x = Gu */
    protected double[] u; 
    
    /** The Babai point */
    protected double[] x;
    
    /** 
     * Index of Babai point in LLL reduced basis.
     * x = GUuh
     */
    protected double[] uh; 
    
    /** 
     * Point y in triangular reference frame.
     * yr = Q'y
     */
    protected double[] yr;
    
    /** LLL reduced basis matrix. G = BU */
    protected Matrix B; 
    
    /** 
     * Unimodular transform between G and it's LLL
     * reduction B. G = BU
     */
    protected Matrix U; 
    
    /** R component of B = QR */
    protected Matrix R;
    
    /** Q component of B = QR */       
    protected Matrix Q;
    
    protected int n, m;
    protected LatticeReduction lll;
    
    
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
        uh = new double[n];
        x = new double[m];
        yr = new double[n];
        
        lll = new LLL();
        B = lll.reduce(G);
        U = lll.getUnimodularMatrix();
        Jama.QRDecomposition QR = new Jama.QRDecomposition(B);
        R = QR.getR();
        Q = QR.getQ();

        System.out.println(VectorFunctions.print(R));

        
    }

    public void nearestPoint(double[] y) {
        if(m != y.length)
            throw new RuntimeException("Point y and Generator matrix are of different dimension!");
        
        computeBabaiPoint(y);
              
    }

    public double[] getLatticePoint() {
        return x;
    }

    public double[] getIndex() {
        return u;
    }

    //compute the babai
    protected void computeBabaiPoint(double[] y) {
        
         VectorFunctions.matrixMultVector(Q.transpose(), y, yr);

        for (int i = n - 1; i >= 0; i--) {
            double rsum = 0.0;
            for (int j = n - 1; j > i; j--) {
                rsum += R.get(i, j) * uh[j];
            }
            uh[i] = Math.round((yr[i] - rsum) / R.get(i, i));
        }

        //System.out.println(VectorFunctions.print(R));
        //System.out.println(VectorFunctions.print(Q));
        //System.out.println(VectorFunctions.print(Q));
        
        //compute index u = Uuh so that Gu is Babai
        //point
        VectorFunctions.matrixMultVector(U, uh, u);
        
        //compute Babai point
        VectorFunctions.matrixMultVector(G, u, x);

    }
    

}
