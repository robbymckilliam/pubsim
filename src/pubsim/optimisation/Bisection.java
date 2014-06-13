package pubsim.optimisation;

/**
 * Implements the bisection method for inverting functions
 * @author Robby McKilliam
 */
public class Bisection {
    
    /** The value of x for which f(x)=0 */
    public final double zero;
    
   /** 
   *Search for a zero of the function f in the interval [a, b].
   *Finds a solution such that |x0 - x| < tol where f(x0) = 0.
   *Uses the bisection method, only guaranteed to converge if there is a unique zero 
   *between a and b and f is continuous and sign(f(a)) = -sign(f(b))
   *
   *@param  f          The function to zero 
   *@param  a         Left endpoint of initial interval
   *@param  b         Right endpoint of initial interval
   *@param  tol         Desired length of the interval in which the minimum will be determined to lie (default 1e-6)
   *@param  ITRMAX  maximum number of iterations before terminating (default 100)
   */
  public Bisection(SingleVariateFunction f, double a, double b, double tol, int ITRMAX){
      zero = zero(f,a,b,tol,ITRMAX);
  }
    
  /** 
   *Search for a zero of the function f in the interval [a, b].
   *Finds a solution such that |x0 - x| < tol where f(x0) = 0.
   *Uses the bisection method, only guaranteed to converge if there is a unique zero 
   *between a and b and f is continuous and sign(f(a)) = -sign(f(b))
   *
   *@param  f          The function to zero 
   *@param  a         Left endpoint of initial interval
   *@param  b         Right endpoint of initial interval
   *@param  tol         Desired length of the interval in which the minimum will be determined to lie (default 1e-6)
   *@param  ITRMAX  maximum number of iterations before terminating (default 100)
   */
  public static double zero(SingleVariateFunction f, double a, double b, double tol, int ITRMAX){
      double ax = a; 
      double bx = b;
      for( int i = 1; i <= ITRMAX; i++ ){
        double c = (ax + bx)/2;
        double fc = f.value(c);
        if( fc == 0 || Math.abs(ax-bx)/2 < tol ) return c;
        if( Math.signum(fc) == Math.signum(f.value(ax)) ) ax = c; else bx = c;
      }
      throw new RuntimeException("Bisection failed.  Maximum number " + ITRMAX + " of iterations exceeded");
  }
  /** Default number of iterations in 100 */
  public static double zero(SingleVariateFunction f, double a, double b, double tol){
      return zero(f,a,b,tol,100);
  }
  
}
