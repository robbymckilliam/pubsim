/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Vector;
import lattices.An.AnFastSelect;
import lattices.leech.Leech;
import lattices.Craig;
import lattices.Zn;
import lattices.P48;
import lattices.An.AnFastSelect;
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


        double Sstart = -4;
        double Send = 10;
        double Sstep = 0.1;
        Vector<Double> proberr = new Vector<Double>();

        //AbstractLattice L = new Leech();
        //AbstractLattice L = new Dn(4);
        //AbstractLattice L = new Vnm(7, 60);
        //AbstractLattice L = new Zn(10);
        //AbstractLattice L = new P48();
        //AbstractLattice L = new AnFastSelect(2);

        int p = 379;
        int n = p-1;
        int r = (p+1)/4;
        AbstractLattice L = new Craig(n, r);

        System.out.println(L.inradius() + ", " + L.kissingNumber() + ", " + L.logVolume() + ", " + L.centerDensity());

        for(double S : range(Sstart, Send, Sstep) ){
            //double d = L.probCodingError(S);
            //double d = L.log10ProbCodingError(S);
            double d = L.unshapedProbCodingError(Math.pow(10, S/10));
            //double d = L.probCodingErrorPer2Dim(10*Math.log10(S));
            proberr.add(d);
            System.out.println(S + ", " + d);
        }

        File file = new File( L.getClass().toString().replace(' ', '.') + "_" + L.getDimension() );
        BufferedWriter writer =  new BufferedWriter(new FileWriter(file));
        int count = 0;
        for( double S : range(Sstart, Send, Sstep)){
            if(proberr.get(count) != 0.0){
                writer.write(S + "\t"
                        + proberr.get(count).toString().replace('E', 'e'));
                writer.newLine(  );
                count++;
            }
        }
        writer.close();

    }

}
