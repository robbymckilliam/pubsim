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
    
    final public double real;   // the real part
    final public double imag;   // the imaginary part
    
    final public static Complex zero = new Complex(0,0);
    final public static Complex one = new UnitCircle(1.0);

    /** create a new object with the given real and imaginary parts */
    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    /** Copy constructor */
    public Complex(Complex x) {
        real = x.real;
        imag = x.imag;
    }
    
    /** Default constructor has zero real and imaginary components */
    public Complex() {
        real = 0;
        imag = 0;
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
        if (imag == 0) return real + "";
        if (real == 0) return imag + "i";
        if (imag <  0) return real + " - " + (-imag) + "i";
        return real + " + " + imag + "i";
    }

    /** return abs/modulus/magnitude */
    public double abs()   { return Math.hypot(real, imag); }  // Math.sqrt(real*real + imag*imag)
    
    /** Return the angle/phase/argument */
    public double phase() { return Math.atan2(imag, real); }  // between -pi and pi
    
    /** Return abs/modulus/magnitude */
    public double abs2()   { return real*real + imag*imag; }

    /** return a new Complex object whose value is (this + b) */
    public Complex plus(Complex b) {
        return new Complex(real + b.real, imag + b.imag);
    }
    
//    /** Complex addition (this + b) in place */
//    public Complex plusP(Complex b) {
//        return set(real + b.real, imag + b.imag);
//    }

    /** return a new Complex object whose value is (this - b) */
    public Complex minus(Complex b) {
        return new Complex(real - b.real, imag - b.imag);
    }
    
//    /** Complex subtraction (this + b) in place */
//    public Complex minusP(Complex b) {
//        return set(real - b.real, imag - b.imag);
//    }

    /** return a new Complex object whose value is (this * b) */
    public Complex times(Complex b) {
        double r = this.real * b.real - this.imag * b.imag;
        double i = this.real * b.imag + this.imag * b.real;
        return new Complex(r, i);
    }
    
//    /** multiply in place */
//    public Complex timesP(Complex b){
//        double real = this.real * b.real - this.imag * b.imag;
//        double imag = this.real * b.imag + this.imag * b.real;
//        return set(real, imag);
//    }

    /** 
     * scalar multiplication. <br>
     * return a new object whose value is (this * alpha)
     */
    public Complex times(double alpha) {
        return new Complex(alpha * real, alpha * imag);
    }
    
//    /** 
//     * scalar multiplication in place. <br>
//     * return a new object whose value is (this * alpha)
//     */
//    public Complex timesP(double alpha) {
//        return set(alpha * real, alpha * imag);
//    }

    /** 
     * Convert this complex number into a complex number from the 
     * flanagan library.
     */
    public flanagan.complex.Complex toFlanComplex(){
        return new flanagan.complex.Complex(real, imag);
    }

    /** return a new Complex object whose value is the conjugate of this */
    public Complex conjugate() {  return new Complex(real, -imag); }
    
//    /** conjugate in place. */
//    public Complex conjugateP() {  
//        return set(real, -imag); 
//    }

    /** return a new Complex object whose value is the reciprocal of this */
    public Complex reciprocal() {
        double scale = real*real + imag*imag;
        return new Complex(real / scale, -imag / scale);
    }
    
//    /** calculate the reciprocal of this Complex in place. */
//    public Complex reciprocalP() {
//        double scale = real*real + imag*imag;
//        return  set(real / scale, -imag / scale);
//    }

    /** return the real or imaginary part */
    final public double re() { return real; }
    final public double im() { return imag; }

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
        return new Complex(Math.exp(real) * Math.cos(imag), 
                Math.exp(real) * Math.sin(imag));
    }

    /** return a new Complex object whose value is the complex sine of this */
    public Complex sin() {
        return new Complex(Math.sin(real) * Math.cosh(imag), 
                Math.cos(real) * Math.sinh(imag));
    }

    /** return a new Complex object whose value is the complex cosine of this */
    public Complex cos() {
        return new Complex(Math.cos(real) * Math.cosh(imag), 
                -Math.sin(real) * Math.sinh(imag));
    }

    /** return a new Complex object whose value is the tangent of this */
    public Complex tan() {
        return sin().divides(cos());
    }
    
    /** a static version of plus */
    public static Complex plus(Complex a, Complex b) {
        return new Complex(a.real + b.real, a.imag + b.imag);
    }
    
    /** Test if this complex number is equal to c */
    public boolean equals(Complex c){
        return c.real == real && c.imag == imag;
    }
    
    /** Returns this complex number to the power of r */
    public Complex pow(double r){
        final double theta = r*phase();
        final double A = Math.pow(abs(), r);
        return polar(A,theta);
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

    /**
     * Represents a complex number on the unit circle. Optimises multiplications
     * and powering for this case.
     */
    public static class UnitCircle extends Complex {

        final protected double phase;

        public UnitCircle(double phase) {
            super(Complex.polar(1.0, phase));
            this.phase = phase;
        }
        
        /**
         * Returns this complex number to the power of r
         */
        @Override
        public Complex pow(double r) {
            return new UnitCircle(phase * r);
        }
        
        /** UnitCircle retained if multiplying two UnitCircles together */
        public UnitCircle times(UnitCircle c){
            return new UnitCircle(phase * c.phase);
        }

        /**
         * return abs/modulus/magnitude
         */
        @Override
        public double abs() {
            return 1.0;
        }  

        /**
         * Return the angle/phase/argument
         */
        @Override
        public double phase() {
            return phase;
        }  

        /**
         * Return abs/modulus/magnitude
         */
        @Override
        public double abs2() {
            return 1.0;
        }

    }
    
}
