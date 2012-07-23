/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.Vnm.codec;

import Jama.Matrix;
import pubsim.lattices.Vnm.Vnm;

/**
 * Class for encoding and decoding the Vnm lattice based codes.  See the paper
 * R. G McKilliam, "Convolutional lattice codes and the Prouhet-Tarry-Escott problem"
 * @author Robby McKilliam
 */
public class VnmCodec {
    
    final protected int n, m, N;
    
    public VnmCodec(int n, int m){
        this.n = n;
        this.m = m;
        this.N = n + m + 1;
    }
    
    /** 
     * Return the shaping loss with respect to the hypercube for this code.  Value returned in dB.
     */
    public static double shapingLoss(int n, int m){
        Vnm lattice = new Vnm(m,n);
        Matrix G = lattice.getGeneratorMatrix();
        Matrix R = new  Jama.QRDecomposition(G).getR();
        double scale = Math.pow(lattice.volume(),1.0/n);
        double secmom = 0.0;
        for(int i = 0; i < n; i++){
            double d = R.get(i,i)/2.0/scale;
            secmom +=d*d;
        }
        secmom /= n;
        double hypercubesecmom = 1.0/12.0;
        return 10.0 * Math.log10(secmom/hypercubesecmom);
    }
    
}
