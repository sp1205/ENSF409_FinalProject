package Server;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import Server.StudentRunnable;

import java.io.ObjectInputStream;

interface LoginQueries {
    public static String studentLogin = "1";
    public static String adminLogin = "2";
    public static String quit = "3";
}
public class LoginRunnable extends CustomRunnable implements LoginQueries{

    LoginRunnable(PrintWriter p, BufferedReader r, ObjectOutputStream objOut, ObjectInputStream objIn) {
        super(p, r, objOut, objIn);
    }

    @Override
    public void sendMenu() {
        String menu = 
         "/n/nLogin Menu"  
        + LoginQueries.studentLogin + ". Student Login"
        + LoginQueries.adminLogin + ". Admin Login (Coming Soon!)"
        + LoginQueries.quit + ". Quit";
        sendString(menu);
    }

    public void loginStudent() {
        StudentRunnable studentRunnable = new StudentRunnable(m_sendString,
        m_readString, m_sendObject, m_readObject, null);

        studentRunnable.start();
        stop();
    }

    public void loginAdmin() {
        System.out.println("Not Implemented Yet!");
    }

    @Override
    public void handleInput(String in) {
        if (in == LoginQueries.studentLogin) {
            loginStudent();
        }
        else if (in == LoginQueries.adminLogin) {
            loginAdmin();
        }
        else if (in == LoginQueries.quit){
            stop();
        }
        else {
            sendString("Unknown input: " + in);
        }
    }

    @Override
    public void handleInput(ArrayList<String> in) { return; }

    @Override
    public void run() {
        try {
            start();
            sendMenu();
            while (isRunning()) {
                String userInput = readString();
                System.out.println("LoginRunnable: " + userInput);

                handleInput(userInput);
            }

            System.out.println("LoginRunnable: Shutting down Login Thread");
            shutdown();
        }
        catch (Exception e) {
            System.out.println("Exception in LoginRunnable::run");
            e.printStackTrace();
            stop();
            shutdown();
        }
    }
}
