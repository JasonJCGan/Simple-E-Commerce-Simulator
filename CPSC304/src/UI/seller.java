import net.proteanit.sql.DbUtils;
import package1.ActiveUser;
import package1.Connections;
import package1.Operations;

import javax.sound.midi.SysexMessage;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class seller {
    private JTextField textProdId;
    private JTextField textProdName;
    private JTextField textProdPrice;
    private JTextField textProdQuantity;
    private JTextField textProdBrand;
    private JTextField textProdCategory;
    private JButton addProductButton;
    private JButton deleteProductButton;
    public JPanel SellerUI;
    private JTable tableProduct;
    private JTable tableRatings;
    private JButton LOGOUTButton;
    private JTable tableDiv;
    private JTextField divField;
    private JButton divButton;
    private JButton findMaxAverageRatingButton;
    private JButton findMinAverageRatingButton;
    private JButton findMaxAverageRatingButton1;
    private JButton findMinAverageRatingButton1;

    private static Connection con;
    private ActiveUser activeUser = ActiveUser.getActiveUser();

    static {
        try {
            con = Connections.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Operations ope = new Operations();

    public seller() throws SQLException {
        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean added;
                try {
                    added = ope.s_addProduct(
                            Integer.parseInt(textProdId.getText()),
                            Integer.parseInt(textProdQuantity.getText()),
                            textProdName.getText(),
                            textProdCategory.getText(),
                            textProdBrand.getText(),
                            Float.parseFloat(textProdPrice.getText()),
                            10,
                            activeUser.getUser_id(),
                            con);
                    if (added) {
                        // log in to Customer UI
                        JOptionPane.showMessageDialog(null, "Success!");
                    } else {
                        throw new SQLException();
                    }
                } catch (java.sql.SQLException e2) {
                    System.out.println(e2);
                    JOptionPane.showMessageDialog(null, "Failed to add product!");
                }

            }
        });
        deleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean deleted;
                try {
                    deleted = ope.s_deleteProduct(
                            Integer.parseInt(textProdId.getText()), con);
                    if (deleted) {
                        JOptionPane.showMessageDialog(null, "Success!");
                    } else {
                        throw new SQLException();
                    }
                } catch (java.sql.SQLException e2) {
                    JOptionPane.showMessageDialog(null, "Failed to delete product!");
                }
            }
        });

        tableRatings.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                super.focusGained(focusEvent);
                String query = "SELECT R.rate_rating, R.PRODUCTHAS_ID " +
                                "FROM RATE R, PRODUCTHAS P " +
                                "WHERE P.SELLER_ID = " + Integer.toString(activeUser.getUser_id()) + " AND R.PRODUCTHAS_ID = P.PRODUCTHAS_ID";
                try {
                    PreparedStatement ps = con.prepareStatement(query);
                    ResultSet r = ps.executeQuery();
                    tableRatings.setModel(DbUtils.resultSetToTableModel(r));
                } catch (java.sql.SQLException e2) {
                    System.out.println(e2);
                    JOptionPane.showMessageDialog(null, "Failed to update!");
                }
            }
        });

        tableProduct.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                super.focusGained(focusEvent);
                String query = "select * from producthas where producthas.SELLER_ID = " + Integer.toString(activeUser.getUser_id());
                    try{
                        PreparedStatement ps = con.prepareStatement(query);
                        ResultSet r = ps.executeQuery();
                        tableProduct.setModel(DbUtils.resultSetToTableModel(r));
                    }
                    catch (java.sql.SQLException e2){
                        System.out.println(e2);
                        JOptionPane.showMessageDialog(null,"Failed to update!");
                    }
                }
        });

        LOGOUTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                activeUser.setSeller(false);
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


        divButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = "SELECT C.customer_id " +
                                "FROM customer C " +
                                "WHERE NOT EXISTS (" +
                                    "SELECT P.producthas_id " +
                                    "FROM producthas P " +
                                    "WHERE P.producthas_brand = '" + divField.getText() + "' AND NOT EXISTS (" +
                                        "SELECT O.putorder_id " +
                                        "FROM putorder O " +
                                        "WHERE P.producthas_id = O.producthas_id " +
                                        "AND O.customer_id = C.customer_id" +
                                        ")" +
                                    ")";
                try {
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    tableDiv.setModel(DbUtils.resultSetToTableModel(rs));
                    JOptionPane.showMessageDialog(null, "Search success!");
                }
                catch (SQLException ex) {
                    System.out.println("Search failed (Division): " + ex.getMessage());
                    JOptionPane.showMessageDialog(null, "Search failed.");
                }
            }
        });
        findMaxAverageRatingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String query = "select max(avg) from (select avg(r.rate_rating) as avg, p.producthas_category from " +
                        "(select producthas_id, producthas_category from producthas) P, rate R where P.producthas_id = R.producthas_id group by P.producthas_category)";
                try{
                    PreparedStatement ps = con.prepareStatement(query);
                    ResultSet r = ps.executeQuery();
                    tableProduct.setModel(DbUtils.resultSetToTableModel(r));
                }
                catch (java.sql.SQLException e2){
                    System.out.println(e2);
                    JOptionPane.showMessageDialog(null,"Failed to update!");
                }
            }
        });
        findMinAverageRatingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String query = "select min(avg) from (select avg(r.rate_rating) as avg, p.producthas_category from " +
                        "(select producthas_id, producthas_category from producthas) P, rate R where P.producthas_id = R.producthas_id group by P.producthas_category)";
                try{
                    PreparedStatement ps = con.prepareStatement(query);
                    ResultSet r = ps.executeQuery();
                    tableProduct.setModel(DbUtils.resultSetToTableModel(r));
                }
                catch (java.sql.SQLException e2){
                    System.out.println(e2);
                    JOptionPane.showMessageDialog(null,"Failed to update!");
                }
            }
        });
        findMaxAverageRatingButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String query = "select max(avg) from (select avg(r.rate_rating) as avg, p.producthas_brand from " +
                        "(select producthas_id, producthas_brand from producthas) P, rate R where P.producthas_id = R.producthas_id group by P.producthas_brand)";
                try{
                    PreparedStatement ps = con.prepareStatement(query);
                    ResultSet r = ps.executeQuery();
                    tableProduct.setModel(DbUtils.resultSetToTableModel(r));
                }
                catch (java.sql.SQLException e2){
                    System.out.println(e2);
                    JOptionPane.showMessageDialog(null,"Failed to update!");
                }
            }
        });

        findMinAverageRatingButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String query = "select min(avg) from (select avg(r.rate_rating) as avg, p.producthas_brand from " +
                        "(select producthas_id, producthas_brand from producthas) P, rate R where P.producthas_id = R.producthas_id group by P.producthas_brand)";
                try{
                    PreparedStatement ps = con.prepareStatement(query);
                    ResultSet r = ps.executeQuery();
                    tableProduct.setModel(DbUtils.resultSetToTableModel(r));
                }
                catch (java.sql.SQLException e2){
                    System.out.println(e2);
                    JOptionPane.showMessageDialog(null,"Failed to update!");
                }
            }
        });
    }
}