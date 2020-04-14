package backend.models;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * Represents a course
 * 
 * @author Devon Vipond, Robyn Scholz, Janam Marthak
 * 
 */
public class Course implements Serializable{

	private static final long serialVersionUID = 2L;

	private String courseName;
	private int courseID;
	private ArrayList<CourseOffering> courseOfferingArrayList;

	/**
	 * Constructs a course
	 * @param courseName course name
	 * @param courseID course unique identifier
	 */
	public Course(String courseName, int courseID) {
		this.setCourseName(courseName);
		this.setCourseID(courseID);
		this.courseOfferingArrayList = new ArrayList<CourseOffering>();
	}

	/**
	 * Returns a list of all offerings
	 * @return list of all offerings
	 */
	public ArrayList<CourseOffering> getCourseOfferingList() {
		return courseOfferingArrayList;
	}

	/**
	 * Adds offering to course
	 * @param offering to add
	 */
	public void addCourseOffering(CourseOffering offering) {
		this.courseOfferingArrayList.add(offering);
	}

	/**
	 * Returns name
	 * @return name of course
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Changes course name
	 * @param courseName new name
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 *
	 * @return returns course ID
	 */
	public int getCourseID() {
		return courseID;
	}

	/**
	 * Changes course id
	 * @param courseNum new id
	 */
	public void setCourseID(int courseNum) {
		this.courseID = courseNum;
	}

	/**
	 *
	 * @return String representation of object
	 */
	@Override
	public String toString () {
		String st = "";
		st += getCourseName() + " " + getCourseID ();
//		st += "\n-------\n";
		return st;
	}

}
