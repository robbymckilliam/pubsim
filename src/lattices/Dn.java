package lattices;

import simulator.VectorFunctions;

/**
 * Nearest point algorithm for the lattice Dn.
 * @author Robby McKilliam
 */
public class Dn implements NearestPointAlgorithmInterface{

    double[] u;
    int n;
    
    @Override
    public void setDimension(int n) {
        this.n = n;
        u = new double[n];
    }

    @Override
    public void nearestPoint(double[] y) {
        if (n != y.length)
	    setDimension(y.length);
        
        VectorFunctions.round(y, u);
        int m = (int)Math.rint(VectorFunctions.sum(u));
        if( m%2 == 1){
            int k = 0;
            double D = Double.POSITIVE_INFINITY;
            for(int i = 0; i < n; i++){
                if( Math.abs(y[i] - u[i]) < D)
                    k = i;
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

}
