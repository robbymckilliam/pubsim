package pubsim.snpe;


/**
 * Implementation of Sidiropoulos et al.'s SLS2-NOVLP algorithm for PRI
 * estimation.
 * @author Vaughan Clarkson, 15-Jun-05.
 * Added setSize method, 16-Jun-05.
 */
public class SLS2novlp implements PRIEstimator {
    protected int NUM_SAMPLES = 100;

    int N = 0, m;
    double[] d;
    int[] u;

    protected PhaseEstimator phasestor;

    /** Period and phase estimates */
    protected double That, phat;
    
    public SLS2novlp(){
    }
    
    public SLS2novlp(int samples){
        NUM_SAMPLES = samples;
    }


    public void setSize(int N) {
        this.N = N;
	m = N / 2;
        phasestor = new PhaseEstimator(N);
	d = new double[m];
	u = new int[m];
    }

    public void estimate(double[] y, double Tmin, double Tmax) {
        double fmin = 1/Tmax; double fmax = 1/Tmin;
	for (int i = 0; i < m; i++)
	    d[i] = y[(2 * i) + 1] - y[2 * i];
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

    public double getPeriod() {
        return That;
    }

    public double getPhase() {
        return phat;
    }


}
