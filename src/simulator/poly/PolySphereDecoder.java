/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly;

import lattices.Lattice;
import lattices.reduction.LLL;
import simulator.VectorFunctions;

/**
 * This is a special variant of the sphere decoder for the PhinaStar lattice.
 * It improves the running time by setting the decoding sphere radius to be
 * the smallest of the Babai point and a known upper bound on the covering radius
 * of the lattice (the covering radius of An*).
 * 
 * Note that this estimator is only valid for evenly spaced time steps.
 * 
 * THERE IS SOMETHING WRONG WITH THIS!  It fails in low dimension.  I am
 * not sure why yet.
 * @author Robby McKilliam
 */
public class PolySphereDecoder extends lattices.decoder.SphereDecoder 
                    implements lattices.decoder.GeneralNearestPointAlgorithm{
    
    /** Bound on the square of the covering radius */
    double coveringRadiusBound;
    
    @Override
    public void setLattice(Lattice L) {
        G = L.getGeneratorMatrix().copy();
        m = G.getRowDimension();
        n = G.getColumnDimension();
        u = new double[n];
        uh = new double[n];
        x = new double[m];
        yr = new double[n];
        ut = new double[n];
        ubest = new double[n];
        xr = new double[m];
        
        lll = new LLL();
        B = lll.reduce(G);
        U = lll.getUnimodularMatrix();
        
        //CAREFULL!  This version of the sphere decoder requires R to
        //have positive diagonal entries.
        simulator.QRDecomposition QR = new simulator.QRDecomposition(B);
        R = QR.getR();
        Q = QR.getQ();
        
        //see SPLAG page 115.
        coveringRadiusBound = (m-1)*(m+1)/(12*m);
    }
       
    @Override
    public void nearestPoint(double[] y) {
        if(m != y.length)
            throw new RuntimeException("Point y and Generator matrix are of different dimension!");
        
        computeBabaiPoint(y);
        
        //compute the Babai point in the triangular frame
        VectorFunctions.matrixMultVector(R, uh, xr);
        
        //compute the radius squared of the sphere we are decoding in.
        //This is the miniumum of the covering radius bound and the
        //distance to the Babai point.
        D = Math.min(VectorFunctions.distance_between2(yr, xr) * DELTA,
                        coveringRadiusBound);
        
        //current element being decoded
        int k = n-1;
        
        decode(k, 0);
        
        //compute index u = Uuh so that Gu is nearest point
        VectorFunctions.matrixMultVector(U, ubest, u);
        
        //compute nearest point
        VectorFunctions.matrixMultVector(G, u, x);
        
    }

}
