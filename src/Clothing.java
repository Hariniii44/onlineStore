public class Clothing extends Product{
    //Attributes
    private String size;
    private String colour;

    //Constructors

    public Clothing(String productID, String productName, int availableItems, double price, String size, String colour) {
        super(productID, productName, availableItems, price);
        this.size = size;
        this.colour = colour;
    }

    //getters

    public String getSize() {
        return size;
    }

    public String getColour() {
        return colour;
    }

    //setters

    public void setSize(String size) {
        this.size = size;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    //Abstract methods
    public void displayProductDetails() {
        System.out.println("Product ID: " + getProductID());
        System.out.println("Name: " + getProductName());
        System.out.println("Available Items: " + getAvailableItems());
        System.out.println("Price: $" + getPrice());
        System.out.println("Size: " + size);
        System.out.println("Color: " + colour);
    }
}
