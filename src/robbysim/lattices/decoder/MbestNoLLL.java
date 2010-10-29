/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.lattices.decoder;

import Jama.Matrix;
import robbysim.lattices.Lattice;

/**
 * 
 * @author Robby McKilliam
 */
public class MbestNoLLL extends Mbest {

    /**
     * Contructor sets the M parameter for the M best method.
     * This is the maximum number of points that can be kept at
     * each iteration of the decoder.
     */
    public MbestNoLLL(int M){
        super();
        this.M = M;
    }

    public MbestNoLLL(Lattice L, int M){
        super(L,M);
    }

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
        xr = new double[n];

        //System.out.println("GETTING HERE!");

        B = G;
        U = Matrix.identity(n, n);
        robbysim.QRDecomposition QR = new robbysim.QRDecomposition(B);
        R = QR.getR();
        Q = QR.getQ();

        Qtrans = Q.transpose();

    }
    

}
