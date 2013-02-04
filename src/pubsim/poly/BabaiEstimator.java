/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.poly;

import Jama.Matrix;
import pubsim.VectorFunctions;
import pubsim.lattices.NearestPointAlgorithm;
import pubsim.lattices.VnmStar;
import pubsim.lattices.VnmStarGlued;
import pubsim.lattices.decoder.Babai;
import pubsim.lattices.reduction.LatticeReduction;
import pubsim.lattices.reduction.LLL;

// For test harness only
import pubsim.lattices.reduction.HKZ;
import pubsim.poly.PolynomialPhaseSignal;
import pubsim.distributions.GaussianNoise;

/**
 * Uses the Babai nearest plane algorithm
 *
 * @author Robby Modified by Vaughan Clarkson to allow different underlying
 * reduction algorithms (especially HKZ).
 */
public class BabaiEstimator implements PolynomialPhaseEstimator {

    protected double[] ya, p;
    protected int n, m;
    protected VnmStar lattice;
    protected NearestPointAlgorithm npalgorithm;
    protected Matrix M, K;
    protected AmbiguityRemover ambiguityRemover;

    protected BabaiEstimator() {
    }

    /**
     * You must set the polynomial order in the constructor
     *
     * @param m = polynomial order
     */
    public BabaiEstimator(int m, int n) {
        this(m, n, new LLL());
    }

    public BabaiEstimator(int m, int n, LatticeReduction lr) {
        lattice = new VnmStarGlued(m, n - m - 1);
        npalgorithm = new Babai(lattice, lr);
        this.m = m;
        ambiguityRemover = new AmbiguityRemover(m);

        ya = new double[n];
        p = new double[m + 1];
        this.n = n;

        M = lattice.getMMatrix();
        Matrix Mt = M.transpose();
        K = Mt.times(M).inverse().times(Mt);
    }

    @Override
    public double[] estimate(double[] real, double[] imag) {
        if (n != real.length) {
            throw new RuntimeException("Data length does not equal " + n);
        }

        for (int i = 0; i < real.length; i++) {
            ya[i] = Math.atan2(imag[i], real[i]) / (2 * Math.PI);
        }
        npalgorithm.nearestPoint(ya);
        double[] u = npalgorithm.getIndex();

        double[] ymu = new double[ya.length];
        for (int i = 0; i < u.length; i++) {
            ymu[i] = ya[i] - u[i];
        }
        System.arraycopy(ya, u.length, ymu, u.length, ya.length - u.length);

        //compute the parameters
        VectorFunctions.matrixMultVector(K, ymu, p);

        return ambiguityRemover.disambiguate(p);
    }

    /**
     * Run the estimator and return the square error wrapped modulo the the
     * ambiguity region between the estimate and the truth. i.e. dealias the
     * estimate before computing the square error.
     */
    public double[] error(double[] real, double[] imag, double[] truth) {

        double[] est = estimate(real, imag);
        double[] err = new double[est.length];

        for (int i = 0; i < err.length; i++) {
            err[i] = est[i] - truth[i];
        }
        err = ambiguityRemover.disambiguate(err);
        for (int i = 0; i < err.length; i++) {
            err[i] *= err[i];
        }
        return err;
    }

    @Override
    public int getOrder() {
        return m;
    }

    // Test harness
    public static void main(String args[]) {
        int numTrials = 2000;
        int N = 140;
        int order = 1; // frequency estimation
        double minSNRdB = -5, maxSNRdB = 10, stepSNRdB = 1;
        PolynomialPhaseSignal siggen = new PolynomialPhaseSignal(N);
        BabaiEstimator estlll = new BabaiEstimator(order, N, new LLL());
        BabaiEstimator esthkz = new BabaiEstimator(order, N, new HKZ());
        SphereDecoderEstimator estsd = new SphereDecoderEstimator(order, N);
        MbestEstimator estmbest = new MbestEstimator(order, N, 4*N);
        MbestEstimator estmbesthkz = new MbestEstimator(order, N, 4*N, new HKZ());
        double mselll = 0, msehkz = 0, msesd = 0, msembest = 0, msembesthkz = 0;
        for (double SNRdB = minSNRdB; SNRdB <= maxSNRdB; SNRdB += stepSNRdB) {
            double SNR = Math.pow(10, SNRdB / 10);
            GaussianNoise noise = new GaussianNoise(0, 1 / SNR);
            siggen.setNoiseGenerator(noise);
            for (int trial = 0; trial < numTrials; trial++) {
                siggen.generateRandomParameters(order);
                double[] p0 = siggen.getParameters();
                siggen.generateReceivedSignal();
                double[] err = estlll.error(siggen.getReal(), siggen.getImag(), p0);
                mselll += err[order];
                err = esthkz.error(siggen.getReal(), siggen.getImag(), p0);
                msehkz += err[order];
                //err = estsd.error(siggen.getReal(), siggen.getImag(), p0);
                //msesd += err[order];
                err = estmbest.error(siggen.getReal(), siggen.getImag(), p0);
                msembest += err[order];
                err = estmbesthkz.error(siggen.getReal(), siggen.getImag(), p0);
                msembesthkz += err[order];
            }
            mselll /= numTrials;
            msehkz /= numTrials;
            msesd /= numTrials;
            msembest /= numTrials;
            msembesthkz /= numTrials;
            System.out.println("" + SNRdB + " " + mselll + " " + msehkz + " " + msesd + " "+ msembest + " " + msembesthkz);
        }
    }
}
