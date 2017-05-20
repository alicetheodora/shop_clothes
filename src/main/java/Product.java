/*
Table format:
name = primary key
sizes = string with sizes separated by space ' '
available sizes: XS S M L XL XXL XXXL
quantity
 */
public class Product {
    private String name;
    private String description;
    private int price;
    private String []s = new String[7];
    private String size;


    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price=price;
    }


    public String[] getS() {
        return s;
    }

    public void setS(String[] s) {
        this.s = s;
    }


    public void sizes(){
        s[0]="XS";
        s[1]="S";
        s[2]="M";
        s[3]="L";
        s[4]="XL";
        s[5]="XXL";
        s[6]="XXXL";

    }


}
