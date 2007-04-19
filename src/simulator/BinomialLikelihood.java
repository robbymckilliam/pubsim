/*
 * BinomialLikelihood.java
 *
 * Created on 18 April 2007, 12:07
 */

package simulator;

/**
 * Modification to the liklihood function by adding the
 * binomial probability of s.  This is suposed to make finding
 * the principal frequency more likely.  The procedure should
 * still be ML provided that the best lattice point is found.
 * @author Robby McKilliam
 */
public class BinomialLikelihood extends ShatErrorTesterLLS implements PRIEstimator{
    
    public BinomialLikelihood(){ NUM_SAMPLES = 1000; }
    
    /**
     * Reimplemented estimateFreq with the binomial
     * likelihood function.
     */
    public double estimateFreq(double[] y, double fmin, double fmax) {
        if (n != y.length-1)
            setSize(y.length);
        project(y, zeta);
        double bestL = Double.NEGATIVE_INFINITY;
        double fhat = fmin;
        double fstep = (fmax - fmin) / NUM_SAMPLES;
        for (double f = fmin; f <= fmax; f += fstep) {
            for (int i = 0; i <= n; i++)
                fzeta[i] = f * zeta[i];
            nearestPoint(fzeta);
            double sumv2 = 0, sumvz = 0;
            for (int i = 0; i <= n; i++) {
                sumv2 += v[i] * v[i];
                sumvz += v[i] * zeta[i];
            }
            double f0 = sumv2 / sumvz;
            double dist2 = 0;
            for (int i = 0; i <= n; i++) {
                double diff = zeta[i] - (v[i] / f0);
                dist2 += diff * diff;
            }
            double sum = 0;
            boolean possibleu = true; 
            for(int i = 0; i < u.length-1; i++){
                if(u[i] == u[i+1]) possibleu = false;
            }
            double sn = f0*y[n]; // - Math.round(sum/u.length);
            double alpha = (n+1)/(sn+1);
            double L = (n+1)*Math.log(alpha) + (sn+1-(n+1))*Math.log(1-alpha) - dist2;
            if (L > bestL && possibleu) {
                System.out.println("y.length = " + y.length + " alpha = " + alpha + " sn = " + sn + " sum/len = " + sum/u.length + " bin = " + (n*Math.log(alpha) + (sn+1-n)*Math.log(1-alpha)) + " dist = " + -dist2/(f0*f0) + " f = " + f0);
                //System.out.println(" fzeta[n] = " + fzeta[n] + " sn = " + (u[n] - u[0]));
                for (int i = 0; i< u.length; i++){
                    //System.out.print(", " + f0*y[i]);
                }
                //System.out.println(")");
                bestL = L;
                fhat = f0;
                bestU = u.clone();
            }
            possibleu = true;
        }
        likelihood = bestL;
        return fhat;
    }
}
