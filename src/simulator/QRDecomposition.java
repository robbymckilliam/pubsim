/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator;

import Jama.Matrix;

/**
 * Wrapper for jama.QRDecomposition that ensures the
 * diagonal entries of the R matrix are positive.
 * @author Robby McKilliam
 */
public class QRDecomposition {

    Matrix R, Q;
    
    
   public QRDecomposition(Matrix B){
       
       int m = B.getRowDimension();
       int n = B.getColumnDimension();
       
       Jama.QRDecomposition QR = new Jama.QRDecomposition(B);
       Matrix Rd = QR.getR();
       Matrix Id = Matrix.identity(n, n);
       
       //construct a matrix to correct for negative diagonal entries
       for(int i = 0; i < n; i ++){
           if(Rd.get(i,i) < 0){
               Id.set(i,i, -1.0);
           }
       }
       
       R = Id.times(Rd);
       Q = QR.getQ().times(Id);
       
   }
   
   public Matrix getR(){
       return R;
   }
    
   public Matrix getQ(){
       return Q;
   }
       
}
