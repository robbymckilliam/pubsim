/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.util;

/**
 * PointEnumerator with the ability to be restarted
 * @author Robby McKilliam
 */
public interface RestartablePointEnumerator extends PointEnumerator{

    /** Restart the enumerator */
    void restart();

}
