package frontend.Controller;

import frontend.GUI.StudentGUI;
import frontend.models.Course;
import frontend.models.CourseOffering;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

/**
 * This class controls the flow of information from the GUI to the communication with the server.
 *
 * @author Robyn Scholz, Janam Marthak, Devon Vipond
 * @version 1.0
 * @since 2020-04-09
 */

public class GUIController {

    private StudentGUI view;
    private ComController serverConnection;

    public GUIController(StudentGUI newView, ComController newServerConnection){
        view = newView;
        serverConnection = newServerConnection;
    }

    /**
     * Starts the user interaction by requesting the login information.
     */
    public void startAppLogin(){
        while(true){
            String loginInfo = view.getUserLogin();
            if(serverConnection.communicateLogin(loginInfo)){
                break;
            }
            view.showMessageWindow("Login Failed");
        }
        registerActionListeners();
    }

    /**
     * Assigns the response to each button on the GUI.
     */
    public void registerActionListeners(){
        view.getButtonSearchCourses().addActionListener((ActionEvent e) -> {searchCourses();});
        view.getButtonAddStudentToCourse().addActionListener((ActionEvent e) -> {addCourse();});
        view.getButtonRemoveStudentFromCourse().addActionListener((ActionEvent e) -> {dropCourse();});
        view.getButtonViewAllCourses().addActionListener((ActionEvent e) -> {viewAllCourses();});
        view.getButtonViewAllStudentCourse().addActionListener((ActionEvent e) -> {viewAllStudentCourses();});
    }

    /**
     * Controls flow for searching by course name. Displays result to GUI.
     */
    public void searchCourses(){
        String courseName = view.getCourseNameForSearch();
        Course c = (Course) serverConnection.communicateSearchCourse(courseName);
        if(c == null){
            view.showMessageWindow("There were no results for the search");
        }
        else{
            displaySearchResult(c);
        }
    }

    /**
     * Displays course information.
     * @param course the course object to display
     */
    public void displaySearchResult(Course course){
        view.displayResponseText("The results of the search are:");
        for(CourseOffering co : course.getCourseOfferingList()){
            view.displayResponseText(co.toString());
        }
    }

    /**
     * Controls the flow to add a student to a course.
     */
    public void addCourse(){
        String courseName = view.getCourseNameToAdd();
        Boolean result = serverConnection.communicateAddCourse(courseName);
        if(result){
            view.showMessageWindow("Successfully added you to the course");
        }
        else {
            view.showMessageWindow("Error adding you to the course");
        }
    }

    /**
     * Controls the flow to remove a student to a course.
     */
    public void dropCourse(){
        String courseName = view.getCourseNameToDrop();
        Boolean result = serverConnection.communicateDropCourse(courseName);
        if(result){
            view.showMessageWindow("Successfully dropped you from the course");
        }
        else {
            view.showMessageWindow("Error dropping you from the course");
        }
    }

    /**
     * Requests and displays all courses to the GUI.
     */
    public void viewAllCourses(){
        ArrayList<Course> courses = (ArrayList<Course>) serverConnection.communicateViewAllCourse();  // TODO figure out arraylist
        if(courses == null){
            view.showMessageWindow("Error getting information from server");
        }
        else{
            view.showMessageWindow("The list of courses available is");
            displayCourses(courses);
        }
    }

    /**
     * Requests and displays all student courses to the GUI.
     */
    public void viewAllStudentCourses(){
        ArrayList<Course> courses = (ArrayList<Course>) serverConnection.communicateListStudentCourses();  // TODO figure out arraylist
        if(courses == null){
            view.showMessageWindow("Error getting information from server");
        }
        else{
            view.showMessageWindow("The courses that the student is taking are");
            displayCourses(courses);
        }
    }

    /**
     * Display the content of the list of courses.
     * @param courses arraylist of courses to be displayed
     */
    public void displayCourses(ArrayList<Course> courses){
        for(Course course : courses){
            for(CourseOffering co : course.getCourseOfferingList()){
                view.showMessageWindow(co.toString());
            }
        }
    }

    public static void main(String[] args){
        StudentGUI view= new StudentGUI("Main App");
        ComController comms = new ComController("localhost", 8040);
        GUIController studentApp = new GUIController(view, comms);
        studentApp.startAppLogin();
    }
}
