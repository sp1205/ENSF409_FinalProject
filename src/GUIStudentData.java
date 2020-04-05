import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;

/**
 * The following class is a GUI that makes calls to a BinarySearchTree for
 * a user to see output relating to student data.
 *
 * @author Robyn Scholz, Devon Vipond, Janam Marthak
 * @version 1.0
 * @since 2020-04-04
 *
 */
public class GUIStudentData extends JFrame{

    private BinSearchTree studentData;
    private JTextArea display;


    /**
     * Constructs JFrame based object for user interaction, formats all buttons and displays and assigns listeners
     * to button objects. Creates the Binary Search Tree.
     * @param s The title for the main window of the GUI.
     */
    public GUIStudentData  (String s) {
        // Set high level JFrame properties
        super (s);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     // Ends program on GUI exit
        setSize(900, 500);
        setLayout(new BorderLayout());

        // Create 4 buttons
        JButton insert = new JButton("Insert");
        JButton find = new JButton("Find");
        JButton browse = new JButton("Browse");
        JButton createTreeFromFile = new JButton("Create Tree From File");
        // Add buttons to bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(insert);
        bottomPanel.add(find);
        bottomPanel.add(browse);
        bottomPanel.add(createTreeFromFile);

        // Add a title to the top panel
        JLabel title = new JLabel("An application to Maintain Student Records");
        JPanel topPanel = new JPanel();
        topPanel.add(title);

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

        // Set up action listener for each button.
        insert.addActionListener((ActionEvent e) -> {insertResponse();});
        find.addActionListener((ActionEvent e) -> {findResponse();});
        browse.addActionListener((ActionEvent e) -> {browseResponse();});
        createTreeFromFile.addActionListener((ActionEvent e) -> {createTreeFromFileResponse();});

        // Create empty tree of student data
        studentData = new BinSearchTree();
    }

    /**
     * Opens input dialog for user to enter new student information and adds data to BST.
     */
    private void insertResponse(){

        // Create the text inputs
        JPanel newPanel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        JTextField idField = new JTextField(15);
        JTextField facultyField = new JTextField(15);
        JTextField majField = new JTextField(15);
        JTextField yearField = new JTextField(15);

        topPanel.add(new JLabel("Enter the student ID:"));
        topPanel.add(idField);

        topPanel.add(new JLabel("Enter faculty:"));
        topPanel.add(facultyField);

        bottomPanel.add(new JLabel("Enter student's major:"));
        bottomPanel.add(majField);

        bottomPanel.add(new JLabel("Enter year:"));
        bottomPanel.add(yearField);

        // Get panels ready for display
        newPanel.add(BorderLayout.NORTH, topPanel);
        newPanel.add(BorderLayout.SOUTH, bottomPanel);

        // Present the panel to the user and get input
        int result = JOptionPane.showConfirmDialog(this, newPanel, "Enter new student information",
                JOptionPane.OK_CANCEL_OPTION);

        // Check that we have a valid student ID
        String studentID = idField.getText();
        if(studentID.length() == 6) {
            if (result == JOptionPane.OK_OPTION) {
                studentData.insert(studentID, facultyField.getText(), majField.getText(), yearField.getText());
            }
        }
        else{
            JOptionPane.showMessageDialog(this, "Student ID must be 6 numbers long");
        }
    }

    /**
     * Opens input dialog for user to search for a student number and displays the result of the seach from the BST.
     */
    private void findResponse(){
        String studentId = JOptionPane.showInputDialog(this, "Please enter the student's id:");
        if(studentId != null) {
            Object result = studentData.find(studentData.root, studentId);
            if (result == null) {
                JOptionPane.showMessageDialog(this, "The student record was not found");
            } else {
                JOptionPane.showMessageDialog(this, result);
            }
        }
    }

    /**
     * Displays the contents of the BST to the GUI.
     */
    private void browseResponse(){

        // Create special printer capabilities
        StringWriter buffer = new StringWriter();
        PrintWriter writer = new PrintWriter(buffer);
        if(studentData.empty()){        // Tell user the tree is empty
            JOptionPane.showMessageDialog(this, "No data to show.");
        }
        else {
            try {
                studentData.print_tree(studentData.root, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String contents = buffer.toString();
            display.setText(contents);      // Show accumulated outputs to display
        }
    }

    /**
     * Opens input dialog to get the name of input file from the user.
     */
    private void createTreeFromFileResponse(){
        String filename = JOptionPane.showInputDialog(this, "Enter file name:");
        if(filename != null) {
            buildData(filename);
        }
    }

    /**
     * Opens and passes file contents to construct BST.
     * @param fileName name of the text file to open
     */
    private void buildData(String fileName) {
        studentData.destroy();
        try {
            File f = new File(fileName);//input must in the same folder this java file.

            BufferedReader bf = new BufferedReader(new FileReader(f));
            String s = bf.readLine();
            while (s != null) {
                // Parse the line for words and replace characters
                String[] data = s.split("\\s+");
                studentData.insert(data[1], data[2], data[3], data[4]);      // Immediately add each word to the BST
                s = bf.readLine();
            }
            bf.close();     // Close input stream
            JOptionPane.showMessageDialog(this, "The student data was loaded.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Could not open File.");
        }
    }


    public static void main (String [] args) {
        GUIStudentData myFrame = new GUIStudentData ("Main Window");
    }
}
