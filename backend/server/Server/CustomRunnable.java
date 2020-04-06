package Server;

import java.io.*;

public abstract class CustomRunnable implements Runnable {
    protected ObjectOutputStream m_sendObject;
    protected ObjectInputStream m_readObject;

    protected BufferedReader m_readString;
    protected PrintWriter m_sendString;

    protected boolean m_running;

    CustomRunnable(PrintWriter p, BufferedReader r, 
                    ObjectOutputStream objOut, ObjectInputStream objIn) {
        m_sendString = p;
        m_readString = r;
        m_readObject = objIn;
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

    protected abstract void sendMenu() ;

    protected abstract void handleInput(String in) ;

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
