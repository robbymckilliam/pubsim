/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.decoder.firsttype;

import Jama.Matrix;
import pubsim.VectorFunctions;
import pubsim.lattices.Anstar.Anstar;
import pubsim.lattices.Lattice;
import pubsim.lattices.NearestPointAlgorithm;
import pubsim.lattices.util.IntegerVectors;
import static pubsim.VectorFunctions.onesColumn;
import static pubsim.Util.fracpart;
import static pubsim.VectorFunctions.matrixMultVector;
import static pubsim.VectorFunctions.sum2;

/**
 * The naive algorithm for computing a nearest point in a lattice of first type by just checking
 * all the possible 2^(n+1) combinations of the corresponding quadratic {0,1} program after
 * correctly directing the {0,1,2} program.
 * @author Robby McKilliam
 */
public class DirectedBruteForce extends MinCutFirstType implements NearestPointAlgorithm {
    
    
    public DirectedBruteForce(Lattice L){ super(L); }
    
    @Override
    public void nearestPoint(double[] y) {
        
        matrixMultVector(Binv, y, z); 
        z[N] = 0.0;
        
        Anstar.project(z, z);
        
//        double[] test = matrixMultVector(B, z);
//         assert(VectorFunctions.distance_between(test, y) < 0.0000001);
//        //System.out.println(VectorFunctions.print(test));
//        //System.out.println(VectorFunctions.print(y));
        System.out.println(VectorFunctions.print(z));
//        double[] zround = new double[z.length];
//        for(int i = 0; i < z.length; i++) zround[i] = Math.round(z[i]);
//        System.out.println(VectorFunctions.print(zround));
//        System.out.println();
        
        double Dmin = Double.POSITIVE_INFINITY;
        for(Matrix p : new IntegerVectors(N+1, 2) ){
            double[] pd = p.getColumnPackedCopy();
            double D = computeDistance(pd, z);
            
            //double[] pds = new double[pd.length];
            //for(int i = 0; i < N+1; i++) pds[i] = sgn(fracpart(z[i]))*pd[i];
            //System.out.print(VectorFunctions.print(pds) + "\t");
            //System.out.println();
            if(D < Dmin){
                System.out.print(D + "\t");
                double[] pds = new double[pd.length];
                for(int i = 0; i < N+1; i++) pds[i] = sgn(fracpart(z[i]))*pd[i];
                System.out.print(VectorFunctions.print(pds) + "\t");
                System.out.println("*******");
                Dmin = D;
                for(int i = 0; i < N+1; i++){
                    u[i] = Math.round(z[i]) + sgn(fracpart(z[i]))*pd[i];
                }
            }
        }
        
        //compute the nearest point from the index
        matrixMultVector(B, u, v); 
            
    }
    
    protected double computeDistance(double[] p, double[] z){
        for(int i = 0; i < N+1; i++){
            double zf = fracpart(z[i]);
            s[i] = zf - sgn(zf)*p[i];
        }
        return sum2(matrixMultVector(B, s));
    }
    
    /** Sign function without zero behaviour */
    protected double sgn(double x){
        if( x >= 0 ) return 1.0;
        else return -1.0;
    }

}
