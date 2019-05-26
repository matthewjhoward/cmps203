class HashMapper:
    # Fields
    buckets = []
    undoStack = LinkedStack()

    nBuckets = None
    size = None

    # Constructor
    def __init__(self):
        size = 0
        nBuckets = 20
        buckets = []

        for i in range(0, nBuckets): # May not needt hese two lines, or may need to declare the 2d array in a nother fashion
            buckets.append(None)

    def size(self):
        return size

    def bucketIndex(self, key):
        hash = key.hashCode() # what is the equivalent in python?