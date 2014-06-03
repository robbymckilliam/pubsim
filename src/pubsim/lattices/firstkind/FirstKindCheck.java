package pubsim.lattices.firstkind;

import java.util.Set;
import java.util.HashSet;
import pubsim.lattices.Lattice;
import Jama.Matrix;
import pubsim.CombinationEnumerator;

/**
 * Tests whether a given lattice is of first kind or not.  Will run in reasonable time only
 * for lattices of small dimension, say 5 or less.
 * @author Robby McKilliam
 */
public class FirstKindCheck {
    
    ///Set containing all relevant vectors
    protected final Set<Matrix> R;
    
    ///True if this lattice is of first kind
    public final boolean isFirstKind;
    
    ///Dimension of the lattice
    public final int n;
    
    public FirstKindCheck(Lattice L) {
        n = L.getDimension();
        R = new HashSet(); 
        for( Matrix v : L.relevantVectors() ) R.add(v); //load all relevant vectors into the set R
        isFirstKind = containsObtuseSuperBasis(R, n+1);
    }
    
    ///Given a set of atleast k vectors R, decide whether the set contains an obtuse super basis
    public static boolean containsObtuseSuperBasis(Set<Matrix> R, int k) {
        for( Set<Matrix> C : new CombinationEnumerator<>(R,k) ) {
            if( isObtuse(C) && isSuperbase(C) ) return true;
        }
        return false;
    }
    
    public static boolean isObtuse(Set<Matrix> C) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static boolean isSuperbase(Set<Matrix> C) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
