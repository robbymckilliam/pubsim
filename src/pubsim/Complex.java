/*
 * Complex.java
 *
 * Created on 11 October 2007, 10:53
 */

package pubsim;

import java.io.Serializable;

/**
 * Basic complex functions.  I nicked this from 
 * http://www.cs.princeton.edu/introcs/97data/Complex.java.html 
 * <p>
 * @author Robby McKilliam
 */
public class Complex extends Object implements Serializable, Field<Complex>, Comparable<Complex>{
    
    protected double re;   // the real part
    protected double im;   // the imaginary part
    
    final public static Complex zero = new Complex(0,0);
    final public static Complex one = new Complex(1,0);

    /** create a new object with the given real and imaginary parts */
    public Complex(double real, double imag) {
        re = real;
        im = imag;
    }

    /** Copy constructor */
    public Complex(Complex x) {
        re = x.re;
        im = x.im;
    }
    
    /** Default constructor has zero real and imaginary components */
    public Complex() {
        re = 0;
        im = 0;
    }

    /**
     * Construct a complex number with magnitude m and phase a in radians
     */
    public static Complex polar(double m, double a){
        return new Complex(m*Math.cos(a), m*Math.sin(a));
    }
    
    /** return a string representation of the invoking Complex object */
    @Override
    public String toString() {
        if (im == 0) return re + "";
        if (re == 0) return im + "i";
        if (im <  0) return re + " - " + (-im) + "i";
        return re + " + " + im + "i";
    }

    /** return abs/modulus/magnitude */
    final public double abs()   { return Math.hypot(re, im); }  // Math.sqrt(re*re + im*im)
    
    /** Return the angle/phase/argument */
    final public double phase() { return Math.atan2(im, re); }  // between -pi and pi
    
    /** Return abs/modulus/magnitude */
    final public double abs2()   { return re*re + im*im; }

    /** return a new Complex object whose value is (this + b) */
    final public Complex plus(Complex b) {
        return new Complex(re + b.re, im + b.im);
    }
    
//    /** Complex addition (this + b) in place */
//    public Complex plusP(Complex b) {
//        return set(re + b.re, im + b.im);
//    }

    /** return a new Complex object whose value is (this - b) */
    final  public Complex minus(Complex b) {
        return new Complex(re - b.re, im - b.im);
    }
    
//    /** Complex subtraction (this + b) in place */
//    public Complex minusP(Complex b) {
//        return set(re - b.re, im - b.im);
//    }

    /** return a new Complex object whose value is (this * b) */
    final public Complex times(Complex b) {
        Complex a = this;
        double real = a.re * b.re - a.im * b.im;
        double imag = a.re * b.im + a.im * b.re;
        return new Complex(real, imag);
    }
    
//    /** multiply in place */
//    public Complex timesP(Complex b){
//        double real = this.re * b.re - this.im * b.im;
//        double imag = this.re * b.im + this.im * b.re;
//        return set(real, imag);
//    }

    /** 
     * scalar multiplication. <br>
     * return a new object whose value is (this * alpha)
     */
    public Complex times(double alpha) {
        return new Complex(alpha * re, alpha * im);
    }
    
//    /** 
//     * scalar multiplication in place. <br>
//     * return a new object whose value is (this * alpha)
//     */
//    public Complex timesP(double alpha) {
//        return set(alpha * re, alpha * im);
//    }

    /** 
     * Convert this complex number into a complex number from the 
     * flanagan library.
     */
    public flanagan.complex.Complex toFlanComplex(){
        return new flanagan.complex.Complex(re, im);
    }

    /** return a new Complex object whose value is the conjugate of this */
    public Complex conjugate() {  return new Complex(re, -im); }
    
//    /** conjugate in place. */
//    public Complex conjugateP() {  
//        return set(re, -im); 
//    }

    /** return a new Complex object whose value is the reciprocal of this */
    public Complex reciprocal() {
        double scale = re*re + im*im;
        return new Complex(re / scale, -im / scale);
    }
    
//    /** calculate the reciprocal of this Complex in place. */
//    public Complex reciprocalP() {
//        double scale = re*re + im*im;
//        return  set(re / scale, -im / scale);
//    }

    /** return the real or imaginary part */
    final public double re() { return re; }
    final public double im() { return im; }

    /** return a / b */
    public Complex divides(Complex b) {
        Complex a = this;
        return a.times(b.reciprocal());
    }
    
//    /** Calculates a / b in place. */
//    public Complex dividesP(Complex b) {
//        working.copy(b);
//        return this.timesP(working.reciprocalP());
//    }

    /** 
     * return a new Complex object whose 
     * value is the complex exponential of this
     */
    public Complex exp() {
        return new Complex(Math.exp(re) * Math.cos(im), 
                Math.exp(re) * Math.sin(im));
    }

    /** return a new Complex object whose value is the complex sine of this */
    public Complex sin() {
        return new Complex(Math.sin(re) * Math.cosh(im), 
                Math.cos(re) * Math.sinh(im));
    }

    /** return a new Complex object whose value is the complex cosine of this */
    public Complex cos() {
        return new Complex(Math.cos(re) * Math.cosh(im), 
                -Math.sin(re) * Math.sinh(im));
    }

    /** return a new Complex object whose value is the tangent of this */
    public Complex tan() {
        return sin().divides(cos());
    }
    
    /** a static version of plus */
    public static Complex plus(Complex a, Complex b) {
        return new Complex(a.re + b.re, a.im + b.im);
    }
    
    /** Test if this complex number is equal to c */
    public boolean equals(Complex c){
        return c.re == re && c.im == im;
    }

    @Override
    public Complex add(Complex that) {
        return this.plus(that);
    }

    @Override
    public Complex multiply(Complex that) {
        return this.times(that);
    }

    @Override
    public Complex subtract(Complex that) {
        return this.minus(that);
    }

    @Override
    public Complex divide(Complex that) {
        return this.divides(that);
    }

    /**
     * Compares complex numbers based on magnitude.
     * This is not strictly natural, but it is useful.
     */
    @Override
    public int compareTo(Complex o) {
        return Double.compare(abs2(), o.abs2());
    }

    
}
