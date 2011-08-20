/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.lattices.An;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Set;
import java.util.Vector;
import javax.imageio.stream.FileImageInputStream;
import pubsim.BigRational;

/**
 * Runs the recursion to compute moments for An and also the probability of error
 * @author Robby McKilliam
 */
public class MomentComputer {
    
    //stores the recurively computed values
    Grecursion Gr;
    
    public MomentComputer(String fname){
       
        //try to read in a stored table of values
        FileInputStream fis;
        ObjectInputStream ois;
        try{
            fis = new FileInputStream(fname);
            ois = new ObjectInputStream(fis);
            Gr = (Grecursion) ois.readObject();
        }
        catch(Exception ex){
            Gr = new Grecursion();
        }
        
    }
    
    public BigRational G(int n, int k, int m){
        
        return null;
        
    }
    
    public static class Grecursion implements Serializable{
        final Vector<Vector<Vector<BigRational>>> G;
        
        public Grecursion(){
            G = new Vector<Vector<Vector<BigRational>>>();
        }
        
        public BigRational G(int n, int k, int m){
            BigRational ret = null;
            if(n < 1 || k < 0 || m < 0) throw new RuntimeException("n,k or m are out of bounds");
            else if(n == 1) return new BigRational(1,2*k+m+1);
            else if(k==0 && m==0) return new BigRational(1, 1);
            else if(G.get(n).get(k).get(m) != null) return G.get(n).get(k).get(m);
            
            for(int kd = 0; kd <= k; kd++){
                for(int md = 0; md <= m; md++){
                    
                }
            }
            return ret;
        }
        
        public void add(int n, int k, int m){

        }
        
         
    } 
    
    
    
    
    
}
