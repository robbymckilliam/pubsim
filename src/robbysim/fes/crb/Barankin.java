/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.fes.crb;

import Jama.Matrix;
import flanagan.integration.IntegralFunction;
import flanagan.integration.Integration;
import java.util.Vector;
import robbysim.Util;
import robbysim.VectorFunctions;

/**
 * Calculates Barankin type bounds for fes.
 * @author Robby McKilliam
 */
public class Barankin extends Hamersley implements BoundCalculator {

    protected Vector<TestPoint> testPoints;
    
    protected static int INTERGRATION_SAMPLES = 200;
    
    /** Constructor.  The amplitude defaults to 1. */
    public Barankin(){
        testPoints = new Vector<Barankin.TestPoint>();
        setAmplitude(1.0);
    }
    
    /** 
     * Add a freqency and phase value as a test point to the
     * Barankin bound.
     */
    public void addTestPoint(double f, double t){
        testPoints.add(new TestPoint(f, t));
    }

    /** 
     * Adds test points that have rational frequency.  These
     * are conjectured to be the best test points to use.
     */
    public void setNumberOfTestPoints(int t){
        testPoints.clear();
        for(int r = 2; r < t+2; r+=2){
            addTestPoint(1.0/r, -1.0/(2.0*r));
        }
        for(int r = 3; r < t+2; r+=2){
            addTestPoint(1.0/r, 0.0);
        }
    }
    
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
        dPPtOverP funcRB = new dPPtOverP();
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
                J.set(i, j, -sum);
                J.set(j, i, -sum);
            }
        }
        
        //compute the Barankin parts
        PtPtOverP funcB = new PtPtOverP();
        funcB.setv(v);
        intg.setIntegrationFunction(funcB);
        for(int i = 2; i < J.getRowDimension(); i++){
            for(int j = i; j < J.getColumnDimension() ; j++){
                double prod = 1.0;
                for(int n = 1; n <= N; n++){
                    double t1 = testPoints.get(i-2).f * n + testPoints.get(i-2).t;
                    double t2 = testPoints.get(j-2).f * n + testPoints.get(j-2).t;                 
                    funcB.setTranslations(t1, t2);
                    prod *= intg.gaussQuad(INTERGRATION_SAMPLES);
                }
                J.set(i, j, prod);
                J.set(j, i, prod);
            }
        }
        
        System.out.println(VectorFunctions.print(J));
        
        
        Matrix Jnum = J.getMatrix(1, J.getRowDimension()-1, 1, J.getColumnDimension()-1);
        
        return Jnum.det()/J.det();
    }
    
    /** Test point for the Barankin bound */
    protected class TestPoint{
        public double f, t;
        public TestPoint(double f, double t){
            this.f = f;
            this.t = t;
        }
    }
    
    protected class dPPtOverP extends dPOverP implements IntegralFunction {
        
        protected double t;
        
        public void setTranslation(double t){
            this.t = t;
        }
        
        @Override
         public double function(double x) {
            
            double fd = robbysim.distributions.circular.ProjectedNormalDistribution.dPdf(x, v);
            double ft = robbysim.distributions.circular.ProjectedNormalDistribution.Pdf(x - t, v);
            double f = robbysim.distributions.circular.ProjectedNormalDistribution.Pdf(x, v);
            
            return fd*ft/f;
            //return fd*ft;
            
        }
        
    }
    
    protected class PtPtOverP extends dPOverP implements IntegralFunction {
        
        protected double t1, t2;
        
        public void setTranslations(double t1, double t2){
            this.t1 = t1;
            this.t2 = t2;
        }
        
        @Override
         public double function(double x) {
            
            double ft1 = robbysim.distributions.circular.ProjectedNormalDistribution.Pdf(x - t1, v);
            double ft2 = robbysim.distributions.circular.ProjectedNormalDistribution.Pdf(x - t2, v);
            double f = robbysim.distributions.circular.ProjectedNormalDistribution.Pdf(x, v);
            
            return ft1*ft2/f;
            //return ft1*ft2;
            
        }
        
    }

}
