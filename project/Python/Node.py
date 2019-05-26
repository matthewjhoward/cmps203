class Node:
    # Fields
    data = None

    nextNode = Node(None)

    # Constructor
    def __init__(self, data):
        self.data = data

    def getData(self):
        return data