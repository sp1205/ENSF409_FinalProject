package Server;


import Models.*;

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
}



public class StudentRunnable extends CustomRunnable implements  StudentQueries{

	private DatabaseManager m_db;
    StudentRunnable(PrintWriter p, BufferedReader r, ObjectOutputStream objOut, ObjectInputStream objIn, DatabaseManager db) {
        super(p, r, objOut, objIn);
        m_db = db;
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
    

    public void searchCourse() {
    	sendString("Enter Course Id");
    	String id = readString();
    	int uid = Integer.parseInt(id);
    	
    	Course course = m_db.searchCourse(uid);


    	sendObject(course);
    }

    public void listCourses() {
    	System.out.println("StudentRunnable: sending Course List");

    	ArrayList<Course> list =  m_db.getAllCourses();

		sendObject(list);
    }

    public ArrayList<String> readMessage() {
    	ArrayList<String> message = new ArrayList<String>();
		String read = "";

		while ((read += readString()) != null) {
			int idx = read.indexOf(StudentQueries.messageDelimiter);
			if (idx < 0) {
				continue;
			}
			else {
				String[] parts = read.split(StudentQueries.messageDelimiter);
				read = parts[parts.length-1];
				
				for (int i = 0; i < parts.length-2; i++) {
					message.add(parts[i]);
				}
			}
		}
		
		return message;
    	
    }

    public void handleInput(String in ) {return; }
    public void handleInput(ArrayList<String> message) {
    	if (message.size() <= 0) {
    		System.out.println("StudentRunanble: handleInput: received empty message as parm");
    		return;
    	}

    	String in = message.get(0);
        if (in.equals( StudentQueries.searchCourse)) {
        	System.out.println("StudentRunnable: input == searchCourse");
        	searchCourse();
        }
        else if (in.equals( StudentQueries.addCourseToStudent)) {
        	return;
        }
        else if (in.equals( StudentQueries.removeCourseFromStudent)) {
        	return;
        }
        else if (in.equals(StudentQueries.listCourses)) {
        	listCourses();
        }
        else if (in.equals( StudentQueries.allCoursesTakenByStudent)) {
        	return;
        }
        else if (in.equals( StudentQueries.quit)) {
            stop();
        }
        else if (in.equals("")) {
        	System.out.println("Server::handleInput null input received. Terminating");
            stop();
        }
        else {
            sendString("Unknown input: " + in);
        }
    }

    public void run() {
        try {
            start();
            sendMenu();
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
            stop();
            shutdown();
        }
    }
}
