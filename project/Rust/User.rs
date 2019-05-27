pub struct User {
    pub firstname: String,
    pub lastname: String,
    pub username: String,
    pub password : String,
}

impl User {
    pub fn setFirstname(&mut self, firstname : String) -> () {
        self.firstname = firstname;
    }

    pub fn setLastname(&mut self, lastname : String) -> () {
        self.lastname = lastname;
    }

    pub fn setUsername(&mut self, username : String) -> () {
        self.username = username;
    }

    pub fn setPassword(&mut self, password : String) -> () {
        self.password = password;
    }

    pub fn getFirstname(self) -> String {
        return self.firstname
    }

    pub fn getLastname(self) -> String {
        return self.lastname;
    }
    
    pub fn getUsername(self) -> String {
        return self.username;
    }

    pub fn getPassword(self) -> String {
        return self.password;
    }
}

impl PartialEq for User {
    fn eq(&self, other: &Self) -> bool {
        self.username == other.username
    }
}