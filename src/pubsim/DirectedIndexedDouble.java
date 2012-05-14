package pubsim;



/** 
 * Class used for storing a double and an index and a "direction".
 * Acts a little bit like simulator.Sorter.ComparibleHolder but
 * is more specialised and probably faster and is just simpler
 * and nicer to look at.
 * @author Robby McKilliam
 */
public class DirectedIndexedDouble implements Comparable{
    public double value;
    public int index;
    public int direction;
    
    public DirectedIndexedDouble(){}
    
    public DirectedIndexedDouble(double value, int index, int direction){
        this.value = value;
        this.index = index;
        this.direction = direction;
    }

    @Override
    public int compareTo(Object o) {
        DirectedIndexedDouble co = (DirectedIndexedDouble) o;
        return Double.compare(value, co.value);
    } 
   
    @Override
    public String toString() {
        return "(" + value + ", " + index + ", " + direction + ")";
    }
    
}