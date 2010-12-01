/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.quantisedFTs;

import pubsim.Complex;

/**
 *
 * @author Robby McKilliam
 */
public class Util {

    /**
     * Return the N by N discrete Fourier transform matrix.
     */
    public static Complex[][] DFTMatrix(int N) {
        Complex[][] D = new Complex[N][N];
        for(int n = 0; n < N; n++){
            for(int m = 0; m < N; m++){
                D[n][m] = new Complex(Math.cos((2*Math.PI*n*m)/N),
                        Math.sin((2*Math.PI*n*m)/N));
            }
        }
        return D;
    }

    /**
     * Return the N by N quantised discrete Fourier transform matrix
     * with quantisation factor M.  The levels in the
     * quantiser have size 1/M
     */
    public static Complex[][] quantisedDFTMatrix(int N, int M) {
        Complex[][] D = new Complex[N][N];
        for(int n = 0; n < N; n++){
            for(int m = 0; m < N; m++){
                D[n][m] = new Complex(
                        Math.rint(M * Math.cos((2*Math.PI*n*m)/N)) / M,
                        Math.rint(M * Math.sin((2*Math.PI*n*m)/N)) / M );
            }
        }
        return D;
    }

    /**
     * Generate a circulant LTI Channel matrix from the channel impulse
     * response h.
     */
    public static Complex[][] circulantChannelMatrix(Complex[] h) {
        int N = h.length;
        Complex[][] H = new Complex[N][N];
        for(int n = 0; n < N; n++){
            for(int m = 0; m < N; m++){
                H[n][(m+n)%N] = new Complex(h[m]);
            }
        }
        return H;
    }

}
