import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.awt.event.*;


/**
 * Main Window upon loading the program. Allows Sign Up & Login of Accounts in system.
*/
public class AdminGUI extends JFrame {

    private JPanel disPnl; // (display panel) entire panel on the left  
    private JPanel inptPnl; // (input panel) entire panel on the right 
    private JPanel innerPnl; // For username and password fields inside input panel
    private JPanel btnPnl; // (button panel) for buttons on bottom right

    private JTextField username; // accepts input for username
    private JPasswordField pass; // accepts input for password
    private JLabel userLbl, passLbl;
    private JLabel verifyLbl; // used for error msgs (set text to give feedback on login attempts)

    private JButton btnLogin, btnSignup, btnExit; // Login, Signup, Quit buttons

    // commonly used colors
    private Color mainBlue = new Color(10, 87, 162);
    private Color mainWhite = new Color(255, 255, 255);
    private Color lessBlue = new Color(43, 90, 137);
    private Color errorRed = new Color(239, 66, 66);    
    private Color successGreen = new Color(68, 218, 103);
    
    private WelcomeScreen thisWS; //previous screen
    private static AdminGUI thisAdminGUI; //current screen instance

    private Database db;

    public AdminGUI(WelcomeScreen ws) {    

        /**
         * This sets up attributes to ensure that the window instances are linked
         */        
        thisWS = ws;
        thisAdminGUI = this;

        /**
         * This turns off the welcome screen while the ResidentGUI screen is open.
         */        
        ws.setVisible(false);
       
        //Setting up program icon
        Image icon = ws.getIconImage();    
        setIconImage(icon);     
        
        //Labelling the frame/window 
        setTitle("QuickWash Administrator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Declaring layout for window
        //setLayout(new BorderLayout());




        //Extra frame/window settings
        pack();
        setSize(1024, 720);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

    }// public AdminGUI() end (constructor)


    //=========================================================//
    //=          BUTTON LISTENING FUNCTIONALITIES             =//
    //=========================================================//

    /**
     * This implements Login Button functionalities
     */
    private class LoginBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        }

    }

    /**
     * This implements Sign Up Button functionalities
     */
    private class SignUpBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //error message on invalid inputs
        }

    }

    /**
     * This exits the application
     */
    private class ExitBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }

    }


} // public class WelcomeScreen() end
