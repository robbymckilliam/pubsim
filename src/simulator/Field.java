/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator;

/**
 * Mathematical field.  Implements multiple, divide, add, subtract and equals.
 * @author Robby McKilliam
 */
public interface Field<T> {

    public T add(T that);

    public T multiply(T that);

    public T subtract(T that);

    public T divide(T that);

}
