/*
 * T3OptimalNonCoherentReciever.java
 *
 * Created on 20 September 2007, 13:34
 */

package pubsim.qam;
import pubsim.VectorFunctions;
import pubsim.Complex;

/**
 * Optimal algorithm proposed by Dan.  Runs in O(T^3) time.
 * Under construction.
 * @author Robby McKilliam
 */
public class T3Optimal extends NonCoherentReceiver implements  QAMReceiver {
    
    protected double[] y1, y2;
    protected double[] x, xopt;
    protected double[] dreal;
    protected double[] dimag;
    
    private double[] xp;
    
    /** Creates a new instance of T3OptimalNonCoherentReciever */
    public T3Optimal() {
    }
    
    /** Set the size of the QAM array */
    @Override
    public void setQAMSize(int M){ this.M = M; }
    
    /** 
    * Set number of QAM signals to use for
    * estimating the channel
    */
    @Override
    public void setT(int T){
        this.T = T;
        
        y1 = new double[2*T];
        y2 = new double[2*T];
        x = new double[2*T];
        xp = new double[2*T];
        xopt = new double[2*T];
        dreal = new double[T];
        dimag = new double[T];
        
    }

    public void setChannel(Complex h) {
        //do nothing.  This is a noncoherent detector.  The channel will
        //be estimated.
    }
    
    /**Decode the QAM signal*/
    @Override
    public void decode(double[] rreal, double[] rimag){
        if( rreal.length != T )
            setT(rreal.length);
        
        createPlane(rreal, rimag, y1, y2);
        
        //Dan's small offset to ensure we translate off a nearest
        //neighbour boundry.
        double e = 0.0000001;
        double Lbest = Double.NEGATIVE_INFINITY;
        
        for(int i = 0; i < 2*T-1; i++){
            for(int j = i+1; j < 2*T; j++){
                for(int k = -M; k <= M; k+=2){
                    for(int n = -M; n <= M; n+=2){
                        
                        //2x2 matrix inversion 
                        double det = y1[i]*y2[j] - y1[j]*y2[i];
                        double a = (y2[j]*k - y2[i]*n)/det;
                        double b = (-y1[j]*k + y1[i]*n)/det;
                        
                        if( a > 0.0 && b > 0.0 ){
                        
                            //run for positive and negative e
                            for(double ve = e; ve >= -1.1*e; ve-=2*e){
                                for(int ii=0; ii < 2*T; ii++)
                                    x[ii] = (a+ve)*y1[ii] + (b+ve)*y2[ii];
                                NN(x,x,M);

                                project(x,xp);
                                double L = VectorFunctions.sum2(xp)/VectorFunctions.sum2(x);
                                //double L = VectorFunctions.angle_between(x,xp);
                                //double L = VectorFunctions.distance_between(x,xp);
                                if(L > Lbest){
                                    Lbest = L;
                                    System.arraycopy(x, 0, xopt, 0, 2*T);
                                }
                            }
                            
                        }
                        
                    }
                }     
            }
        }
        
        //Write the best codeword into real and
        //imaginary vectors
        toRealImag(xopt, dreal, dimag);
        
    }
    
    /**
     * Project x into the plane created by y1 and y2.  Return the
     * value into y.  Uses the fact that y1 and y2 are orthogonal
     * already.
     * Pre: x.length = y.length 
     */
    protected void project(double[] x, double[] y){
        
        double y1tx = 0.0, y1ty1 = 0.0;
        double y2tx = 0.0, y2ty2 = 0.0;
        for(int i = 0; i < x.length; i++){
            y1tx += y1[i]*x[i];
            y1ty1 += y1[i]*y1[i];
            y2tx += y2[i]*x[i];
            y2ty2 += y2[i]*y2[i];
        }
        for(int i = 0; i < x.length; i++)
            y[i] = y1tx/y1ty1 * y1[i] + y2tx/y2ty2 * y2[i];
        
    }
    
    /** 
     * Get the real part of the decoded QAM signal.
     * Call decode first.
     */
    @Override
    public double[] getReal(){ return dreal;}
    
    /** 
     * Get the imaginary part of the decoded QAM signal.
     * Call decode first.
     */
    @Override
    public double[] getImag(){ return dimag;}
    
}
