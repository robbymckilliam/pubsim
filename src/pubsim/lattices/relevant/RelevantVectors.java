package pubsim.lattices.relevant;

import Jama.Matrix;
import pubsim.VectorFunctions;
import pubsim.lattices.Lattice;
import pubsim.lattices.LatticeAndNearestPointAlgorithmInterface;
import pubsim.lattices.LatticeInterface;
import pubsim.lattices.decoder.SphereDecoder;
import pubsim.lattices.decoder.SphereDecoderSchnorrEuchner;
import pubsim.lattices.util.AbstractPointEnumerator;
import pubsim.lattices.util.IntegerVectors;
import pubsim.lattices.util.PointEnumerator;

/**
 * Returns all relevant vectors in the lattice.  Each relevant vector is computed by
 * computing a closest lattice point to the origin in the cosets of L/2L. This code should reliably
 * return all of the 'strict' relevant vectors, but might only return a subset of the 'lax' relevant
 * as a result of numerical imprecision.
 * 
 * This will only run in reasonable time for lattices of small dimensions, say 8 or less.
 * 
 * @author Robby McKilliam
 */
public class RelevantVectors 
    extends AbstractPointEnumerator
        implements PointEnumerator{

    public final long totalvectors; //the total number of strict and lax relevant vectors
    public final LatticeAndNearestPointAlgorithmInterface L; //the lattice for will find the relevant vectors of
    public final Matrix B;  //the basis matrix of the lattice
        
    protected long vectorscounted = 0;
    protected final IntegerVectors intenum;
    
    public RelevantVectors(LatticeAndNearestPointAlgorithmInterface L){
        this.L = L;
        B = L.getGeneratorMatrix();
        int N = L.getDimension();
        totalvectors = (long)(Math.pow(2,N+1)-2);
        intenum = new IntegerVectors(N, 2);
    }
    
    @Override
    public double[] nextElementDouble() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double percentageComplete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasMoreElements() {
        return vectorscounted < totalvectors;
    }

    protected Matrix prev; //stores the previous vector so that negatives can be efficiently returned
    
    @Override
    public Matrix nextElement() {
        if(!hasMoreElements()) throw new ArrayIndexOutOfBoundsException("There are no more relevant vectors!");
        vectorscounted++;
        //every second relevant vector is just the negation of the last
        if(vectorscounted%2 == 0) {
            return prev.times(-1.0);
        } else {
            Matrix v = B.times(intenum.nextElement()).times(0.5);
            L.nearestPoint(v.getColumnPackedCopy());
            prev = VectorFunctions.columnMatrix(L.getLatticePoint());
            return prev;
        }
    }

}