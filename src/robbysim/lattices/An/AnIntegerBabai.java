/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.lattices.An;

import robbysim.lattices.Anstar.Anstar;

/**
 * Ridiculously simple linear time nearest point
 * algorithm for An.  It's only approximate
 * @author Robby McKilliam
 */
public class AnIntegerBabai extends An {

    double[] v, yp;

    public AnIntegerBabai(int n){
        setDimension(n);
    }

    public void setDimension(int n) {
        this.n = n;
        u = new double[n];
        v = new double[n+1];
        yp = new double[n+1];
    }

    public void nearestPoint(double[] y) {
        if(y.length != n+1) setDimension(y.length - 1);

        Anstar.project(y, yp);

        //set v to zeros
        for(int i = 0; i < n; i++)
            v[i] = 0.0;
        
        double prev = 0;
        for(int i = 0; i < n; i++){
            u[i] = Math.round(yp[i]) + prev;
            prev = u[i];
        }

        //calcualte lattice point.  This effectively
        //multiplies by the generator matrix.
        v[0] = u[0];
        for(int i = 1; i < n; i++)
            v[i] =u[i] - u[i-1];
        v[n] = -u[n-1];

    }

    @Override
    public double[] getLatticePoint() {
        return v;
    }

}
