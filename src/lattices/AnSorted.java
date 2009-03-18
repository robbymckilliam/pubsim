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
public class AnSorted extends An
        implements LatticeAndNearestPointAlgorithm{

    protected IndexedDouble[] z;
    
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
    

}
