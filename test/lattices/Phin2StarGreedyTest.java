/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simulator.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author Robby
 */
public class Phin2StarGreedyTest {

    public Phin2StarGreedyTest() {
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
     * Test of nearestPoint method, of class Phin2StarGreedy.
     */
    @Test
    public void nearestPoint() {
        System.out.println("nearestPoint");
        int n = 10;
        Random rand = new Random();
        
        double[] y = new double[n];
        Phin2StarSampled instance = new Phin2StarSampled(1000);
        Phin2StarGlued tester = new Phin2StarGlued();
        
        for(int i = 0; i < 100; i++){
            for(int j=0; j<n; j++)
                y[j] = 10 * rand.nextGaussian();
            
            Phin2StarZnLLS.project(y,y);
            
            instance.nearestPoint(y);
            tester.nearestPoint(y);
            
            double dist = VectorFunctions.distance_between(instance.getLatticePoint(), tester.getLatticePoint());
            System.out.println("inst = " + VectorFunctions.distance_between(instance.getLatticePoint(), y));
            System.out.println("test = " + VectorFunctions.distance_between(tester.getLatticePoint(), y));
            assertEquals(dist < 0.0001, true);
            
        }
    }

}