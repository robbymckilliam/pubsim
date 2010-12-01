/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.hex.ambiguity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;
import pubsim.lattices.Hexagonal;
import pubsim.lattices.util.IntegerVectors;
import pubsim.EisensteinInteger;
import pubsim.lattices.util.PointInSphere;
import pubsim.qam.hex.HexagonalCode;

/**
 * Class to compute an upper bound on the number of codeword errors that occur
 * using Voronoi code based Hexagonal QAM.  The follows along the same lines
 * as Dan's paper, however in this case only an upper bound can be achieved.
 * @author Robby McKilliam
 */
public class HexAmbiguityCalculator {

    private final int r;
    private final double scale;
    private final Vector<MobiusAndNumDivisors> mset = new Vector<MobiusAndNumDivisors>();

    private final Iterable<EisensteinInteger> code;
    private final Iterable<EisensteinInteger> ring;

    /**
     *
     * @param r_hex Voronoi code scaling factor
     * @param transcale scaling due to the Voronoi code translation
     * that is required to make the codewords lie on the ring of
     * Einstein integers.
     */
    public HexAmbiguityCalculator(int r_hex, double transcale){
        this.r = r_hex;
        this.scale = transcale;

        //make an iterable for the scaled hex codewords
        code = new Iterable<EisensteinInteger>() {
            HexagonalCode hex = new HexagonalCode(r);
            public Iterator<EisensteinInteger> iterator() {
                return new Iterator<EisensteinInteger>() {
                    IntegerVectors intvecs = new IntegerVectors(2, r);
                    public boolean hasNext() {
                        return intvecs.hasMoreElements();
                    }
                    public EisensteinInteger next() {
                        intvecs.nextElement();
                        double[] i = intvecs.nextElementDouble();
                        double[] d = hex.encode(i);
                        return new EisensteinInteger(scale*d[0], scale*d[1]);
                    }
                    public void remove() {
                        throw new UnsupportedOperationException("Not supported yet.");
                    }
                };
            }
        };

        //for( EisensteinInteger c : code ) System.out.println(c);
        System.out.println("***********************************************");

        //get the magnitude of the maximum magnitude codeword.
        double magmax = Double.NEGATIVE_INFINITY;
        for( EisensteinInteger p : code ){
            double mag = p.abs();
            if(mag > magmax) magmax = mag;
        }
        final double maxMag = magmax;

        //make an iterable for Eintien integers of sufficiently small magnitude
        ring = new Iterable<EisensteinInteger>() {
            final double[] origin = {0.0,0.0};
            public Iterator<EisensteinInteger> iterator() {
                return new Iterator<EisensteinInteger>() {
                    PointInSphere points = new PointInSphere(new Hexagonal(), maxMag, origin);
                    public boolean hasNext() {
                        return points.hasMoreElements();
                    }
                    public EisensteinInteger next() {
                        double[] d = points.nextElementDouble();
                        return new EisensteinInteger(d[0], d[1]);
                    }
                    public void remove() {
                        throw new UnsupportedOperationException("Not supported yet.");
                    }
                };
            }
        };

        int ringsize = 0;
        for( EisensteinInteger re : ring ) ringsize++;
        EisensteinInteger[] ringa = new EisensteinInteger[ringsize];
        int count = 0;
        for( EisensteinInteger re : ring ){
            ringa[count] = re;
            count++;
        }
        Arrays.sort(ringa);

        //for( EisensteinInteger re : ring ) System.out.println(re);
        System.out.println("***********************************************");

//        int ringsize = 0;
//        for( EisensteinInteger re : ring ) ringsize++;
//        int codesize = 0;
//        for( EisensteinInteger c : code ) codesize++;
//
//        System.out.println(codesize);
//        System.out.println(ringsize);
//
//        setup mset with enough memory.
//        mset = new Vector<MobiusAndNumDivisors>(ringsize*codesize);
        

        //compute the Mobius and divisors array
        int lastpercent = 0;
        for( int re = 0; re < ringa.length; re++ ){
            if(!ringa[re].isUnit()){
                int numdivs = 0;
                for( EisensteinInteger c : code ){
                    if( EisensteinInteger.divides(ringa[re], c)) numdivs++;
                }
                //System.out.println(new MobiusAndNumDivisors(ringa[re].factorise(ringa), numdivs));
                mset.add(new MobiusAndNumDivisors(ringa[re].factorise(ringa), numdivs));
            }

            int percentComplete = (int)(100*(((double)re)/ringa.length));
            if( percentComplete > lastpercent){
                System.out.println(percentComplete + "% ");
                //System.out.flush();
                lastpercent = percentComplete;
            }

        }

        System.out.println(mset.size());
        //for( MobiusAndNumDivisors m : mset ) System.out.println(m);
        System.out.println("***********************************************");

    }


    public double upperBoundAmbiguousCodewords(int N){
        double ambs = 0;
        for( MobiusAndNumDivisors m : mset ){
            ambs -= (m.mobius * Math.pow(m.numDivisors, N));
        }
        return ambs;
    }

    public double upperBoundBLER(int N){
        return Math.exp( Math.log(upperBoundAmbiguousCodewords(N))
                                                    - N*Math.log(r*r) );
    }


    /**
     * Class to hold the Mobius number and the number of codeword divisors
     * for a particular Gaussian integer.
     */
    public class MobiusAndNumDivisors{
        public final int mobius;
        public final int numDivisors;
        public MobiusAndNumDivisors(Collection<EisensteinInteger> factors, int d){
            mobius = mobiusFunction(factors);
            numDivisors = d;
        }

        /** return a string representation of the invoking Complex object */
        @Override
        public String toString() {
            return mobius + ", " + numDivisors;
        }

    }

    //Compute the Mobius function from a collection of factors.
    public static int mobiusFunction(Collection<EisensteinInteger> factors){

        //if there is only one element and it is a unit then return 1
        if(factors.size() == 1){
            if( factors.iterator().next().isUnit() ) return 1;
        }

        //copy this to an array, java's iterators are a bit crap.
        EisensteinInteger[] farray =
                factors.toArray(new EisensteinInteger[factors.size()]);

        //System.out.println(factors);

        for(int f1 = 0; f1 < farray.length; f1++){
            for(int f2 = f1+1; f2 < farray.length; f2++){
                if(EisensteinInteger.equivalentIdeal(farray[f1], farray[f2]))
                    return 0;
            }
        }

        //otherwise
        return (int)Math.pow(-1, factors.size());
    }

}
