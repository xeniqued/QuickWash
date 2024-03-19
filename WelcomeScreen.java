import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * Main Window upon loading the program. Allows Sign Up & Login of Accounts in system.
*/

public class WelcomeScreen extends JFrame {

    private JPanel disPnl;
    private JPanel btnPnl;
    private JPanel inptPnl;
    private JPanel innerPnl;

    private JTextField username;
    private JPasswordField pass;
    private JLabel userLbl;
    private JLabel passLbl;
    private JLabel verifyLbl;

    private JButton btnLogin;
    private JButton btnSignup;
    private JButton btnExit;

    private Color mainBlue = new Color(10, 87, 162);
    private Color mainWhite = new Color(255, 255, 255);
    private Color lessBlue = new Color(43, 90, 137);
    private Color errorRed = new Color(239, 66, 66);    
    private Color successGreen = new Color(68, 218, 103);

    private static ImageIcon icon;
    private static Image iconimg;
    private static ImageIcon banner;
    private static WelcomeScreen thisUserData;
    private ResidentGUI resGUI;

    public WelcomeScreen() {
        
        /*Setting a up window*/
        setTitle("Welcome To QuickWash");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {        
            // Setting up program icon (make sure the 'pics' folder is downloaded and in the
            // active folder)
            icon = new ImageIcon(System.getProperty("user.dir") + "/pics/appicon.png");
            iconimg = icon.getImage();
            setIconImage(iconimg);
            System.out.println("QuickWash icon has loaded.");
        } catch (NullPointerException e) {
            System.out.println("QuickWash icon did not load.");
        }

        // Declaring layout
        setLayout(new BorderLayout());

        // ==================================================//
        // =               SETTING UP PANELS                =//
        // ==================================================//

        disPnl = new JPanel(new GridBagLayout());

        try {
            banner = new ImageIcon(System.getProperty("user.dir") + "/pics/banner.png");
            JLabel picLbl = new JLabel(banner);
            disPnl.add(picLbl);
        } catch (Exception ioe) {
            System.out.println("QuickWash banner not found.");
        }

        disPnl.setBackground(mainWhite);
        disPnl.setPreferredSize(new Dimension(700, 500));

        // ==================================================//
        // =   STRUCTURING & CREATING INPUT PANEL TO RIGHT  =//
        // ==================================================//
        
        // For login/register text fields
        inptPnl = new JPanel(new BorderLayout());
        //public MatteBorder(int top, int left, int bottom, int right, Color matteColor)
        //BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLUE)
        inptPnl.setBorder(new EmptyBorder(0, 50, 100, 50));
        inptPnl.setBackground(mainBlue);
        //inptPnl.setBackground(new Color(43, 90, 137));
        inptPnl.setPreferredSize(new Dimension(307,720));

        // Creating and alligning text input fields
        innerPnl = new JPanel();
        //GridLayout(int rows, int columns, int hgap, int vgap)
        innerPnl.setLayout(new GridLayout(3, 1, 20, 30));
        innerPnl.setBorder(new EmptyBorder(120, 0, 0, 0));
        innerPnl.setOpaque(false);

        
        //FLATLAF Textfield stylings
        UIManager.put( "TextComponent.arc", 27);


        JPanel i0Pnl = new JPanel();
        i0Pnl.setOpaque(false);
        JLabel iconLbl = new JLabel();
        try {
            iconLbl.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/banner.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
        } catch (Exception ioe) {
            System.out.println("App Icon not found.");
        }


        JPanel i1Pnl = new JPanel();
        //GridLayout(int rows, int columns, int hgap, int vgap)
        i1Pnl.setLayout(new GridLayout(2, 1, 20, 10));
        i1Pnl.setOpaque(false);
        userLbl = new JLabel();
        try {
            userLbl.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/usericon.png").getImage().getScaledInstance(32, 32, Image.SCALE_AREA_AVERAGING)));
        } catch (Exception ioe) {
            System.out.println("Username icon not found.");
        }        
        //userLbl.setText("<html>" + "<B>" + "Username:" + "</B>" + "</html>");
        userLbl.setText("Username:");
        userLbl.setForeground(Color.WHITE);
        userLbl.setFont(new Font(userLbl.getFont().getFontName(), Font.BOLD, 15));
        username = new JTextField(10);
        i1Pnl.add(userLbl);
        i1Pnl.add(username);
        innerPnl.add(i1Pnl, BorderLayout.EAST);
        

        JPanel i2Pnl = new JPanel();
        //GridLayout(int rows, int columns, int hgap, int vgap)
        i2Pnl.setLayout(new GridLayout(2, 1, 20, 10));
        i2Pnl.setOpaque(false);
        passLbl = new JLabel();
        try {
            passLbl.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/passicon.png").getImage().getScaledInstance(32, 32, Image.SCALE_AREA_AVERAGING)));
        } catch (Exception ioe) {
            System.out.println("Password icon not found.");
        }      
        passLbl.setText("Password:");
        passLbl.setForeground(Color.WHITE);        
        passLbl.setFont(new Font(passLbl.getFont().getFontName(), Font.BOLD, 15));
        pass = new JPasswordField(10);
        i2Pnl.add(passLbl);
        i2Pnl.add(pass);
        innerPnl.add(i2Pnl, BorderLayout.EAST);


        JPanel i3Pnl = new JPanel();
        i3Pnl.setOpaque(false);
        verifyLbl = new JLabel();  
        try {
            //error icon
            //verifyLbl.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/erroricon.png").getImage().getScaledInstance(32, 32, Image.SCALE_AREA_AVERAGING)));
            //success icon
            verifyLbl.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/successicon.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
        } catch (Exception ioe) {
            System.out.println("Error/Success icon not found.");
        }      
        //verifyLbl.setText("<html>" +
                        //"Incorrect Username <br> or Password. <br> Please Try Again." +
                        //"</html>"); //error message
        verifyLbl.setText("<html>" + "Login Successful." + "</div></html>"); //success message
        //verifyLbl.setForeground(errorRed);  //error red    
        verifyLbl.setForeground(successGreen);  //success green    
        verifyLbl.setFont(new Font(passLbl.getFont().getFontName(), Font.BOLD, 15));
        i3Pnl.add(verifyLbl);
        innerPnl.add(i3Pnl, BorderLayout.EAST);

        inptPnl.add(innerPnl, BorderLayout.CENTER);

        // ========================================//
        // =    CREATING THE BUTTONS AT SIDE      =//
        // ========================================//
        UIManager.put( "Button.arc", 27);

        btnPnl = new JPanel();
        btnPnl.setLayout(new GridLayout(3, 1, 20, 28));
        btnPnl.setBorder(new EmptyBorder(10, 0, 0, 0));
        btnPnl.setBackground(mainBlue);
        btnPnl.setOpaque(false);

        /*Button set up*/
        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font(btnLogin.getFont().getFontName(), Font.BOLD, 16));
        btnLogin.setForeground(lessBlue);
        btnLogin.setPreferredSize(new Dimension(40, 32));
        btnLogin.addActionListener(new LoginBtnListener());

        btnSignup = new JButton("Create Account");
        btnSignup.setFont(new Font(btnSignup.getFont().getFontName(), Font.BOLD, 16));
        btnSignup.setForeground(lessBlue);
        btnSignup.addActionListener(new SignUpBtnListener());

        btnExit = new JButton("Quit");
        btnExit.setFont(new Font(btnExit.getFont().getFontName(), Font.BOLD, 16));
        btnExit.setForeground(lessBlue);
        btnExit.addActionListener(new ExitBtnListener());

        inptPnl.add(btnPnl, BorderLayout.SOUTH);

        // Adding to button panel
        btnPnl.add(btnLogin);
        btnPnl.add(btnSignup);
        btnPnl.add(btnExit);

        // ==============================================//
        // =        ADDING MAIN PANELS TO FRAME         =//
        // ==============================================//
        add(disPnl, BorderLayout.WEST);
        add(inptPnl, BorderLayout.EAST);

        //Extra frame/window settings
        pack();
        setSize(1024, 720);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

    }// public WelcomeScreen() end (constructor)


    public static void main(String[] args) {      
        
        // Creating welcome screen
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel( new FlatLightLaf() );
                } catch( Exception ex ) {
                    System.err.println( "Failed to initialize LaF" );
                }

                thisUserData = new WelcomeScreen();
            }
        });

    }

    //=========================================================//
    //=          BUTTON LISTENING FUNCTIONALITIES             =//
    //=========================================================//

    /**
     * This implements Login Button functionalities
     */
    private class LoginBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //error message on invalid inputs
            
            resGUI = new ResidentGUI(thisUserData);
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
