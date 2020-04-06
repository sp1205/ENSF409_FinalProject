package Server;
// TODO: UNCOMMENT!!
//package Database;

import Models.*;
//import Database.*;

import java.util.ArrayList;

public class DatabaseManager {
	//private CourseTable m_courseTable;

    DatabaseManager() {

    }

    // not required for milestone 2
    //public User searchUser(User user) {
    //    return null;
    //}

    public Course searchCourse(int uid) {
    	System.out.println("DatabaseManager::searchCourse");
    	return new Course("ENCM", uid);
    }

    public void addStudentCourse() {

    }

    public void removeStudentCourse() {

    }

    public void getCoursesByStudent() {

    }

    public ArrayList<Course> getAllCourses() {
    	System.out.println("DatabaseManager::getAllCourses");
    	ArrayList<Course> list = new ArrayList<Course>();
    	list.add(new Course("ENGG", 233));
    	list.add(new Course("ENGG", 209));
    	list.add(new Course("ENEL", 439));
    	list.add(new Course("ENCM", 211));
    	list.add(new Course("ENOG", 825));
    	
    	return list;

    }





}
