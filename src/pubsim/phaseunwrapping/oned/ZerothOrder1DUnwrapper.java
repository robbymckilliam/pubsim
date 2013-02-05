/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.phaseunwrapping.oned;

import Jama.Matrix;
import pubsim.lattices.Lattice;
import pubsim.lattices.decoder.BabaiNoLLL;
import pubsim.VectorFunctions;
import pubsim.lattices.NearestPointAlgorithmInterface;

/**
 * Uses the sphere decoder to perform 0th order unwrapping
 * @author Robby McKilliam
 */
public class ZerothOrder1DUnwrapper implements OneDUnwrapperInterface {

    protected Matrix B;
    NearestPointAlgorithmInterface decoder;
    int N;
    double[] u;

    public double[] unwrap(double[] y) {
        if(N != y.length) setSize(y.length);
        double[] By = VectorFunctions.matrixMultVector(B, y);
        decoder.nearestPoint(By);
        System.arraycopy(decoder.getIndex(), 0, u, 0, N-1);
        u[N-1] = 0.0;
        return u;
    }

    public void setSize(int N) {
        this.N = N;
        u = new double[N];

        B = new Matrix(N-1, N);
        for(int n = 0; n < N-1; n++){
            B.set(n,n, 1);
            B.set(n,n+1, -1);
        }

        System.out.println(VectorFunctions.print(B));

        decoder = new BabaiNoLLL(new Lattice(B.getMatrix(0, N-2, 0, N-2)));
        
    }



}
