/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.fes.crb;

import Jama.Matrix;
import flanagan.integration.IntegralFunction;
import flanagan.integration.Integration;
import robbysim.VectorFunctions;

/**
 * Barankin-type bound motivated by 
 * "A Lower Bound on the Mean-Square Error in Random Parameter Estimation"
 * by Weiss and Weinstein 
 * @author Robby McKilliam
 */
public class Weinstein extends Barankin implements BoundCalculator{
    
    
    @Override
    public double getBound() {
        
        double v = amplitude/Math.sqrt(var);
        Matrix J = new Matrix(testPoints.size() + 2, testPoints.size() + 2);
        
        dPOverP func = new dPOverP();
        func.setv(v);        
        Integration intg = new Integration(func, -0.5, 0.5);
        double Bint = intg.gaussQuad(INTERGRATION_SAMPLES);
        
        /** Set the CRB part of the matrix */
        J.set(0, 0, ntn * Bint); J.set(0, 1, nt1 * Bint);
        J.set(1, 0, nt1 * Bint); J.set(1, 1, N * Bint);
        
        //now regular/Barankin parts
        dPPptPmtOverSqrtP funcRB = new dPPptPmtOverSqrtP();
        funcRB.setv(v);
        intg.setIntegrationFunction(funcRB);
        for(int i = 2; i < J.getRowDimension(); i++){
            for(int j = 0; j < 2; j++){
                double sum = 0.0;
                for(int n = 1; n <= N; n++){
                    double t = testPoints.get(i-2).f * n + testPoints.get(i-2).t;              
                    funcRB.setTranslation(t);
                    sum += Math.pow(n, 1-j) * intg.gaussQuad(INTERGRATION_SAMPLES);
                }
                if(1-j == 0) sum = 0;
                J.set(i, j, -sum);
                J.set(j, i, -sum);
            }
        }
        
        //compute the Barankin parts
        PiPj funcB = new PiPj();
        funcB.setv(v);
        intg.setIntegrationFunction(funcB);
        for(int i = 2; i < J.getRowDimension(); i++){
            for(int j = i; j < J.getColumnDimension() ; j++){
                double prod1 = 1.0, prod2 = 1.0, prod3 = 1.0, prod4 = 1.0;
                for(int n = 1; n <= N; n++){
                    double t1 = testPoints.get(i-2).f * n + testPoints.get(i-2).t;
                    double t2 = testPoints.get(j-2).f * n + testPoints.get(j-2).t;                 
                    funcB.setTranslations(t1, t2);
                    prod1 *= intg.gaussQuad(INTERGRATION_SAMPLES);
                    funcB.setTranslations(-t1, t2);
                    prod2 *= intg.gaussQuad(INTERGRATION_SAMPLES);
                    funcB.setTranslations(t1, -t2);
                    prod3 *= intg.gaussQuad(INTERGRATION_SAMPLES);
                    funcB.setTranslations(-t1, -t2);
                    prod4 *= intg.gaussQuad(INTERGRATION_SAMPLES);
                    
                }
                double eval = prod1 - prod2 - prod3 + prod4;
                if(i != j) eval = 0;
                J.set(i, j, eval);
                J.set(j, i, eval);
            }
        }
        
        System.out.println(VectorFunctions.print(J));
        
        Matrix Jnum = J.getMatrix(1, J.getRowDimension()-1, 1, J.getColumnDimension()-1);
        
        return Jnum.det()/J.det();
    }
    
    protected class PiPj extends PtPtOverP implements IntegralFunction {
        
        @Override
         public double function(double x) {
            
            double ft1 = Math.sqrt(robbysim.distributions.circular.ProjectedNormalDistribution.Pdf(x + t1, v));
            double ft2 = Math.sqrt(robbysim.distributions.circular.ProjectedNormalDistribution.Pdf(x + t2, v));
            
            return ft1*ft2;
            
        }
        
    }
        
    protected class dPPptPmtOverSqrtP extends dPOverP implements IntegralFunction {
        
        protected double t;
        
        public void setTranslation(double t){
            this.t = t;
        }
        
        @Override
         public double function(double x) {
            
            double fd = robbysim.distributions.circular.ProjectedNormalDistribution.dPdf(x, v);
            double ft = Math.sqrt(robbysim.distributions.circular.ProjectedNormalDistribution.Pdf(x + t, v))
                    - Math.sqrt(robbysim.distributions.circular.ProjectedNormalDistribution.Pdf(x - t, v));
            double f = Math.sqrt(robbysim.distributions.circular.ProjectedNormalDistribution.Pdf(x, v));
            
            return fd*ft/f;
            
        }
        
    }

}
