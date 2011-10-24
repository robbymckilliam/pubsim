/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.decoder.firsttype;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pubsim.lattices.An.AnFastSelect;
import pubsim.lattices.Anstar.Anstar;
import pubsim.lattices.Anstar.AnstarBucket;
import pubsim.lattices.Anstar.AnstarBucketVaughan;
import pubsim.lattices.Vn2Star.Vn2Star;
import pubsim.lattices.Vn2Star.Vn2StarGlued;
import pubsim.lattices.leech.Leech;
import static org.junit.Assert.*;

/**
 *
 * @author Robby McKilliam
 */
public class MinCutFirstTypeTest {
    
    public MinCutFirstTypeTest() {
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
     * Test of constructor correctly detects and computes obtuse bases.
     */
    @Test
    public void testDetectObtuseBasis() {
        System.out.println("Detect Obtuse Basis");
        
        new MinCutFirstType(new AnstarBucketVaughan(10));
        
        new MinCutFirstType(new AnFastSelect(10));
        
        boolean exc = false;
        try{ 
            new MinCutFirstType(new Vn2StarGlued(20));
        }catch(RuntimeException e){
          exc = true;  
        }
        assertTrue(exc);
        
        exc = false;
        try{ 
            new MinCutFirstType(new Leech());
        }catch(RuntimeException e){
          exc = true;  
        }
        assertTrue(exc);
        
    }
    
}
