import package1.Connections;
import package1.Operations;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class login {
    public JPanel log_in;
    private JTextField customerID;
    private JTextField sellerID;
    private JButton logInAsCustomerButton;
    private JButton logInAsSellerButton;
    //used to call methods in operations
    public Operations ope = new Operations();

    public login() {
        logInAsCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = (Connection) Connections.getConnection();
                    String c_id = customerID.getText();
                    boolean logged;
                    try{
                        logged = ope.c_login(c_id,con);
                        if(logged){
                            // log in to Customer UI
                            JOptionPane.showMessageDialog(null,"success");
                            JFrame frame = JFrames.get_frame();
                            frame.setTitle("Customer");
                            customer customer = new customer();
                            frame.setContentPane(customer.CustomerUI);
                            frame.pack();
                            frame.setVisible(true);
                            customer.setCustomerID(c_id);
                        }
                        else{
                            throw new SQLException();
                        }
                    }
                    catch (java.sql.SQLException e2){
                        JOptionPane.showMessageDialog(null,"Incorrect CustomerID");
                    }
                } catch (java.sql.SQLException e1) {
                    JOptionPane.showMessageDialog(null,"e1");
                }
            }
        });
        logInAsSellerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection con = (Connection) Connections.getConnection();
                    String s_id = sellerID.getText();
                    boolean logged;
                    try{
                        logged = ope.s_login(s_id,con);
                        if(logged){
                            // log in to Seller UI
                            JOptionPane.showMessageDialog(null,"success");
                            JFrame frame = JFrames.get_frame();
                            frame.setTitle("Seller");
                            seller Seller = new seller();
                            frame.setContentPane(Seller.SellerUI);
                            frame.pack();
                            frame.setVisible(true);
                            Seller.setSellerID(s_id);
                        }
                        else{
                            throw new SQLException();
                        }
                    }
                    catch (java.sql.SQLException e2){
                        JOptionPane.showMessageDialog(null,"Incorrect SellerID");
                    }
                } catch (java.sql.SQLException e1) {
                    JOptionPane.showMessageDialog(null,"e1");
                }

            }
        });
    }
}
