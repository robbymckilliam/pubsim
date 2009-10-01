/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.Anm;

import Jama.Matrix;
import junit.framework.TestCase;
import lattices.Lattice;
import simulator.VectorFunctions;

/**
 *
 * @author Robby
 */
public class AnmTest extends TestCase {
    
    public AnmTest(String testName) {
        super(testName);
    }            

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
        /**
     * Test of nearestPoint method, of class Anm.
     */
    public void testGeneratorMatrix() {
        System.out.println("testGeneratorMatrix");
        int M = 3;
        int n = 8;
        Lattice anm = new AnmSorted(M);
        anm.setDimension(n);

        Matrix Mat = anm.getGeneratorMatrix();
        System.out.println(VectorFunctions.print(Mat));

        double det = Mat.transpose().times(Mat).det();
        System.out.println(det);
        System.out.println(anm.volume());

    }

}
