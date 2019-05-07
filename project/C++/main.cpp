#include <iostream>
#include <fstream>
#include <string>

#include "User.h"
#include "HashTable.h"
#include "LinkedStack.h"

// typedef User* USER;

using namespace std;

/*
Main Objectives:

- Read Input to Database from input file
- Start a do-while for users to input commands for insertion, removal, lookuo, undo-removal, print evaluations
- Implement a simple hash table (storage)
- Implement a simple stack (undo)

*/

struct inputFileTuple {
    bool isOpened;
    string filename;
} ioResult, result;

inputFileTuple openInputFile(ifstream &ifs);
void readInputFile(ifstream &inputFile, HashTable<User>* hashTable);
void processMenu(HashTable<User>* hashTable, LinkedStack<User> undoStack);
void addUser(HashTable<User>* hashTable)
User getUser(HashTable<User>* hashTable)
void removeUser(HashTable<User>* hashTable, LinkedStack<User>& undoStack)
void printEfficiencyData(HashTable<User>* hashTable)

int main() {
    ifstream inputFile;

    ioResult = openInputFile(inputFile);

    if (!ioResult.isOpened)
	{
		cout << "Error: Unable to open input file " << ioResult.filename << endl;
		return -1; // Returns a failure
	}
    else {
        cout << "Input file " << ioResult.filename << " opened!" << endl;
    }

    // Declare Hash Table for storing the input file of user data
    HashTable<User>* hashTable = new HashTable<User>();

    LinkedStack<VideoGame> undoStack;

    readInputFile(inputFile, hashTable);

    cout << "Closing input file " << ioResult.filename << "..." << endl;
	inputFile.close();
    cout << "Input file closed" << endl;

    User* user = new User("Alex", "Williamson", "alswilli69", "thisisabadpassword567");
    user->printUser();

    processMenu(hTable, undoStack);

	cout << "Now ending program..." << endl;
	delete hashTable;

    system("pause");

    return 0;
}

inputFileTuple openInputFile(ifstream &inputFile) {
	string filename;

	cout << "Enter the input filename: ";
	getline(cin, filename);
	inputFile.open(filename.c_str());

    result = {inputFile.is_open(), filename};

	return result;
}

void readInputFile(ifstream &inputFile, HashTable<User>* hashTable) {
	string firstname;
	string lastname;
	string username;
    string password;

	while (getline(inputFile, firstname))
	{
		getline(inputFile, lastname);
		getline(inputFile, password);
		getline(inputFile, username);
		User* user = new User(firstname, lastname, password, username);
		hashTable->insert(username, user);
	}

	cout << endl;
}

void processMenu(HashTable<User>* hashTable, LinkedStack<User> undoStack) {
	char ynAnswer;
	char letterChoice;
	User* undoptr = new User();
	bool undoUsed = false;

	do
	{
		system("CLS");
		cout << "What would you like to do with the User database? Select a leter from the list below." << endl;
		cout << "A. Insert a new User" << endl;
		cout << "B. Find an existing User" << endl;
		cout << "C. Remove an existing User" << endl;
		cout << "D. Undo removal of User" << endl;
		cout << "E. Print Efficiency data" << endl;
		cout << "F. Quit" << endl << endl;
		cout << "Please input your selected letter here, and press Enter: ";
		cin >> letterChoice;
		cout << endl;

		switch (letterChoice)
		{
		case 'a':
		case 'A':
			addUser(hashTable);
			break;

		case 'b':
		case 'B':
			User user = getUser(hashTable);
            cout << "Found User! Now displaying..." << endl;
            cout << "First Name: " << user.firstname << endl;
            cout << "Last Name: " << user.lastname << endl;
            // cout << "Username: " << user.lastname << endl;
            // cout << "Password: " << user.firstname << endl;
            cout << endl;
			break;

		case 'c':
		case 'C':
			removeUser(hashTable, undoStack);
			break;

		case 'd':
		case 'D':
			if (!undoStack.isEmpty())
			{
				*undoptr = undoStack.peek();
				hTable->insert(undoptr); // needs to be modified to except key and value
				undoStack.pop();
				undoUsed = true;
			}
			else
			{
				cout << "No removals to undo!" << endl << endl;

			}
			break;

		case 'e':
		case 'E':
			printEfficiencyData(hashTable);
			break;

		case 'f':
		case 'F':
			if (undoUsed == false) delete undoptr;
			cout << "Now quitting Menu..." << endl;
			return;

		default: cout << "Letter not valid!" << endl;
		}

		cout << "Would you like to continue using the User database? Input Yes('Y' or 'y') or No ('N' or 'n'): ";
		cin >> ynAnswer;

	} while (ynAnswer != 'N' && ynAnswer != 'n');
	if (undoUsed == false) delete undoptr;
}

void addUser(HashTable<User>* hashTable) {
	char ynAnswer;
	string firstname;
	string lastname;
	string username;
    string password;

	do
	{
		cout << "What will the first name be for the User you would like to add: " << endl;
		getline(cin, firstname);
		cout << endl;

		cout << "What will the last name be for the User you would like to add: " << endl;
		getline(cin, lastname);
		cout << endl;

        cout << "What will the username be for the User you would like to add: " << endl;
		cin >> username;
		cin.get();
		cout << endl;

        if (hashTable->get(username)) // Check if exists
		{
            // We should change this functionality so that they can input another username instead
			cout << "There is already a User with that username in the Hash Table! Returning to menu..." << endl; 
			break;
		}

		cout << "What will the password be for the User you would like to add?" << endl;
		cin >> password;
		cout << endl;

		User* user = new User(firstname, lastname, username, password);

		cout << "Now adding User to Hash Table..." << endl;
		hashTable->insert(user);
        cout << "User added." << endl;

		cout << "Would you like to add another User? Input Yes('Y' or 'y') or No('N' or 'n'): ";
		cin >> ynAnswer;

	} while (ynAnswer != 'N' && ynAnswer != 'n');
}

User getUser(HashTable<User>* hashTable) {
    string username;

    cout << "What is the username of the User you would like to retrieve: " << endl;
    cin >> username;
    cout << endl;

    User user = hTable->get(username);

    if (user == NULL) 
    {
        // We should change this functionality so that they can input another username instead
        cout << "Could not find User with provided. Returning to menu..." << endl;
        return;
    }
    else

    return user;
}

void removeUser(HashTable<User>* hashTable, LinkedStack<User>& undoStack) {
	char ynAnswer;
	string username;

	do
	{
        cout << "What is the username of the User you would like to remove: " << endl;
        cin >> username;
        cout << endl;

        if (hashTable->get(username)) // Check if exists
		{
			cout << "User found! Now removing User..." << endl;
            undoStack.push(username);
			hashTable->remove(username)
            cout << "User removed." << endl;
		}
        else {
            cout << "User not found." << endl;
        }

		cout << "Would you like to remove another User? Input Yes('Y' or 'y') or No('N' or 'n'): ";
		cin >> ynAnswer;

	} while (ynAnswer != 'N' && ynAnswer != 'n');
}

void printEfficiencyData(HashTable<User>* hashTable) {
    // TO-DO
}

