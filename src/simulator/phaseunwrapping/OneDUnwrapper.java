/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.phaseunwrapping;

import Jama.Matrix;
import simulator.VectorFunctions;

/**
 *
 * @author Robby McKilliam
 */
public class OneDUnwrapper {

    int m, w, N;
    Matrix B;

    protected OneDUnwrapper(){
    }


    public OneDUnwrapper(int approx_order, int approx_size){
        m = approx_order;
        w = approx_size;
    }

    public void setSize(int N){
        this.N = N;

        int num_params = m*(N-w+1);
        Matrix M = new Matrix(num_params, num_params);
        Matrix K = new Matrix(num_params, N);
        Matrix P = new Matrix(N*w, num_params);
        Matrix Y = new Matrix(N*w, N*w);

        //compute K matrix
        for(int p = 0; p < m; p++){
            for(int i = p*(N-w+1); i < (p+1)*(N-w+1); i++){
                for(int j = i - p*(N-w+1); j < i - p*(N-w+1) + w; j++){
                    //System.out.println(i + ", " + j);
                    K.set(i, j, Math.pow(j+1, p));
                }
            }
            //System.out.println(VectorFunctions.print(K));
        }

        //compute M matrix
        for(int p = 0; p < m; p++){
            for(int i = 0; i < (N-w+1); i++){
                int jp = p;
                for(int j = 2*i; j < 2*i + m ; j++){
                    //System.out.println((i + 1) + ", " + j);
                    M.set(i+p*(N-w+1), j, sumMtoNpow(i+1, i+w, jp));
                    jp++;
                }
            }
            //System.out.println(VectorFunctions.print(M));
        }

        for(int i = 0; i < N; i++){
            for( int k = i*w; k < i*(w+1); k++){
                for(int j = i-1; j < (i+1) ; j++){
                    //if(j > -1 && j < (N-w+1))

                }
            }
        }
        //compute the P matrix

        System.out.println(VectorFunctions.print(P));



    }


    /**
     * Return the sum of interger from M to N to the power P ie.
     * M^P + (M+1)^P + ... + N^2
     */
    public static double sumMtoNpow(int M, int N, double P){
        double sum = 0;
        for(int m = M; m <= N; m++){
            sum += Math.pow(m, P);
        }
        return sum;
    }

}
