/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices;

import Jama.Matrix;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author harprobey
 */
public class BarnesWallTest {
    
    public BarnesWallTest() {
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
     * Test of coveringRadius method, of class BarnesWall.
     */
    @Test
    public void printNominalGain() {
        System.out.println("printNominalGain");
        for(int m = 1; m < 9; m++){
            BarnesWall lattice = new BarnesWall(m);
            System.out.println(lattice.getDimension() + ", " + lattice.nominalCodingGain());
        }
    }
    
}
