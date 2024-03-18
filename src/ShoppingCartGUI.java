import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartGUI extends JFrame {
    private final JComboBox<String> productDropdownMenu;
    private final JTable productListTable;
    private final DefaultTableModel productTableModel;
    private final ProductDetailsPanel productDetailsPanel;
    private final WestminsterShoppingManager westminsterShoppingManager;
    private final ShoppingCart shoppingCart;
    private final ShoppingCartPanel shoppingCartPanel;

    //constructors
    public ShoppingCartGUI(WestminsterShoppingManager westminsterShoppingManager) {
        productDropdownMenu = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        productTableModel = new DefaultTableModel();
        productListTable = new JTable(productTableModel);
        productDetailsPanel = new ProductDetailsPanel();
        JButton addToCartButton = new JButton();
        JButton viewCart = new JButton();

        //layout of main
        setLayout(new GridBagLayout());
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.weightx = 0;
        constraint.weighty = 0;
        add(productDropdownMenu, constraint);

        constraint.gridx = 0;
        constraint.gridy = 1;
        constraint.weightx = 1.0;
        constraint.weighty = 0.5;
        add(new JScrollPane(productListTable), constraint);

        constraint.gridx = 0;
        constraint.gridy = 2;
        constraint.weightx = 1;
        constraint.weighty = 1;
        add(productDetailsPanel, constraint);

        addToCartButton = new JButton("Add to Shopping Cart");
        constraint.gridx = 0;
        constraint.gridy = 3;
        constraint.weightx = 0;
        constraint.weighty = 0;
        add(addToCartButton, constraint);

        JButton sortButton = new JButton("Sort");
        constraint.gridx = 1;
        constraint.gridy = 0;
        constraint.weightx = 0.2;
        constraint.anchor = GridBagConstraints.NORTHEAST;
        add(sortButton, constraint);

        viewCart = new JButton("Shopping Cart");
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.weightx = 0.2;
        constraint.anchor = GridBagConstraints.NORTHEAST;
        add(viewCart, constraint);

        JLabel menuTopic = new JLabel("Select Product Category ");
        constraint.gridx = 0;
        constraint.gridy = 0;
        constraint.weightx = 1;
        constraint.anchor = GridBagConstraints.NORTHWEST;
        add(menuTopic, constraint);

        //properties of the frame
        setTitle("Westminster Shopping Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setSize(600, 800);
        setLocationRelativeTo(null);
        setVisible(true);

        this.westminsterShoppingManager = westminsterShoppingManager;

        //set the structure for the table
        configureProductTableStructure();

        //productDropDownMenu_productListTable
        productDropdownMenu.addActionListener(e -> {
            String selectedCategory = (String) productDropdownMenu.getSelectedItem();

            //Set data for the product table
            setProductTableData(selectedCategory);
        });

        //get data to the details panel
        productListTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int clickedRow = productListTable.getSelectedRow();
                if (clickedRow != -1) {
                    String product_ID = productListTable.getValueAt(clickedRow, 0).toString();
                    String product_Name = productListTable.getValueAt(clickedRow, 1).toString();
                    String itemCategory = productListTable.getValueAt(clickedRow, 2).toString();
                    String info = productListTable.getValueAt(clickedRow, 4).toString();

                    Product selectedProd = westminsterShoppingManager.findProductByID(product_ID);
                    String st_available_items = "";
                    if (selectedProd != null) {
                        int availableItems = selectedProd.getAvailableItems();
                        st_available_items = String.valueOf(availableItems);
                    }

                    productDetailsPanel.updateDetailsToPanel(product_ID, itemCategory, product_Name, info, st_available_items);
                }
            }
        });

        this.shoppingCart = new ShoppingCart();
        shoppingCartPanel = new ShoppingCartPanel(shoppingCart);

        //add product to shopping cart by clicking button
        addToCartButton.addActionListener(e -> addToCart());

        //view shopping cart by clicking shopping cart button
        viewCart.addActionListener(e -> showShoppingCart());

        //Sort the productListTable
        sortButton.addActionListener(e -> sortProductListTableByName());
    }

    private void sortProductListTableByName() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(productTableModel);
        productListTable.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();
    }

    private void addToCart() {
        int clickedRow = productListTable.getSelectedRow();
        if (clickedRow != -1) {
            String productID = productListTable.getValueAt(clickedRow, 0).toString();
            Product selectedProd = westminsterShoppingManager.findProductByID(productID);
            if (selectedProd != null) {
                shoppingCart.addProduct(selectedProd);
                shoppingCartPanel.updateShoppingCart();
            }
        }
    }

    private void showShoppingCart() {
        JFrame shoppingCartFrame = new JFrame("Shopping Cart");
        shoppingCartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        shoppingCartFrame.add(shoppingCartPanel);

        shoppingCartFrame.setSize(400, 300);
        shoppingCartFrame.setLocationRelativeTo(this);
        shoppingCartFrame.setVisible(true);
    }

    public void configureProductTableStructure() {
        TableColumnModel columnModel = productListTable.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(100);
            } else {
                column.setPreferredWidth(150);
            }
        }
    }

    private List<Product> filterProductCategory(List<Product> productList, Class<?> classCategory) {          //for the dropdown menu
        List<Product> filteredList = new ArrayList<>();
        for (Product product : productList) {
            if (classCategory.isInstance(product)) {
                filteredList.add(product);
            }
        }
        return filteredList;
    }

    public void setProductTableData(String selectedCategory) {
        List<Product> productList;

        if ("Electronics".equals(selectedCategory)) {
            productList = filterProductCategory(westminsterShoppingManager.getProductList(), Electronics.class);
        } else if ("Clothing".equals(selectedCategory)) {
            productList = filterProductCategory(westminsterShoppingManager.getProductList(), Clothing.class);
        } else {
            productList = westminsterShoppingManager.getProductList();    //if "All" is pressed
        }

        //convert the data in the list to a 2D array
        Object[][] productData = new Object[productList.size()][];
        for (int i = 0; i < productList.size(); i++) {
            Product product = productList.get(i);

            //check whether electronic or clothing
            Object[] Data;
            if (product instanceof Electronics electronics) {
                String type = "Electronics";
                Data = new Object[]{
                        product.getProductID(), product.getProductName(), type, product.getPrice(), electronics.getBrand() + ", " + electronics.getWarrantyPeriod()
                };
            } else if (product instanceof Clothing clothing) {
                String type = "Clothing";
                Data = new Object[]{
                        product.getProductID(), product.getProductName(), type, product.getPrice(), clothing.getSize() + ", " + clothing.getColour()
                };
            } else {
                String type = "-";
                Data = new Object[]{
                        product.getProductID(), product.getProductName(), type, product.getPrice()
                };
            }
            productData[i] = Data;
        }

        //column names
        String[] columnNames = {"Product ID", "Product Name", "Category", "Price", "Info"};

        //make the cell non-editable
        DefaultTableModel nonEditable = new DefaultTableModel(productData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        productTableModel.setDataVector(productData, columnNames);   //set data
        productListTable.setModel(nonEditable);     //cell non-editable
        //reducedAvailability();

        productListTable.setDefaultRenderer(Object.class, new CustomTableCellRenderer(westminsterShoppingManager));
    }
}
