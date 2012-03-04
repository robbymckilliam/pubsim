/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.decoder.firsttype;

import Jama.Matrix;
import static pubsim.VectorFunctions.*;
import pubsim.lattices.Lattice;
import pubsim.lattices.NearestPointAlgorithm;
import pubsim.lattices.util.IntegerVectors;

/**
 * The naive algorithm for computing a nearest point in a lattice of first type by just checking
 * all the possible 3^(n+1) combinations of the corresponding quadratic {0,1,2} program.
 * @author Robby McKilliam
 */
public class NaiveBruteForce extends MinCutFirstType implements NearestPointAlgorithm {
    
    
    public NaiveBruteForce(Lattice L){ super(L); }
    
    @Override
    public void nearestPoint(double[] y) {
        
        matrixMultVector(Binv, y, z); 
        z[N] = 0.0;
        
        //double[] test = matrixMultVector(B, z);
        // assert(VectorFunctions.distance_between(test, y) < 0.0000001);
        //System.out.println(VectorFunctions.print(test));
        //System.out.println(VectorFunctions.print(y));
        //System.out.println(VectorFunctions.print(z));
        //double[] zfloor = new double[z.length];
        //for(int i = 0; i < z.length; i++) zfloor[i] = Math.floor(z[i]);
        //System.out.println(VectorFunctions.print(zfloor));
        //System.out.println();
        
        double Dmin = Double.POSITIVE_INFINITY;
        for(Matrix p : new IntegerVectors(N+1, 3) ){
            double[] pd = p.getColumnPackedCopy();
            double D = computeDistance(pd, z);
            //System.out.print(D + "\t");
            //System.out.print(VectorFunctions.print(pd) + "\t");
            //System.out.println();
            if(D < Dmin){
           //     System.out.print(D + "\t");
           //     System.out.print(VectorFunctions.print(pd) + "\t");
           //     System.out.println("*******");
                Dmin = D;
                for(int i = 0; i < N+1; i++){
                    u[i] = Math.floor(z[i]) + pd[i];
                }
            }
            //else System.out.println();
        }
        
        //compute the nearest point from the index
        matrixMultVector(B, u, v); 
            
    }
    
    protected double computeDistance(double[] p, double[] z){
        for(int i = 0; i < N+1; i++) s[i] = z[i] - Math.floor(z[i]) - p[i];
        return sum2(matrixMultVector(B, s));
    }

    
}
