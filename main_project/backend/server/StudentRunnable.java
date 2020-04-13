package backend.server;

import backend.models.*;
import java.io.BufferedReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.ObjectInputStream;

interface StudentQueries {
    public static String searchCourse = "1";

    public static String addCourseToStudent = "2";

    public static String removeCourseFromStudent = "3";

    public static String listCourses = "4";

    public static String allCoursesTakenByStudent = "5";

    public static String quit = "6";

    public static String messageDelimiter = "\t";

    public static String error = "ERR";

    public static String success = "SUCC";

    public static String failed = "FAIL";
}



public class StudentRunnable extends CustomRunnable implements  StudentQueries{

	private DatabaseManager m_db;
	private Student m_user;
	private boolean m_hasCommited = false;
    StudentRunnable(PrintWriter p, BufferedReader r, ObjectOutputStream objOut,
                     DatabaseManager db, Student user) {
        super(p, r, objOut);
        m_db = db;
        m_user = user;
    }

    @Override
    public void sendMenu() {
        String menu = 
         "Course Registratin System"  
        + "1. Search catalogue courses"
        + "2. Add course to student courses"
        + "3. Remove course from student courses"
        + "4. View All courses in catalogue"
        + "5. View All courses taken by student"
        + "6. Quit";
        sendString(menu);
    }
    

    public void searchCourse(ArrayList<String> message) {
        if (message.size() != 2) {
            System.out.println("StudentRunnable: searchCourse: invalid message of length: " + message.size());
            return;
        }

    	Course course = m_db.searchCourse(message.get(1));
    	
    	if (course == null) {
    		System.out.println("Course is null - entered course == null!");
    		sendResponse(false, null, "Unable to find course: " + message.get(1));
    		return;
        }

        sendResponse(true, course, null);
    }

    private void listCourses() {
    	System.out.println("StudentRunnable: sending Course List");

    	ArrayList<Course> list =  m_db.getAllCourses();

        if (list == null) {
            sendResponse(false, null, "No courses in catalogue");
        }

    	sendResponse(true, list, null);
    }

    private void allCoursesTakenByStudent(ArrayList<String> message) {
        System.out.println("StudentRunnable: sending Course List");

        ArrayList<Registration> list =  m_db.getCoursesByStudent(m_user.getStudentName());

        if (list == null) {
            sendResponse(false, null, "Not registered in any courses");
        }

        sendResponse(true, list, null);
    }

    private void addCourseToStudent(ArrayList<String> message) {
        if (message.size() != 2) {
            System.out.println("StudentRunnable: addCourseToStudent: invalid message of length: " + message.size());
            return;
        }

        String uid = message.get(1);
        Course course = m_db.searchCourse(uid);
        if (course == null) {
            sendResponse(false, null, "Course: " + uid + " does not exist in database");
            return;
        }

        if (m_db.addCourseToStudent(m_user.getStudentId(), course.getCourseID())) {
            sendResponse(true, null, null);
        }
        else {
            sendResponse(false, null, "Unable to register student into course: " + uid);
        }
    }

    private void removeCourseFromStudent(ArrayList<String> message) {
        if (message.size() != 2) {
            System.out.println("StudentRunnable: removeCourseToStudent: invalid message of length: " + message.size());
            return;
        }

        String uid = (message.get(1));
        Course course = m_db.searchCourse(uid);
        System.out.println("StudentRunnable::removeCourseFromStudent: received course: " + course.toString());
        if (course == null) {
            sendResponse(false, null, "Course: " + uid + " does not exist in database");
            return;
        }

        if (m_db.removeCourseFromStudent(m_user.getStudentId(), course.getCourseName())) {
            sendResponse(true, null, null);
        }
        else {
            sendResponse(false, null, "Unable to remove student from course: " + uid);
        }
    }

    public void handleInput(String in ) { return; }

    public void handleInput(ArrayList<String> message) {
    	if (message.size() <= 0) {
    		System.out.println("StudentRunanble: handleInput: received empty message as parameter");
    		return;
    	}

    	String in = message.get(0);
        if (in.equals( StudentQueries.searchCourse)) {
        	System.out.println("StudentRunnable: input == searchCourse");
        	searchCourse(message);
        }
        else if (in.equals( StudentQueries.addCourseToStudent)) {
            addCourseToStudent(message);
        }
        else if (in.equals( StudentQueries.removeCourseFromStudent)) {
            removeCourseFromStudent(message);
        }
        else if (in.equals(StudentQueries.listCourses)) {
        	listCourses();
        }
        else if (in.equals( StudentQueries.allCoursesTakenByStudent)) {
            allCoursesTakenByStudent(message);
        }
        else if (in.equals(StudentQueries.quit)) {
            shutdown();
        }
        else if (in.equals("")) {
        	System.out.println("Server::handleInput null input received. Terminating");
        	shutdown();
        }
        else {
            sendString("Unknown input: " + in);
        }
    }

    @Override
    protected void stop() {
        if (m_hasCommited == false) {
            m_hasCommited = true;
            m_db.commit();
        }
        super.stop();
    }

    public void run() {
        System.out.println("Student runnable started");
        try {
            start();
            while (isRunning()) {
                ArrayList<String> userInput = readMessage();
                System.out.println("StudentRunnable: " + userInput);

                handleInput(userInput);
            }

            shutdown();
        }
        catch (Exception e) {
            System.out.println("Exception in StudentRunnable::run");
            e.printStackTrace();
            shutdown();
        }
    }
}
