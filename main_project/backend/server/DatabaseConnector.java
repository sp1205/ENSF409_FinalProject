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

	private ArrayList<String> exitQueriesSQL;
	
	private Hashtable<Integer, Registration> registrationMap;
	private Hashtable<Integer, CourseOffering> courseOfferingMap;
	private Hashtable<Integer, Student> studentIDMap;
	private Hashtable<String, Student> studentsMap;
	private Hashtable<String, Course> courseMap;


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
		this.setExitQueriesSQL(new ArrayList<String>());
		

		this.studentsMap = new Hashtable<String, Student>();
		this.courseMap = new Hashtable<String, Course>();
		this.studentIDMap = new Hashtable<Integer, Student>();
		this.registrationMap = new Hashtable<Integer, Registration>();
		
		for (Course c : this.courses) {
			this.courseMap.put(c.getCourseName(), c);
		}
		for (Student c : this.students) {
			this.studentsMap.put(c.getStudentName(), c);
			this.studentIDMap.put(c.getStudentId(), c);
		}
		for(CourseOffering c : this.courseOfferings) {
			this.courseOfferingMap.put(c.getCourseOfferingID(), c);
			Course course = this.courseMap.get(c.getTheCourse());

			if (course == null) {
				System.out.println("Unable to add courseOffering: " + c.toString() + " to course");
				continue;
			}

			course.addCourseOffering(c);
		}
		for(Registration c : this.registrations) {
			this.registrationMap.put(c.getRegistrationID(), c);
		}
		
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
		
		
		String s = "";
		s += "INSERT INTO coursedb.tblregistration ";
		s += "(StudentID, CourseOfferingID) ";
		s += "values ('" +  Integer.toString(studentID) + "', '" + Integer.toString(courseOfferingID) + "')";

		
		this.getExitQueriesSQL().add(s);
		
//		try {
//			Statement toExecute = this.getMyConnection().createStatement();
//			toExecute.executeUpdate(s);
//			System.out.println("Registered student "+studentID+" for courseOffering:"+courseOfferingID);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("Registration not successful");
//		}
//		
		
//		return s;
	}
	
	public void deleteRegistration(int registrationID) {
		
		Registration r = this.getRegistrationMap().get(registrationID);
		this.getRegistrations().remove(r);
		this.getRegistrationMap().remove(registrationID, r);
		
		String s = "";
		s += "DELETE FROM coursedb.tblregistration ";
		s += "WHERE RegistrationID="+Integer.toString(registrationID);
		
		this.getExitQueriesSQL().add(s);
		
//		try {
//			Statement toExecute = this.getMyConnection().createStatement();
//			int rowsDeleted = toExecute.executeUpdate(s);
//			System.out.println("Deleted "+rowsDeleted+" rows from tblRegistration");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("Removal not successful");
//		}
//		
		
//		return s;	
	}
	
	public void committToSQL() {
		int x = 0;
		for(String c : this.getExitQueriesSQL()) {
			x++;
			try {
				Statement toExecute = this.getMyConnection().createStatement();
				toExecute.executeUpdate(c);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Update "+x+" to database failed!");
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


	/******************************* Testing Code *******************************************************/
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
	
}
		
	
	



