package Server;
//import Database.DatabaseManager;

import java.io.*;
import java.util.concurrent.*;
import java.net.*;

public class Server {

    private ServerSocket m_serverSocket;
    private Socket m_socket;
    private ExecutorService m_pool;

    private DatabaseManager m_db;

    private ObjectOutputStream m_sendObject;

    private ObjectInputStream m_readObject;

    private BufferedReader m_readString;
    private PrintWriter m_sendString;

    private boolean m_running;

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

    public void stop() {
        m_running = false;
    }

    private void shutdown() {
        try {
            m_socket.close();
            m_readObject.close();
            m_sendObject.close();
            m_readString.close();
            m_sendString.close();

        }
        catch (Exception e) {
            System.out.println("Exception in Server::shutdown");
            e.printStackTrace();
        }

    }

    public void run() {
        int log = 0;
        m_running = true;

        while (m_running) {
            try {
                // listen for clients 
                m_socket = m_serverSocket.accept();

                m_readString = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));

                m_sendString = new PrintWriter(m_socket.getOutputStream(), true);

                m_sendObject = new ObjectOutputStream(m_socket.getOutputStream());
                m_sendObject.flush();
                m_readObject = new ObjectInputStream(m_socket.getInputStream());


                System.out.println("Server: Starting StudentRunnable");
                Runnable task = new StudentRunnable(m_sendString, m_readString, m_sendObject, m_readObject);
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

    public static void main(String[] args) {
        try {
            System.out.println("Starting server on port 8081");
            Server server = new Server(8081);
            server.run();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

