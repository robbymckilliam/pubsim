/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.fes.crb;

import Jama.Matrix;
import java.util.Iterator;
import lattices.decoder.ZnWithinSphere;
import simulator.VectorFunctions;

/**
 * This is the Hammersley-Chapman-Robbins bound for frequency
 * estimation by phase unwrapping.
 * @author Robby McKilliam
 */
public class Hamersley implements simulator.fes.crb.BoundCalculator {

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
        Matrix num = new Matrix(2,2);
        Matrix den = new Matrix(3,3);
        
        //System.out.println(N);
        //System.out.println(2*Math.sqrt(sts));
        
        //ZnWithinSphere deltas = new ZnWithinSphere(N, Math.sqrt(ntn));
        ZnWithinSphere deltas = new ZnWithinSphere(N, 5.1);
        
        double B = Double.NEGATIVE_INFINITY;
        Iterator itr = deltas.iterator();
        while(itr.hasNext()){
            
            double[] delta = (double[]) itr.next();
            
            //System.out.println(VectorFunctions.print(delta));
                      
            double dt1 = VectorFunctions.sum(delta);
            double dtn = VectorFunctions.dot(delta, narray);
            double magd = VectorFunctions.magnitude(delta);
            double dtd = VectorFunctions.sum2(delta);
            
            //set the numerator matrix
            num.set(0,0, N/var); num.set(0,1, dt1/(var*magd));
            num.set(1,0, dt1/(var*magd)); num.set(1,1, Math.expm1(dtd/var)/dtd);

            //set the numerator matrix
            den.set(0,0, ntn/var); den.set(0,1, nt1/var); den.set(0,2, dtn/(var*magd));
            den.set(1,0, nt1/var); den.set(1,1, N/var); den.set(1,2, dt1/(var*magd));
            den.set(2,1, dtn/(var*magd)); den.set(2,1, dt1/(var*magd)); den.set(2,2, Math.expm1(dtd/var)/dtd);
            
            double bound = VectorFunctions.stableDet(num)/VectorFunctions.stableDet(den);
            if(bound > B && Math.abs(VectorFunctions.sum(delta)) > 0.5){
                B = bound;
            }
        }
        
        return B;
    }
    
    

}
