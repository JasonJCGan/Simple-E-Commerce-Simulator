import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class customer {
    private JTextArea text_proname;
    private JTextArea text_proprice;
    private JTextArea text_probrand;
    private JTextArea text_proID;
    private JTextArea text_sellerID;
    private JTextArea text_sellername;
    private JTextField price_low;
    private JTextField price_high;
    private JTextField search_proID;
    private JButton searchProductsByNameButton;
    private JButton searchProductsByPriceButton;
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

    public customer() {
        findCheapestProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        findMostExpensiveProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        searchProductsByNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        searchProductsByPriceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
