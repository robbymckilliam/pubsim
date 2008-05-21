/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.pes.crb;

/**
 *
 * @author Robby McKilliam
 */
public class ClairvoyantCRBRandomS extends ClairvoyantCRB{
    
    double p;
    int N;
    
    /** 
     * Set the parameter for the geometrically distributed
     * index differences.
     * @param p the geometric parameter in range [0,1]
     */
    @Override
    public void setGeometicParameter(double p){
        this.p = p;
    }
    
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
    
    
    /** Return the clairvoyant CRB for the set parameters */
    @Override
    public double getBound(){
        double ntn = N*N*N/3.0 + N*N/2.0 + N/6.0;
        double nt1 = N*N/2.0 + N/2.0;
        double ep = 1/p;
        double vp = (1-p)/(p*p);
        double ests = ntn*ep*ep + nt1*vp;
        double est1 = nt1*ep;
        
        double extx = ests - est1*est1/N;
        
        return var/extx;
    }

}
