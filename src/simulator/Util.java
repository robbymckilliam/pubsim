/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator;

/**
 * Utility class for common math operations etc.
 * @author Robby McKilliam
 */
public class Util {
    
    /** 
     * Takes x mod y and works for negative numbers.  Ie is not
     * just a remainder like java's % operator.
     */
    public static int mod(int x, int y){
        int t = x%y;
        if(t < 0) t+=y;
        return t;
    }
    
    /** The desired accuracy of the erf function */
    public static double ERF_TOLERANCE = 0.0000001;
    
    /** 
     * The error function.
     * Calculates erf with accuracy tolerance specified by ERF_TOLERANCE.
     * Or when 1000 elements of the Talyor series have been summed.
     */ 
    public static double erf(double x){
        
        double prod = 1.0;
        double sum = 0.0;
        double tooAdd = Double.POSITIVE_INFINITY;
        
        int n = 0;
        while(Math.abs(tooAdd) > ERF_TOLERANCE && n < 1000){
            tooAdd = x/(2*n + 1)*prod; 
            sum += tooAdd;
            prod *= -x*x/(n+1);
            n++;
        }
        
        return 2.0/Math.sqrt(Math.PI)*sum;
        
    }
    
    /** Factorial */
    public static long factorial(int i){
        long ret = 1; 
        for(int j = 1; j <= i; j++)
            ret *= j;
        return ret;
    }
    
    /** 
     * This is just a wrapper for Math.IEEEremainder
     */
    public static double modPart(double x, double m){
        return Math.IEEEremainder(x, m);
    }

}
