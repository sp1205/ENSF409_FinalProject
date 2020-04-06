package Database;

import java.sql.*;
interface TableConstants{
    public static String url  = "jdbc:sqlserver://localhost:8080;user=root;password=root";
    public static String username  = "root";
    public static String password  = "root";
    public static String driverName = "jdbc:sqlserver://";
}

public class Table implements TableConstants{
    protected Connection m_conn;
    protected PreparedStatement m_statement;

    public Table( ){
        try {
            Class.forName(TableConstants.driverName);

            m_conn = DriverManager.getConnection(
                TableConstants.url,
                TableConstants.username,
                TableConstants.password
                );

        }
        catch (Exception e) {
            System.out.println("Exceptin in Table::Constructor");
            e.getMessage();
        }
    }

    protected Connection getConnection() {
        return m_conn;
    }

    protected PreparedStatement getStatement() {
        return m_statement;
    }
    
    public void shutdown() {
    	try {
    		m_conn.close();
    	}
    	catch (Exception e) {
    		System.out.println("Exception in Table::shutdown: unable to close connection");
    		e.printStackTrace();
    	}
    }


}