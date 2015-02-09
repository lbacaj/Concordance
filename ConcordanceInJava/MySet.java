 
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Luljan.Bacaj
 * To change this template use File | Settings | File Templates.
 */
public interface MySet<E> extends Iterable<E>
{
    /**
     * Remove all the values from this collection so that it becomes empty.
     */
    public void clear();

    /**
     *@return the number of objects in this collection ;
     */
    public int size();

    /**
     * If this collection is empty, return true otherwise return false.
     * @return A boolean indicating if this collection is or is not empty.
     */
    public boolean isEmpty();

    /**
     * If this collection is contains the given value return true, otherwise return false.
     * @param The value being searched for in this collection.
     * @return A boolean indicating if this collection contains the value.
     */
    public boolean contains(E element);

    /**
     * Remove the given item from this collection. If the item is not a member of this
     * collection the operation fails.
     * @return true if the value is removed, false if it is not removed.
     */
    public boolean remove(E element);

    /**
     Create and return an iterator for this collection.
     @return an iterator for this collection.
     */
    public Iterator<E> iterator();


    /**
     * Insert a value into this set if it is not already present.
     * @param value the object being added to the set
     * @return true if the object is added, false if it is not added.
     */
    public boolean add (E element);


    /**
     * Return a string representation of this set.
     * @return A string that contains the contents of the set.
     */
    public String toString();


    /**
     * Test to see if the this set has the same contents as the given set.
     * @param the set that is being compared to this set.
     * @return true if the two sets have the same contents, false otherwise.
     */
    public boolean equals(Object other);


    /**
     * Create a new set whose contents will be the union of the this set
     * and the given set.
     * @param the set whose contents are being added to this set to form the union.
     * @return a new set that contains the union of the two sets.
     */
    public MySet<E> union(MySet<E> otherSet);


    /**
     * Create a new set whose contents will be the intersection of this
     * set and the given set.
     * @param the set whose contents are being intersected with this set.
     * @return a new set that contains the intersection of the two sets.
     */
    public MySet<E> intersection(MySet<E> otherSet);


    /**
     * Create a new set whose contents will be the set difference of this set and
     * the given set.  An item will be in the set difference if and only if it is
     * in this set and not in the given set.
     * @param the set being used to form the difference with this set.
     * @return a new set that contains the difference of the two sets.
     */
    public MySet<E> difference(MySet<E> otherSet);


    /**
     * Test to see if the given set is a subset of this set.
     * @param the set that is being tested.
     * @return true if the given set is a subset of this set, false otherwise.
     */
    public boolean containsSubset(MySet<E> otherSet);
}

