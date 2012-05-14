/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.hex;

import static pubsim.VectorFunctions.allElementsEqual;

/**
 * Radial Lines receiver that removes ambiguous codewords.
 * @author Robby McKilliam
 */
public class RadialLinesNoAmbiguity extends RadialLinesReciever{

    private final double[] minSymbol;

    public RadialLinesNoAmbiguity(int N, int scale){
        super(N, scale);
        minSymbol = hex.minimumEnergyCodeword();
    }

    @Override
    public double[] getReal() {
        resolveAmbiguity();
        return ur;
    }

    @Override
    public double[] getImag() {
        resolveAmbiguity();
        return ui;
    }

    private void resolveAmbiguity() {
        if(isAmbiguous(ur, ui)){
            for (int n = 0; n < N; n++) {
                ur[n] = minSymbol[0];
                ui[n] = minSymbol[1];
            }
        }
    }

    private boolean isAmbiguous(double[] ur, double[] ui){
        if(ur[0] == minSymbol[0] && ui[0] == minSymbol[1]) return false;
        if (!allElementsEqual(ur) || !allElementsEqual(ui)) return false;
        return true;
    }

}
