package robbysim.pes;

/**
 * Implementation of Sidiropoulos et al.'s SLS2-ALL algorithm for PRI
 * estimation.
 * @author Vaughan Clarkson, 16-Jun-05.
 * Stupid bug fix, 17-Jun-05.
 */
public class SLS2all implements PRIEstimator {

    protected int NUM_SAMPLES = 100;

    int N, m;
    double[] d, kappa;
    int[] u;

    protected PhaseEstimator phasestor;

    /** Period and phase estimates */
    protected double That, phat;
    
    protected SLS2all(){
    }
    
    public SLS2all(int N, int samples){
        setSize(N);
        NUM_SAMPLES = samples;
    }

    private void setSize(int N) {
	this.N = N;
        phasestor = new PhaseEstimator(N);
	m = N * (N-1) / 2;
	d = new double[m];
	u = new int[m];
	kappa = new double[N];
    }

    public void estimate(double[] y, double Tmin, double Tmax) {

        //first compute the period estimate
        double fmin = 1/Tmax; double fmax = 1/Tmin;
	int k = 0;
	for (int i = 0; i < N-1; i++)
	    for (int j = i+1; j < N; j++)
		d[k++] = y[j] - y[i];
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
