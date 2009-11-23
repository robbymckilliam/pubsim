package lattices;

import Jama.Matrix;
import simulator.VectorFunctions;
import simulator.Util;

/**
 * Nearest point algorithm for the lattice Dn.
 * @author Robby McKilliam
 */
public class Dn extends LatticeAndNearestPointAlgorithm{

    double[] u;
    int n;

    protected Dn(){}

    public Dn(int n){
        setDimension(n);
    }

    @Override
    public void setDimension(int n) {
        this.n = n;
        u = new double[n];
    }
    
    @Override
    public int getDimension() {
        return n;
    }

    @Override
    public void nearestPoint(double[] y) {
        if (n != y.length)
	    setDimension(y.length);
        
        VectorFunctions.round(y, u);
        int m = (int)Math.rint(VectorFunctions.sum(u));
        if( Util.mod(m, 2) == 1){
            int k = 0;
            double D = Double.NEGATIVE_INFINITY;
            for(int i = 0; i < n; i++){
                double d = Math.abs(y[i] - u[i]);
                if( d > D){
                    k = i;
                    D = d;
                }
            }
            u[k] += Math.signum(y[k] - u[k]);
        }
    }

    @Override
    public double[] getLatticePoint() {
        return u;
    }

    @Override
    public double[] getIndex() {
        return u;
    }

    @Override
    public double volume() {
        return 2.0;
    }

    public Matrix getGeneratorMatrix() {
        Matrix B = new Matrix(n, n);
        B.set(0, 0, -1); B.set(1, 0, -1);
        for(int j = 1; j < n; j++){
            B.set(j-1, j, 1); B.set(j, j, -1);
        }
        return B;
    }

    public double coveringRadius() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
