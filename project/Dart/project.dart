import 'dart:io';
// for step 3:
import 'dart:async';
import 'dart:convert';
import 'HashMapper.dart';
import 'User.dart';
import 'dart:collection';

void main() {
  // print("Hello, World");
  File data = new File("input.txt");
  var hashTable = new HashMapper();
  var doMenu = false;
  if (doMenu){
    data.readAsLines().then(processLines);
  }else{
    runTests();
  }
  

  return;
}

runTests(){
  File data = new File("input.txt");
  data.readAsLines().then(processLines2);
}

processLines2(List<String> fileLines){
  List<User> users = new List<User>();
  List<double> add_times = new List<double>();
  List<double> del_times = new List<double>();
  List<double> undo_times = new List<double>();
  for (var line in fileLines){
      List tokens = line.split(",");
      User user = new User(tokens[0], tokens[1], tokens[2], tokens[3]);
      users.add(user);
  }
  var hashTable = new HashMapper();
  var i = 0;
  var start_time = new DateTime.now().microsecondsSinceEpoch;
  for(var user in users){
    i = i+1;
    hashTable.put(user.username, user);

    if(i%10 == 0){
      add_times.add((new DateTime.now().microsecondsSinceEpoch - start_time)/1000.0);
    }
  }
  i = 0;
  start_time = new DateTime.now().microsecondsSinceEpoch;
  for(var user in users){
    i = i+1;
    hashTable.remove(user.username);

    if(i%10 == 0){
      del_times.add((new DateTime.now().microsecondsSinceEpoch - start_time)/1000.0);
    }
  }
  i = 0;
  start_time = new DateTime.now().microsecondsSinceEpoch;
  for(var user in users){
    i = i+1;
    hashTable.undoRemove();

    if(i%10 == 0){
      undo_times.add((new DateTime.now().microsecondsSinceEpoch - start_time)/1000.0);
    }
  }
  var out = new File("output.txt");
  var sink = out.openWrite();
  sink.write(add_times.join(",")+"\n");
  sink.write(del_times.join(",")+"\n");
  sink.write(undo_times.join(",")+"\n");
  sink.close();
}

processLines(List<String> fileLines) {
  // process lines:
  var lines = [];

  for (var line in fileLines) {
    // print(line);
    lines.add(line);
  }

  // var hashTable = new Map();
  var hashTable = new HashMapper();

  for (var line in lines) {
    List tokens = line.split(",");

    String firstname = tokens[0];
    String lastname = tokens[1];
    String username = tokens[2];
    String password = tokens[3];

    var user = new User(firstname, lastname, username, password);
    hashTable.put(username, user);
    // print(tokens[0]);
    // print(tokens[1]);
    // print(tokens[2]);
    // print(tokens[3]);
  }

  processMenu(hashTable);

  return;
}

// handleError(e) {
//   print("Error: Could not read file");
// }

processMenu(hashTable) {
  String ynAnswer = "";
  String letterChoice = "";

  while (true) { 
    print("What would you like to do with the User database? Select a letter from the list below");
    print("A. Insert a new User");
    print("B. Find an existing User");
    print("C. Remove an existing User");
    print("D. Undo removal of User");
    print("E. Print efficiency data");
    print("F. Quit program");
    print("Please input your selected letter here, then press Enter: ");
    letterChoice = stdin.readLineSync();

    switch(letterChoice) {
      case "a": 
      case "A": {
        addUser(hashTable);
        // break;
      }
      break;
      
      case "b": 
      case "B": {
        var user = getUser(hashTable);
        print(user);
        if (user != null) {
            print("First Name: " + user.getFirstname());
            print("Last Name: " + user.getLastname());
            print("Username: " + user.getUsername());
            print("Password: " + user.getPassword());
        }
      }
      break;

      case "c": 
      case "C": {
        removeUser(hashTable);
      }
      break;

      case "d": 
      case "D": {
        hashTable.undoRemove();
      }
      break;

      case "e": 
      case "E": {
        printEfficiencyData(hashTable);
      }
      break;

      case "f":
      case "F": {
        return;
      }    
      break;

      default: {
        print("Letter not valid!");
      }
      break;
    }
    if ((letterChoice == "N") || (letterChoice == "n")) {
      break;
    }

  } //while(identical(letterChoice, "F") == false && identical(letterChoice, "f") == false); 
}

addUser(hashTable) {
  String ynAnswer = "";
  String firstname;
  String lastname;
  String username;
  String password;

  while (true) {
    print("What will the first name be for the User you would like to add: ");
    firstname = stdin.readLineSync();

    print("What will the last name be for the User you would like to add: ");
    lastname = stdin.readLineSync();

    print("What will the username be for the User you would like to add: ");
    username = stdin.readLineSync();

    if (hashTable.get(username) != null) {
        print(hashTable.get(username));
        // print("HERE");
        while(hashTable.get(username) != null) {
            print("There is already a User with that username in the hash table! Please try another username: ");
            username = stdin.readLineSync();
        }
    }

    print("What will the password be for the User you would like to add: ");
    password = stdin.readLineSync();

    var user = new User(firstname, lastname, username, password);
    hashTable.put(username, user);

    print("User " + username + " added to the hash table!");
    print("Would you like to add another User? Input Yes('Y' or 'y') or No('N' or 'n'): ");
    ynAnswer = stdin.readLineSync();

    if ((ynAnswer == "N") || (ynAnswer == "n")) {
      break;
    }

  } //while ((ynAnswer != "N") || (ynAnswer != "n"));

  return;
}

getUser(hashTable) {
  String username;

  print("What is the name of the User you would like to retrieve: ");
  username = stdin.readLineSync();
  var user = hashTable.get(username);
  // print(user);

  if (user == null) {
      print("Could not find User with username " +  username + ".");
  }
  else {
      print("User " + username + " retrived!");
      // hashTable.putIfAbsent(username, ()=>user); //put it back
      return user;
  }

  // return null;
}

removeUser(hashTable) {
  String ynAnswer = "";
  String username;

  while (true) {
    print("What is the username of the User you would like to remove: ");
    username = stdin.readLineSync();

    if (hashTable.get(username) == null) {
      print("User not found! ");
    }
    else {
      hashTable.remove(username);
      if (hashTable.get(username) == null) {
          print("User " + username + " removed from the hash table!");
      }
    }

    print("Would you like to removing another another User? Input Yes('Y' or 'y') or No('N' or 'n'): ");
    ynAnswer = stdin.readLineSync();

    if ((ynAnswer == "N") || (ynAnswer == "n")) {
      break;
    }

  } //while ((ynAnswer != "N") || (ynAnswer != "n"));

  return;
}

printEfficiencyData(hashTable) {
  
}