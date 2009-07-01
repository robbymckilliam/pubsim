/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.Anstar;

import lattices.An.AnFastSelect;
import lattices.An.AnSorted;
import simulator.VectorFunctions;

/**
 * Nearest point algorithm that uses the n+1 glue vectors that
 * glue the lattice An into An*.  This is Conway and Sloanes
 * original algorithm.  It requires O(n^2 logn) operations.
 * It's very slow!
 * @author Robby McKilliam
 */
public class AnstarAnGlued extends Anstar{

    private double[] g, yd;
    private AnSorted an;

    public AnstarAnGlued(int n){
        setDimension(n);
    }

    /** 
     * Sets protected variable g to the glue
     * vector [i].  See SPLAG pp109.
     */
    protected void glueVector(int i){
        
        /*
        int j = n + 1 - i;
        for(int k = 0; k < j; k++)
            g[k] = i/(double)(n+1);
        for(int k = (int)j; k < n + 1; k++)
            g[k] = -j/(double)(n+1);
        */ 
        
        //this is another set of glue vectors that can be
        //used.  These are in a line and are sometimes
        //more convenient than Conway and Sloane's.
        g[0] = i*(1.0 - 1.0/(n+1));
        for(int j = 1; j < n+1; j++)
            g[j] = -i*1.0/(n+1);
         
        
    }
    
    
    public void setDimension(int n) {
        this.n = n;
        g = new double[n+1];
        yd = new double[n+1];
        v = new double[n+1];
        an = new AnFastSelect(n);
    }
    
    /** Simple nearest point algorithm based on glue vectors */
    public void nearestPoint(double[] y) {
        if (n != y.length-1)
	    setDimension(y.length-1);
        
        double D = Double.POSITIVE_INFINITY;
        int besti = 0;
        
        for(int i = 0; i < n+1; i++){
            glueVector(i);
            
            System.out.println(VectorFunctions.print(g));
            
            for(int j = 0; j < n+1; j++)
                yd[j] = y[j] - g[j];
            
            an.nearestPoint(yd);
            
            double d = VectorFunctions.distance_between2(yd, an.getLatticePoint());

            
            if( d < D ){
                besti = i;
                D = d;
            }
            
        }
        
        glueVector(besti);
        for(int j = 0; j < n+1; j++)
            yd[j] = y[j] - g[j];
            
        an.nearestPoint(yd);
        
        for(int j = 0; j < n+1; j++)
            v[j] = an.getLatticePoint()[j] + g[j];
        
    }
    
    

}
