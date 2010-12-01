/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pubsim.lattices.An;

import pubsim.lattices.Anstar.Anstar;
import java.util.Arrays;
import pubsim.FastSelection;
import pubsim.VectorFunctions;

/**
 * Simple linear time An algorithm using the Rivest Tarjan
 * selection algorithm.  This was described in the most
 * recent version of SPLAG.
 * @author Robby McKilliam
 */
public class AnFastSelect extends AnSorted {

    public AnFastSelect(int n){
        setDimension(n);
    }

    @Override
    public void nearestPoint(double[] y) {
        if (n != y.length-1)
	    setDimension(y.length-1);
        
        Anstar.project(y, y);
        
        VectorFunctions.round(y, u);
        int m = (int)VectorFunctions.sum(u);
        for(int i = 0; i < n+1; i++){
            z[i].value = Math.signum(m)*(y[i] - u[i]);
            z[i].index = i;
        }
        
        if(m != 0)
            FastSelection.FloydRivestSelect(0, n, (int)Math.abs(m), z);
    
        for(int i = 0; i < Math.abs(m); i++)
            u[z[i].index] -= Math.signum(m);
        
    }

}
