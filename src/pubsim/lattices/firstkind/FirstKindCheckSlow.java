package pubsim.lattices.firstkind;

import Jama.Matrix;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import pubsim.CombinationEnumerator;
import pubsim.lattices.LatticeInterface;
import static pubsim.VectorFunctions.dot;
import pubsim.lattices.LatticeAndNearestPointAlgorithm;

/**
 * Tests whether a given lattice is of first kind or not.  Will run in reasonable time only
 * for lattices of small dimension, say 5 or less.
 * @author Robby McKilliam
 */
public class FirstKindCheckSlow {
    
    ///Set containing all relevant vectors
    protected final Set<Matrix> R;
    
    ///Set containing vectors from the obtuse superbase if it exists
    protected Set<Matrix> B = new HashSet();
    
    ///True if this lattice is of first kind
    public final boolean isFirstKind;
    
    ///Dimension of the lattice
    public final int n;
   
    /** Asserts if the lattice with generator B is of first kind */
    public FirstKindCheckSlow(Matrix B) {
        this(new LatticeAndNearestPointAlgorithm(B));
    }
    
    /** Asserts if the lattice L is of first kind */
    public FirstKindCheckSlow(LatticeInterface L) {
        n = L.getDimension();
        R = new HashSet(); 
        for( Matrix v : L.relevantVectors() ) R.add(v); //load all relevant vectors into the set R
        isFirstKind = containsObtuseSuperBasis(R);
    }
    
    ///Given a set R containingatleast k vectors, decide whether the set contains an obtuse super basis
    protected boolean containsObtuseSuperBasis(Set<Matrix> R) {
        for( Set<Matrix> C : new CombinationEnumerator<>(R,n+1) ) {
            if( isObtuse(C) && isSuperbase(C) ) {
                B = new HashSet(C);
                return true;
            }
        }
        return false;
    }    
    
    ///@return true if this lattice is of first kind
    public boolean isFirstKind() { return isFirstKind; }
    
    /**
     * @return set containing vectors from the obtuse superbase if it 
     * exists, i.e., if this lattice is of first kind. Returns emptyset otherwise.
     * */
    public Set<Matrix> obtuseSuperbase() { return B; }
    
    ///Returns true if the vectos in C are all obtuse or orthogonal. TOL > 0 decides how close to zero is considered zero.
    public static boolean isObtuse(Set<Matrix> C, double TOL) {
        int N = C.size();
        Matrix[] b = new Matrix[N];
        C.toArray(b); //build an array with pointers to vectors in C
        for(int i = 0; i < N; i++)
            for(int j = i+1; j < N; j++)
                if( dot(b[i],b[j]) > Math.abs(TOL) ) return false;
        return true;
    }
    ///Default TOL = 1e-10
    public static boolean isObtuse(Set<Matrix> C) { return isObtuse(C, 1e-10); }
    
    ///Returns true if the vectors in C sum to zero.  TOL decides how close to zero each element must be
    public static boolean isSuperbase(Set<Matrix> C, double TOL) {
        Iterator<Matrix> itr = C.iterator();
        Matrix s = itr.next();
        while(itr.hasNext()) s = s.plus(itr.next());
        return s.normInf() < TOL;
    }
    ///Default TOL = 1e-8
    public static boolean isSuperbase(Set<Matrix> C) { return isSuperbase(C, 1e-8); }
    
}
