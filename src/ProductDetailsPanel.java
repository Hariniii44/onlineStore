import javax.swing.*;
import java.awt.*;

public class ProductDetailsPanel extends JPanel {
    private final JLabel product_ID;
    private final JLabel item_Category;
    private final JLabel size;
    private final JLabel product_Name;
    private final JLabel colour;
    private final JLabel brand;
    private final JLabel warranty;
    private final JLabel available_Items;

    public ProductDetailsPanel() {
        JLabel detailsPanelTopic = new JLabel("Selected product - Details ");
        product_ID = new JLabel();
        item_Category = new JLabel();
        product_Name = new JLabel();
        size = new JLabel();
        colour = new JLabel();
        brand = new JLabel();
        warranty = new JLabel();
        available_Items = new JLabel();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(detailsPanelTopic);
        add(product_ID);
        add(item_Category);
        add(product_Name);
        add(size);
        add(colour);
        add(brand);
        add(warranty);
        add(available_Items);

        setPreferredSize(new Dimension(getPreferredSize().width, 150));
        setPreferredSize(new Dimension(300, getPreferredSize().height));

    }

    public void updateDetailsToPanel(String productID, String itemCategory, String productName, String info, String availableItems) {
        product_ID.setText("Product ID: " + productID);
        item_Category.setText("Category: " + itemCategory);
        product_Name.setText("Name: " + productName);
        available_Items.setText("Items available: " + availableItems);

        if ("Electronics".equals(itemCategory)) {
            brand.setText("Brand: " + info.split(", ")[0]);
            warranty.setText("Warranty period: " +info.split(", ")[1]);

            size.setVisible(false);
            colour.setVisible(false);
            brand.setVisible(true);
            warranty.setVisible(true);

        } else if ("Clothing".equals(itemCategory)) {
            size.setText("Size: " + info.split(", ")[0]);
            colour.setText("Colour: " + info.split(", ") [1]);

            brand.setVisible(false);
            warranty.setVisible(false);
            size.setVisible(true);
            colour.setVisible(true);
        }
    }
}
