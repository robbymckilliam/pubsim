/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.bearing.phase;

import distributions.GaussianNoise;
import distributions.circular.CircularMeanVariance;
import distributions.circular.ProjectedNormalDistribution;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;
import simulator.Complex;
import static simulator.Util.fracpart;

/**
 *
 * @author Robby McKilliam
 */
public class RunSimulation {

    public static void main(String[] args) throws Exception {

        int n = 4096;
        int seed = 26;
        int iterations = 10000;

        String nameetx = "_" + Integer.toString(n);

        ConstantPhaseSignal signal_gen = new ConstantPhaseSignal();
        signal_gen.setLength(n);
        GaussianNoise noise = new GaussianNoise();
        signal_gen.setNoiseGenerator(noise);

        double from_var_db = 10;
        double to_var_db = -25; //this goes to 25 for smaller n
        //double from_var_db = -8;
        //double to_var_db = -40.0;
        double step_var_db = -1;

        Vector<Double> var_array = new Vector<Double>();
        Vector<Double> var_db_array = new Vector<Double>();
        for(double vardb = from_var_db; vardb >= to_var_db; vardb += step_var_db){
            var_db_array.add(new Double(vardb));
            var_array.add(new Double(Math.pow(10.0, ((vardb)/10.0))));
        }

        Vector<PhaseEstimator> estimators = new Vector<PhaseEstimator>();

        //add the estimators you want to run
        //estimators.add(new LeastSquaresUnwrapping());
        //estimators.add(new ArgumentOfComplexMean());
        //estimators.add(new SamplingLatticeEstimator(12*n));
        //estimators.add(new KaysEstimator());
        //estimators.add(new PSCFDEstimator());
        //estimators.add(new VectorMeanEstimator());

        Iterator<PhaseEstimator> eitr = estimators.iterator();
        while(eitr.hasNext()){

            PhaseEstimator est = eitr.next();

            Vector<Double> mse_array = new Vector<Double>(var_array.size());
            java.util.Date start_time = new java.util.Date();
            for(int i = 0; i < var_array.size(); i++){

                noise.setVariance(var_array.get(i));

                double mse = runIterations(est, signal_gen, iterations);

                mse_array.add(mse/iterations);

                System.out.println(var_array.get(i) + "\t" + mse/iterations);


            }
            java.util.Date end_time = new java.util.Date();
            System.out.println(est.getClass().getName() +
                    " completed in " +
                    (end_time.getTime() - start_time.getTime())/1000.0
                    + "seconds");

            try{
                String fname = est.getClass().getName() + "_" + noise.getClass().getName();
                File file = new File(fname.concat(nameetx).replace('$', '.'));
                BufferedWriter writer =  new BufferedWriter(new FileWriter(file));
                for(int i = 0; i < var_array.size(); i++){
                    writer.write(
                            var_array.get(i).toString().replace('E', 'e')
                            + "\t" + mse_array.get(i).toString().replace('E', 'e'));
                    writer.newLine();
                }
                writer.close();
            } catch(IOException e) {
                System.out.println(e.toString());
            }

        }

        Vector<Double> mse_array = new Vector<Double>(var_array.size());
        //finally print out the asymptotic circularVariance
        for(int i = 0; i < var_array.size(); i++){
                noise.setVariance(var_array.get(i));
                //double mse = LeastSquaresEstimator.asymptoticVariance(
                //        new ProjectedNormalDistribution(0.0, var_array.get(i)),
                //        n);
                double mse = (new CircularMeanVariance(
                        new ProjectedNormalDistribution(0.0, var_array.get(i)))).variance()/n;
                //double mse = var_array.get(i)/(Math.PI*Math.PI*4*n);
                mse_array.add(mse);
                System.out.println(var_array.get(i) + "\t" + mse);
        }
        try{
            String fname = "asymp_arg_" + noise.getClass().getName();
            //String fname = "asmyp_" + noise.getClass().getName();
            File file = new File(fname.concat(nameetx).replace('$', '.'));
            BufferedWriter writer =  new BufferedWriter(new FileWriter(file));
            for(int i = 0; i < var_array.size(); i++){
                writer.write(
                        var_array.get(i).toString().replace('E', 'e')
                        + "\t" + mse_array.get(i).toString().replace('E', 'e'));
                writer.newLine();
            }
            writer.close();
        } catch(IOException e) {
            System.out.println(e.toString());
        }

    }

    /**
     * Runs \param iterations number of iterations of the QAM receiver and
     * returns the codeword error rate (CER)
     */
    public static double runIterations(PhaseEstimator rec, ConstantPhaseSignal siggen, int iterations){

        double mse = 0.0;
        Random r = new Random();
        for(int i = 0; i < iterations; i++){

            double angle = r.nextDouble() - 0.5;
            Complex mean = new Complex(Math.cos(angle*2*Math.PI), Math.sin(angle*2*Math.PI));
            siggen.setMean(mean);

            siggen.generateReceivedSignal();

            double anglehat = rec.estimatePhase(siggen.generateReceivedSignal());

            double err = fracpart(angle - anglehat);

            mse += err*err;

        }

        return mse;
    }

}
