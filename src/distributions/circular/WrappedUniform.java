/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package distributions.circular;

import distributions.UniformNoise;

/**
 * 
 * @author Robert McKilliam
 */
public class WrappedUniform extends UniformNoise implements CircularDistribution{

        /** Creates Gaussian noise with mean = 0.0 and variance = 1.0 */
    public WrappedUniform() {
        super();
    }

    /** Creates a new instance of GaussianNoise with specific variance and mean */
    public WrappedUniform(double mean, double variance){
        super(Math.IEEEremainder(mean,2*Math.PI), variance);
    }

    /** Returns an instance of wrapped Gaussian noise */
    @Override
    public double getNoise(){
        return Math.IEEEremainder(super.getNoise(), 2*Math.PI);
    }


    @Override
    public double pdf(double x){
        double pdf = 0.0;

        //add positive wrapping
        double add = super.pdf(x);
        int n = 1;
        while( add != 0.0 ){
            pdf += add;
            add = super.pdf( x + 2*Math.PI*n );
            n++;
        }

        //add negative wrapping
        add = super.pdf(x-1);
        n = -2;
        while( add != 0.0 ){
            pdf += add;
            add = super.pdf( x + 2*Math.PI*n );
            n--;
        }
        return pdf;
    }

    public double getWrappedVariance() {
        WrappedVarianceCalculator vcalc = new WrappedVarianceCalculator(this);
        return vcalc.computeVarianceMod2pi();
    }

    public static class Mod1  extends UniformNoise implements CircularDistribution{

        /** Creates Gaussian noise with mean = 0.0 and variance = 1.0 */
        public Mod1() {
            super();
        }

        /** Creates a new instance of GaussianNoise with specific variance and mean */
        public Mod1(double mean, double variance){
            super(Math.IEEEremainder(mean,1.0), variance);
        }

        /** Returns an instance of wrapped Gaussian noise */
        @Override
        public double getNoise(){
            return Math.IEEEremainder(super.getNoise(), 1.0);
        }


        @Override
        public double pdf(double x){
            double pdf = 0.0;

            //add positive wrapping
            double add = super.pdf(x);
            int n = 1;
            while( add != 0.0 ){
                pdf += add;
                add = super.pdf( x + n );
                n++;
            }

            //add negative wrapping
            add = super.pdf(x-1);
            n = -2;
            while( add != 0.0 ){
                pdf += add;
                add = super.pdf( x + n );
                n--;
            }
            return pdf;
        }

        public double getWrappedVariance() {
            WrappedVarianceCalculator vcalc = new WrappedVarianceCalculator(this);
            return vcalc.computeVarianceMod1();
        }
    }

}
