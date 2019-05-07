#ifndef _USER
#define _USER

#include <string>

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

        // Print the key and value for a given DatabeObject 
        void printUser();
};

User::User() {
    this->firstname = NULL;
    this->lastname = NULL;
    this->username = NULL;
    this->password = NULL;
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