/*
Table format:
id product_id client_id quantity price_total date status
id = primary key
product_id = foreign id (the product which the client has purchased, points to the Product table)
client_id = foreign id (which client has made the purchase, points to the Client table)
status = pending / completed / cart
price_total = Product.quantity * Product.price

 */

public class Transaction extends Product{
    private int id;
    private int product_name;
    private int client_email;
    private int price_total;
    private int quantity;
    //private boolean status; //Removed "status" variable, instead we use the "pending / completed / cart" variables
    private boolean pending;
    private boolean completed;
    private boolean cart;
    private String date;



    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isCart(){
        return cart;
    }

    public void setCart(boolean cart){
        this.cart = cart;
    }

    public int getProduct_name() { return product_name; }

    public void setPpoduct_name(int product_name) {
        this.product_name = product_name;
    }


    public int getClient_email() {
        return client_email;
    }

    public void setClient_email(int client_email) {
        this.client_email = client_email;
    }


    public void setPrice_total(int price_total) {
        this.price_total = price_total;
    }


    public boolean isStatus() {
        if(isCart()){
            System.out.println("Transaction is in cart.");
        }else
            if(isPending()){
                System.out.println("Transaction is pending.");
            }
            else
                if(isCompleted()) {
                    System.out.println("Transaction is completed."); //when working with transactions, it only uses one of 3 statuses
                }
                else
                    return false; //status not set
        return true;
    }


    public boolean setStatus(String status) {
        cart = pending = completed = false;
        status = status.toLowerCase();
        if(status == "cart"){
            cart = true;
        }else
            if(status == "pending")
                pending = true;
            else
                if(status == "completed")
                    completed = true;
                else
                    return false; //unrecognised status variable
        return true;
    }

}
