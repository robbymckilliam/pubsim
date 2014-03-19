/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.phaseunwrapping.twod;

import pubsim.phaseunwrapping.twod.ZerothOrderUnwrapper;
import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Robby
 */
public class ZerothOrderUnwrapperTest {

    public ZerothOrderUnwrapperTest() {
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
     * Test of setSize method, of class ZerothOrderUnwrapper.
     */
    @Test
    public void testSetSize() {
        System.out.println("setSize");
        int M = 5;
        int N = 5;
        ZerothOrderUnwrapper instance = new ZerothOrderUnwrapper();
        instance.setSize(M, N);
    }


}