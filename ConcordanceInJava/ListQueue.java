 

/**
 * Created with IntelliJ IDEA.
 * User: Luljan.Bacaj
 * To change this template use File | Settings | File Templates.
 */
public class ListQueue<E> implements MyQueue<E>
{
    private ListDeque<E> data;

    public ListQueue()
    { data = new ListDeque<E>(); }

    public E deQueue()
    { return data.popFront(); }

    public void enQueue(E o)
    { data.pushBack(o); }

    public E front()
    { return data.front(); }

    public boolean isEmpty()
    { return data.isEmpty(); }

    public int size()
    {return data.size();}
}
