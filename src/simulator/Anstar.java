package simulator;

/**
 * Class for methods associated with the lattice A_n^*.
 * @author Vaughan Clarkson, 15-Jun-05.
 */
public class Anstar {

    protected int n = 0, m;
    protected int[] s, tmps;
    protected double[] u, v, x;
    protected Double[] delta;

    protected void setDimension(int n) {
	this.n = n;
	// Allocate some space for arrays

	x = new double[n+1];
	u = new double[n+1];
	v = new double[n+1];
	s = new int[n+1];
	tmps = new int[n+1];
	delta = new Double[n+1];
    }
    
    protected void nearestPoint(double[] y) {
	if (n != y.length-1)
	    setDimension(y.length-1);

	project(y, x);

	// Find the closest point

	// Algorithm 1

	for (int i = 0; i <= n; i++)
	    u[i] = Math.round(x[i]);
	project(u, v);
	for (int i = 0; i <= n; i++)
	    delta[i] = new Double(x[i] - v[i]);
	Sorter.sortIndices(delta, s);
	/*
	// DEBUG CHECK
	for (int i = 0; i < n; i++)
	    if (Math.abs(delta[s[i]].doubleValue()
			 - delta[s[i+1]].doubleValue()) > 1) {
		System.err.println("*** ERROR: not alpha close");
		break;
	    }
	// END DEBUG CHECK
	*/

	// Algorithm 2

	m = 0;
	while (delta[s[m]].doubleValue() < (double) m / (n+1) - 0.5) {
	    u[s[m]]--;
	    m++;
	}
	m = n;
	while (delta[s[m]].doubleValue() > (double) (m+1) / (n+1) - 0.5) {
	    u[s[m]]++;
	    m--;
	}
	project(u, v);
	for (int i = 0; i <= n; i++)
	    delta[i] = new Double(x[i] - v[i]);
	Sorter.sortIndices(delta, s);
	/*
	// DEBUG CHECK
	for (int i = 0; i <= n; i++)
	    if (Math.abs(delta[i].doubleValue()) > 0.5) {
		System.err.println("*** ERROR: not beta close");
		break;
	    }
	// END DEBUG CHECK
	*/

	// Algorithm 3

	m = n;
	double t = 0, tau = 0;
	for (int i = 0; i < n; i++) {
	    t += delta[s[i]].doubleValue()
		- (double) (2 * i - n) / (2 * n + 2);
	    if (t < tau) {
		tau = t;
		m = i;
	    }
	}
	rotate(m);
        
    }

    protected void rotate(int r) {
	// Update u and v

	for (int i = r+1; i <= n; i++)
	    u[s[i]]++;
	project(u, v);

	// Rotate s circularly to the left by r+1 positions

	for (int i = 0; i <= n; i++)
	    tmps[i] = s[i];
	for (int i = 0; i <= n; i++)
	    s[i] = tmps[(i + r + 1) % (n + 1)];
    }

    /**
     * Project a vector into the zero-mean plane
     * y is output, x is input (x & y can be the same array)
     * <p>
     * Pre: y.length >= x.length
     */
    public static void project(double[] x, double[] y) {
	double xbar = 0.0;
	for (int i = 0; i < x.length; i++)
	    xbar += x[i];
	xbar /= x.length;
	for (int i = 0; i < x.length; i++)
	    y[i] = x[i] - xbar;
    }
    
    /**Getter for the nearest point.  This is for testing */
    public double[] getv() {return v;}
    /**Getter for the interger index.  This is for testing */
    public double[] getu() {return u;}
}
