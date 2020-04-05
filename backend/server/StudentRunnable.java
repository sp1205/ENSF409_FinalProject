package Server;

import java.io.BufferedReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.ObjectInputStream;

interface StudentQueries {
    public static String listCourses = "1";
    public static String listStudents = "2";
    public static String quit = "3";
}
public class StudentRunnable extends CustomRunnable implements  StudentQueries{

    StudentRunnable(PrintWriter p, BufferedReader r, ObjectOutputStream objOut, ObjectInputStream objIn) {
        super(p, r, objOut, objIn);
    }

    @Override
    public void sendMenu() {
        String menu = 
         "/n/nCourse Registratin System"  
        + "1. Search catalogue courses"
        + "2. Add course to student courses"
        + "3. Remove course from student courses"
        + "4. View All courses in catalogue"
        + "5. View all courses taken by student"
        + "6. Quit";
        sendString(menu);
    }

    public void listCourses() {
        // not sure what else to do here
    }

    public void listStudents() {
        // not sure what else to do here
    }

    public void handleInput(String in) {
        if (in == StudentQueries.listCourses) {
            listCourses();
        }
        else if (in == StudentQueries.listStudents) {
            listStudents();
        }
        else if (in == StudentQueries.quit){
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
                String userInput = readString();
                System.out.println("StudentRunnable: " + userInput);

                handleInput(userInput);
            }
        }
        catch (Exception e) {
            System.out.println("Exception in StudentRunnable::run");
            e.printStackTrace();
            stop();
        }
    }
}
