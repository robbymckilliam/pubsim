/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.fes.crb;

import flanagan.integration.Integration;
import java.util.Vector;

/**
 * Calculates Barankin type bounds for fes.
 * @author Robby McKilliam
 */
public class Barankin extends Hamersley implements BoundCalculator {

    Vector<TestPoint> testPoints;
    
    /** Constructor.  The amplitude defaults to 1. */
    public Barankin(){
        setAmplitude(1.0);
    }
    
    /** 
     * Add a freqency and phase value as a test point to the
     * Barankin bound.
     */
    public void addTestPoint(double f, double t){
        testPoints.add(new TestPoint(f, t));
    }

    /** 
     * Adds test points that have rational frequency.  These
     * are conjectured to be the best test points to use.
     */
    public void setNumberOfTestPoints(int t){
        testPoints.clear();
        for(int i = 0; i < t; i++){
            
        }
    }
    
    @Override
    public double getBound() {
        
        double dets = N/(ntn*N - nt1*nt1);
        double v = amplitude/Math.sqrt(var);
        
        dPoverP func = new dPoverP();
        func.setv(v);
        
        Integration intg = new Integration(func, -0.5, 0.5);
        
        return dets/intg.gaussQuad(1000);
    }
    
    /** Test point for the Barankin bound */
    protected class TestPoint{
        public double f, t;
        public TestPoint(double f, double t){
            this.f = f;
            this.t = t;
        }
    }

}
