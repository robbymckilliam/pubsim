/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package optimisation;

import Jama.Matrix;

/**
 *
 * @author Robby McKilliam
 */
public class NewtonRaphson {

    /** Maximimum number of time Newton's method with iterate */
    int max_iterations = 10;
    /** Tolerance to aim for between iterations */
    double tolerance = 1e-10;

    NewtonRaphsonFunction f;

    public NewtonRaphson(NewtonRaphsonFunction f){
        this.f = f;
    }

    public NewtonRaphson(NewtonRaphsonFunction f,
            int max_iterations, double tolerance){
        this.f = f;
        this.max_iterations = max_iterations;
        this.tolerance = tolerance;
    }

    public Matrix optimise(Matrix x){

        double e = Double.POSITIVE_INFINITY;
        int itr = 0;
        Matrix xprev = x;
        Matrix xnext;
        while(e > tolerance && itr < max_iterations){

            Matrix H = f.hessian(xprev);
            Matrix G = f.gradient(xprev);
            xnext = xprev.minus(H.inverse().times(G));

            itr++;
            e = xnext.minus(xprev).normF();
            xprev = xnext;
        }

        return xprev;
    }

}
