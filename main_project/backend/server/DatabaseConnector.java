package backend.server;

import java.util.*;
import backend.models.*;
import java.sql.*;

/**
 * Constants being used in the following class DatabaseConnector
 * @author Janam Marthak
 *
 */
interface Constants {
	
	/**
	 * URL for the jdbc connection to database
	 */
	static final String URL = "jdbc:mysql://localhost:3306/coursedb";
	/**
	 * USERID for database
	 */
	static final String USER = "root";
	/**
	 * Password for database for the user defined above
	 */
	static final String PASSWORD = "root";	
}

public class DatabaseConnector implements Constants {
	/**
	 * Connection member variable for the database
	 */
	Connection myConnection;
	/**
	 * ArrayList that stores all the students from database
	 */
	private ArrayList<Student> students;
	/**
	 * ArrayList that stores all the courses from database
	 */
	private ArrayList<Course> courses;
	/**
	 * ArrayList that stores all the registrations from database
	 */
	private ArrayList<Registration> registrations;
	/**
	 * ArrayList that stores all the courseOfferings from database
	 */
	private ArrayList<CourseOffering> courseOfferings;

	/**
	 * ArrayList that stores the buffer for insert/delete queries to be executed at exit to commit to database
	 */
	private ArrayList<String> exitQueriesSQL;
	
	/**
	 * HashTable to find Registration object using Registration ID
	 */
	private Hashtable<Integer, Registration> registrationMap;
	/**
	 * HashTable to find CourseOffering object using CourseOfferingID
	 */
	private Hashtable<Integer, CourseOffering> courseOfferingMap;
	/**
	 * HashTable to find Student object using Student ID
	 */
	private Hashtable<Integer, Student> studentIDMap;
	/**
	 * HashTable to find Student object using Student name
	 */
	private Hashtable<String, Student> studentsMap;
	/**
	 * HashTable to find Course object using course name
	 */
	private Hashtable<String, Course> courseMap;

	/**
	 * Default constructor which constructs the DatabaseConnector, initiates connection to database
	 * and runs the queries and the functions below to pull data from database and put it inside the
	 * member variable array lists to work with locally.
	 */
	public DatabaseConnector() {
		System.out.println("Starting conection now");
		try {
			//Connecting to database on localhost
			this.myConnection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Connection successful!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Connection unsuccessful!");
		}
		//method executed to pull data from SQL database and store them inside model objects
		this.objectsFromSQL();
		//Initiating the exit queries buffer
		this.setExitQueriesSQL(new ArrayList<String>());

		this.studentsMap = new Hashtable<String, Student>();
		this.courseMap = new Hashtable<String, Course>();
		this.studentIDMap = new Hashtable<Integer, Student>();
		this.registrationMap = new Hashtable<Integer, Registration>();
		this.courseOfferingMap = new Hashtable<Integer, CourseOffering>();
		
		//populating HashTables
		for (Course c : this.courses) {
			this.courseMap.put(c.getCourseName(), c);
		}
		for (Student c : this.students) {
			this.studentsMap.put(c.getStudentName(), c);
			this.studentIDMap.put(c.getStudentId(), c);
		}
		for(CourseOffering c : this.courseOfferings) {
			this.courseOfferingMap.put(c.getCourseOfferingID(), c);
		}
		for(Registration c : this.registrations) {
			this.registrationMap.put(c.getRegistrationID(), c);
		}
		
	}
	
	/**
	 * This method calls all the methods that contain queries to pull data from SQL database
	 * and store them inside member variables
	 */
	public void objectsFromSQL() {
		this.getStudentsSQL();
		System.out.println("Imported tblstudents successfully!");
		this.getCoursesSQL();
		System.out.println("Imported tblcourses successfully!");
		this.getCourseOfferingSQL();
		System.out.println("Imported tblcourseoffering successfully!");
		this.getRegstrationSQL();
		System.out.println("Imported tblregistration successfully!");
		
		//Populating registration objects inside CourseOfferings
		for(CourseOffering x: this.getCourseOfferings()) {
			for(Registration c: this.getRegistrations()) {
				if(x.getCourseOfferingID() == c.getTheOffering().getCourseOfferingID()) {
					x.addRegistration(c);
					c.getTheOffering().addRegistration(c);
				}
			}
		}
		
		//Populating CourseOffering objects inside relevant course
		for(Course c: this.getCourses()) {
			for(CourseOffering x: this.getCourseOfferings()) {
				if(c.getCourseID() == x.getTheCourse().getCourseID()) {
					c.addCourseOffering(x);
				}
			}
		}
		
		System.out.println("Organized CourseOffering objects successfully by searching registration objects!");
		
	}
	
	/**
	 * This method gets the tblStudents table from the SQL database and and stores the data inside 
	 * Students ArrayList member variable as Student objects
	 */
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
	
	/**
	 * This method gets the tblCourses table from the SQL database and stores the data inside
	 * Course objects in the Courses ArrayList member variable
	 */
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
	
	/**
	 * This method gets the tblCourseOffering table from the SQL database and stores the data inside
	 * CourseOffering objects in the CourseOfferings ArrayList member variable
	 */
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
	
	/**
	 * This method gets the tblRegistration table from the SQL database and stores the data inside
	 * Registration objects in the Registrations ArrayList member variable
	 */
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
	
	/**
	 * This method creates an insert query for a student to register in a course 
	 * and stores it in the exitQueries buffer
	 * @param studentID ID of student
	 * @param courseOfferingID ID of courseOffering to be registered
	 */
	public void registerStudent(int studentID, int courseOfferingID) {
		int tempRegID = this.getRegistrations().get(this.getRegistrations().size() - 1).getRegistrationID();
		Registration r = new Registration(tempRegID+1);
		Student st = this.getStudentIDMap().get(studentID);
		if(st == null) {
			System.out.println("Error registering student!");
		}
		CourseOffering ct = this.getCourseOfferingMap().get(courseOfferingID);
		if(ct == null) {
			System.out.println("Error registering student - CourseOffering not found!");
		}
		
		r.completeRegistration(st, ct);
		
		this.registrations.add(r);
		this.registrationMap.put(tempRegID+1, r);
		
		String s = "";
		s += "INSERT INTO coursedb.tblregistration ";
		s += "(StudentID, CourseOfferingID) ";
		s += "values ('" +  Integer.toString(studentID) + "', '" + Integer.toString(courseOfferingID) + "')";

		
		this.getExitQueriesSQL().add(s);
		
		this.committToSQL(s);
		
		this.objectsFromSQL();
		
	}
	
	/**
	 * This method creates a delete query for a student to delete a course from his/her roster
	 * and stores the query in the exitQueries buffer
	 * @param registrationID
	 */
	public void deleteRegistration(int registrationID) {
		
		Registration r = this.getRegistrationMap().get(registrationID);
		this.getRegistrations().remove(r);
		this.getRegistrationMap().remove(registrationID, r);
		
		String s = "";
		s += "DELETE FROM coursedb.tblregistration ";
		s += "WHERE RegistrationID="+Integer.toString(registrationID);
//		System.out.println(s);
		this.getExitQueriesSQL().add(s);
		
		this.committToSQL(s);
		
		this.objectsFromSQL();
	}
	
	/**
	 * This method executes the queries in the exit queries buffer one at a time 
	 */
	public void committToSQL(String s) {
		System.out.println("commitToSQL called: " + ++counter + " times!");	
			try {
				Statement toExecute = this.getMyConnection().createStatement();
				toExecute.executeUpdate(s);
				System.out.println("Committed query \n"+s+"\n to database");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Update \n"+s+"\n to database failed!");
			}
		}
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

	/**
	 *
	 * @return hash table which maps student name -> student
	 */
	public Hashtable<String, Student> getStudentsMap() {
		return studentsMap;
	}

	/**
	 *
	 * @return hash table which maps course name -> course
	 */
	public Hashtable<String, Course> getCourseMap() {
		return courseMap;
	}


	/******************************* Testing Code - Not relevant to project *************************/
	public static void main(String[] args) {
		DatabaseConnector test = new DatabaseConnector();
	
//		System.out.println(test.registerStudent(10, 20));
	}
/****************************************************************************************************/

	/**
	 * @return the exitQueriesSQL
	 */
	public ArrayList<String> getExitQueriesSQL() {
		return exitQueriesSQL;
	}

	/**
	 * @param exitQueriesSQL the exitQueriesSQL to set
	 */
	public void setExitQueriesSQL(ArrayList<String> exitQueriesSQL) {
		this.exitQueriesSQL = exitQueriesSQL;
	}

	/**
	 * @return the studentIDMap
	 */
	public Hashtable<Integer, Student> getStudentIDMap() {
		return studentIDMap;
	}

	/**
	 * @param studentIDMap the studentIDMap to set
	 */
	public void setStudentIDMap(Hashtable<Integer, Student> studentIDMap) {
		this.studentIDMap = studentIDMap;
	}

	/**
	 * @return the courseOfferingMap
	 */
	public Hashtable<Integer, CourseOffering> getCourseOfferingMap() {
		return courseOfferingMap;
	}

	/**
	 * @param courseOfferingMap the courseOfferingMap to set
	 */
	public void setCourseOfferingMap(Hashtable<Integer, CourseOffering> courseOfferingMap) {
		this.courseOfferingMap = courseOfferingMap;
	}
	
	/**
	 * @return the registrationMap
	 */
	public Hashtable<Integer, Registration> getRegistrationMap() {
		return registrationMap;
	}

	/**
	 * @param registrationMap the registrationMap to set
	 */
	public void setRegistrationMap(Hashtable<Integer, Registration> registrationMap) {
		this.registrationMap = registrationMap;
	}

	/**
	 * @param studentsMap the studentsMap to set
	 */
	public void setStudentsMap(Hashtable<String, Student> studentsMap) {
		this.studentsMap = studentsMap;
	}

	/**
	 * @param courseMap the courseMap to set
	 */
	public void setCourseMap(Hashtable<String, Course> courseMap) {
		this.courseMap = courseMap;
	}
	
	static int counter = 0;
	
}
		
	
	



