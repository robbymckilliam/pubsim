/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.hex;

import pubsim.lattices.LatticeAndNearestPointAlgorithmInterface;
import pubsim.Complex;
import pubsim.Point2;
import pubsim.VectorFunctions;

/**
 * Contains some standard numenclature and useful classes for
 * line search based Nearset hex decoders.
 * @author Robby McKilliam
 */
public abstract class LineHexReciever implements HexReciever{

    /** Vectors to store received codewords */
    protected final double[] ur, ui;

    /** Stores the search plane */
    protected final double[] y1, y2;

    /** Stores the current search line */
    protected final double[] d1, d2;

    /** Block length */
    protected final int N;

    /** Hexagonal Voronoi code used */
    protected final HexagonalCode hex;

    public LineHexReciever(int N, int scale){
        this.hex = new HexagonalCode(scale);
        ur = new double[N];
        ui = new double[N];
        y1 = new double[2*N];
        y2 = new double[2*N];
        d1 = new double[N];
        d2 = new double[N];
        this.N = N;
    }

    /**
     * Array containing 6 boundaries of a hexagon.  This is the position
     * of the relavant vectors of the hexagonal lattice
     */
    protected static final Point2[] hexagonalBoundaries = {
                        new Point2(1.0,0.0),
                        new Point2(-1.0,0.0),
                        new Point2( 0.5, Math.sqrt(3.0)/2.0 ),
                        new Point2( -0.5, Math.sqrt(3.0)/2.0 ),
                        new Point2( -0.5, -Math.sqrt(3.0)/2.0 ),
                        new Point2( 0.5, -Math.sqrt(3.0)/2.0 )
                    };

    /** Class to contain an Integer index a Double and a Point2 */
    public static class DoubleAndPoint2AndIndex implements Comparable{
        public Integer index;
        public Double value;
        public Point2 point;

        @Override
        public String toString() {
            return index + ", " + value + ", " + point;
        }

        @Override
        public int compareTo(Object o) {
            DoubleAndPoint2AndIndex co = (DoubleAndPoint2AndIndex) o;
            return Double.compare(value, co.value);
        }

    }

    /**
     * Compute the next crossed point in the hexagonal code.  Line is d1, d2
     * and current hexagonal code point is c1, c2.
     */
    public static DoubleAndPoint2AndIndex nextHexangonalNearPoint(
            double d1, double d2,
            double c1, double c2){

        Point2 c = new Point2(c1, c2);
        Point2 d = new Point2(d1, d2);

        double rmin = Point2.dot(c,d)/Point2.dot(d,d);

        DoubleAndPoint2AndIndex dp = new DoubleAndPoint2AndIndex();
        dp.value = Double.POSITIVE_INFINITY;
        for(int n = 0; n < hexagonalBoundaries.length; n++){
            Point2 v = hexagonalBoundaries[n];
            double cv = Point2.dot(c, v);
            double dv = Point2.dot(d, v);
            double vv = Point2.dot(v, v);
            double rnextp = (cv + vv)/dv;
            double r = (cv + vv/2.0)/dv;
            if(rnextp > rmin && r < dp.value){
                dp.value = r;
                dp.point = v;
            }
        }
        return dp;
    }


    public double[] getReal() {
        return ur;
    }

    public double[] getImag() {
        return ui;
    }

    public void setChannel(Complex h) {
        //do nothing.  This is a noncoherent detector.  The channel will
        //be estimated.
    }

    public boolean inScaledVor(Complex c){
        final double r =  hex.getScale();
        double[] y = {c.re()/r, c.im()/r};
        hex.getLattice().nearestPoint(y);
        double[] x = hex.getLattice().getLatticePoint();
        return VectorFunctions.magnitude(x) < 0.000001;
    }


}
