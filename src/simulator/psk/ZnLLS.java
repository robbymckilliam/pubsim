/*
 * ZnLLSOld.java
 */

package simulator.psk;

import simulator.psk.decoder.Util;
import lattices.Phin2Star.Phin2Star;
import lattices.Phin2Star.Phin2StarZnLLSOld;

/**
 *
 * @author Tim
 */
public class ZnLLS implements CarrierEstimator{
    
    protected int M;
    protected int N;
    protected double[] marg;
    protected double symF;
    
    protected double fmin, fmax;
    protected double phase, frequency;
    
    protected Phin2Star lattice;
    protected simulator.fes.ZnLLSOld fes;
    
    public ZnLLS(){
        // Force fmin and fmax for the lattice -- this needs refactoring.
        lattice = new Phin2StarZnLLSOld(-0.5, 0.5);
        fes = new simulator.fes.ZnLLSOld();
    }
    
    /** Return the estimated phase */
    public double getPhase(){
        return phase;
    }
    
    /** Return the estiamted freqenucy */
    public double getFreqency(){
        return frequency;
    }
    
    /** Set the maximum allowed frequency */
    public void setFmin(double fmin) {
        this.fmin = fmin;
    }
    
    /** 
     * Set the minimum allowed frequency 
     * Set this to M*fmin
     */
    public void setFmax(double fmax){
        this.fmax = fmax;
    }
    
    /** Set the number of samples */
    public void setSize(int n){
        lattice.setDimension(n-2);  
        fes.setSize(n);
        marg = new double[n];
        N = n;
    }
    
    /** Set to M-ary QPSK */
    public void setM(int M){
        this.M = M;
    }
    
    public void setSymbolRate(double symF) {
        this.symF = symF;
    }
    
    public void estimateCarrier(double[] real, double[] imag) {
        double[] arg = new double[real.length];
        for (int i = 0; i < real.length; i++) {
            arg[i] = Math.atan2(imag[i], real[i])/(2*Math.PI);
        }
        
        estimateCarrier(arg);
    }

    public void estimateCarrier(double[] arg){
        
        /*
        if(n+2 != arg.length)
            setSize(arg.length);
        
        for(int i = 0; i < arg.length; i++) {
            marg[i] = arg[i];
        }
        */
        /*
        System.out.println("Looking at " + (int)Math.floor(1/symF) + " samples");
        
        setSize((int)Math.floor(1/symF));
        
 
        for(int i = 0; i < Math.floor(1/symF); i++) {
            marg[i] = arg[i];
        }
        
        System.out.println("Trying to learn from: " + VectorFunctions.print(marg));
        
        frequency = fes.estimateFreq(marg);
        */
        for(int i = 0; i < arg.length; i++) {
            marg[i] = M*arg[i];
        }
        
        // This has problems when |M * the real frequency| > 0.5, since the
        // frequency estimator tries to return a value between -0.5 and 0.5
        // This might be eliminated if the underlying lattice searches only
        // between fmin = -0.5 and fmax = 0.5 -- check this
        frequency = fes.estimateFreq(marg)/M;
        
        System.out.println(frequency);
        
        /*
        lattice.nearestPoint(marg);
        
        double[] u = lattice.getIndex();
        
        double f = 0;
        double sumn = N*(N+1)/2;
        double sumn2 = N*(N+1)*(2*N+1)/6;
        for(int i = 0; i < N; i++) {
            f += (N*(i+1) - sumn)*(marg[i]-u[i]);
        }
        f = f/(M*(sumn2*N - sumn*sumn));
        
        System.out.println("returned: " + VectorFunctions.print(u));
        System.out.println("carrier freq est: " + f);
        
        frequency = f;
        */
    }
    
    public double[] decode(double[] real, double[] imag) {
        double[] arg = new double[real.length];
        for (int i = 0; i < real.length; i++) {
            arg[i] = Math.atan2(imag[i], real[i])/(2*Math.PI);
        }
        
        return decode(arg);
    }
    
    public double[] decode(double[] arg) {
        int numSymbols = (int)Math.ceil(arg.length * symF);
        double[] decoded = new double[numSymbols];
        double[] first = new double[numSymbols];
        double[] last = new double[numSymbols];
        double[] diff = new double[numSymbols];
        
        int offset = 0;
        
        //System.out.println(numSymbols + " symbols to check");
        
        for (int i = 0; i < numSymbols; i++) {
            //System.out.println("i: " + i);
            int length = (int)Math.floor((i+1) / symF) - offset;
            if (offset + length > arg.length) {
                length = arg.length - offset;
            }
            //System.out.println(length + " samples for this symbol");
            
            double[] marg = new double[length];
            setSize(length);
            for (int j = 0; j < length; j++) {
                marg[j] = arg[offset + j];
            }
            
            first[i] = fes.estimatePhase(marg);
            frequency = fes.getFrequency();
            last[i] = first[i] + frequency*length;
            
            if (i > 0) {
                // Find the phase shift across the symbol boundary
                diff[i] = first[i] - last[i - 1];
                // Put it in the range 0 - 1
                diff[i] -= Math.round(diff[i]);
                // Find how the symbol has changed
                decoded[i] = Util.mod(
                                        (int)(decoded[i-1] + Math.round(diff[i] * M)),
                                         M
                                     );
                
            } else {
                // Get the fractional component of the phase in the range 0 - 1
                diff[i] = first[i] - Math.round(first[i]);
                // Find which symbol this implies
                decoded[i] = Util.mod(
                                (int)Math.round((diff[i] - 1/(double)M) * M),
                                M
                             );
                             
            }
            
            offset += length;
        }
        /*
        System.out.println("First entries are: " + VectorFunctions.print(first));
        System.out.println("Last entries are: " + VectorFunctions.print(last));
        System.out.println("Diff entries are: " + VectorFunctions.print(diff));
        */
        //System.out.println("Decoded: " + VectorFunctions.print(decoded));
        
        return decoded;
    }

}
