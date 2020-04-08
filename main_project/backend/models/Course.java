package backend.models;
import java.io.Serializable;

/** Fixing toString function required **/

public class Course implements Serializable{

	private static final long serialVersionUID = 2L;

	private String courseName;
	private int courseID;

	public Course(String courseName, int courseID) {
		this.setCourseName(courseName);
		this.setCourseID(courseID);
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseNum) {
		this.courseID = courseNum;
	}
	@Override
	public String toString () {
		String st = "\n";
		st += getCourseName() + " " + getCourseID ();
		st += "\n-------\n";
		return st;
	}

}
