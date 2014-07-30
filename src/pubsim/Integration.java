package pubsim;

/**
 * Class for performing one dimensional intergration.
 * @author Robby McKilliam
 */
public abstract class Integration {
    
    /** The function to be integrated.  Should be overriden. */
    public abstract double f(double x);
    
    /** 
     * Approximates integral of f from a to b by the trapezoidal method.
     * @param N is the number of steps used.
     */
    public double trapezoid(double a, double b, int N){
        double del = (b - a)/N;
        double inner = 0.0;
        for(int n = 1; n <= N-1; n++) inner += 2*f(a + n*del);
        return del/2 * ( inner + f(a) + f(b) );
    }
    
}
