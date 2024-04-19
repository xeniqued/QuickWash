import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;

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

    private JButton btnLogin, btnSignup, btnForgotPass, btnShowPass, btnExit; // Login, Signup, show password, Quit buttons

    // Forgot Password Components
    private JTextField forgID, forgEmail;
    private JPasswordField forgNewPass, forgConfPass;
    private JLabel  forgIDLbl, forgEmailLbl, forgNewPassLbl, forgConfPassLbl;
    //ShowPassBtns are the show password toggle buttons for new and confirm passwords
    private JButton btnShowNPass, btnShowCPass, btnSaveNewPass, btnCancelPass;

    // commonly used colors
    private Color mainBlue = new Color(10, 87, 162);
    private Color newBlue = new Color(71, 149, 228);
    private Color mainWhite = new Color(255, 255, 255);
    private Color lessBlue = new Color(43, 90, 137);
    private Color errorRed = new Color(239, 66, 66);  
    private Color successGreen = new Color(68, 218, 103);
    private Color waitBlue = new Color(56, 182, 255);

    private static WelcomeScreen thisUserData; //current screen instance
    private ResidentGUI resGUI; //resident screen instance
    private StaffGUI staffGUI; //staff screen instance
    private AdminGUI adminGUI; //staff screen instance

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
        
        //FLATLAF Button stylings
        UIManager.put( "Button.arc", 27);
        
        inptPnl = new JPanel(new BorderLayout());
        inptPnl.setBackground(mainBlue);
        //inptPnl.setBackground(new Color(43, 90, 137));
        inptPnl.setPreferredSize(new Dimension(307,720));

        // For username and password fields inside input panel
        innerPnl = new JPanel();
        
        btnPnl = new JPanel();

        //FLATLAF Textfield stylings
        UIManager.put( "TextComponent.arc", 27);
        
        setLoginSignupFields();

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
        
        //Check Connection
        if(!Database.isConnected()){
            connectionErrorPanel();
        };

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
            if(!Database.isConnected()){
                connectionErrorPanel();
                System.exit(0);
            }else{           

                String txtName = username.getText();                            
                String txtPass = String.valueOf(pass.getPassword());
                
                try {
                    setWaitingMessage("Verifying...");
                    verifyLbl.paintImmediately(verifyLbl.getVisibleRect());
                    
                    setNotification("Beginning Verification.", null);

                    if ((txtName.length() == 0 || txtPass.length() == 0)) {
                        username.setText("");
                        pass.setText("");
                        setErrorMessage("Please fill out all fields.");
                        
                    } else if (Database.selectUserById(Integer.parseInt(txtName)) != null){
                            
            
                            User user = Database.selectUserById(Integer.parseInt(txtName));
                            String dbName = user.getName();
                            String dbPassword = user.getPassword();
                                    
                            //Error message on invalid inputs
                            if(txtPass.equals(dbPassword)){                            
                                setSuccessMessage("Login Successful.");
                                verifyLbl.paintImmediately(verifyLbl.getVisibleRect());

                                setNotification("Launching QuickWash.", null);

                                username.setText("");
                                pass.setText("");

                                //If password is correct, open one of below screens
                                if(user.getType_user().equals("Resident")){
                                    resGUI = new ResidentGUI(thisUserData, txtName, dbName);
                                } else if(user.getType_user().equals("Staff")){
                                staffGUI = new StaffGUI(thisUserData, txtName, dbName); 
                                } else if(user.getType_user().equals("admin")){
                                    adminGUI = new AdminGUI(thisUserData); 
                                }                                                        

                            } else {
                                pass.setText("");
                                setErrorMessage("Incorrect Password. <br> Please Try Again.");
                            }

                    } else {                    
                        username.setText("");
                        pass.setText("");

                        setErrorMessage("Username Not Found. <br> Please Try Again.");
                    }
                } catch (Exception ex) {
                    username.setText("");
                    pass.setText("");
                    
                    setErrorMessage("Invalid Username. <br> Please Try Again.");
                }
            }
            
        }

    }

    /**
     * This implements Sign Up Button functionalities
     */
    private class SignUpBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(!Database.isConnected()){
                connectionErrorPanel();
                System.exit(0);
            }else{
                new UserCreationUI(thisUserData).setVisible(true);
            }
        }

    }

    /**
     * This implements Sign Up Button functionalities
     */
    private class ForgotBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(!Database.isConnected()){
                connectionErrorPanel();
                System.exit(0);
            }else{
                //setNotification("Opening Password Reset Form.", "Password Reset");
                setForgotPasswordFields();
            }
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

    /**
     * This toggles the hide password for the login password
     */
    private class ShowPassListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (pass.getEchoChar() != '\u0000') {
                pass.setEchoChar('\u0000');
                btnShowPass.setBackground(newBlue);
            } else {
                pass.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));
                btnShowPass.setBackground(mainBlue);
            }
        }
    }

    /**
     * This toggles the hide password for the new password (reset)
     */
    private class ShowNPassListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (forgNewPass.getEchoChar() != '\u0000') {
                forgNewPass.setEchoChar('\u0000');
                btnShowNPass.setBackground(newBlue);
            } else {
                forgNewPass.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));
                btnShowNPass.setBackground(mainBlue);
            }
        }
    }

    /**
     * This toggles the hide password for the confirmed password (reset)
     */
    private class ShowCPassListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (forgConfPass.getEchoChar() != '\u0000') {
                forgConfPass.setEchoChar('\u0000');
                btnShowCPass.setBackground(newBlue);
            } else {
                forgConfPass.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));
                btnShowCPass.setBackground(mainBlue);
            }
        }
    }
    
    /**
     * This resets the password for a given user
     */
    private class SavePassBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(!Database.isConnected()){
                connectionErrorPanel();
                System.exit(0);
                }else{
                //setNotification("This works", null);

                String ID = forgID.getText();     
                String email = forgEmail.getText();     

                char [] newPassArray = forgNewPass.getPassword();
                char [] confirmPassArray = forgConfPass.getPassword();        
                String newPass = new String(newPassArray);
                String confirmPass = new String(confirmPassArray);
                int idTest = -1;

                // Blank field validation
                if ((email.isEmpty() || ID.isEmpty() || newPass.isEmpty()|| confirmPass.isEmpty())){
                    setErrorMessage("Please fill out all fields.");
                    return;
                }
                // Email validation
                else if (!email.matches("^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*$")) {
                    setErrorMessage("Invalid Email Address. <br> Please Try Again.");
                    return;
                }
                // Checking if passwords match
                else if(!newPass.equals(confirmPass)){                
                    setErrorMessage("Passwords Don't Match. <br> Please Try Again.");
                    return;
                }
                // ID number validation
                try {
                    idTest = Integer.parseInt(ID);
                } catch (NumberFormatException ex){
                    setErrorMessage("Invalid Username. <br> Please Try Again.");
                    return;
                }


                // Display Verification Message
                setWaitingMessage("Verifying...");
                verifyLbl.paintImmediately(verifyLbl.getVisibleRect());            
                setNotification("Beginning Verification.", null); 
                
                // Attempting to update password
                if (Database.selectUserById(idTest) != null){
                    User user = Database.selectUserById(idTest);
                    if (user.getEmail().equals(email)){
                        Database.updatePassword(idTest, newPass); 

                        forgID.setText("");
                        forgEmail.setText("");
                        forgNewPass.setText("");
                        forgConfPass.setText("");

                        setSuccessMessage("Password Reset. <br> Please Login.");   
                    } else {
                        forgEmail.setText("");
                        setErrorMessage("Incorrect Email. <br> Please Try Again.");
                    }
                } else {                
                    forgID.setText("");
                    forgEmail.setText("");
                    forgNewPass.setText("");
                    forgConfPass.setText("");
                    
                    setErrorMessage("Username Not Found. <br> Please Try Again.");
                }
            }

        }

    }
    
    private class CancelPassBtnListener implements ActionListener{
        public void actionPerformed (ActionEvent e){
            setLoginSignupFields();
        }
    }



    //=========================================================//
    //=                   FUNCTIONALITIES                     =//
    //=========================================================//

    // Function to set notification message
    public void setNotification(String msg, String title) {        
        JOptionPane.showMessageDialog(null, msg, 
        title, JOptionPane.INFORMATION_MESSAGE);
    }

    // Function to toggle hiding message
    public void hideVerifyMessage(Boolean hide) {
        verifyLbl.setVisible(hide);
    }

    // Function to set wait message
    public void setWaitingMessage(String waitMsg) {
        hideVerifyMessage(true);
        try {
            //wait icon
            verifyLbl.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/waiticon.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
        } catch (Exception ioe) {
            System.out.println("Wait icon not found.");
        }  

        verifyLbl.setText("<html>" + waitMsg + "</html>"); //wait message
        verifyLbl.setForeground(waitBlue);  //wait blue 
    }
    
    // Function to set error message
    public void setErrorMessage(String errorMsg) {
        hideVerifyMessage(true);
        try {
            //error icon
            verifyLbl.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/erroricon.png").getImage().getScaledInstance(32, 32, Image.SCALE_AREA_AVERAGING)));
        } catch (Exception ioe) {
            System.out.println("Error icon not found.");
        }     

        verifyLbl.setText("<html>" + errorMsg + "</html>"); //error message
        verifyLbl.setForeground(errorRed);  //error red
    }

    // Function to set error message
    public void setSuccessMessage(String successMsg) {
        hideVerifyMessage(true);
        try {
            //success icon
            verifyLbl.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/successicon.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
        } catch (Exception ioe) {
            System.out.println("Success icon not found.");
        }  

        verifyLbl.setText("<html>" + successMsg + "</html>"); //success message
        verifyLbl.setForeground(successGreen);  //success green   
    }


    private  void connectionErrorPanel() {
        JOptionPane.showMessageDialog(null, "Check your connection and Restart application", "Error", JOptionPane.ERROR_MESSAGE);
    }




    
    
    //==============================================================//
    //=                                                            =//
    //=     TOGGLE FORGOT PASSWORD AND LOGIN FIELDS FUNCTIONS      =//
    //=                                                            =//
    //==============================================================//


    //===============================================//
    //=        LOGIN & SIGNUP PANEL DISPLAY         =//
    //===============================================//

    public void setLoginSignupFields(){      
        //Removing old components to allow a rewrite
        inptPnl.removeAll();
        innerPnl.removeAll();
        btnPnl.removeAll();

        
        inptPnl.setBorder(new EmptyBorder(0, 50, 65, 50));

        //GridLayout(int rows, int columns, int hgap, int vgap)
        innerPnl.setLayout(new GridLayout(3, 1, 20, 30));
        innerPnl.setBorder(new EmptyBorder(130, 0, 0, 0));
        innerPnl.setOpaque(false);



        // CREATING AND ALLIGNING USERNAME INPUT FIELD //               
        //(innerpanel1) holds both username label and textfield for easy styling inside inner panel above
        JPanel i1Pnl = new JPanel(); i1Pnl.setLayout(new GridLayout(2, 1, 20, 10));
        i1Pnl.setOpaque(false);

        userLbl = new JLabel(); //Username Label
        try {
            //creating user icon, putting in try catch in case of retrieval error
            userLbl.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/usericon.png").getImage().getScaledInstance(31, 31, Image.SCALE_AREA_AVERAGING)));
        } catch (Exception ioe) {
            System.out.println("Username icon not found.");
        }                
        userLbl.setText("Username (ID #):");
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
            passLbl.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/passicon.png").getImage().getScaledInstance(31, 31, Image.SCALE_AREA_AVERAGING)));
        } catch (Exception ioe) {
            System.out.println("Password icon not found.");
        }      
        passLbl.setText("Password:");
        passLbl.setForeground(Color.WHITE);        
        passLbl.setFont(new Font(passLbl.getFont().getFontName(), Font.BOLD, 15));
        
        JPanel i2innerPnl = new JPanel();
        //i2innerPnl.setLayout(new BorderLayout());
        i2innerPnl.setLayout(new GridBagLayout());
        i2innerPnl.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;

        pass = new JPasswordField(11); //Username Field    
        pass.setHorizontalAlignment(SwingConstants.LEFT);
        ImageIcon ShowPassIcon = null;      
        try {
            ShowPassIcon = (new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/showpassicon.png").getImage().getScaledInstance(23, 23, Image.SCALE_SMOOTH)));
        } catch (Exception ioe) {
            System.out.println("Create icon not found.");
        }      
        btnShowPass = new JButton(ShowPassIcon);
        btnShowPass.setBackground(mainBlue);
        //btnShowPass.setPreferredSize(new Dimension(25, 30));
        btnShowPass.setHorizontalAlignment(SwingConstants.RIGHT);
        btnShowPass.setBorderPainted(false);
        btnShowPass.addActionListener(new ShowPassListener());
       
        // insets for x component
        //gbc.insets = new Insets(0, 0, 0, 5);
        gbc.gridx = 0; // column   
        gbc.weightx = 0.2;     
        gbc.gridy = 0; // row 0
        gbc.ipadx = 158; // increases components width by x pixels      
        gbc.ipady = i2innerPnl.getHeight() + 3; // increases components height by x pixels
        i2innerPnl.add(pass, gbc);
        
        // insets for x component
        //gbc.insets = new Insets(0, 0, 0, 0);
        gbc.weightx = 0.0;     
        gbc.gridx = 1; // column 1        
        gbc.ipadx = 1; // increases components width by x pixels        
        i2innerPnl.add(btnShowPass, gbc);
        
        // adding the label and textfield to innerpanel2
        i2Pnl.add(passLbl);
        i2Pnl.add(i2innerPnl);
        innerPnl.add(i2Pnl, BorderLayout.EAST); //adding innerpanel2 to innner panel


        // CREATING LABEL FOR DISPLAYING ERROR/SUCCESS MESSAGES //   
        //(innerpanel3) holds error message label
        JPanel i3Pnl = new JPanel();
        i3Pnl.setOpaque(false);

        verifyLbl = new JLabel(""); // error message label
        verifyLbl.setFont(new Font(verifyLbl.getFont().getFontName(), Font.BOLD, 15));
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
        btnPnl.setLayout(new GridLayout(4, 1, 20, 20));
        btnPnl.setBorder(new EmptyBorder(10, 0, 0, 0));
        btnPnl.setBackground(mainBlue);
        btnPnl.setOpaque(false);

        // CREATING AND ALLIGNING LOGIN BUTTON // 
        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font(btnLogin.getFont().getFontName(), Font.BOLD, 16));
        btnLogin.setForeground(lessBlue);
        btnLogin.setPreferredSize(new Dimension(40, 32));
        btnLogin.addActionListener(new LoginBtnListener());

        // CREATING AND ALLIGNING FORGOT PASSWORD BUTTON // 
        ImageIcon ForgPassIcon = null;      
        try {
            ForgPassIcon = (new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/forgoticon.png").getImage().getScaledInstance(23, 23, Image.SCALE_SMOOTH)));
        } catch (Exception ioe) {
            System.out.println("Create icon not found.");
        }      
        btnForgotPass = new JButton(ForgPassIcon);
        btnForgotPass.setText("Forgot Password?");
        btnForgotPass.setFont(new Font(btnForgotPass.getFont().getFontName(), Font.PLAIN, 16));
        btnForgotPass.setForeground(mainWhite);
        btnForgotPass.setBackground(mainBlue);
        btnForgotPass.setBorderPainted(false);
        btnForgotPass.setContentAreaFilled(false);
        btnForgotPass.setFocusPainted(false);
        btnForgotPass.addActionListener(new ForgotBtnListener());

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
        btnPnl.add(btnForgotPass);
        btnPnl.add(btnSignup);
        btnPnl.add(btnExit);
        
        //Update panels
        inptPnl.revalidate();
        inptPnl.repaint();
        innerPnl.revalidate();
        innerPnl.repaint();
        btnPnl.revalidate();
        btnPnl.repaint();
    }




    //===============================================//
    //=       FORGOT PASSWORD PANEL DISPLAY         =//
    //===============================================//

    public void setForgotPasswordFields(){
        //Removing old components to allow a rewrite
        inptPnl.removeAll();
        innerPnl.removeAll();
        btnPnl.removeAll();

        
        inptPnl.setBorder(new EmptyBorder(0, 50, 10, 50));

        //GridLayout(int rows, int columns, int hgap, int vgap)
        innerPnl.setLayout(new GridLayout(5, 1, 20, 20));
        innerPnl.setBorder(new EmptyBorder(80, 0, 0, 0));
        innerPnl.setOpaque(false);

        
        // CREATING AND ALLIGNING USERNAME INPUT FIELD //               
        //(innerpanel1) holds both id label and textfield for easy styling inside inner panel above
        JPanel i1Pnl = new JPanel(); 
        i1Pnl.setLayout(new GridLayout(2, 1, 20, 10));
        i1Pnl.setOpaque(false);

        forgIDLbl = new JLabel(); //Username Label
        try {
            //creating user icon, putting in try catch in case of retrieval error
            forgIDLbl.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/usericon.png").getImage().getScaledInstance(29, 29, Image.SCALE_AREA_AVERAGING)));
        } catch (Exception ioe) {
            System.out.println("Username icon not found.");
        }                
        forgIDLbl.setText("Username (ID #):");
        forgIDLbl.setForeground(Color.WHITE);
        forgIDLbl.setFont(new Font(forgIDLbl.getFont().getFontName(), Font.BOLD, 15));
        forgID = new JTextField(10); //Username Field
        // adding the label and textfield to innerpanel1
        i1Pnl.add(forgIDLbl);
        i1Pnl.add(forgID);
        innerPnl.add(i1Pnl, BorderLayout.EAST); //adding innerpanel1 to innner panel
        

        // CREATING AND ALLIGNING PASSWORD INPUT FIELD //   
        //(innerpanel2) holds both email label and textfield for easy styling inside inner panel above
        JPanel i2Pnl = new JPanel();        
        i2Pnl.setLayout(new GridLayout(2, 1, 20, 10));
        i2Pnl.setOpaque(false);
        
        forgEmailLbl = new JLabel(); //Email Label
        try {
            //creating email icon, putting in try catch in case of retrieval error
            forgEmailLbl.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/emailicon.png").getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING)));
        } catch (Exception ioe) {
            System.out.println("Email icon not found.");
        }      
        forgEmailLbl.setText("Email:");
        forgEmailLbl.setForeground(Color.WHITE);        
        forgEmailLbl.setFont(new Font(forgEmailLbl.getFont().getFontName(), Font.BOLD, 15));
        forgEmail = new JTextField(10); //Email Field        
        // adding the label and textfield to innerpanel2
        i2Pnl.add(forgEmailLbl);
        i2Pnl.add(forgEmail);
        innerPnl.add(i2Pnl, BorderLayout.EAST); //adding innerpanel2 to innner panel

        

        // CREATING LABEL FOR DISPLAYING ERROR/SUCCESS MESSAGES //   
        //(innerpanel3) holds error message label
        JPanel i3Pnl = new JPanel();
        i3Pnl.setLayout(new GridLayout(2, 1, 20, 10));
        i3Pnl.setOpaque(false);
        
        forgNewPassLbl = new JLabel(); //Password Label
        try {
            //creating password icon, putting in try catch in case of retrieval error
            forgNewPassLbl.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/passicon.png").getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING)));
        } catch (Exception ioe) {
            System.out.println("Password icon not found.");
        }      
        forgNewPassLbl.setText("New Password:");
        forgNewPassLbl.setForeground(Color.WHITE);        
        forgNewPassLbl.setFont(new Font(forgNewPassLbl.getFont().getFontName(), Font.BOLD, 15));
        
        JPanel i3innerPnl = new JPanel();
        //i3innerPnl.setLayout(new BorderLayout());
        i3innerPnl.setLayout(new GridBagLayout());
        i3innerPnl.setOpaque(false);

        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.anchor = GridBagConstraints.WEST; 

        forgNewPass = new JPasswordField(11); //New Password Field           
        forgNewPass.setHorizontalAlignment(SwingConstants.LEFT);
        ImageIcon ShowPassIcon1 = null;      
        try {
            ShowPassIcon1 = (new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/showpassicon.png").getImage().getScaledInstance(23, 22, Image.SCALE_SMOOTH)));
        } catch (Exception ioe) {
            System.out.println("Show pass icon not found.");
        }      
        //
        btnShowNPass = new JButton(ShowPassIcon1);          
        btnShowNPass.setHorizontalAlignment(SwingConstants.RIGHT);
        btnShowNPass.setBackground(mainBlue);
        //btnShowNPass.setPreferredSize(new Dimension(25, 30));
        btnShowNPass.setBorderPainted(false);
        btnShowNPass.addActionListener(new ShowNPassListener());

        // insets for x component
        //gbc.insets = new Insets(0, 0, 0, 5);
        gbc1.gridx = 0; // column   
        gbc1.weightx = 0.2;     
        gbc1.gridy = 0; // row 0
        gbc1.ipadx = 158; // increases components width by x pixels      
        gbc1.ipady = i3innerPnl.getHeight() + 3; // increases components height by x pixels
        i3innerPnl.add(forgNewPass, gbc1);

        // insets for x component
        //gbc.insets = new Insets(0, 0, 0, 0);
        gbc1.weightx = 0.0;     
        gbc1.gridx = 1; // column 1        
        gbc1.ipadx = 1; // increases components width by x pixels   
        i3innerPnl.add(btnShowNPass, gbc1);

        // adding the label and textfield to innerpanel2
        i3Pnl.add(forgNewPassLbl);
        i3Pnl.add(i3innerPnl);
        innerPnl.add(i3Pnl, BorderLayout.EAST); //adding innerpanel2 to innner panel


        // CREATING LABEL FOR DISPLAYING ERROR/SUCCESS MESSAGES //   
        //(innerpanel4) holds error message label
        JPanel i4Pnl = new JPanel();
        i4Pnl.setLayout(new GridLayout(2, 1, 20, 10));
        i4Pnl.setOpaque(false);
        
        forgConfPassLbl = new JLabel(); //Password Label
        try {
            //creating password icon, putting in try catch in case of retrieval error
            forgConfPassLbl.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/passicon.png").getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING)));
        } catch (Exception ioe) {
            System.out.println("Password icon not found.");
        }      
        forgConfPassLbl.setText("Confirm Password:");
        forgConfPassLbl.setForeground(Color.WHITE);        
        forgConfPassLbl.setFont(new Font(forgConfPassLbl.getFont().getFontName(), Font.BOLD, 15));
        
        JPanel i4innerPnl = new JPanel();
        //i4innerPnl.setLayout(new BorderLayout());
        i4innerPnl.setLayout(new GridBagLayout());
        i4innerPnl.setOpaque(false);

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.anchor = GridBagConstraints.WEST;

        forgConfPass = new JPasswordField(11); //Confirm Password Field
        forgConfPass.setHorizontalAlignment(SwingConstants.RIGHT);        
        ImageIcon ShowPassIcon2 = null;      
        try {
            ShowPassIcon2 = (new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/showpassicon.png").getImage().getScaledInstance(23, 22, Image.SCALE_SMOOTH)));
        } catch (Exception ioe) {
            System.out.println("Show pass icon not found.");
        }      
        btnShowCPass = new JButton(ShowPassIcon2);
        btnShowCPass.setHorizontalAlignment(SwingConstants.RIGHT);
        btnShowCPass.setBackground(mainBlue);
        //btnShowCPass.setPreferredSize(new Dimension(25, 30));
        btnShowCPass.setBorderPainted(false);
        btnShowCPass.addActionListener(new ShowCPassListener());

        // insets for x component
        //gbc.insets = new Insets(0, 0, 0, 5);
        gbc2.gridx = 0; // column   
        gbc2.weightx = 0.2;     
        gbc2.gridy = 0; // row 0
        gbc2.ipadx = 158; // increases components width by x pixels      
        gbc2.ipady = i4innerPnl.getHeight() + 3; // increases components height by x pixels
        i4innerPnl.add(forgConfPass, gbc2);

        // insets for x component
        //gbc.insets = new Insets(0, 0, 0, 0);
        gbc2.weightx = 0.0;     
        gbc2.gridx = 1; // column 1        
        gbc2.ipadx = 1; // increases components width by x pixels   
        i4innerPnl.add(btnShowCPass, gbc2);
        
        // adding the label and textfield to innerpanel4
        i4Pnl.add(forgConfPassLbl);
        i4Pnl.add(i4innerPnl);
        innerPnl.add(i4Pnl, BorderLayout.EAST); //adding innerpanel4 to innner panel



        // CREATING LABEL FOR DISPLAYING ERROR/SUCCESS MESSAGES //   
        //(innerpanel5) holds error message label
        JPanel i5Pnl = new JPanel();
        i5Pnl.setOpaque(false);

        verifyLbl = new JLabel(""); // error message label
        verifyLbl.setFont(new Font(verifyLbl.getFont().getFontName(), Font.BOLD, 15));
        i5Pnl.add(verifyLbl); // adding the label to innerpanel3
        innerPnl.add(i5Pnl, BorderLayout.EAST); //adding innerpanel3 to innner panel

        //adding the inner panel itself into the input panel
        inptPnl.add(innerPnl, BorderLayout.CENTER);

        setErrorMessage("Please Fill Out Fields.");


        //===============================================//
        //=    CREATING THE BUTTONS AT BOTTOM RIGHT     =//
        //===============================================//

        
        //(button panel) For holding buttons inside input panel
        btnPnl.setLayout(new GridLayout(3, 1, 20, 20));
        btnPnl.setBorder(new EmptyBorder(10, 0, 0, 0));
        btnPnl.setBackground(mainBlue);
        btnPnl.setOpaque(false);

        // CREATING AND ALLIGNING LOGIN BUTTON // 
        btnSaveNewPass = new JButton("Reset Password");
        btnSaveNewPass.setFont(new Font(btnLogin.getFont().getFontName(), Font.BOLD, 16));
        btnSaveNewPass.setForeground(lessBlue);
        btnSaveNewPass.setPreferredSize(new Dimension(40, 32));
        btnSaveNewPass.addActionListener(new SavePassBtnListener());
        
        // CREATING AND ALLIGNING QUIT BUTTON // 
        ImageIcon ReturnIcon = null;      
        try {
            ReturnIcon = (new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/returnicon.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        } catch (Exception ioe) {
            System.out.println("Create icon not found.");
        }      
        btnCancelPass = new JButton(ReturnIcon);
        btnCancelPass.setText("Return");
        btnCancelPass.setFont(new Font(btnCancelPass.getFont().getFontName(), Font.BOLD, 16));
        btnCancelPass.setForeground(lessBlue);
        btnCancelPass.addActionListener(new CancelPassBtnListener());
        
        //adding the button panel itself into the input panel
        inptPnl.add(btnPnl, BorderLayout.SOUTH);

        // Adding buttons to button panel
        btnPnl.add(btnSaveNewPass);
        btnPnl.add(btnCancelPass);


        //Update panels
        inptPnl.revalidate();
        inptPnl.repaint();
        innerPnl.revalidate();
        innerPnl.repaint();
        btnPnl.revalidate();
        btnPnl.repaint();
        System.out.println("Checking Connection");
        
    }



} // public class WelcomeScreen() end
