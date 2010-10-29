/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.lattices.decoder;

import Jama.Matrix;
import robbysim.lattices.Lattice;
import robbysim.VectorFunctions;

/**
 * Lazy Babai algorithm that does not bother to
 * do LLL reduction first.
 * @author Robby McKilliam
 */
public class BabaiNoLLL extends Babai
                        implements GeneralNearestPointAlgorithm {

    public BabaiNoLLL(){

    }

    public BabaiNoLLL(Lattice L){
        setLattice(L);
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

        B = G;
        U = Matrix.identity(n, n);
        Jama.QRDecomposition QR = new Jama.QRDecomposition(G);
        R = QR.getR();
        Q = QR.getQ();

        Qtrans = Q.transpose();
        //System.out.println(VectorFunctions.print(R));


    }

}
