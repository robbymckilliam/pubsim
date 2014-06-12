package pubsim.distributions;

import Jama.Matrix;

/**
 * Class describes the multivariate normal distribution
 * @author Robby McKilliam
 */
public class MultivariateNormal implements GenericRandomVariable<Matrix> {
    
    protected final Matrix mean, cov, invcov;
    protected final double coeff;

    public MultivariateNormal(Matrix mean, Matrix cov) {
        this.mean = mean;
        this.cov = cov;
        invcov = cov.inverse();
        int k = cov.getColumnDimension();
        coeff = Math.pow(2*Math.PI,-k/2.0)/Math.sqrt(cov.det());
    }
    
    public double pdf(Matrix x){
        Matrix y = x.minus(mean);
        double ySy = (y.transpose().times(invcov).times(y)).get(0,0);
        //System.out.println(ySy);
        return coeff*Math.exp(-ySy/2.0);
    }

    @Override
    public Matrix mean() {
        return mean;
    }

    /** Returns the covariance matrix */
    @Override
    public Matrix variance() {
        return cov;
    }

    @Override
    public double cdf(Matrix x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Matrix icdf(double x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Matrix noise() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void randomSeed() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSeed(long seed) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
