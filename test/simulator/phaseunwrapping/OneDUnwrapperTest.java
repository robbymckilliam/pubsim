/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.phaseunwrapping;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author robertm
 */
public class OneDUnwrapperTest {

    public OneDUnwrapperTest() {
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
     * Test of setSize method, of class OneDUnwrapper.
     */
    @Test
    public void testSetSize() {
        System.out.println("setSize");
        int N = 7;
        OneDUnwrapper instance = new OneDUnwrapper(2, 3);
        instance.setSize(N);
        // TODO review the generated test code and remove the default call to fail.
    }

}