import java.io.*;
import java.util.*;


public class WestminsterShoppingManager implements ShoppingManager {
    //Attributes
    private final List<Product> productList = new ArrayList<>();

    //constructors
    public WestminsterShoppingManager() {
        loadFromFile(); //read back the info saved in a file
    }

    //methods
    //add a new product to the system
    @Override
    public void addNewProduct(Product product) {
        try {
            if (productList.size() < 50) {
                productList.add(product);
                System.out.println("Product added successfully. ");
                System.out.println("Product added: " + product.getProductName());
            } else {
                System.out.println("Maximum product limit reached!");
            }
        } catch (NullPointerException e) {
            System.out.println("Unable to add product." );
        }
    }

    public static boolean isProductAlreadyExisting(List<Product> products, String newProductID) {
        for (Product product : products) {
            if (product.getProductID().equals(newProductID)) {
                return true;
            }
        }
        return false;
    }

    //method to collect product info
    public void collectProductInfoToAdd() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the product details: ");
        System.out.println("Enter product ID (Eg: A100): ");
        String productID = scanner.nextLine();
        while (productID.length() != 4 || !productID.matches("[A-Z][0-9][0-9][0-9]")) {
            System.out.println("Invalid Product ID. Please enter an ID of this format: A100 ");
            productID = scanner.nextLine();
            while (isProductAlreadyExisting(productList, productID)) {
                System.out.println("This productID already exists. Please enter a new Product ID. ");
                productID = scanner.nextLine();
            }
        }

        System.out.println("Enter product name: ");
        String productName = scanner.nextLine();
        while (productName.isEmpty() || !productName.matches("[A-Za-z0-9]+")) {
            System.out.println("Invalid name. Please enter a valid name. ");
            productName = scanner.nextLine();
        }
        System.out.println("Enter available items: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        int availableItems = scanner.nextInt();
        while (availableItems < 0) {
            System.out.println("Invalid input. Available items cannot be less that zero.  ");
            availableItems = scanner.nextInt();
        }
        System.out.println("Enter the price: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a number");
            scanner.next();
        }
        double price = scanner.nextDouble();
        while (price < 0) {
            System.out.println("Invalid input. Please enter a non negative value. ");
            price = scanner.nextDouble();
        }

        //check whether the product is electronic or clothing
        System.out.println("Enter the type of the product (E for electronic and C for clothing: ");
        String productType = scanner.next();
        while (!productType.equalsIgnoreCase("E") && !productType.equalsIgnoreCase("C")) {
            System.out.println("Invalid input. Please enter E for electronic and C for clothing. ");
            productType = scanner.next();
        }
        if (productType.equalsIgnoreCase("E")) {
            System.out.println("Brand: ");
            String brand = scanner.next();
            while (brand.isEmpty()) {
                System.out.println("Invalid input. Please enter a valid brand name. ");
                brand = scanner.next();
            }
            System.out.println("Warranty period (In months): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number. ");
                scanner.next();
            }
            int warrantyPeriod = scanner.nextInt();
            while (warrantyPeriod < 0) {
                System.out.println("Invalid input. Please enter a non negative value. ");
                warrantyPeriod = scanner.nextInt();
            }
            Electronics electronic_product = new Electronics(productID, productName, availableItems, price, brand, warrantyPeriod); //creates an Electronics object
            addNewProduct(electronic_product);

        } else if (productType.equalsIgnoreCase("C")) {
            System.out.println("Size: (S, M, L)");
            String size = scanner.next();
            while (!size.matches("[SML]")) {
                System.out.println("Invalid input. Please enter a valid size (S,M or L )");
                size = scanner.next();
            }
            System.out.println("Colour: ");
            String colour = scanner.next();
            while (colour.isEmpty()) {
                System.out.println("Invalid input. Please enter a valid colour. ");
                colour = scanner.next();
            }
            Clothing clothing_product = new Clothing(productID, productName, availableItems, price, size, colour);
            addNewProduct(clothing_product);
        }
    }

    //get productID and delete product
    public void deleteProductByID() {
        if (productList.isEmpty()) {
            System.out.println("All products are removed from the system. ");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the product ID to delete: ");
        String deleteProductID = scanner.nextLine();
        while (deleteProductID.length() != 4 || !deleteProductID.matches("[A-Z][0-9][0-9][0-9]")) {
            System.out.println("Invalid Product ID. Please enter an ID of this format: A100 ");
            deleteProductID = scanner.nextLine();
        }
        Iterator<Product> iterator = productList.iterator();
        boolean found = false;

        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getProductID().equals(deleteProductID)) {
                iterator.remove(); //remove the product
                found = true;
                System.out.println("Product successfully deleted.");
                System.out.println("Product deleted: " + product.getProductName());
                if (product instanceof Electronics) {
                    System.out.println("Deleted product was an electronics product.");
                } else if (product instanceof  Clothing) {
                    System.out.println("Deleted product was a Clothing product.");
                } else {
                    System.out.println("The type of the deleted product was not stated.");
                }
                break;
            }
            System.out.println("The total number of products left in the system: " + productList.size());
        }
        if (!found) {
            System.out.println("Product cannot be found. ");
        }
    }

    //Print a detailed list of products in the system
    @Override
    public void printProductsList() {
        //sorting the productList
        productList.sort(Comparator.comparing(Product::getProductID));
        System.out.println("Products in the system: ");
        for (Product product : productList) {
            System.out.println(product.toString());
            if (product instanceof Electronics) {   //to determine whether it's electronic or clothing.
                System.out.println("This product is an electronic product. ");
            } else if (product instanceof Clothing) {
                System.out.println("This product is a clothing product. ");
            }
        }
    }

    //save products in a file
    @Override
    public void saveProductsInFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Product_Info"))) {
            for (Product product : productList) {
                writer.write("Product ID: " + product.getProductID() + "\n");
                writer.write("Product Name: " + product.getProductName() + "\n");
                writer.write("Price: " + product.getPrice() + "\n");
                writer.write( "Available Items: " + product.getAvailableItems() + "\n");
                if (product instanceof Electronics electronics) {
                    writer.write("Type: Electronics\n");
                    writer.write("Brand: "+ electronics.getBrand() + "\n");
                    writer.write("Warranty Period: " + electronics.getWarrantyPeriod());
                }
                if (product instanceof Clothing clothing) {
                    writer.write("Type: Clothing\n");
                    writer.write("Size: "+ clothing.getSize() + "\n");
                    writer.write("Colour: " + clothing.getColour());
                }
                writer.newLine();
            }
            System.out.println("Products are saved to the file: Product_Info");
        } catch (IOException e) {
            System.err.println("Error. Product was not saved to the file: " + e.getMessage());
        }
    }

    //To add the previous product info to the system when the system is started from the beginning.
    public void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Product_Info"))) {
            String line;
            String productID = null;
            String productName = null;
            double price = 0.0;
            int availableItems = 0;
            String brand;
            int warrantyPeriod;
            String size;
            String color;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Product ID: ")) {   //reading line by line
                    productID = line.substring(12);
                }
                if (line.startsWith("Product Name: ")) {
                    productName = line.substring(14);
                }
                if (line.startsWith("Price: ")) {
                    price = Double.parseDouble(line.substring(7));
                }
                if (line.startsWith("Available Items: ")) {
                    availableItems = Integer.parseInt(line.substring(17));
                }
                if (line.startsWith("Type: ")) {
                    try {
                        if (line.endsWith("Electronics")) {
                            line = reader.readLine();
                            brand = line.substring(7);
                            line = reader.readLine();
                            warrantyPeriod = Integer.parseInt(line.substring(17));
                            Electronics electronics = new Electronics(productID, productName, availableItems, price, brand, warrantyPeriod);
                            productList.add(electronics);
                        } else if (line.endsWith("Clothing")) {
                            line = reader.readLine();
                            size = line.substring(6);
                            line = reader.readLine();
                            color = line.substring(8);
                            Clothing clothing = new Clothing(productID, productName, availableItems, price, size, color);
                            productList.add(clothing);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Error occurred while reading the file. " + e.getMessage());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File is not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error. Product list cannot be loaded from file. " + e.getMessage());
        }
    }

    //menu with the list of options
    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Welcome to Westminster Shopping Manager.");
            System.out.println("Select an option: ");

            System.out.println("1: Add a new product to the system.");
            System.out.println("2: Delete a product from the system.");
            System.out.println("3: Print a detailed list of the products in the system.");
            System.out.println("4: Save the product list to a file");
            System.out.println("0: Exit shopping manager menu.");

            System.out.println("Enter you choice (1,2,3,4 or 0): ");

//            choice = scanner.nextInt();
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 -4. ");
                scanner.next();
            }
            choice = scanner.nextInt();

            if (choice < 0 || choice > 4) {
                System.out.println("Invalid choice. Please enter a number between 0 - 4. ");
                continue;
            }

            switch (choice) {
                case 1 -> collectProductInfoToAdd();
                case 2 -> deleteProductByID();
                case 3 -> printProductsList();
                case 4 -> saveProductsInFile();
                case 0 -> System.out.println("Exiting the shopping manager.");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
        scanner.close();
    }

    //get product list to enter to the gui table. This method is used in setProductTableData method in ShoppingCartGUI class.
    public List<Product> getProductList() {
        return productList;
    }

    //to get the available items of a certain product to the product details panel. used in ShoppingCartGUI class actionListener.
    public Product findProductByID(String product_ID) {
        for (Product product : productList) {
            if (product.getProductID().equals(product_ID)) {
                return product;
            }
        }
        return null;
    }
}
