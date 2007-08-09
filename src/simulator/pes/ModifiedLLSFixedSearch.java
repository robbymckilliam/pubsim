/*
 * ModifiedLLSFixedSearch.java
 *
 * Created on 6 June 2007, 17:39
 */

package simulator.pes;

import java.util.Random;
import simulator.*;

/**
 * Allows specification of the frequencies to search
 * @author Robby McKilliam
 */
public class ModifiedLLSFixedSearch extends ShatErrorTesterLLS implements PRIEstimator {
    
    protected double[] fsearch;
    
    /** Creates a new instance of ModifiedLLSFixedSearch */
    public ModifiedLLSFixedSearch(int type) {
        if(type == 1)
            fsearch = new double[] {3.0/4, 1.0 };
        else if(type == 5)
            fsearch = new double[] {3.0/4, 4./5.0, 1.0, 5.0/4.0, 4.0/3.0 };
        else {
            fsearch = new double[200];
            Random rand = new Random();
            for(int i=0; i < fsearch.length-1; i++){
                fsearch[i] = 1.0 + 7.0/12.0*(rand.nextDouble()-0.5);
            }
            fsearch[199] = 1.0;
        }
    }
    
    public double estimateFreq(double[] y, double fmin, double fmax) {
	if (n != y.length-1)
	    setSize(y.length);
	project(y, zeta);
	double bestL = Double.POSITIVE_INFINITY;
	double fhat = fsearch[0];
       
	for (int k = 0; k < fsearch.length; k++) {
	    for (int i = 0; i <= n; i++)
		fzeta[i] = fsearch[k] * zeta[i];
	    nearestPoint(fzeta);
	    double sumz2 = 0, sumvz = 0;
	    for (int i = 0; i <= n; i++) {
		//sumv2 += v[i] * v[i];
                sumz2 += zeta[i] * zeta[i];
		sumvz += v[i] * zeta[i];
	    }
	    //double f0 = sumv2 / sumvz;
            double f0 = sumvz / sumz2;
	    double L = 0;
	    for (int i = 0; i <= n; i++) {
                double diff = v[i] - (f0 * zeta[i]);
		L += diff * diff;
	    }
            //System.out.println(L + ", " + f0 + ", " + fsearch[k]);
	    if (L < bestL) {
		bestL = L;
		fhat = f0;
                likelihood = -L;
                //bestU = u.clone();
                //System.out.println(bestL);
	    }
	}
	return fhat;
    }
    
    
    
}
