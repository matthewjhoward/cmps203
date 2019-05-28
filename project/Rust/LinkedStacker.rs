// mod Node;
use Node::Node;

pub struct LinkedStacker<V> {
    pub top: Node, //Node<V>
}

impl<V> LinkedStacker<V> {
    pub fn isEmpty(self) -> bool {
        match self.top {
            Some(i) => {
                return false;
            },
            _ => return true, 
        };
        return true;
    }

    pub fn push(self, data : V) -> bool {
        let mut newNode = Node::Node{
            data : data, 
        };
        newNode.nextNode = self.top;
        self.top = newNode;
        return true;
    }

    pub fn pop(self) -> bool {
        if self.isEmpty() == true {
            return false
        }

        let mut nodeToDelete = self.top;
        self.top = self.top.nextNode;
        match nodeToDelete {
            _ => None, 
        };
        return true;
    }

    pub fn peek(self) -> V {
        return self.top.data;
    }
}