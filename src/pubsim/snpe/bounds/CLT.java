/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.snpe.bounds;

/**
 *
 * @author Robby McKilliam
 */
public interface CLT {
    
    public double phaseVar(int N);

    public double periodVar(int N);

    public double periodPhaseCoVar(int N);

    
}
