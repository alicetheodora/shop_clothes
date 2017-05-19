/*
Table format:
id product_id client_id quantity price_total date status
id = primary key
product_id = foreign id (the product which the client has purchased, points to the Product table)
client_id = foreign id (which client has made the purchase, points to the Client table)
status = pending / completed
price_total = Transaction.quantity * Product.price
 */
public class Transaction {
}
