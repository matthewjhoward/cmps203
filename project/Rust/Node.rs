pub struct Node<V> {
    pub data: V,
    pub nextNode: Node, //Node<V>?
}

impl<V> Node<V> {
    pub fn getData(self) -> String {
        return self.data;
    }
}