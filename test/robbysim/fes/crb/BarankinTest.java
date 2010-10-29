/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.fes.crb;

import robbysim.fes.crb.Barankin;
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
public class BarankinTest {

    public BarankinTest() {
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
     * Test of getBound method, of class Barankin.
     */
    @Test
    public void getBound() {
        int N = 5;
        System.out.println("getBound");
        Barankin instance = new Barankin();
        instance.setN(N);
        instance.setAmplitude(1.0);
        instance.setVariance(1.0);
        
        instance.setNumberOfTestPoints(1);
        
        instance.getBound();
        
    }

}