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
import lattices.Craig;

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


        double Sstart = 0;
        double Send = 10;
        double Sstep = 1;
        Vector<Double> proberr = new Vector<Double>();

        //AbstractLattice L = new Leech();
        //AbstractLattice L = new Vnm(2, 48);
        //AbstractLattice L = new Zn(10);

        int p = 307;
        int n = p-1;
        int r = (p+1)/4;
        AbstractLattice L = new Craig(n, r);

        for(double S : range(Sstart, Send, Sstep) ){
            //double d = L.probCodingError(S);
            //double d = L.log10ProbCodingError(S);
            double d = L.unshapedProbCodingError(Math.pow(10, S/10));
            //double d = L.probCodingErrorPer2Dim(10*Math.log10(S));
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
