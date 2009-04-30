/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.fes;

import flanagan.complex.Complex;


/**
 * Implementation of the Quinn and Fernandes frequency estimator
 *
 * @author Robby McKilliam
 */
public class QuinnFernades extends PeriodogramFFTEstimator
        implements FrequencyEstimator{

    protected QuinnFernades(){
    }

    /** Contructor that sets the number of samples to be taken of
     * the periodogram.
     */
    public QuinnFernades(int oversampled) {
        this.oversampled = oversampled;
    }

    @Override
    public double estimateFreq(double[] real, double[] imag) {
        if (n != real.length) {
            setSize(real.length);
        }

        for (int i = 0; i < n; i++) {
            sig[i] = new Complex(real[i], imag[i]);
        }
        for (int i = n; i < sig.length; i++) {
            sig[i] = new Complex(0.0, 0.0);
        }

        fft.setData(sig);
        fft.transform();
        Complex[] ft = fft.getTransformedDataAsComplex();

        //note that the FFT is generally defined with exp(-jw) but
        //periodogram has exp(jw) so freq are -ve here.
        double maxp = 0;
        double fhat = 0.0;
        double f = 0.0;
        double fstep = 1.0 / ft.length;
        for (int i = 0; i < ft.length; i++) {
            double p = ft[i].squareAbs();
            if (p > maxp) {
                maxp = p;
                fhat = f;
            }
            f-=fstep;
        }

        //Now implements QuinnFernandes iterations.

        //System.out.println(fhat);

        return fhat - Math.round(fhat);
    }


}
