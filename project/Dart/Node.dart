class Node<V>
{
  // Fields
  V data;

  Node<V> nextNode;
  
  // Constructor
  Node(V data)
  {
      this.data = data;
  }
  
  V getData() {
      return data;
  }
}