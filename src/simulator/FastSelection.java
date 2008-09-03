/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * This runs the fast selection algorithm of Blum, Rivest and Tarjan.
 * UNDER CONSTRUCTION
 * @author Robby McKilliam
 */
public class FastSelection {
    
    Object elem;
    Collection before;
    Collection after;
    Object[] all;
   
    public FastSelection(int maxsize){
        all = new Object[maxsize];
        before = new ArrayList();
        after = new ArrayList();
    }
    
    public Collection beforeAndIncluding(){
        return before;
    }
    
    /**
     * @return the collection of elements that come after
     * the kth.
     */
    public Collection after(){
        return after;
    }
    
    /**
     * @return the kth element
     */
    public Object getElement(){
        return elem;
    }
    
    /**
     * Run the Blum, Rivest and Tarjan selection algorithm
     * and return the kth element.  This is setup with the
     * intetion of you casting the object to the type you
     * want later.  I can't be bothered with numerics atm.
     * @param k: get the kth element
     * @param c: the collection to select from
     * @return the kth element
     * 
     * UNDER CONSTRUCTION, currently this just sorts! and allocates memory
     * 
     */
    public Object select(int k, Collection c){
        elem = null;
        before.clear();
        after.clear();
        if(k <= c.size() && k > 0){
            all = c.toArray();
            Arrays.sort(all);
            elem = all[k-1];
            for(int i = 0; i < k; i++)
                before.add(all[i]);
            for(int i = k; i < all.length; i++)
                after.add(all[i]);
        }
        return getElement();
    }
    
}
