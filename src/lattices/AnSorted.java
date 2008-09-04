package lattices;

import Jama.Matrix;
import java.util.Arrays;
import simulator.IndexedDouble;
import simulator.VectorFunctions;

/**
 * Nearest point algorithm for the Lattice An.  This uses the sorting
 * method described by Conway and Sloane.
 * @author Robby McKilliam
 */
public class AnSorted implements NearestPointAlgorithmInterface{

    protected double[] u;
    protected IndexedDouble[] z;
    protected int n;
    
    @Override
    public void setDimension(int n) {
        this.n = n;
        u = new double[n+1];
        z = new IndexedDouble[n+1];
        for(int i = 0; i < n+1; i++)
            z[i] = new IndexedDouble();
    }

    @Override
    public void nearestPoint(double[] y) {
        if (n != y.length-1)
	    setDimension(y.length-1);
        
        Anstar.project(y, y);
        
        VectorFunctions.round(y, u);
        double m = VectorFunctions.sum(u);
        for(int i = 0; i < n+1; i++){
            z[i].value = Math.signum(m)*(y[i] - u[i]);
            z[i].index = i;
        }
        //System.out.println(VectorFunctions.print(y));
        //System.out.println(VectorFunctions.print(u));
        //System.out.println(m);
        Arrays.sort(z);
        for(int i = 0; i < Math.abs(m); i++)
            u[z[i].index] -= Math.signum(m);
        //System.out.println(VectorFunctions.print(u));
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
        return n+1;
    }
    
    /** 
     * Returns glue vector [i] for An.
     * See SPLAG pp109.  This is not an
     * efficient implementation.  It
     * allocates memory.  This is
     * here for testing purposes.
     */
    public double[] getGlueVector(double i){
        double[] g = new double[n+1];
        double j = n + 1 - i;
        for(int k = 0; k < j; k++)
            g[k] = i/(n+1);
        for(int k = (int)j; k < n + 1; k++)
            g[k] = -j/(n+1);
        return g;
    }

    public double inradius() {
        return Math.sqrt(2)/2.0;
    }

    public double getDimension() {
        return n;
    }

    public double centerDensity(){
        return Math.pow(inradius(), getDimension())/volume();
    }

    public Matrix getGeneratorMatrix() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
