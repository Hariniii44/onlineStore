import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {
    private final WestminsterShoppingManager westminsterShoppingManager;

    public CustomTableCellRenderer(WestminsterShoppingManager westminsterShoppingManager) {
        this.westminsterShoppingManager = westminsterShoppingManager;
    }


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        String productID = table.getValueAt(row, 0).toString();
        Product prodInRow = westminsterShoppingManager.findProductByID(productID);

        if (prodInRow != null) {
            int availableItems = prodInRow.getAvailableItems();
            if (availableItems < 3) {
                comp.setBackground(Color.RED);
            } else {
                comp.setBackground(table.getBackground());
            }
        }
        return comp;
    }
}
