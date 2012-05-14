package pubsim.snpe;


/**
 * A variant on the SLS2 algorithm that should do significantly better than
 * SLS2-NOVLP.
 * @author Vaughan Clarkson, 15-Jun-05.
 * Added setSize method, 16-Jun-05.
 */
public class SLS2new implements PRIEstimator {
    protected int NUM_SAMPLES = 100;

    int N = 0, m, s;
    double[] d;
    int[] u;

    protected PhaseEstimator phasestor;

    /** Period and phase estimates */
    protected double That, phat;
    
    public SLS2new(int samples){
        NUM_SAMPLES = samples;
    }

    public void setSize(int N) {
        this.N = N;
        phasestor = new PhaseEstimator(N);
	m = N / 2;
	s = m + (N % 2);
	d = new double[m];
	u = new int[m];
    }

    @Override
    public void estimate(Double[] y, double Tmin, double Tmax) {
        double fmin = 1/Tmax; double fmax = 1/Tmin;
	if (N != y.length)
	    setSize(y.length);
	for (int i = 0; i < m; i++)
	    d[i] = y[i + s] - y[i];
	double bestL = Double.POSITIVE_INFINITY;
	double fhat = fmin;
	double fstep = (fmax - fmin) / NUM_SAMPLES;
	for (double f = fmin; f <= fmax; f += fstep) {
	    double sumu2 = 0;
	    double sumud = 0;
	    for (int i = 0; i < m; i++) {
		u[i] = (int) Math.round(f * d[i]);
		sumu2 += u[i] * u[i];
		sumud += u[i] * d[i];
	    }
	    double f0 = sumu2 / sumud;
	    double L = 0;
	    for (int i = 0; i < m; i++) {
		double diff = d[i] - (u[i] / f0);
		L += diff * diff;
	    }
	    if (L < bestL) {
		bestL = L;
		fhat = f0;
	    }
	}
	That = 1/fhat;

        //now compute the phase estimate
        phat = phasestor.getPhase(y, That);
    }

    @Override
    public double getPeriod() {
        return That;
    }

    @Override
    public double getPhase() {
        return phat;
    }

}
