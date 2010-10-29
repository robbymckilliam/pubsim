/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim;

import Jama.Matrix;

/**
 * Simple extension of Jama.Matrix which is only a two element column vector.
 * @author Robby McKilliam
 */
public class Point2 extends Matrix{

    public Point2(){
        super(2, 1);
    }

    public Point2(double[] x){
        super(2, 1);
        if(x.length != 2)
            throw new ArrayIndexOutOfBoundsException("Must be vector of length 2");
        set(0, 0, x[0]);
        set(1, 0, x[1]);
    }

    public Point2(Matrix x){
        super(2, 1);
        if(x.getRowDimension() != 2 || x.getColumnDimension() != 1)
            throw new ArrayIndexOutOfBoundsException("Must be matrix of size 2x1");
        set(0, 0, x.get(0, 0));
        set(1, 0, x.get(1, 0));
    }

    public Point2(double x, double y){
        super(2, 1);
        set(0, 0, x);
        set(1, 0, y);
    }

    public double getX(){
        return get(0,0);
    }

    public double getY(){
        return get(1,0);
    }

    public double magnitude(){
        return normF();
    }

    /**
     * @return x^2 + y^2
     */
    public double magnitude2(){
        return getX()*getX() + getY()*getY();
    }

    public boolean equals(Point2 p){
        return get(0,0) == p.get(0,0) && get(1,0) == p.get(1,0);
    }

    public boolean equals(Point2 p, double tol){
        boolean b1 = Math.abs(get(0,0) - p.get(0,0)) < tol;
        boolean b2 = Math.abs(get(1,0) - p.get(1,0)) < tol;
        return b1 && b2;
    }

    /** Dot (inner) product between two points */
    public static double dot(Point2 a, Point2 b){
        return a.getX()*b.getX() + a.getY()*b.getY();
    }

    /** return a string representation of the invoking Complex object */
    @Override
    public String toString() {
        return "( " + getX() + ", " + getY() + " )";
    }

}
