import org.jasypt.util.password.BasicPasswordEncryptor;
/*
Table format:
id first_name last_name username password (encrypted!!!) email
id = primary key
*/
public class Client {
    private String first_name;
    private String last_name;
    private int id;
    private String username;
    private String password;
    private String email;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                ", id=" + id +
                ", username='" + username + '\'' +
                ", email=" + email +
                '}';
    }
}
