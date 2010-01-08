/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.Anm;

import Jama.Matrix;
import junit.framework.TestCase;
import lattices.GeneralLatticeAndNearestPointAlgorithm;
import lattices.Lattice;
import lattices.LatticeAndNearestPointAlgorithm;
import simulator.VectorFunctions;
import simulator.VectorFunctionsTest;
import static org.junit.Assert.*;

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
        int M = 2;
        int n = 13;
        LatticeAndNearestPointAlgorithm anm = new AnmSorted(M);
        anm.setDimension(n);

        Matrix Mat = anm.getGeneratorMatrix();
        System.out.println(VectorFunctions.print(Mat));

        double det = Mat.transpose().times(Mat).det();
        System.out.println(Math.sqrt(det));
        System.out.println(anm.volume());


        double[] r = VectorFunctions.randomGaussian(n+1, 100, 1000);
        GeneralLatticeAndNearestPointAlgorithm sd
                = new GeneralLatticeAndNearestPointAlgorithm(Mat);

        sd.nearestPoint(r);
        anm.nearestPoint(r);

        VectorFunctionsTest.assertVectorsEqual(sd.getLatticePoint(), anm.getLatticePoint());
        
//        System.out.println(VectorFunctions.print(sd.getLatticePoint()));
//        System.out.println(VectorFunctions.print(anm.getLatticePoint()));
//        System.out.println(VectorFunctions.print(r));


    }

}
