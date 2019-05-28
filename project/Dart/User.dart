class User {
  String firstname;
  String lastname;
  String username;
  String password;

  // Constructor
  User(String firstname, String lastname, String username, String password) {
      this.firstname = firstname;
      this.lastname = lastname;
      this.username = username;
      this.password = password;
  }

  // Setters
  void setFirstname(String firstname) {
      this.firstname = firstname;
  }

  void setLastname(String lastname) {
      this.lastname = lastname;
  }

  void setUsername(String username) {
      this.username = username;
  }

  void setPassword(String password) {
      this.password = password;   
  }

  // Getters
  String getFirstname() {
      return firstname;
  }

  String getLastname() {
      return lastname;
  }

  String getUsername() {
      return username;
  }

  String getPassword() {
      return password;
  }
}