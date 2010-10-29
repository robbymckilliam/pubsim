/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.pes.bounds;

import Jama.Matrix;
import robbysim.VectorFunctions;

/**
 * Hamersley-Chapman-Robbins bound that applies
 * when the index differences are geometrically
 * distributed.
 * @author Robby McKilliam
 */
public class Hamersley extends ClairvoyantCRB implements BoundCalculator{
    
    int N;
    double p;
    
    /** Set the vector of indicies */
    @Override
    public void setS(double[] s){
        setLength(s.length);
    }
    
    /** Set the data length */
    @Override
    public void setLength(int N){
        this.N = N-1;
    }
    
    /** 
     * Set the parameter for the geometrically distributed
     * index differences.
     * @param p the geometric parameter in range [0,1]
     */
    @Override
    public void setGeometicParameter(double p){
        this.p = p;
    }
    
   /** Return the clairvoyant CRB for the set parameters */
    @Override
    public double getBound(){
        double ntn = N*N*N/3.0 + N*N/2.0 + N/6.0;
        double nt1 = N*N/2.0 + N/2.0;
        double ep = 1/p;
        double vp = (1-p)/(p*p);
        double ests = ntn*ep*ep + nt1*vp;
        double est1 = nt1*ep;
        double estd = N*ep;
        
                //set the numerator matrix
        Matrix num = new Matrix(2,2);
        num.set(0,0, N/var); num.set(0,1, T/var);
        num.set(1,0, T/var); num.set(1,1, Math.expm1(T*T/var));
        
        //set the numerator matrix
        Matrix den = new Matrix(3,3);
        den.set(0,0, ests/var); den.set(0,1, est1/var); den.set(0,2, T*estd/var);
        den.set(1,0, est1/var); den.set(1,1, N/var); den.set(1,2, T/var);
        den.set(2,1, T*estd/var); den.set(2,1, T/var); den.set(2,2, Math.expm1(T*T/var));
        
        return VectorFunctions.stableDet(num)/VectorFunctions.stableDet(den);
        
    }

}
