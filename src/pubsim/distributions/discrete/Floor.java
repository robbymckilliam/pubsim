package pubsim.distributions.discrete;

import flanagan.integration.Integration;
import pubsim.distributions.RealRandomVariable;

/**
 * A discrete random variable construct by taking the floor (the greater integer less
 * than) a random variable on the real line.
 * @author Robby McKilliam
 */
public class Floor extends AbstractDiscreteRandomVariable {
    
    protected final RealRandomVariable rrv;
    
    public Floor(RealRandomVariable rv){
        rrv = rv;
    }

    @Override
    public double mean() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double variance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double pmf(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double cmf(Integer k) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer icmf(double x) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer noise() {
        return (int)Math.floor(rrv.noise());
    }
    
    
}
