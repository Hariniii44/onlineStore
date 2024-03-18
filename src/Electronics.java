public class Electronics extends Product {

    private String brand;
    private int warrantyPeriod;

    //Constructors
    public Electronics(String productID, String productName, int availableItems, double price, String brand, int warrantyPeriod) {
        super(productID, productName, availableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    //getters
    public String getBrand() {
        return brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    //setters
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

}
