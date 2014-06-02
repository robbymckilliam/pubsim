package pubsim;

import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Robby McKilliam
 */
public class CombinationEnumeratorTest {
    
    public CombinationEnumeratorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /** Test combinations are correct when k=1 */
    @Test
    public void testWithK1() {
        System.out.println("Test with k=1");
        Set<Integer> S = new HashSet();
        for(int i = 0; i < 5; i++) S.add(i);
        CombinationEnumerator<Integer> instance = new CombinationEnumerator(S, 1);
        long count = 0;
        for( Set<Integer> Si : instance ) {
            assertTrue(Si.size()==1);
            System.out.println(Si.iterator().next());
            count++;
        }
        assertTrue(count==pubsim.Util.binom(S.size(),1));
    }
    
    /** Test combinations are correct when k=1 */
    @Test
    public void testWithK2() {
        System.out.println("Test with k=2");
        Set<Integer> S = new HashSet();
        for(int i = 0; i < 5; i++) S.add(i);
        CombinationEnumerator<Integer> instance = new CombinationEnumerator(S, 2);
        long count = 0;
        for( Set<Integer> Si : instance ) {
            assertTrue(Si.size()==2);
            System.out.print("{ "); for( Integer x : Si ) System.out.print(x + " "); System.out.println("}");
            count++;
        }
        assertTrue(count==pubsim.Util.binom(S.size(),2));
    }
    
    /** Test combinations are correct when k=1 */
    @Test
    public void testWithK3() {
        System.out.println("Test with k=2");
        Set<Integer> S = new HashSet();
        for(int i = 0; i < 5; i++) S.add(i);
        CombinationEnumerator<Integer> instance = new CombinationEnumerator(S, 3);
        long count = 0;
        for( Set<Integer> Si : instance ) {
            assertTrue(Si.size()==3);
            System.out.print("{ "); for( Integer x : Si ) System.out.print(x + " "); System.out.println("}");
            count++;
        }
        assertTrue(count==pubsim.Util.binom(S.size(),3));
    }
    
}
