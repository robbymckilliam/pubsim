package pubsim.distributions;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robby McKilliam
 */
public class LevyTest {
    
    public LevyTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of pdf method, of class Levy.
     */
    @Test
    public void testPdf() {
        System.out.println("check against mathematica output");
        
        double c1 = 1;
        double[] xs1 = {0.01, 1.01, 2.01, 3.01, 4.01, 5.01, 6.01, 7.01, 8.01, 9.01};
        double[] res1 = {0.0, 0.239569, 0.109165, 0.0647021, 0.0438573, 0.0321967, 0.0249154, 0.020015, 0.016533, 0.0139548};
        Levy levy1 = new Levy(c1);
        for(int i = 0; i < xs1.length; i++) assertEquals(levy1.pdf(xs1[i]), res1[i], 1e-6); 
        
        double mu2 = 1, c2 = 1;
        double[] xs2 = {1.01, 2.01, 3.01, 4.01, 5.01, 6.01, 7.01, 8.01, 9.01};
        double[] res2 = {0.0, 0.239569, 0.109165, 0.0647021, 0.0438573, 0.0321967, 0.0249154, 0.020015, 0.016533};
        Levy levy2 = new Levy(mu2,c2);
        for(int i = 0; i < xs2.length; i++) assertEquals(levy2.pdf(xs2[i]), res2[i], 1e-6); 
        
        double c3 = 3;
        double[] xs3 = {0.01, 1.01, 2.01, 3.01, 4.01, 5.01, 6.01, 7.01, 8.01, 9.01};
        double[] res3 = {0.0, 0.154169, 0.114968, 0.0803887, 0.059197, 0.0456758, 0.0365398, 0.0300582, 0.0252751, 0.0216312};
        Levy levy3 = new Levy(c3);
        for(int i = 0; i < xs3.length; i++) assertEquals(levy3.pdf(xs3[i]), res3[i], 1e-6); 
        
        double c4 = 0.1;
        double[] xs4 = {0.001, 1.001, 2.001, 3.001, 4.001, 5.001, 6.001, 7.001, 8.001, 9.001};
        double[] res4 = {0.0, 0.11983, 0.0434698, 0.0238658, 0.0155679, 0.0111682, 0.00851052, 0.00676189, 0.00553962, 0.00464581};
        Levy levy4 = new Levy(c4);
        for(int i = 0; i < xs4.length; i++) assertEquals(levy4.pdf(xs4[i]), res4[i], 1e-6); 
        assertEquals(levy4.pdf(0.01), 0.850037, 1e-6);
        
    }

    /**
     * Test of cdf method, of class Levy.
     */
    @Test
    public void testCdf() {
        System.out.println("check against mathematica output");
        
        double c1 = 1;
        double[] xs1 = {0.001, 1.001, 2.001, 3.001, 4.001, 5.001, 6.001, 7.001, 8.001, 9.001};
        double[] res1 = {0.0, 0.317552, 0.47961, 0.563768, 0.617119, 0.654753, 0.683116, 0.705477, 0.72369, 0.738897};
        Levy levy1 = new Levy(c1);
        for(int i = 0; i < xs1.length; i++) assertEquals(levy1.cdf(xs1[i]), res1[i], 1e-6); 
        
        double c4 = 0.1;
        double[] xs4 = {0.001, 1.001, 2.001, 3.001, 4.001, 5.001, 6.001, 7.001, 8.001, 9.001};
        double[] res4 = {0.0, 0.75195, 0.823107, 0.855156, 0.874383, 0.887548, 0.897287, 0.904868, 0.910985, 0.916056};
        Levy levy4 = new Levy(c4);
        for(int i = 0; i < xs4.length; i++) assertEquals(levy4.cdf(xs4[i]), res4[i], 1e-6); 
        assertEquals(levy4.pdf(0.01), 0.850037, 1e-6);
        
    }
    
    @Test
    public void testNoise() {
        System.out.println("generating some instances of Levy noise");
        Levy d = new Levy(1.0);
        for(int i = 0; i < 10; i++) System.out.println(d.noise());
    }
    
}
