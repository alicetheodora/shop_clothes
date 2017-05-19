public class Main {

    public static void main(String[] args) {
        try {
            DbConnect connect = new DbConnect();
            connect.getData();
        }catch (Exception e){
            e.printStackTrace();
        }

        Shop main_shop = new Shop();
        System.out.println("End of program. Have a nice day!");

        Client c= new Client();
        System.out.println("Welcome"+c.getUsername());

        System.out.println("Please select an option:");
        System.out.println("1. View products by category / categories.\n" +
                           "2. Order a product.\n" +
                           "3. View shopping basket.\n" +
                           "4. Confirm order. \n" +
                           "5. Logout.");
        int val=1;

        switch(val) {
            case 1 :
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
           default :
                System.out.println("Invalid option");
        }
        System.out.println("Your option are: " + val);
    }
}

