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
    
    // // Access funtions
    // int length()  // Returns the number of elements in this List.
    // {   
    //     return listLength;
    // }
    
    // int index()  //  If cursor is undefined, returns -1. Otherwise, returns the index of the cursor element.
    // {
    //     if(CursorElem == null)
    //     {
    //         return -1; 
    //     }
    //     else
    //     {
    //         return indexOfCursor;
    //     }
    // }
    
    // int front()  // Returns front element. Pre: length()>0 
    // {
    //     if(listLength <= 0)
    //     {
    //         throw new RuntimeException("List Error: front() was called on an empty List");    
    //     }    
    //     return FrontElem.theData;
    // }
    
    // int back()  // Returns back element. Pre: length()>0 
    // {
    //     if(listLength <= 0)
    //     {
    //         throw new RuntimeException("List Error: back() was called on an empty List");    
    //     }    
    //     return BackElem.theData;    
    // }
    
    // int get()  //  Returns cursor element. Pre: length()>0, index()>=0 
    // {
    //     if(listLength <= 0)
    //     {
    //         throw new RuntimeException("List Error: get() was called on an empty List");  
    //     }
    //     if(indexOfCursor < 0)
    //     {
    //         throw new RuntimeException("List Error: get() was called with an undefined index on a List");
    //     }
    //     return CursorElem.theData;
    // }
    
    // boolean equals(List L)  //  Returns true if this List and L are the same integer sequence. The cursor is ignored in both lists. 
    // {   
    //     if(listLength == L.listLength)
    //     {    
    //         Node Listfront = FrontElem;  // Front element of this List targeted
    //         Node Lfront = L.FrontElem;   // Front element of list L targeted
    //         while(Listfront != null && Lfront != null)
    //         {
    //             if(Listfront.equals(Lfront))
    //             {
    //                 Listfront = Listfront.nextNode;
    //                 Lfront = Lfront.nextNode;
    //             }
    //             else
    //             {
    //                 return false;
    //             }
    //         }
    //         return true;
    //     }
    //     else
    //     {
    //         return false;
    //     }
    // }
    
    // // Manipulation procedures 
    // void clear() // Resets this List to its original empty state.  
    // {
    //     listLength = 0;
    //     indexOfCursor = -1;
    //     FrontElem = null;
    //     BackElem = null;
    //     CursorElem = null;
    // }
    
    // void moveFront() // If List is non-empty, places the cursor under the front element. Otherwise, does nothing.  
    // {
    //     if(listLength > 0)
    //     {
    //         CursorElem = FrontElem;  
    //         indexOfCursor = 0;       // This signifies the placement of the first element of the List
    //     }
    //     else
    //     {
    //         // Function does nothing
    //     }
    // }
    
    // void moveBack() // If List is non-empty, places the cursor under the back element. Otherwise, does nothing.  
    // {
    //     if(listLength > 0)
    //     {
    //         CursorElem = BackElem;          
    //         indexOfCursor = listLength - 1;  // This signifies n-1 placement, the last element of the List
    //     }
    //      else
    //     {
    //         // Function does nothing
    //     }
    // }
    
    // void movePrev() //  If cursor is defined and not at front, moves cursor one step toward front of this List. If cursor is defined and at front, cursor becomes undefined. If cursor is undefined, does nothing.   
    // {
    //     if(CursorElem != null && indexOfCursor != 0)  // Checks if cursor is defined and not at front
    //     {
    //         CursorElem = CursorElem.prevNode;
    //         indexOfCursor = (indexOfCursor - 1);
    //     }
    //     else if(CursorElem != null && indexOfCursor == 0)  // Checks if cursor is defined and at front
    //     {
    //         indexOfCursor = -1;  // the "undefined" index value for when cursor is undefined
    //         CursorElem = null;
    //     }
    //     else if(CursorElem == null)
    //     {
    //         // Function does nothing 
    //     }
    // }
    
    // void moveNext() //  If cursor is defined and not at back, moves cursor one step toward back of this List. If cursor is defined and at back, cursor becomes undefined. If cursor is underfined, does nothing.   
    // {
    //     if(CursorElem != null && indexOfCursor != (listLength - 1))  // Checks if cursor is defined and not at back
    //     {
    //         CursorElem = CursorElem.nextNode;
    //         indexOfCursor = (indexOfCursor + 1); 
    //     }
    //     else if(CursorElem != null && indexOfCursor == (listLength - 1))  // Checks if cursor is defined and at back
    //     {
    //         indexOfCursor = -1;  // the "undefined" index value for when cursor is undefined
    //         CursorElem = null;
    //     }
    //     else if(CursorElem == null)
    //     {
    //         // Function does nothing
    //     }
    // }
    
    // void prepend(int theData) // Insert new element into this List.  If List is non-empty, insertion takes place before front element.    
    // {
    //     Node NewElem = new Node(theData);
    //     if(listLength == 0) 
    //     { 
    //         FrontElem = NewElem;
    //         BackElem = NewElem;
    //     }
    //     else
    //     {     
    //         NewElem.nextNode = FrontElem;
    //         FrontElem.prevNode = NewElem;
    //         FrontElem = NewElem;  // NewElem becomes the new FrontElem
    //     }
    //     if(CursorElem == null)
    //     {
    //         // Function does nothing
    //     }
    //     else
    //     {
    //         indexOfCursor = (indexOfCursor + 1);
    //     }
    //     listLength = (listLength + 1);
    // }
    
    //  void append(int theData) // Insert new element into this List.  If List is non-empty, insertion takes place after back element.    
    // {
    //     Node NewElem = new Node(theData);
    //     if(listLength == 0) 
    //     { 
    //         FrontElem = NewElem;
    //         BackElem = NewElem;
    //     }
    //     else
    //     {    
    //         BackElem.nextNode = NewElem;
    //         NewElem.prevNode = BackElem;
    //         BackElem = NewElem;  // NewElem becomes the new BackElem
    //     }
    //     listLength = (listLength + 1);
    // }
    
    // void insertBefore(int theData) // Insert new element before cursor. Pre: length()>0, index()>=0    
    // {
    //     if(listLength <= 0)
    //     {
    //         throw new RuntimeException("List Error: insertBefore() was called on an empty List");    
    //     }
    //     if(indexOfCursor < 0)  // If this test passes, then I know that the cursor is defined
    //     {
    //         throw new RuntimeException("List Error: insertBefore() was called with an undefined index on a List");
    //     }
    //     Node NewElem = new Node(theData); 
    //     if(CursorElem == FrontElem)
    //     {
    //         FrontElem = NewElem;
    //         FrontElem.nextNode = CursorElem;
    //         CursorElem.prevNode = FrontElem;
    //         /*FrontElem = NewElem;
    //         CursorElem.prevNode = NewElem;
    //         NewElem.nextNode = CursorElem;*/
    //     }
    //     else
    //     {
    //         CursorElem.prevNode.nextNode = NewElem;
    //         NewElem.prevNode = CursorElem.prevNode;
    //         CursorElem.prevNode = NewElem;
    //         NewElem.nextNode = CursorElem;
    //     }
    //     indexOfCursor = (indexOfCursor + 1);
    //     listLength = (listLength + 1);
    // }
    
    // void insertAfter(int theData) // Inserts new element after cursor. Pre: length()>0, index()>=0    
    // {
    //     if(listLength <= 0)
    //     {
    //         throw new RuntimeException("List Error: insertBefore() was called on an empty List");    
    //     }
    //     if(indexOfCursor < 0)  // If this test passes, then I know that the cursor is defined
    //     {
    //         throw new RuntimeException("List Error: insertBefore() was called with an undefined index on a List");
    //     }
    //     Node NewElem = new Node(theData);
    //     if(CursorElem == BackElem) 
    //     { 
    //         BackElem = NewElem;
    //         BackElem.prevNode = CursorElem;
    //         CursorElem.nextNode = BackElem;
    //         /*BackElem = NewElem;
    //         CursorElem.nextNode = NewElem;
    //         NewElem.prevNode = CursorElem;*/
    //     }
    //     else
    //     {   
    //         CursorElem.nextNode.prevNode = NewElem;
    //         NewElem.nextNode = CursorElem.nextNode;
    //         CursorElem.nextNode = NewElem;
    //         NewElem.prevNode = CursorElem;
    //     }
    //     listLength = (listLength + 1);
    // }
    
    // void deleteFront() // Deletes the front element. Pre: length()>0    
    // {
    //     if(listLength <= 0)
    //     {
    //         throw new RuntimeException("List Error: insertBefore() was called on an empty List");    
    //     }
    //     if(listLength > 1)
    //     {
    //         if(CursorElem == FrontElem)
    //         {
    //             FrontElem = FrontElem.nextNode;
    //             FrontElem.prevNode = null;
    //             CursorElem = null;
    //             indexOfCursor = -1;
    //         }
    //         else
    //         {
    //             FrontElem = FrontElem.nextNode;
    //             FrontElem.prevNode = null;
    //         }
    //     }
    //     else  // listLength == 1
    //     {
    //         if(CursorElem == FrontElem)
    //         {
    //              FrontElem = null;
    //              BackElem = null;
    //              CursorElem = null;
    //              indexOfCursor = -1;
    //         }
    //         else
    //         {
    //             FrontElem = null;
    //             BackElem = null;
    //         }   
    //     }
    //     listLength = (listLength - 1);
    // }
   
    
    // void deleteBack() // Deletes the back element. Pre: length()>0    
    // {   
    //     if(listLength <= 0)
    //     {   
    //         throw new RuntimeException("List Error: insertBefore() was called on an empty List");    
    //     }
    //     if(listLength > 1)
    //     {
    //         if(CursorElem == BackElem)
    //         {
    //             BackElem = BackElem.prevNode;
    //             BackElem.nextNode = null;
    //             CursorElem = null;
    //             indexOfCursor = -1;
    //         }
    //         else
    //         {
    //             BackElem = BackElem.prevNode;
    //             BackElem.nextNode = null;
    //         }
    //     }
    //     else  // listLength == 1
    //     {
    //         if(CursorElem == BackElem)
    //         {
    //              FrontElem = null;
    //              BackElem = null;
    //              CursorElem = null;
    //              indexOfCursor = -1;
    //         }
    //         else
    //         {
    //             FrontElem = null;
    //             BackElem = null;
    //         }  
    //     }
    //     listLength = (listLength - 1);
    // }
    
    // void delete() // Deletes cursor element, making cursor undefined. Pre: length()>0, index()>=0    
    // {
    //     if(listLength <= 0)
    //     {
    //         throw new RuntimeException("List Error: insertBefore() was called on an empty List");    
    //     }
    //     if(indexOfCursor < 0)
    //     {
    //         throw new RuntimeException("List Error: insertBefore() was called with an undefined index on a List");
    //     }
    //     if(listLength > 1)
    //     {
    //         if(CursorElem == BackElem)
    //         {
    //             deleteBack();
    //         }
    //         else if(CursorElem == FrontElem)
    //         {
    //             deleteFront();
    //         }
    //         else
    //         {
    //             CursorElem.prevNode.nextNode = CursorElem.nextNode;
    //             CursorElem.nextNode.prevNode = CursorElem.prevNode;
    //             CursorElem = null;
    //             indexOfCursor = -1;
    //         }
    //     }
    //     else  // listLength == 1
    //     {
    //         if(CursorElem != BackElem && CursorElem != FrontElem)  // On the edges (null)
    //         {
    //             FrontElem = null;
    //             BackElem = null;
    //         }
    //         else
    //         {
    //             FrontElem = null;
    //             BackElem = null;
    //             CursorElem = null;
    //             indexOfCursor = -1;
    //         }
    //     }
    //     listLength = (listLength - 1);
    // }
    
    // // Other methods
    // public String toString() //  Overrides Object's toString method. Returns a String representation of this List consisting of a space separated sequence of integers, with front on left.
    // {
    //     Node NewNode = FrontElem;
    //     StringBuffer strBuff = new StringBuffer();        
    //     while(NewNode != null)
    //     {   
    //         strBuff.append(NewNode.toString());
    //         strBuff.append(" ");
    //         NewNode = NewNode.nextNode;
    //     }
    //     return new String(strBuff);
    // }
    
    // List copy() // Returns a new List representing the same integer sequence as this List. The cursor in the new list is undefined, regardless of the state of the cursor in this List. The List is unchanged. 
    // {   
    //     List NewList = new List();
    //     Node NewNode = this.FrontElem;
    //     while( NewNode != null ) // Will go until the end of this List
    //     {
    //         NewList.append(NewNode.theData);
    //         NewNode = NewNode.nextNode;   // set up next cycle (for previous line to be correct each time)
    //     }
    //     return NewList;
    // }   
    
    // List concat(List L) // Returns a new List which is the concatenation of this List followed by L. The cursor in the new List is undefined, regardless of the states of the cursors in the List and L. The states of this List and L are unchanged.
    // {
    //     List NewListTwo = copy(); // Makes a copy of this List so that the new concatenation will be its own entity of (List + L)
    //     Node NewNode = L.FrontElem;
    //     while( NewNode != null ) // Will go until the end of this List
    //     {
    //         NewListTwo.append(NewNode.theData);
    //         NewNode = NewNode.nextNode;   // set up next cycle (for previous line to be correct each time)
    //     }
    //     return NewListTwo;
    // }   
}