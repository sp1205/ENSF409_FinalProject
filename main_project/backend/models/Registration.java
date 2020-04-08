package backend.models;
import java.util.ArrayList;

public class Registration {
	private Student theStudent;
	private CourseOffering theOffering;
	private int registrationId;
	private final int MAX_COURSES_PER_STUDENT = 6;
	private char grade;

	public Registration(int registrationId) {
		this.registrationId = registrationId;
	}

	Boolean completeRegistration (Student st, CourseOffering of) {
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
	
	private void addRegistration () {
		//theStudent.addRegistration(this);
		theOffering.addRegistration(this);
	}
	
	
	public Student getTheStudent() {
		return theStudent;
	}
	public void setTheStudent(Student theStudent) {
		this.theStudent = theStudent;
	}
	public CourseOffering getTheOffering() {
		return theOffering;
	}
	public void setTheOffering(CourseOffering theOffering) {
		this.theOffering = theOffering;
	}
	public char getGrade() {
		return grade;
	}
	public void setGrade(char grade) {
		this.grade = grade;
	}
	
	@Override
	public String toString () {
		String st = "\n";
		st += "Student Name: " + getTheStudent() + "\n";
		st += "The Offering: " + getTheOffering () + "\n";
		st += "Grade: " + getGrade();
		st += "\n-----------\n";
		return st;
		
	}
	

}
