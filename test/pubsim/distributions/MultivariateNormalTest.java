/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.distributions;

import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.VectorFunctions;
import pubsim.optimisation.AutoIntegralFunction;
import static org.junit.Assert.*;

/**
 *
 * @author Robby McKilliam
 */
public class MultivariateNormalTest {
    
    public MultivariateNormalTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of pdf method, of class MultivariateNormal.
     */
    @Test
    public void testPdf() {
        System.out.println("pdf");

        final Matrix mean = Matrix.random(2, 1);
        final Matrix covs = Matrix.random(2,2).plus(Matrix.identity(2, 2));
        final Matrix cov = covs.transpose().times(covs);
        //System.out.println(cov.eig());
        
        System.out.println(VectorFunctions.print(mean));
        System.out.println(VectorFunctions.print(cov));
        System.out.println(cov.cond());
        
        final MultivariateNormal instance = new MultivariateNormal(mean, cov);
        
        for(double x = -10; x < 10; x+=0.5){
            for(double y = -10; y < 10; y+=0.5){
                Matrix testpoint = new Matrix(2,1);
                testpoint.set(0,0,x); testpoint.set(1,0,y);
                double pdfval = instance.pdf(testpoint);
                //System.out.println(pdfval);
                assertTrue(pdfval < 1.0);
            }
        }
        
        
        //test that the pdf integrates to 1
        double[] min = {-10,-10}; double[] max = {10,10};
        double intv =  new AutoIntegralFunction(1000) {
            public double value(Matrix x) {
                return instance.pdf(x);
            }
        }.integral(min, max);
        assertEquals(1.0, intv, 0.001);
        
        //test the mean 
        intv =  new AutoIntegralFunction(1000) {
            public double value(Matrix x) {
                return x.get(0,0)*instance.pdf(x);
            }
        }.integral(min, max);
        assertEquals(mean.get(0,0), intv, 0.001);
        
        //test the variance 
        intv =  new AutoIntegralFunction(1000) {
            public double value(Matrix x) {
                double xm =x.get(0,0) - mean.get(0,0);
                return xm*xm*instance.pdf(x);
            }
        }.integral(min, max);
        assertEquals(cov.get(0,0), intv, 0.001);
        
        //test the covariance 
        intv =  new AutoIntegralFunction(1000) {
            public double value(Matrix x) {
                double xm =x.get(0,0) - mean.get(0,0);
                double ym =x.get(1,0) - mean.get(1,0);
                return xm*ym*instance.pdf(x);
            }
        }.integral(min, max);
        assertEquals(cov.get(0,1), intv, 0.001);
        
    }
}
