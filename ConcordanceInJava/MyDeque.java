 

/**
 * Created with IntelliJ IDEA.
 * User: Luljan.Bacaj
 * To change this template use File | Settings | File Templates.
 */
public interface MyDeque<E>
{
    /**
     *If the Deque is empty, return true otherwise return false.
     *
     *@return A boolean indicating if the Deque is or is not empty.
     */
    public boolean isEmpty();

    /**
     *If the Deque is not empty, remove the element that is currently at
     *the front and return this object.  If the Deque is empty return null.
     *@return The object at the front of the Deque or null if the Deque is empty
     */
    public E popFront();

    /**
     *If the Deque is not empty, remove the element that is currently at
     *the back and return this object.  If the Deque is empty return null.
     *@return The object at the back of the Deque or null if the Deque is empty
     */
    public E popBack();

    /**
     *Insert an Object at the front of the Deque.
     *
     *@param obj the Object being added to the Deque
     */
    public void pushFront(E obj);


    /**
     *Insert an Object at the back of the Deque.
     *
     *@param obj the Object being added to the Deque
     */
    public void pushBack(E obj);

    /**
     *If the Deque is not empty, return the element that is currently at
     *the front of the Deque.  If the Deque is empty return null.  The
     *contents of the Deque is not changed.
     *@return The object at the front of the Deque or null if the Deque is empty
     */
    public E front();

    /**
     *If the Deque is not empty, return the element that is currently at
     *the back of the Deque.  If the Deque is empty return null.  The
     *contents of the Deque is not changed.
     *@return The object at the back of the Deque or null if the Deque is empty
     */
    public E back();

    /**
     *@return the number of objects in the current Deque.
     */
    public int size();
}

