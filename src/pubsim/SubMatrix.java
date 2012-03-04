/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim;

import java.io.Serializable;

/**
 * Transparent holder for sub matrix of a matrix.  It would
 * be nice if this could extend Jama.Matrix, but Jama.Matrix
 * is not written in a particularly extensible manner.  It would
 * be necessary to extend and rewrite almost every method.
 * @author Robby McKilliam
 */
public class SubMatrix  implements Serializable{


    /**Offsets if this is a submatrix*/
    protected int offm, offn, lastm, lastn;

    /** This is the underlying matrix */
    protected Jama.Matrix M;

    public SubMatrix(Jama.Matrix M){
        this.M = M;
        offm = 0; offn = 0;
        lastm = M.getRowDimension()-1;
        lastn = M.getColumnDimension()-1;
    }

    protected SubMatrix(Jama.Matrix M, int offm, int lastm, int offn, int lastn){
        //System.out.println(offm + ", " + lastm + ", " + offn + ", " + lastn);
        this.offm = offm;
        this.offn = offn;
        this.lastm = lastm;
        this.lastn = lastn;
        this.M = M;
    }

    public double get(int i , int j){
        if(i > (lastm - offm) || j > (lastn - offn))
            throw new ArrayIndexOutOfBoundsException("get indicies");
        return M.get(offm + i, offn + j);
    }

    public SubMatrix getSubMatrix(int offm, int lastm, int offn, int lastn){
        if(lastm > getRowDimension()-1 || lastn > getColumnDimension()-1
                || offn < 0 || offm < 0)
            throw new ArrayIndexOutOfBoundsException("get indicies");
        return new SubMatrix(M, offm + this.offm, lastm + this.offm,
                offn + this.offn, lastn + this.offn);
    }

    public Jama.Matrix getJamaMatrix(){
        return M.getMatrix(offm, lastm, offn, lastn);
    }

    public int getRowDimension(){
        return lastm - offm + 1;
    }

    public int getColumnDimension(){
        return lastn - offn + 1;
    }


}
