import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Product {
    private int id;
    private String name;
    private double price;
    private int quantity;
    
    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    
    public Product(int id, String name, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

	public Product(int id, int quantity) {
		this.id = id;
		this.quantity = quantity;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public double getPrice() {
		return this.price;
	}

	public int getQuantity() {
		return this.quantity;
	}
	public Product getProduct(int id,Connection connection) {
		try {
        	Statement stmt = connection.createStatement();
			
			String query = "select  from customer where id = ?";
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return new Product(rs.getInt("id"),rs.getString("name"),rs.getDouble("price"),rs.getInt("quantity"));
			}
			rs.close();
			stmt.close();
        }
        catch(SQLException e) {
			System.err.println(e.getMessage());
		}
        System.err.println("Invalid User name or password");
        return null;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
		
	}
}
