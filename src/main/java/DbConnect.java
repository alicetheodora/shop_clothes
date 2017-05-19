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
                cl.setId(rs.getInt("id"));
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

}
