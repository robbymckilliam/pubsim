/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.nearset;

import lattices.Anstar.Anstar;
import lattices.Anstar.AnstarBucketVaughan;
import lattices.Anstar.AnstarNew;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static simulator.VectorFunctions.randomGaussian;
import static simulator.VectorFunctions.distance_between;
import static simulator.VectorFunctions.print;
import static simulator.VectorFunctions.ones;
import static simulator.Range.range;
import static org.junit.Assert.*;

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
    public void compute(){
        System.out.println("compute");
        int N = 10;
        double[] m = randomGaussian(N);
        double[] c = new double[N];

        NearestInZnToLine inst = new NearestInZnToLine(N);
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

        System.out.println("ones = " + print(ones));

        NearestInZnToLine inst = new NearestInZnToLine(N);
        AnstarNew anstar = new AnstarNew(N-1);

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