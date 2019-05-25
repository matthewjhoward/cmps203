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
        
        // public String toString() // Overrides Object's toString() method
        // {
        //     return String.valueOf(theData);
        // } 
        
        // public boolean equals(Object x) // Overrides Object's equals() method 
        // {
        //     boolean eq = false;
        //     Node that;
        //     if(x instanceof Node)
        //     {
        //         that = (Node) x;
        //         eq = (this.theData == that.theData);
        //     }
        //     return eq;
        // }
    }