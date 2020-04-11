package frontend.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * This class is the JFrame GUI that the user interacts with to query information about the student records.
 *
 * @author Robyn Scholz, Janam Marthak, Devon Vipond
 * @version 1.0
 * @since 2020-04-09
 */
public class StudentGUI extends JFrame {

    private JButton searchCourses, addStudentToCourse, removeStudentFromCourse, viewAllCourses, viewAllStudentCourse;
    private JTextArea display;

    /**
     * Constructs JFrame based object for user interaction, formats all buttons.
     * @param s The title for the main window of the GUI.
     */
    public StudentGUI(String s) {
        // Set high level JFrame properties
        super(s);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     // Ends program on GUI exit
        setSize(1000, 500);
        setLayout(new BorderLayout());

        // Add a title to the top panel
        JLabel title = new JLabel("Student Course Registration System");
        JPanel topPanel = new JPanel();
        topPanel.add(title);

        // Get the buttons set up
        JPanel bottomPanel = createButtonPane();

        // Create the text area
        display = new JTextArea(15, 70);
        display.setEditable(false);   // Stop user editing input
        JScrollPane scrollPane = new JScrollPane(display);   // Make scroll feature

        // Add all components to the content pane
        getContentPane().add(BorderLayout.SOUTH, bottomPanel);
        getContentPane().add(BorderLayout.NORTH, topPanel);
        getContentPane().add(BorderLayout.CENTER, scrollPane);

        setVisible(true);
        pack();
    }

    /**
     * Create and format the content pane containing the buttons for user interaction.
     * @return a JPanel with the buttons formatted inside.
     */
    private JPanel createButtonPane(){
        searchCourses = new JButton("Search course catalogue");
        addStudentToCourse = new JButton("Register for course");
        removeStudentFromCourse = new JButton("Drop course");
        viewAllCourses = new JButton("View all offered courses");
        viewAllStudentCourse = new JButton("View your courses");

        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();

        topPanel.add(searchCourses);
        topPanel.add(viewAllCourses);
        topPanel.add(viewAllStudentCourse);
        bottomPanel.add(addStudentToCourse);
        bottomPanel.add(removeStudentFromCourse);
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(BorderLayout.NORTH, topPanel);
        buttonPanel.add(BorderLayout.SOUTH, bottomPanel);
        return buttonPanel;
    }

    /**
     * Prompts the user to enter their student ID.
     * @return the student ID
     */
    public String getUserLogin(){
        String ID = JOptionPane.showInputDialog("Enter your name to login to the system:");
        return ID;
    }

    /**
     * Displays the input string in the test area.
     * @param toShow the string to be displayed in the text area
     */
    public void displayResponseText(String toShow){
        display.append(toShow);
        display.append("\n");
    }

    /**
     * Clears the text display.
     */
    public void clearTextDisplay(){
        display.setText("");
    }

    /**
     * Prompts the user for the information of the course they want to drop.
     * @return the user input
     */
    public String getCourseNameToDrop(){
        String course = JOptionPane.showInputDialog(this, "Enter the name of the course to drop");
        return course;
    }

    /**
     * Prompts the user for the information of the course they want to add.
     * @return the user input
     */
    public String getCourseNameToAdd(){
        String course = JOptionPane.showInputDialog(this, "Enter the name of the course to add");
        return course;
    }

    /**
     * Prompts the user for the information of the course they want to search for.
     * @return the user input
     */
    public String getCourseNameForSearch(){
        String course = JOptionPane.showInputDialog(this, "Search by course name");
        return course;
    }

    /**
     * Displays the message in a dialog window
     * @param msg to be displayed
     */
    public void showMessageWindow(String msg){
        JOptionPane.showMessageDialog(this, msg);
    }

    /**
     * Gets the Search Courses button/
     * @return the button
     */
    public JButton getButtonSearchCourses(){
        return searchCourses;
    }

    /**
     * Gets the Add student course button.
     * @return the button
     */
    public JButton getButtonAddStudentToCourse(){
        return addStudentToCourse;
    }

    /**
     * Gets the remove student course button.
     * @return the button
     */
    public JButton getButtonRemoveStudentFromCourse(){
        return removeStudentFromCourse;
    }

    /**
     * Gets the view all courses button.
     * @return the button
     */
    public JButton getButtonViewAllCourses(){
        return viewAllCourses;
    }

    /**
     * Gets the view all student courses button.
     * @return the button
     */
    public JButton getButtonViewAllStudentCourse(){
        return viewAllStudentCourse;
    }
}