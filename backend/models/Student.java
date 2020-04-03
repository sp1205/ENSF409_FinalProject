import java.util.ArrayList;

public class Student {
	
	private String studentName;
	private int studentId;
	//private ArrayList<CourseOffering> offeringList;
	private ArrayList<Registration> studentRegList;
	
	public Student (String studentName, int studentId) {
		this.setStudentName(studentName);
		this.setStudentId(studentId);
		studentRegList = new ArrayList<Registration>();
	}

	public int numCourses() {
		return this.studentRegList.size();
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	@Override
	public String toString () {
		String st = "Student Name: " + getStudentName() + "\n" +
				"Student Id: " + getStudentId() + "\n\n";
		return st;
	}

	public void addRegistration(Registration registration) {
		// TODO Auto-generated method stub
		studentRegList.add(registration);
	}
	public void removeRegistration(Registration reg) {
		studentRegList.remove(reg);
	}

}
