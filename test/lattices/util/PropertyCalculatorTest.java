/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.util;

import lattices.AnFastSelect;
import lattices.Dn;
import lattices.Zn;
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
public class PropertyCalculatorTest {

    public PropertyCalculatorTest() {
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

    int samples = 50;

    /**
     * Test of properties for Zn lattice
     */
    @Test
    public void testZn() {
        int N = 3;
        PropertyCalculator prop = new PropertyCalculator(new Zn(N), samples);
        assertEquals(N*0.5*0.5, prop.outRadius()*prop.outRadius(), 0.0001);
        assertEquals(1.0/12.0, prop.dimensionalessSecondMoment(), 0.001);

    }

    /**
     * Test of properties for Zn lattice
     */
    @Test
    public void testAn() {
        int N =4;
        PropertyCalculator prop = new PropertyCalculator(new AnFastSelect(N), samples);
        //assertEquals(prop.outRadius()*prop.outRadius(), 5*0.5*0.5, 0.00001);

        double I =  N/12.0 + N/(6.0*(N+1));
        assertEquals(I, prop.normalisedSecondMoment(), 0.001);

        double G = I/Math.pow(N+1, 1.0/N)/N;
        assertEquals(G, prop.dimensionalessSecondMoment(), 0.001);

    }

        /**
     * Test of properties for Zn lattice
     */
    @Test
    public void testDn() {
        int N = 4;
        PropertyCalculator prop = new PropertyCalculator(new Dn(N), samples);
        //assertEquals(prop.outRadius()*prop.outRadius(), 5*0.5*0.5, 0.00001);

        double I =  N/12.0 + 1/(2.0*(N+1));
        assertEquals(I, prop.normalisedSecondMoment(), 0.001);

        double G = I/Math.pow(2, 2.0/N)/N;
        assertEquals(G, prop.dimensionalessSecondMoment(), 0.001);

    }

}