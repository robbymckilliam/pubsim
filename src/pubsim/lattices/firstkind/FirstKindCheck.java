package pubsim.lattices.firstkind;

import pubsim.lattices.Lattice;
import pubsim.lattices.util.PointEnumerator;

/**
 * Tests whether a given lattice is of first kind or not.  Will run in reasonable time only
 * for lattices of small dimension, say 5 or less.
 * @author Robby McKilliam
 */
public class FirstKindCheck {
    
    public FirstKindCheck(Lattice L) {
        PointEnumerator relvec = L.relevantVectors();
    }
    
}
