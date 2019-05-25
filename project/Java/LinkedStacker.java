public class LinkedStacker<V> { 
    Node<V> top;      
    
    public LinkedStacker()  
    {
        top = null;
    }

    public boolean isEmpty() {
        if (top == null) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean push(V data) {
        Node<V> newNode = new Node<V>(data);
        newNode.nextNode = top;
        top = newNode;
        return true;
    }

    public boolean pop() {
        if (isEmpty() == true) {
            return false;
        }

        Node<V> nodeToDelete = top;
        top = top.nextNode;
        nodeToDelete = null;
        return true;
    }

    public V peek() {
        return top.data;
    }
}