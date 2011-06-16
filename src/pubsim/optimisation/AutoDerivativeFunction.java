/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.optimisation;

import Jama.Matrix;
import pubsim.VectorFunctions;

/**
 * Only requires writting the value(.) function.  The gradient and
 * Hessian are then approximated automatically using pseudo derivatives.
 * There is a pretty good paper by John Hobby discussing the advantages
 * and disadvantages of using the psuedo derivative.
 * http://ect.bell-labs.com/who/hobby/ad04long.pdf
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
        //System.out.println(VectorFunctions.print(x));
        int p = x.getRowDimension();
        Matrix H = new Matrix(p, p);
        for(int n = 0; n < p; n++){
            double v = x.get(n, 0);
            x.set(n, 0, v+interval/2.0);
            //System.out.println(VectorFunctions.print(x));
            Matrix g = gradient(x);
           // System.out.println(VectorFunctions.print(g));
            x.set(n, 0, v-interval/2.0);
            //System.out.println(VectorFunctions.print(x));
           // System.out.println(VectorFunctions.print(gradient(x)));
            g.minusEquals(gradient(x));
            //System.out.println(VectorFunctions.print(g));
            g.timesEquals(1.0/interval);
            //System.out.println("g = " +  VectorFunctions.print(g));
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
            //System.out.println(VectorFunctions.print(x));
           // System.out.println(value(x));
            x.set(n, 0, v-interval/2.0);
            //System.out.println(VectorFunctions.print(x));
            der -= value(x);
            //System.out.println(value(x));
            //System.out.println(value(x));
            der /= interval;
            //System.out.println(der);
            g.set(n,0, der);
            x.set(n, 0, v);
        }
        return g;
    }

}
