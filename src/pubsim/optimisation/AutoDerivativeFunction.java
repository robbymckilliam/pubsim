/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.optimisation;

import Jama.Matrix;
import pubsim.VectorFunctions;

/**
 * Only requires writing the value(.) function.  The gradient and
 * Hessian are then approximated automatically using pseudo derivatives.
 * There is a pretty good paper by John Hobby discussing the advantages
 * and disadvantages of using the pseudo derivative.
 * http://ect.bell-labs.com/who/hobby/ad04long.pdf
 * 
 * Also, I know that control people study these types of things quite
 * a bit.  I went to a talk by Dragan Nesic from Melbourne Uni on this.
 * It's similar to what he calls `extremum seeking control'.
 * 
 * @author Robby McKilliam
 */
public abstract class AutoDerivativeFunction implements FunctionAndDerivatives{

    /**
     * Interval to use when computing derivates.  The smaller the
     * better, but setting this too small is likely to cause numerical
     * problems.  This will depend quite alot on how rapidly the function
     * changes, i.e. the magnitude of the second derivative.
     */
    protected final double interval;

    public AutoDerivativeFunction(){ interval = 1e-5; }

    public AutoDerivativeFunction(double interval){
        this.interval = interval;
    }

    /**
     * Return the Hessian matrix calculated at x
     * Makes the assumption that x is a column vector!
     */
    public Matrix hessian(Matrix x){
        int p = x.getRowDimension();
        Matrix H = new Matrix(p, p);
        for(int n = 0; n < p; n++){
            double v = x.get(n, 0);
            x.set(n, 0, v+interval/2.0);
            Matrix g = gradient(x);
            x.set(n, 0, v-interval/2.0);
            g.minusEquals(gradient(x));
            g.timesEquals(1.0/interval);
            H.setMatrix(0, p-1, n, n, g);
            x.set(n, 0, v);
        }
        return H;
    }

    /** 
     * Return the gradient matrix calculated at x.
     * Makes the assumption that x is a column vector!
     */
    public Matrix gradient(Matrix x){
        Matrix g = new Matrix(x.getRowDimension(), x.getColumnDimension());
        for(int n = 0; n < g.getRowDimension(); n++){
            double v = x.get(n, 0);
            x.set(n, 0, v+interval/2.0);
            double der = value(x);
            x.set(n, 0, v-interval/2.0);
            der -= value(x);
            der /= interval;
            g.set(n,0, der);
            x.set(n, 0, v);
        }
        return g;
    }

}
