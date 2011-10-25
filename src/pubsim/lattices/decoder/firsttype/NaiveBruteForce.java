/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.decoder.firsttype;

import Jama.Matrix;
import pubsim.VectorFunctions;
import pubsim.lattices.Lattice;
import pubsim.lattices.NearestPointAlgorithm;
import pubsim.lattices.util.IntegerVectors;
import static pubsim.VectorFunctions.onesColumn;
import static pubsim.Util.fracpart;
import static pubsim.VectorFunctions.matrixMultVector;
import static pubsim.VectorFunctions.sum2;

/**
 * The naive algorithm for computing a nearest point in a lattice of first type by just checking
 * all the possible combinations of the corresponding binary quadratic form.
 * @author Robby McKilliam
 */
public class NaiveBruteForce extends MinCutFirstType implements NearestPointAlgorithm {
    
    
    public NaiveBruteForce(Lattice L){ super(L); }
    
    @Override
    public void nearestPoint(double[] y) {
        
        matrixMultVector(Binv, y, z); 
        z[N] = 0.0;
        
        //System.out.println(VectorFunctions.print(z));
        
        double Dmin = Double.POSITIVE_INFINITY;
        for(Matrix p : new IntegerVectors(N+1, 2) ){
            double[] pd = p.getColumnPackedCopy();
            double D = computeDistance(pd, z);
            //System.out.println(D);
            //System.out.println(VectorFunctions.print(pd));
            //System.out.println();
            if(D < Dmin){
                Dmin = D;
                for(int i = 0; i < N+1; i++){
                    u[i] = Math.floor(z[i]) + pd[i];
                }
            }
        }
        
        //compute the nearest point from the index
        matrixMultVector(B, u, v); 
            
    }
    
    protected double computeDistance(double[] p, double[] z){
        for(int i = 0; i < N+1; i++) s[i] = z[i] - Math.floor(z[i]) - p[i];
        return sum2(matrixMultVector(B, s));
    }

    
}
