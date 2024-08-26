import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StoreManagement {
	
    Database db;
    private Admin admin;

    public StoreManagement() {
        db = new Database();
        admin = new Admin("admin", "admin");  // Simple admin login
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        Connection connection = db.connectDB();
        if(connection == null) {
        	System.out.println("DB Connection Error");
        	scanner.close();
        	return;
        	
        }
        while (true) {
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login");
            System.out.println("3. Register Customer");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    adminLogin(scanner,connection);
                    break;
                case 2:
                    CustomerHandler.customerMain(0,scanner,connection);
                    break;
                case 3:
                	CustomerHandler.customerMain(1,scanner,connection);
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void adminLogin(Scanner scanner, Connection connection) {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
        	System.out.println("Admin logged in");
            adminMenu(scanner,connection);
        } else {
            System.out.println("Invalid login.");
        }
    }
    
    private void adminMenu(Scanner scanner, Connection connection) {
        while (true) {
            System.out.println("1. Add Product");
            System.out.println("2. Remove Product");
            System.out.println("3. Modify Product");
            System.out.println("4. View Products");
            System.out.println("5. View Customers");
            System.out.println("6. Logout");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter product name: ");
                    scanner.next();
                    String name = scanner.nextLine();
                    System.out.print("Enter product price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter product quantity: ");
                    int quantity = scanner.nextInt();
                    admin.addProduct(new Product(name, price, quantity),connection);
                    break;
                case 2:
                    System.out.print("Enter product ID to remove: ");
                    int removeId = scanner.nextInt();
                    admin.removeProduct(removeId,connection);
                    break;
                case 3:
                    System.out.print("Enter product ID to modify: ");
                    int modifyId = scanner.nextInt();
                    System.out.print("Enter new product name: ");
                    String newName = scanner.next();
                    System.out.print("Enter new product price: ");
                    double newPrice = scanner.nextDouble();
                    System.out.print("Enter new product quantity: ");
                    int newQuantity = scanner.nextInt();
                    admin.modifyProduct(modifyId, new Product(newName, newPrice, newQuantity),connection);
                    break;
                case 4:
                    admin.viewProducts(connection);
                    break;
                case 5:
                    admin.viewCustomers(connection);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    

    public static void main(String[] args) {
    	StoreManagement system = new StoreManagement();
        system.run();
        System.out.println("System closed");
    }
}
