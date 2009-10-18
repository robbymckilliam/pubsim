/*
 * SLSKay.java
 *
 * Created on 5 October 2007, 18:39
 */

package simulator.pes;

/**
 * SLS estimator that uses all adjacent differences and
 * attemps to whiten the noise with a filter.  It
 * is uncertain whether this will work at all!  However,
 * it might be interesting to see what happens.
 * Under construction.
 * @author Robby McKilliam
 */
public class SLSKay implements PRIEstimator {
    protected int NUM_SAMPLES = 100;

    int n = 0;
    double[] d;
    double[] u;
    
    /** Creates a new instance of SLSKay */
    public SLSKay() {
    }
    
    public SLSKay(int samples){
        NUM_SAMPLES = samples;
    }


    public void setSize(int n) {
	d = new double[n-1];
	u = new double[n-1];
    }

    public double estimateFreq(double[] y, double fmin, double fmax) {
	if (n != y.length)
	    setSize(y.length);
        
        double fhat = 0.0;
        
        for(int i=0; i< n-1; i++)
            d[i] = y[i+1] - y[i];
        
        double step = (fmax-fmin)/NUM_SAMPLES;
        for(double f = fmin; f < fmax; f+=step){
            for(int i=0; i< n; i++)
                u[i] = Math.round(f*d[i]);
                
            //matrix multiply, this can probably be done
            //by a filter in O(n) rather than O(n^2)
            double uCd = 0.0, uCu = 0.0;
            for(int i = 1; i < n; i++){
                for(int j = 1; j < n; j++){
                    double Cij = (Math.min(i,j) - ((double)(i*j))/n);
                    uCd += u[i]*Cij*d[i];
                    uCu += u[i]*Cij*u[i];
                }
            }
            
            //now need to calculate the error term to
            //work out which is the best fhat
            fhat = uCd/uCu;
            
                
                
        }

	return fhat;
    }

    //not implemented
    public double varianceBound(double sigma, double[] k) {
        return 0.0;
    }

    public void estimate(double[] y, double fmin, double fmax) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double getPhase() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double getFrequency() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double getPeriod() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
