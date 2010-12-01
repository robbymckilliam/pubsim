/*
 * NcPAMBrute.java
 *
 * Created on 18 January 2008, 15:03
 */

package pubsim.qam.crb;

import pubsim.VectorFunctions;

/**
 * Computes the Hammersly-Chapman-Robins bound
 * by brute force enumeration over the codewords.
 * This gives a tight bound for all values of variance (snr). 
 * @author Robby McKilliam
 */
public class NcPAMBrute extends NoncoherentPAM {
     
    public double getCRB(){
        double p = h/var;
        AllPAMSymbolGenerator gen = new AllPAMSymbolGenerator();
        double bestcrb = Double.NEGATIVE_INFINITY;
        double[] d = new double[T];
        for(int i = 0; i < Math.pow(M, T); i++){
            double[] nextsym = gen.getNext();
            //System.out.println(nextsym.length);
            for(int j = 0; j < T; j++){
                d[j] = x[j] - nextsym[j];
            }
            double magd2 = VectorFunctions.sum2(d);
            if(magd2 > 0.5){
                double std = VectorFunctions.dot(x, d);
                double crb = var/(magx2 - p*std*std/Math.expm1(p*magd2));
                if(crb > bestcrb)
                    bestcrb = crb;
            }
        }
        return bestcrb;
    }
   
    protected class AllPAMSymbolGenerator{
        
        private double[] sym;
        private double[] ret;
        
        public AllPAMSymbolGenerator(){
            sym = new double[T];
            ret = new double[T];
        }
        
        public double[] getNext(){
            addto(0);
            for(int i=0; i < T; i++)
                ret[i] = 2*sym[i] - M + 1;
            return ret;
        }
        
        public void addto(int i){
            if(sym[i] == M-1){
                sym[i] = 0;
                if(i < T-1)
                    addto(i+1);
            }else{
                sym[i]++;
            }
        }
 
    }
    
    
}
