/*
 * NonCoherentReceiver.java
 *
 * Created on 27 September 2007, 13:58
 */

package simulator.qam;

import simulator.VectorFunctions;

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
    protected static void NN(double[] x, double[] y){
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
    
    /**
     * Test two vectors for equality up to the
     * ambiguity of an pi/2 rotation in phase.
     * This assumes the vectors are in the format
     * of Dan's underline operator.
     */
    public static boolean ambiguityEqual(double[] x, double[] y ){
        
        boolean ret = true;
        for(int i = 0; i < x.length; i++)
            ret = ret && (x[i] == y[i]);
        if(ret == true) return true;
        
        ret = true;
        for(int i = 0; i < x.length; i++)
            ret = ret && (x[i] == -y[i]);
        if(ret == true) return true;
        
        ret = true;
        for(int i = 0; i < x.length/2; i++)
            ret = ret && (x[2*i] == -y[2*i+1])&&(x[2*i+1] == y[2*i]);
        if(ret == true) return true;
        
        ret = true;
        for(int i = 0; i < x.length/2; i++)
            ret = ret && (x[2*i] == y[2*i+1])&&(x[2*i+1] == -y[2*i]);
        if(ret == true) return true;
        
        return false;
    }
    
    /** Returns true if v is within boundry of an M-ary QAM symbol */
    protected static boolean inbounds(double[] v, int M){
        return VectorFunctions.max(v) < M && VectorFunctions.min(v) > -M;
    }
    
    /**
     * Create two vectors y1 and y2 that form the search plane
     * for QAM receivers.  This is detailed in Dan's paper.
     * PRE: y1.length == y2.length == 2*real.length
     */
    protected static void createPlane(double[] real, double[] imag,
            double[] y1, double[] y2){
        for(int i = 0; i < real.length; i++){
            y1[2*i] = real[i];
            y1[2*i+1] = imag[i];
            y2[2*i] = -imag[i];
            y2[2*i+1] = real[i];
        }
    }
    
    /**
     * Goes from a vector in Dan's underline notation to
     * two separate vector's containing real and imaginary
     * parts.
     * PRE: y.length == 2*real.length == 2*imag.length
     */
    protected static void toRealImag(double[] y, double[] real, double[] imag){
        for(int i = 0; i < real.length; i++){
            real[i] = y[2*i];
            imag[i] = y[2*i + 1];
        }
    }
    
}
