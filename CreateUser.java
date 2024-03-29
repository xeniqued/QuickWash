import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

//import com.formdev.flatlaf.FlatLightLaf;
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


public class CreateUser extends JFrame {

    /**
    * Main Window to create new user accounts in the system.
    */
    public CreateUser() {
        initComponents();
    }

    private JPanel disPnl; // (display panel) entire panel on the left 
    private JPanel inptPnl; // (display panel) entire panel on the right

    private JButton btnlogin; // Login button
    private JButton btnExit; // Quit button
    private JButton btnSignup; // Signup button

    private JComboBox<String> UserType; //Specifies the user type (Resident, Employee)
    private JComboBox<String> BlockSelction; // Specifies the block of Residence
    private JComboBox<String> RoomNumber; //Specifies the Room of residence 

    private JLabel picLbl; //Logo Label
    private JLabel nameLbl; // Name Label
    private JLabel blockLbl; // Block Label
    private JLabel roomLbl; //Room label
    private JLabel idLbl; //ID label
    private JLabel emailLbl; //Email Label
    private JLabel passwordLbl; // Password label
    private JLabel alrregistered; // Already Registered?
    
    private JPasswordField passwordfield; // Password feild 
    private JTextField Name;
    private JTextField IDNumber;
    private JTextField Email;

    private Color mainBlue = new Color(10, 87, 162);
    private Color mainWhite = new Color(255, 255, 255);
    private Color maingrey = new Color(204, 204, 204);
    private Color lessBlue = new Color(43, 90, 137);
    private Color errorRed = new Color(239, 66, 66);    
    private Color successGreen = new Color(68, 218, 103);

    private static CreateUser thisUserData; //This screen instance
    private WelcomeScreen welcomeScreen; //Welcome screen instance
                          
    private void initComponents() {

        disPnl = new JPanel(); //Left Panel
        
        picLbl = new JLabel(); //Logo

        UserType = new JComboBox<>(); //User Type selection

        Name = new JTextField(); // Name 
        nameLbl = new javax.swing.JLabel(); //Name Label

        blockLbl = new javax.swing.JLabel();// Block Label
        BlockSelction = new javax.swing.JComboBox<>(); //Block Selection 

        idLbl = new javax.swing.JLabel(); //ID Number Label
        IDNumber = new javax.swing.JTextField(); // ID Number

        RoomNumber = new javax.swing.JComboBox<>(); //Room Number
        roomLbl = new javax.swing.JLabel(); //Room Number label

        Email = new javax.swing.JTextField(); //Email 
        emailLbl = new javax.swing.JLabel(); //Email Label

        passwordfield = new javax.swing.JPasswordField(); //Password 
        passwordLbl = new javax.swing.JLabel(); // Password label
        
        inptPnl = new javax.swing.JPanel(); //Right panel 
        btnlogin = new javax.swing.JButton(); // Login Button
        btnExit= new javax.swing.JButton(); //Quit Button
        btnSignup = new javax.swing.JButton(); // Create Account 

        alrregistered = new javax.swing.JLabel(); // Already Registered?
        

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1024, 720));

        disPnl.setBackground(mainWhite);
        disPnl.setPreferredSize(new Dimension(700, 500));

        nameLbl.setIcon(new ImageIcon(getClass().getResource("/pics/quickwashlogo.png"))); 
        nameLbl.setPreferredSize(new Dimension(300, 300));

        UserType.setBackground(maingrey);
        UserType.setModel(new DefaultComboBoxModel<>(new String[] { "Select User Type", "Resident", "Employee" }));
        UserType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jComboBox1ActionPerformed(null);
            }
        });

        Name.setBackground(maingrey);
        Name.setPreferredSize(new Dimension(121, 22));
        Name.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        nameLbl.setText("Name");

        blockLbl.setText("Block");

        BlockSelction.setBackground(maingrey);
        BlockSelction.setModel(new DefaultComboBoxModel<>(new String[] { "Select a Block", "Block J ", "Block I", "Block G", "Block H" }));
        BlockSelction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        idLbl.setText("ID Number");

        IDNumber.setBackground(maingrey);
        IDNumber.setPreferredSize(new Dimension(170, 28));

        RoomNumber.setBackground(maingrey);
        RoomNumber.setModel(new DefaultComboBoxModel<>(new String[] { "Select a Room Number", "101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "111", "112", "113", "114", "115", "116", "117", "118", "119", "120", "121", "122", "123", "124", "201", "202", "203", "204", "205", "206", "207", "208", "209", "210", "211", "212", "213", "214", "215", "216", "217", "218", "219", "220", "221", "222", "223", "224", "301", "302", "303", "304", "305", "306", "307", "308", "309", "310", "311", "312", "313", "314", "315", "316", "317", "318", "319", "320", "321", "322", "323", "324", "401", "402", "403", "404", "405", "406", "407", "408", "409", "410", "411", "412", "413", "414", "415", "416", "417", "418", "419", "420", "421", "422", "423", "424", "501", "502", "503", "504", "505", "506", "507", "508", "509", "510", "511", "512", "513", "514", "515", "516", "517", "518", "519", "520", "521", "522", "523", "524", "601", "602", "603", "604", "605", "606", "607", "608", "609", "610", "611", "612", "613", "614", "615", "616", "617", "618", "619", "620", "621", "622", "623", "624" }));

        roomLbl.setText("Room Number");

        Email.setBackground(maingrey);
        Email.setPreferredSize(new Dimension(170, 28));
        Email.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        emailLbl.setText("Email");

        passwordfield.setBackground(maingrey);

        passwordLbl.setText("Password");

        GroupLayout disPnlLayout = new GroupLayout(disPnl);
        disPnl.setLayout(disPnlLayout);
        disPnlLayout.setHorizontalGroup(
            disPnlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(disPnlLayout.createSequentialGroup()
                .addGroup(disPnlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(disPnlLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(disPnl, GroupLayout.PREFERRED_SIZE, 587, GroupLayout.PREFERRED_SIZE))
                    .addGroup(disPnlLayout.createSequentialGroup()
                        .addGap(250, 250, 250)
                        .addComponent(UserType, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE))
                    .addGroup(disPnlLayout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addGroup(disPnlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(disPnlLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(nameLbl), GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
                                .addComponent(Name, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(idLbl, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                                .addComponent(Name, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(IDNumber, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addComponent(emailLbl, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
                        .addGap(102, 102, 102)
                        .addGroup(disPnlLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(BlockSelction, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(blockLbl, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                            .addComponent(RoomNumber, 0, 170, Short.MAX_VALUE)
                            .addComponent(roomLbl, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                            .addComponent(passwordfield)
                            .addComponent(passwordLbl, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(58, Short.MAX_VALUE));
        


        disPnlLayout.setVerticalGroup(
            disPnlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(disPnlLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(picLbl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(UserType, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(disPnlLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLbl)
                    .addComponent(blockLbl))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(disPnlLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(Name, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                    .addComponent(RoomNumber, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(disPnlLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(idLbl)
                    .addComponent(roomLbl))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(disPnlLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(RoomNumber)
                    .addComponent(Name, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(disPnlLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(emailLbl)
                    .addComponent(passwordLbl))
                .addGap(10, 10, 10)
                .addGroup(disPnlLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(passwordfield)
                    .addComponent(Email, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        inptPnl.setBackground(mainBlue);
        inptPnl.setBorder(BorderFactory.createEmptyBorder(0, 50, 100, 50));
        inptPnl.setPreferredSize(new Dimension(307, 720));

        btnlogin.setFont(new Font("Segoe UI", 1, 16)); 
        btnlogin.setForeground(lessBlue);
        btnlogin.setText("Login");
        btnlogin.setPreferredSize(new Dimension(40, 32));
        btnlogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnloginActionPerformed(evt);
            }
        });

        btnExit.setFont(new Font("Segoe UI", 1, 16)); 
        btnExit.setForeground(lessBlue);
        btnExit.setText("Quit");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        alrregistered.setForeground(new java.awt.Color(255, 255, 255));
        alrregistered.setText("Already Registered? ");

        btnSignup.setFont(new java.awt.Font("Segoe UI", 1, 16)); 
        btnSignup.setForeground(lessBlue);
        btnSignup.setText("Create Account");
        btnSignup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSignupActionPerformed(evt);
            }
        });

        GroupLayout inptPnlLayout = new GroupLayout(inptPnl);
        inptPnl.setLayout(inptPnlLayout);
        inptPnlLayout.setHorizontalGroup(
            inptPnlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(inptPnlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inptPnlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(inptPnlLayout.createSequentialGroup()
                        .addComponent(btnSignup, GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(inptPnlLayout.createSequentialGroup()
                        .addGroup(inptPnlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(btnlogin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnExit, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(GroupLayout.Alignment.TRAILING, inptPnlLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(alrregistered)
                        .addGap(46, 46, 46))))
        );
        inptPnlLayout.setVerticalGroup(
            inptPnlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, inptPnlLayout.createSequentialGroup()
                .addContainerGap(272, Short.MAX_VALUE)
                .addComponent(btnSignup, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                .addGap(139, 139, 139)
                .addComponent(alrregistered)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnlogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(disPnl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inptPnl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(disPnl, GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(inptPnl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }                       

    private void jComboBox1ActionPerformed(ActionEvent evt) {                                           
        // User Type handling
        String selectedUserType = (String) UserType.getSelectedItem();
        if ("Resident".equals(selectedUserType)) 
        {
            // Show text fields and labels for Resident
            nameLbl.setVisible(true);
            Name.setVisible(true);
            blockLbl.setVisible(true);
            BlockSelction.setVisible(true);
            roomLbl.setVisible(true);
            RoomNumber.setVisible(true);
            idLbl.setVisible(true);
            IDNumber.setVisible(true);
            emailLbl.setVisible(true);
            Email.setVisible(true);
            passwordLbl.setVisible(true);
            passwordfield.setVisible(true);
        } 
    
        else {
            // Hide text fields and labels for other user types
            nameLbl.setVisible(false);
            Name.setVisible(false);
            blockLbl.setVisible(false);
            BlockSelction.setVisible(false);
            roomLbl.setVisible(false);
            RoomNumber.setVisible(false);
            idLbl.setVisible(false);
            IDNumber.setVisible(false);
            emailLbl.setVisible(false);
            Email.setVisible(false);
            passwordLbl.setVisible(false);
            passwordfield.setVisible(false);
        }
}
                                          

    private void jTextField1ActionPerformed(ActionEvent evt) {                                            
        // Name Error handling

    }                                           

    private void jComboBox2ActionPerformed(ActionEvent evt) {                                           
        // Block Error handling
    }                                          

    private void jTextField3ActionPerformed(ActionEvent evt) {                                            
        // Email Error handling
    }                                           

    protected void btnSignupActionPerformed(ActionEvent evt) {
        // SignUp Action handling

        
    }

    private void btnloginActionPerformed(ActionEvent evt) {                                         
        // Login Button handling
        thisUserData.setVisible(false);
        welcomeScreen.setVisible(true);
        
    }                                        

    private void btnExitActionPerformed(ActionEvent evt) {                                         
        // Exit Button handling
            System.exit(0);
    }                                        

    
    public static void main(String args[]) {
        /* Set the FlatLaf look and feel */
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                try {
                    //initializing FLATLAF
                    //UIManager.setLookAndFeel( new FlatLightLaf() );
                } catch( Exception ex ) {
                    System.err.println( "Failed to initialize LaF" );
                }

                new CreateUser().setVisible(true);
            }
        });
    }

                     
}
