import Models.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.*;
import java.net.Socket;

public class TestClient {
	private PrintWriter socketOut;
	private Socket clientSocket;
	private BufferedReader stdIn;
	private BufferedReader socketIn;

    private ObjectOutputStream m_sendObject;
    private ObjectInputStream m_readObject;

	public TestClient(String serverName, int portNumber) {
		try {
			clientSocket = new Socket(serverName, portNumber);
                m_sendObject = new ObjectOutputStream(clientSocket.getOutputStream());
                m_sendObject.flush();
                m_readObject = new ObjectInputStream(clientSocket.getInputStream());
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			socketIn = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			socketOut = new PrintWriter((clientSocket.getOutputStream()), true);
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}
	
	public String readSocket() {
		try {
			return socketIn.readLine();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void communicate()  {

		String line = "";
		String response = "";
		while (true) {
			try {
				response = readSocket();
				System.out.println(response);
				while (true) {
					System.out.println("please enter a option: ");
					line = stdIn.readLine(); 
					if (line.equals)

					socketOut.println(line);
					response = readSocket();
					System.out.println(response);	
				}
				
			} catch (Exception e) {
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

	public static void main(String[] args) throws IOException  {
		TestClient aTestClient = new TestClient("localhost", 8081);
		aTestClient.communicate();
	}
}
