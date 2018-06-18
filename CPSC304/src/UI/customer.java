import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.*;
import package1.Connections;

public class customer {
    private JTextArea text_proname;
    private JTextArea text_proprice;
    private JTextArea text_probrand;
    private JTextArea text_proID;
    private JTextArea text_sellerID;
    private JTextArea text_sellername;
    private JTextField price_low;
    private JTextField price_high;
    private JButton findCheapestProductButton;
    private JButton findMostExpensiveProductButton;
    private JTextField rate_proID;
    private JTextField rate_score;
    private JButton rateByIDButton;
    private JTextField order_proID;
    private JTextField order_sellerID;
    private JTextField order_quantity;
    private JButton putOrderButton;
    public JPanel CustomerUI;
    private JTextField search_proName;
    private JTextField search_sellerName;
    private JButton searchButton;
    private JTextField search_rating;
    private JButton findRatingButton;
    private String customerID;

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public customer() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String SELECT = "SELECT P.producthas_id";
                String FROM = " FROM producthas P ";
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

                    if (!search_sellerName.getText().isEmpty()){
                        SELECT += ", seller S";
                        if (WHERE.isEmpty()) {
                            WHERE = "WHERE P.seller_id = S.seller_id AND S.seller_name LIKE %" + search_sellerName + "%";
                        }
                        else {
                            WHERE = WHERE + " AND P.seller_id = S.seller_id AND S.seller_name LIKE %" + search_sellerName + "%";
                        }
                    }

                    rs =stmt.executeQuery(SELECT + FROM + WHERE);
                    // Do something with ResultSet

                }catch(SQLException ex){
                    System.out.println("Search failed (Combo): " + ex.getMessage());
                }
            }

            private String getLikePName(String where, String proName) {
                if (!proName.isEmpty()){
                    if (where.isEmpty()) {
                        where = "WHERE P.producthas_name LIKE %" + proName + "%";
                    }
                    else {
                        where += " AND P.producthas_name LIKE %" + proName + "%";
                    }
                }
                return where;
            }

            private String setPriceRange(String where, String low, String high) {
                if (!(low.isEmpty() && high.isEmpty())){
                    if (where.isEmpty()) {
                        where = "WHERE P.producthas_price <= " + high + " AND P.producthas_price >= " + low;
                    }
                    else {
                        where += " AND P.producthas_price <= " + high + " AND P.producthas_price >= " + low;
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
                    ResultSet rs = stmt.executeQuery("SELECT MIN(P.producthas_price) " +
                            "FROM producthas P " +
                            "WHERE P.producthas_id = " + search_rating);

                    // Do something with ResultSet

                }
                catch (SQLException ex){
                    System.out.println("Search failed (Rating): " + ex.getMessage());
                }
           }
        });

        findCheapestProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = Connections.getConnection();
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT MIN(P.producthas_price) " +
                            "FROM producthas P");

                    // Do something with ResultSet

                }
                catch (SQLException ex){
                    System.out.println("Search failed (Rating): " + ex.getMessage());
                }
            }
        });
        findMostExpensiveProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = Connections.getConnection();
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT MAX(P.producthas_price) " +
                            "FROM producthas P");

                    // Do something with ResultSet

                }
                catch (SQLException ex){
                    System.out.println("Search failed (Rating): " + ex.getMessage());
                }
            }
        });
        rateByIDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        putOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });



    }
}
