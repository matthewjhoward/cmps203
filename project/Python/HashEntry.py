class HashEntry:
    # key = None
    # value = None

    # next = HashEntry(None, None)

    def __init__(self, key, value):
        self.key = key
        self.value = value
        self.next = None