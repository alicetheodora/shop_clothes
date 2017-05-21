import sun.reflect.annotation.ExceptionProxy;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Shop {
    private DbConnect connect;
    private boolean connectedToShop;
    Shop(){
        try {
            connect = new DbConnect();
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
                System.out.println("1. View products by category.\n" +
                        "2. Order a product.\n" +
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
                            viewProductsByCategory();
                            break;
                        case 2:
                            orderProduct(currentUser);
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
                if(val != 4 && val !=5 && val != 6) {
                    System.out.println("Welcome " + currentUser);
                    System.out.println("Please select an option:");
                    System.out.println("1. Register new client user.\n" +
                            "2. Add new clothing categories.\n" +
                            "3. Add new clothes in store categories.\n" +
                            "4. View existing categories.\n" +
                            "5. View pending orders. (not implemented)\n" +
                            "6. View order history. (not implemented)\n" +
                            "7. Logout.\n");
                }else
                    System.out.println("Please select an option:");
                try {
                    val = Integer.parseInt(reader.readLine());
                } catch(Exception e){
                    System.out.println("Could not read option.");
                }
                    switch (val) {
                        case 1:
                            registerClient(); //reduces clutter in the Shop class, but if email is already taken, we have to do it all over again
                            break;
                        case 2:
                            addCategory();
                            break;
                        case 3:
                            addProduct();
                            break;
                        case 4:
                            connect.viewCategories();
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

    private void addCategory(){
        System.out.println("Please type in the new clothing categories you would like to add, or \"cancel\" if you are finished adding new clothing categories.");
        String aux = new String("");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) { //you quit via cancel
            try {
                aux = reader.readLine();
                while (aux.equals("")) {
                    System.out.println("Category must not be null.");
                    aux = reader.readLine();
                }
                if(aux.toLowerCase().equals("cancel"))
                    return;
                if( connect.insertCategory(aux) == false ){
                    System.out.println("Category already exists.");
                }
            } catch (Exception e) {
                System.out.println("Could not read line, in Shop.addCategory()");
                System.out.println("Error: " + e);
            }
        }
    }

    private void addProduct(){
        String auxCategory = new String("");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true){ //quit via cancel
            try{
                System.out.println("Please type in the category you would like to edit, or \"cancel\" to go back to menu.");
                auxCategory = reader.readLine();
                if( auxCategory.toLowerCase().equals("cancel") )
                    return;
                while(connect.searchCategory(auxCategory) == false){
                    System.out.println("Category must exist.");
                    auxCategory = reader.readLine();
                    if( auxCategory.toLowerCase().equals("cancel") )
                        return;
                }
                while(connect.insertProduct(auxCategory) == true); //keeps adding items until the user types "cancel", then he can pick a different cateogry
            }catch (Exception e){
                System.out.println("Could not read line, in Shop.addProduct()");
                System.out.println("Error: " + e);
            }
        }
    }

    private void viewProductsByCategory() {
        connect.viewCategories();
        String auxCategory = new String("");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) { //quit via "cancel", as always
            try {
                System.out.println("Which category would you like to view? Type \"cancel\" if you wish to quit.");
                auxCategory = reader.readLine();
                while(auxCategory.equals("") || connect.searchCategory(auxCategory) == false){
                    if(auxCategory.toLowerCase().equals("cancel"))
                        return;
                    System.out.println("Must be a valid category.");
                    auxCategory = reader.readLine();
                }
                if(auxCategory.equals("cancel"))
                    return;
                connect.viewProductsByCategory(auxCategory);
            } catch (Exception e) {
                System.out.println("Could not read line in Shop.viewProductsByCategory()");
                System.out.println("Error: " + e);
            }
        }
    }

    private void orderProduct(String currentUser) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String auxName = new String("");
        String aux;
        Product auxProduct;
        int quantity;
        while (true) {
            try {
                System.out.println("Which product would you like to order? Type \"cancel\" if you wish to quit.");
                auxName = reader.readLine();
                while ( (auxProduct = connect.getProduct(auxName)) == null) {
                    if(auxName.toLowerCase().equals("cancel"))
                        return;
                    System.out.println("Product must exist.");
                    auxName = reader.readLine();
                }
                System.out.println("The " + auxName + " is " + auxProduct.getPrice() + " Zimbabwean dollars. How many would you like to order?");
                quantity = Integer.parseInt(reader.readLine());
                while(quantity < 0){
                    System.out.println("You must order at least one product.");
                    quantity = Integer.parseInt(reader.readLine());
                }
                System.out.println("That is " + quantity + " " + auxName + " for a total price of " + quantity*auxProduct.getPrice()+", would you like to add this order to your cart?");
                aux = reader.readLine();
                while(!aux.toLowerCase().equals("y") && !aux.toLowerCase().equals("yes") &&
                        !aux.toLowerCase().equals("n") && !aux.toLowerCase().equals("no")){
                    aux = reader.readLine();
                }
                if(aux.toLowerCase().equals("y") || aux.toLowerCase().equals("yes")){
                    if(connect.addToCart(auxProduct, currentUser, quantity) == true)
                        System.out.println("Your order has been placed in your cart!");
                    else
                        System.out.println("There is a problem with our servers. Please try again later.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
    }
}
