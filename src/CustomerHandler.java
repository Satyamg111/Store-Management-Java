import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CustomerHandler {
	Scanner scanner;
	Connection connection;
	Customer customer;
	
	CustomerHandler(Scanner scanner,Connection connection){
		this.scanner = scanner;
		this.connection = connection;
		this.customer = null;
	}
	private void customerMenu() {
        while (true) {
        	System.out.println("1. View Products");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. Checkout");
            System.out.println("4. Logout");
            int choice = scanner.nextInt();

            switch (choice) {
            	case 1:
            		customer.viewProducts(connection);
            		break;
                case 2:
                    System.out.print("Enter product ID to add to cart: ");              
                    int id = scanner.nextInt();
                    System.out.print("Enter quantity of product : ");              
                    int quantity = scanner.nextInt();
                    customer.addToCart(id,quantity,connection);
                    break;
                case 3:
                    customer.checkout(connection);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
	public void login() {
		System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        try {
        	Statement stmt = connection.createStatement();
			
			String query = "select id,username, password from customers where username = ? and password = ?";
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.setString(2,password);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				int id = rs.getInt("id");
				customer = new Customer(id,username);
				System.out.println("Login Succsesfuly");
				customerMenu();
			}
			rs.close();
			stmt.close();
        }
        catch(SQLException e) {
			System.err.println(e.getMessage());
		}
        System.err.println("Invalid User name or password");
        
	}
	public void register() {
		System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        try {
        	Statement stmt = connection.createStatement();
			
			String query = "Insert into customers (username, password) values(?,?)";
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, username);
			pstmt.setString(2,password);
			int rs = pstmt.executeUpdate();
			if(rs > 0) {
				System.out.println("Customer registered successfully.");
				login();
			}
			stmt.close();
        }
        catch(SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	public static void customerMain(int choice, Scanner scanner, Connection connection) {
		CustomerHandler ch = new CustomerHandler(scanner, connection);
		if(choice == 0) {
			ch.login();
		}
		else {
			ch.register();
		}
	}
}
