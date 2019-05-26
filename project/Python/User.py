class User:
    # Fields
    firstname = None
    lastname = None
    username = None
    password = None

    # Constructor
    def __init__(self, firstname, lastname, username, password):
        self.firstname = firstname
        self.lastname = lastname
        self.username = username
        self.password = password

    # Setters
    def setFirstname(self, firstname):
        self.firstname = firstname

    def setLastname(self, lastname):
        self.lastname = lastname

    def setUsername(self, username):
        self.username = username

    def setPassword(self, password):
        self.password = password

    # Getters
    def getFirstname(self):
        return firstname

    def getLastname(self):
        return lastname

    def getUsername(self):
        return username

    def getPassword(self):
        return password

    