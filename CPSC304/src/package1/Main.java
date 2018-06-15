package package1;
 
import java.awt.Dimension;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Main extends JFrame {
	static String username = "ora_s1h0b";
	static String password = "a33731143";
	 // command line reader
    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.                                                                                                                     in));

    private static Connection con;

    // user is allowed 3 login attempts
    private int loginAttempts = 0;

    // components of the login window
    private static JTextField usernameField;
    private static JPasswordField passwordField;
    private static JFrame mainFrame;
    
    
    
	public static void main(String[] args) throws Exception {
		//starts connection
		connect(username, password);

		selectUser();
		System.out.println("Welcome to the Market");
	}
	



	private static void selectUser() {
// frame name
		 mainFrame = new JFrame("User Selection");

//	      JLabel usernameLabel = new JLabel("Input1: ");
//	      JLabel passwordLabel = new JLabel("Input2: ");

	      usernameField = new JTextField(10);
	      passwordField = new JPasswordField(10);
	      passwordField.setEchoChar('*');
// user types
	      JButton vipButton = new JButton("VIP Customer");
	      JButton regButton = new JButton("Regular Customer");
	      JButton sellerButton = new JButton("Seller");

	      JPanel contentPane = new JPanel();
	      mainFrame.setContentPane(contentPane);


	      // layout components using the GridBag layout manager

	      GridBagLayout gb = new GridBagLayout();
	      GridBagConstraints c = new GridBagConstraints();

	      contentPane.setLayout(gb);
	      contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	      
	      // place buttons	     
	      c.gridwidth = GridBagConstraints.REMAINDER;
	      c.insets = new Insets(5, 10, 10, 10);
	      c.anchor = GridBagConstraints.CENTER;
	      gb.setConstraints(vipButton, c);
	      contentPane.add(vipButton);
	      gb.setConstraints(regButton, c);
	      contentPane.add(regButton);
	      gb.setConstraints(sellerButton, c);
	      contentPane.add(sellerButton);

	      // register password field and OK button with action event handler
//	      passwordField.addActionListener(this);
//	      loginButton.addActionListener(this);

	      // anonymous inner class for closing the window
	      mainFrame.addWindowListener(new WindowAdapter()
	      {
	        public void windowClosing(WindowEvent e)
	        {
	          System.exit(0);
	        }
	      });

	      // size the window to obtain a best fit for the components
	      mainFrame.pack();

	      // center the frame
	      Dimension d = mainFrame.getToolkit().getScreenSize();
	      Rectangle r = mainFrame.getBounds();
	      mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

	      // make the window visible
	      mainFrame.setVisible(true);

	      // place the cursor in the text field for the username
	      usernameField.requestFocus();	
	}


	/*
     * connects to Oracle database named ug using user supplied username and pas                                                                                                                     sword
     */
    private static boolean connect(String username, String password)
    {
      String connectURL = "jdbc:oracle:thin:@localhost:1522:ug";
      try {
        // Load the Oracle JDBC driver
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        con = DriverManager.getConnection(connectURL,username,password);

        System.out.println("\nConnected to Oracle!");
        return true;
      }
      catch (SQLException ex) {
        System.out.println("Message: " + ex.getMessage());
        return false;
      }
    }

}
