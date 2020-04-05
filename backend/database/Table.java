package Database;

import java.sql.*;
interface TableConstants{
    public static String url  = "jdbc:mysql://localhost:8080/db";
    public static String username  = "root";
    public static String password  = "root";
    public static String driverName = "com.mysql.jdbc.Driver";
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


}