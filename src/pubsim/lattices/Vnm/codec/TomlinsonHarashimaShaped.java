/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.Vnm.codec;

import pubsim.lattices.Vnm.codec.generators.UpperTriangularGenerator;

/**
 * Basic Tomlinson Harashima shaped encoder.  This has very poor shaping if the dimension is not
 * large enough.  Decoder is a standard trellis that incorporates the shaping.
 * @author Robby McKilliam
 */
public class TomlinsonHarashimaShaped extends VnmCodec {
    
    final double[] x;
    final int[] u, k;
    final int M;
    UpperTriangularGenerator R;
    
    /** 
     * @param n code length/lattice dimension
     * @param m Vnm lattice parameter/filter order
     * @param M size of symbol alphabet, i.e. M=2 would mean a rate 1 code.
     */
    public TomlinsonHarashimaShaped(int n, int m, int M){
        super(n,m);
        this.M = M;
        x = new double[n];
        u = new int[n];
        k = new int[n];
        R = new UpperTriangularGenerator(n, m); //get a generator for our lattice
    }

    /**
     * Encodes and shapes the transmitted symbols u.
     * Currently runs in O(N^2) but can easily be made O(N).
     */
    @Override
    public double[] encode(int[] u) {
        for(int i = 0; i < n; i++) k[i] = 0; //clear working memory
        for( int i = n-1; i >= 0; i-- ){
            double sum = 0.0;
            for( int j = n-1; j>=i; j-- ){
                sum += R.get(i,j) * (u[j] + k[j]);
            }
            k[i] = (int)Math.round(-sum/M/R.get(i,i));
            x[i] = sum + k[i]*M*R.get(i,i);
        }
        return x;
    }

    @Override
    public int[] decode(double[] y) {
        throw new UnsupportedOperationException("Not supported yet.");
        //return u;
    }
    
    
    
}
