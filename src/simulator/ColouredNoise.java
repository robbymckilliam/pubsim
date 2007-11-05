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
public class ColouredNoise extends NoiseVector implements SignalGenerator {
    
    protected double[] iidsignal, corsignal;
    protected double[][] cor;
    protected NoiseGenerator noise;
    
    public void setCorrelationMatrix(double[][] cor){
        n = cor.length;
        this.cor = cor;
    }
    
    public void setNoiseGenerator(NoiseGenerator noise){
        this.noise = noise;
    }
    
    public NoiseGenerator getNoiseGenerator(){
        return noise;
    }
    
    public double[] generateReceivedSignal(){
        //generate uncoloured noise
        if(iidsignal.length != cor[0].length)
            iidsignal = new double[cor[0].length];
        for(int i = 0; i < iidsignal.length; i++)
            iidsignal[i] = noise.getNoise();
        
        //colour the noise
        if(corsignal.length != cor.length)
            corsignal = new double[cor.length];
        VectorFunctions.matrixMultVector(cor, iidsignal, corsignal);
        return corsignal;
    }
    
}
