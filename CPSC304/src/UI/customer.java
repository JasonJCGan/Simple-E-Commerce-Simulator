import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.*;

import net.proteanit.sql.DbUtils;
import package1.ActiveUser;
import package1.Connections;

public class customer {
    private JTextField price_low;
    private JTextField price_high;
    private JButton findCheapestProductButton;
    private JButton findMostExpensiveProductButton;
    private JTextField rate_proID;
    private JTextField rate_score;
    private JButton rateByIDButton;
    private JTextField order_proID;
    private JButton putOrderButton;
    public JPanel CustomerUI;
    private JTextField search_proName;
    private JTextField search_sellerName;
    private JButton searchButton;
    private JTextField search_rating;
    private JButton findRatingButton;
    private JTable tableSearch;
    private JButton LOGOUTButton;

    private ActiveUser activeUser = ActiveUser.getActiveUser();

    public customer() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String SELECT = "SELECT P.producthas_name, P.producthas_brand, P.producthas_price, P.producthas_id, P.seller_id ";
                String FROM = "FROM producthas P ";
                String WHERE = "";
                try {
                    Connection con = Connections.getConnection();
                    Statement stmt = con.createStatement();
                    ResultSet rs;
                    String proName = search_proName.getText();
                    String low = price_low.getText();
                    String high = price_high.getText();
                    String sellerName = search_sellerName.getText();

                    WHERE = getLikePName(WHERE, proName);
                    WHERE = setPriceRange(WHERE, low, high);

                    if (!(sellerName.isEmpty() || sellerName == null)){
                        SELECT = "SELECT P.producthas_name, P.producthas_brand, P.producthas_price, P.producthas_id, P.seller_id, S.seller_name ";
                        FROM = "FROM producthas P, seller S ";
                        if (WHERE.isEmpty()) {
                            WHERE = "WHERE P.seller_id = S.seller_id AND S.seller_name LIKE '%" + sellerName + "%'";
                        }
                        else {
                            WHERE = WHERE + " AND P.seller_id = S.seller_id AND S.seller_name LIKE '%" + sellerName + "%'";
                        }
                    }

                    rs =stmt.executeQuery(SELECT + FROM + WHERE);
                    tableSearch.setModel(DbUtils.resultSetToTableModel(rs));
                    JOptionPane.showMessageDialog(null, "Search success!");
                }catch(SQLException ex){
                    System.out.println("Search failed (Combo): " + ex.getMessage());
                    JOptionPane.showMessageDialog(null, "Search failure.");
                }
            }

            private String getLikePName(String where, String proName) {
                if (!(proName.isEmpty() || proName == null)){
                    if (where.isEmpty()) {
                        where = "WHERE P.producthas_name LIKE '%" + proName + "%'";
                    }
                    else {
                        where = where + " AND P.producthas_name LIKE '%" + proName + "%'";
                    }
                }
                return where;
            }

            private String setPriceRange(String where, String low, String high) {
                if (!(low.isEmpty() || high.isEmpty())){
                    if (where.isEmpty()) {
                        where = "WHERE P.producthas_price <= " + high + " AND P.producthas_price >= " + low;
                    }
                    else {
                        where = where + " AND P.producthas_price <= " + high + " AND P.producthas_price >= " + low;
                    }
                }
                else if (!low.isEmpty()){
                    if (where.isEmpty()) {
                        where = "WHERE P.producthas_price >= " + low;
                    }
                    else {
                        where += " AND P.producthas_price >= " + low;
                    }
                }
                else if (!high.isEmpty()) {
                    if (where.isEmpty()) {
                        where = "WHERE P.producthas_price <= " + high;
                    }
                    else {
                        where += " AND P.producthas_price >= " + high;
                    }
                }
                return where;
            }
        });

        findRatingButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = Connections.getConnection();
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT AVG(R.rate_rating) " +
                            "FROM rate R " +
                            "WHERE R.Producthas_ID = " +
                            search_rating.getText());


                    tableSearch.setModel(DbUtils.resultSetToTableModel(rs));
                }
                catch (SQLException ex){
                    System.out.println("Search failed (Rating): " + ex.getMessage());
                    JOptionPane.showMessageDialog(null, "Search failure.");
                }
           }
        });

        findCheapestProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = Connections.getConnection();
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT P.producthas_name, P.producthas_brand, P.producthas_price, P.producthas_id, P.seller_id " +
                                                            "FROM producthas P " +
                                                            "WHERE P.producthas_price IN (SELECT MIN(P1.producthas_price) " +
                                                                                            "FROM producthas P1)");

                    tableSearch.setModel(DbUtils.resultSetToTableModel(rs));
                }
                catch (SQLException ex){
                    System.out.println("Search failed (Cheapest): " + ex.getMessage());
                    JOptionPane.showMessageDialog(null, "Search failure.");
                }
            }
        });
        findMostExpensiveProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = Connections.getConnection();
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT P.producthas_name, P.producthas_brand, P.producthas_price, P.producthas_id, P.seller_id " +
                                                            "FROM producthas P " +
                                                            "WHERE P.producthas_price IN (SELECT MAX(P1.producthas_price) " +
                                                                                            "FROM producthas P1)");

                    tableSearch.setModel(DbUtils.resultSetToTableModel(rs));
                }
                catch (SQLException ex){
                    System.out.println("Search failed (Most Expensive): " + ex.getMessage());
                    JOptionPane.showMessageDialog(null, "Search failure.");
                }
            }
        });
        rateByIDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = Connections.getConnection();
                    boolean empty;
                    String query = "SELECT R.* FROM rate R WHERE R.customer_id = " + activeUser.getUser_id() + " AND R.producthas_id = " + rate_proID.getText();
                    try (PreparedStatement ps = con.prepareStatement
                            (query)) {
                        ResultSet temp = ps.executeQuery();
                        empty = temp.next();
                    }

                    if (!empty) {
                        try (PreparedStatement ps = con.prepareStatement("INSERT INTO rate(rate_rating, customer_id, producthas_id) VALUES (?,?,?)")) {
                            ps.setFloat(1, Float.parseFloat(rate_score.getText()));
                            ps.setInt(2, activeUser.getUser_id());
                            ps.setInt(3, Integer.parseInt(rate_proID.getText()));
                            ps.executeUpdate();
                            con.commit();
                        }
                        JOptionPane.showMessageDialog(null, "Item rated!");
                    }
                    else {
                        try (PreparedStatement ps = con.prepareStatement("UPDATE rate SET rate_rating = ? WHERE customer_id = ? AND producthas_id = ?")) {
                            ps.setInt(1, Integer.parseInt(rate_score.getText()));
                            ps.setInt(2, activeUser.getUser_id());
                            ps.setString(3, rate_proID.getText());
                            ps.executeUpdate();
                            con.commit();
                        }
                        JOptionPane.showMessageDialog(null, "Rating updated!");
                    }

                }
                catch (Exception e1){
                    System.out.println("Rating failed : " + e1.getMessage());
                    JOptionPane.showMessageDialog(null, "Rating failed." + e1.getMessage());
                }
            }
        });
        putOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = Connections.getConnection();
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT P.* FROM PUTORDER");
                    int rows = rs.getRow() + 1;
                    java.util.Date today = new java.util.Date();
                    java.sql.Date sqlToday = new java.sql.Date(today.getTime());
                    int rowCount = stmt.executeUpdate("INSERT INTO PUTORDER VALUES (" + rows + ", " +
                            null + ", " + null + ", " + sqlToday + ", " + "PayPal, " + activeUser.getUser_id() + ", " + order_proID.getText() +")");
                }
                catch (SQLException ex) {
                    System.out.println("Order not made : " + ex.getMessage());
                    JOptionPane.showMessageDialog(null, "Ordering failed.");
                }
            }
        });


        LOGOUTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                activeUser.setCustomer(false);
                activeUser.setUser_id(0);

                JOptionPane.showMessageDialog(null,"Logging Out");
                JFrame frame = JFrames.get_frame();
                frame.setTitle("Login");
                login login = new login();
                frame.setContentPane(login.log_in);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
