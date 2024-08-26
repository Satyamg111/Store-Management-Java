import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class Admin {
    private String username;
    private String password;
    
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void addProduct(Product product, Connection connection) {
    	try {
    		String query = "Insert into products(name, price, quantity) values (?,?,?);";
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1,product.getName());
			pstmt.setDouble(2,product.getPrice());
			pstmt.setDouble(3,product.getQuantity());
			int res = pstmt.executeUpdate();
			if(res > 0) {
				System.out.println("Product Added Succsesfuly");
			}
			else {
				System.out.println("Insertion failed");
			}
			pstmt.close();
		}catch(SQLException e) {
			System.err.println(e.getMessage());
		}
    	
    }

    public void removeProduct(int productId, Connection connection) {
    	try {
			String query = "Delete  from products where id = ?";
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, productId);
			int rs = pstmt.executeUpdate();
			if(rs > 0) {
				System.out.println("Product deleted");
			}
			else {
				System.out.println("Product not found");
			}
			pstmt.close();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
    }

    public void modifyProduct(int productId, Product newProduct,Connection connection) {
        // update query
    }

    public void viewProducts(Connection connection) {
    	try {
        	Statement stmt = connection.createStatement();
			
			String query = "select * from products";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("-------------------------------------------------------------------------");
			System.out.println("|\tP. Id\t|\tName\t|\tPrice\t|\tQuantity\t|");
			System.out.println("-------------------------------------------------------------------------");
			while(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				double price = rs.getDouble("price");
				int quantity = rs.getInt("quantity");
				System.out.print("|\t");
				System.out.print(id );
				System.out.print("\t|\t");
				System.out.print( name );
				System.out.print("\t|\t");
				System.out.print(price);
				System.out.print("\t|\t");
				System.out.print(quantity);
				System.out.println("\t\t|");
				System.out.println("-------------------------------------------------------------------------");
			}
			rs.close();
			stmt.close();
        }
        catch(SQLException e) {
			System.err.println(e.getMessage());
		}
    }

    public void viewCustomers(Connection connection) {
    	try {
        	Statement stmt = connection.createStatement();
			
			String query = "select * from customers";
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("----------------------------------------------------");
			System.out.println("|\tC. Id\t|\tUser name\t|");
			System.out.println("----------------------------------------------------");
			while(rs.next()) {
				int id = rs.getInt("id");
				String userName = rs.getString("username");
				System.out.print("|\t");
				System.out.print(id );
				System.out.print("\t|\t");
				System.out.print( userName );
				System.out.println("\t\t|");
				System.out.println("------------------------------------------------");
			}
			rs.close();
			stmt.close();
        }
        catch(SQLException e) {
			System.err.println(e.getMessage());
		}
    }
	public String getUsername() {
		return this.username;
	}

	public String  getPassword() {
		return this.password;
	}

}
