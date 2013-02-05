/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.decoder;

import Jama.Matrix;
import pubsim.lattices.LatticeInterface;

/**
 * 
 * @author Robby McKilliam
 */
public class MbestNoLLL extends Mbest {

    public MbestNoLLL(LatticeInterface L, int M){
        super(L,M);
        
        G = L.getGeneratorMatrix().copy();
        m = G.getRowDimension();
        n = G.getColumnDimension();
        u = new double[n];
        uh = new double[n];
        x = new double[m];
        yr = new double[n];
        ut = new double[n];
        ubest = new double[n];
        xr = new double[n];

        //System.out.println("GETTING HERE!");

        B = G;
        U = Matrix.identity(n, n);
        pubsim.QRDecomposition QR = new pubsim.QRDecomposition(B);
        R = QR.getR();
        Q = QR.getQ();

        Qtrans = Q.transpose();
    }
    

}
