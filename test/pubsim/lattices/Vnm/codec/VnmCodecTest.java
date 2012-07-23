/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.Vnm.codec;

import Jama.Matrix;
import org.junit.*;
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

     /**
     * Compute augmented lattice.
     */
    @Test
    public void computeAugmentedLattice() {
        System.out.println("Compute augmented lattice");
        int m = 1;
        int n = 60;
        int k = 10;
        Matrix VnmMat = new Vnm(m,n).getGeneratorMatrix();
            Matrix M = Matrix.identity(n+m+1, n+m+1).times(k);
            for(int i = 0; i < n+m+1; i++)
                for(int j = 0; j < n; j++)
                    M.set(i,j, VnmMat.get(i,j));
            //M.set(n+m+1,n-1,5); //M.set(n+m+1,n-2,7);         
            System.out.println(VectorFunctions.print(M));
            GeneralLattice lattice = new GeneralLattice(M);
            System.out.println(lattice.norm());
            System.out.println(lattice.kissingNumber());
    }
}
