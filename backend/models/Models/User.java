package Models;
import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 188888888L;

    protected int m_uid;
    protected String m_username;
    protected String m_password;
    protected String m_firstName;
    protected String m_lastName;

    public User(int m_uid, String m_username, String m_password, String m_firstName, String m_lastName) {
        this.m_uid = m_uid;
        this.m_username = m_username;
        this.m_password = m_password;
        this.m_firstName = m_firstName;
        this.m_lastName = m_lastName;
    }

    public int getuid() {
        return m_uid;
    }

    public void setuid(int m_uid) {
        this.m_uid = m_uid;
    }

    public String getusername() {
        return m_username;
    }

    public void setusername(String m_username) {
        this.m_username = m_username;
    }

    public String getpassword() {
        return m_password;
    }

    public void setpassword(String m_password) {
        this.m_password = m_password;
    }

    public String getfirstName() {
        return m_firstName;
    }

    public void setfirstName(String m_firstName) {
        this.m_firstName = m_firstName;
    }

    public String getlastName() {
        return m_lastName;
    }

    public void setlastName(String m_lastName) {
        this.m_lastName = m_lastName;
    }
    


}
