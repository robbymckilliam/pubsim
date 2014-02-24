package pubsim;

import Jama.Matrix;
import java.util.Collection;
import java.util.Vector;
import pubsim.lattices.Hexagonal;
import pubsim.lattices.util.PointInSphere;

/**
 *
 * @author Robby McKilliam
 */
public class EisensteinInteger extends Complex {

    /** Hexagonal lattice for doing rounding etc */
    protected final static Hexagonal hex_lattice = new Hexagonal();

    /** Tolerance when testing equality */
    private static final double tol = 0.0000001;
    
    public EisensteinInteger(Complex x){
        this(x.re,x.im);
    }

    public EisensteinInteger(double x, double y){
        super(x,y);
        double[] a = {x, y};
        hex_lattice.nearestPoint(a);
        double[] r = hex_lattice.getLatticePoint();
        re = r[0];
        im = r[1];
    }

    public boolean isUnit(){
        return (this.abs2() - 1.0) < tol;
    }

    /**
     * Return true if the two integers are a = u*b where u is a unit integer
     */
    public static boolean equivalentIdeal(EisensteinInteger a, EisensteinInteger b){
        for( EisensteinInteger u : units() ){
            if(a.equals(u.times(b))) return true;
        }
        return false;
    }

    /** Return an container with all of the units */
    public static Vector<EisensteinInteger> units() {
        final Vector<EisensteinInteger> units = new Vector<EisensteinInteger>();
        units.add(new EisensteinInteger(1.0, 0.0));
        units.add(new EisensteinInteger(0.5, Math.sqrt(3)/2.0));
        units.add(new EisensteinInteger(-0.5, Math.sqrt(3)/2.0));
        units.add(new EisensteinInteger(-1.0, 0.0));
        units.add(new EisensteinInteger(-0.5, -Math.sqrt(3)/2.0));
        units.add(new EisensteinInteger(0.5, -Math.sqrt(3)/2.0));
        return units;
    }

    public boolean isZero(){
        return (this.abs2()) < tol;
    }

    public static double[] toArray(EisensteinInteger x){
        double[] a = {x.re(), x.im()};
        return a;
    }

    public static EisensteinInteger fromArray(double[] a){
        return new EisensteinInteger(a[0], a[1]);
    }

    public static EisensteinInteger fromMatrix(Matrix m){
        return new EisensteinInteger(m.get(0,0), m.get(1,0));
    }

    /** Test if this EisensteinInteger number is equal to Complex c */
    @Override
    public boolean equals(Complex c){
        return this.minus(c).abs() < tol;
    }

    public static boolean isInteger(Complex x){
        EisensteinInteger e = new EisensteinInteger(x);
        return e.equals(x);
    }

    public static boolean divides(EisensteinInteger a, EisensteinInteger b){
        return isInteger(b.divide(a));
    }

    /**
     * Factorise this integer.  This uses a sphere decoder to work out
     * which points to test.  The implementation of the sphere decoder
     * here uses a thread an this can cause problems.  It's recommended
     * that you use factorise(array) instead.
     * @return Collection of factors
     */
    public Collection<EisensteinInteger> factorise() {
        Collection<EisensteinInteger> factors = new Vector<EisensteinInteger>();
        double[] origin = {0.0,0.0};
        PointInSphere points = new PointInSphere(hex_lattice, this.abs(), origin);

        //System.out.println("this = " + this);

        //  If this is a unit return an empty set of factors
        if(this.isUnit()) return factors;

        //Test all the integers who's magnitude is less that this one
        //to see if it divides
        boolean prime = true;
        for( Matrix p : points ){
            EisensteinInteger i = fromMatrix(p);
            //if there are two factors, then factorise them and
            //add their factors to this collection.
            if( divides(i, this) && !i.isUnit() && !i.isZero() ){
                EisensteinInteger div = new EisensteinInteger(this.divide(i));
                if( !div.isUnit() && !div.isZero() ){
                    //System.out.println("i = " + i);
                    //System.out.println("div = " + div);
                    factors.addAll(i.factorise());
                    factors.addAll(div.factorise());
                    prime = false;
                    break;
                }
            }
        }
        //if no divisors this is prime so add it to the list of factors.
        if(prime) factors.add(this);

        return factors;
    }
    
    /**
     * Factorise this integer.  Assumes that the integer is contained in ring
     * and ring contains all of the element of magnitude less than ring.
     * Also assumes that ring has been sorted in ascending order of magnitude.
     *
     * This is much faster than the other version.
     * @return Collection of factors
     */
    public Collection<EisensteinInteger> factorise(EisensteinInteger[] ring) {
        Collection<EisensteinInteger> factors = new Vector<EisensteinInteger>();

        //System.out.println("this = " + this);

        //  If this is a unit return an empty set of factors
        if(this.isUnit()) return factors;

        //Test all the integers who's magnitude is less that this one
        //to see if it divides
        boolean prime = true;
        for( int p = 0; p < ring.length; p++ ){
            EisensteinInteger i = ring[p];
            if(ring[p].abs() > this.abs()) break;
            //if there are two factors, then factorise them and
            //add their factors to this collection.
            if( divides(i, this) && !i.isUnit() && !i.isZero() ){
                EisensteinInteger div = new EisensteinInteger(this.divide(i));
                if( !div.isUnit() && !div.isZero() ){
                    //System.out.println("i = " + i);
                    //System.out.println("div = " + div);
                    factors.addAll(i.factorise(ring));
                    factors.addAll(div.factorise(ring));
                    prime = false;
                    break;
                }
            }
        }
        //if no divisors this is prime so add it to the list of factors.
        if(prime) factors.add(this);

        return factors;
    }


}
