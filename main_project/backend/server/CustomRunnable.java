package backend.server;

import com.sun.xml.internal.ws.api.message.Message;

import java.io.*;
import java.util.ArrayList;

public abstract class CustomRunnable implements Runnable {
    protected ObjectOutputStream m_sendObject;
    protected ObjectInputStream m_readObject;

    protected BufferedReader m_readString;
    protected PrintWriter m_sendString;

    protected boolean m_running;

    CustomRunnable(PrintWriter p, BufferedReader r, 
                    ObjectOutputStream objOut) {
        m_sendString = p;
        m_readString = r;
        m_sendObject = objOut;

        m_running = false;
    }
    
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


    protected abstract void sendMenu() ;

    protected abstract void handleInput(String in) ;
    protected abstract void handleInput(ArrayList<String> in) ;

    protected boolean isRunning() {
        return m_running;
    }

    protected void start() {
        m_running = true;
    }

    protected void stop () {
        m_running = false;
    }
    
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
