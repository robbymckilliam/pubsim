/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.decoder.banded;

import Jama.Matrix;
import pubsim.lattices.GeneralLattice;
import pubsim.lattices.Lattice;
import pubsim.lattices.util.PointInSphere;
import pubsim.SubMatrix;
import pubsim.VectorFunctions;

/**
 * Decoder for lattices with banded generator matrices.
 * This requires at most only O(N^log(N)) operations,
 * an improvement on the sphere decoder.
 *
 * This class assumes that the generator matrix is
 * upper triangular, square and banded.  Note that
 * any banded matrix can be put in this form by
 * a QR decompostion, so this is general.
 * EXPERIMENTAL
 * @author Robby McKilliam
 */
public class BandedDecoder implements ConstrainedNearestPointAlgorithm{


    protected SubMatrix M;
    protected int band;
    protected double[] u, x, ubest, xbest;
    protected int n, b1n, b2n;

    protected ConstrainedNearestPointAlgorithm b1, b2;
    protected PointInSphere Cpoints;
    Matrix midM, midQ;

    //constraints.
    Double[] c;

    /**
     * Assumes column and row bands are the same length.
     * @param L The lattice to decode
     * @param band The length of the band
     */
    public BandedDecoder(Lattice L, int band){
        init(new SubMatrix(L.getGeneratorMatrix()), band);
    }

    /**
     * Internal constructor that takes a submatrix rather
     * than a lattice.  This is to avoid making multiple copies
     * of matrix elements.
     * @param L Submatrix that is the generator for the lattice
     * @param band The length of the band
     */
    protected BandedDecoder(SubMatrix L, int band){
        init(L, band);
    }

    private void init(SubMatrix L, int band){
        M = L;
        n = M.getColumnDimension();
        this.band = band;
        u = new double[n];
        x = new double[n];
        ubest = new double[n];
        xbest = new double[n];

        //uses the fact that integers division rounds down
        b1n = n/2;
        if(n % 2 == 0)
            b2n = n/2;
        else
            b2n = n/2 + 1;

        //System.out.println(b1n + ", " + b2n);
        //System.out.println(b2n);

         //get the three relavant sublattices matrices.
        SubMatrix sm1 = M.getSubMatrix(0, b1n-1, 0, b1n-1);
        SubMatrix sm2 = M.getSubMatrix(b1n, n-1, b1n, n-1);
        //SubMatrix midM = M.getSubMatrix(n - b2n, b1n-1, n - b2n, b1n-1);

       // System.out.println("****");
        //System.out.println(VectorFunctions.print(M.getJamaMatrix()));
        //System.out.println(VectorFunctions.print(sm1.getJamaMatrix()));
        //System.out.println(VectorFunctions.print(sm2.getJamaMatrix()));

        //if(b1n == band){
            //System.out.println(" b1 is sphere");
            b1 = new ConstrainedSphereDecoder(
                    new GeneralLattice(sm1.getJamaMatrix()));
        //}else{
        //    b1 = new BandedDecoder(sm1, band);
        //}

        //if(b2n == band){
            //System.out.println(" b2 is sphere");
            b2 = new ConstrainedSphereDecoder(
                    new GeneralLattice(sm2.getJamaMatrix()));
        //}else{
        //    b2 = new BandedDecoder(sm2, band);
        //}

        //this is to get the QR'd mid matrix, potentially, this could
        //be done better
        Matrix Mtemp = M.getJamaMatrix().copy();
        VectorFunctions.swapColumns(Mtemp, b1n, n - band + 1 , band-1);
        //System.out.println("Mtemp = \n" + VectorFunctions.print(Mtemp));
        pubsim.QRDecomposition QR = new pubsim.QRDecomposition(Mtemp);
        midM = QR.getR().getMatrix(n - band + 1, n-1, n - band + 1, n-1);
        midQ = QR.getQ();

        //System.out.print(VectorFunctions.print(midM));

        //set no constraints by default
        c = new Double[n];

    }

    public void nearestPoint(double[] y) {
        
        double[] y1 = VectorFunctions.getSubVector(y, 0, b1n-1);
        b1.nearestPoint(y1);
        double d1 = VectorFunctions.distance_between2(y1, b1.getLatticePoint());
        double[] y2 = VectorFunctions.getSubVector(y, n - b2n, n-1);
        b2.nearestPoint(y2);
        double d2 = VectorFunctions.distance_between2(y2, b2.getLatticePoint());

        //current distance
        double D = n*(d1 + d2);

        double[] yt = VectorFunctions.matrixMultVector(midQ.transpose(), y);
        double[] ymid = VectorFunctions.getSubVector(yt, n - band + 1, n-1);
        Cpoints = new PointInSphere(new GeneralLattice(midM),
                Math.sqrt(D), ymid);

        //System.out.println("D = " + D);

        //System.out.println("y.length = " + y1.length + ", b1n = " + b1n);
        //System.out.println(VectorFunctions.print(y1));
        //System.out.println(VectorFunctions.print(sm1.getJamaMatrix()));

        //System.out.println("****");
        //System.out.println(VectorFunctions.print(y));
        //System.out.println(VectorFunctions.print(y1));
        //System.out.println(VectorFunctions.print(y2));
       // System.out.println(VectorFunctions.print(ymid));

        //System.out.println(VectorFunctions.print(b1.getIndex()));
        //System.out.println(VectorFunctions.print(b2.getIndex()));

        while(Cpoints.hasMoreElements()){
            Cpoints.nextElement();
            Double[] Ccs = VectorFunctions.doubleArrayToDoubleArray(
                    Cpoints.getElementIndexDouble());

            Double[] c1 = VectorFunctions.getSubVector(c, 0, b1n-1);
            Matrix tmpM = M.getSubMatrix(
                    b1n - band + 1, b1n-1, b1n, band + b1n - 2).getJamaMatrix();
            double[] tmpc = VectorFunctions.DoubleArrayTodoubleArray(Ccs);
            double[] tmpsub = VectorFunctions.matrixMultVector(tmpM, tmpc);
            double[] ys = new double[y1.length];
            for(int i = 0; i < tmpsub.length; i++){
                ys[ys.length - tmpsub.length + i] 
                        = y1[ys.length - tmpsub.length + i] - tmpsub[i];
            }
            b1.setConstraints(c1);
            b1.nearestPoint(ys, D);

            Double[] c2 = VectorFunctions.getSubVector(c, b1n, n-1);
            VectorFunctions.fillStart(c2, Ccs);
            b2.setConstraints(c2);
            b2.nearestPoint(y2, D);


            VectorFunctions.fillStart(u, b1.getIndex());
            VectorFunctions.fillEnd(u, b2.getIndex());
            VectorFunctions.matrixMultVector(M.getJamaMatrix(), u, x);

            System.out.println("c1 = " + VectorFunctions.print(c1));
            System.out.println("c2 = " + VectorFunctions.print(c2));

            double d = VectorFunctions.distance_between2(y, x);

            System.out.println("u = " + VectorFunctions.print(u));
            System.out.println("d = " + d);

            if(d < D){
                System.arraycopy(u, 0, ubest, 0, n);
                System.arraycopy(x, 0, xbest, 0, n);
                D = d;
                System.out.println("D = " + D);
            }

        }

    }

    public double[] getLatticePoint() {
        return xbest;
    }

    public double[] getIndex() {
        return ubest;
    }

    public void setConstraints(Double[] c) {
        if(n != c.length)
            throw new RuntimeException("The constraints are not the same" +
                    "dimension as the lattice!");
        this.c = c;
    }

    public void nearestPoint(double[] y, double D) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    

}
