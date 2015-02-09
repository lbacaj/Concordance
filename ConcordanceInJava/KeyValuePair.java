 

/**
 * Created with IntelliJ IDEA.
 * User: Luljan.Bacaj
 * To change this template use File | Settings | File Templates.
 */
public class KeyValuePair<K, E>
{
    private K key;
    private E info;

    public KeyValuePair()
    {
        key  = null;
        info = null;
    }

    public KeyValuePair(K theKey, E theInfo)
    {
        key  = theKey;
        info = theInfo;
    }

    public K getFirst()
    { return key; }

    public E getSecond()
    { return info; }

    public void setFirst(K newKey)
    {   key = newKey;}

    public void setSecond(E newInfo)
    {   info = newInfo; }

    public String toString()
    {return key + "   " + info;}
}


