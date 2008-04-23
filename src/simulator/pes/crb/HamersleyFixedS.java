/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.pes.crb;

import lattices.Anstar;
import simulator.VectorFunctions;

/**
 * This is the Hamerley-Chapmin-Robbins bound that
 * includes the discrete parameter.  It is only tight when
 * sigma^2/T^2 is less than log(3).  This is generally in
 * the range that the estimators work so it's not a
 * terrible restriction.
 * @author Robby McKilliam
 */
public class HamersleyFixedS extends ClairvoyantCRB{
    
    private double maxs, mag2s;
    
    /** Set the vector of indicies */
    @Override
    public void setS(double[] s){
        x = new double[s.length];
        Anstar.project(s, x);
        maxs = VectorFunctions.max(x);
        mag2s = VectorFunctions.sum2(x);
    }
    
    /** Return the Hamerley-Chapmin-Robbins bound for the set parameters */
    @Override
    public double getBound(){
        double p = T*T/var;
        double div = mag2s - p*maxs*maxs/Math.expm1(p);
        return var/div;
    }

}
