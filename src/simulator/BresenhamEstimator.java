package simulator;

import java.util.Iterator;
import java.util.Random;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Vector;

import javax.vecmath.GVector;

/**
 * A class that iterates along the Bresenham set for a line z in A_n^* 
 * <p>
 * For the moment, only works if fmin >= 0 and where the line doesn't
 * pass through any lattice points.
 * <p>
 * @author Vaughan Clarkson, 24-Feb-05.
 * First working (?) version, 25-Feb-05.
 * Included closest point algorithm, 27-Feb-05.
 * Reworked as an extension of Anstar with PRIEstimator interface, 15-Jun-05.
 * Added setSize method, 16-Jun-05.
 */
    public class BresenhamEstimator extends Anstar implements PRIEstimator {

    double f, nextf;
    double[] z, sumz, sumv, kappa;
    Double[] phi;
    Integer[] ix;
    TreeMap sorted;

    public void setSize(int N) {
	setDimension(N-1); // => n = N-1
	z = new double[N];
	kappa = new double[N];
	sumz = new double[n];
	sumv = new double[n];
	sorted = new TreeMap();
	phi = new Double[2*n];
	ix = new Integer[2*n];
	for (int i = 0; i < 2*n; i++)
	    ix[i] = new Integer(i);
    }

    void initialise(double[] y, double fmin) {
	if (n != y.length-1)
	    setSize(y.length);

	// Re-using z; not good programming practice!
	for (int i = 0; i < y.length; i++)
	    z[i] = fmin * y[i];
	nearestPoint(z);

	// Here is where z is really set up
	project(y, z);

	check(fmin); // DEBUG CHECK

	// Figure out s in the special case that fmin == 0

	if (fmin == 0) {
	    for (int i = 0; i <= n; i++)
		delta[i] = new Double(z[i]);
	    Sorter.sortIndices(delta, s);
	}

	calcall();
	nextIndex(fmin);
    }

    void nextPermutahedron() {
	while (!nextCrossedPermutahedronBoundary());
    }

    boolean nextCrossedPermutahedronBoundary() {
	if (m >= n) {

	    // Swap s[m-n] and s[m-n+1]

	    int temp = s[m-n];
	    s[m-n] = s[m-n+1];
	    s[m-n+1] = temp;

	    if (m > n) {
		sumz[m-n] = sumz[m-n-1] + z[s[m-n]];
 		sumv[m-n] = sumv[m-n-1] + v[s[m-n]];
	    }
	    else {
		sumz[0] = z[s[0]];
		sumv[0] = v[s[0]];
	    }

	    recalcphi(m-n);
	    if (m > n)
		recalcphi(m-1);
	    if (m + 1 < 2*n)
		recalcphi(m+1);
	    nextIndex();
	    return false;
	}

	rotate(m);
	calcall();
	m = n - m - 1;
	nextIndex();
	return true;
    }

    void calcall() {
	double lastz = 0, lastv = 0;
	for (int i = 0; i < n; i++) {
	    sumz[i] = lastz + z[s[i]];
	    sumv[i] = lastv + v[s[i]];
	    lastz = sumz[i];
	    lastv = sumv[i];
	}

	sorted.clear();
	for (int i = 0; i < 2*n; i++) {
	    phi[i] = new Double(calcphi(i));
	    sorted.put(phi[i], ix[i]);
	}
    }

    void recalcphi(int i) {
	sorted.remove(phi[i]);
	phi[i] = new Double(calcphi(i));
	sorted.put(phi[i], ix[i]);
    }

    double calcphi(int i) {
	if (i < n)
	    return (gamma(i) + sumv[i]) / sumz[i];
	else
	    return (v[s[i-n]] - v[s[i-n+1]]) / (z[s[i-n]] - z[s[i-n+1]]);
    }

    void nextIndex() {
	nextIndex(phi[m].doubleValue());
    }

    void nextIndex(double f) {
	this.f = f;

	// Is this next bit guaranteed to work?  The API documentation
	// is a little unclear on whether the keySet of a tailMap is
	// actually sorted.  I'm assuming it is.

	Iterator iterator = sorted.tailMap(new Double(f)).keySet().iterator();
	Double d;

	/*
	// DEBUG CHECK
	double last = ((Double) iterator.next()).doubleValue();
	while (iterator.hasNext()) {
	    if ((d = (Double) iterator.next()).doubleValue() <= last)
		System.err.println("*** ERROR: tailMap is out of order");
	    last = d.doubleValue();
	}
	iterator = sorted.tailMap(phi[m]).keySet().iterator();
	// END DEBUG CHECK
	*/

	while ((d = (Double) iterator.next()).doubleValue() <= f);
	nextf = d.doubleValue();
	m = ((Integer) sorted.get(d)).intValue();
    }

    double gamma(int i) {
	return (double) -(i+1) * (n-i) / (2*n + 2);
    }

    // Check that we haven't derailed, i.e., that s maintains a true
    // order and that v is really the closest lattice point to the
    // line on the line segment being considered

    boolean check() {
	return check((f + nextf) / 2);
    }

    boolean check(double fmid) {
	double[] delta = new double[n+1];
	double sumdelta = 0;
	for (int i = 0; i <= n; i++) {
	    delta[i] = fmid * z[s[i]] - v[s[i]];
	    if ((i > 0) && (delta[i] < delta[i-1])) {
		System.err.println("*** ERROR: s is out of order");
		return false;
	    }
	    sumdelta += delta[i];
	    if ((i < n) && (sumdelta < gamma(i))) {
		System.err.println("*** ERROR: v is not closest");
		return false;
	    }
	}
	return true;
    }
    
    public int numRegions;
    /**
     * Return the number of regions entered by the estimator
     */
    public int regionsEntered() { return numRegions; }

    // Pre: fmin > 0

    public double estimateFreq(double[] y, double fmin, double fmax) {
	initialise(y, fmin);
	GVector zbf = new GVector(z);
	GVector vbf = new GVector(v);
	double fhat;
	if (vbf.normSquared() == 0)
	    fhat = fmin;
	else
	    fhat = Math.max(fmin, Math.min(fmax,
					   vbf.normSquared() / vbf.dot(zbf)));
	GVector deltabf = new GVector(z);
	deltabf.scale(fhat);
	deltabf.sub(vbf);
	double bestScore = deltabf.normSquared() / (fhat * fhat);
        numRegions = 0;
	while (f < fmax) {
	    // check();
	    if (nextCrossedPermutahedronBoundary()) {
		vbf.set(v);
		double f = Math.min(fmax, vbf.normSquared() / vbf.dot(zbf));
		deltabf.set(zbf);
		deltabf.scale(f);
		deltabf.sub(vbf);
		double score = deltabf.normSquared() / (f*f);
                //double score = deltabf.normSquared();
		if (score < bestScore) {
		    bestScore = score;
		    fhat = f;
		}
                numRegions++;
	    }
	}
	return fhat;
    }

    public double varianceBound(double sigma, double[] k) {
	Anstar.project(k, kappa);
	double sk = 0;
	for (int i = 0; i < k.length; i++)
	    sk += kappa[i] * kappa[i];
	return sigma * sigma / sk;
    }

    public static void main(String argv[]) {
	int n = 5;
	Random random = new Random();
	double[] y = new double[n+1];

	System.out.print("y =");
	for (int i = 0; i <= n; i++) {
	    y[i] = random.nextGaussian();
	    System.out.print(" " + y[i]);
	}
	System.out.println();

	double fmin = Math.abs(random.nextGaussian());
	BresenhamEstimator bi = new BresenhamEstimator();
	bi.initialise(y, fmin);
	bi.check(fmin);

	System.out.print("z =");
	for (int i = 0; i <= n; i++)
	    System.out.print(" " + bi.z[i]);
	System.out.println();

	for (int iter = 0; iter < 100; iter++) {
	    System.out.println("f = " + bi.f);

	    System.out.print("u =");
	    for (int i = 0; i <= n; i++)
		System.out.print(" " + (int) bi.u[i]);
	    System.out.println();

	    bi.check();
	    bi.nextCrossedPermutahedronBoundary();
	}
    }
    
    protected Vector fs, Ls;
    public Object[] getfs() { return fs.toArray(); }
    public Object[] getLs() { return Ls.toArray(); }
    /** 
     * Return all the frequencies closest to a lattice point and the liklihood
     * of these frequencies.
     */
    public void calculateLikelihood(double[] y, double fmin, double fmax) {
	initialise(y, fmin);
	GVector zbf = new GVector(z);
	GVector vbf = new GVector(v);
        fs = new Vector();
        Ls = new Vector();
	double fhat;
	if (vbf.normSquared() == 0)
	    fhat = fmin;
	else
	    fhat = Math.max(fmin, Math.min(fmax,
					   vbf.normSquared() / vbf.dot(zbf)));
	GVector deltabf = new GVector(z);
	deltabf.scale(fhat);
	deltabf.sub(vbf);
	double bestScore = deltabf.normSquared() / (fhat * fhat);
	while (f < fmax) {
	    // check();
	    if (nextCrossedPermutahedronBoundary()) {
		vbf.set(v);
		double f = Math.min(fmax, vbf.normSquared() / vbf.dot(zbf));
		deltabf.set(zbf);
		deltabf.scale(f);
		deltabf.sub(vbf);
		//double score = deltabf.normSquared() / (f*f);
                double score = deltabf.normSquared();
		if (score < bestScore) {
		    bestScore = score;
		    fhat = f;
		}
                //fill up the array of values
                fs.add(f);
                Ls.add(-score);                
	    }
	}
    }
}
