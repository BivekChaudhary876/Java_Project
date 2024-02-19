package com.student;

import java.sql.*;
public class DBConnection {

	Connection con = null;
	Statement stmt;
	ResultSet rs;
	public DBConnection() {
		
		try {
			//Load the driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Connect with the database
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CSITStudents","root","chaudhary1435");
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
   	 
	}

}
