import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Customer {
	private int id;
    private String username;
    private String password;
    private List<Product> cart;

    public Customer(int id,String username) {
    	this.id = id;
        this.username = username;
        this.cart = new ArrayList<>();
    }
    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
        this.cart = new ArrayList<>();
    }
    public Customer() {
		// TODO Auto-generated constructor stub
	}
    private Product getProduct(int id,Connection connection) {
    	Product product = null;
    	try {

        	Statement stmt = connection.createStatement();
			
			String query = "select * from products where id = ?";
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				String name = rs.getString("name");
				double price = rs.getDouble("price");
				int qty = rs.getInt("quantity");
				product = new Product(id,name,price,qty);
				
			}else {
				System.err.println("Product not found");
			}
			rs.close();
			stmt.close();
			return product;
        }
        catch(SQLException e) {
			System.err.println(e.getMessage());
		}
    	return null;
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
	
    public void addToCart(int id,int quantity,Connection connection) {
    	Product product = getProduct(id,connection);
    	if(product == null) {
    		System.err.println("Product not found");
    		return;
    	}
    	else if(product.getQuantity() < quantity) {
    		System.err.println("Quantuty eceeds than stock");
    		return;
    	}
    	try {
        	Statement stmt = connection.createStatement();
			
			String query = "Insert into carts (customer_id,product_id) Values(?,?)";
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1,this.id);
			pstmt.setInt(2, id);
			int rs = pstmt.executeUpdate();
			if(rs > 0) {
				product.setQuantity(quantity);
				cart.add(product);
				System.out.println("Product added to cart");
				
			}else {
				System.err.println("Product not found");
			}
			stmt.close();
        }
        catch(SQLException e) {
			System.err.println(e.getMessage());
		}
    }
    
    
    public void removeToCart(int productId,Connection connection) {
    	// remove product from cart
    }
    
    
    public void checkout(Connection connection) {
    	if(cart.isEmpty()) {
    		System.err.println("Cart is empty");
    		return;
    	}
    	try {
			String query = "Update products Set quantity = products.quantity - ? ";
			PreparedStatement pstmt = connection.prepareStatement(query);
			for(Product prodcut : cart) {
				pstmt.setInt(1,prodcut.getQuantity());
				int rs = pstmt.executeUpdate();
			}
			cart.removeAll(cart);
			System.out.println("Checkout Successfuly");
			pstmt.close();
        }
        catch(SQLException e) {
			System.err.println(e.getMessage());
		}
    }

	public String getUsername() {
		return this.username;
	}

	public Object getPassword() {
		return this.password;
	}
}
