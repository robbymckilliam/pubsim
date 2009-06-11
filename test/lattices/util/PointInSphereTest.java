/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.util;

import Jama.Matrix;
import lattices.Anstar;
import lattices.AnstarBucket;
import lattices.GeneralLattice;
import lattices.Zn;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simulator.VectorFunctions;
import static org.junit.Assert.*;

/**
 *
 * @author harprobey
 */
public class PointInSphereTest {

    public PointInSphereTest() {
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
     * Test of nextElementDouble method, of class PointInSphere.
     */
    @Test
    public void testNextElementZn() {
        System.out.println("testNextElementZn");
        int N = 2;
        double radius = 2.0;
        PointInSphere instance = new PointInSphere(new Zn(N), radius);
        while(instance.hasMoreElements()){
            double[] p = instance.nextElementDouble();
            System.out.println(VectorFunctions.print(p));
            assertTrue(VectorFunctions.magnitude(p) <= radius);
        }

    }

    /**
     * Test of nextElementDouble method, of class PointInSphere.
     */
    @Test
    public void testNextElementB() {
        System.out.println("testNextElementB");
        Matrix B = new Matrix(2,2);
        B.set(0,0, 1); B.set(0,1, 0.2);
        B.set(1, 0, 0.2); B.set(1,1, 1);
        double radius = 100.0;

        for(radius = 2.0; radius < 100.0; radius += 3.0){
            System.out.println(radius);
            PointInSphere instance = new PointInSphere(new GeneralLattice(B), radius);
            while(instance.hasMoreElements()){
                double[] p = instance.nextElementDouble();
                //System.out.println(VectorFunctions.print(p));
                assertTrue(VectorFunctions.magnitude(p) <= radius);
            }

        }

    }

        /**
     * Test of nextElementDouble method, of class PointInSphere.
     */
    @Test
    public void testNextElementBnotcentre() {
        System.out.println("testNextElementB");
        Matrix B = new Matrix(2,2);
        B.set(0,0, 1); B.set(0,1, 0.2);
        B.set(1, 0, 0.2); B.set(1,1, 1);
        double radius = 100.0;

        for(radius = 2.0; radius < 100.0; radius += 3.0){
            System.out.println(radius);
            double[] c = VectorFunctions.randomGaussian(2, 0, 1000);
            PointInSphere instance = new PointInSphere(new GeneralLattice(B), radius, c);
            while(instance.hasMoreElements()){
                double[] p = instance.nextElementDouble();
                //System.out.println(VectorFunctions.print(p));
                assertTrue(VectorFunctions.distance_between(p, c) <= radius);
            }

        }

    }

    /**
     * Test of nextElementDouble method, of class PointInSphere.
     */
    @Test
    public void testNextElementAnStar() {
        System.out.println("testNextElementAnstar");
        int N = 10;

        for(double radius = 1.0; radius < 5.0; radius += 1.0){
            System.out.println(radius);
            PointInSphere instance = new PointInSphere(new AnstarBucket(N), radius);
            while(instance.hasMoreElements()){
                double[] p = instance.nextElementDouble();
                //System.out.println(VectorFunctions.print(p));
                assertTrue(VectorFunctions.magnitude(p) <= radius + 0.00001);
            }

        }

    }

}