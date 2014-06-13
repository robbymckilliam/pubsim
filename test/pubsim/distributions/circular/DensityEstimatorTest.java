package pubsim.distributions.circular;

import static org.junit.Assert.assertEquals;
import org.junit.*;
import pubsim.distributions.RealRandomVariable;
import pubsim.distributions.Uniform;

/**
 *
 * @author Robby McKilliam
 */
public class DensityEstimatorTest {

    public DensityEstimatorTest() {
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
     * Test of pdf method, of class DensityEstimator.
     */
    @Test
    public void testPdf() {
        System.out.println("pdf");
        Double[] data = {0.4, -0.4};
        RealRandomVariable ker = Uniform.constructFromMeanAndRange(0, 0.3);
        DensityEstimator dest = new DensityEstimator(data, ker);
        assertEquals(10.0/3.0, dest.pdf(0.5), 0.0000001);
        assertEquals(5.0/3.0, dest.pdf(0.39), 0.0000001);
        assertEquals(5.0/3.0, dest.pdf(-0.39), 0.0000001);
        assertEquals(0.0, dest.pdf(0.0), 0.0000001);
    }
    
    
    /**
     * Test of pdf method, of class DensityEstimator.
     */
    @Test
    public void testWithWrappedGaussian() {
        System.out.println("test with wrapped gaussian");
        
        WrappedGaussian instance = new WrappedGaussian(0,0.05);        
        double eres = instance.pdf(-0.5);
        
        int N = 100000;
        Double[] X = new Double[N];
        for(int i = 0; i < N; i++) X[i] = instance.noise();
        
        DensityEstimator dest = new DensityEstimator(X, new WrappedUniform(0,1.0/10000));
        double res = dest.pdf(-0.5);
                
        System.out.println(res + ", " + eres);
        
    }

}