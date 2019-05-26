#ifndef _LINKED_STACKER
#define _LINKED_STACKER

#include "Node.h"

template<class V>
class LinkedStacker {
	private:
		Node<V>* top;

	public:
		LinkedStacker() { top = NULL; }
		~LinkedStacker() { while (top != NULL) { pop(); } }

		bool isEmpty();
		bool push(V data);
		bool pop();
		V peek();
};

template<class V>
bool LinkedStacker<V>::isEmpty() {
	if (top == NULL) {
		return true;
	}
	else {
		return false;
	}
}

template<class V>
bool LinkedStacker<V>::push(V data)
{
	Node<V>* newNode = new Node<V>(data);
	newNode->nextNode = top;
	top = newNode;
	return true;
}

template<class V>
bool LinkedStacker<V>::pop()
{
	if (isEmpty() == true) {
		return false;
	}

	Node<V>* nodeToDelete = top;
	top = top->nextNode;
	nodeToDelete = NULL;
	delete nodeToDelete;
	return true;
}

template<class V>
V LinkedStacker<V>::peek() {
	return top->data;
}

#endif
