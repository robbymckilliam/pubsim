/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices;

import Jama.Matrix;
import static pubsim.Util.*;
import pubsim.VectorFunctions;
import static pubsim.VectorFunctions.dot;
import pubsim.lattices.reduction.LLL;

/**
 * Glue vector based algorithm for VnmStar.
 * I am using the notation from my thesis.  m is the order of the polynomial
 * and N = n + m + 1 is the dimension that lattice lies in.  n is the
 * dimension of the lattice.
 * 
 * @author Robby McKilliam
 */
public class VnmStarGlued extends VnmStar implements LatticeAndNearestPointAlgorithm {


    //generator matrix for the dual of the integer valued polynomials
    //assuming they are tranformed to the integer lattice.
    private Matrix B;

    //store all the legendre polynomials so that we can make projection fast.
    double[][] legendre;
    
    private double[] ytlat, ylat, ulat, utlat, xlat, p;
    int N;

    private SphereDecoder sd;

    //radius of the sphere we are going to use.
    private double radius, BEST_DIST;

    protected VnmStarGlued(){}

    public VnmStarGlued(int m, int n){
        this.m = m;
        setDimension(n);
    }

    public VnmStarGlued(int m){
        this.m = m;
    }

    @Override
    public void setDimension(int n) {
        this.n = n;
        N = n + m + 1;

        //compute the matrix mapping the dual to integer valued polys
        Matrix M = new Matrix(m+1, m+1);
        for(int k = 0; k <= m; k++){
            long fk = factorial(k);
            double ldl = fk*fk*binom(N+k, 2*k+1)/((double)binom(2*k,k));
            for(int i = 0; i <= m; i++){
                double l = discreteLegendrePolynomial(k, N, i);
                M.set(i, k, l/ldl);
            }
        }
        Matrix L = new Matrix(m+1, m+1);
        for(int k = 0; k <= m; k++){
            double scale = factorial(k)/((double)binom(2*k,k));
            for(int s = 0; s <= m; s++){
                double l = scale*Math.pow(-1, s+k)*binom(s+k, s)*binom(N-s-1, N-k-1);
                L.set(k, s, l);
            }
        }
        B = M.times(L);
        sd = new SphereDecoder(new GeneralLattice(B));

        //compute all the Legendre polynomials
        legendre = new double[m+1][];
        for(int k = 0; k <= m; k++)
            legendre[k] = discreteLegendrePolynomialVector(N, k);

        //allocate working memory
        ytlat = new double[N];
        ylat = new double[N];
        ulat = new double[N];
        utlat = new double[N];
        xlat = new double[N];
        p = new double[m+1];



    }

    @Override
    public void nearestPoint(double[] y) {

        //project y into hyperplane of the lattice.
        //this modifies the memory sent, so you need to be a little
        project(y,ylat);

        //setup object to enumerate points in by in sphere
        //surrounding the hypercube.
        radius = Math.sqrt(m+1)*0.5 + 0.000001;
        BEST_DIST = Double.POSITIVE_INFINITY;
        sd.run();

        project(ulat, xlat);

    }

    /** Project into the space this lattice lies in. */
    @Override
    public void project(double[] x, double[] y){
        System.arraycopy(x, 0, y, 0, N);
        for(int k = 0; k <= m; k++){
            double[] ell = legendre[k];
            double ytp = dot(y,ell);
            double ptp = dot(ell,ell);
            double scale = ytp/ptp;
            for(int s = 0; s < N; s++) y[s] -= ell[s]*scale;
        }
    }

    @Override
    public double[] getLatticePoint() {
        return xlat;
    }

    @Override
    public double[] getIndex() {
        return ulat;
    }

    @Override
    public double distance() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Modified SphereDecoder.  Runs some code internally.  Probably would
     * not be a bad idea to make a general functional version of this.
     * It would be able to replace the clunky threaded PointInSphere.
     */
    private class SphereDecoder extends pubsim.lattices.decoder.SphereDecoder{

        public SphereDecoder(Lattice L){
            super(L);
        }

        @Override
        public void setLattice(Lattice L) {
            G = L.getGeneratorMatrix().copy();
            m = G.getRowDimension();
            n = G.getColumnDimension();
            u = new double[n];
            uh = new double[n];
            x = new double[m];
            yr = new double[n];
            ut = new double[n];
            ubest = new double[n];
            xr = new double[n];

            lll = new LLL();
            B = lll.reduce(G);
            U = lll.getUnimodularMatrix();

            //CAREFULL!  This version of the sphere decoder requires R to
            //have positive diagonal entries.
            pubsim.QRDecomposition QR = new pubsim.QRDecomposition(B);
            R = QR.getR();
            Q = QR.getQ();

        }

        public void run() {

            D = radius;

            //current element being decoded
            int k = n-1;

            decode(k, 0);

        }

        /**
         * Recursive decode function to test nearest plane
         * for a particular dimension/element
         */
        @Override
        protected void decode(int k, double d){
            //return if this is already not the closest point
            if(d > D){
                return;
            }

            //compute the sum of R[k][k+i]*uh[k+i]'s
            //and the distance so far
            double rsum = 0.0;
            for(int i = k+1; i < n; i++ ){
                rsum += ut[i]*R.get(k, i);
            }

            //set least possible ut[k]
            ut[k] = Math.ceil((-Math.sqrt(D - d) - rsum)/R.get(k,k));

            while(ut[k] <= (Math.sqrt(D - d) - rsum)/R.get(k,k) ){
                double kd = R.get(k, k)*ut[k] + rsum;
                double sumd = d + kd*kd;

                //if this is not the first element then recurse
                if( k > 0)
                    decode(k-1, sumd);
                //otherwise check if this is the best point so far encounted
                //and update if required
                else{
                    if(sumd <= D){

                        
                        for(int i = 0; i < N; i++) ytlat[i] = 0.0;
                        VectorFunctions.matrixMultVector(U, ut, p);
                        System.arraycopy(p, 0, ytlat, 0, p.length);
                        project(ytlat,ytlat);

                        double dist = 0.0;
                        for(int i = 0; i < N; i++) {
                            utlat[i] = Math.round(ylat[i] - ytlat[i]);
                            ytlat[i] = utlat[i] + ytlat[i];
                            double ddd = ylat[i] - ytlat[i];
                            dist += ddd*ddd;
                        }
                        if(dist < BEST_DIST){
                            System.arraycopy(utlat, 0, ulat, 0, N);
                            for(int i = 0; i < p.length; i++) ulat[i] += p[i];
                            BEST_DIST = dist;
                        }

                    }
                }
                ut[k]++;
            }

        }

    }
    
    private double[] yDoubletoy;
    @Override
    public void nearestPoint(Double[] y) {
        if(yDoubletoy == null || yDoubletoy.length != y.length)
            for(int i = 0; i < y.length; i++) yDoubletoy[i] = y[i];
        this.nearestPoint(y);
    }


}
