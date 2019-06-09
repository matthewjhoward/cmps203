from HashMapper import HashMapper
from User import User

def main():
    doMenu = True
    hashTable = HashMapper()
    if doMenu:
        lines = []
        f = open("input.txt", "r")
        
        for line in f:
            lines.append(line)

            

        for line in lines:
            tokens = line.split(",")

            firstname = tokens[0]
            lastname = tokens[1]
            username = tokens[2]
            password = tokens[3]

            user = User(firstname, lastname, username, password)
            hashTable.put(username, user)
        
        processMenu(hashTable) #need self?
        f.close()
    else:
        runTests(hashTable)
    return

def runTests(hashTable):
    from datetime import datetime
    from datetime import timedelta
    import csv
    
    start_time = datetime.now()

    # returns the elapsed milliseconds since the start of the program
    def millis():
        dt = datetime.now() - start_time
        ms = (dt.days * 24 * 60 * 60 + dt.seconds) * 1000 + dt.microseconds / 1000.0
        return ms
    
    users = []
    with open('input.txt', 'r') as infile:
        for line in infile:
            tokens = line.split(',')
            users.append(User(tokens[0], tokens[1], tokens[2], tokens[3]))
    
    add_times = []
    del_times = []
    undo_times = []
    i = 0
    start_time = datetime.now()
    for user in users:
        i+=1
        hashTable.put(user.username, user)

        if i%10 == 0:
            add_times.append(millis())
        
    print(add_times)
    i = 0
    start_time = datetime.now()
    for user in users:
        i+=1
        hashTable.remove(user.username)

        if i%10 == 0:
            del_times.append(millis())
    print(del_times)

    i = 0
    start_time = datetime.now()
    for user in users:
        i+=1
        hashTable.undoRemove()

        if i%10 == 0:
            undo_times.append(millis())
    print(undo_times)

    with open('output.txt', 'w') as fout:
        writer = csv.writer(fout)
        writer.writerow(add_times)
        writer.writerow(del_times)
        writer.writerow(undo_times)





def processMenu(hashTable):
    ynAnswer = None
    letterChoice = None

    while True:
        print("What would you like to do with the User database? Select a letter from the list below")
        print("A. Insert a new User")
        print("B. Find an existing User")
        print("C. Remove an existing User")
        print("D. Undo removal of User")
        print("E. Print efficiency data")
        print("F. Quit program")
        # print("Please input your selected letter here, the press Enter: ")
        letterChoice = input("Please input your selected letter here, the press Enter: ")
        print(letterChoice)

        # options = {
        #     "a" : aA(hashTable), #might need to pass parameters?
        #     "A" : aA(hashTable),
        #     "b" : bB(hashTable),
        #     "B" : bB(hashTable),
        #     "c" : cC(hashTable),
        #     "C" : cC(hashTable),
        #     "d" : dD(hashTable),
        #     "D" : dD(hashTable),
        #     "e" : eE(hashTable),
        #     "E" : eE(hashTable)
        # }

        if letterChoice == "a" or letterChoice == "A":
            addUser(hashTable)
        elif letterChoice == "b" or letterChoice == "B":
            user = getUser(hashTable)
            if user is not None:
                print("First Name: " + user.getFirstname())
                print("Last Name: " + user.getLastname())
                print("Username: " + user.getUsername())
                print("Password: " + user.getPassword())
        elif letterChoice == "c" or letterChoice == "C":
            removeUser(hashTable)
        elif letterChoice == "d" or letterChoice == "D":
            hashTable.undoRemove()
        elif letterChoice == "e" or letterChoice == "E":
            printEfficiencyData(hashTable)
        elif letterChoice == "F" or letterChoice == "f":
            break
        else:
            print("Letter not valid!")

        # validLetters = ["a", "A", "b", "B", "c", "C", "d", "D", "e", "E", "f", "F"]

        # if letterChoice in validLetters:
            # options[letterChoice]()

        # if(letterChoice == "F" or letterChoice == "f"):
        #     break

def aA(hashTable):
    addUser(hashTable)

def bB(hashTable):
    user = getUser(hashTable)
    if user is not None:
        print("First Name: " + user.getFirstname())
        print("Last Name: " + user.getLastname())
        print("Username: " + user.getUsername())
        print("Password: " + user.getPassword())

def cC(hashTable):
    removeUser(hashTable)

def dD(hashTable):
    hashTable.undoRemove()

def eE(hashTable):
    printEfficiencyData(hashTable)

# def fF():
    
    
def addUser(hashTable):
    ynAnswer = None
    firstname = None
    lastname = None
    username = None
    password = None

    while True:
        firstname = input("What will the first name be for the User you would like to add: ")
        lastname = input("What will the last name be for the User you would like to add: ")
        username = input("What will the username be for the User you would like to add: ")

        if hashTable.get(username) is not None:
            while hashTable.get(username) is not None:
                username = input("There is already a User with that username in the hash table! Please try another username: ")

        password = input("What will the password be for the User you would like to add: ")

        user = User(firstname, lastname, username, password)
        hashTable.put(username, user)

        print("User " + username + " added to the hash table!")
        ynAnswer = input("Would you like to add another User? Input Yes('Y' or 'y') or No('N' or 'n'): ")

        if(ynAnswer == "n" or ynAnswer == "N"):
            break

def getUser(hashTable):
    username = None

    username = input("What is the username of the User you would like to retrieve: ")
    user = hashTable.get(username)

    if user is None:
        print("Could not find User with username " +  username + ".")
    else:
        print("User " + username + " retrived!")
        return user

    return None

def removeUser(hashTable):
    ynAnswer = None
    username = None

    while True:
        username = input("What is the username of the User you would like to remove: ")

        if hashTable.get(username) is None:
            print("User not found!")
        else:
            hashTable.remove(username)
            if hashTable.get(username) is None:
                print("User " + username + " removed from the hash table!")

        ynAnswer = input("Would you like to try removing another User? Input Yes('Y' or 'y') or No('N' or 'n'): ")

        if(ynAnswer == "n" or ynAnswer == "N"):
            break

def printEfficiencyData(hashTable):
    f = 0

if __name__ == "__main__":
    main()

