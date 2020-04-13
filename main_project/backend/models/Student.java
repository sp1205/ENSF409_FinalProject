package backend.models;
import java.io.Serializable;

/** Fixing toString function required **/

/**
 * Represents a student
 */
public class Student implements Serializable{
	private static final long serialVersionUID = 999909L;
	
	private String studentName;
	private int studentId;

	/**
	 * Constructor
	 * @param studentName name of student
	 * @param studentId unique id of student
	 */
	public Student (String studentName, int studentId) {
		this.setStudentName(studentName);
		this.setStudentId(studentId);
	}

	/**
	 *
	 * @return name of student
	 */
	public String getStudentName() {
		return studentName;
	}

	/**
	 *
	 * @param studentName students new name
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	/**
	 *
	 * @return id of student
	 */
	public int getStudentId() {
		return studentId;
	}

	/**
	 *
	 * @param studentId new id of student
	 */
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	/**
	 *
	 * @return String representation of object
	 */
	@Override
	public String toString () {
		String st = "Student Name: " + getStudentName() + "\n" +
				"Student Id: " + getStudentId() + "\n";
		return st;
	}

}
