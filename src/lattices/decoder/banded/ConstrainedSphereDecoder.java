/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder.banded;

import lattices.Lattice;
import lattices.decoder.GeneralNearestPointAlgorithm;
import lattices.decoder.SphereDecoder;
import simulator.VectorFunctions;

/**
 * Constrained sphere decoder that can enforce some indicies to
 * take particular values.  This version does not perform LLL reduction nor
 * does it use Babai's algorithm, it expects to be given a sphere radius to
 * use.  Really, this is a utility class for the BandedDecoder, it's unlikely
 * to have other uses.
 * @author Robby McKilliam
 */
public class ConstrainedSphereDecoder extends SphereDecoder
        implements GeneralNearestPointAlgorithm{

    /** Array of constraints */
    protected Double[] c;

    /**
     * @param L The lattice to decode
     * @param radius Sphere radius
     * @param constraints Array of constraints
     */
    public ConstrainedSphereDecoder(Lattice L, 
            Double[] constraints, double radius){
        D = radius*radius + DELTA;
        c = constraints;

        G = L.getGeneratorMatrix().copy();
        m = G.getRowDimension();
        n = G.getColumnDimension();
        
        if(n != c.length)
            throw new RuntimeException("The constraints are not the same" +
                    "dimension as the lattice!");

        u = new double[n];
        x = new double[m];
        yr = new double[n];
        ubest = new double[n];
        
        //CAREFULL!  This version of the sphere decoder requires R to
        //have positive diagonal entries.
        simulator.QRDecomposition QR = new simulator.QRDecomposition(G);
        R = QR.getR();
        Q = QR.getQ();

    }

    /** Set the constraints for this decoder */
    public void setConstraint(Double[] c){
        this.c = c;
    }

    @Override
    public void nearestPoint(double[] y) {
        if(m != y.length)
            throw new RuntimeException("Point y and Generator" +
                    " matrix are of different dimension!");
        
        //compute y in the triangular frame
        VectorFunctions.matrixMultVector(Q.transpose(), y, yr);

        //current element being decoded
        int k = n-1;

        decode(k, 0);

        //compute nearest point
        VectorFunctions.matrixMultVector(G, ubest, x);

    }

    /**
     * Recursive decode function to test nearest plane
     * for a particular dimension/element
     */
    @Override
    protected void decode(int k, double d){
        //return if this is already not the closest point
        if(d > D){
            return;
        }

        //compute the sum of R[k][k+i]*uh[k+i]'s
        //and the distance so far
        double rsum = 0.0;
        for(int i = k+1; i < n; i++ ){
            rsum += u[i]*R.get(k, i);
        }

        //System.out.println(VectorFunctions.print(u));

        //check if this index is constrained
        if(c[k] != null){
            u[k] = c[k].doubleValue();
            testPoint(k, rsum, d);
        }else{
            //set least possible ut[k]
            u[k] = Math.ceil((-Math.sqrt(D - d) + yr[k] - rsum)/R.get(k,k));

            while(u[k] <= (Math.sqrt(D - d) + yr[k] - rsum)/R.get(k,k) ){
                testPoint(k, rsum, d);
                u[k]++;
            }
        }
    }

    private void testPoint(int k, double rsum, double d) {
        double kd = R.get(k, k) * u[k] + rsum - yr[k];
        double sumd = d + kd * kd;
        //if this is not the first element then recurse
        if (k > 0) {
            decode(k - 1, sumd);
        } else {
            //otherwise check if this is the best point so far encounted
            //and update if required
            if (sumd <= D) {
                System.arraycopy(u, 0, ubest, 0, n);
                //System.out.println("**** ubest = " + VectorFunctions.print(ubest));
                D = sumd;
            }
        }
    }

    @Override
    public double[] getLatticePoint() {
        return x;
    }

    @Override
    public double[] getIndex() {
        return ubest;
    }

    @Override
    public void setLattice(Lattice G) {
        throw new UnsupportedOperationException("This is not supported. " +
                "Set the lattice in the constructor");
    }

}
