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

public class CreateAccount extends JFrame {

    /**
     * Creates new form CreateAccount
     */
    public CreateAccount() {
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
    
    private void initComponents() {

        /* Setting up the window */
        setTitle("Create a New Account");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        /* Logo */
        picLbl = new JLabel();

        /* User Type */
        UserType = new JComboBox<>();

        /* Verification Components */
        verifyLbl = new JLabel();

        /* Name input field */
        Name = new JTextField();
        nameLbl = new JLabel();

        /*Block input field */
        blockLbl = new JLabel();
        BlockSelection = new JComboBox<>();

        /* Room number input field */
        roomLbl = new JLabel();
        RoomNumber = new JComboBox<>();

        /*ID number input field */
        IDNumber = new JTextField();
        idLbl = new JLabel();

        /* Email input field */
        Email = new JTextField();
        emailLbl = new JLabel();

        /* Password Field */
        passwordfield = new JPasswordField();
        passwordLbl = new JLabel();

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
        disPnl.setPreferredSize(new Dimension(700, 500));


        // creating quick wash logo, putting in try catch in case of retrieval error
        try {
            picLbl.setIcon(new ImageIcon(getClass().getResource("/pics/quickwashlogo.png"))); 
            picLbl.setPreferredSize(new Dimension(700, 300));
            picLbl.setHorizontalAlignment(SwingConstants.CENTER);
        } catch (Exception ioe) {
            System.out.println("QuickWash banner not found.");
        }
         

        usertypePnl = new JPanel(new FlowLayout(FlowLayout.CENTER));
        usertypePnl.setBackground(mainWhite);
        usertypePnl.setPreferredSize(new Dimension(700, 100));

        UserType.setBackground(mainGrey);
        UserType.setPreferredSize(new Dimension(170,28));
        UserType.setModel(new DefaultComboBoxModel<>(new String[] { "Select User Type", "Resident", "Employee" }));
        UserType.addActionListener(new UserTypeListener());

    
        //Adding the UserType JComboBox to the userTypePanel
        usertypePnl.add(UserType);
        
        //==================================================//
        //=    STRUCTURING & CREATING THE INPUT PANEL      =//
        //==================================================//

        nameLbl.setText("Name");
        Name.setBackground(mainGrey);
        Name.setPreferredSize(new Dimension(121, 22));

        idLbl.setText("ID Number");
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

        roomLbl.setText("Room Number");
        RoomNumber.setBackground(mainGrey);
        RoomNumber.setModel(new DefaultComboBoxModel<>(new String[]{"Select a Room Number", "101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "111", "112", "113", "114", "115", "116", "117", "118", "119", "120", "121", "122", "123", "124", "201", "202", "203", "204", "205", "206", "207", "208", "209", "210", "211", "212", "213", "214", "215", "216", "217", "218", "219", "220", "221", "222", "223", "224", "301", "302", "303", "304", "305", "306", "307", "308", "309", "310", "311", "312", "313", "314", "315", "316", "317", "318", "319", "320", "321", "322", "323", "324", "401", "402", "403", "404", "405", "406", "407", "408", "409", "410", "411", "412", "413", "414", "415", "416", "417", "418", "419", "420", "421", "422", "423", "424", "501", "502", "503", "504", "505", "506", "507", "508", "509", "510", "511", "512", "513", "514", "515", "516", "517", "518", "519", "520", "521", "522", "523", "524", "601", "602", "603", "604", "605", "606", "607", "608", "609", "610", "611", "612", "613", "614", "615", "616", "617", "618", "619", "620", "621", "622", "623", "624"}));


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

        
        
        //===============================================//
        //=    CREATING THE BUTTONS AT RIGHT            =//
        //===============================================//

        //FLATLAF Button stylings
        UIManager.put( "Button.arc", 27);

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


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    //initializing FLATLAF
                    UIManager.setLookAndFeel( new FlatLightLaf() );
                } catch( Exception ex ) {
                    System.err.println( "Failed to initialize LaF" );
                }
                
                new CreateAccount().setVisible(true);
            }
        });
    }

    
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

            } 
            else if (selectedType.equals("Resident")) {
                toggleResidentFields(true);
                
            }
            else if (selectedType.equals("Employee")) {
                toggleEmployeeFields(true);
            
        }

        }
        
    }




    /**
     * This implements Login Button functionalities
     */
    private class LoginBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Returns the user to the Welcome Screen for login
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
            

            // Blank field validation
            if(name.isEmpty() || email.isEmpty() || idNumber.isEmpty() || password.isEmpty()){
                JOptionPane.showMessageDialog(null, "One or more fields are left blank", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Name validation
            if (!name.matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(null, "Name must only contain alphabetic characters.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Email validation
            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(null, "Email must contain an '@' character.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // ID number validation
            if (!idNumber.matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "ID Number must only contain numerical values.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            if (selectedType.equals("Resident")){
                // Block Selection validation
                if (selectedBlock.equals("Select a Block")) {
                    JOptionPane.showMessageDialog(null, "Please select a Block.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Room Number selection validation
                if (selectedRoom.equals("Select a Room Number")) {
                    JOptionPane.showMessageDialog(null, "Please select a Room Number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            }
            

            else{
                //Navigate to the Resident/Employee Main screen
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

    

    
} //public class CreateAccount() end