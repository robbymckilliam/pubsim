/*
 * This project is made available under the terms of the BSD license, more information can be found at
 * http://www.opensource.org/licenses/bsd-license.html
 *
 * Copyright (c) 2007. Christian Ullenboom (http://www.tutego.com/) and contributors. All rights reserved.
 */
package robbysim;

import java.util.Iterator;

/**
 * Class that generates immutable sequences (ranges) as Iterable<Integer>
 * objects. A range represents a start (0 if not given), an stop (mandatory) and
 * an optional step (1 by default). The start value is included in the range,
 * the stop value is exclusive. Every range is handled by an Iterable<Integer>
 * which can by used in an extended for loop.
 *
 * <pre>
 * for ( int i : range( 0, 10, 3 ) )
 *   System.out.print( i + " " ); // 0 3 6 9
 * </pre>
 *
 * @author Christian Ullenboom (tutego)
 * @version 1.0
 */
public class Range {

    /** Iterate from start to stop with step */
    public static Iterable<Integer> range(final int start, final int stop, final int step) {
        if (step <= 0) {
            throw new IllegalArgumentException("step > 0 isrequired!");
        }

        return new Iterable<Integer>() {

            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {

                    private int counter = start;

                    public boolean hasNext() {
                        return counter < stop;
                    }

                    public Integer next() {
                        try {
                            return counter;
                        } finally {
                            counter += step;
                        }
                    }

                    public void remove() {
                    }
                };
            }
        };
    }

    /** Iterate from start to stop with step = 1 */
    public static Iterable<Integer> range(final int start, final int stop) {
        return range(start, stop, 1);
    }

    /** Iterate from 0 to stop with step = 1 */
    public static Iterable<Integer> range(final int stop) {
        return range(0, stop, 1);
    }

    /** Iterate from start to stop with step */
    public static Iterable<Double> range(final double start, final double stop, final double step) {
        if (step <= 0) {
            throw new IllegalArgumentException("step > 0 isrequired!");
        }

        return new Iterable<Double>() {

            public Iterator<Double> iterator() {
                return new Iterator<Double>() {

                    private double counter = start;

                    public boolean hasNext() {
                        return counter < stop;
                    }

                    public Double next() {
                        try {
                            return counter;
                        } finally {
                            counter += step;
                        }
                    }

                    public void remove() {
                    }
                };
            }
        };
    }

    /** Iterate from start to stop with step = 1 */
    public static Iterable<Double> range(final double start, final double stop) {
        return range(start, stop, 1);
    }

    /** Iterate from 0 to stop with step = 1 */
    public static Iterable<Double> range(final double stop) {
        return range(0, stop, 1);
    }
}
