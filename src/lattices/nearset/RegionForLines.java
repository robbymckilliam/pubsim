/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lattices.nearset;
import lattices.util.region.Region;
import lattices.util.region.BoundingBox;

/**
 * Represents a region with the abilty to compute where
 * a parameterised line passes through it.
 * @author Robby McKilliam
 */
public interface RegionForLines extends Region, BoundingBox{

    /**
     * Returns true if the line rm + c passes through the region
     * The line is parametersised  by r.
     */
    public boolean linePassesThrough(double [] m, double[] c);

    /**
     * Returns the minimum value of r such that the line
     * rm + c is in/on the region.
     */
    public double minParam();

    /**
     * Returns the maximum value of r such that the line
     * rm + c is in/on the region.
     */
    public double maxParam();

}
