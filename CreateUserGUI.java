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
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 *
 * @author xenique
 */

public class CreateUserGUI extends JFrame {

    private WelcomeScreen thisWS; //previous screen

    /**
     * Creates new form CreateUser
     */
    public CreateUserGUI(WelcomeScreen ws) {
        /**
         * This sets up attributes to ensure that the window instances are linked
         */        
        thisWS = ws;

        /**
         * This turns off the welcome screen while the ResidentGUI screen is open.
         */        
        ws.setVisible(false);

        initComponents();
    }

    /* Panels */
    private JPanel disPnl; // (display panel) entire panel on the left  
    private JPanel rightPnl; // (input panel) entire panel on the right 
    private JPanel btnPnl; // button panel 
    private JPanel usertypePnl; // inner panel for User type selection
    private JPanel inptPnl; // input panel for Resident data fields

    /* Buttons */
    private JButton btnLogin;
    private JButton btnExit;
    private JButton btnSignup;

    /* Drop-down Menus */
    private JComboBox<String> UserType;
    private JComboBox<String> BlockSelection;
    private JComboBox<String> RoomNumber;

    /* Labels */
    private JLabel verifyLbl;
    private JLabel picLbl;
    private JLabel nameLbl;
    private JLabel blockLbl;
    private JLabel idLbl;
    private JLabel roomLbl;
    private JLabel emailLbl;
    private JLabel passwordLbl;
    private JLabel registeredLbl;

    /* Input Fields */
    private JPasswordField passwordfield;
    private JTextField Name;
    private JTextField IDNumber;
    private JTextField Email;


    // commonly used colors
    private Color mainBlue = new Color(10, 87, 162);
    private Color mainWhite = new Color(255, 255, 255);
    private Color lessBlue = new Color(43, 90, 137);
    private Color mainGrey = new Color(204,204,204);
    private Color errorRed = new Color(239, 66, 66);  
    private Color successGreen = new Color(68, 218, 103);
    private Color waitBlue = new Color(56, 182, 255);
    
    private void initComponents() {

        /* Setting up the window */
        setTitle("Create a New Account");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        /* Logo */
        picLbl = new JLabel();

        /* User Type */
        UserType = new JComboBox<>();

        /* Name input field */
        nameLbl = new JLabel();
        Name = new JTextField();

        /*Block input field */
        blockLbl = new JLabel();
        BlockSelection = new JComboBox<>();

        /* Room number input field */
        roomLbl = new JLabel();
        RoomNumber = new JComboBox<>();

        /*ID number input field */
        idLbl = new JLabel();
        IDNumber = new JTextField();

        /* Email input field */
        emailLbl = new JLabel();
        Email = new JTextField();

        /* Password Field */
        passwordLbl = new JLabel();
        passwordfield = new JPasswordField();

        /* Buttons */
        btnLogin = new JButton();
        btnExit = new JButton();
        registeredLbl = new JLabel();
        btnSignup = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1024, 720));

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

        disPnl = new JPanel(new BorderLayout());
        disPnl.setBackground(mainWhite);
        disPnl.setPreferredSize(new Dimension(700, 510));


        // creating quick wash logo, putting in try catch in case of retrieval error
        try {
            picLbl.setIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/quickwashlogo2.png")); 
            picLbl.setPreferredSize(new Dimension(700, 290));
            picLbl.setBorder(new EmptyBorder(80, 0, 80, 0));
            picLbl.setHorizontalAlignment(SwingConstants.CENTER);
        } catch (Exception ioe) {
            System.out.println("QuickWash banner not found.");
        }
         

        usertypePnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        usertypePnl.setBackground(mainWhite);
        usertypePnl.setPreferredSize(new Dimension(700, 100));

        UserType.setBackground(mainGrey);
        UserType.setPreferredSize(new Dimension(170,28));
        UserType.setModel(new DefaultComboBoxModel<>(new String[] { "Select User Type", "Resident", "Staff" }));
        UserType.addActionListener(new UserTypeListener());

    
        //Adding the UserType JComboBox to the userTypePanel
        usertypePnl.add(UserType);
        
        //==================================================//
        //=    STRUCTURING & CREATING THE INPUT PANEL      =//
        //==================================================//

        nameLbl.setText("Name");
        Name.setBackground(mainGrey);
        Name.setPreferredSize(new Dimension(121, 22));

        idLbl.setText("ID #");
        IDNumber.setBackground(mainGrey);
        IDNumber.setPreferredSize(new java.awt.Dimension(170, 28));

        emailLbl.setText("Email");
        Email.setBackground(mainGrey);
        Email.setPreferredSize(new Dimension(43, 28));

        passwordLbl.setText("Password");
        passwordfield.setBackground(mainGrey);

        blockLbl.setText("Block");
        BlockSelection.setBackground(mainGrey);
        BlockSelection.setModel(new DefaultComboBoxModel<>(new String[]{"Select a Block", "Block J ", "Block I", "Block G", "Block H"}));

        roomLbl.setText("Room #");
        RoomNumber.setBackground(mainGrey);           
        ArrayList<String> roomsArrayLst = new ArrayList<String>();
        roomsArrayLst.add("Select a Room #");
        int count = 0;    
        for (int r = 101; r <= 624; r++) {
            roomsArrayLst.add(Integer.toString(r));  
            count++;
        };    
        String[] rooms = new String[roomsArrayLst.size()];
        for(int i=0; i<roomsArrayLst.size(); i++) {
            rooms[i] = roomsArrayLst.get(i);
        }

        RoomNumber.setModel(new DefaultComboBoxModel<>(rooms));


        inptPnl = new JPanel();
        inptPnl.setBackground(mainWhite);
        inptPnl.setPreferredSize(new Dimension(479,340));
        
        GroupLayout inptPnlLayout = new GroupLayout(inptPnl);
        inptPnl.setLayout(inptPnlLayout);
        inptPnlLayout.setHorizontalGroup(
            inptPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inptPnlLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(inptPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BlockSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RoomNumber, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(inptPnlLayout.createSequentialGroup()
                        .addGroup(inptPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(inptPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(Name, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                .addComponent(emailLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(blockLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Email))
                            .addComponent(nameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(83, 83, 83)
                        .addGroup(inptPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(idLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(passwordLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(IDNumber)
                            .addComponent(passwordfield, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                            .addComponent(roomLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        inptPnlLayout.setVerticalGroup(
            inptPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inptPnlLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(inptPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLbl)
                    .addComponent(idLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inptPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Name, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(IDNumber))
                .addGap(18, 18, 18)
                .addGroup(inptPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailLbl)
                    .addComponent(passwordLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inptPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Email, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(passwordfield))
                .addGap(18, 18, 18)
                .addGroup(inptPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(blockLbl)
                    .addComponent(roomLbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(inptPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BlockSelection, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(RoomNumber))
                .addContainerGap(43, Short.MAX_VALUE))
        );



        // Setting Up toggle functions for the User Type Listener
        toggleNoUserType(true);


        // Adding the logo and User type dropdown to the panel.
        disPnl.setLayout(new BorderLayout());
        disPnl.add(picLbl, BorderLayout.NORTH);
        disPnl.add(usertypePnl, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(mainWhite);
        centerPanel.add(inptPnl);

        disPnl.add(centerPanel, BorderLayout.SOUTH);


        //=================================================//
        //=  STRUCTURING & CREATING INPUT PANEL TO RIGHT  =//
        //=================================================//

        rightPnl = new JPanel(new BorderLayout());
        rightPnl.setBorder(new EmptyBorder(0, 50, 100, 50));
        rightPnl.setBackground(mainBlue);
        rightPnl.setPreferredSize(new Dimension(307,720));

        
        JPanel rightinnerPnl = new JPanel();
        rightinnerPnl.setOpaque(false);

        verifyLbl = new JLabel(); // error message label
        verifyLbl.setBorder(new EmptyBorder(300, 50, 0, 50));
        verifyLbl.setFont(new Font(verifyLbl.getFont().getFontName(), Font.BOLD, 15));
        
        setErrorMessage("Please select User Type.");
        rightinnerPnl.add(verifyLbl); // adding the label to innerpanel3

        //adding the inner panel itself into the right panel
        rightPnl.add(rightinnerPnl, BorderLayout.NORTH);
        

        //===============================================//
        //=    CREATING THE BUTTONS AT RIGHT            =//
        //===============================================//

        //(button panel) For holding buttons inside input panel
        btnPnl = new JPanel();
        btnPnl.setLayout(new GridLayout(4, 1, 20, 28));
        btnPnl.setBorder(new EmptyBorder(10, 0, 0, 0));
        btnPnl.setBackground(mainBlue);
        btnPnl.setOpaque(false);

        // 'ALREADY REGISTERED' TEXT //
        registeredLbl.setForeground(mainWhite);
        registeredLbl.setText("Already Registered? ");
        registeredLbl.setHorizontalAlignment(SwingConstants.CENTER);

        // CREATING AND ALLIGNING LOGIN BUTTON // 
        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font(btnLogin.getFont().getFontName(), Font.BOLD, 16));
        btnLogin.setForeground(lessBlue);
        btnLogin.setPreferredSize(new Dimension(40, 32));
        btnLogin.addActionListener(new LoginBtnListener());

        // CREATING AND ALLIGNING QUIT BUTTON // 
        btnExit = new JButton("Quit");
        btnExit.setFont(new Font(btnExit.getFont().getFontName(), Font.BOLD, 16));
        btnExit.setForeground(lessBlue);
        btnExit.addActionListener(new ExitBtnListener());


        // CREATING AND ALLIGNING SIGN UP BUTTON // 
        btnSignup = new JButton("Create Account");
        btnSignup.setFont(new Font(btnSignup.getFont().getFontName(), Font.BOLD, 16));
        btnSignup.setForeground(lessBlue);
        btnSignup.addActionListener(new SignUpBtnListener());

        //adding the button panel itself into the input panel
        rightPnl.add(btnPnl, BorderLayout.SOUTH);

        // Adding buttons to button panel
        btnPnl.add(btnSignup);
        btnPnl.add(registeredLbl);
        btnPnl.add(btnLogin);
        btnPnl.add(btnExit);


         //==============================================//
        //=        ADDING MAIN PANELS TO FRAME         =//
        //==============================================//
        add(disPnl, BorderLayout.WEST);
        add(rightPnl, BorderLayout.EAST);

        //Extra frame/window settings
        pack();
        setSize(1024, 720);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

    } // public CreateAccount() end (constructor)


    
    //======================================================================//
    //= TOGGLING FIELD VISIBILTY BASED ON THE USER TYPE SELECTED            =//
    //=======================================================================//
    private void toggleNoUserType(boolean show){
        nameLbl.setVisible(false);
        Name.setVisible(false);
        blockLbl.setVisible(false);
        BlockSelection.setVisible(false);
        idLbl.setVisible(false);
        IDNumber.setVisible(false);
        roomLbl.setVisible(false);
        RoomNumber.setVisible(false);
        emailLbl.setVisible(false);
        Email.setVisible(false);
        passwordLbl.setVisible(false);
        passwordfield.setVisible(false);

        disPnl.revalidate();
        disPnl.repaint();
    }
    
    private void toggleResidentFields(boolean show) {
        nameLbl.setVisible(show);
        Name.setVisible(show);
        blockLbl.setVisible(show);
        BlockSelection.setVisible(show);
        roomLbl.setVisible(show);
        RoomNumber.setVisible(show);
        idLbl.setVisible(show);
        IDNumber.setVisible(show);
        emailLbl.setVisible(show);
        Email.setVisible(show);
        
        passwordLbl.setVisible(show);
        passwordfield.setVisible(show);

        disPnl.revalidate();
        disPnl.repaint();
    }

    
    private void toggleEmployeeFields(boolean show) {
        nameLbl.setVisible(show);
        Name.setVisible(show);
        idLbl.setVisible(show);
        IDNumber.setVisible(show);
        emailLbl.setVisible(show);
        Email.setVisible(show);
        passwordLbl.setVisible(show);
        passwordfield.setVisible(show);

        blockLbl.setVisible(false);
        BlockSelection.setVisible(false);
        roomLbl.setVisible(false);
        RoomNumber.setVisible(false);

        disPnl.revalidate();
        disPnl.repaint();
    }
    


    //=========================================================//
    //=    BUTTON AND COMBOBOX LISTENING FUNCTIONALITIES      =//
    //=========================================================//


    private class UserTypeListener implements ActionListener{
        public void actionPerformed (ActionEvent e){
            String selectedType = (String) UserType.getSelectedItem();

            if (selectedType.equals("Select User Type")) {
                toggleNoUserType(true);
                setErrorMessage("Please select User Type.");

            } 
            else if (selectedType.equals("Resident")) {
                toggleResidentFields(true);
                hideVerifyMessage(false);
                
            }
            else if (selectedType.equals("Employee")) {
                toggleEmployeeFields(true);
                hideVerifyMessage(false);
            
        }

        }
        
    }

    /**
     * This implements Login Button functionalities
     */
    private class LoginBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Returns the user to the Welcome Screen for login
            setVisible(false); //stops displaying window/frame
            thisWS.setVisible(true); //makes welcome screen visible again
        }
    }

    /**
     * This implements Sign Up Button functionalities
     */
    private class SignUpBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            String selectedType = (String) UserType.getSelectedItem();
            String name = Name.getText().trim();
            String email = Email.getText().trim();
            String idNumber = IDNumber.getText().trim();
            String password = new String(passwordfield.getPassword());
            String selectedBlock = (String) BlockSelection.getSelectedItem();
            String selectedRoom = (String) RoomNumber.getSelectedItem();
           
            
            // User Type selected validation
            if (selectedType.equals("Select User Type")) {
                setErrorMessage("Please select User Type.");
                
            } else {

                // Blank field validation
                if (name.isEmpty() || email.isEmpty() || idNumber.isEmpty() || password.isEmpty()){
                    setErrorMessage("Please fill out all fields.");
                }

                //Name validation
                else if (!name.matches("[a-zA-Z\\s]+")) {
                    setErrorMessage("Name must only have <br> alphabetic characters.");                
                }
                
                // Email validation
                else if (!email.matches("^[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*$")) {
                    setErrorMessage("Invalid Email Address. <br> Please Try Again.");
                }

                // ID number validation
                else if (!idNumber.matches("\\d+")) {
                    setErrorMessage("ID Number must only <br> contain numbers.");
                }


                else if (selectedType.equals("Resident")){
                    // Block Selection validation
                    if (selectedBlock.equals("Select a Block")) {
                        setErrorMessage("Please select a Block.");
                    }
                    // Room Number selection validation
                    else if (selectedRoom.equals("Select a Room #")) {
                        setErrorMessage("Please select a Room <br> Number.");
                    }

                    else {
                        launchQuickWash(selectedType,name,email,Integer.parseInt(idNumber),password,selectedBlock,Integer.parseInt(selectedRoom));
                    }

                }            
                //If user type is an employee and all fields are valid
                else {
                    launchQuickWash(selectedType,name,email,Integer.parseInt(idNumber),password,selectedBlock,Integer.parseInt(selectedRoom));
                }
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

    //=========================================================//
    //=                   FUNCTIONALITIES                     =//
    //=========================================================//

    // Function to launch quickwash if all fields are valid
    public void launchQuickWash(String selectedType,String name,String email,int idNumber,String password,String selectedBlock,int selectedRoom) {        
        setSuccessMessage("Creating Account...");
        verifyLbl.paintImmediately(verifyLbl.getVisibleRect());
        if (selectedType.equals("Resident")){
            try {
                Database.addUser(selectedType,name,idNumber,email,selectedRoom,selectedBlock,password);
                System.out.println("Resident added to the Databse.");
            } catch (Exception e) {
                System.out.println("Error adding resident to the Databse.");
                e.printStackTrace();
            }
        }else{
            try {
                int room_num=0;
                String block="";
                Database.addUser(selectedType,name,idNumber,email,room_num,block,password);
                System.out.println("Staff added to the Databse.");
            } catch (Exception e) {
                System.out.println("Error adding staff to the Databse.");
                e.printStackTrace();
            }
        }

        setNotification("Launching QuickWash.", null);
                    
        /* Resetting fields */
        Name.setText("");
        BlockSelection.setSelectedIndex(0);
        RoomNumber.setSelectedIndex(0);
        IDNumber.setText("");
        Email.setText("");
        passwordfield.setText("");

        setVisible(false);
        thisWS=new WelcomeScreen();

        // Navigate to the Resident/Employee Main screen below based on account type //
        /*if(user.getType_user().equals("resident")){
            resGUI = new ResidentGUI(thisUserData, txtName, dbName);
        } else if(user.getType_user().equals("staff")){
            staffGUI = new StaffGUI(thisUserData, txtName, dbName); 
        }*/
    }


    // Function to set notification message
    public void setNotification(String waitMsg, String title) {        
        JOptionPane.showMessageDialog(null, waitMsg, 
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

    

    
} //public class CreateUser() end