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

    protected int N;
    protected double var, amplitude, ntn, nt1;
    protected double[] narray;
    
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
        
        dPOverP func = new dPOverP();
        func.setv(v);
        
        Integration intg = new Integration(func, -0.5, 0.5);
        
        return dets/intg.gaussQuad(1000);
    }
    
    
    protected class dPOverP implements IntegralFunction {
        
        double v = 0.0;
        
        public void setv(double v){
            this.v = v;
        }

        public double function(double x) {
            
            double fd = distributions.circular.ProjectedNormalDistribution.dPdf(x, v);
            double f = distributions.circular.ProjectedNormalDistribution.Pdf(x, v);

            return fd*fd/f;
            
        }
    
    }

}
