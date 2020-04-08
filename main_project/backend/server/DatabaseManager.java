package backend.server;
// TODO: UNCOMMENT!!
//package Database;

import backend.models.*;
//import Database.*;

import java.util.ArrayList;

public class DatabaseManager {
    DatabaseConnector m_dbConnector;
    DatabaseManager() {
        m_dbConnector = new DatabaseConnector();
        m_dbConnector.objectsFromSQL();
    }

    public Course searchCourse(String courseName) {
    	System.out.println("DatabaseManager::searchCourse");

    	Course c = m_dbConnector.getCourseMap().get(courseName);

        if (c == null) {
            System.out.println("DatabaseManager::searchCourse unable to find course " + courseName);
            return null;
        }

    	return c;
    }

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
        m_dbConnector.objectsFromSQL();

        return true;
    }

    public boolean removeCourseFromStudent(int studentId, int courseId) {
        Registration found = null;
        for (Registration r : m_dbConnector.getRegistrations()) {
            if (r.getTheStudent().getStudentId() == studentId &&
                r.getTheOffering().getTheCourse().getCourseID() == courseId) {
                found = r;
                break;
            }
        }

        if (found == null) {
            return false;
        }

        m_dbConnector.deleteRegistration(found.getRegistrationID());
        m_dbConnector.objectsFromSQL();
        return true;
    }

    public ArrayList<Registration> getCoursesByStudent(String studentName) {
        ArrayList<Registration> studentRegList = new ArrayList<Registration>();

        for (Registration c : m_dbConnector.getRegistrations()) {
            if (c.getTheStudent().getStudentName().equals(studentName)) {
                studentRegList.add(c);
            }
        }

        return studentRegList;
    }

    public ArrayList<Course> getAllCourses() {
        return m_dbConnector.getCourses();
    }

    public Student login(String studentName) {
        for (Student s : m_dbConnector.getStudents()) {
            if (s.getStudentName().equals(studentName)) {
                return s;
            }
        }

        return null;
    }
}
