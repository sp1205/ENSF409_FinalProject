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
	private ArrayList<Student> students;
	private ArrayList<Course> courses;
	private ArrayList<Registration> registrations;
	private ArrayList<CourseOffering> courseOfferings;
	

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
		
		this.objectsFromSQL();
	}
	
	public void objectsFromSQL() {
		this.getStudentsSQL();
		System.out.println("Imported tblstudents successfully!");
		this.getCoursesSQL();
		System.out.println("Imported tblcourses successfully!");
		this.getCourseOfferingSQL();
		System.out.println("Imported tblcourseoffering successfully!");
		this.getRegstrationSQL();
		System.out.println("Imported tblregistration successfully!");
		
		for(CourseOffering x: this.getCourseOfferings()) {
			for(Registration c: this.getRegistrations()) {
				if(x.getCourseOfferingID() == c.getTheOffering().getCourseOfferingID()) {
					x.addRegistration(c);
					c.getTheOffering().addRegistration(c);
				}
			}
		}
		
		System.out.println("Organized CourseOffering objects successfully by searching registration objects!");
		
	}
	
	
	public void getStudentsSQL() {
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
		this.setStudents(students);
	}
	
	public void getCoursesSQL() {
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
		this.setCourses(courses);
	}
	
	public void getCourseOfferingSQL() {
		ArrayList<CourseOffering> cof = new ArrayList<CourseOffering>();
		
		try {
			//Creating a statement for SQL query
			Statement myStatement = this.getMyConnection().createStatement();
			//Executing SQL query
			ResultSet result = myStatement.executeQuery("SELECT * FROM coursedb.tblcourseoffering");
			
			while(result.next()) {
				int courseID = Integer.parseInt(result.getString("CourseID"));
				int courseOfferingNum = Integer.parseInt(result.getString("CourseOfferingNum"));
				int courseOfferingID = Integer.parseInt(result.getString("CourseOfferingID"));
				
				Course temp = null;
				for(Course c: this.courses) {
					if(c.getCourseID() == courseID) {
						temp = c;
					}
				}
				if(temp == null) {
					System.out.println("Course iteration not successful");
				}
				CourseOffering toAdd = new CourseOffering(courseOfferingID, courseOfferingNum, temp);
				cof.add(toAdd);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Unsuccessful getting CourseOfferings from SQL database!");
		}
		this.setCourseOfferings(cof);
	}
	
	
	public void getRegstrationSQL() {
		ArrayList<Registration> reg = new ArrayList<Registration>();
		
		try {
			//Creating a statement for SQL query
			Statement myStatement = this.getMyConnection().createStatement();
			//Executing SQL query
			ResultSet result = myStatement.executeQuery("SELECT * FROM coursedb.tblregistration");
			
			while(result.next()) {
				int registrationID = Integer.parseInt(result.getString("RegistrationID"));
				int studentID = Integer.parseInt(result.getString("StudentID"));
				int courseOfferingID = Integer.parseInt(result.getString("CourseOfferingID"));
				
				Student temp = null;
				for(Student c: this.students) {
					if(c.getStudentId() == studentID) {
						temp = c;
					}
				}
				if(temp == null) {
					System.out.println("Student iteration not successful");
				}
				
				CourseOffering temp1 = null;
				for(CourseOffering c: this.courseOfferings) {
					if(c.getCourseOfferingID() == courseOfferingID) {
						temp1 = c;
					}
				}
				if(temp1 == null) {
					System.out.println("Course offering iteration not successful");
				}
				
				Registration toAdd = new Registration(registrationID);
				toAdd.setTheStudent(temp);
				toAdd.setTheOffering(temp1);
				reg.add(toAdd);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Unsuccessful getting Registration from SQL database!");
		}
		this.setRegistrations(reg);
	}
	
	public String registerStudent(int studentID, int courseOfferingID) {
		String s = "";
		s += "INSERT INTO coursedb.tblregistration ";
		s += "(StudentID, CourseOfferingID) ";
		s += "values ('" +  Integer.toString(studentID) + "', '" + Integer.toString(courseOfferingID) + "')";

		
		try {
			Statement toExecute = this.getMyConnection().createStatement();
			toExecute.executeUpdate(s);
			System.out.println("Registered student "+studentID+" for courseOffering:"+courseOfferingID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Registration not successful");
		}
		
		
		return s;
	}
	
	public String deleteRegistration(int registrationID) {
		String s = "";
		s += "DELETE FROM coursedb.tblregistration ";
		s += "WHERE RegistrationID="+Integer.toString(registrationID);
		
		try {
			Statement toExecute = this.getMyConnection().createStatement();
			int rowsDeleted = toExecute.executeUpdate(s);
			System.out.println("Deleted "+rowsDeleted+" rows from tblRegistration");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Removal not successful");
		}
		
		
		return s;	
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
	
	

	/**
	 * @return the students
	 */
	public ArrayList<Student> getStudents() {
		return students;
	}


	/**
	 * @param students the students to set
	 */
	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}


	/**
	 * @return the courses
	 */
	public ArrayList<Course> getCourses() {
		return courses;
	}


	/**
	 * @param courses the courses to set
	 */
	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}


	/**
	 * @return the registrations
	 */
	public ArrayList<Registration> getRegistrations() {
		return registrations;
	}


	/**
	 * @param registrations the registrations to set
	 */
	public void setRegistrations(ArrayList<Registration> registrations) {
		this.registrations = registrations;
	}


	/**
	 * @return the courseOfferings
	 */
	public ArrayList<CourseOffering> getCourseOfferings() {
		return courseOfferings;
	}


	/**
	 * @param courseOfferings the courseOfferings to set
	 */
	public void setCourseOfferings(ArrayList<CourseOffering> courseOfferings) {
		this.courseOfferings = courseOfferings;
	}
	
/******************************* Testing Code *******************************************************/	
	public static void main(String[] args) {
		DatabaseConnector test = new DatabaseConnector();
	
		System.out.println(test.registerStudent(10, 20));
	}
/****************************************************************************************************/
	
}
		
	
	


