/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.qam.pat;

import pubsim.Complex;

/**
 * Stored a sequence of pilot symbols and the abilty to return
 * the sequence in cyclically
 * @author Robby McKilliam
 */
public class PilotSequence {
    
    Complex[] seq;
    int current;

    public PilotSequence(){
        current = 0;
    }
    
    public PilotSequence(Complex[] s){
        setSequence(s);
        current = 0;
    }
    
    public PilotSequence(double[] s){
        if(s.length%2 != 0)
            throw new Error("Input sequence must have even length");
        
        seq = new Complex[s.length/2];
        for(int i = 0; i < s.length/2; i++)
            seq[i] = new Complex(s[2*i], s[2*i+1]);
        
        current = 0;
    }
    
    public void setSequence(Complex[] s){
        seq = s;
    }
    
    public void setPosition(int i){
        current = i%seq.length;
    }
    
    /** 
     * Return the next symbol to the next symbol.
     * This cycles around when the end of the sequence is reached.
     */
    public Complex next(){
        current = (current+1)%seq.length;
        return current();
    }
    
    /** Return the current symbol in the sequence */
    public Complex current(){
        return seq[current];
    }
    
}
