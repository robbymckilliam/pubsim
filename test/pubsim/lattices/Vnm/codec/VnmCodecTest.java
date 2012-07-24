/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.Vnm.codec;

import Jama.Matrix;
import org.junit.*;
import static org.junit.Assert.*;
import pubsim.VectorFunctions;
import pubsim.lattices.GeneralLattice;
import pubsim.lattices.Vnm.Vnm;

/**
 *
 * @author Robby McKilliam
 */
public class VnmCodecTest {
    
    public VnmCodecTest() {
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

//     /**
//     * Compute augmented lattice.
//     */
//    @Test
//    public void computeAugmentedLattice() {
//        System.out.println("Compute augmented lattice");
//        int m = 2;
//        int n = 12;
//        int k = 10;
//        Matrix VnmMat = new Vnm(n, m).getGeneratorMatrix();
//            Matrix M = Matrix.identity(n+m+1, n+m+1).times(k);
//            for(int i = 0; i < n+m+1; i++)
//                for(int j = 0; j < n; j++)
//                    M.set(i,j, VnmMat.get(i,j));
//            //M.set(n+m+1,n-1,5); //M.set(n+m+1,n-2,7);         
//            System.out.println(VectorFunctions.print(M));
//            GeneralLattice lattice = new GeneralLattice(M);
//            System.out.println(lattice.norm());
//            System.out.println(lattice.kissingNumber());
//    }
//    
     /**
     * Test shaping loss.
     */
    @Test
    public void testShapingLoss() {
        System.out.println("test Shaping loss");
        int m = 2;
        int n = 10;
        System.out.println(VnmCodec.shapingLoss(n, m));
    }
    
    /**
     * Test shaping loss.
     */
    @Test
    public void testR() {
        System.out.println("test R matrix");
        int n = 100;
        int m = 2;
        Vnm lattice = new Vnm(n,m);
        Matrix Rtest = lattice.getGeneratorMatrix().qr().getR();
        Matrix R = VnmCodec.getR(n, m);
        //System.out.println(VectorFunctions.print(R.getMatrix(0, n-1, 0, n-1)));
        //System.out.println(VectorFunctions.print(Rtest.getMatrix(0, n-1, 0, n-1)));
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                R.set(i,j, Math.abs(R.get(i,j)));
                Rtest.set(i,j, Math.abs(Rtest.get(i,j)));
            }
        }
        assertTrue(Rtest.getMatrix(0, n-1, 0, n-1).minus(R.getMatrix(0, n-1, 0, n-1)).normF() < 0.01);
    }
    
}
