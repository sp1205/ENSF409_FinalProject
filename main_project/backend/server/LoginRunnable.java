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
public class LoginRunnable extends CustomRunnable implements LoginQueries{
    private DatabaseManager m_db;

    LoginRunnable(PrintWriter p, BufferedReader r, ObjectOutputStream objOut,  DatabaseManager db) {
        super(p, r, objOut );
        m_db = db;
    }

    @Override
    public void sendMenu() {
        String menu = 
         "/n/nLogin Menu"  
        + LoginQueries.studentLogin + ". Student Login"
        + LoginQueries.quit + ". Quit";
        sendString(menu);
    }

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
        stop();
    }

    public void loginAdmin() {
        System.out.println("Not Implemented Yet!");
    }

    @Override
    public void handleInput(String in){return;}

    @Override
    public void handleInput(ArrayList<String> in) {
        if (in.size() <= 0) {
            sendResponse(false, null, "Invalid Message");
            return;
        }
        if (in.get(0).equals(StudentQueries.quit)){
            stop();
            return;
        }

        loginStudent(in);
    }

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
            stop();
        }
        shutdown();
    }
}
