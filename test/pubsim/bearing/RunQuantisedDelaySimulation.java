/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.bearing;

import pubsim.bearing.AngularLeastSquaresEstimator;
import pubsim.bearing.BearingEstimator;
import pubsim.bearing.QuantisedDelaySignal;
import pubsim.distributions.circular.WrappedUniform;
import pubsim.distributions.discrete.GeometricRandomVariable;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;
import static pubsim.Util.fracpart;

/**
 *
 * @author Robert McKilliam
 */
public class RunQuantisedDelaySimulation {

    public static void main(String[] args) throws Exception {

        int n = 1000;
        int iterations = 1000;

        String nameetx = "_" + Integer.toString(n) + "quantised";

        QuantisedDelaySignal signal_gen = new QuantisedDelaySignal(n);

        //parameter for geometric distribution.
        double gemparam = 0.9;
        GeometricRandomVariable noise = new GeometricRandomVariable(gemparam);
        signal_gen.setNoiseGenerator(noise);

        double startP = 0.112308417230587;
        double stopP = 0.05;
        double stepP = 0.8198471598640;

        Vector<Double> P_array = new Vector<Double>();
        for(double Pval = startP; Pval >= stopP; Pval *= stepP){
            P_array.add(new Double(Pval));
        }

        Vector<BearingEstimator> estimators = new Vector<BearingEstimator>();

        //add the estimators you want to run
        estimators.add(new AngularLeastSquaresEstimator(n));
        //estimators.add(new MockMeanEstimator());
        //estimators.add(new VectorMeanEstimator());

        Iterator<BearingEstimator> eitr = estimators.iterator();
        while(eitr.hasNext()){

            BearingEstimator est = eitr.next();

            Vector<Double> mse_array = new Vector<Double>(P_array.size());
            java.util.Date start_time = new java.util.Date();
            for(int i = 0; i < P_array.size(); i++){

                signal_gen.setClockPeriod(P_array.get(i));

                double mse = runIterations(est, signal_gen, iterations);

                mse_array.add(mse/iterations);

                System.out.println(P_array.get(i) + "\t" + mse/iterations);


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
                for(int i = 0; i < P_array.size(); i++){
                    writer.write(
                            P_array.get(i).toString().replace('E', 'e')
                            + "\t" + mse_array.get(i).toString().replace('E', 'e'));
                    writer.newLine();
                }
                writer.close();
            } catch(IOException e) {
                System.out.println(e.toString());
            }

        }

        Vector<Double> mse_array = new Vector<Double>(P_array.size());
        //finally print out the asymptotic variance
        for(int i = 0; i < P_array.size(); i++){
                //WrappedUniform.Mod1 unoise = new WrappedUniform.Mod1(0.0, 0.0);
                //unoise.setRange(P_array.get(i));
                //double mse = AngularLeastSquaresEstimator.asymptoticVariance(unoise, n);
                double mse = (Math.pow(P_array.get(i)/2.0 , 2)/3.0)/(n);
                //double wrappedvar = noise.getWrappedVariance();
                //double mse = var_array.get(i)/n;
                mse_array.add(mse);
                System.out.println(P_array.get(i) + "\t" + (Math.pow(P_array.get(i)/2.0 , 2)/3.0) + "\t" + mse);
        }
        try{
            String fname = "asmyp_" + noise.getClass().getName();
            //String fname = "crb_" + noise.getClass().getName();
            File file = new File(fname.concat(nameetx).replace('$', '.'));
            BufferedWriter writer =  new BufferedWriter(new FileWriter(file));
            for(int i = 0; i < P_array.size(); i++){
                    writer.write(
                            P_array.get(i).toString().replace('E', 'e')
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
    public static double runIterations(BearingEstimator rec, QuantisedDelaySignal siggen, int iterations){

        double mse = 0.0;
        Random r = new Random();
        for(int i = 0; i < iterations; i++){

            double delay = (r.nextDouble() - 0.5);
            //delay = 0.00;
            siggen.setDelay(delay);

            double[] signal = siggen.generateReceivedSignal();

//            double var = 0.0;
//            for(int j = 0; j < signal.length; j++){
//                var += (signal[j]-delay)*(signal[j]-delay);
//            }
//            System.out.println(var/signal.length);

            double delayhat = rec.estimateBearing(signal);

            double err = fracpart(siggen.getDelay() - delayhat);

            mse += err*err;

        }

        return mse;
    }

}
