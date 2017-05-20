import org.jasypt.util.password.BasicPasswordEncryptor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

/*
Table format:
first_name last_name username password (encrypted!!!) email
email = primary key
*/
public class Client {
    private String first_name;
    private String last_name;
    private String username;
    private String password;
    private String email; //primary key, all emails are unique
    private Boolean admin;

    Client() {
        first_name = new String();
        last_name = new String();
        username = new String();
        password = new String();
        email = new String();
        admin = false;
    }

    public Boolean init() {
        //used to initialise the Client, if it returns 0 then the process was canceled by the user
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String aux = new String();
        System.out.println("Please type in the new client's credidentials (or \"cancel\" at any time to cancel the process.");
        try {
            System.out.println("First Name: ");
            first_name = reader.readLine();
            if (first_name.toLowerCase().equals("cancel"))
                return false;

            System.out.println("Last Name: ");
            last_name = reader.readLine();
            if (last_name.toLowerCase().equals("cancel"))
                return false;

            System.out.println("Username: ");
            username = reader.readLine();
            if (username.toLowerCase().equals("cancel"))
                return false;

            System.out.println("Password: ");
            aux = reader.readLine();
            if (aux.toLowerCase().equals("cancel"))
                return false;
            setPassword(aux);

            email = null;
            System.out.println("Email: ");
            while (email == null) {
                aux = reader.readLine();
                if(aux.toLowerCase().equals("cancel"))
                    return false;
                //Checks if the email has ONE @, and if it is placed BEFORE the last '.'
                if (!aux.contains("@") ||
                        aux.indexOf('@') != aux.lastIndexOf('@') ||
                        !aux.contains(".") ||
                        aux.indexOf('@') > aux.lastIndexOf('.'))
                    System.out.println("Email must be valid.");
                else
                    email = aux;
            }
            admin = false; //we can not register Admins
            return true; //finished reading Client data

        } catch (Exception e) {
            System.out.println("Could not read input Stream.");
            System.out.println("Error in Client.init(): " + e);
            return false;
        }
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean isAdmin() {
        return admin;
    }

    public void setPassword(String password) {
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        this.password = passwordEncryptor.encryptPassword(password);
        //to check if entered password is correct, use passwordEncryptor.checkPassword(password, encryptedPassword)
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", username='" + username + '\'' +
                ", email=" + email +
                '}';
    }
}
