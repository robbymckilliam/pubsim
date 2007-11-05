/*
 * ColouredNoise.java
 *
 * Created on 4 November 2007, 20:34
 */

package simulator;

/**
 * Creates a coloured noise sequence.  Use setCorrelationMatrix to
 * set the correlation matrix.  The matrix is just a double[][].
 * I have refrained from using a linear alebra library as you loose
 * track of how memory is allocated and currently the only thing I
 * need is matrix multiplication.  If matrix decompositions are
 * needed in future it will be a good idea to use a library.
 * The JAMA library looks decent.
 * @author Robby
 */
public class ColouredNoise implements SignalGenerator {
    
    protected double[] iidsignal, corsignal;
    protected double[][] cor;
    protected NoiseGenerator noise;
    
    public void setCorrelationMatrix(double[][] cor){
        this.cor = cor;
    }
    
    public void setNoiseGenerator(NoiseGenerator noise){
        this.noise = noise;
    }
    
    public NoiseGenerator getNoiseGenerator(){
        return noise;
    }
    
    /** 
     * Generate the iid noise of length n.
     * The correlation matrix must have n columns.
     */
    public double[] generateIIDNoise(int n){
        if( iidsignal.length != n )
            iidsignal = new double[n];
        for(int i = 0; i < n; i++)
            iidsignal[i] = noise.getNoise();
        return iidsignal;
    }
    
    public double[] generateReceivedSignal(){
        if(corsignal.length != cor[0].length)
            corsignal = new double[cor[0].length];
        VectorFunctions.matrixMultVector(corsignal, cor, iidsignal);
        return corsignal;
    }
    
}
