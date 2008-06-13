/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.psk.decoder;

/**
 * Generates signals that sum to 0 mod(kM) where k is an integer 
 * will be a multiple of M (ie. M-PSK).  This is the requirement 
 * for the Coxeter code.
 * 
 * I am testing this initially with k=1
 * 
 * @author Robby McKilliam
 */
public class CoxeterCodedPSKSignal extends PSKSignal{

//    int k;
//    
//    public void setCodeMultiple(int k){
//        this.k = k;
//    }
//    
    /**
     * Generate a random PSK signal of the currently
     * specified length.  Make sure it is 0 mod(M)
     */
    @Override
    public void generatePSKSignal(){
        int sum = 0;
        for(int i=0; i < T-1; i++){
            x[i] = random.nextInt(M);
            sum += x[i];
        }
        x[T-1] = Util.mod(M - sum, M);
    }
    
}
