/*
 * ColouredNoise.java
 *
 * Created on 4 November 2007, 20:34
 */

package distributions.processes;

import Jama.CholeskyDecomposition;
import Jama.Matrix;
import distributions.GaussianNoise;
import distributions.RandomVariable;
import simulator.SignalGenerator;
import simulator.VectorFunctions;

/**
 * Creates a coloured Guassian noise vector
 * @author Robby
 */
public class ColouredGaussianNoise extends IIDNoise implements SignalGenerator {
    
    protected final double[] corsignal;
    protected final double[] mean;
    protected final Matrix chol;

    /** Initialise this generator with a mean vertor and correlation matrix */
    public ColouredGaussianNoise(double[] mean, Matrix cor){
        n = mean.length;
        if(n != cor.getColumnDimension())
            throw new ArrayIndexOutOfBoundsException("Correlation matrix and mean vector don't match in size");
        noise = new GaussianNoise(0, 1);
        iidsignal = new double[n];
        corsignal = new double[n];
        this.mean = mean;
        chol = new CholeskyDecomposition(cor).getL();
        System.out.println(VectorFunctions.print(chol));
    }

    /** Initialise with zero mean and correlation matrix cor*/
    public ColouredGaussianNoise(Matrix cor){
        n = cor.getColumnDimension();
        noise = new GaussianNoise(0, 1);
        iidsignal = new double[n];
        corsignal = new double[n];
        this.mean = new double[n];
        chol = new CholeskyDecomposition(cor).getL();
        //System.out.println(VectorFunctions.print(chol));
    }

    /** Does nothing. Noise is always Gaussian. */
    public void setNoiseGenerator(RandomVariable noise){ }

    /** This just returns GaussianNoise(0,1) */
    public RandomVariable getNoiseGenerator(){
        return noise;
    }
    
    public double[] generateReceivedSignal(){
        //generate uncoloured noise
        for(int i = 0; i < n; i++)
            iidsignal[i] = noise.getNoise();
        VectorFunctions.matrixMultVector(chol, iidsignal, corsignal);
        for(int i = 0; i < n; i++) corsignal[i] += mean[i];
        
        return corsignal;
    }
    
}
