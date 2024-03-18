public interface ShoppingManager {
    void addNewProduct(Product product);
    void printProductsList();
    void saveProductsInFile();
    void loadFromFile();
    void collectProductInfoToAdd();
    void deleteProductByID();
    void displayMenu();
}
