import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	String url = "jdbc:mysql://localhost:3306/store_db";
	String userName = "root";
	String password = "Satyam@123";
	Database() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded succesfully");
			
		} catch(ClassNotFoundException e) {
			System.err.println(e.getMessage());
		}
	}
	Connection connectDB() {
		Connection connection;
		try {
			connection = DriverManager.getConnection(url,userName,password);
			System.out.println("DB connnected succesfully");
		}catch(SQLException e) {
			System.err.println("Connection failure " + e.getMessage());
			connection = null;
		}
		return connection;
	}
}
