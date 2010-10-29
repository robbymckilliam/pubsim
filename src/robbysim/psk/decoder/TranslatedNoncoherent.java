/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.psk.decoder;

import java.util.Arrays;
import robbysim.Complex;
import robbysim.IndexedDouble;
import robbysim.qam.pat.PATSymbol;

/**
 * The receiver for pilot translated PSK.
 * UNDER CONSTRUCTION
 * @author Robby McKilliam
 */
public class TranslatedNoncoherent implements PSKReceiver, PATSymbol{
    
    protected int M, T;
    protected double[] x;
    protected IndexedDouble[] s;

    public void setM(int M) {
        this.M = M;
        setT(T);
    }

    public void setT(int T) {
        this.T = T;
        x = new double[T];
        s = new IndexedDouble[2*M*T];
        for(int i = 0; i < s.length; i++)
            s[i] = new IndexedDouble();
    }

    public double[] decode(Complex[] y) {
        
        //calculate the array of indicies and intersections
        //to be sorted.
        int scount = 0;
        for(int t = 0; t < T; t++){
            for(int k = 0; k < M; k++){
                //solve a quadratic equation to work out the
                //angle that this line intersects with the unit circle
                double tk = Math.tan(Math.PI*2*(k+0.5)/M);
                double a = tk*tk + 1;
                double b = -(2*PAT.re()*tk*tk + 2*PAT.im()*tk);
                double c = tk*tk*PAT.re()*PAT.re() + PAT.im()*PAT.im()
                            - 2*tk*PAT.re()*PAT.im() - 1.0;
                
                //first root
                double re = (Math.sqrt(b*b - 4*a*c)-b)/(2*a);
                double im = tk*(re-PAT.re()) + PAT.im();
                
                s[scount].index = t;
                s[scount].value = Math.atan2(im, re) + Math.PI;
                scount++;
                
                //second root
                re = (-Math.sqrt(b*b - 4*a*c)-b)/(2*a);
                im = tk*(re-PAT.re()) + PAT.im();
                
                s[scount].index = t;
                s[scount].value = Math.atan2(im, re);
                scount++;
            }
        }
        
        Arrays.sort(s);
        
        //calculate the first symbol.  This is when the channel has
        //value -1 + 0i.
        
        
        
        return null;
    }

    /** The PAT symbol used */
    protected Complex PAT;

    public void setPATSymbol(double real, double imag) {
        PAT = new Complex(real, imag);
    }

    public void setPATSymbol(Complex c) {
        PAT = new Complex(c);
    }

    public Complex getPATSymbol() {
        return PAT;
    }

    public int bitErrors(double[] x) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /** This is a noncoherent reciever so setting the channel does nothing*/
    public void setChannel(Complex h) {  }

    public int bitsPerCodeword() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public int symbolErrors(double[] x) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean codewordError(double[] x) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
