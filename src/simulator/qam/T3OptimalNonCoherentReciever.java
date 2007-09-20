/*
 * T3OptimalNonCoherentReciever.java
 *
 * Created on 20 September 2007, 13:34
 */

package simulator.qam;

/**
 * Optimal algorithm proposed by Dan.  Runs in O(T^3) time.
 * Under construction.
 * @author Robby
 */
public class T3OptimalNonCoherentReciever implements  QAMReceiver {
    
    protected int M;
    protected int T;
    
    protected double[] y1, y2;
    protected double[] v, vbest;
    protected double[] dreal;
    protected double[] dimag;
    
    /** Creates a new instance of T3OptimalNonCoherentReciever */
    public T3OptimalNonCoherentReciever() {
    }
    
    /** Set the size of the QAM array */
    public void setQAMSize(int M) { this.M = M;}
    
    /** 
    * Set number of QAM signals to use for
    * estimating the channel
    */
    public void setT(int T){
        this.T = T;
        
        y1 = new double[2*T];
        y2 = new double[2*T];
        v = new double[2*T];
        vbest = new double[2*T];
        dreal = new double[T];
        dimag = new double[T];
        
    }
    
    /**Decode the QAM signal*/
    public void decode(double[] rreal, double[] rimag){
        
    }
    
    /** 
     * Get the real part of the decoded QAM signal.
     * Call decode first.
     */
    public double[] getReal(){ return dreal;}
    
    /** 
     * Get the imaginary part of the decoded QAM signal.
     * Call decode first.
     */
    public double[] getImag(){ return dimag;}
    
}
