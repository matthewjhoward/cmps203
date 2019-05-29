import java.io.File

open class User()
class NullUser() : User()

class EntryUser(val firstname: String, val lastname: String, val username: String, val password: String) : User()

open class Node()
class NullNode() : Node()
class NodeEntry(var key: String, var value: User) : Node() {
  var next: Node = NullNode()
}

class LinkedStacker(){
  var top: Node = NullNode()

  fun isEmpty() : Boolean {
    return top is NullNode
  }
  fun push(key: String, value: User) : Boolean {
    var newNode = NodeEntry(key, value)
    newNode.next = top
    top = newNode
    return true
  }

  fun pop() : Boolean {
    if(isEmpty()){
      return false
    }
    var toDelete = top as NodeEntry
    top = toDelete.next
    return true
  }
  fun peek() : Node {
    return top
  }
}

open class Entry()
class NullEntry() : Entry()

class HashEntry(var key: String, var value: User) : Entry() {
  var next: Entry = NullEntry()
}

class HashMapper(){
  
  val nullEntry = NullEntry()
  var buckets = ArrayList<Entry>()
  var undoStack: LinkedStacker = LinkedStacker()
  var nBuckets = 20
  var size = 0
  init {
    for (i in 0..nBuckets){
      buckets.add(nullEntry)
    }
  }

  fun bucketIndex(key: String) : Int {
    val hash = key.hashCode() and 0x7FFFFFFF
    val idx = hash % nBuckets
    return idx
  }

  fun put(key: String, value: User) {
    val idx = bucketIndex(key)
    
    var head = buckets.get(idx)

    while (head !is NullEntry){
      var temp = head as HashEntry
      if(temp.key == key){
        temp.value = value
        return
      }
      head = temp.next
    }

    size +=1
    head = buckets.get(idx)
    var add = HashEntry(key, value)
    add.next = head
    buckets.set(idx, add)
    resizeTable()
  }

  fun get(key: String) : User {
    val idx = bucketIndex(key)
    var head = buckets.get(idx)

    while (head !is NullEntry){
      var temp = head as HashEntry
      if(temp.key == key){
        return temp.value 
      }
      head = temp.next
    }
    return NullUser()
  }

  fun remove(key: String) : User {
    val idx = bucketIndex(key)
    var head = buckets.get(idx)
    var prev: Entry = NullEntry()
    
    while(head !is NullEntry){
      var temp = head as HashEntry
      if(temp.key == key){
        break
      }
      prev = temp
      head = temp.next
    }
    if (head is NullEntry){
      return NullUser()
    }
    var temp = head as HashEntry
    undoStack.push(temp.key, temp.value)
    size -=1
    if (prev !is NullEntry){
      prev = prev as HashEntry
      prev.next = temp.next
    }else{
      buckets.set(idx, temp.next)
    }
    return temp.value
  }

  fun undoRemove() : Boolean {
    if(undoStack.isEmpty()){
      println("There are no removals to undo!")
      return false
    }else{
      var user = undoStack.peek() as NodeEntry
      put(user.key, user.value)
      println("Removal of user " + user.key + " is undone!")
      undoStack.pop()
      return true
    }
  }

  fun resizeTable() {
    if( (size*1.0)/nBuckets >= 0.6){
      var tempBuckets = buckets
      buckets = ArrayList<Entry>()
      nBuckets = 2*nBuckets
      size = 0
      for(i in 0..nBuckets){
        buckets.add(nullEntry)
      }

      for(i in 0..tempBuckets.size){
        var temp = tempBuckets.get(i)
        while(temp !is NullEntry){
          temp = temp as HashEntry
          put(temp.key, temp.value)
          tempBuckets.set(i, temp.next)
        }
      }
    }
  }
}
fun addUser(table: HashMapper){
  var ynAnswer: String
  var firstname: String
  var lastname: String
  var username: String
  var password: String
  do {
    println("What will the first name be for the User you would like to add: ")
    firstname = readLine() as String
    println("What will the last name be for the User you would like to add: ")
    lastname = readLine() as String
    println("What will the username be for the User you would like to add: ")
    username = readLine() as String
    if(table.get(username) !is NullUser){
      while(table.get(username) !is NullUser){
        println("There is already a User with that username in the hash table! Please try another username: ")
        username = readLine() as String
      }
    }
    println("What will the password be for the User you would like to add: ")
    password = readLine() as String
    var user: EntryUser = EntryUser(firstname, lastname, username, password)
    table.put(username, user)
    println("User $username added to the hash table!")
    println("Would you like to add another User? Input Yes('Y' or 'y') or No('N' or 'n'): ")
    ynAnswer = readLine() as String
    ynAnswer = ynAnswer.toLowerCase()


  } while(!(ynAnswer == "n"));

}

fun getUser(table: HashMapper) : User {
  var username: String
  println("What is the username of the User you would like to retrieve?: ")
  username = readLine() as String
  var user = table.get(username)
  if(user is NullUser){
    println("Could not find User with username: " + username)
    return user
  }else{
    println("User ($username) retrieved!")
    return user
  }
}

fun removeUser(table: HashMapper){
  var ynAnswer: String
  var username: String
  do{
    println("What is the username of the User you would like to remove: ");
    username = readLine() as String
    if(table.get(username) is NullUser){
      println("User not found!")
    }else{
      table.remove(username)
      if(table.get(username) is NullUser){
        println("User " + username + " removed from the hash table!")
      }
    }
    println("Would you like to try removing another User? Input Yes('Y' or 'y') or No('N' or 'n'): ")
    ynAnswer = readLine() as String
    ynAnswer = ynAnswer.toLowerCase()

  } while(ynAnswer != "n")
}


fun processMenu(table: HashMapper){
  var letterChoice: String
  do {
    println("What would you like to do with the User database? Select a letter from the list below")
    println("A. Insert a new User")
    println("B. Find an existing User")
    println("C. Remove an existing User")
    println("D. Undo removal of User")
    println("E. Print efficiency data")
    println("F. Quit program")
    println("Please input your selected letter here, then press Enter: ")
    letterChoice = readLine() as String
    
    letterChoice = letterChoice.toLowerCase()

    when(letterChoice){
      "a" -> addUser(table)
      "b" -> {
        var user = getUser(table)
        if (user !is NullUser){
          user = user as EntryUser
          println("First Name: " + user.firstname)
          println("Last Name: " + user.lastname)
          println("Username: " + user.username)
          println("Password: " + user.password)
        }
      }
      "c" -> removeUser(table)
      "d" -> table.undoRemove()
      "e" -> println("Nothing here...")
      "f" -> println("Quitting...")
      else -> println("Input not valid!")
    }
  } while(!(letterChoice == "f"));
}


fun main() {
  var table = HashMapper()
  
  val filename = "input.txt"
  var lines = File(filename).readLines()
  for(line in lines){
    var tokens = line.split(",")
    var firstname = tokens.get(0)
    var lastname = tokens.get(1)
    var username = tokens.get(2)
    var password = tokens.get(3)
    var user: EntryUser = EntryUser(firstname, lastname, username, password)
    table.put(username, user)
  }
  println(table.size)
  processMenu(table)
}
main()