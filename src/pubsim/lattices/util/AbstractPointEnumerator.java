package pubsim.lattices.util;

import Jama.Matrix;

/**
 *
 * @author Robby McKilliam
 */
public abstract class AbstractPointEnumerator implements PointEnumerator{

    public abstract double[] nextElementDouble();

    public abstract double percentageComplete();

    public abstract boolean hasMoreElements();

    public abstract Matrix nextElement();

    public java.util.Iterator<Matrix> iterator() {
        return new Iterator();
    }

    protected class Iterator implements java.util.Iterator<Matrix>{

        public boolean hasNext() {
            return hasMoreElements();
        }

        public Matrix next() {
            return nextElement();
        }

        public void remove() {
        }

    }

}