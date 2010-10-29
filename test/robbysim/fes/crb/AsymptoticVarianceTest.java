/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.fes.crb;

import robbysim.fes.crb.AsymptoticVariance;
import java.util.Vector;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author harprobey
 */
public class AsymptoticVarianceTest {

    public AsymptoticVarianceTest() {
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
     * Test of getBound method, of class AsymptoticVariance.
     */
    @Test
    public void testGetBound() {
        int n = 16;

        double from_log_snr = 14;
        double to_log_snr = -20.0;
        double step_log_snr = -2;

        Vector<Double> snr_array = new Vector<Double>();
        Vector<Double> snr_db_array = new Vector<Double>();
        for(double snrdb = from_log_snr; snrdb >= to_log_snr; snrdb += step_log_snr){
            snr_db_array.add(new Double(snrdb));
            snr_array.add(new Double(Math.pow(10.0, ((snrdb)/10.0))));
        }

        AsymptoticVariance asympcal = new AsymptoticVariance();
        asympcal.setN(n);

        for( Double snr : snr_array ){
            double var = 1.0/(2.0*snr);
            asympcal.setVariance(var);
            System.out.println(10.0*Math.log10(snr) + "\t" + asympcal.getBound());
        }

    }

}