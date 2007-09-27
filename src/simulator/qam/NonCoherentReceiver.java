/*
 * NonCoherentReceiver.java
 *
 * Created on 27 September 2007, 13:58
 */

package simulator.qam;

/**
 * Contains functions relavant to most NonCoherentReceivers
 * @author robertm
 */
public class NonCoherentReceiver {
    
    /** 
    * Nearest neighbour algorithm for this form of QAM.
    * See Dan's paper. x input, y output.
    * pre: x.length == y.length.
    */
    public static void NN(double[] x, double[] y){
        for(int i = 0; i < x.length; i++)
            y[i] = 2*Math.round((x[i]+1)/2) - 1;
    }
    
    /**
    * Tests two QAM symbols for equality up to the
    * ambiguity of an pi/2 rotation in phase.
    */
    public static boolean ambiguityEqual(double[] xreal, double[] ximag, 
                                            double[] yreal, double[] yimag){
        boolean ret = true;
        for(int i = 0; i < xreal.length; i++)
            ret = ret && (xreal[i] == yreal[i])&&(ximag[i] == yimag[i]);
        if(ret == true) return true;
        
        ret = true;
        for(int i = 0; i < xreal.length; i++)
            ret = ret && (xreal[i] == -yreal[i])&&(ximag[i] == -yimag[i]);
        if(ret == true) return true;
        
        ret = true;
        for(int i = 0; i < xreal.length; i++)
            ret = ret && (xreal[i] == -yimag[i])&&(ximag[i] == yreal[i]);
        if(ret == true) return true;
        
        ret = true;
        for(int i = 0; i < xreal.length; i++)
            ret = ret && (xreal[i] == yimag[i])&&(ximag[i] == -yreal[i]);
        if(ret == true) return true;
            
        return false;
    }
    
}
