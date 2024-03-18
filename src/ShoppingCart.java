import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {
    //Attribute
    private static List<Product> productListCart;
    private final Map<Product, Integer> productQuantity = new HashMap<>();
    //constructors
    public ShoppingCart() {
        productListCart = new ArrayList<>();
    }

    //getters
    public List<Product> getProductListCart() {
        return productListCart;
    }

    //setters
    public void setProductList(List<Product> productList) {
        productListCart = productList;
    }

    //methods
    public void addProduct(Product product) {
        if (productListCart.contains(product)) {
            increaseQuantity(product);
        } else {
            productListCart.add(product);
            increaseQuantity(product);
        }
    }

    public int getQuantity(Product product) {
        return productQuantity.getOrDefault(product, 0);
    }

    public void increaseQuantity(Product product) {
        int quantityFr = getQuantity(product);
        productQuantity.put(product, quantityFr + 1);
    }

    public void removeProduct(Product product) {
        productListCart.remove(product);
    }
}
