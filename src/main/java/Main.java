public class Main {

    public static void main(String[] args) {

        Shop main_shop = new Shop();
        if(main_shop.getConnection() == false)
            return;
        main_shop.startShop();
    }
}

