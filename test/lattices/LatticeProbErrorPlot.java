/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import lattices.leech.Leech;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Vector;
import static simulator.Range.*;

/**
 *
 * @author Robby McKilliam
 */
public class LatticeProbErrorPlot {


    /**
     * Main function for testing the practical running times
     * of nearest point algorithms.
     */
    public static void main(String[] args) throws Exception {


        double Sstart = 1;
        double Send = 20;
        double Sstep = 0.1;
        Vector<Double> proberr = new Vector<Double>();

        //Lattice L = new Leech();
        Lattice L = new Vnm(3, 48);

        int a = 6;
        for(double S : range(Sstart, Send, Sstep) ){
            double d = L.log10ProbCodingError(S);
            proberr.add(d);
            System.out.println(S + ", " + d);
        }

        File file = new File( L.getClass().toString() + "_" + L.getDimension() );
        BufferedWriter writer =  new BufferedWriter(new FileWriter(file));
        int count = 0;
        for( double S : range(Sstart, Send, Sstep)){
            writer.write(S + "\t"
                    + proberr.get(count).toString().replace('E', 'e'));
            writer.newLine(  );
            count++;
        }
        writer.close();

    }

}
