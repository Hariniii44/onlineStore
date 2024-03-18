import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ShoppingCartPanel extends JPanel {
    private final DefaultTableModel shoppingCartTableModel;
    private final ShoppingCart shoppingCart;
    private final JLabel total;
    private final JLabel categoryDisc;
    private final JLabel finalTotal;

    public ShoppingCartPanel(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
        shoppingCartTableModel = new DefaultTableModel();
        JTable shoppingCartTable = new JTable(shoppingCartTableModel);

        setLayout(new BorderLayout());
        add(new JScrollPane(shoppingCartTable), BorderLayout.CENTER);

        JPanel additionalLabelsPanel = new JPanel();
        additionalLabelsPanel.setLayout(new BoxLayout(additionalLabelsPanel, BoxLayout.Y_AXIS));

        total = new JLabel();
        additionalLabelsPanel.add(total);

        categoryDisc = new JLabel();
        additionalLabelsPanel.add(categoryDisc);

        finalTotal = new JLabel();
        additionalLabelsPanel.add(finalTotal);

        add(additionalLabelsPanel, BorderLayout.SOUTH);
    }

    public void updateShoppingCart() {
        List<Product> newList = shoppingCart.getProductListCart();
        double total_price = 0;
        double final_total;
        int electronic_count = 0;
        int clothing_count = 0;

        //convert the list to a 2D array
        Object[][] cartData = new Object[newList.size()][];
        for (int i = 0; i < newList.size(); i++) {
            Product product = newList.get(i);

            int quantity = shoppingCart.getQuantity(product);
            double totalPrice = product.getPrice()* quantity;  //total of each product
            total_price += totalPrice;  //total of all products

            Object[] Data;
            if (product instanceof Electronics electronics) {
                Data = new Object[]{
                        product.getProductID() + ", " + product.getProductName() + ", " + electronics.getBrand() + ", " + electronics.getWarrantyPeriod(), quantity, totalPrice
                };
                electronic_count += 1;
            } else if (product instanceof Clothing clothing) {
                Data = new Object[]{
                        product.getProductID() + ", " + product.getProductName() + ", " + clothing.getSize() + ", " + clothing.getColour(), quantity, totalPrice
                };
                clothing_count += 1;
            } else {
                Data = new Object[]{
                        product.getProductID() + ", " + product.getProductName(), quantity, totalPrice
                };
            }
            cartData[i] = Data;
        }
        //column names
        String[] columnNames = {"Product", "Quantity", "Price"};

        shoppingCartTableModel.setDataVector(cartData, columnNames);

        total.setText("Total: $ " + total_price);

        double category_disc = 0;
        if (clothing_count >= 3) {
            category_disc = total_price * 0.2;
            categoryDisc.setText("Three Items in same Category Discount (20%): $ -" + category_disc);
        } else if (electronic_count >= 3) {
            category_disc = total_price * 0.2;
            categoryDisc.setText("Three Items in same Category Discount (20%): $ -" + category_disc);
        }
        final_total = total_price - category_disc;
        finalTotal.setText("Final Total: $ " + final_total);

    }
}
