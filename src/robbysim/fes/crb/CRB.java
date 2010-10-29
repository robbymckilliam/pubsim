/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.fes.crb;

/**
 * Standard Cramer-Rao bound for frequency estimation.
 * @author Robby McKilliam
 */
public class CRB implements BoundCalculator{

    int n;
    double var, amplitude;
    
    /** Constructor.  The amplitude defaults to 1. */
    public CRB(){
        setAmplitude(1.0);
    }
    
    public void setN(int N) {
        this.n = N;
    }

    public void setVariance(double var) {
        this.var = var;
    }

    public void setAmplitude(double amp) {
        amplitude = amp;
    }

    public double getBound() {
        return 3.0*var/(Math.PI*Math.PI*amplitude*amplitude*n*(n*n-1));
    }

    
    
}
