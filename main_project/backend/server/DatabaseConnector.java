package backend.server;
import java.util.*;
import backend.models.*;
import java.sql.*;

interface Constants {
	static final String URL = "jdbc:mysql://localhost:3306/coursedb";
	static final String USER = "root";
	static final String PASSWORD = "root";	
}

public class DatabaseConnector implements Constants {
	
	Connection myConnection;
	
	public DatabaseConnector() {
		System.out.println("Starting conection now");
		try {
			this.myConnection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Connection successful!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Connection unsuccessful!");
		}
	}
	
	
	public ArrayList<Student> getStudentsSQL() {
	
	}
	
	
	
	
	}
		
}
	
	



