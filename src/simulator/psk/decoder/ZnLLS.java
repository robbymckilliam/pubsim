/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.psk.decoder;

import simulator.Complex;
import simulator.VectorFunctions;

/**
 * This uses the the Phin2StarZnLLS frequency estimator to also estimate the
 * phase and therefore decode PSK.
 * 
 * @author Tim
 */

public class ZnLLS extends simulator.psk.ZnLLS 
        implements PSKReceiver{
    
    double[] argy;
    int T;
    
    public ZnLLS(){
        super();
    }

    public void setT(int T) {
        this.T = T;
        argy = new double[T];
        super.setSize(T);
    }

    /** Implements the ZnLLS decoder using the Phin2StarZnLLS algorithm.
     * @param y the PSK symbols
     * @return the index of the nearest lattice point
     */
    public double[] decode(Complex[] y) {
        if(y.length != T) setT(y.length);
        
        //calculate the argument of of y and scale
        //so that the symbols are given by integers
        //in the range {0,1,...,M-1}
        
        for(int i = 0; i < T; i++){
            //System.out.println(y[i].phase());
            argy[i] = M/(2*Math.PI)*y[i].phase();
            //argy[i] = M*y[i].phase();
        }
        
        //System.out.println();
        //System.out.println(VectorFunctions.print(y));
        //System.out.println("argy = " + VectorFunctions.print(argy));
        
        //estimateCarrier(argy);
        //System.out.println(frequency);
        
        lattice.nearestPoint(argy);
        
        double[] proj = new double[T];
        lattices.Anstar.project(lattice.getIndex(), proj);
        //return proj;
        return lattice.getIndex();
        
    }

    public int bitErrors(double[] x) {
        return Util.differentialEncodedBitErrors(lattice.getIndex(), x, M);
    }

    /** This is a noncoherent reciever so setting the channel does nothing*/
    public void setChannel(Complex h) {  }
    
    public int bitsPerCodeword() {
        return (int)Math.round((T-1)*Math.log(M)/Math.log(2));
    }


}
