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

}
