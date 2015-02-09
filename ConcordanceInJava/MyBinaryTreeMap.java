 
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Luljan.Bacaj
 * To change this template use File | Settings | File Templates.
 */
public class MyBinaryTreeMap<K extends Comparable<? super K>, E>
        implements MyMap<K, E>, Iterable< KeyValuePair<K, E> >
{
    private TreeNode<K, E> root;
    private TreeNode<K, E> cursor;
    private int size;

    public MyBinaryTreeMap()
    {
        root = null;
        cursor = null;
        size = 0;
    }

    public MyBinaryTreeMap(MyMap<K, E> otherMap)
    {
        this();
        Iterator<KeyValuePair<K, E>> itr = otherMap.iterator();
        while(itr.hasNext())
        {
            KeyValuePair<K, E> next = itr.next();
            put(next.getFirst(), next.getSecond());
        }
    }

    public int size()
    { return size; }

    public boolean isEmpty()
    {return (size == 0);}

    private boolean find(K k)
    {
        cursor = root;
        if(cursor == null)
            return false;

        while(true)
        {
            K key = cursor.key;
            int n = k.compareTo(key);
            if (n == 0)
                return true;
            else if(n < 0)
            {
                if(cursor.left == null)
                    return false;
                else cursor = cursor.left;
            }
            else
            {
                if(cursor.right == null)
                    return false;
                else cursor = cursor.right;
            }
        }
    }

    public boolean contains(K k)
    { return find(k); }

    public E get(K k)
    {
        boolean found = find(k);
        if(!found)
            return null;
        return cursor.info;
    }

    public boolean set(K k, E value)
    {
        boolean found = find(k);
        if(!found)
            return false;
        cursor.info = value;
        return true;
    }

    public boolean put (K k, E value)
    {
        if (isEmpty())
        {
            TreeNode<K, E> temp = new TreeNode<K, E>(k, value, null, null, null);
            root = temp;
            size++;
            return true;
        }

        boolean found = find(k);
        if(found)
            return false;
        TreeNode<K, E> temp = new TreeNode<K, E>(k, value, null, null, cursor);
        K key = cursor.key;
        int n = k.compareTo(key);
        if(n < 0)
        {//insert as left child
            cursor.left = temp;
        }
        else
        {//insert as right child
            cursor.right = temp;
        }
        size++;
        return true;
    }

    private void extract(TreeNode<K, E> ptr)
    {//remove from the tree the element that ptr is currently pointing at.
        TreeNode<K, E> temp = ptr;
        if(temp.left != null && temp.right != null)
        {//if ptr is pointing at a node with two children.
            ptr = successor(temp);
            temp.key = ptr.key;
            temp.info = ptr.info;
        }
        //at this point ptr is pointing at a node with at most one child.
        TreeNode<K, E> replacement = null;
        if(ptr.left != null)
            replacement = ptr.left;
        else if(ptr.right != null)
            replacement = ptr.right;
        if(replacement != null)
        {//ptr has one child
            replacement.parent = ptr.parent;
            if(ptr.parent == null)
                root = replacement;
            else if(ptr == ptr.parent.left)
                ptr.parent.left = replacement;
            else ptr.parent.right = replacement;
            return;
        }
        //replacement is null so ptr has no children
        else if(ptr.parent == null)
            root = null;
        else
        {
            if(ptr == ptr.parent.left)
                ptr.parent.left = null;
            else ptr.parent.right = null;
        }
    }

    public boolean remove(K k)
    {
        boolean found = find(k);
        if(found)
        {
            extract(cursor);
            cursor = null;
            size--;
        }
        return found;
    }

    private TreeNode<K, E> successor(TreeNode<K, E> p)
    {
        TreeNode<K, E> temp = null;
        if(p == null)
            return null;
        if(p.right != null)
        {
            temp = p.right;
            while(temp.left != null)
                temp = temp.left;
            return temp;
        }
        temp = p.parent;
        TreeNode<K, E> trail = p;
        while( temp != null && trail == temp.right )
        {
            trail = temp;
            temp = temp.parent;
        }
        return temp;
    }

    private TreeNode<K, E> predecessor(TreeNode<K, E> p)
    {
        TreeNode<K, E> temp = null;
        if(p == null)
            return temp;
        if(p.left != null)
        {
            temp = p.left;
            while(temp.right != null)
                temp = temp.right;
            return temp;
        }
        temp = p.parent;
        TreeNode<K, E> trail = p;
        while( temp != null && trail == temp.left )
        {
            trail = temp;
            temp = temp.parent;
        }
        return temp;
    }


    public void clear()
    {
        root = null;
        cursor = null;
        size = 0;
    }

    public Iterator< KeyValuePair<K, E> > iterator()
    {
        Iterator< KeyValuePair<K, E> > itr = new MyTreeItr();
        return itr;
    }

    public Iterator< KeyValuePair<K, E> > breadthFirstIterator()
    {
        Iterator< KeyValuePair<K, E> > itr = new MyBreadthFirstItr();
        return itr;
    }
    /*********************************************************************************/
    private class MyBreadthFirstItr implements Iterator<KeyValuePair<K, E>>
    {
        private TreeNode<K, E> lastReturned;
        private MyQueue<TreeNode<K, E>> q;

        public MyBreadthFirstItr()
        {
            q = new ListQueue<TreeNode<K, E>>();
            lastReturned = null;
            if(root != null)
                q.enQueue(root);
        }

        public boolean hasNext()
        { return !q.isEmpty(); }

        public KeyValuePair<K, E> next()
        {
            if ( q.isEmpty() )
                throw new NoSuchElementException();
            lastReturned = q.front();
            q.deQueue();
            if (lastReturned.left != null)
                q.enQueue(lastReturned.left);
            if(lastReturned.right != null)
                q.enQueue(lastReturned.right);

            K k = lastReturned.key;
            KeyValuePair<K, E> hold = new KeyValuePair<K, E>(k, lastReturned.info);
            return hold;
        }

        public void remove()
        { throw new UnsupportedOperationException(); }
    }
    /**********************************************************************************/
    private class MyTreeItr implements Iterator<KeyValuePair<K, E>>
    {
        private TreeNode<K, E> lastReturned, next;

        public MyTreeItr()
        {
            next = root;
            lastReturned = null;
            if(next != null)
                while(next.left != null)
                    next = next.left;
        }

        public boolean hasNext()
        { return(next != null);  }

        public KeyValuePair<K, E> next()
        {
            if (next == null)
                throw new NoSuchElementException();
            else
            {
                lastReturned = next;
                next = successor(next);
                K k = lastReturned.key;
                KeyValuePair<K,E> hold = new KeyValuePair<K,E>(k, lastReturned.info);
                return hold;
            }
        }

        public void remove()
        {
            if (lastReturned == null)
                throw new IllegalStateException();
            else
            {
                if(lastReturned.right != null && lastReturned.left != null)
                {//going to replace with successor
                    next = lastReturned;
                }
                MyBinaryTreeMap.this.extract(lastReturned);
                lastReturned = null;
                return;
            }
        }
    }

    /**********************************************************************************
     Inner Class: Implements the nodes used by the Tree.
     **********************************************************************************/

    private static class TreeNode<K, E>
    {
        private K key;
        private E info;
        private TreeNode<K, E> left;
        private TreeNode<K, E> right;
        private TreeNode<K, E> parent;

        public TreeNode()
        {
            key = null;
            info = null;
            left = null;
            right = null;
            parent = null;
        }

        public TreeNode(K k, E o, TreeNode<K, E> lf, TreeNode<K, E> rt)
        {
            key    = k;
            info   = o;
            left   = lf;
            right  = rt;
            parent = null;
        }

        public TreeNode(K k, E o, TreeNode<K, E> lf, TreeNode<K, E> rt, TreeNode<K, E> par)
        {
            key    = k;
            info   = o;
            left   = lf;
            right  = rt;
            parent = par;
        }

        public TreeNode(TreeNode<K, E> otherNode)
        {
            key    = otherNode.key;
            info   = otherNode.info;
            left   = otherNode.left;
            right  = otherNode.right;
            parent = otherNode.parent;
        }
    }
}
