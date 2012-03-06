/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pubsim.distributions;

/**
 * Static class for safely generating unique seeds for each random number 
 * generator created.
 * @author Robby McKilliam
 */
public final class SeedGenerator {
    
    private static long currentSeed = 0;
    
    public synchronized static long getSeed(){
        currentSeed++;
        return currentSeed;
    }
    
}
