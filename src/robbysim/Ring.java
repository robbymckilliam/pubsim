/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim;

/**
 * Mathematical ring.  Closed under addition and multiplication.
 * @author Robby McKilliam
 */
public interface Ring<T> {

    public T add(T that);

    public T subtract(T that);

    public T multiply(T that);

}
