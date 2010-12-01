/*
 * ColouredNoise.java
 *
 * Created on 4 November 2007, 20:34
 */

package pubsim.distributions.processes;

import Jama.CholeskyDecomposition;
import Jama.Matrix;
import pubsim.distributions.GaussianNoise;
import pubsim.distributions.RandomVariable;
import pubsim.SignalGenerator;
import pubsim.VectorFunctions;

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
        super(mean.length);
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
        super(cor.getColumnDimension());
        noise = new GaussianNoise(0, 1);
        iidsignal = new double[n];
        corsignal = new double[n];
        this.mean = new double[n];
        chol = new CholeskyDecomposition(cor).getL();
        //System.out.println(VectorFunctions.print(chol));
    }

    /** Does nothing. Noise is always Gaussian. */
    @Override
    public void setNoiseGenerator(RandomVariable noise){ }

    /** This just returns GaussianNoise(0,1) */
    @Override
    public RandomVariable getNoiseGenerator(){
        return noise;
    }
    
    @Override
    public double[] generateReceivedSignal(){
        //generate uncoloured noise
        for(int i = 0; i < n; i++)
            iidsignal[i] = noise.getNoise();
        VectorFunctions.matrixMultVector(chol, iidsignal, corsignal);
        for(int i = 0; i < n; i++) corsignal[i] += mean[i];
        
        return corsignal;
    }
    
}
