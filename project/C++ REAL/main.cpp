#include <iostream>
#include <fstream>
#include <string>
#include <bits/stdc++.h>

#include "User.h"
#include "HashMapper.h"
// #include "LinkedStacker.h"

using namespace std;

// struct inputFileTuple {
//     bool isOpened;
//     string filename;
// } ioResult, result;

// void processMenu(HashMapper<User>* hashTable, LinkedStacker<User> undoStack);
// void addUser(HashMapper<User>* hashTable);
// User getUser(HashMapper<User>* hashTable);
// void removeUser(HashMapper<User>* hashTable, LinkedStacker<User>& undoStack);
// void printEfficiencyData(HashMapper<User>* hashTable);

int main() {
	ifstream inputFile;
	vector<string> lines;
	inputFile.open("input.txt");

	if(inputFile.is_open()) {
		for (string line; getline(inputFile, line);)
		{
			lines.push_back(line);
		}
		inputFile.close();
	} 
	else {
		cout << "Failure to read input file!" << endl;
		return -1;
	}

	HashMapper<string, User>* hashTable;  //might need to be new HashMapper<>();

	for (string line : lines) {
		vector<string> tokens;
		stringstream check1(line);
		string intermediate;

		while(getline(check1, intermediate, ',')) {
			tokens.push_back(intermediate);
		}

		string firstname = tokens[0];
		// cout << tokens[0] << endl;
		string lastname = tokens[1];
		// cout << tokens[1] << endl;
		string username = tokens[2];
		// cout << tokens[2] << endl;
		string password = tokens[3];
		// cout << tokens[3] << endl;

		User* user = new User(firstname, lastname, username, password);
		hashTable->put(username, *user); //dereference user first?
	}

	// processMenu(hashTable)
}

// void processMenu(HashMapper<User>* hashTable, LinkedStacker<User> undoStack) {
// 	char ynAnswer;
// 	char letterChoice;
// 	User* undoptr = new User();
// 	bool undoUsed = false;

// 	do
// 	{
// 		system("CLS");
// 		cout << "What would you like to do with the User database? Select a leter from the list below." << endl;
// 		cout << "A. Insert a new User" << endl;
// 		cout << "B. Find an existing User" << endl;
// 		cout << "C. Remove an existing User" << endl;
// 		cout << "D. Undo removal of User" << endl;
// 		cout << "E. Print Efficiency data" << endl;
// 		cout << "F. Quit" << endl << endl;
// 		cout << "Please input your selected letter here, and press Enter: ";
// 		cin >> letterChoice;
// 		cout << endl;

// 		switch (letterChoice)
// 		{
// 		case 'a':
// 		case 'A':
// 			{
// 				addUser(hashTable);
// 			}
// 			break;

// 		case 'b':
// 		case 'B':
// 			{
// 				User user = getUser(hashTable);
// 				cout << "Found User! Now displaying..." << endl;
// 				cout << "First Name: " << user.getFirstname() << endl;
// 				cout << "Last Name: " << user.getLastname() << endl;
// 				// cout << "Username: " << user.lastname << endl;
// 				// cout << "Password: " << user.firstname << endl;
// 				cout << endl;
// 			}
// 			break;

// 		case 'c':
// 		case 'C':
// 			{
// 				removeUser(hashTable, undoStack);
// 			}
// 			break;

// 		case 'd':
// 		case 'D':
// 			{
// 				if (!undoStack.isEmpty())
// 				{
// 					*undoptr = undoStack.peek();
// 					string key = undoptr->getUsername();
// 					hashTable->insert(key, *undoptr); // needs to be modified to accept key and value
// 					undoStack.pop();
// 					undoUsed = true;
// 				}
// 				else
// 				{
// 					cout << "No removals to undo!" << endl << endl;

// 				}
// 			}
// 			break;

// 		case 'e':
// 		case 'E':
// 			{
// 				printEfficiencyData(hashTable);
// 			}
// 			break;

// 		case 'f':
// 		case 'F':
// 			{
// 				if (undoUsed == false) {
// 					delete undoptr;
// 				}
// 				cout << "Now quitting Menu..." << endl;
// 				return;	
// 			}

// 		default: cout << "Letter not valid!" << endl;
// 		}

// 		cout << "Would you like to continue using the User database? Input Yes('Y' or 'y') or No ('N' or 'n'): ";
// 		cin >> ynAnswer;

// 	} while (ynAnswer != 'N' && ynAnswer != 'n');
// 	if (undoUsed == false) delete undoptr;
// }

// void addUser(HashMapper<User>* hashTable) {
// 	char ynAnswer;
// 	string firstname;
// 	string lastname;
// 	string username;
//     string password;

// 	do
// 	{
// 		cout << "What will the first name be for the User you would like to add: " << endl;
// 		getline(cin, firstname);
// 		cout << endl;

// 		cout << "What will the last name be for the User you would like to add: " << endl;
// 		getline(cin, lastname);
// 		cout << endl;

//         cout << "What will the username be for the User you would like to add: " << endl;
// 		cin >> username;
// 		cin.get();
// 		cout << endl;

//         if (hashTable->get(username) != NULL) // Check if exists
// 		{
//             // We should change this functionality so that they can input another username instead
// 			cout << "There is already a User with that username in the Hash Table! Returning to menu..." << endl; 
// 			break;
// 		}

// 		cout << "What will the password be for the User you would like to add?" << endl;
// 		cin >> password;
// 		cout << endl;

// 		User* user = new User(firstname, lastname, username, password);

// 		cout << "Now adding User to Hash Table..." << endl;
// 		hashTable->insert(username, *user);
//         cout << "User added." << endl;

// 		cout << "Would you like to add another User? Input Yes('Y' or 'y') or No('N' or 'n'): ";
// 		cin >> ynAnswer;

// 	} while (ynAnswer != 'N' && ynAnswer != 'n');
// }

// User getUser(HashMapper<User>* hashTable) {
//     string username;

//     cout << "What is the username of the User you would like to retrieve: " << endl;
//     cin >> username;
//     cout << endl;

//     User user = hashTable->get(username); // NEEDS TO RETURN A POINTER!!!!!

//     if (!user) 
//     {
//         // We should change this functionality so that they can input another username instead
//         cout << "Could not find User with provided. Returning to menu..." << endl;
//         return 0;
//     }
//     else

//     return user;
// }

// void removeUser(HashMapper<User>* hashTable, LinkedStacker<User>& undoStack) {
// 	char ynAnswer;
// 	string username;

// 	do
// 	{
//         cout << "What is the username of the User you would like to remove: " << endl;
//         cin >> username;
//         cout << endl;
// 		User user = hashTable->get(username)

//         if (user != NULL) // Check if exists
// 		{
// 			cout << "User found! Now removing User..." << endl;
//             undoStack.push(user);
// 			hashTable->remove(username);
//             cout << "User removed." << endl;
// 		}
//         else {
//             cout << "User not found." << endl;
//         }

// 		cout << "Would you like to remove another User? Input Yes('Y' or 'y') or No('N' or 'n'): ";
// 		cin >> ynAnswer;

// 	} while (ynAnswer != 'N' && ynAnswer != 'n');
// }

// void printEfficiencyData(HashMapper<User>* hashTable) {
//     // TO-DO
// }

