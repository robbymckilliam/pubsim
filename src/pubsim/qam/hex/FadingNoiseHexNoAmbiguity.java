/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.hex;

import static pubsim.VectorFunctions.allElementsEqual;

/**
 * Fading noisy hex signal with |C|-1 symbols like [a,a,a,a...] removed so
 * that there are no ambiguities.
 * @author Robby McKilliam
 */
public class FadingNoiseHexNoAmbiguity extends FadingNoisyHex{

    protected double[] minSymbol;

    /**
     * @param M Constellation size
     * @param N Block length
     */
    public FadingNoiseHexNoAmbiguity(int N, int M) {
        super(N,M);
        minSymbol = hex.minimumEnergyCodeword();
    }

    /**
     * Generate a random transmittable codeword.  Will regenerate if this
     * codeword if it is ambiguous.
     */
    @Override
    public void generateCodeword(){
        for(int i = 0; i < xr.length; i++){
            xr[i] = random.nextInt(M);
            xi[i] = random.nextInt(M);
        }
        //regenerate if this codeword is ambiguous.
        if(allElementsEqual(xr) && allElementsEqual(xi)
                && xr[0] != minSymbol[0] && xi[0] != minSymbol[1])
            generateCodeword();   
    }

}
