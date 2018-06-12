package package1;
 
import java.sql.*;

public class Main {
	static String username = "user";
	static String password = "password";
	public static void main(String[] args) throws Exception {
		//loads driver 
                       
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		//creates connection to ugrad db
		Connection con = DriverManager.getConnection(
				  "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", 
				username, password);
		
		//make a sql statement
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM branch");
		
		//while(rs.next()){
			System.out.println(rs);
		//}
			//
		
		rs.close();
		stmt.close();
		con.close();
	}
}
