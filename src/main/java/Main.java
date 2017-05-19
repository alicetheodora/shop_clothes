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
    }
}
