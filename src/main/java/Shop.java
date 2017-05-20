import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Shop {
    private DbConnect connect;
    private boolean connectedToShop;
    Shop(){
        try {
            connect = new DbConnect();
            connect.getData();
            connectedToShop = true;
        }catch (Exception e){
            System.out.println("Error! Could not connect to database from Shop class.");
            e.printStackTrace();
            connectedToShop = false;
        }
    }

    public Boolean getConnection(){
        return connectedToShop;
    }

    public void startShop(){
        //registerClient(); //if you need to register an admin, add this and "adminStatus = true" in the DBConnect.addClient() method
        String currentUser = new String();
        while(currentUser != "false") { //false == we exit the application
            currentUser = login();
            if (currentUser == "false") {
                System.out.println("Thank you for shopping with us, we hope to see you again!");
                return;
            }
            boolean adminRights = connect.checkAdmin(currentUser);
            mainScreen(currentUser, adminRights);
            System.out.println("Thank you for shopping with us, we hope to see you again!");
        }
    }

    private String login(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String auxUser = new String();
        String auxPass = new String();
        boolean loggedIn = false;
        //Login Screen
        try {
            System.out.println("Welcome to our shop! Please login to continue, or type \"cancel\" to exit.");
            while(loggedIn == false) {
                System.out.println("Username: "); auxUser = reader.readLine(); if (auxUser.toLowerCase().equals("cancel")) return "false";
                System.out.println("Password: "); auxPass = reader.readLine(); if (auxPass.toLowerCase().equals("cancel")) return "false";
                if(connect.loginCheck(auxUser, auxPass) == true)
                    loggedIn = true;
                else
                    System.out.println("Wrong username or password.");

            }
        }catch(Exception e){
            System.out.println("Could not read input Stream.");
            System.out.println("Error in Client.init(): " + e);
            return "false"; //"false" = quit
        }
        return auxUser; //login succesful!
    }

    private void mainScreen(String currentUser, Boolean adminRights){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int val = 0;
        if(adminRights == false) { //Client screen
            while (true) { //you may exit via return;
                System.out.println("Welcome " + currentUser);
                System.out.println("Please select an option:");
                System.out.println("1. View products by category / categories. (not implemented)\n" +
                        "2. Order a product. (not implemented)\n" +
                        "3. View shopping basket. (not implemented)\n" +
                        "4. Confirm order. (not implemented)\n" +
                        "5. Logout.");
                try {
                    val = Integer.parseInt(reader.readLine());
                } catch(Exception e){
                    System.out.println("Could not read option.");
                }
                    switch (val) {
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        case 5:
                            return;
                        default:
                            System.out.println("Invalid option");
                    }
            }
        } else
        if(adminRights == true) { //Administrator Screen
            while (true) {
                System.out.println("Welcome " + currentUser);
                System.out.println("Please select an option:");
                System.out.println("1. Register new client user.\n" +
                        "2. Add new clothing categories. (not implemented)\n" +
                        "3. Add new clothes in store categories. (not implemented)\n" +
                        "4. View existing categories. (not implemented)\n" +
                        "5. View pending orders. (not implemented)\n" +
                        "6. View order history. (not implemented)\n" +
                        "7. Logout.\n");
                try {
                    val = Integer.parseInt(reader.readLine());
                } catch(Exception e){
                    System.out.println("Could not read option.");
                }
                    switch (val) {
                        case 1:
                            registerClient(); //reduces clutter in the Shop class, but if email is already taken, we have to do it all over again
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                        case 6:
                            break;
                        case 7:
                            System.out.println("RETURN!");
                            return;
                        default:
                            System.out.println("Invalid option");
                }
            }
        }
    }

    private void registerClient(){
        Client newClient = new Client();
        if( newClient.init() == true ){
            connect.addClient(newClient);
        }
    }
}
