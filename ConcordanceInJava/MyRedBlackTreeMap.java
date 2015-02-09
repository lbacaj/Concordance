import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Luljan.Bacaj
 * To change this template use File | Settings | File Templates.
 */
public class MyRedBlackTreeMap<K extends Comparable<? super K>, E> implements MyMap<K, E>
{

  private RBTreeNode<K, E> root;
  private int size;
  private RBTreeNode<K, E> cursor;

  private static final boolean RED   = false;
  private static final boolean BLACK = true;

  public MyRedBlackTreeMap()
  {
      root = null;
      cursor = null;
      size = 0;
  }

  public int size()
  { return size; }

  public boolean isEmpty()
  {return (size == 0);}

  private boolean find(K k)
  {
    boolean done = false;
    cursor = root;
    if(root == null)
      return false;
    while(!done)
    {
       K key = cursor.key;
       int n = k.compareTo(key);
       if(n == 0)
            return true;
       else if(n < 0)
       {
         if(cursor.left != null)
            cursor = cursor.left;
         else done = true;
       }
       else
       {
         if(cursor.right != null)
            cursor = cursor.right;
         else done = true;
       }
    }
    return false;
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

  public boolean set(K key, E value)
  {
      boolean found = find(key);
      if(!found)
        return false;
      cursor.info = value;
      return true;
  }

  private RBTreeNode<K, E> predecessor(RBTreeNode<K, E> p)
  {
      RBTreeNode<K,E> temp = null;
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
      RBTreeNode<K,E> trail = p;
      while( temp != null && trail == temp.left )
      {
          trail = temp;
          temp = temp.parent;
      }
      return temp;
  }


  private RBTreeNode<K, E> successor(RBTreeNode<K, E> p)
  {
        RBTreeNode<K, E> temp = null;
        if(p == null)
           return temp;
        if(p.right != null)
        {
           temp = p.right;
           while(temp.left != null)
              temp = temp.left;
           return temp;
        }
        temp = p.parent;
        RBTreeNode<K, E> trail = p;
        while( temp != null && trail == temp.right )
        {
            trail = temp;
            temp = temp.parent;
        }
        return temp;
    }


  private void rotateLeft(RBTreeNode<K, E> ptr)
  {
          RBTreeNode<K, E> r = ptr.right;
          ptr.right =  r.left;
          if (r.left != null)
              r.left.parent = ptr;
          r.parent = ptr.parent;

          if (ptr.parent == null)
              root = r;
          else if (ptr.parent.left == ptr)
                ptr.parent.left = r;
          else  ptr.parent.right = r;

         r.left = ptr;
          ptr.parent = r;
  }

  private void rotateRight(RBTreeNode<K, E> ptr)
  {
         RBTreeNode<K, E> l = ptr.left;
         ptr.left = l.right;
         if (l.right != null)
             l.right.parent = ptr;
         l.parent = ptr.parent;

         if (ptr.parent == null)
             root = l;
         else if (ptr.parent.left == ptr)
               ptr.parent.left = l;
         else  ptr.parent.right= l;

         l.right = ptr;
         ptr.parent = l;
  }

  private void fixAfterInsertion(RBTreeNode<K, E> x)
  {                              //x is a RED node with a RED parent
                                 //grandparent must be BLACK
     RBTreeNode<K, E> uncle, parent, grandParent;
     while (x != null && x != root && x.parent.color == RED)
     {
       parent = x.parent;
       grandParent = parent.parent;
       if (parent == leftOf(grandParent))
       {
          uncle = rightOf(grandParent);
          if (colorOf(uncle) == RED)
          {   //Equivalent of a full node in a 2-3-4 Tree.
              setColor(parent, BLACK);
              setColor(uncle, BLACK);
              setColor(grandParent, RED);
              x = grandParent;
          }
          else  //uncle is BLACK
          {
             if (x == rightOf(parent))
             {
                x = parent;
                rotateLeft(x);
                parent = parentOf(x);
             }
             setColor(parent, BLACK);
             setColor(grandParent, RED);
             if (grandParent != null)
                rotateRight(grandParent);
         }
       }
       else  //parent is right child of grandparent
       {
          uncle  = leftOf(grandParent);
          if ( colorOf(uncle) == RED )
          {
            setColor(parent, BLACK);
            setColor(uncle, BLACK);
            setColor(grandParent, RED);
            x = grandParent;
          }
          else
          {//uncle is BLACK
             if (x == leftOf(parent) )
             {
                 x = parent;
                 rotateRight(x);
                 parent = parentOf(x);
             }
             setColor(parent, BLACK);
             setColor(grandParent, RED);
             if (grandParent != null)
                 rotateLeft(grandParent);
         }
       }
     }
     root.color = BLACK;
  }

  public boolean put(K key, E value)
  {
     if(size == 0)
     {
       size++;
       root = new RBTreeNode<K, E>(key, value, null, null, null);
       root.color = BLACK;
       return true;
     }

     boolean found = find(key);
     if(found)
       return false;

     int cmp = key.compareTo( cursor.key );
     if (cmp < 0)
     {
        size++;
        cursor.left = new RBTreeNode<K, E>(key, value, null, null, cursor);
        fixAfterInsertion(cursor.left);
        return true;
     }
     else
     {
       size++;
       cursor.right = new RBTreeNode<K, E>(key, value, null, null, cursor);
       fixAfterInsertion(cursor.right);
       return true;
     }
  }


  public boolean remove(K key)
  {
      boolean found = find(key);
      if (!found)
         return false;

      deleteElement(cursor);
      return true;
  }

  private void deleteElement(RBTreeNode<K, E> ptr)
  {//remove the element pointed at by ptr;
       size--;
       RBTreeNode<K, E> p = ptr;
       // If two children, copy successor's element to p and then make p
       // point to successor.
       if (p.left != null && p.right != null)
       {//node being removed has two children
          ptr = successor(p);
          p.key = ptr.key;
          p.info = ptr.info;
          p = ptr;
       } // p has 2 children

       // Start fixup at replacement node, if it exists.
       RBTreeNode<K, E> replacement = null;
       if(p.left != null)
            replacement =  p.left;
       else replacement =  p.right;

      if (replacement != null)
      {   // Not removing a leaf
          // Link replacement to parent
          replacement.parent = p.parent;
          if (p.parent == null)
              root = replacement;
          else if (p == p.parent.left)
               p.parent.left = replacement;
          else p.parent.right = replacement;

          p.left   = null;
          p.right  = null;
          p.parent = null;

          // Fix replacement
          if (p.color == BLACK)
              fixAfterDeletion(replacement);
      }
      //Cases for removing a leaf
      //1. Removing the root, so the tree becomes empty
      else if (p.parent == null)
          root = null;
      else
      { //  No children. Use self as phantom replacement and unlink.
          if (p.color == BLACK)
              fixAfterDeletion(p);

          if (p.parent != null)
          {
             if (p == p.parent.left)
                p.parent.left = null;
             else if (p == p.parent.right)
                p.parent.right = null;
             p.parent = null;
          }
      }
  }

  private void fixAfterDeletion(RBTreeNode<K, E> x)
  {
    while (x != root && colorOf(x) == BLACK)
    {
      if (x == leftOf(parentOf(x)))
      {
          RBTreeNode<K, E> sib = rightOf(parentOf(x));

          if (colorOf(sib) == RED)
          {
              setColor(sib, BLACK);
              setColor(parentOf(x), RED);
              rotateLeft(parentOf(x));
              sib = rightOf(parentOf(x));
          }

          if (colorOf(leftOf(sib))  == BLACK &&
                      colorOf(rightOf(sib)) == BLACK)
          {
              setColor(sib,  RED);
              x = parentOf(x);
          }
          else
          {
             if (colorOf(rightOf(sib)) == BLACK)
             {
                 setColor(leftOf(sib), BLACK);
                 setColor(sib, RED);
                 rotateRight(sib);
                 sib = rightOf(parentOf(x));
             }
             setColor(sib, colorOf(parentOf(x)));
             setColor(parentOf(x), BLACK);
             setColor(rightOf(sib), BLACK);
             rotateLeft(parentOf(x));
             x = root;
          }
      }
      else
      { // symmetric
        RBTreeNode<K, E> sib = leftOf(parentOf(x));

        if (colorOf(sib) == RED)
        {
           setColor(sib, BLACK);
           setColor(parentOf(x), RED);
           rotateRight(parentOf(x));
           sib = leftOf(parentOf(x));
        }

        if (colorOf(rightOf(sib)) == BLACK &&
                    colorOf(leftOf(sib)) == BLACK)
        {
            setColor(sib, RED);
            x = parentOf(x);
        }
        else
        {
            if (colorOf(leftOf(sib)) == BLACK)
            {
               setColor(rightOf(sib), BLACK);
               setColor(sib, RED);
               rotateLeft(sib);
               sib = leftOf(parentOf(x));
            }
            setColor(sib, colorOf(parentOf(x)));
            setColor(parentOf(x), BLACK);
            setColor(leftOf(sib), BLACK);
            rotateRight(parentOf(x));
            x = root;
        }
      }
    }
    setColor(x, BLACK);
  }

  private boolean colorOf(RBTreeNode<K, E> p)
  {
      if(p == null)
        return BLACK;
      return p.color;
  }

  private RBTreeNode<K, E>  parentOf(RBTreeNode<K, E> p)
  {
     if(p == null)
       return null;
     return  p.parent;
  }

  private void setColor(RBTreeNode<K, E> p, boolean c)
  {
      if(p != null)
        p.color = c;
  }

  private RBTreeNode<K, E> leftOf(RBTreeNode<K, E> p)
  {
      if(p == null)
        return null;
      return  p.left;
  }

  private RBTreeNode<K, E> rightOf(RBTreeNode<K, E> p)
  {
      if(p == null)
        return null;
      return p.right;
  }

  public void clear()
  {
     root = null;
     cursor = null;
     size = 0;
  }

  public Iterator<KeyValuePair<K, E> > iterator()
  {
     Iterator<KeyValuePair<K, E> > itr = new MyInOrderItr();
     return itr;
  }

  public Iterator<KeyValuePair<K, E> > breadthFirstIterator()
  {
      Iterator<KeyValuePair<K, E> > itr = new MyBreadthFirstItr();
      return itr;
  }


 /*********************************************************************************/

     private class MyBreadthFirstItr implements Iterator<KeyValuePair<K, E>>
     {
       private RBTreeNode<K, E> lastReturned;
       private MyQueue<RBTreeNode<K, E>> q;

       public MyBreadthFirstItr()
       {
           q = new ListQueue<RBTreeNode<K, E>>();
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

         K newKey = lastReturned.key;
         KeyValuePair<K, E> hold = new KeyValuePair<K, E>(newKey, lastReturned.info);
         return hold;
       }

       public void remove()
       { throw new UnsupportedOperationException(); }

     }

  /**********************************************************************************/

     private class MyInOrderItr implements Iterator<KeyValuePair<K, E>>
     {
       private RBTreeNode<K, E> lastReturned, next;

       public MyInOrderItr()
       {
           next = root;
           if(root == null)
             return;
           while(next.left != null)
                next = next.left;
           lastReturned = null;
       }

       public boolean hasNext()
       { return (next != null); }

       public KeyValuePair<K, E> next()
       {
          if (next == null)
             throw new NoSuchElementException();
          lastReturned = next;
          next = successor(next);
          K k = lastReturned.key;
          KeyValuePair<K, E> hold = new KeyValuePair<K, E>(k, lastReturned.info);
          return hold;
       }

       public void remove()
       {
         if (lastReturned == null)
            throw new IllegalStateException();
         else
         {
             deleteElement(lastReturned);
             lastReturned = null;
         }
       }
     }

 /**********************************************************************************/


     private static class RBTreeNode<K, E>
     {
            private K key;
            private E info;
            private RBTreeNode<K, E> left;
            private RBTreeNode<K, E> right;
            private RBTreeNode<K, E> parent;
            private boolean color;

            public RBTreeNode()
            {
                parent = left = right = null;
                color  = RED;
            }

            public RBTreeNode(K k, E o, RBTreeNode<K, E> lf,
                                        RBTreeNode<K, E> rt)
            {
               key    = k;
               info   = o;
               left   = lf;
               right  = rt;
               parent = null;
               color  = RED;
            }

            public RBTreeNode(K k, E o, RBTreeNode<K, E> lf,
                                        RBTreeNode<K, E> rt, RBTreeNode<K, E> par)
            {
               key    = k;
               info   = o;
               left   = lf;
               right  = rt;
               parent = par;
               color  = RED;
            }

            public RBTreeNode(RBTreeNode<K, E> otherNode)
            {
              key    = otherNode.key;
              info   = otherNode.info;
              left   = otherNode.left;
              right  = otherNode.right;
              parent = otherNode.parent;
              color = otherNode.color;
            }
     }
}



