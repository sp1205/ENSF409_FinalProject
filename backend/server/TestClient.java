import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.*;
import java.net.Socket;

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

	public void communicate()  {

		String line = "";
		String response = "";
		boolean running = true;
		while (running) {
			try {
				response = socketIn.readLine();
				System.out.println(response);



				System.out.println("please enter a option: ");
				line = stdIn.readLine();
				if (!line.equals("QUIT")){
					socketOut.println(line);
					response = socketIn.readLine();
					System.out.println(response);	
				}else{
					running = false;
				}
				
			} catch (IOException e) {
				System.out.println("Sending error: " + e.getMessage());
			}
		}
		try {
			stdIn.close();
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			System.out.println("Closing error: " + e.getMessage());
		}

	}
	/*
	public void communicate()  {

		String line = "";
		String response = "";
		boolean running = true;
		while (running) {
			try {
				System.out.println("please enter a option: ");
				line = stdIn.readLine();
				if (!line.equals("QUIT")){
					socketOut.println(line);
					response = socketIn.readLine();
					System.out.println(response);	
				}else{
					running = false;
				}
				
			} catch (IOException e) {
				System.out.println("Sending error: " + e.getMessage());
			}
		}
		try {
			stdIn.close();
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			System.out.println("Closing error: " + e.getMessage());
		}

	}*/

	public static void main(String[] args) throws IOException  {
		TestClient aTestClient = new TestClient("localhost", 8081);
		aTestClient.communicate();
	}
}