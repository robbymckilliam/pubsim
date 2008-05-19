/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.psk.decoder;

import lattices.AnstarBucketVaughan;

/**
 * Noncoherent receiver using the linear time nearest point algorihtm
 * for An*.
 * @author Robby McKilliam
 */
public class LinearTimeNoncoherent extends SweldensNoncoherent
        implements PSKReceiver{
    
    public LinearTimeNoncoherent(){
        anstar = new AnstarBucketVaughan(); //this is the O(n) An* algorithm
    }

}
