/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package optimisation;

import Jama.Matrix;
import simulator.VectorFunctions;

/**
 *
 * @author Robby McKilliam
 */
public class NewtonRaphson {

    /** Maximimum number of time Newton's method with iterate */
    int max_iterations = 10;
    /** Tolerance to aim for between iterations */
    double tolerance = 1e-10;

    FunctionAndDerivatives f;

    public NewtonRaphson(FunctionAndDerivatives f){
        this.f = f;
    }

    public NewtonRaphson(FunctionAndDerivatives f,
            int max_iterations, double tolerance){
        this.f = f;
        this.max_iterations = max_iterations;
        this.tolerance = tolerance;
    }

    public Matrix maximise(Matrix x) throws Exception{

        double e = Double.POSITIVE_INFINITY;
        int itr = 0;
        Matrix xprev = x;
        Matrix xnext;
        while(e > tolerance && itr < max_iterations){

            //System.out.println(VectorFunctions.print(xprev));

            Matrix H = f.hessian(xprev);
            Matrix G = f.gradient(xprev);

            if(H.rank() != H.getColumnDimension())
                throw new Exception("Matrix is not full rank!");

            xnext = xprev.minus(H.inverse().times(G));

            itr++;
            e = xnext.minus(xprev).normF();
            xprev = xnext;
        }

        return xprev;
    }

}
