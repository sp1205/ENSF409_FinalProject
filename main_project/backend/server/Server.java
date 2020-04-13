package backend.server;

import java.io.*;
import java.util.concurrent.*;
import java.net.*;

/**
 * Listens for connections from client.
 */
public class Server {

    /**
     * Incoming connections go thru here
     */
    private ServerSocket m_serverSocket;
    /**
     * socket to client
     */
    private Socket m_socket;
    /**
     * Handles login threads
     */
    private ExecutorService m_pool;

    /**
     * Handles database queries
     */
    private DatabaseManager m_db;

    /**
     * Writes to client
     */
    private ObjectOutputStream m_sendObject;


    /**
     * Reads from client
     */
    private BufferedReader m_readString;
    /**
     * Sends string to cleint
     */
    private PrintWriter m_sendString;

    /**
     * True if main loop is running.
     */
    private boolean m_running;

    /**
     * Constructs a server listening on a specific port
     * @param port port to listen on
     */
    public Server(int port) {
        try {
            m_serverSocket = new ServerSocket(port);
            m_pool = Executors.newCachedThreadPool();
            System.out.println("Server: Server is running on port " + port);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stops main loop
     */
    public void stop() {
        m_running = false;
    }

    /**
     * CLoses all IO interfaces.
     */
    private void shutdown() {
        try {
            m_socket.close();
            m_sendObject.close();
            m_readString.close();
            m_sendString.close();

        }
        catch (Exception e) {
            System.out.println("Exception in Server::shutdown");
            e.printStackTrace();
        }

    }

    /**
     * Main execution loop. Listens for connections from clients.
     */
    public void run() {
        int log = 0;
        m_running = true;
        DatabaseManager db = new DatabaseManager();

        while (m_running) {
            try {
                // listen for clients 
                m_socket = m_serverSocket.accept();

                m_readString = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));

                m_sendString = new PrintWriter(m_socket.getOutputStream(), true);

                m_sendObject = new ObjectOutputStream(m_socket.getOutputStream());
                m_sendObject.flush();


                //Runnable task = new StudentRunnable(m_sendString, m_readString, m_sendObject, m_readObject, db);
                Runnable task = new LoginRunnable(m_sendString, m_readString, m_sendObject, db);
                m_pool.execute(task);
            }
            catch (Exception e) {
                System.out.println("Exception in Server::run");
                e.printStackTrace();
                shutdown();
                break;
            }
        }
    }

    /**
     * This is where the backend starts execution
     * @param args command line args, there should be none .
     */
    public static void main(String[] args) {
        try {
            System.out.println("Starting server on port 8081");
            Server server = new Server(8040);
            server.run();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

