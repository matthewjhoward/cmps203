from Noder import Noder

class LinkedStacker:
    # top = None

    def __init__(self):
        self.top = Noder(None)

    def isEmpty(self):
        if self.top is None:
            return True
        else:
            return False
    
    def push(self, data):
        newNode = Noder(data)
        newNode.nextNode = self.top
        self.top = newNode
        return True

    def pop(self):
        if self.isEmpty() == True:
            return False

        nodeToDelete = self.top
        self.top = self.top.nextNode
        nodeToDelete = None
        return True

    def peek(self):
        return self.top.data