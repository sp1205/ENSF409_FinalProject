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
		ArrayList<Student> students = new ArrayList<Student>();
		
		try {
			//Creating a statement for SQL query
			Statement myStatement = this.getMyConnection().createStatement();
			//Executing SQL query
			ResultSet result = myStatement.executeQuery("SELECT * FROM coursedb.tblstudents");
			
			while(result.next()) {
				students.add(new Student(result.getString("StudentName"), Integer.parseInt(result.getString("StudentID"))));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Unsuccessful getting students from SQL database!");
		}
		return students;
	}
	
	public ArrayList<Course> getCoursesSQL() {
		ArrayList<Course> courses = new ArrayList<Course>();
		
		try {
			//Creating a statement for SQL query
			Statement myStatement = this.getMyConnection().createStatement();
			//Executing SQL query
			ResultSet result = myStatement.executeQuery("SELECT * FROM coursedb.tblcourses");
			
			while(result.next()) {
				courses.add(new Course(result.getString("CourseName"), Integer.parseInt(result.getString("CourseID"))));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Unsuccessful getting Courses from SQL database!");
		}
		return courses;
	}
	
	
	
	
	
	/**
	 * @return the myConnection
	 */
	public Connection getMyConnection() {
		return myConnection;
	}


	/**
	 * @param myConnection the myConnection to set
	 */
	public void setMyConnection(Connection myConnection) {
		this.myConnection = myConnection;
	}
	
	public static void main(String[] args) {
		DatabaseConnector test = new DatabaseConnector();
		System.out.println(test.getCoursesSQL());
		
		
	}
	
	
}
		
	
	



