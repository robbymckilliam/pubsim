/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.pes.bounds;

import Jama.Matrix;
import java.util.Iterator;
import robbysim.lattices.util.ZnWithinSphere;
import robbysim.VectorFunctions;

/**
 * This is the Hamerley-Chapmin-Robbins bound that
 * includes the discrete parameter.  It is only tight when
 * sigma^2/T^2 is less than log(3).  This is generally in
 * the range that the estimators work so it's not a
 * terrible restriction.
 * @author Robby McKilliam
 */
public class HamersleyFixedS extends ClairvoyantCRB{
    
    int N;
    double sts, st1;
    
    /** Set the vector of indicies */
    @Override
    public void setS(double[] s){
        this.s = s;
        sts = VectorFunctions.sum2(s);
        st1 = VectorFunctions.sum(s);
        N = s.length;
        
    }
    
    /** Return the Hamerley-Chapmin-Robbins bound for the set parameters */
    @Override
    public double getBound(){
        
        Matrix num = new Matrix(2,2);
        Matrix den = new Matrix(3,3);
        
        System.out.println(N);
        System.out.println(2*Math.sqrt(sts));
        
        ZnWithinSphere deltas = new ZnWithinSphere(N, 2*Math.sqrt(sts));
        
        double B = Double.NEGATIVE_INFINITY;
        Iterator itr = deltas.iterator();
        while(itr.hasNext()){
            
            double[] delta = (double[]) itr.next();
            
            //System.out.println(VectorFunctions.print(delta));
            
            
            double dt1 = VectorFunctions.sum(delta);
            double dts = VectorFunctions.dot(delta, s);
            double magd = VectorFunctions.magnitude(delta);
            double dtd = VectorFunctions.sum2(delta);
            
            //set the numerator matrix
            num.set(0,0, N/var); num.set(0,1, T*dt1/(var*magd));
            num.set(1,0, T*dt1/(var*magd)); num.set(1,1, Math.expm1(T*T*dtd/var)/dtd);

            //set the numerator matrix
            den.set(0,0, sts/var); den.set(0,1, st1/var); den.set(0,2, T*dts/(var*magd));
            den.set(1,0, st1/var); den.set(1,1, N/var); den.set(1,2, T*dt1/(var*magd));
            den.set(2,1, T*dts/(var*magd)); den.set(2,1, T*dt1/(var*magd)); den.set(2,2, Math.expm1(T*T*dtd/var)/dtd);
            
            double bound = VectorFunctions.stableDet(num)/VectorFunctions.stableDet(den);
            if(bound > B && Math.abs(VectorFunctions.sum(delta)) > 0.5){
                B = bound;
            }
        }
        
        return B;
    }

}
