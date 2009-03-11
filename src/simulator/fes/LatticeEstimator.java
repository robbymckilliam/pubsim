/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.fes;

import Jama.Matrix;
import lattices.Phin2Star;

/**
 * Abstract class for the frequency estimators that use the Phina2Star
 * lattice.
 * @author Robby McKilliam
 */
public abstract class LatticeEstimator implements FrequencyEstimator{

    protected int n;
    protected Phin2Star lattice;
    protected double[] y;
    protected Matrix K;

    @Override
    public void setSize(int n) {
        this.n = n;
        lattice.setDimension(n - 2);

        y = new double[n];
        //p = new double[a];

        Matrix M = lattice.getMMatrix();
        Matrix Mt = M.transpose();
        K = Mt.times(M).inverse().times(Mt);

    }


        /** Run the estimator on recieved data, @param y */
    @Override
    public double estimateFreq(double[] real, double[] imag){
        if(n+2 != real.length)
            setSize(real.length);

        for(int i = 0; i < real.length; i++)
            y[i] = Math.atan2(imag[i],real[i])/(2*Math.PI);

        lattice.nearestPoint(y);

        //calculate f from the nearest point
        double f = 0;
        double N = n;   //avoid integer arithmetic overflows for n >= 1024!
        double sumn = N*(N+1)/2.0;
        double sumn2 = N*(N+1)*(2.0*N+1)/6.0;
        double[] u = lattice.getIndex();
        for(int i = 0; i < n; i++)
            f += (N*(i+1) - sumn)*(y[i]-u[i]);

        f /= (sumn2*n - sumn*sumn);

        //System.out.println("f = " + f);

        return f;

    }

}
