/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.decoder.banded;

import Jama.Matrix;
import lattices.GeneralLattice;
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
public class ConstrainedSphereDecoderTest {

    public ConstrainedSphereDecoderTest() {
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

    @Test
    public void returnsErrorWhenDimensionAreWrong() {
        System.out.println("returnsErrorWhenDimensionAreWrong");
        double[] y = {1, 2, 3, 4};
        GeneralLattice lattice = new GeneralLattice(Matrix.random(6, 5));
        Double[] c = new Double[5];
        ConstrainedSphereDecoder decoder = 
                new ConstrainedSphereDecoder(lattice, c, 1.0);

        boolean caught = false;
        try{
            decoder.nearestPoint(y);
        } catch(RuntimeException e){
            caught = true;
        }

        assertTrue(caught);

        lattice = new GeneralLattice(Matrix.random(6, 4));
        c = new Double[5];

        caught = false;
        try{
            decoder = new ConstrainedSphereDecoder(lattice, c, 1.0);
        } catch(RuntimeException e){
            caught = true;
        }

        assertTrue(caught);

    }

    

}