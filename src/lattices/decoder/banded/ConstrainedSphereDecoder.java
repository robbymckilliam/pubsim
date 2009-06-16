/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder.banded;

import lattices.Lattice;
import lattices.NearestPointAlgorithm;
import simulator.VectorFunctions;

/**
 * Constrained sphere decoder that can enforce some indicies to
 * take particular values.  Does not use LLL because of constraints.
 * Really, this is a utility class for the BandedDecoder,
 * it's unlikely to have other uses.
 * @author Robby McKilliam
 */
public class ConstrainedSphereDecoder extends ConstrainedBabai
        implements ConstrainedNearestPointAlgorithm{

    /** Current sphere radius squared */
    protected double D;

    //temporary variables
    protected double[] xr, ut;

    /** small number to avoid numerical errors in branches. */
    protected double DELTA = 0.000001;

    /**
     * No contraints are set.
     * @param L The lattice to decode
     */
    public ConstrainedSphereDecoder(Lattice L){
        super(L);
    }

    /**
     * @param L The lattice to decode
     * @param constraints Array of constraints
     */
    public ConstrainedSphereDecoder(Lattice L, Double[] constraints){
        super(L, constraints);
    }
    
    @Override
    protected void setLattice(Lattice L) {
        G = L.getGeneratorMatrix().copy();
        m = G.getRowDimension();
        n = G.getColumnDimension();
        u = new double[n];
        ut = new double[n];
        xr = new double[n];
        x = new double[m];
        yr = new double[n];
        
        //CAREFULL!  This version of the sphere decoder requires R to
        //have positive diagonal entries.
        simulator.QRDecomposition QR = new simulator.QRDecomposition(G);
        R = QR.getR();
        Q = QR.getQ();

    }



    @Override
    public void nearestPoint(double[] y) {
        if(m != y.length)
            throw new RuntimeException("Point y and Generator" +
                    " matrix are of different dimension!");

        //compute the Babai point.
        //This store the point in variable x
        computeBabaiPoint(y);

        //compute the radius squared of the sphere we are decoding in.
        //Add DELTA to avoid numerical error causing the
        //Babai point to be rejected.
        D = VectorFunctions.distance_between2(y, x) + DELTA;

        //System.out.println(" const D = " + D);

        //current element being decoded
        int k = n-1;

        decode(k, 0);

        //compute nearest point
        VectorFunctions.matrixMultVector(G, u, x);

    }

    /**
     * Recursive decode function to test nearest plane
     * for a particular dimension/element
     */
    protected void decode(int k, double d){
        //return if this is already not the closest point
        if(d > D){
            return;
        }

        //compute the sum of R[k][k+i]*uh[k+i]'s
        //and the distance so far
        double rsum = 0.0;
        for(int i = k+1; i < n; i++ ){
            rsum += ut[i]*R.get(k, i);
        }

        //System.out.println(VectorFunctions.print(u));

        //check if this index is constrained
        if(c[k] != null){
            ut[k] = c[k].doubleValue();
            testPoint(k, rsum, d);
        }else{
            //set least possible ut[k]
            ut[k] = Math.ceil((-Math.sqrt(D - d) + yr[k] - rsum)/R.get(k,k));

            while(ut[k] <= (Math.sqrt(D - d) + yr[k] - rsum)/R.get(k,k) ){
                testPoint(k, rsum, d);
                ut[k]++;
            }
        }
    }

    private void testPoint(int k, double rsum, double d) {
        double kd = R.get(k, k) * ut[k] + rsum - yr[k];
        double sumd = d + kd * kd;
        //if this is not the first element then recurse
        if (k > 0) {
            decode(k - 1, sumd);
        } else {
            //otherwise check if this is the best point so far encounted
            //and update if required
            if (sumd <= D) {
                System.arraycopy(ut, 0, u, 0, n);
                //System.out.println("**** ubest = " + VectorFunctions.print(ubest));
                D = sumd;
            }
        }
    }

}