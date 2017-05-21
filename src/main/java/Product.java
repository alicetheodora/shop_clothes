import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

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
    private String size;
    private String category;

    Product(){
        name = null;
        description = null;
        price = 0;
        size = null;
    }

    public boolean init(String category){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String aux = new String();
        System.out.println("Please type in the new product's details, or \" cancel\" to go back.");
        try{
            System.out.println("Name: ");
            name = reader.readLine();
            while(name.equals("")){
                System.out.println("Name must not be null.");
                name =reader.readLine();
            }
            if(name.toLowerCase().equals("cancel"))
                return false;

            System.out.println("Description: ");
            description = reader.readLine();
            while(description.equals("")){
                System.out.println("Description must not be null.");
                description =reader.readLine();
            }
            if(description.toLowerCase().equals("cancel"))
                return false;

            System.out.println("Price: ");
            aux = reader.readLine();
            if(aux.toLowerCase().equals("cancel"))
                return false;
            while( Integer.parseInt(aux) <= 0 ){
                System.out.println("Price must be greater than 0.");
                aux = reader.readLine();
                if(aux.toLowerCase().equals("cancel"))
                    return false;
            }
            price = Integer.parseInt(aux);

            System.out.println("Size (separated by spaces): ");
            aux = reader.readLine(); aux = aux.toUpperCase();
            size = aux;
            aux = aux.replace("XS", "");
            aux = aux.replace("S", "");
            aux = aux.replace("M", "");
            aux = aux.replace("XXXL", "");
            aux = aux.replace("XXL", "");
            aux = aux.replace("XL", "");
            aux = aux.replace("L", "");
            aux = aux.replaceAll(" ", "");
            while( size.equals("") ||!aux.equals("") ){
                if(size.toLowerCase().equals("cancel"))
                    return false;
                System.out.println("String must be of format \" XS S M L XL XXL XXXL\", must have at least one category.");
                aux = reader.readLine(); aux = aux.toUpperCase();
                size = aux;
                //Long I know, it's the same operations above
                aux = aux.replace("XS", "");aux = aux.replace("S", "");aux = aux.replace("M", "");aux = aux.replace("XXXL", "");aux = aux.replace("XXL", "");aux = aux.replace("XL", "");aux = aux.replace("L", "");aux = aux.replaceAll(" ", "");            }
            if(size.toLowerCase().equals("cancel"))
                return false;
            this.category = category;
            return true;
        }catch(Exception e){
            System.out.println("Failed to read data from Product.init()");
            System.out.println("Error: " + e);
            return false;
        }
    }

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

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }

    @Override
    public String toString(){
        return "Name: \"" + this.name +
                "\"; Description: \"" + this.description +
                "\"; Price: " + this.price +
                "\"; Size: \"" + this.size +
                "\"; Category: \"" + this.category + "\".";
    }

}
