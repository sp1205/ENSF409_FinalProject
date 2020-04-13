package backend.models;

import java.io.Serializable;

/** Fixing toString function required **/

/**
 * Representation of a student->course registration
 */
public class Registration implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Student theStudent;
	private CourseOffering theOffering;
	private int registrationID;
	private final int MAX_COURSES_PER_STUDENT = 6;
	private char grade;

	/**
	 * Constructor
	 * @param registrationID unique id of registration
	 */
	public Registration(int registrationID) {
		this.setRegistrationID(registrationID);
		this.setGrade('U');
	}

	/**
	 * Registers student into offering
	 * @param st student
	 * @param of offering
	 * @return true if successful.
	 */
	public boolean completeRegistration (Student st, CourseOffering of) {
		// DVIPOND TODO: figure out a way to implement max courses
		//if (st.numCourses() >= MAX_COURSES_PER_STUDENT) {
		//	System.out.println(st.getStudentName() + " is unable to reister" +
		//			"for more courses due to max course capacity being reached");
		//	return false;
		//}

		theStudent = st;
		theOffering = of;
		addRegistration ();
		return true;
	}

	/**
	 * adds registration to offering
	 */
	private void addRegistration () {
		//theStudent.addRegistration(this);
		theOffering.addRegistration(this);
	}


	/**
	 *
	 * @return registered student
	 */
	public Student getTheStudent() {
		return theStudent;
	}

	/**
	 *
	 * @param theStudent student to register
	 */
	public void setTheStudent(Student theStudent) {
		this.theStudent = theStudent;
	}

	/**
	 *
	 * @return the offering
	 */
	public CourseOffering getTheOffering() {
		return theOffering;
	}

	/**
	 *
	 * @param theOffering the offering in the registration
	 */
	public void setTheOffering(CourseOffering theOffering) {
		this.theOffering = theOffering;
	}

	/**
	 *
	 * @return the students grade
	 */
	public char getGrade() {
		return grade;
	}

	/**
	 * Changes the students grade.
	 * @param grade new grade
	 */
	public void setGrade(char grade) {
		this.grade = grade;
	}

	/**
	 * String representatin of object
	 * @return String repr of object
	 */
	@Override
	public String toString () {
		String st = "";
		st += "Student Name: " + getTheStudent() + "\n";
		st += "The Offering: " + getTheOffering () + "\n";
		st += "Grade: " + getGrade();
	//	st += "\n-----------\n";
		return st;
		
	}

	/**
	 * @return the registrationID
	 */
	public int getRegistrationID() {
		return registrationID;
	}

	/**
	 * @param registrationID the registrationID to set
	 */
	public void setRegistrationID(int registrationID) {
		this.registrationID = registrationID;
	}
	

}
