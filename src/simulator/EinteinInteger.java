/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator;

import Jama.Matrix;
import java.util.Collection;
import java.util.Vector;
import lattices.Hexagonal;
import lattices.util.PointInSphere;

/**
 *
 * @author Robby McKilliam
 */
public class EinteinInteger extends Complex {

    /** Hexagonal lattice for doing rounding etc */
    protected final static Hexagonal hex_lattice = new Hexagonal();

    /** Tolerance when testing equality */
    private static final double tol = 0.0000001;

    public EinteinInteger(double x, double y){
        double[] a = {x, y};
        hex_lattice.nearestPoint(a);
        double[] r = hex_lattice.getLatticePoint();
        this.re = r[0];
        this.im = r[1];
    }

    public EinteinInteger(Complex x){
        double[] a = {x.re(), x.im()};
        hex_lattice.nearestPoint(a);
        double[] r = hex_lattice.getLatticePoint();
        this.re = r[0];
        this.im = r[1];
    }


    public boolean isUnit(){
        return (this.abs2() - 1.0) < tol;
    }

    public boolean isZero(){
        return (this.abs2()) < tol;
    }

    public static double[] toArray(EinteinInteger x){
        double[] a = {x.re(), x.im()};
        return a;
    }

    public static EinteinInteger fromArray(double[] a){
        return new EinteinInteger(a[0], a[1]);
    }

    public static EinteinInteger fromMatrix(Matrix m){
        return new EinteinInteger(m.get(0,0), m.get(1,0));
    }

    /** Test if this EinteinInteger number is equal to Complex c */
    @Override
    public boolean equals(Complex c){
        return this.minus(c).abs() < tol;
    }

    public static boolean isInteger(Complex x){
        EinteinInteger e = new EinteinInteger(x);
        return e.equals(x);
    }

    public static boolean divides(EinteinInteger a, EinteinInteger b){
        return isInteger(b.divide(a));
    }

    /**
     * Factorise this integer
     * @return Collection of factors
     */
    public Collection<EinteinInteger> factorise() {
        Collection<EinteinInteger> factors = new Vector<EinteinInteger>();
        double[] origin = {0.0,0.0};
        PointInSphere points = new PointInSphere(hex_lattice, this.abs(), origin);

        System.out.println("this = " + this);

        //  If this is a unit return an empty set of factors
        if(this.isUnit()) return factors;

        //Test all the integers who's magnitude is less that this one
        //to see if it divides
        boolean prime = true;
        for( Matrix p : points ){
            EinteinInteger i = fromMatrix(p);
            //if there are two factors, then factorise them and
            //add their factors to this collection.
            if( divides(i, this) && !i.isUnit() && !i.isZero() ){
                EinteinInteger div = new EinteinInteger(this.divide(i));
                if( !div.isUnit() && !div.isZero() ){
                    System.out.println("i = " + i);
                    System.out.println("div = " + div);
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

}
