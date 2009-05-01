/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.fes;

import flanagan.complex.Complex;
import flanagan.math.FourierTransform;


/**
 * Implementation of the Quinn and Fernandes frequency estimator
 *
 * @author Robby McKilliam
 */
public class QuinnFernades extends PeriodogramFFTEstimator
        implements FrequencyEstimator{

    //eta term in Barry's paper.  This is related to the ARMA(1,1) model.
    protected Complex[] eta;

    /**
     * By default, oversample = 1. Quinn Fernandes does not need
     * zero padding.
     */
    public QuinnFernades(){
        oversampled = 1;
    }

    /** Contructor that sets the number of samples to be taken of
     * the periodogram.
     */
    public QuinnFernades(int oversampled) {
        this.oversampled = oversampled;
    }

    @Override
    public void setSize(int n) {
        this.n = n;
        sig = new Complex[FourierTransform.nextPowerOfTwo(oversampled * n)];
        fft = new FourierTransform();
        eta = new Complex[FourierTransform.nextPowerOfTwo(oversampled * n) + 1];
    }

    @Override
    public double estimateFreq(double[] real, double[] imag) {
        if (n != real.length) {
            setSize(real.length);
        }

        //construct zero padded complex signal
        for (int i = 0; i < n; i++) {
            sig[i] = new Complex(real[i], imag[i]);
        }
        for (int i = n; i < sig.length; i++) {
            sig[i] = new Complex(0.0, 0.0);
        }

        for (int i = n; i < sig.length; i++) {
            eta[i] = new Complex(0.0, 0.0);
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

        fhat = fhat - Math.round(fhat);

        //Now implement QuinnFernandes iterations.
        int numIter = 0;
        double lastf = fhat - 2 * EPSILON;
        while(Math.abs(fhat - lastf) > EPSILON && numIter <= MAX_ITER){
            //System.out.println(fhat);
            lastf = fhat;
            Complex efhat = new Complex(Math.cos(2*Math.PI*fhat),
                                        Math.sin(2*Math.PI*fhat));
            //compute next eta
            eta[0] = new Complex(0,0);
            for(int t = 0; t < sig.length; t++){
                eta[t+1] = sig[t].plus( efhat.times(eta[t]) );
            }
            //compute next fhat
            Complex efhatc = efhat.conjugate();
            double num = 0.0, den = 0.0;
            for(int t = 0; t < sig.length; t++){
                num += efhatc.times(sig[t].times(eta[t].conjugate())).getImag();
                den += eta[t].squareAbs();
            }
            fhat += 2*num/den/2/Math.PI;
            //System.out.println(fhat);
            numIter++;
        }
        //System.out.println(fhat);
        return fhat - Math.round(fhat);
    }


}
