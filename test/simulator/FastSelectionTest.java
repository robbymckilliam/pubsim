/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator;

import java.util.Collection;
import java.util.Random;
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import simulator.FastSelection.ArrayBackedCollection;
import simulator.FastSelection.ArrayBackedCollection.ArrayBackedCollectionIterator;
import static org.junit.Assert.*;

/**
 *
 * @author harprobey
 */
public class FastSelectionTest {

    int maxsize = 2000;
    int k;
    Double elem;
    FastSelection instance = new FastSelection(maxsize);
    
    public FastSelectionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        int length = 1500;
        
        double[] A = VectorFunctions.random(length);
        Vector<Double> Ac = new Vector<Double>();
        for(int i = 0; i < A.length; i++)
            Ac.add(A[i]);
          
        Random r = new Random();
        k = r.nextInt(A.length);
        elem = (Double) instance.select(k, Ac);
        
        System.out.println("k = " + k + ", elem = " + elem);
        
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of select method, of class FastSelection.
     */
    @Test
    public void select() {
        System.out.println("select");
        
    }
    
    
        /**
     * Test of beforeAndIncluding method, of class FastSelection.
     */
    @Test
    public void before() {
        System.out.println("before");
        java.util.Iterator itr = instance.before().iterator();
        while(itr.hasNext())
            assertTrue(elem.compareTo((Double)itr.next()) >= 0);
         
    }
    
     /**
     * Test of beforeAndIncluding method, of class FastSelection.
     */
    @Test
    public void smallest() {
        System.out.println("smallest");
        java.util.Iterator itr = instance.smallest().iterator();
        while(itr.hasNext())
            assertTrue(elem.compareTo((Double)itr.next()) >= 0);
                
    }

    /**
     * Test of after method, of class FastSelection.
     */
    @Test
    public void after() {
        System.out.println("after");
        java.util.Iterator itr = instance.after().iterator();
        while(itr.hasNext())
            assertTrue(elem.compareTo((Double)itr.next()) <= 0);
    }
    

    /**
     * Test of FloydRivestSelect method, of class FastSelection.
     */
    @Test
    public void FloydRivestSelect() {
        System.out.println("FloydRivestSelect");
        
        int iters = 100;
        Random r = new Random();
        
        for(int j = 1; j < iters; j++){
        
            double[] A = VectorFunctions.random(iters*10);
            int L = 0;
            int R = A.length - 1;
            int K = r.nextInt(A.length);
            double result = FastSelection.FloydRivestSelect(L, R, K, A);
            //assertEquals(expResult, result);
            for(int i = 0; i<K; i++){
                assertTrue(A[i] <= A[K]);
            }
            for(int i = K+1; i<A.length; i++){
                assertTrue(A[i] >= A[K]);
            }
            
        }
        
        for(int j = 1; j < iters; j++){
        
            double[] A = VectorFunctions.random(iters*10);
            Double[] Ac = new Double[A.length];
            for(int i = 0; i < A.length; i++)
                Ac[i] = new Double(A[i]);
                
            int L = 0;
            int R = A.length - 1;
           int K = r.nextInt(A.length);
            FastSelection.FloydRivestSelect(L, R, K, Ac);
            //assertEquals(expResult, result);
            for(int i = 0; i<K; i++){
                assertTrue(Ac[i].compareTo(Ac[K]) <= 0);
            }
            for(int i = K+1; i<A.length; i++){
                assertTrue(Ac[i].compareTo(Ac[K]) >= 0);
            }
            
        }

   }
    
    @Test
    public void ArrayBackedCollectionAndIterator() {
        System.out.println("ArrayBackedCollectionAndIterator");
        
        Double[] d = new Double[10];
        for(int i = 0; i < d.length; i++)
            d[i] = new Double(i);
        
       ArrayBackedCollection inst = new ArrayBackedCollection(d);
       ArrayBackedCollectionIterator itr = inst.iterator();
       int i = 0;
       while(itr.hasNext()){
           //System.out.println(d[i]);
           assertTrue(d[i] == ((Double)itr.next()).doubleValue());
           i++;
       }
       
       inst.start(2);
       inst.end(5);
       i = 2;
       itr = inst.iterator();
       while(itr.hasNext()){
           //System.out.println(d[i]);
           assertTrue(d[i] ==  ((Double)itr.next()).doubleValue());
           i++;
       }
       
       
    }
    

}