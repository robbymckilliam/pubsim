/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.fes.crb;

import flanagan.integration.IntegralFunction;
import flanagan.integration.Integration;
import simulator.Util;

/**
 *
 * @author Robby
 */
public class Hamersley implements BoundCalculator {

    int N;
    double var, amplitude, ntn, nt1;
    double[] narray;
    
    /** Constructor.  The amplitude defaults to 1. */
    public Hamersley(){
        setAmplitude(1.0);
    }
    
    public void setN(int N) {
        this.N = N;
        narray = new double[this.N];
        ntn = 0.0; nt1 = 0.0;
        for(int i = 0; i < this.N; i++){
            narray[i] = i+1;
            ntn += (i+1)*(i+1);
            nt1 += i+1;
        }        
    }

    public void setVariance(double var) {
        this.var = var;
    }

    public void setAmplitude(double amp) {
        amplitude = amp;
    }

    public double getBound() {
        
        double dets = N/(ntn*N - nt1*nt1);
        double v = amplitude/Math.sqrt(var);
        
        IntFunc func = new IntFunc();
        func.setv(v);
        
        Integration intg = new Integration(func, -0.5, 0.5);
        
        return dets/intg.gaussQuad(1000);
    }
    
    protected class IntFunc implements IntegralFunction {
        
        double v = 0.0;
        
        public void setv(double v){
            this.v = v;
        }

        public double function(double x) {
            
            double pi = Math.PI;
            double cx = Math.cos(2*pi*x);
            double sx = Math.sin(2*pi*x);
            
            double p1 = v*cx;
            double p2 = Math.exp( -v*v/2 * sx*sx);
            double p3 = Math.sqrt(pi/2) * ( 1.0 + Util.erf( v/Math.sqrt(2)*cx ) );

            double f = Math.exp(-v*v/2) + p1*p2*p3;

            double dp1 = -v*2*pi*sx;
            double dp2 = -2*pi*v*v * cx * sx * Math.exp(-v*v/2 * sx*sx);
            double dp3 = -v*2* pi * sx * Math.exp(-v*v/2 * cx*cx);

            double fd = dp1*p2*p3 + p1*dp2*p3 + p1*p2*dp3;

            return fd*fd/f;
            
        }
    
    }

}
