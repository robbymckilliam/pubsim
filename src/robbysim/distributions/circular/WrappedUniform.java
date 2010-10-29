/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package robbysim.distributions.circular;

import robbysim.distributions.UniformNoise;

/**
 * 
 * @author Robert McKilliam
 */
public class WrappedUniform extends WrappedCircularRandomVariable{

    public WrappedUniform(double mean, double var){
        super(new UniformNoise(mean, var));
    }

}
