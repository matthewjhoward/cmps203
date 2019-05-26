from LinkedStacker import LinkedStacker
from HashEntry import HashEntry

class HashMapper:
    # Fields
    # buckets = []
    # undoStack = LinkedStacker()

    # nBuckets = None
    # size = None

    # Constructor
    def __init__(self):
        self.size = 0
        self.nBuckets = 20
        self.buckets = []

        for i in range(0, self.nBuckets): # May not needt hese two lines, or may need to declare the 2d array in a nother fashion
            self.buckets.append(None)
            
        self.undoStack = LinkedStacker()

    def size(self):
        return size

    def bucketIndex(self, key):
        hashCode = hash(key)
        idx = hashCode % self.nBuckets
        return idx

    def put(self, key, value):
        idx = self.bucketIndex(key)
        head = HashEntry(None, None)
        head = self.buckets[idx]

        while (head is not None):
            if head.key == key:
                head.value = value
                return
            head = head.next
        
        self.size += 1
        head = self.buckets[idx]
        add = HashEntry(key, value)
        add.next = head
        self.buckets[idx] = add
        self.resizeTable()

    def get(self, key):
        idx = self.bucketIndex(key)
        head = HashEntry(None, None)
        head = self.buckets[idx]
        while head is not None:
            if head.key == key:
                return head.value
            head = head.next
        return None

    def remove(self, key):
        idx = self.bucketIndex(key)
        head = HashEntry(None, None)
        head = self.buckets[idx]
        prev = None
        while head is not None:
            if head.key == key:
                break
            prev = head
            head = head.next

        if head is None:
            return None

        self.undoStack.push(head.value) 
        self.size -= 1

        if prev is not None:
            prev.next = head.next
        else:
            self.buckets[idx] = head.next

        return head.value

    def undoRemove(self):
        if self.undoStack.isEmpty() == True:
            print("There are no more removals to undo!")
            return False
        else:
            user = self.undoStack.peek()
            username = user.getUsername()
            self.put(username, user)

            print("Removal of user " + username + " is undone!")

            self.undoStack.pop()

            return True

    def resizeTable(self):
        if (self.size * 1.0) / self.nBuckets >= 0.6:
            tempBuckets = HashEntry(None, None)
            tempBuckets = self.buckets
            self.buckets = [] # especially here, might need to refer to self.buckets
            self.nBuckets = 2*nBuckets
            self.size = 0
            for i in range(0, self.nBuckets):
                self.buckets.append(None)
            for entry in tempBuckets:
                while entry is not None:
                    self.put(entry.key, entry.value)
                    entry = entry.next