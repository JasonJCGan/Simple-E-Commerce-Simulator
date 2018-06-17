package package1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Operations {
    /**************** Queries for Customer Login(YUAN) *****************************/

    public boolean c_login(String id,Connection con) throws SQLException {
        boolean r;
        try{
            int c_id = Integer.parseInt(id);
            try (PreparedStatement ps = con.prepareStatement
                    ("SELECT Customer_ID FROM Customer WHERE Customer_ID = ? ")) {
                ps.setInt(1, c_id);
                ResultSet temp = ps.executeQuery();
                r = !temp.wasNull();
                ps.close();
            }
        }catch (Exception e){
            throw new SQLException();
        }
    return r;
    }

    /**************** Queries for Seller Login(YUAN) *****************************/
    public boolean s_login(String id,Connection con) throws SQLException {
        boolean r;
        try{
            int s_id = Integer.parseInt(id);
            try (PreparedStatement ps = con.prepareStatement
                    ("SELECT Seller_ID FROM Seller WHERE Seller_ID = ? ")) {
                ps.setInt(1, s_id);
                ResultSet temp = ps.executeQuery();
                r = !temp.wasNull();
                ps.close();
            }
        }catch (Exception e){
            throw new SQLException();
        }
        return r;
    }
}
