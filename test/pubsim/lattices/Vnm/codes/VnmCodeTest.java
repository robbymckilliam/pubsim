/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.Vnm.codes;

import Jama.Matrix;
import org.junit.*;
import pubsim.VectorFunctions;
import pubsim.lattices.GeneralLattice;
import pubsim.lattices.Vnm.Vnm;
import pubsim.lattices.decoder.ShortVectorSphereDecoded;

/**
 *
 * @author Robby McKilliam
 */
public class VnmCodeTest {
    
    public VnmCodeTest() {
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
        int n = 8;
        int k = 3;
        Matrix VnmMat = new Vnm(m,n).getGeneratorMatrix();
            Matrix M = Matrix.identity(n+m+2, n+m+2).times(k);
            for(int i = 0; i < n+m+1; i++)
                for(int j = 0; j < n; j++)
                    M.set(i,j, VnmMat.get(i,j));
            M.set(n+m+1,n-1,5); //M.set(n+m+1,n-2,7);         
            System.out.println(VectorFunctions.print(M));
            GeneralLattice lattice = new GeneralLattice(M);
            System.out.println(lattice.norm());
            System.out.println(lattice.kissingNumber());
    }

    
//    /**
//     * Compute minimum k for the Vnm codes.
//     */
//    @Test
//    public void computeMinimumK() {
//        System.out.println("Compute minimum k");
//        int m = 2;
//        int k = 1;
//        double norm = 2*(m+1);
//        double cnorm = 0;
//        while(cnorm < norm){
//            Matrix VnmMat = new Vnm(m,2*(m+1)).getGeneratorMatrix();
//            Matrix M = Matrix.identity(2*(m+1), 2*(m+1));
//            for(int i = 0; i < 2*(m+1); i++){
//                for(int j = 0; j < 2*(m+1); j++){
//                    M.set(i,j, VnmMat.get(i,j));
//                    if(j >= 2*(m+1)-m-1) {
//                        if(i==j) M.set(i,j, k);
//                        else M.set(i,j,0);
//                    }
//                }
//            }
//            System.out.println(VectorFunctions.print(M));
//            GeneralLattice lattice = new GeneralLattice(M);
//            ShortVectorSphereDecoded sv = new ShortVectorSphereDecoded(lattice);
//            System.out.println(VectorFunctions.print(sv.getShortestVector()));
//            System.out.println(VectorFunctions.print(sv.getShortestIndex()));
//            cnorm = VectorFunctions.sum2(sv.getShortestVector());
//            System.out.println(cnorm);
//            k++;
//        }
//        System.out.println(k-1);
//    }

}
