package pubsim;



/** 
 * Class used for storing a double and an index.
 * Acts a little bit like pubsim.Sorter.ComparibleHolder but
 * is more specialised and probably faster and is just simpler
 * and nicer to look at.
 * @author Robby McKilliam
 */
public class IndexedDouble implements Comparable<IndexedDouble> {
    public double value;
    public int index;
    
    public IndexedDouble(){}
    
    public IndexedDouble(double value, int index){
        this.value = value;
        this.index = index;
    }

    @Override
    public final int compareTo(IndexedDouble that) {
        return Double.compare(value, that.value);
    } 
   
    @Override
    public final String toString() {
        return "(" + value + ", " + index + ")";
    }
    
}