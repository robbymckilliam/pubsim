/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Vector;
import static simulator.Range.*;

/**
 * Class draws a plot of the density of a lattice.
 * @author Robby McKilliam
 */
public class LatticeDensityPlots {

    /**
     * Main function for testing the practical running times
     * of nearest point algorithms.
     */
    public static void main(String[] args) throws Exception {


        int nstart = 1;
        int nend = 60;
        int nstep = 1;
        Vector<Double> density = new Vector<Double>();

        int a = 7;
        for(int n : range(nstart, nend, nstep) ){
            Lattice L = new Phina(a, n);
            //Lattice L = new Craig(n, a);
            //double d = L.logCenterDensity();
            //double d = L.inradius();
            //double d = L.logPackingDensity();
            double d = L.kissingNumber();
            density.add(d);
            System.out.println(n + ", " + d);
        }

        File file = new File( "Craig" + a + "_cdensity" );
        BufferedWriter writer =  new BufferedWriter(new FileWriter(file));
        int count = 0;
        for(int n : range(nstart, nend, nstep)){
            writer.write(n + "\t"
                    + density.get(count).toString().replace('E', 'e'));
            writer.newLine(  );
            count++;
        }
        writer.close();

    }


}
