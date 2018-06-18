package package1;

import java.sql.*;

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
                r = temp.next();
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
                r = temp.next();
            }
        }catch (Exception e){
            throw new SQLException();
        }
        return r;
    }

    /**************** Queries for Seller Add Product(Jason) *****************************/
    public boolean s_addProduct(int id, int quantity, String name, String category,
                                String brand, float price, float discount, int sellerId,
                                Connection con) throws SQLException {
        boolean r;
        try {
            PreparedStatement ps = con.prepareStatement
                    ("INSERT INTO producthas " +
                            "(producthas_id,producthas_stock,producthas_name,producthas_category,producthas_brand,producthas_price,producthas_vipdiscount,seller_id) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            ps.setInt(1, id);
            ps.setInt(2, quantity);
            ps.setString(3, name);
            ps.setString(4, category);
            ps.setString(5, brand);
            ps.setFloat(6, price);
            ps.setFloat(7, discount);
            ps.setInt(8, sellerId);
            ps.executeUpdate();
            con.commit();
            ps.close();
            r = true;
        }
        catch (Exception e){
            throw new SQLException();
        }
        return r;
    }
    /**************** Queries for Seller Delete Product(Jason) *****************************/
    public boolean s_deleteProduct(int id, Connection con) throws SQLException {
        boolean r;
        try{
            try (PreparedStatement ps = con.prepareStatement
                    ("DELETE FROM producthas WHERE producthas_id = ?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
                r = true;
                con.commit();
            }
        }catch (Exception e){
            throw new SQLException();
        }
        return r;
    }
}
