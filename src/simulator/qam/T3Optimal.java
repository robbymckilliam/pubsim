/*
 * T3OptimalNonCoherentReciever.java
 *
 * Created on 20 September 2007, 13:34
 */

package simulator.qam;
import simulator.VectorFunctions;

/**
 * Optimal algorithm proposed by Dan.  Runs in O(T^3) time.
 * Under construction.
 * @author Robby
 */
public class T3Optimal extends NonCoherentReceiver implements  QAMReceiver {
    
    protected int M;
    protected int T;
    
    protected double[] y1, y2;
    protected double[] v, vbest, vp;
    protected double[] dreal;
    protected double[] dimag;
    
    /** Creates a new instance of T3OptimalNonCoherentReciever */
    public T3Optimal() {
    }
    
    /** Set the size of the QAM array */
    public void setQAMSize(int M){ this.M = M; }
    
    /** 
    * Set number of QAM signals to use for
    * estimating the channel
    */
    public void setT(int T){
        this.T = T;
        
        y1 = new double[2*T];
        y2 = new double[2*T];
        v = new double[2*T];
        vp = new double[2*T];
        vbest = new double[2*T];
        dreal = new double[T];
        dimag = new double[T];
        
    }
    
    /**Decode the QAM signal*/
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
                for(int k = 2; k <= M; k+=2){
                    for(int n = -M; n <= M; n+=2){
                        
                        //2x2 matrix inversion 
                        double det = y1[i]*y2[j] - y1[j]*y2[i];
                        double a = (y2[j]*k - y2[i]*n)/det;
                        double b = (-y1[j]*k + y1[i]*n)/det;
                        
                        //if( a < 0.0 || b < 0.0 ) break;
                        
                        //run for positive and negative e
                        for(double ve = e; ve >= -1.1*e; ve-=2*e){
                            for(int ii=0; ii < 2*T; ii++)
                                v[ii] = (a+ve)*y1[ii] + (b+ve)*y2[ii];
                            NN(v,v);
                            
                            if(inbounds(v,M)){
                                project(v,vp);
                                double L = VectorFunctions.sum2(vp)/VectorFunctions.sum2(v);
                                //double L = VectorFunctions.angle_between(v,vp);
                                //double L = VectorFunctions.distance_between(v,vp);
                                if(L > Lbest){
                                    Lbest = L;
                                    for(int ii=0; ii < 2*T; ii++)
                                        vbest[ii] = v[ii];
                                    //System.out.println("L = " + L*L);
                                    //System.out.println("bv = " + VectorFunctions.print(vbest));
                                }
                            }
                        }  
                        
                    }
                }     
            }
        }
        
        //Write the best codeword into real and
        //imaginary vectors
        toRealImag(vbest, dreal, dimag);
        
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
    public double[] getReal(){ return dreal;}
    
    /** 
     * Get the imaginary part of the decoded QAM signal.
     * Call decode first.
     */
    public double[] getImag(){ return dimag;}
    
}
