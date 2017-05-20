import org.jasypt.util.password.BasicPasswordEncryptor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbConnect {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Statement getSt() {
        return st;
    }

    public void setSt(Statement st) {
        this.st = st;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public DbConnect() {
        try {
            //library com.mysql.jdbc.Driver" now imported via Maven
            //"ClassNotFoundException" catch no longer needed
            //Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/shop", "root", "");
            st = con.createStatement();
        }catch (NullPointerException|SQLException s) {
            System.out.println("Error:" + s);
        }
    }

    public void getData() {
        try {
            rs = st.executeQuery("select * from client");
            List<Client> clients_list = new ArrayList<Client>();
            while (rs.next()) {
                Client cl = new Client();
                cl.setUsername(rs.getString("username"));
                cl.setFirst_name(rs.getString("first_name"));
                cl.setLast_name(rs.getString("last_name"));
                cl.setEmail(rs.getString("email"));
                cl.setPassword(rs.getString("password"));
                clients_list.add(cl);
                System.out.println(clients_list);
            }
        } catch (NullPointerException|SQLException s) {
            System.out.println("Error:" + s);
        }

    }

    public boolean loginCheck(String userIn, String passIn){
        String encryptedPassword;
        try{
            rs = st.executeQuery("SELECT password FROM client WHERE username = '" + userIn + "'");
            BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
            // gotta run rs.next
            if( rs.next() ) {
                encryptedPassword = rs.getString("password");
                System.out.println("Encrypted password == " + encryptedPassword);
                if (passwordEncryptor.checkPassword(passIn, encryptedPassword) == true)
                    return true;
            }
                return false;

        } catch(NullPointerException|SQLException s){
            System.out.println("Error:" + s);
            return false;
        }
    }

    public boolean checkAdmin(String userIn){
        try{
            rs = st.executeQuery("SELECT admin FROM client WHERE username = '" + userIn + "'");
            if(rs.next())
                if(rs.getBoolean("admin") == true )
                    return true;
            return false;

        } catch(NullPointerException|SQLException s){
            System.out.println("Error:" + s);
            return false;
        }
    }

    public void addClient(Client newClient){
        try {
            String adminStatus = "FALSE"; //change to "TRUE" when adding admins for debugging purposes, otherwise "FALSE" because this is how SQL works
            st.executeUpdate("insert into client(first_name, last_name, username, password, email, admin) values( '" +
                    newClient.getFirst_name() + "', '" + newClient.getLast_name() + "', '" + newClient.getUsername() + "', '" + newClient.getPassword() + "', '" + newClient.getEmail() + "', " + adminStatus + ")");
        } catch(NullPointerException|SQLException s){
            System.out.println("Error:" + s);
            return;
        }
    }

    public boolean searchCategory(String auxCategory){
        try {
            rs = st.executeQuery("SELECT category FROM category WHERE category = '" + auxCategory + "'");
            if(rs.next()) {
                if (rs.getString("category").equals(auxCategory))
                    return true;
            }
            return false;
        } catch(NullPointerException|SQLException s){
            System.out.println("Error:" + s);
            return false;
        }
    }

    public boolean insertCategory(String newCategory){
        if(searchCategory(newCategory.toLowerCase()) == true)
            return false;
        try {
            st.executeUpdate("insert into category(category) values( '" + newCategory + "')");
            System.out.println("Category added!");
            return true;
        } catch(NullPointerException|SQLException s){
            System.out.println("Error:" + s);
            return false;
        }
    }

}
