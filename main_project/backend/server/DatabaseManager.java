package backend.server;
// TODO: UNCOMMENT!!
//package Database;

import backend.models.*;
//import Database.*;

import java.util.ArrayList;


/**
 * Wrapper around DatabaseConnector. Used by the server to execute database queries.
 * 
 * @author Devon Vipond, Robyn Scholz, Janam Marthak
 * 
 */
public class DatabaseManager {
    /**
     * manages this object
     */
    DatabaseConnector m_dbConnector;

    /**
     * Constructor
     */
    DatabaseManager() {
        m_dbConnector = new DatabaseConnector();
    //    m_dbConnector.objectsFromSQL();
    }

    /**
     * Searches database for course
     * @param courseName course to serach for
     * @return returns course if found, otherwise null.
     */
    public Course searchCourse(String courseName) {
    	System.out.println("DatabaseManager::searchCourse");

    	Course c = m_dbConnector.getCourseMap().get(courseName);

        if (c == null) {
            System.out.println("DatabaseManager::searchCourse unable to find course " + courseName);
            return null;
        }

    	return c;
    }

    /**
     * Adds course to students list of courses
     * @param studentId student in question
     * @param courseId course to add
     * @return true if successful
     */
    public boolean addCourseToStudent(int studentId, int courseId) {
        CourseOffering offering = null;
        for (CourseOffering c : m_dbConnector.getCourseOfferings()) {
            if (c.getTheCourse().getCourseID() == courseId) {
                offering = c;
                break;
            }
        }

        if (offering == null) {
            return false;
        }

        m_dbConnector.registerStudent(studentId, offering.getCourseOfferingID() );

        return true;
    }

    /**
     * Removes course from students list of courses
     * @param studentId id of student
     * @param courseName name of course
     * @return true if successful.
     */
    public boolean removeCourseFromStudent(int studentId, String courseName) {
        Registration found = null;
        for (Registration r : m_dbConnector.getRegistrations()) {
            if (r.getTheStudent().getStudentId() == studentId &&
                r.getTheOffering().getTheCourse().getCourseName().equals(courseName) ) {
                found = r;
                break;
            }
        }

        if (found == null) {
            System.out.println("Registration not found! Unable to delete student from course");
        	return false;
        }
        
        System.out.println("Deleting registration r with ID: "+ found.getRegistrationID());
        System.out.println(found);
        m_dbConnector.deleteRegistration(found.getRegistrationID());
        return true;
    }

    /**
     * returns list of registrations by student
     * @param studentName students name
     * @return list of registrations.
     */
    public ArrayList<Registration> getCoursesByStudent(String studentName) {
        ArrayList<Registration> studentRegList = new ArrayList<Registration>();

        for (Registration c : m_dbConnector.getRegistrations()) {
            if (c.getTheStudent().getStudentName().equals(studentName)) {
                studentRegList.add(c);
            }
        }

        return studentRegList;
    }

    /**
     * Returns all courrses taken by student
     * @return all courses taken by student
     */
    public ArrayList<Course> getAllCourses() {
        return m_dbConnector.getCourses();
    }

    /**
     * Handles logging in a student
     * @param studentName login info
     * @return true if logging successful
     */
    public Student login(String studentName) {
        for (Student s : m_dbConnector.getStudents()) {
            if (s.getStudentName().equals(studentName)) {
                return s;
            }
        }

        return null;
    }

    /**
     * Updates database
     */
    public void commit() {
   //     m_dbConnector.committToSQL();
    }
}
