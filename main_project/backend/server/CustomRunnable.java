package backend.server;

import com.sun.xml.internal.ws.api.message.Message;

import java.io.*;
import java.util.ArrayList;

/**
 * Base class for all runnables in project backend. Handles socket I/O and Threading.
 *
 */
public abstract class CustomRunnable implements Runnable {
    /**
     * Send object to client.
     */
    protected ObjectOutputStream m_sendObject;
    /**
     * Read object from client.
     */
    protected ObjectInputStream m_readObject;

    /**
     * Read string from client.
     */
    protected BufferedReader m_readString;
    /**
     * Send string to clinet.
     */
    protected PrintWriter m_sendString;

    /**
     * True if this is running in a thread, else false.
     *
     */
    protected boolean m_running;


    /**
     * Constructor
     * @param p assigned to internal member
     * @param r assigned to internal member
     * @param objOut assigned to internal member
     */
    CustomRunnable(PrintWriter p, BufferedReader r, 
                    ObjectOutputStream objOut) {
        m_sendString = p;
        m_readString = r;
        m_sendObject = objOut;

        m_running = false;
    }

    /**
     * Handles reading string from client. Blocking operation.
     * @return String read from client.
     */
    protected String readString() {
        try {
            return m_readString.readLine();
        }
        catch (IOException e) {
            System.out.println("Exception in CustomRunnable::readString");
            e.printStackTrace();
            stop();
            shutdown();
            return null;
        }
    }

    /**
     * Sends string to client
     * @param s string to send
     * @return true if successful, else false
     */
    protected boolean sendString (String s) {
        try {
            m_sendString.println(s);
            m_sendString.flush();
        }
        catch (Exception e) {
            System.out.println("Exception in CustomRunnable::sendString");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Sends object to client.
     * @param obj object to send
     * @return true if successful.
     */
    protected boolean sendObject(Object obj) {
    	try {
			m_sendObject.flush();
			m_sendObject.writeObject(obj);
    	}
        catch (Exception e) {
            System.out.println("Exception in CustomRunnable::sendObject");
            e.printStackTrace();
            return false;
        }
    	
    	return true;
    }

    /**
     * Reads full message from client.
     * @return message read from client.
     */
    public ArrayList<String> readMessage() {
        ArrayList<String> message = new ArrayList<String>();
        String read = "";

        read = readString();

        String[] parts = read.split(StudentQueries.messageDelimiter);
        for (String s : parts) {
            System.out.println(s);
            message.add(s);
        }

        System.out.println("CustomRunnable::readMessage: parsed full message: "  );
        for (String s : message) {
            System.out.println(s);

        }
        return message;

    }

    /**
     * Sends either a success or a fail message to client
     *
     * an object or an error message can also optionally be sent.
     * @param success was the client query successful
     * @param obj object to optionally send.
     * @param errorMessage error message to optionally send.
     */
    public void sendResponse(boolean success, Object obj, String errorMessage) {
        if (success) {
            sendString(new MessageBuilder().successMessage());
        }
        else {
            sendString(errorMessage+ StudentQueries.messageDelimiter);
        }

        if (obj != null)
            sendObject(obj);
    }


    /**
     * Sends a list of options to cleint
     */
    protected abstract void sendMenu() ;

    /**
     * Handles input read from client
     * @param in input message
     */
    protected abstract void handleInput(String in) ;
    /**
     * Handles input read from client
     * @param in input message
     */
    protected abstract void handleInput(ArrayList<String> in) ;

    /**
     * Return true if running
     * @return true if running
     */
    protected boolean isRunning() {
        return m_running;
    }

    /**
     * Starts the main loop in runnable
     */
    protected void start() {
        m_running = true;
    }

    /**
     * Stops main loop
     */
    protected void stop () {
        m_running = false;
    }

    /**
     * Closes all IO connections and stops the runnable.
     */
    protected void shutdown() {
        try {
            stop();
            m_readObject.close();
            m_sendObject.close();
            m_sendString.close();
            m_readString.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
