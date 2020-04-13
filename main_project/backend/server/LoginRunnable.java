package backend.server;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import backend.models.Student;

import java.io.ObjectInputStream;

interface LoginQueries {
    public static String studentLogin = "1";
    public static String quit = "2";
}

/**
 * Handles logins.
 */
public class LoginRunnable extends CustomRunnable implements LoginQueries{
    private DatabaseManager m_db;

    /**
     * Creates a runnable with IO objects
     * @param p assigned to internal member
     * @param r assigned to internal member
     * @param objOut assigned to internal member
     * @param db assigned to internal member
     */
    LoginRunnable(PrintWriter p, BufferedReader r, ObjectOutputStream objOut,  DatabaseManager db) {
        super(p, r, objOut );
        m_db = db;
    }

    /**
     * Sends list of options to client.
     */
    @Override
    public void sendMenu() {
        String menu = 
         "/n/nLogin Menu"  
        + LoginQueries.studentLogin + ". Student Login"
        + LoginQueries.quit + ". Quit";
        sendString(menu);
    }

    /**
     * Parses login message from client
     * @param message message from client
     */
    public void loginStudent(ArrayList<String> message) {
        Student student = m_db.login(message.get(0));
        if (student == null) {
            sendResponse(false, null, "Invalid Login Credentials");
            return;
        }

        sendResponse(true, null, null);

        Runnable studentRunnable = new StudentRunnable(m_sendString, m_readString, m_sendObject,
                 m_db, student);

        studentRunnable.run();
        shutdown();
    }

    public void loginAdmin() {
        System.out.println("Not Implemented Yet!");
    }

    /**
     * Parses input from user
     * @param in input
     */
    @Override
    public void handleInput(String in){return;}

    /**
     * Parses input from user
     * @param in input
     */
    @Override
    public void handleInput(ArrayList<String> in) {
        if (in.size() <= 0) {
            sendResponse(false, null, "Invalid Message");
            return;
        }
        if (in.get(0).equals(StudentQueries.quit)){
            shutdown();
            return;
        }

        loginStudent(in);
    }

    /**
     * Main execution loop. Handles reading/writing from client.
     */
    @Override
    public void run() {
        System.out.println("Login runnable started");
        try {
            start();
            while (isRunning()) {
                ArrayList<String> userInput = readMessage();

                handleInput(userInput);
            }

            System.out.println("LoginRunnable: Shutting down Login Thread");
            shutdown();
        }
        catch (Exception e) {
            System.out.println("Exception in LoginRunnable::run");
            e.printStackTrace();
            shutdown();
        }
        shutdown();
    }
}
