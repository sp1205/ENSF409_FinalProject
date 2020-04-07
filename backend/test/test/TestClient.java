package test;
import Models.*;
import Server.*;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.*;
import java.net.Socket;

interface StudentQueries {
    public static String searchCourse = "1";
    public static String addCourseToStudent = "2";
    public static String removeCourseFromStudent = "3";
    public static String listCourses = "4";
    public static String allCoursesTakenByStudent = "5";
    public static String quit = "6";
}


public class TestClient {
	private PrintWriter socketOut;
	private Socket palinSocket;
	private BufferedReader stdIn;
	private BufferedReader socketIn;

    private ObjectOutputStream m_sendObject;
    private ObjectInputStream m_readObject;

	public TestClient(String serverName, int portNumber) {
		try {
			palinSocket = new Socket(serverName, portNumber);
                m_sendObject = new ObjectOutputStream(palinSocket.getOutputStream());
                m_sendObject.flush();
                m_readObject = new ObjectInputStream(palinSocket.getInputStream());
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			socketIn = new BufferedReader(new InputStreamReader(
					palinSocket.getInputStream()));
			socketOut = new PrintWriter((palinSocket.getOutputStream()), true);
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}
	
	public void sendString(String str) {
		socketOut.flush();
		socketOut.println(str);
	}

	public void communicate()  {

		MessageBuilder mb = new MessageBuilder();
		String in = "";
		String response = "";
		try {
			while (true) {
				System.out.println("please enter a option: ");
				in = stdIn.readLine();

				if (in.equals( StudentQueries.searchCourse)) {
					System.out.println("Enter Course Id");
					String courseId = stdIn.readLine();
					
					sendString(mb.searchCourseMessage(courseId));
					
					System.out.println("Client: waiting for response");
					response = socketIn.readLine();

					System.out.println(response);
					if (!response.equals(mb.successMessage()))
						continue;

					Course course = (Course) m_readObject.readObject();
					System.out.println(course);
				}
				else if (in.equals( StudentQueries.addCourseToStudent)) {
					System.out.println("Enter Course Id");
					String courseId = stdIn.readLine();

					sendString(mb.addCourseToStudentMessage(courseId));
				}
				else if (in.equals( StudentQueries.removeCourseFromStudent)) {
					System.out.println("Enter Course Id");
					String courseId = stdIn.readLine();

					sendString(mb.removeCourseFromStudentMessage(courseId));
				}
				else if (in.equals(StudentQueries.listCourses)) {
					sendString(mb.listCoursesMessage());

					System.out.println("Client: waiting for response");
					response = socketIn.readLine();
					System.out.println(response);

					System.out.println("Client: waiting for arrayList");
					ArrayList<Course> list = (ArrayList<Course>) m_readObject.readObject();

					for (Course course : list) {
						System.out.println(course);
					}
				}
				else if (in.equals( StudentQueries.allCoursesTakenByStudent)) {
					sendString(mb.allCoursesTakenByStudentMessage());
				}
			}
			
		} catch (Exception e) {
			System.out.println("Sending error: " + e.getMessage());
			e.printStackTrace();
		}
		try {
			stdIn.close();
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			System.out.println("Closing error: " + e.getMessage());
		}

	}

	public static void main(String[] args) throws IOException  {
		TestClient aTestClient = new TestClient("localhost", 8040);
		aTestClient.communicate();
	}
}

