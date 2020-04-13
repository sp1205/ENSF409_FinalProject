package frontend.Controller;

import java.io.*;
import java.net.Socket;

/**
 * This class manages the communication with server regarding student records.
 *
 * @author Robyn Scholz, Janam Marthak, Devon Vipond
 * @version 1.0
 * @since 2020-04-09
 */
public class ComController{
    private PrintWriter socketOut;
    private Socket studentSysSocket;
    private BufferedReader socketIn;

    private ObjectInputStream m_readObject;
    MessageBuilder mb;

    /**
     * Constructs the ComController object.
     * @param serverName The hostname of the server.
     * @param portNumber The port number to connect to on the server
     */
    public ComController(String serverName, int portNumber) {
        try {
            studentSysSocket = new Socket(serverName, portNumber);
            m_readObject = new ObjectInputStream(studentSysSocket.getInputStream());
            socketIn = new BufferedReader(new InputStreamReader(studentSysSocket.getInputStream()));
            socketOut = new PrintWriter((studentSysSocket.getOutputStream()), true);
            mb = new MessageBuilder();
        } catch (IOException e) {
            System.err.println(e.getStackTrace());
        }
    }

    /**
     * Writes to socket.
     * @param str the message to send to the server
     */
    public void sendString(String str) {
        socketOut.flush();
        socketOut.println(str);
    }

    /**
     * Sends a message to the server and checks for a valid response.
     * @param msg the message to send to the server
     * @return true, if server responded with success message, false otherwise
     */
    public Boolean communicate(String msg) {
        String response = "";
        try {
            sendString(msg);
            response = socketIn.readLine();
            if(response.equals(mb.successMessage())){
                return true;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Gets the serialized objects sent by the Server
     * @return the Objects
     */
    public Object getObjects(){
        try {
            return m_readObject.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Closes the required sockets to terminate the connection to the Server.
     */
    public void terminateConnection(){
        try {
            socketIn.close();
            socketOut.close();
        } catch (IOException e) {
            System.out.println("Closing error: " + e.getMessage());
        }
    }

    /**
     * Creates and sends login message to server
     * @param studentName the name of the student
     * @return result of communicate (true if success message received)
     */
    public Boolean communicateLogin(String studentName){
        return communicate(mb.loginMessage(studentName));
    }

    /**
     * Creates and sends add course message to server
     * @param courseName the name of the student
     * @return result of communicate (true if success message received)
     */
    public Boolean communicateAddCourse(String courseName){
        return communicate(mb.addCourseToStudentMessage(courseName));
    }

    /**
     * Creates and sends drop course message to server
     * @param courseName the name of the course
     * @return result of communicate (true if success message received)
     */
    public Boolean communicateDropCourse(String courseName){
        return communicate(mb.removeCourseFromStudentMessage(courseName));
    }

    /**
     * Creates and sends search course message to server
     * @param courseName the name of the course
     * @return the serialized object sent be the server, or null
     */
    public Object communicateSearchCourse(String courseName){
        if(communicate(mb.searchCourseMessage(courseName))){
            return getObjects();
        }
        return null;
    }

    /**
     * Creates and sends view all courses message to server
     * @return the serialized object sent be the server, or null
     */
    public Object communicateViewAllCourse(){
        if(communicate(mb.listCoursesMessage())){
            return getObjects();
        }
        return null;
    }

    /**
     * Creates and sends view all student courses message to server
     * @return the serialized object sent be the server, or null
     */
    public Object communicateListStudentCourses(){
        if(communicate(mb.allCoursesTakenByStudentMessage())){
            return getObjects();
        }
        return null;
    }
}
