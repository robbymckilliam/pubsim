/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.lattices;

import robbysim.lattices.LatticeAndNearestPointAlgorithm;
import robbysim.distributions.GaussianNoise;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.Vector;
import robbysim.lattices.An.AnFastSelect;
import robbysim.lattices.An.AnSorted;
import robbysim.lattices.Anm.AnmGlued;
import robbysim.lattices.Anm.AnmLinear;
import robbysim.lattices.Anm.AnmSorted;
import robbysim.lattices.Anstar.AnstarBucketVaughan;
import robbysim.lattices.Anstar.AnstarNew;
import static robbysim.Range.range;

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

        int numTrials = 20000;
        GaussianNoise rand = new GaussianNoise(0.0, 1000.0);
        Vector<Double> timearray = new Vector<Double>();

        int nstart = 4;
        int nend = 250;
        int nstep = 16;

        LatticeAndNearestPointAlgorithm lattice = null;

        for(int n : range(nstart, nend, nstep) ){

            int M = 4;
            double[] y = new double[n];

            //lattice = new AnSorted(n-1);
            //lattice = new AnFastSelect(n-1);
            //lattice = new AnstarNew();
            //lattice = new AnstarBucketVaughan();
            //lattice = new AnmLinear(M);
            lattice = new AnmGlued(M);
            //lattice = new AnmSorted(M);
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

        String fname = (lattice.getClass().toString() + "_m4").replace(' ', '.');
        File file = new File( fname );
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
