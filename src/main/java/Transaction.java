/*
Table format:
id product_id client_id quantity price_total date status
id = primary key
product_id = foreign id (the product which the client has purchased, points to the Product table)
client_id = foreign id (which client has made the purchase, points to the Client table)
status = pending / completed
price_total = Product.quantity * Product.price

 */
public class Transaction extends Product{
    private int product_key;
    private int client_id;
    private int price_total;
    private boolean status;
    private boolean pending;
    private boolean completed;


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

    public int getProduct_key() {
        return product_key;
    }

    public void setProduct_key(int product_key) {
        this.product_key = product_key;
    }


    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }


    public int getPrice_total() {
        return price_total = getQuantity() * getPrice();
    }

    public void setPrice_total(int price_total) {
        this.price_total = price_total;
    }


    public boolean isStatus() {
        if (getClient_id() == 0) {
            status= isPending();
            System.out.printf("Transaction in pending");
            return true;
        } else {
            status = isCompleted();
            System.out.println("Transaction is completed");
            return false;
        }
    }


    public void setStatus(boolean status) {
      this.status=status;
    }



}
