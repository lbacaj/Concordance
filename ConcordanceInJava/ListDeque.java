 

/**
 * Created with IntelliJ IDEA.
 * User: Luljan.Bacaj
 * To change this template use File | Settings | File Templates.
 */
public class ListDeque<E> implements MyDeque<E>
{
    private ListNode<E> dummy;
    private int size;

    public ListDeque()
    {
        dummy = new ListNode<E>();
        dummy.prev = dummy;
        dummy.next = dummy;
        size = 0;
    }

    public boolean isEmpty()
    { return (size == 0); }

    public E popFront()
    {
        if(size == 0)
            return null;
        ListNode<E> temp = dummy.next;
        E hold = temp.info;
        temp = temp.next;
        dummy.next = temp;
        temp.prev = dummy;
        size--;
        return hold;
    }

    public E popBack()
    {
        if(size == 0)
            return null;
        ListNode<E> temp = dummy.prev;
        E hold = temp.info;
        temp = temp.prev;
        dummy.prev = temp;
        temp.next = dummy;
        size--;
        return hold;
    }

    public void pushFront(E obj)
    {
        ListNode<E> hold = dummy.next;
        ListNode<E> temp = new ListNode<E>(obj, dummy, hold);
        hold.prev = temp;
        dummy.next =temp;
        size++;
    }

    public void pushBack(E obj)
    {
        ListNode<E> hold = dummy.prev;
        ListNode<E> temp = new ListNode<E>(obj, hold, dummy);
        hold.next = temp;
        dummy.prev = temp;
        size++;
    }

    public E front()
    {
        if(size == 0)
            return null;
        ListNode<E> temp = dummy.next;
        return temp.info;
    }

    public E back()
    {
        if(size == 0)
            return null;
        ListNode<E> temp = dummy.prev;
        return temp.info;
    }

    public int size()
    { return size; }

    /*************************************************************************
     Inner Node Class
     **************************************************************************/
    private static class ListNode<E>
    {
        private E info;
        private ListNode<E> prev;
        private ListNode<E> next;

        public ListNode()
        { prev = next = null; }

        public ListNode(E obj, ListNode<E> previousNode, ListNode<E> nextNode)
        {
            info = obj;
            prev = previousNode;
            next = nextNode;
        }

        public ListNode(ListNode<E> otherNode)
        {
            info = otherNode.info;
            prev = otherNode.prev;
            next = otherNode.next;
        }
    }
}

