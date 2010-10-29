/*
 * GlueAnstarEstimator.java
 *
 * Created on 12 August 2007, 12:28
 */

package robbysim.fes;

import robbysim.lattices.Vn2Star.Vn2StarGlued;

/**
 * Frequency estimator that uses Pn1 glue vector algorithm to solve the nearest
 * point problem for the frequency estimation lattice Pn1.  O(n^4log(n)).
 * @author Robby McKilliam
 */
public class GlueAnstarEstimator 
        extends LatticeEstimator
        implements FrequencyEstimator {
    
   public GlueAnstarEstimator(){
        lattice = new Vn2StarGlued();
    }
    
}
