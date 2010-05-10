/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.poly.bounds;

import distributions.circular.CircularDistribution;
import distributions.circular.ProjectedNormalDistribution;
import distributions.circular.VonMises;
import distributions.circular.WrappedUniform;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Vector;

/**
 *
 * @author Robby McKilliam
 */
public class AngularLeastSquaresVariance extends BoundCalculator{

    CircularDistribution dist;

    public AngularLeastSquaresVariance(int N, int m, CircularDistribution dist){
        super(N,m);
        this.dist = dist;
    }

    public void setVariance(double var){
        dist.setVariance(var);
    }

    @Override
    public double getBound(int m) {
        double sigma2 = dist.getWrappedVariance();
        double d = 1 - dist.pdf(-0.5);
        return sigma2/(Math.pow(N, 2*m+1)*d*d) * C.get(m,m);
    }


    //** Write CRB data to a file */
    public static void main(String[] args) throws Exception  {

        int N = 16;
        int m = 3;

        CircularDistribution dist = new VonMises.Mod1();

        AngularLeastSquaresVariance bound =
                new AngularLeastSquaresVariance(N, m, dist);


        //this commented bit is for the projected normal
        //double from_log_snr = 20.0;
        //double to_log_snr = -3.0;
        //double step_log_snr = -1;

//        Vector<Double> snr_array = new Vector<Double>();
//        Vector<Double> snr_db_array = new Vector<Double>();
//        for(double snrdb = from_log_snr; snrdb >= to_log_snr; snrdb += step_log_snr){
//            snr_db_array.add(new Double(snrdb));
//            snr_array.add(new Double(Math.pow(10.0, ((snrdb)/10.0))));
//        }
//
//
//
//        for(int j = 0; j <= m; j++){
//            String fname = bound.getClass().getName() + dist.getClass().getCanonicalName();
//            fname = fname.concat(Integer.toString(N) + "_p" + j);
//            File file = new File(fname);
//            BufferedWriter writer =  new BufferedWriter(new FileWriter(file));
//            for(int i = 0; i < snr_array.size(); i++){
//                double var = 0.5/snr_array.get(i);
//                bound.setVariance(var);
//                writer.write(
//                        (new Double(var)).toString().replace('E', 'e')
//                        + "\t" + (new Double(bound.getBound(j))).toString().replace('E', 'e'));
//                writer.newLine();
//            }
//            writer.close();
//        }

        //this is for wrapped uniform and von mises
        double from_var_db = 18;
        double to_var_db = -3;
        //double from_var_db = -11.2;
        //double to_var_db = -35.8;
        double step_var_db = -1;

        Vector<Double> var_array = new Vector<Double>();
        Vector<Double> var_db_array = new Vector<Double>();
        for(double vardb = from_var_db; vardb >= to_var_db; vardb += step_var_db){
            var_array.add(new Double(Math.pow(10.0, ((vardb)/10.0))));
        }

        for(int j = 0; j <= m; j++){
            String fname = bound.getClass().getName() + dist.getClass().getCanonicalName();
            fname = fname.concat(Integer.toString(N) + "_p" + j);
            File file = new File(fname);
            BufferedWriter writer =  new BufferedWriter(new FileWriter(file));
            for(int i = 0; i < var_array.size(); i++){
                double var = var_array.get(i);
                bound.setVariance(var);
                writer.write(
                        (new Double(dist.getWrappedVariance())).toString().replace('E', 'e')
                        + "\t" + (new Double(bound.getBound(j))).toString().replace('E', 'e'));
                writer.newLine();
            }
            writer.close();
        }

    }

}
