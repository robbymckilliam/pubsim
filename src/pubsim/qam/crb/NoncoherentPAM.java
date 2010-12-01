/*
 * NoncoherentPAM.java
 *
 * Created on 18 January 2008, 12:50
 */

package pubsim.qam.crb;

import pubsim.VectorFunctions;

/**
 * Computes the Hammersly-Chapman-Robins bound using
 * a formula given by Dan.  This should be valid provided
 * that p = h/var > log(3)/4.
 * @author Robby McKilliam
 */
public class NoncoherentPAM {
    
    protected int T, M;
    protected double h, var;
    protected double[] x;
    protected double maxx, magx2;
    
    public void setT(int T){
        this.T = T;
    }
    
    public void setChannel(double h){
        this.h = h;
    }
    
    public void setVariance(double var){
        this.var = var;
    }
    
    public void setM(int M){
        this.M = M;
    }
    
    /** Set the PAM symbol being sent */
    public void setx(double[] x){
        this.x = x;
        maxx = VectorFunctions.max(x);
        magx2 = VectorFunctions.sum2(x);
        T = x.length;
    }
    
    /** 
     * Returns the CRB as calculated by Dan's formulae.  This
     * should be valid provided p > log(3)/4
     */
    public double getCRB(){
        double p = h/var;
        return var/(magx2 - 4*p*maxx*maxx/Math.expm1(4*p));
    }
}
