/*
 * CoherentPAM.java
 *
 * Created on 18 January 2008, 14:53
 */

package pubsim.qam.crb;

/**
 *
 * @author robertm
 */
public class CoherentPAM extends NoncoherentPAM {

    /** Returns the coherent CRB. */
    public double getCRB(){
        return var/magx2;
    }
    
}
