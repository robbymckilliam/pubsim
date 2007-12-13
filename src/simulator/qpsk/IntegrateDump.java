/*
 * IntegrateDump.java
 *
 * Created on 13 December 2007, 14:57
 */

package simulator.qpsk;

/**
 *
 * @author Robby
 */
public class IntegrateDump {
    
    /**
     * Sum of the last symbol of 
     * the previous block
     */
    protected double prev_sr, prev_si;
            
    protected double f,p;
    
    protected int M;
    
    protected QPSKSignal.SymbolGenerator slist;
    
    public IntegrateDump(){
        slist = new QPSKSignal.SymbolGenerator();
    }
            
    /** Set the symbol phase for the current block */
    public void setPhase(double p){ this.p = p; }
    
    /** Set the frequency for the current block */
    public void setFrequency(double f){ this.f = f; }
    
    public void setM(int M){ this.M = M; }
    
    /** 
     * Takes the inphase and quadrature components and averages
     * them over each symbol time.  The output symbol is then the
     * argument of the average.
     */
    public void calculateSymbols(double[] real, double[] imag){
        int n = real.length;
        
        double nexts = p;
        double sr = prev_sr;
        double si = prev_si;
        for(int i = 0; i < n; i++){
            if(i < nexts){
                sr += real[i];
                si += imag[i];
            }else{
                double arg = Math.atan2(si, sr)/(2*Math.PI);
                int sym = (int)Math.round(M*(arg + 0.5))%M;
                slist.addSymbol(sym);
                sr = 0;
                si = 0;
                nexts += 1.0/f;
            }
        }
        prev_sr = sr;
        prev_si = si;
    }
    
}
