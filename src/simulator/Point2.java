/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator;

import Jama.Matrix;

/**
 * Simple extension of Jama.Matrix which is only a two element column vector.
 * @author Robby McKilliam
 */
public class Point2 extends Matrix{

    public Point2(){
        super(2, 1);
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

}
