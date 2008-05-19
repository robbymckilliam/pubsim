/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.psk.decoder;

import lattices.Anstar;
import lattices.AnstarNew;
import simulator.Complex;
import simulator.VectorFunctions;

/**
 * This is an implementation of Sweldens' noncoherent detector
 * for PSK.  This is essentially the O(nlogn) An* algorithm.
 * @author Robby McKilliam
 */
public class SweldensNoncoherent implements PSKReceiver{
    
    double[] argy;
    Anstar anstar;
    int T, M;
    
    public SweldensNoncoherent(){
        anstar = new AnstarNew(); //this is the O(nlogn) An* algorithm
    }

    public void setM(int M) {
        this.M = M;
    }

    public void setT(int T) {
        this.T = T;
        argy = new double[T];
        anstar.setDimension(T-1);
    }

    /** Implements the Sweldens Noncoherent decoder using the O(nlogn)
     * nearest point algorithm for An*.
     * @param y the PSK symbols
     * @return the index of the nearest lattice point
     */
    public double[] decode(Complex[] y) {
        if(y.length != T) setT(y.length);
        
        //calculate the argument of of y and scale
        //so that the symbols are given by integers
        //in the range {0,1,...,M-1}
        for(int i = 0; i < T; i++){
            //System.out.print(y[i].phase());
            argy[i] = M/(2*Math.PI)*(y[i].phase() + Math.PI) - 0.5;
        }
        System.out.println();
        //System.out.println(VectorFunctions.print(y));
        System.out.println("argy = " + VectorFunctions.print(argy));
        
        anstar.nearestPoint(argy);
        
        return anstar.getIndex();
        
    }

}
