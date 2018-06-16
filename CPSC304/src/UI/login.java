import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login {
    public JPanel log_in;
    private JTextField customerID;
    private JTextField sellerID;
    private JButton logInAsCustomerButton;
    private JButton logInAsSellerButton;

    public login() {
        logInAsCustomerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        logInAsSellerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
