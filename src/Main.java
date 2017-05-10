public class Main {

    public static void main(String[] args) {
         try {
             DbConnect connect = new DbConnect();
             connect.getData();
         }catch (Exception e){
             e.printStackTrace();
         }
    }
}
