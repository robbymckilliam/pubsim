/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.nearset;

import pubsim.lattices.Anstar.Anstar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static pubsim.VectorFunctions.randomGaussian;
import static pubsim.VectorFunctions.columnMatrix;
import static pubsim.VectorFunctions.print;
import static pubsim.VectorFunctions.ones;
import static pubsim.Range.range;
import static org.junit.Assert.*;
import pubsim.lattices.Anstar.AnstarLinear;

/**
 *
 * @author Robby McKilliam
 */
public class NearestInZnToLineTest {

    public NearestInZnToLineTest() {
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
    public void zeroForLineThroughOrigin(){
        System.out.println("zeroForLineThroughOrigin");
        int N = 10;
        double[] m = randomGaussian(N);
        double[] c = new double[N];
        RegionForLines R = new NSphereForLines(1, c);

        NearestInZnToLine inst = new NearestInZnToLine(columnMatrix(m), R);
        inst.compute(c, m, -10.0, 10.0);

        double[] u = inst.nearestPoint();
        double r = inst.nearestParam();
        assertEquals(r, 0.0, 0.00000001);
        for(int n = 0; n < N; n++){
            assertEquals(u[n], 0.0, 0.0000001);
        }

    }

    @Test
    public void sameAsAnstar(){
        System.out.println("sameAsAnstar");
        int N = 10;

        double[] ones = ones(N);
        RegionForLines R = new NSphereForLines(1, N);

        System.out.println("ones = " + print(ones));

        NearestInZnToLine inst = new NearestInZnToLine(columnMatrix(ones), R);
        AnstarLinear anstar = new AnstarLinear(N-1);

        for(int k : range(10)){

            double[] y = randomGaussian(N);
            inst.compute(y, ones, 0.0, 1.0);

            double[] u = inst.nearestPoint();
            anstar.nearestPoint(y);
            Anstar.project(u, u);

            //System.out.println("inst = " + distance_between(u, y));
            //System.out.println("anst = " + distance_between(anstar.getLatticePoint(), y));

            for(int n = 0; n < N; n++){
                assertEquals(u[n], anstar.getLatticePoint()[n], 0.00000001);
            }
        }

    }

}