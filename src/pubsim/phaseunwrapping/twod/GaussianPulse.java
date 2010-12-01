/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.phaseunwrapping.twod;

import pubsim.distributions.GaussianNoise;
import pubsim.distributions.RandomVariable;

/**
 * Construct a wrapped, noisy, 2D gaussian pulse.
 * By default the noise variace is 0.
 * @author Robby McKilliam
 */
public class GaussianPulse implements WrappedData{

    RandomVariable noise = new GaussianNoise(0.0,0.0);
    int N, M;
    double[][] y, yw, u;
    double a, b;

    public GaussianPulse(){
        setParameters(1.0, 1.0);
        setSize(10, 10);
    }

    public GaussianPulse(int M, int N, double amplitude, double stretch){
        setParameters(amplitude, stretch);
        setSize(M, N);
    }

    public void setParameters(double amplitude, double stretch){
        a = amplitude;
        b = stretch;
    }

    public double[][] getWrappedData() {
        return yw;
    }

    public double[][] getTrueData() {
        return y;
    }

    public double[][] getWrappedIntegers() {
        return u;
    }

    public void generateData() {
        double offN = N/2.0;
        double offM = M/2.0;
        for(int m = 0; m < M; m++){
            for(int n = 0; n < N; n++){
                double x = (m - offM)*(m - offM) + (n - offN)*(n - offN);
                y[m][n] = a * Math.exp(-b*x) + noise.getNoise();
                u[m][n] = -Math.round(y[m][n]);
                yw[m][n] = y[m][n] + u[m][n];
            }
        }
    }

    public void setSize(int M, int N) {
        this.N = N;
        this.M = M;
        y = new double[M][N];
        yw = new double[M][N];
        u = new double[M][N];
    }

    public void setNoiseGenerator(RandomVariable noise) {
        this.noise = noise;
    }

}
