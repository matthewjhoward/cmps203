class LinkedStacker:
    top = Node(None)

    def __init__(self):
        top = None

    def isEmpty(self):
        if top is None:
            return True
        else
            return False
    
    def push(self, data):
        newNode = Node(data)
        newNode.nextNode = top
        top = newNode
        return True

    def pop(self):
        if isEmpty() == True:
            return False

        nodeToDelete = top
        top = top.nextNode
        nodeToDelete = None
        return True

    def peek(self):
        return top.data