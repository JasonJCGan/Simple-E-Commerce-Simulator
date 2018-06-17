package package1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connections {
    static String connectURL = "jdbc:oracle:thin:@localhost:1522:ug";
    static String username = "ora_s1h0b";
    static String password = "a33731143";
    private static Connection con=null;

    private Connections() throws SQLException{
        try {
            // Load the Oracle JDBC driver
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            con = DriverManager.getConnection(connectURL,username,password);
            System.out.println("\nConnected to Oracle!");
    }
        catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException{
        if(con == null){
            new Connections();
        }
        return con;

    }
}