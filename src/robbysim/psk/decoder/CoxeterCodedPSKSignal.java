/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.psk.decoder;

/**
 * Generates signals that sum to 0 mod(kM) where k is an integer 
 * will be a multiple of M (ie. M-PSK).  This is the requirement 
 * for the Coxeter code.
 * 
 * @author Robby McKilliam
 */
public class CoxeterCodedPSKSignal extends PSKSignal{

    int k;
    
    private void setCodeMultiple(int k){
        this.k = k;
    }
    
    public CoxeterCodedPSKSignal(){
        setM(4);
        setCodeMultiple(1);
    }
    
    public CoxeterCodedPSKSignal(int M){
        setM(M);
        setCodeMultiple(1);
    }
    
    public CoxeterCodedPSKSignal(int M, int k){
        setM(M);
        setCodeMultiple(k);
    }
    
    /**
     * Generate a random PSK signal of the currently
     * specified length.  Make sure it is 0 mod(kM)
     */
    @Override
    public void generatePSKSignal(){
        int sum = 0;
        int mod = k*M - k + 1;
        for(int i=0; i < T-k; i++){
            x[i] = random.nextInt(M);
            sum += x[i];
        }
        int toadd = Util.mod(mod - sum, mod);
        //System.out.println("sum = " + sum);
        //System.out.println("toadd = " + toadd);
        for(int i = T-k; i < T; i++ ){
            int sym = Math.min(M-1, toadd);
            x[i] = sym;
            toadd -= sym;
        }
        //System.out.println("toadd = " + toadd);
    }
    
}
