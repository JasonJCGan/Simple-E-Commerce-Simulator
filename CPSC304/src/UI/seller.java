import package1.Connections;
import package1.Operations;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private String sellerID;
    //used to call methods in operations
    public Operations ope = new Operations();

    public seller() throws SQLException {
        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = Connections.getConnection();
                    boolean added;
                    try{
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
                        if (added){
                            // log in to Customer UI
                            JOptionPane.showMessageDialog(null,"Success!");
                        }
                        else{
                            throw new SQLException();
                        }
                    }
                    catch (java.sql.SQLException e2){
                        System.out.println(e2);
                        JOptionPane.showMessageDialog(null,"Failed to add product!");
                    }
                } catch (java.sql.SQLException e1) {
                    System.out.println(e1);
                    JOptionPane.showMessageDialog(null,"e1");
                }
            }
        });
        deleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = Connections.getConnection();
                    boolean deleted;
                    try{
                        deleted = ope.s_deleteProduct(
                                Integer.parseInt(textProdId.getText()), con);
                        if (deleted){
                            JOptionPane.showMessageDialog(null,"Success!");
                        }
                        else{
                            throw new SQLException();
                        }
                    }
                    catch (java.sql.SQLException e2){
                        JOptionPane.showMessageDialog(null,"Failed to delete product!");
                    }
                } catch (java.sql.SQLException e1) {
                    JOptionPane.showMessageDialog(null,"e1");
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