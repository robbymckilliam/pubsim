/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim;

/**
 * Mathematical field.  Implements multiply, divide, add, subtract and equals.
 * @author Robby McKilliam
 */
public interface Field<T> extends Ring<T>{

    public T divide(T that);

}
