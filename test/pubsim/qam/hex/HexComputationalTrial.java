/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.hex;

import pubsim.qam.hex.BruteForceHexReciever;
import pubsim.qam.hex.HexReciever;
import pubsim.qam.hex.FadingNoisyHex;
import pubsim.distributions.GaussianNoise;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.Vector;
import static pubsim.Range.range;

/**
 *
 * @author Robby McKilliam
 */
public class HexComputationalTrial {

    /**
     * Main function for testing the practical running times
     * of nearest point algorithms.
     */
    public static void main(String[] args) throws Exception {

        System.out.println("timingTest");

        int numTrials = 1000;
        final int r = 4;
        Vector<Double> timearray = new Vector<Double>();

        int nstart = 3;
        int nend = 9;
        int nstep = 1;

        for(int n : range(nstart, nend, nstep) ){

            final HexReciever rec = new BruteForceHexReciever(n, r);
            //final HexReciever rec = new RadialLinesReciever(n, r);
            final FadingNoisyHex siggen = new FadingNoisyHex(n, r);
            siggen.setChannel(0.0,1.0);
            final GaussianNoise noise = new GaussianNoise(0.0, 0.001);
            siggen.setNoiseGenerator(noise);

            Date timer = new Date();
            long start = timer.getTime();

            for(int i=0; i<numTrials; i++){
                siggen.generateChannel();
                siggen.generateCodeword();
                siggen.generateReceivedSignal();
                rec.setChannel(siggen.getChannel());
                rec.decode(siggen.getReal(), siggen.getImag());
            }
            timer = new Date();
            long end = timer.getTime();
            double runtime = (end - start)/1000.0;
            System.out.println("n = " + n + " time = " + runtime);
            timearray.add(runtime);
            //assertTrue(true);
        }

        File file = new File( "AnmLinearm1" );
        BufferedWriter writer =  new BufferedWriter(new FileWriter(file));
        int count = 0;
        for(int n : range(nstart, nend, nstep)){
            writer.write(
                    n
                    + "\t" + timearray.get(count).toString().replace('E', 'e'));
            writer.newLine();
            count++;
        }
        writer.close();

    }

}
