import 'Node.dart';

class LinkedStacker<V> { 
    Node<V> top;      
    
    LinkedStacker()  
    {
        top = null;
    }

    bool isEmpty() {
        if (top == null) {
            return true;
        }
        else {
            return false;
        }
    }

    bool push(V data) {
        Node<V> newNode = new Node<V>(data);
        newNode.nextNode = top;
        top = newNode;
        return true;
    }

    bool pop() {
        if (isEmpty() == true) {
            return false;
        }

        Node<V> nodeToDelete = top;
        top = top.nextNode;
        nodeToDelete = null;
        return true;
    }

    V peek() {
        return top.data;
    }
}