import javax.swing.*;

public class JFrame_main {
    public static void main(String[] args) {
        javax.swing.JFrame frame = JFrames.get_frame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Login");
        frame.setContentPane(new login().log_in);
        frame.pack();
        frame.setVisible(true);



    }
}
