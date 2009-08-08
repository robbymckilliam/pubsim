/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import distributions.GaussianNoise;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.Vector;
import lattices.Anm.AnmSorted;
import static simulator.Range.range;

/**
 * Runs computational trials on lattices.
 * @author Robby McKilliam
 */
public class LatticeTimingTest {

     /**
     * Main function for testing the practical running times
     * of nearest point algorithms.
     */
    public static void main(String[] args) throws Exception {

        System.out.println("timingTest");

        int numTrials = 100000;
        GaussianNoise rand = new GaussianNoise(0.0, 1000.0);
        Vector<Double> timearray = new Vector<Double>();

        for(int n : range(4, 1024, 16) ){

            int M = n/4;
            double[] y = new double[n];

            LatticeAndNearestPointAlgorithm lattice = new AnmSorted(M);
            lattice.setDimension(n - 1);

            Date timer = new Date();
            long start = timer.getTime();

            for(int i=0; i<numTrials; i++){
                for(int k = 0; k < n; k++){
                    y[k] = rand.getNoise();
                }
                lattice.nearestPoint(y);
            }
            timer = new Date();
            long end = timer.getTime();
            double runtime = (end - start)/1000.0;
            System.out.println("n = " + n + " time = " + runtime);
            timearray.add(runtime);
            //assertTrue(true);
        }

        File file = new File( "AnmSorted" );
        BufferedWriter writer =  new BufferedWriter(new FileWriter(file));
        int count = 0;
        for(int n : range(4, 1024, 16)){
            writer.write(
                    n
                    + "\t" + timearray.get(count).toString().replace('E', 'e'));
            writer.newLine();
            count++;
        }
        writer.close();

    }

}
