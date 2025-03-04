import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.*;
import java.util.stream.Collectors;
// import com.opencsv.CSVWriter;

public class Project {
    public static void main(String[] args) {
        boolean doMenu = false;
        HashMapper<String, User> hashTable = new HashMapper<>();
        if(doMenu){
            BufferedReader reader;
            ArrayList<String> lines = new ArrayList<String>();
    
            // Grab all the lines (comma separated values)
            try {
                reader = new BufferedReader(new FileReader("input.txt"));
                String line = reader.readLine();
                while (line != null) {
                    lines.add(line);
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    
            
    
            // For each line, store in hash map
            for (String line : lines) {
                // Create new user for each line of input and store in hash table
                String[] tokens = line.split(",");
    
                String firstname = tokens[0];
                String lastname = tokens[1];
                String username = tokens[2];
                String password = tokens[3];
    
                User user = new User(firstname, lastname, username, password);
                hashTable.put(username, user);
            }
    
            processMenu(hashTable);
        }else{
            runTests(hashTable);
        }
        

        return;
    }

    public static void runTests(HashMapper<String, User> hashTable){
        ArrayList<User> users = new ArrayList<User>();
        ArrayList<Double> add_times = new ArrayList<Double>();
        ArrayList<Double> del_times = new ArrayList<Double>();
        ArrayList<Double> undo_times = new ArrayList<Double>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("input.txt"));
            String line = reader.readLine();
            while (line != null) {
                // System.out.println(line);
                String[] tokens = line.split(",");
                User user = new User(tokens[0], tokens[1], tokens[2], tokens[3]);
                users.add(user);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i = 0;
        long start_time = System.nanoTime();
        for (User user : users){
            i++;
            hashTable.put(user.username, user);

            if( i%10 == 0){
                add_times.add((double)(System.nanoTime()-start_time)/1000000.0);
            }
        }
        i = 0;
        start_time = System.nanoTime();
        for (User user : users){
            i++;
            hashTable.remove(user.username);

            if( i%10 == 0){
                del_times.add((double)(System.nanoTime()-start_time)/1000000.0);
            }
        }
        i = 0;
        start_time = System.nanoTime();
        start_time = System.nanoTime();
        for (User user : users){
            i++;
            hashTable.undoRemove();

            if( i%10 == 0){
                undo_times.add((double)(System.nanoTime()-start_time)/1000000.0);
            }
        }
        
        try{
            FileWriter fstream = new FileWriter("output.txt",false);
            BufferedWriter writer = new BufferedWriter(fstream);

            // CSVWriter writer = new CSVWriter(new FileWriter("output.txt"), ',');
            String adds = add_times.stream().map(Object::toString).collect(Collectors.joining(","));
            String dels = del_times.stream().map(Object::toString).collect(Collectors.joining(","));
            String undos = undo_times.stream().map(Object::toString).collect(Collectors.joining(","));
            System.out.println(undos);
            writer.write(adds);
            writer.newLine();
            writer.write(dels);
            writer.newLine();
            writer.write(undos);
            writer.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        
        
        // System.out.println(Arrays.toString(add_times.toArray()));

        
    }

    public static void processMenu(HashMapper<String, User> hashTable) {
        String ynAnswer = "";
        String letterChoice = "";

        do {
            System.out.println("What would you like to do with the User database? Select a letter from the list below");
            System.out.println("A. Insert a new User");
            System.out.println("B. Find an existing User");
            System.out.println("C. Remove an existing User");
            System.out.println("D. Undo removal of User");
            System.out.println("E. Print efficiency data");
            System.out.println("F. Quit program");
            System.out.println("Please input your selected letter here, then press Enter: ");
            Scanner scan = new Scanner(System.in);
            letterChoice = scan.nextLine();

            switch(letterChoice) {
                case "a":
                case "A":
                    addUser(hashTable);
                    break;
                
                case "b":
                case "B":
                    User user = getUser(hashTable);
                    if (user != null) {
                        System.out.println("First Name: " + user.getFirstname());
                        System.out.println("Last Name: " + user.getLastname());
                        System.out.println("Username: " + user.getUsername());
                        System.out.println("Password: " + user.getPassword());
                    }
                    break;

                case "c":
                case "C":
                    removeUser(hashTable);
                    break;

                case "d":
                case "D":
                    hashTable.undoRemove();
                    break;

                case "e":
                case "E":
                    printEfficiencyData(hashTable);
                    break;

                case "f":
                case "F":
                    return;

                default:
                    System.out.println("Letter not valid!");
            }

        } while (Objects.equals(letterChoice, "F") == false && Objects.equals(letterChoice, "f") == false);

        return;
    }

    public static void addUser(HashMapper<String, User> hashTable) {
        String ynAnswer = "";
        String firstname;
        String lastname;
        String username;
        String password;

        do {
            System.out.println("What will the first name be for the User you would like to add: ");
            Scanner scan = new Scanner(System.in);
            firstname = scan.nextLine();

            System.out.println("What will the last name be for the User you would like to add: ");
            lastname = scan.nextLine();

            System.out.println("What will the username be for the User you would like to add: ");
            username = scan.nextLine();

            if (hashTable.get(username) != null) {
                while(hashTable.get(username) != null) {
                    System.out.println("There is already a User with that username in the hash table! Please try another username: ");
                    username = scan.nextLine();
                }
            }

            System.out.println("What will the password be for the User you would like to add: ");
            password = scan.nextLine();

            User user = new User(firstname, lastname, username, password);
            hashTable.put(username, user);

            System.out.println("User " + username + " added to the hash table!");
            System.out.println("Would you like to add another User? Input Yes('Y' or 'y') or No('N' or 'n'): ");
            ynAnswer = scan.nextLine();

        } while (Objects.equals(ynAnswer, "N") == false && Objects.equals(ynAnswer, "n") == false);
    }

    public static User getUser(HashMapper<String, User> hashTable) {
        String username;

        System.out.println("What is the Username of the User you would like to retrieve: ");
        Scanner scan = new Scanner(System.in);
        username = scan.nextLine();
        User user = hashTable.get(username);

        if (user == null) {
            System.out.println("Could not find User with username " +  username + ".");
        }
        else {
            System.out.println("User " + username + " retrived!");
            return user;
        }

        return null;
    }

    public static void removeUser(HashMapper<String, User> hashTable) {
        String ynAnswer = "";
        String username;

        do {
            System.out.println("What is the username of the User you would like to remove: ");
            Scanner scan = new Scanner(System.in);
            username = scan.nextLine();

            if (hashTable.get(username) == null) {
                System.out.println("User not found! ");
            }
            else {
                hashTable.remove(username);
                if (hashTable.get(username) == null) {
                    System.out.println("User " + username + " removed from the hash table!");
                }
            }

            System.out.println("Would you like to try removing another User? Input Yes('Y' or 'y') or No('N' or 'n'): ");
            ynAnswer = scan.nextLine();

        } while (Objects.equals(ynAnswer, "N") == false && Objects.equals(ynAnswer, "n") == false);
    }

    public static void printEfficiencyData(HashMapper<String, User> hashTable) {
        
    }
}