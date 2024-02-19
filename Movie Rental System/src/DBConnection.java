import java.sql.*;
public class DBConnection {

	Connection con = null;
	Statement statement;
	ResultSet rs;
	public DBConnection() {
		
		try {
			//Load the driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//Connect with the database
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MovieRentalSystem","root","chaudhary1435");			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		} 	 
	}

}
