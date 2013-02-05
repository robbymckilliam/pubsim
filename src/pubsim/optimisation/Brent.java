/*
 */
package pubsim.optimisation;

/**
 * *Performs a 1-dimensional minimization. It implements Brent's method which
 * combines a golden-section search and parabolic interpolation.
 * 
* @param f The function to minimise
 * @param ax Left endpoint of initial interval
 * @param cx Right endpoint of initial interval
 * @param bx bx must satisfy ax < bx < cx and f(ax) > f(bx) < f(cx)
 * @param tol Desired length of the interval in which the minimum will be
 * determined to lie (default 100)
 * @params ITRMAX maximum number of iterations before terminating (default 1e-8)
 * @author Robby McKilliam
 */
public class Brent {

    private double x;
    private double fx;
    private static final double ZEPS = 1e-10; //close to machine precision
    private static final double C = (3.0 - Math.sqrt(5.0)) / 2.0;
    
    public final double sign(double a, double b) { return Math.abs(a)*Math.signum(b); }
    
    public final double fmin() { return fx; }
    public final double xmin() { return x; }
    
    public Brent(SingleVariateFunction f, double ax, double bx, double cx){
        this(f,ax,bx,cx,1e-8,100);
    } 
    
    public Brent(SingleVariateFunction f, double ax, double bx, double cx, double tol){
        this(f,ax,bx,cx,tol,100);
    } 

    public Brent(SingleVariateFunction f, double ax, double bx, double cx, double tol, int ITRMAX) {
        double e = 0.0;
        double d = 0.0;
        double a = (ax < cx ? ax : cx);
        double b = (ax > cx ? ax : cx);
        x = bx;
        double w = bx;
        double v = bx;
        double fw = f.value(x);
        double fv = fw;
        fx = fw;
        for (int iter = 0; iter< ITRMAX; iter++){
            final double xm = 0.5 * (a + b);
            final double tol1 = tol * Math.abs(x) + ZEPS;
            final double tol2 = 2.0 * tol1;
            if (Math.abs(x - xm) <= tol2 - 0.5 * (b - a)) {
                return; //(fx, x)
            }
            if (Math.abs(e) > tol1) {
                final double r = (x - w) * (fx - fv);
                double q = (x - v) * (fx - fv);
                double p = (x - v) * q - (x - v) * r;
                q = 2.0 * (q - r);
                if (q > 0.0) {
                    p = -p;
                }
                q = Math.abs(q);
                final double etemp = e;
                e = d;
                if (Math.abs(p) >= Math.abs(0.5 * q * etemp) || p <= q * (a - x) || p >= q * (b - x)) { //golden step
                    e = (x >= xm) ? a - x : b - x;
                    d = C * e;
                } else { //parabolic step
                    d = p / q;
                    final double u = x + d;
                    if (u - a < tol2 || b - u < tol2) {
                        d = sign(tol1, xm - x);
                    }
                }
            } else {
                e = (x >= xm) ? a - x : b - x;
                d = C * e;
            }
            final double u = (Math.abs(d) >= tol1 ? x + d : x + sign(tol1, d));
            final double fu = f.value(u);
            if (fu <= fx) {
                if (u >= x) {
                    a = x;
                } else {
                    b = x;
                }
                v = w;
                w = x;
                x = u;
                fv = fw;
                fw = fx;
                fx = fu;
            } else {
                if (u < x) {
                    a = u;
                } else {
                    b = u;
                }
                if (fu <= fw || w == x) {
                    v = w;
                    w = u;
                    fv = fw;
                    fw = fu;
                } else if (fu <= fv || v == x || v == w) {
                    v = u;
                    fv = fu;
                }
            }
        }
        System.out.println("Warning: Brent's method reached the maximum number " + ITRMAX + " iterations");
        //return (fx, x)
    }
}
