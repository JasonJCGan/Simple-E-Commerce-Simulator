import net.proteanit.sql.DbUtils;
import package1.Connections;
import package1.Operations;

import javax.sound.midi.SysexMessage;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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

    private String sellerID;
    private static Connection con;

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
                            1,
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
                String query = "select * from rate";
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
                String query = "select * from producthas";
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

    public void setSellerID(String id) {
        this.sellerID = id;
    }

    public String getSellerID() {
        return this.sellerID;
    }
}