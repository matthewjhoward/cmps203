use std::io::{self, BufReader};
use std::io::prelude::*;
use std::fs::File;
use std::collections::HashMap;
use std::ptr;

mod User;
// use User::User;

fn main() -> io::Result<()> {
    // println!("Hello!");
    let f = File::open("input.txt")?;
    let f = BufReader::new(f);

    let mut lines = Vec::new();

    for line in f.lines() {
        // println!("{}", line.unwrap());
        lines.push(line.unwrap());
    }

    let mut hashTable = HashMap::new();

    for line in &lines {
        // let mut tokens = Vec::new();
        // tokens.push(line.split([','].as_ref()).collect());

        let tokens: Vec<_> = line.split([','].as_ref()).collect();

        let mut firstnm = &tokens[0];
        println!("{}", &tokens[0]);
        let mut lastnm = &tokens[1];
        println!("{}", &tokens[1]);
        let mut usernm = &tokens[2];
        println!("{}", &tokens[2]);
        let mut passwd = &tokens[3];
        println!("{}", &tokens[3]);

        let user = User::User {
            firstname : firstnm.to_string(), 
            lastname : lastnm.to_string(),         
            username : usernm.to_string(), 
            password : passwd.to_string()
        };
        hashTable.insert(tokens[2], user);

        // println!("{}", hashTable.get(tokens[2]).getUsername()); ## DOESNT WORK
    }

    processMenu(hashTable);

    Ok(())
}

pub fn processMenu(hashTable : HashMap<&str, User::User>) {
    // println!("Made it");
    // let mut ynAnswer = String::new();
    // let mut letterChoice = String::new();

    while true {
        let mut ynAnswer = String::new();
        let mut letterChoice = String::new();

        println!("What would you like to do with the User database? Select a letter from the list below");
        println!("A. Insert a new User");
        println!("B. Find an existing User");
        println!("C. Remove an existing User");
        println!("D. Undo removal of User");
        println!("E. Print efficiency data");
        println!("F. Quit program");
        io::stdin().read_line(&mut letterChoice).expect("Failed to read line");

        letterChoice = letterChoice.trim().to_string();

        match &letterChoice as &str {
            "a" | "A" =>  addUser(&hashTable),
            "b" | "B" =>  {
                // let user = Some(getUser(&hashTable));
                let user = getUser(&hashTable);
                // if let Some(ref m) = user {
                //     println!("First Name: {}",  (*m).getFirstname());
                //     println!("Last Name: {}",  (*m).getLastname());
                //     println!("Username: {}",  (*m).getUsername());
                //     println!("Password: {}",  (*m).getPassword());
                // }
                // match user {
                //     User => {
                //         println!("First Name: {}",  user.getFirstname());
                //         println!("Last Name: {}",  user.getLastname());
                //         println!("Username: {}",  user.getUsername());
                //         println!("Password: {}",  user.getPassword());
                //     }
                // }
                // if user != None { // maybe nil works instead?
                //     println!("First Name: {}",  user.getFirstname());
                //     println!("Last Name: {}",  user.getLastname());
                //     println!("Username: {}",  user.getUsername());
                //     println!("Password: {}",  user.getPassword());
                // }
            },
            "c" | "C" =>  removeUser(&hashTable),
            "d" | "D" =>  println!("hashTable.undoRemove();"),
            "e" | "E" =>  printEfficienyData(&hashTable),
            // "a" | "A" =>  println!("A"),
            // "b" | "B" =>  println!("B"),
            // "c" | "C" =>  println!("C"),
            // "d" | "D" =>  println!("D"),
            // "e" | "E" =>  println!("E"),
            "f" | "F" => break,
            _ => println!("Letter not valid!")
            // "f" | "F" =>  addUser(hashTable),
        }

        // if ynAnswer == "F" || ynAnswer == "f" {
        //     break
        // }
    } 
}

pub fn addUser(hashTable : &HashMap<&str, User::User>) {
    println!("ADD");
}

pub fn getUser(hashTable : &HashMap<&str, User::User>){ // std::option::Option<&'a User::User> OR  User::User
    // println!("GET");
    let mut username = String::new();

    println!("What is the name of the User you would like to retrieve: ");
    io::stdin().read_line(&mut username).expect("Failed to read line");
    username = username.trim().to_string();
    let user = hashTable.get::<str>(&username);

    // let user = Some(hashTable.get(&username));
    
    // if user == None {
    //     println!("Could not find User with username {}.", username);
    // }
    // else {
        // println!("User {} retrieved!", username);
        // return user;
    // }

    // match user {
    //     User => {
    //         println!("User {} retrieved!", username);
    //         return user;
    //     },
    //     None => println!("Could not find User with username {}.", username),
    // }

    // if let Some(ref m) = user {
    //     println!("User {} retrieved!", (*m));
    //     return user;
    // }
    // else {
    //     println!("Could not find User with username {}.", user)
    // }

    return user; // may not need
}

pub fn removeUser(hashTable : &HashMap<&str, User::User>) {
    println!("REMOVE");
}

pub fn printEfficienyData(hashTable : &HashMap<&str, User::User>) {
    println!("PRINT");
}


// while {

//     ynAnswer == "F" || ynAnswer == "f"
// } {}