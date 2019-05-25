#ifndef _LINKED_STACK
#define _LINKED_STACK

#include "StackInterface.h"
#include "Node.h"

template<class ItemType>
class LinkedStack : public StackInterface<ItemType>
{
private:
	Node<ItemType>* topPtr;

public:
	LinkedStack() { topPtr = 0; }
	LinkedStack(const LinkedStack<ItemType>& aStack);
	~LinkedStack() { while (topPtr != 0) { pop(); } }

	bool isEmpty() const { return (topPtr == 0); }
	bool push(const ItemType& newItem);
	bool pop();
	ItemType peek() const { return topPtr->getItem(); }
};

template<class ItemType>
LinkedStack<ItemType>::LinkedStack(const LinkedStack<ItemType>& aStack)
{
	Node<ItemType>* origChainPtr = aStack.topPtr;  // original chain is linked list to be copied

	if (origChainPtr == 0)
		topPtr = 0;
	else
	{
		topPtr = new Node<ItemType>();				// copy top item
		topPtr->setItem(origChainPtr->getItem());

		Node<ItemType>* newChainPtr = topPtr;			// new chain starts at top
		origChainPtr = origChainPtr->getNext();		// start with next item in original chain

		while (origChainPtr != 0)						// copy rest of list
		{
			ItemType nextItem = origChainPtr->getItem();
			Node<ItemType>* newNodePtr = new Node<ItemType>(nextItem);	// create new node
			newChainPtr->setNext(newNodePtr);							// add new node to list
			newChainPtr = newChainPtr->getNext();						// walk to next item
			origChainPtr = origChainPtr->getNext();
		}
		newChainPtr->setNext(0);						// set end of list
	}
}


template<class ItemType>
bool LinkedStack<ItemType>::push(const ItemType& newItem)
{
	Node<ItemType>* newNodePtr = new Node<ItemType>(newItem);
	newNodePtr->setNext(topPtr);
	topPtr = newNodePtr;
	return true;
}


template<class ItemType>
bool LinkedStack<ItemType>::pop()
{
	if (isEmpty())
		return false;

	Node<ItemType>* nodeToDeletePtr = topPtr;
	topPtr = topPtr->getNext();
	delete nodeToDeletePtr;
	return true;
}

#endif
