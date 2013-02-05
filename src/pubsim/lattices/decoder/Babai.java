/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.decoder;

import Jama.Matrix;
import pubsim.lattices.LatticeInterface;
import pubsim.lattices.reduction.LLL;
import pubsim.lattices.reduction.HKZ;
import pubsim.lattices.reduction.LatticeReduction;
import pubsim.VectorFunctions;
import pubsim.lattices.NearestPointAlgorithmInterface;
import pubsim.lattices.Lattice;

/**
 * Implements the Babai nearest plane algorithm.
 * This gives a rough approximation to the nearest point.
 * This does not perform the LLL reduction first.
 * Use BabaiLLL for that.
 * @author Robby McKilliam
 * Modified to use any sort of LatticeReduction (with LLL as the default)
 */
public class Babai implements NearestPointAlgorithmInterface {

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
    protected Matrix Q, Qtrans;
    
    protected int n, m;
    protected LatticeReduction reducer;
    
    public Babai(LatticeInterface L){
	this(L, new LLL());
    }

    public Babai(LatticeInterface L, LatticeReduction lr) {
	reducer = lr;
        G = L.getGeneratorMatrix().copy();
        m = G.getRowDimension();
        n = G.getColumnDimension();
        u = new double[n];
        uh = new double[n];
        x = new double[m];
        yr = new double[n];
        
        B = reducer.reduce(G);
        U = reducer.getUnimodularMatrix();
        Jama.QRDecomposition QR = new Jama.QRDecomposition(B);
        R = QR.getR();
        Q = QR.getQ();
        Qtrans = Q.transpose();
    }

    @Override
    public void nearestPoint(double[] y) {
        if(m != y.length)
            throw new RuntimeException("Point y of length " + y.length + 
                    " and Generator matrix of column length " + m +
                    " are of different dimension!");
        computeBabaiPoint(y);        
    }

    @Override
    public double[] getLatticePoint() {
        return x;
    }

    @Override
    public double[] getIndex() {
        return u;
    }

    //compute the babai
    protected void computeBabaiPoint(double[] y) {
        
         VectorFunctions.matrixMultVector(Qtrans, y, yr);

        for (int i = n - 1; i >= 0; i--) {
            double rsum = 0.0;
            for (int j = n - 1; j > i; j--) {
                rsum += R.get(i, j) * uh[j];
            }
            uh[i] = Math.round((yr[i] - rsum) / R.get(i, i));
        }
    
        //compute index u = Uuh so that Gu is Babai point
        VectorFunctions.matrixMultVector(U, uh, u);
        
        //compute Babai point
        VectorFunctions.matrixMultVector(G, u, x);

    }
    
    @Override
    public double distance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private double[] yDoubletoy;
    @Override
    public void nearestPoint(Double[] y) {
        if(yDoubletoy == null || yDoubletoy.length != y.length)
            yDoubletoy = new double[y.length];
            for(int i = 0; i < y.length; i++) yDoubletoy[i] = y[i];
        this.nearestPoint(yDoubletoy);
    }

    // Test harness
    public static void main(String args[]) {
	int dim = 10;
	Matrix B = Matrix.random(dim, dim);
	System.out.println("B = ");
	B.print(6, 2);
	Matrix y = Matrix.random(dim, 1);
	System.out.println("y = ");
	y.transpose().print(6, 2);
	Lattice Lambda = new Lattice(B);
	Babai blll = new Babai(Lambda, new LLL());
	Babai bhkz = new Babai(Lambda, new HKZ());
	blll.computeBabaiPoint(y.getRowPackedCopy());
	bhkz.computeBabaiPoint(y.getRowPackedCopy());
	Matrix xlll = new Matrix(blll.getLatticePoint(), dim);
	Matrix xhkz = new Matrix(bhkz.getLatticePoint(), dim);
	System.out.println("xlll = ");
	xlll.transpose().print(6, 2);
	System.out.println("xhkz = ");
	xhkz.transpose().print(6, 2);
	System.out.println("||xlll - y|| = " + xlll.minus(y).norm2());
	System.out.println("||xhkz - y|| = " + xhkz.minus(y).norm2());
    }
}
