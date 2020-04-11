package models;
import java.io.Serializable;
import java.util.ArrayList;

/** Fixing toString function required **/

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
	
	public CourseOffering (int courseOfferingID, int courseOfferingNum, Course theCourse) {
		this.setCourseOfferingID(courseOfferingID);
		this.setCourseOfferingNum(courseOfferingNum);
		this.setSecCap(10);
		this.setTheCourse(theCourse);
		offeringRegList = new ArrayList <Registration>();
	}


	public boolean isActive() {
		return offeringRegList.size() >= 6;
	}
	public Course getTheCourse() {
		return theCourse;
	}
	public void setTheCourse(Course theCourse) {
		this.theCourse = theCourse;
	}
	@Override
	public String toString () {
		String st = "\n";
		st += getTheCourse().getCourseName() + " " + getTheCourse().getCourseID() + "\n";
		st += "Section Num: " + getCourseOfferingNum() + ", section cap: "+ getSecCap() +"\n";
		//We also want to print the names of all students in the section
		return st;
	}
	public void addRegistration(Registration registration) {
		// TODO Auto-generated method stub
		offeringRegList.add(registration);
	}
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
