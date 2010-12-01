/*
 * Pnx.java
 *
 * Created on 2 November 2007, 13:24
 */

package pubsim.lattices;

import Jama.Matrix;
import pubsim.lattices.Anstar.Anstar;
import pubsim.lattices.Anstar.AnstarBucketVaughan;
import pubsim.lattices.decoder.ShortestVector;
import pubsim.lattices.util.PointInParallelepiped;
import pubsim.VectorFunctions;
import static pubsim.Util.factorial;

/**
 * Approximate nearest point algorithm for Vnm* that samples
 * in the hyperplane H.  Uses the nearest lattice point algorithm
 * for An* to help speed things up.
 * @author Robby McKilliam
 */
public class VnmStarSampled extends VnmStarGlued{

    private Anstar anstar;
    private double[] x, u;
    private int[] samples;
    private Matrix M;
    private double[] yt;

    public VnmStarSampled(int m, int n, int[] samples){
        this.m = m;
        setDimension(n);
        this.samples = samples;
    }

    @Override
    public void setDimension(int n) {
        this.n = n;
        N = n + m + 1;

        //compute all the Legendre polynomials
        //divide then by k! so that we search the appropriate sized space.
        legendre = new double[m+1][];
        for(int k = 0; k <= m; k++)
            legendre[k] = discreteLegendrePolynomialVector(N, k);
//        for(int k = 0; k <= m; k++){
//            for(int i = 0; i < N; i++)
//                legendre[k][i] = legendre[k][i]/factorial(k);
//        }

        anstar = new AnstarBucketVaughan(N-1);

        M = new Matrix(legendre).transpose().getMatrix(0, N-1, 1, m);

        //working memory
        yt = new double[N];
        u = new double[N];
        x = new double[N];
        
    }

    @Override
    public void nearestPoint(double[] y) {

        PointInParallelepiped points = new PointInParallelepiped(M, samples);
        double D = Double.POSITIVE_INFINITY;
        while(points.hasMoreElements()){
            double[] p = points.nextElement().getColumnPackedCopy();
            for(int i = 0; i < N; i++){
                yt[i] = y[i] - p[i];
            }
            anstar.nearestPoint(yt);
            project(anstar.getIndex(), yt);
            double d = VectorFunctions.distance_between(yt, y);
            if( d < D ){
                System.arraycopy(anstar.getIndex(), 0, u, 0, N);
                D = d;
            }
        }

        project(u, x);

    }

    @Override
    public double[] getLatticePoint() {
        return x;
    }

    @Override
    public double[] getIndex() {
        return u;
    }
    
}
