import scala.collection.mutable.ArrayBuffer
import scala.collection.JavaConversions._
import java.util.ArrayList
import util.control.Breaks._
import scala.io.Source
import scala.io.StdIn
import java.io._



class User(var firstname:String, var lastname:String, var username:String, var password:String)

class HashEntry[K,V](var key: K, var value: V){
  var next:HashEntry[K,V] = null
}
class Node[K, V](var key: K, var value:V){
  var next:Node[K,V] = null
}
class LinkedStacker[K,V]() {
  var top:Node[K,V] = null

  def isEmpty() : Boolean = {
    return top == null
  }

  def push(key:K, data:V) : Boolean = {
    var newNode:Node[K,V] = new Node[K,V](key, data)
    newNode.next = top
    top = newNode
    return true
  }
  def pop() : Boolean = {
    if (isEmpty()){
      return false
    }
    var nodeToDelete:Node[K,V] = top
    top = top.next
    nodeToDelete = null
    return true
  }

  def peek() : Node[K,V] = {
    return top
  }
}

class HashMapper[K,V]() {
  var buckets = new ArrayList[HashEntry[K,V]]()
  var undoStack = new LinkedStacker[K,V]()
  var nBuckets:Int = 20
  var size:Int = 0

  for(i <- 0 to nBuckets){
    buckets.add(null)
  }


  def bucketIndex(key: K) : Int = {
    val hash = key.hashCode() & 0x7fffffff
    val idx = hash % nBuckets
    return idx
  }

  def put(key: K, value: V){
    val idx = bucketIndex(key)
    var head = buckets.get(idx)

    while (head != null){
      if(head.key.equals(key)){
        head.value = value
        return
      }
      head = head.next
    }

    size += 1
    head = buckets.get(idx)
    var add = new HashEntry[K,V](key, value)
    add.next = head
    buckets.set(idx, add)
    resizeTable() 
  }

  def get(key: K) : Option[V] = {
    val idx = bucketIndex(key)
    var head = buckets.get(idx)
    while (head != null){
      if(head.key.equals(key)){
        return Some(head.value)
      }
      head = head.next
    }
    return None
  }

  def remove(key: K) : Option[V] = {
    val idx = bucketIndex(key)
    var head = buckets.get(idx)
    var prev: HashEntry[K,V] = null
    breakable {
      while (head != null){
        if (head.key.equals(key)){
          break
        }
        prev = head
        head = head.next
      }
    }
    if (head == null){
      return None
    }

    undoStack.push(head.key, head.value)
    size -= 1
    if (prev != null){
      prev.next = head.next
    }else{
      buckets.set(idx, head.next)
    }
    return Some(head.value)
    
  }

  def undoRemove() : Boolean = {
    if (undoStack.isEmpty()) {
      println("There are no removals to undo!")
      return false
    }else{
      var user: Node[K,V] = undoStack.peek()
      put(user.key, user.value)
      // println("Removal of user " + user.key + " is undone!")
      undoStack.pop()
      return true
    }
  }

  def resizeTable() {
    if ( (size*1.0)/nBuckets >= 0.6){
      //This may be a problem in scala, not sure if it is
      var tempBuckets = buckets
      buckets = new ArrayList[HashEntry[K,V]]()
      nBuckets = 2*nBuckets
      size = 0
      for( i <- 0 to nBuckets){
        buckets.add(null)
      }
      for( i <- 0 to tempBuckets.length-1){
        while (tempBuckets(i) != null){
          put(tempBuckets(i).key, tempBuckets(i).value)
          tempBuckets(i) = tempBuckets(i).next
        }
      }
      // for (entry <- tempBuckets){
      //   while (entry != null){
      //     put(entry.key, entry.value)
      //     entry = entry.next
      //   }
      // }
    }
  }



}


object Project {
  def main(args: Array[String]) : Unit = {
    var doMenu = true
    var table = new HashMapper[String, User]()
    if (doMenu) {
      
      val filename = "input.txt"
      var lines = Source.fromFile(filename).getLines
      for (line <- lines){
        // println(line)
        var tokens = line.split(",")
        var firstname = tokens(0)
        var lastname = tokens(1)
        var username = tokens(2)
        var password = tokens(3)
        var user:User = new User(firstname, lastname, username, password)
        table.put(username, user)

      }
      println(table.size)
      processMenu(table)
    }else{
      runTests(table)
    }
  }

  def runTests(hashTable: HashMapper[String,User]) {
    
    val filename = "input.txt"
    var lines = Source.fromFile(filename).getLines
    var users = new ArrayList[User]()
    var add_times = new ArrayList[Double]()
    var del_times = new ArrayList[Double]()
    var undo_times = new ArrayList[Double]()
    for (line <- lines){
      var tokens = line.split(",")
      var user:User = new User(tokens(0), tokens(1), tokens(2), tokens(3))
      users.add(user)
    }
    var i = 0
    var start_time = System.nanoTime()
    for (user <- users){
      i = i+1
      hashTable.put(user.username, user)

      if (i%10 == 0){
        add_times.add((System.nanoTime()-start_time)/1000000.0)
      }
    }
    i = 0
    start_time = System.nanoTime()
    for (user <- users){
      i = i+1
      hashTable.remove(user.username)

      if (i%10 == 0){
        del_times.add((System.nanoTime()-start_time)/1000000.0)
      }
    }
    i = 0
    start_time = System.nanoTime()
    for (user <- users){
      i = i+1
      hashTable.undoRemove()

      if (i%10 == 0){
        undo_times.add((System.nanoTime()-start_time)/1000000.0)
      }
    }
    val file = new File("output.txt")
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(add_times.mkString(","))
    bw.newLine()
    bw.write(del_times.mkString(","))
    bw.newLine()
    bw.write(undo_times.mkString(","))
    bw.newLine()
    bw.close()
    // println(undo_times.mkString(","))
    // theStrings.mkString(",")
  }

  def processMenu(hashTable: HashMapper[String,User]) {
    var ynAnswer: String = ""
    var letterChoice: String = ""
    
    do {
      println("What would you like to do with the User database? Select a letter from the list below")
      println("A. Insert a new User")
      println("B. Find an existing User")
      println("C. Remove an existing User")
      println("D. Undo removal of User")
      println("E. Print efficiency data")
      println("F. Quit program")
      println("Please input your selected letter here, then press Enter: ")
      letterChoice = StdIn.readLine()
      letterChoice = letterChoice.toLowerCase()
      letterChoice match {
        case "a" => addUser(hashTable)
        case "b" => {
          var user:User = getUser(hashTable) match {
            case Some(i) => i
            case None => null
          }
          // var user = getUser(hashTable)
          if (user != null) {

            println("First Name: " + user.firstname)
            println("Last Name: " + user.lastname)
            println("Username: " + user.username)
            println("Password: " + user.password)
          }
        }
        case "c" => removeUser(hashTable)
        case "d" => hashTable.undoRemove()
        case "e" => println("Nothing Here. :'(")
        case "f" => println("Quitting...")
        case _ => println("Input not valid!")
      }


    }
    while( !(letterChoice == "F" || letterChoice == "f") )
  }

  def addUser(hashTable: HashMapper[String,User]) {
    var ynAnswer = ""
    var firstname,lastname, username,password = ""
    do {
      println("What will the first name be for the User you would like to add: ")
      firstname = StdIn.readLine()
      println("What will the last name be for the User you would like to add: ")
      lastname = StdIn.readLine()
      println("What will the username be for the User you would like to add: ")
      username = StdIn.readLine()

      if(hashTable.get(username) != None){
        while(hashTable.get(username) != None){
          println("There is already a User with that username in the hash table! Please try another username: ")
          username = StdIn.readLine()
        }
      }
      println("What will the password be for the User you would like to add: ")
      password = StdIn.readLine()

      var user: User = new User(firstname, lastname, username, password)
      hashTable.put(username, user)

      println("User " + username + " added to the hash table!");
      println("Would you like to add another User? Input Yes('Y' or 'y') or No('N' or 'n'): ")
      ynAnswer = StdIn.readLine()




    }
    while(!(ynAnswer == "n" || ynAnswer == "N"))
  }
  def getUser(hashTable: HashMapper[String,User]) : Option[User] = {
    var username: String = ""
    println("What is the username of the User you would like to retrieve?: ")
    username = StdIn.readLine()
    var user: User = hashTable.get(username) match {
      case Some(i) => i
      case None => null
    }
    if (user == null){
      println("Could not find User with username: " + username)
      return None
    }else{
      println("User (" + username + ") retrieved!")
      return Some(user)
    }


    

    
    
  }

  def removeUser(hashTable: HashMapper[String,User]) {
    var ynAnswer = ""
    var username = ""
    do {
      println("What is the username of the User you would like to remove: ");
      username = StdIn.readLine()
      if(hashTable.get(username) == None){
        println("User not found!")
      }else{
        hashTable.remove(username)
        if(hashTable.get(username) == None){
          println("User " + username + " removed from the hash table!")
        }
      }

      println("Would you like to try removing another User? Input Yes('Y' or 'y') or No('N' or 'n'): ")
      ynAnswer = StdIn.readLine()
    }
    while(!(ynAnswer == "n" || ynAnswer == "N"))
  }
}