 

/**
 * Created with IntelliJ IDEA.
 * User: Luljan.Bacaj
 * To change this template use File | Settings | File Templates.
 */
public interface MyQueue<E>
{
    /**
     * If the queue is not empty, remove the element that is currently at
     * the front of the queue and return this object.  If the queue is empty
     * return null.
     * @return The object at the front of the queue or null if the queue is empty
     */
    public E deQueue();

    /**
     * Insert Object o at the back of the queue.
     *
     * @param o the Object being added to the queue
     */
    public void enQueue(E o);

    /**
     * If the queue is not empty, return the element that is currently at
     * the front of the queue.  If the queue is empty return null.  The
     * contents of the queue is not changed.
     * @return The object at the front of the queue or null if the queue is empty
     */
    public E front();

    /**
     * If the queue is empty, return true otherwise return false.
     *
     * @return A boolean indicating if the queue is or is not empty.
     */
    public boolean isEmpty();


    /**
     * Return the number of elments currently in the Queue.
     *
     * @return An int indicating the current size of the queue.
     */
    public int size();
}

