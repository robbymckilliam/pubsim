/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.pes.crb;

import Jama.Matrix;
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
    
    int N;
    double sts, sm, st1;
    
    /** Set the vector of indicies */
    @Override
    public void setS(double[] s){
        this.s = s;
        sts = VectorFunctions.sum2(s);
        st1 = VectorFunctions.sum(s);
        N = s.length;
        
        //last element in s is the biggest, we will use this.
        sm = s[s.length-1];
    }
    
    /** Return the Hamerley-Chapmin-Robbins bound for the set parameters */
    @Override
    public double getBound(){
        
        //set the numerator matrix
        Matrix num = new Matrix(2,2);
        num.set(0,0, N/var); num.set(0,1, T/var);
        num.set(1,0, T/var); num.set(1,1, Math.expm1(T*T/var));
        
        //set the numerator matrix
        Matrix den = new Matrix(3,3);
        den.set(0,0, sts/var); den.set(0,1, st1/var); den.set(0,2, T*sm/var);
        den.set(1,0, st1/var); den.set(1,1, N/var); den.set(1,2, T/var);
        den.set(2,1, T*sm/var); den.set(2,1, T/var); den.set(2,2, Math.expm1(T*T/var));
        
        return VectorFunctions.stableDet(num)/VectorFunctions.stableDet(den);
    }

}
