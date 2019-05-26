#ifndef _NODE
#define _NODE

template<class V>
class Node {
	private:
		V data;
		Node<V>* nextNode;

	public:
		Node(V data)				{ this->data = data; }
		V getData()					{ return data; }
};

#endif
