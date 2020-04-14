package backend.models;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * Represents a course offering
 * 
 * @author Devon Vipond, Robyn Scholz, Janam Marthak
 * 
 */
public class CourseOffering implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int courseOfferingID;
	private Course theCourse;
	private int courseOfferingNum;
	private int secCap;
	private ArrayList <Registration> offeringRegList;

	/**
	 * Constructs a course offering
	 * @param courseOfferingID id of offering
	 * @param courseOfferingNum the section number
	 * @param theCourse course being offered
	 */
	public CourseOffering (int courseOfferingID, int courseOfferingNum, Course theCourse) {
		this.setCourseOfferingID(courseOfferingID);
		this.setCourseOfferingNum(courseOfferingNum);
		this.setSecCap(10);
		this.setTheCourse(theCourse);
		offeringRegList = new ArrayList <Registration>();
	}


	/**
	 *
	 * @return true if course is active
	 */
	public boolean isActive() {
		return offeringRegList.size() >= 6;
	}

	/**
	 *
	 * @return course being offered
	 */
	public Course getTheCourse() {
		return theCourse;
	}

	/**
	 * Changes offered course
	 * @param theCourse the course being offered
	 */
	public void setTheCourse(Course theCourse) {
		this.theCourse = theCourse;
	}

	/**
	 *
	 * @return String representation of object
	 */
	@Override
	public String toString () {
		String st = "";
		st += getTheCourse().getCourseName() + "\n";
		st += "Section Num: " + getCourseOfferingNum() + ", section cap: "+ getSecCap();// +"\n";
		//We also want to print the names of all students in the section
		return st;
	}

	/**
	 * Changes registration
	 * @param registration new registration
	 */
	public void addRegistration(Registration registration) {
		// TODO Auto-generated method stub
		offeringRegList.add(registration);
	}

	/**
	 * Removes registration from offering
	 * @param reg registration to remove
	 */
	public void removeRegistration(Registration reg ) {
	    // DVIPOND TODO: remove course from student!
	    offeringRegList.remove(reg);
	}


	/**
	 * @return the courseOfferingNum
	 */
	public int getCourseOfferingNum() {
		return courseOfferingNum;
	}


	/**
	 * @param courseOfferingNum the courseOfferingNum to set
	 */
	public void setCourseOfferingNum(int courseOfferingNum) {
		this.courseOfferingNum = courseOfferingNum;
	}


	/**
	 * @return the secCap
	 */
	public int getSecCap() {
		return secCap;
	}


	/**
	 * @param secCap the secCap to set
	 */
	public void setSecCap(int secCap) {
		this.secCap = secCap;
	}


	/**
	 * @return the courseOfferingID
	 */
	public int getCourseOfferingID() {
		return courseOfferingID;
	}


	/**
	 * @param courseOfferingID the courseOfferingID to set
	 */
	public void setCourseOfferingID(int courseOfferingID) {
		this.courseOfferingID = courseOfferingID;
	}
	
	

}
