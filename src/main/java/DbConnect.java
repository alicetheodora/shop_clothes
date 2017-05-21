import com.sun.org.apache.xpath.internal.operations.Bool;
import org.jasypt.util.password.BasicPasswordEncryptor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
            System.out.println("User has been registered succesfully!");
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

    public Product getProduct(String auxProduct){
        try {
            rs = st.executeQuery("SELECT * FROM product WHERE name = '" + auxProduct + "'");
            rs.next();
            Product result = new Product();
            result.setName(rs.getString("name"));
            result.setDescription(rs.getString("description"));
            result.setPrice(rs.getInt("price"));
            result.setSize(rs.getString("size"));
            result.setCategory(rs.getString("category"));
            return result;
        }catch(NullPointerException|SQLException s){
            System.out.println("Error: " + s);
            return null;
        }
    }

    public boolean insertCategory(String newCategory){
        if(searchCategory(newCategory.toLowerCase()) == true)
            return false;
        try {
            st.executeUpdate("insert into category(category) values( '" + newCategory.toLowerCase() + "')");
            System.out.println("Category added!");
            return true;
        } catch(NullPointerException|SQLException s){
            System.out.println("Error:" + s);
            return false;
        }
    }

    public boolean insertProduct(String category){
        Product newProduct = new Product();
        if( newProduct.init(category) == false )
            return false;
        try {
            rs = st.executeQuery("SELECT category FROM category WHERE category = '" + category.toLowerCase() + "'");
            rs.next();
            st.executeUpdate("insert into product(name, description, price, size, category) values( '"
                    + newProduct.getName().toLowerCase() + "', '" + newProduct.getDescription().toLowerCase() + "', '" + newProduct.getPrice() + "', '" + newProduct.getSize().toUpperCase() + "', '" + rs.getString("category") + "')");
            return true;
        } catch(NullPointerException|SQLException s){
            System.out.println("Error in DbConnect.insertProduct: " + s);
            return false;
        }
    }

    public void viewCategories(){
        try {
            rs = st.executeQuery("SELECT category FROM category");
            while(rs.next()){
                System.out.println(rs.getString("category"));
            }
        } catch(NullPointerException|SQLException s){
            System.out.println("Error: " + s);
            return;
        }
    }

    public void viewProductsByCategory(String category){
        try{
            rs = st.executeQuery("SELECT * FROM product WHERE category = '" + category.toLowerCase() + "'");
            while(rs.next()){
                System.out.println("Name: \"" + rs.getString("name") +
                "\"; Description: \"" + rs.getString("description") +
                "\"; Price: " + rs.getInt("price") +
                "\"; Size: \"" + rs.getString("size") + "\"");
            }
        }catch(NullPointerException|SQLException s){
            System.out.println("Error: " + s);
            return;
        }
    }

    public boolean addToCart(Product auxProduct, String currentUser, int quantity){
        try {
            st.executeUpdate("INSERT into TRANSACTION(product_name, client_email, price_total, quantity, status, date) values ('" +
            auxProduct.getName() + "', " +
                    "(SELECT email FROM client WHERE username = '" + currentUser +"'), " +
            quantity*auxProduct.getPrice() + ", " +
            quantity + ", 'cart', (select SYSDATE()) )");
            return true;
        } catch(NullPointerException|SQLException s){
            System.out.println("Error in DbConnect.insertProduct: " + s);
            return false;
        }
    }

    public void viewShoppingBasket(String currentUser){
        String aux = new String("");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int priceSum = 0;
        try {
            rs = st.executeQuery("SELECT * FROM transaction WHERE client_email = ( SELECT email FROM client WHERE username = '" + currentUser + "' ) AND status = 'cart'");
            while(rs.next()){
                System.out.println(rs.getInt("id") + ". Product name: \"" + rs.getString("product_name") + "\"; Quantity: " + rs.getInt("quantity") + "; Price total: " + rs.getInt("price_total"));
                priceSum += rs.getInt("price_total");
            }
            if(priceSum != 0){
                System.out.println("Cart price total = " + priceSum);
                System.out.println("Would you like to confirm your purchase? Y/N");
                try {
                    aux = reader.readLine();
                    while (!aux.toLowerCase().equals("y") && !aux.toLowerCase().equals("yes") &&
                            !aux.toLowerCase().equals("n") && !aux.toLowerCase().equals("no")) {
                        aux = reader.readLine();
                    }
                } catch(Exception e){
                    System.out.println("Error: " + e);
                }
                if(aux.toLowerCase().equals("y") || aux.toLowerCase().equals("yes")){
                    if(changeOrderStatus(currentUser, "pending") == true)
                        System.out.println("Your order is now pending, thank you for shopping with us!");
                    else
                        System.out.println("There is a problem with our servers, please try again later.");
                }
            }else{
                System.out.println("You have nothing in your shopping basket, and that makes us sad :(");
            }
        } catch(NullPointerException|SQLException s){
            System.out.println("Error in DbConnect.insertProduct: " + s);
            return;
        }
    }

    private boolean changeOrderStatus(String currentUser, String newStatus) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            st.executeUpdate("UPDATE transaction SET status = '" + newStatus + "' WHERE (SELECT email FROM client WHERE username = '" + currentUser + "') = client_email");
            return true;
        } catch (NullPointerException | SQLException s) {
            System.out.println("Error in DbConnect.insertProduct: " + s);
            return false;
        }
    }

    public void viewShoppingHistory(String currentUser){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int priceSum = 0;
        try {
            rs = st.executeQuery("SELECT * FROM transaction WHERE client_email = ( SELECT email FROM client WHERE username = '" + currentUser + "' ) AND NOT status = 'cart'");
            while(rs.next()){
                System.out.println(rs.getInt("id") + ". Product name: \"" + rs.getString("product_name") + "\"; Quantity: " + rs.getInt("quantity") + "; Price total: " + rs.getInt("price_total"));
                priceSum += rs.getInt("price_total");
            }
            if(priceSum == 0){
                System.out.println("You never ordered anything from us, and that makes us sad :(");
            }else
                System.out.println("Price total = " + priceSum);
        } catch(NullPointerException|SQLException s){
            System.out.println("Error in DbConnect.insertProduct: " + s);
            return;
        }
    }

    public void viewPendingOrders(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String auxEmail = new String("");
        Boolean orderExists = false;
        try {
            rs = st.executeQuery("SELECT * FROM transaction WHERE status = 'pending'");
            while(rs.next()){
                System.out.println(rs.getInt("id") + ". Client email: \"" + rs.getString("client_email") + "\"; Product name: \"" + rs.getString("product_name") + "\"; Quantity: " + rs.getInt("quantity") + "; Price total: " + rs.getInt("price_total"));
                orderExists = true;
            }
            if(orderExists == true){
                System.out.println("Please enter the email of a client who's order you would like to confirm, or type \"cancel\" if you wish to quit.");
                try {
                    auxEmail = reader.readLine();
                    if(auxEmail.toLowerCase().equals("cancel"))
                        return;
                    rs = st.executeQuery("SELECT username FROM client WHERE email = '" + auxEmail + "'");
                    rs.next();
                    if( changeOrderStatus(rs.getString("username"), "completed" ) == true )
                        System.out.println("Order confirmed succesfully.");
                    else
                        System.out.println("There was an error.");
                } catch(Exception e){
                    System.out.println("Error: " + e);
                }
            }else
                System.out.println("Nobody shopped here. Ever.");
        } catch(NullPointerException|SQLException s){
            System.out.println("Error: " + s);
            return;
        }
    }

    public void viewOrderHistory(){
        int priceSum = 0;
        try {
            rs = st.executeQuery("SELECT * FROM transaction WHERE status = 'completed'");
            while(rs.next()){
                System.out.println(rs.getInt("id") + ". Product name: \"" + rs.getString("product_name") + "\"; Quantity: " + rs.getInt("quantity") + "; Price total: " + rs.getInt("price_total"));
                priceSum += rs.getInt("price_total");
            }
        } catch(NullPointerException|SQLException s){
            System.out.println("Error in DbConnect.insertProduct: " + s);
            return;
        }
    }

}
