import java.io.BufferedReader;
import java.io.ObjectOutputStream;

interface Queries {
    public static String listCourses = "1";
    public static String listStudents = "2";
}
public class StudentRunnable implements Queries{
    private ObjectOutputStream m_sendObject;
    private ObjectInputStream m_readObject;

    private BufferedReader m_readString;
    private PrinterWriter m_sendString;

    StudentRunnable(PrinterWriter p, BufferedReader r, 
                    ObjectOutputStream objOut, ObjectInputStream objIn) {
        m_sendString = p;
        m_readString = r;
        m_readObject = objIn;
        m_sendObject = objOut;

    }

    public String readString() {
        return m_readString.readLine();
    }

    public boolean sendString (String s) {
        try {
            m_sendString.println(s);
            m_sendString.flush();
        }
        catch (Exception e) {
            System.out.println("Exception in StudentRunnable::sendString");
            e.printStackTrace();
        }
    }

    public void sendMenu() {
        String menu = 
         "/n/nCourse Registratin System"  
        + "1. Search catalogue courses"
        + "2. Add course to student courses"
        + "3. Remove course from student courses"
        + "4. View All courses in catalogue"
        + "5. View all courses taken by student"
        + "6. Quit";
    }

    public ArrayList<Courses> listCourses() {
        // not sure what else to do here
    }

    public ArrayList<Courses> listStudents() {
        // not sure what else to do here
    }

    public void handleInput(String in) {
        if (in == Queries.listCourses) {
            listCourses();
        }
        else if (in == Queries.listStudents) {
            listStudents();
        }
        else {
            sendString("Unknown input: " + in);
        }

    }

    public void run() {
        try {
            while (true) {
                String userInput = m_readString.readLine();
                System.out.println("StudentRunnable: " + userInput);

                handleInput(userInput);
            }
        }
        catch (Exception e) {
            System.out.println("Exception in StudentRunnable::run");
            e.printStackTrace();
        }
    }
}
