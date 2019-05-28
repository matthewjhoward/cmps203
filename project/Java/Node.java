public class Node<V>
{
    // Fields
    V data;

    Node<V> nextNode;
    
    // Constructor
    public Node(V data)
    {
        this.data = data;
    }
    
    public V getData() {
        return data;
    }
}