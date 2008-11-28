/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator.poly;

import Jama.Matrix;
import lattices.GeneralLattice;
import lattices.Lattice;
import lattices.PhinaStarEfficient;
import lattices.decoder.Babai;
import lattices.decoder.GeneralNearestPointAlgorithm;
import lattices.decoder.SphereDecoder;
import simulator.VectorFunctions;
import simulator.Util;

/**
 * Uses the Babai nearest plane algorithm
 * @author Robby
 */
public class BabaiEstimator implements PolynomialPhaseEstimator {

    protected double[] ya,  p;
    protected int n,  a;
    protected PhinaStarEfficient lattice;
    protected GeneralNearestPointAlgorithm npalgorithm;
    protected Matrix M,  K, ambM;

    //Here for inheritance purposes.  You can't call this.
    protected BabaiEstimator() {
    }
    
    /** 
     * You must set the polynomial order in the constructor
     * @param a = polynomial order
     */
    public BabaiEstimator(int a) {
        lattice = new PhinaStarEfficient(a);
        npalgorithm = new Babai();
        this.a = a;
    }

    @Override
    public void setSize(int n) {
        lattice.setDimension(n - a);
        npalgorithm.setLattice(lattice);

        ya = new double[n];
        p = new double[a];
        this.n = n;

        M = lattice.getMMatrix();
        Matrix Mt = M.transpose();
        K = Mt.times(M).inverse().times(Mt);
        
//        ambM = new Matrix(n, a);
//        for(int i = 0; i < n; i++){
//            for(int j = 0; j < a; j++){
//                ambM.set(i, j, Math.pow(i+1, j)/Util.factorial(j));
//            }
//        }      

    }

    /** 
     *  This is not complete.  I am only returning the parameter of
     *  largest order.  Need to fill the parameter array.
     */
    @Override
    public double[] estimate(double[] real, double[] imag) {
        if (n != real.length) {
            setSize(real.length);
        }
        for (int i = 0; i < real.length; i++) {
            ya[i] = Math.atan2(imag[i], real[i]) / (2 * Math.PI);
        }
        npalgorithm.nearestPoint(ya);
        double[] u = npalgorithm.getIndex();

        double[] ymu = new double[ya.length];
        for (int i = 0; i < u.length; i++) {
            ymu[i] = ya[i] - u[i];
        }
        for (int i = u.length; i < ya.length; i++) {
            ymu[i] = ya[i];
        }

        //compute the parameters
        VectorFunctions.matrixMultVector(K, ymu, p); 
        
        
        //System.out.println("p = " + VectorFunctions.print(p));
        
        //Round the parameters back to
        //allowable ranges.  Care needs to be taken
        //here as the parameters are not independent.
//        for(int i = a-1; i > 0; i--){
//            double val = Math.IEEEremainder(p[i], 1.0/Util.factorial(i));
//            p[i-1] -= p[i] - val;
//            p[i] = val;
//            //p[j] *= 2*Math.PI;
//        }
//        p[0] = Math.IEEEremainder(p[0], 1.0);
        
//        Lattice Klat = new GeneralLattice(ambM);
//        SphereDecoder ambiguityRemover = new SphereDecoder();
//        ambiguityRemover.setLattice(Klat);
//        double[] Mp = new double[n];
//        VectorFunctions.matrixMultVector(M, p, Mp);
//        ambiguityRemover.nearestPoint(Mp);
//        
//        
//        double[] subp = ambiguityRemover.getLatticePoint();
//        //System.out.println("K = " + VectorFunctions.print(K.getMatrix(0, a-1, 0, a-1)));
//        System.out.println("ind = " + VectorFunctions.print(ambiguityRemover.getIndex()));
//        System.out.println("subp = " + VectorFunctions.print(subp));
//        System.out.println("pb = " + VectorFunctions.print(p));
//        for(int i = 0; i < p.length; i++){
//            p[i] -= subp[i];
//        }
//        System.out.println("pa = " + VectorFunctions.print(p));

        return p;
    }
}
