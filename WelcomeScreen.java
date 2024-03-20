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

    private static WelcomeScreen thisUserData; //current screen instance
    private ResidentGUI resGUI; //resident screen instance

    public WelcomeScreen() {    

        /*Setting up window*/
        setTitle("Welcome To QuickWash");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {        
            // Setting up program icon (make sure the 'pics' folder is downloaded and in the
            // active folder)
            ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "/pics/appicon.png");
            Image iconimg = icon.getImage();
            setIconImage(iconimg);
            System.out.println("QuickWash icon has loaded.");
        } catch (NullPointerException e) {
            System.out.println("QuickWash icon did not load.");
        }

        // Declaring layout for window
        setLayout(new BorderLayout());


        //==================================================//
        //=                                                =//
        //=               SETTING UP PANELS                =//
        //=                                                =//
        //==================================================//


        //==================================================//
        //=  STRUCTURING & CREATING DISPLAY PANEL TO LEFT  =//
        //==================================================//
        
        disPnl = new JPanel(new GridBagLayout());
        disPnl.setBackground(mainWhite);
        disPnl.setPreferredSize(new Dimension(700, 500));

        // creating quick wash logo, putting in try catch in case of retrieval error
        try {

            ImageIcon logo = new ImageIcon(System.getProperty("user.dir") + "/pics/quickwashlogo.png");
            JLabel picLbl = new JLabel(logo);
            disPnl.add(picLbl);
        } catch (Exception ioe) {
            System.out.println("QuickWash banner not found.");
        }



        //=================================================//
        //=  STRUCTURING & CREATING INPUT PANEL TO RIGHT  =//
        //=================================================//        
        
        inptPnl = new JPanel(new BorderLayout());
        inptPnl.setBorder(new EmptyBorder(0, 50, 100, 50));
        inptPnl.setBackground(mainBlue);
        //inptPnl.setBackground(new Color(43, 90, 137));
        inptPnl.setPreferredSize(new Dimension(307,720));

        // For username and password fields inside input panel
        innerPnl = new JPanel();
        //GridLayout(int rows, int columns, int hgap, int vgap)
        innerPnl.setLayout(new GridLayout(3, 1, 20, 30));
        innerPnl.setBorder(new EmptyBorder(120, 0, 0, 0));
        innerPnl.setOpaque(false);

        
        //FLATLAF Textfield stylings
        UIManager.put( "TextComponent.arc", 27);


        // CREATING AND ALLIGNING USERNAME INPUT FIELD //               
        //(innerpanel1) holds both username label and textfield for easy styling inside inner panel above
        JPanel i1Pnl = new JPanel(); i1Pnl.setLayout(new GridLayout(2, 1, 20, 10));
        i1Pnl.setOpaque(false);

        userLbl = new JLabel(); //Username Label
        try {
            //creating user icon, putting in try catch in case of retrieval error
            userLbl.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/usericon.png").getImage().getScaledInstance(32, 32, Image.SCALE_AREA_AVERAGING)));
        } catch (Exception ioe) {
            System.out.println("Username icon not found.");
        }                
        userLbl.setText("Username:");
        userLbl.setForeground(Color.WHITE);
        userLbl.setFont(new Font(userLbl.getFont().getFontName(), Font.BOLD, 15));
        username = new JTextField(10); //Username Field
        // adding the label and textfield to innerpanel1
        i1Pnl.add(userLbl);
        i1Pnl.add(username);
        innerPnl.add(i1Pnl, BorderLayout.EAST); //adding innerpanel1 to innner panel
        

        // CREATING AND ALLIGNING PASSWORD INPUT FIELD //   
        //(innerpanel2) holds both password label and textfield for easy styling inside inner panel above
        JPanel i2Pnl = new JPanel();
        i2Pnl.setLayout(new GridLayout(2, 1, 20, 10));
        i2Pnl.setOpaque(false);
        
        passLbl = new JLabel(); //Password Label
        try {
            //creating password icon, putting in try catch in case of retrieval error
            passLbl.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/passicon.png").getImage().getScaledInstance(32, 32, Image.SCALE_AREA_AVERAGING)));
        } catch (Exception ioe) {
            System.out.println("Password icon not found.");
        }      
        passLbl.setText("Password:");
        passLbl.setForeground(Color.WHITE);        
        passLbl.setFont(new Font(passLbl.getFont().getFontName(), Font.BOLD, 15));
        pass = new JPasswordField(10); //Username Field        
        // adding the label and textfield to innerpanel2
        i2Pnl.add(passLbl);
        i2Pnl.add(pass);
        innerPnl.add(i2Pnl, BorderLayout.EAST); //adding innerpanel2 to innner panel


        // CREATING LABEL FOR DISPLAYING ERROR/SUCCESS MESSAGES //   
        //(innerpanel3) holds error message label
        JPanel i3Pnl = new JPanel();
        i3Pnl.setOpaque(false);

        verifyLbl = new JLabel(); // error message label
        //creating either error or success icon, putting in try catch in case of retrieval error
        // this will have to be implemented in login listener to select the right icon and style
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
        i3Pnl.add(verifyLbl); // adding the label to innerpanel3
        innerPnl.add(i3Pnl, BorderLayout.EAST); //adding innerpanel3 to innner panel

        //adding the inner panel itself into the input panel
        inptPnl.add(innerPnl, BorderLayout.CENTER);


        //===============================================//
        //=    CREATING THE BUTTONS AT BOTTOM RIGHT     =//
        //===============================================//

        //FLATLAF Button stylings
        UIManager.put( "Button.arc", 27);

        
        //(button panel) For holding buttons inside input panel
        btnPnl = new JPanel();
        btnPnl.setLayout(new GridLayout(3, 1, 20, 28));
        btnPnl.setBorder(new EmptyBorder(10, 0, 0, 0));
        btnPnl.setBackground(mainBlue);
        btnPnl.setOpaque(false);

        // CREATING AND ALLIGNING LOGIN BUTTON // 
        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font(btnLogin.getFont().getFontName(), Font.BOLD, 16));
        btnLogin.setForeground(lessBlue);
        btnLogin.setPreferredSize(new Dimension(40, 32));
        btnLogin.addActionListener(new LoginBtnListener());

        // CREATING AND ALLIGNING SIGN UP BUTTON // 
        btnSignup = new JButton("Create Account");
        btnSignup.setFont(new Font(btnSignup.getFont().getFontName(), Font.BOLD, 16));
        btnSignup.setForeground(lessBlue);
        btnSignup.addActionListener(new SignUpBtnListener());
        
        // CREATING AND ALLIGNING QUIT BUTTON // 
        btnExit = new JButton("Quit");
        btnExit.setFont(new Font(btnExit.getFont().getFontName(), Font.BOLD, 16));
        btnExit.setForeground(lessBlue);
        btnExit.addActionListener(new ExitBtnListener());
        
        //adding the button panel itself into the input panel
        inptPnl.add(btnPnl, BorderLayout.SOUTH);

        // Adding buttons to button panel
        btnPnl.add(btnLogin);
        btnPnl.add(btnSignup);
        btnPnl.add(btnExit);


        //==============================================//
        //=        ADDING MAIN PANELS TO FRAME         =//
        //==============================================//
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
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    //initializing FLATLAF
                    UIManager.setLookAndFeel( new FlatLightLaf() );
                } catch( Exception ex ) {
                    System.err.println( "Failed to initialize LaF" );
                }

                // Creating welcome screen
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
