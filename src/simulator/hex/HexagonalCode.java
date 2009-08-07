/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package simulator.hex;

import lattices.Hexagonal;
import simulator.VoronoiCodeAutoTranslation;

/**
 *
 * @author Robby McKilliam
 */
public class HexagonalCode 
        extends VoronoiCodeAutoTranslation {

    public HexagonalCode(int M){
        super(new Hexagonal(), M); 
    }

}
