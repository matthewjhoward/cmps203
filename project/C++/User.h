#ifndef _USER
#define _USER

#include <string>
#include <iostream>

using namespace std;

class User {
    private:
        string firstname;
        string lastname;
        string username;
        string password;

    public:
        // Default Constructor
        User();

        // Constructor
        User(string firstname, string lastname, string username, string password);

        // Setters
        void setFirstname(string firstname){ this->firstname = firstname; }
        void setLastname(string lastname){ this->lastname = lastname; }
        void setUsername(string username){ this->username = username; }
        void setPassword(string password){ this->password = password; }

        // Getters
        string getFirstname(){ return firstname; }
	    string getLastname(){ return lastname; }
	    string getUsername(){ return username; }
	    string getPassword() { return password; }

        // Print the key and value for a given DatabeObject 
        void printUser();
};

User::User() {
    // this->firstname = NULL;
    // this->lastname = NULL;
    // this->username = NULL;
    // this->password = NULL;
    this->firstname = "";
    this->lastname = "";
    this->username = "";
    this->password = "";
}

User::User(string firstname, string lastname, string username, string password) {
    this->firstname = firstname;
    this->lastname = lastname;
    this->username = username;
    this->password = password;
}

void User::printUser() {
    cout << "User Details:" << endl;
    cout << "First Name = " << this->firstname << endl;
    cout << "Last Name = " << this->lastname << endl;
    cout << "Username = " << this->username << endl;
    cout << "Password = " << this->password << endl;
}

#endif