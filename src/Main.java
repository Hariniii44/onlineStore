import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to westminster shopping system");

        while (true) {
            System.out.println("Are you a manger or a customer? Enter 'm' for manager and 'c for customer': ");
            String userType = scanner.nextLine().toLowerCase();

            if ("m".equals(userType)) {
                System.out.println("You've selected manager. Displaying menu console:");
                WestminsterShoppingManager westminsterShoppingManager = new WestminsterShoppingManager();
                westminsterShoppingManager.displayMenu();
                break;
            } else if ("c".equals(userType)) {
                System.out.println("You've selected customer. Displaying customer Graphical user interface:");
                System.out.println("Enter your Username: ");
                String username = scanner.nextLine();
                System.out.println("Enter your password: ");
                String password = scanner.nextLine();
                User user = new User(username, password);
                WestminsterShoppingManager westminsterShoppingManager = new WestminsterShoppingManager();
                ShoppingCartGUI shoppingGUI = new ShoppingCartGUI(westminsterShoppingManager);
                break;
            } else {
                System.out.println("Invalid input. Please enter again.");
            }
        }
    }
}