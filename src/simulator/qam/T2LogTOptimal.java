/*
 * T2LogTOptimalNonCoherentReciever.java
 *
 * Created on 17 September 2007, 14:10
 */

package simulator.qam;

import java.util.*;
import java.util.TreeMap;
import simulator.VectorFunctions;

/**
 * The faster O(T^2 log(T)) GLRT-optimal non-coherent QAM
 * receiver. 
 * @author Robby
 */
public class T2LogTOptimal extends NonCoherentReceiver implements  QAMReceiver {
    
    protected int M;
    protected int T;
    
    protected double[] y1, y2;
    protected double[] v, vbest;
    protected double[] c;
    protected double[] d;
    protected double[] dreal;
    protected double[] dimag;
    
    protected TreeMap map;
    
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
        vbest = new double[2*T];
        c = new double[2*T];
        d = new double[2*T];
        
        dreal = new double[T];
        dimag = new double[T];
        
        map = new TreeMap();
    }
    
    /**Decode the QAM signal*/
    public void decode(double[] rreal, double[] rimag){
        if( rreal.length != T )
            setT(rreal.length);
        
        createPlane(rreal, rimag, y1, y2);
     
        double Lbest = Double.NEGATIVE_INFINITY;
        //for each type of line
        for(int i = 0; i < 2*T; i++){
            
            //only use lines with negative gradient
            //this removes half the ambiguities.
            //we could alternatively take only lines
            //of positive gradient.
            if(Math.signum(y1[i])!=Math.signum(y2[i])) {
            
                //for the parallel lines of this type
                //only use the non-negative lines, this
                //removes half the ambiguities.
                for(int k = 0; k <= M-2; k+=2){

                    //calculate parameters for the 
                    //line we are searching.  d can be
                    //calculated outside of k loop but
                    //it's neater to have it here
                    for(int j = 0; j < 2*T; j++){
                        c[j] = k*y2[j]/y2[i];
                        d[j] = y1[j] - y1[i]*y2[j]/y2[i];
                    }

                    //calculate the start point
                    //for the line search
                    double bmin = Double.POSITIVE_INFINITY;
                    int minT = 0;
                    for(int j = 0; j < 2*T; j++){
                        if(j!=i && d[j] != 0.0){
                            double bpos = (M - c[j])/d[j];
                            double bneg = (-M - c[j])/d[j];
                            double thismin = Math.min(bpos, bneg);
                            if(thismin < bmin){
                                minT = j;
                                bmin = thismin;
                            }
                        }
                    }

                    //setup start point
                    for(int j = 0; j < 2*T; j++)
                            v[j] = bmin*d[j]+c[j];
                    NN(v,v,M);
                    v[i] = k + 1;
                    v[minT] = -Math.signum(d[minT])*(M - 1);

                    //setup sorted map
                    map.clear();
                    for(int j = 0; j < 2*T; j++){
                        if(i!=j && d[j] != 0.0)
                            map.put(new Double((v[j]+Math.signum(d[j])-c[j])/d[j]),
                                    new Integer(j));
                    }

                    //setup dot product variables for updating 
                    //likelihood in constant time.
                    double y1tv = 0.0, y2tv = 0.0, y1ty2 = 0.0, vtv = 0.0,
                            y1tvn = 0.0, y2tvn = 0.0, vtvn = 0.0,
                            y1ty1 = 0.0, y2ty2 = 0.0;
                    for(int j = 0; j < 2*T; j++){
                        y1tv += y1[j]*v[j];
                        y2tv += y2[j]*v[j];
                        y1ty2 += y1[j]*y2[j];
                        vtv += v[j]*v[j];
                        y1ty1 += y1[j]*y1[j];
                        y2ty2 += y2[j]*y2[j];
                    }
                    y1tvn = y1tv - 2*y1[i];
                    y2tvn = y2tv - 2*y2[i];
                    vtvn = vtv - 4*v[i] + 4;

                    //run the search loop
                    do{                   
                        //test the likelihood of the codeword on each
                        //side of the line, runs in constant time.
                        double L = vtv/(vtv - y1tv*y1tv/y1ty1 - y2tv*y2tv/y2ty2
                                + y1tv*y2tv*y1ty2/(y1ty1*y2ty2));       
                        if(L > Lbest){
                            Lbest = L;
                            for(int j = 0; j < 2*T; j++)
                                vbest[j] = v[j];
                        }
                        double Ln = vtvn/(vtvn - y1tvn*y1tvn/y1ty1 - y2tvn*y2tvn/y2ty2
                            + y1tvn*y2tvn*y1ty2/(y1ty1*y2ty2));
                        if(Ln > Lbest){
                            Lbest = Ln;
                            for(int j = 0; j < 2*T; j++)
                                vbest[j] = v[j];
                            vbest[i] -= 2;    
                        }

                        Double key = ((Double) map.firstKey());
                        int n = ((Integer)map.get(key)).intValue();
                        double s = Math.signum(d[n]);

                        //update the dot products
                        y1tv += 2*s*y1[n];
                        y2tv += 2*s*y2[n];
                        vtv += 4*s*v[n] + 4;
                        y1tvn += 2*s*y1[n];
                        y2tvn += 2*s*y2[n];
                        vtvn += 4*s*v[n] + 4;

                        v[n] += 2*s;
                        map.remove(key);
                        if((v[n] > -M + 1 ) && (v[n] < M - 1 ))
                            map.put(new Double((v[n]+s-c[n])/d[n]), new Integer(n));

                    }while(!map.isEmpty());

                }
            }
            
        }
        
        //Write the best codeword into real and
        //imaginary vectors
        toRealImag(vbest, dreal, dimag);
        
    }
    
    /** 
     * Get the real part of the decoded QAM signal.
     * Call decode first.
     */
    public double[] getReal(){
        return dreal;
    }
    
    /** 
     * Get the imaginary part of the decoded QAM signal.
     * Call decode first.
     */
    public double[] getImag(){
        return dimag;
    }
 
}
