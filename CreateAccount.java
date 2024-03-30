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

    private JPanel disPnl; // (display panel) entire panel on the left  
    private JPanel inptPnl; // (input panel) entire panel on the right 

    /* Buttons */
    private JButton btnLogin;
    private JButton btnExit;
    private JButton btnSignup;

    /* Drop-down Menus */
    private JComboBox<String> UserType;
    private JComboBox<String> BlockSelection;
    private JComboBox<String> RoomNumber;

    /* Labels */
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

        setTitle("Create a New Account");

        /* Panels */
        disPnl = new JPanel();
        inptPnl = new JPanel();
        
        /* Logo */
        picLbl = new JLabel();

        /* User Type */
        UserType = new JComboBox<>();

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

        disPnl.setBackground(mainWhite);
        disPnl.setPreferredSize(new Dimension(700, 500));

        try {
            picLbl.setIcon(new ImageIcon(getClass().getResource("/pics/quickwashlogo.png"))); 
            picLbl.setPreferredSize(new Dimension(300, 300));
        } catch (Exception ioe) {
            System.out.println("QuickWash banner not found.");
        }
        

        UserType.setBackground(mainGrey);
        UserType.setModel(new DefaultComboBoxModel<>(new String[] { "Select User Type", "Resident", "Employee" }));
        UserType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserTypeActionPerformed(evt);
            }
        });

        toggleNoUserType(true);
        toggleResidentFields(false);
        toggleEmployeeFields(false);

        

        nameLbl.setText("Name");
        Name.setBackground(mainGrey);
        Name.setPreferredSize(new Dimension(121, 22));
        Name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NameActionPerformed(evt);
            }
        });


        blockLbl.setText("Block");

        BlockSelection.setBackground(mainGrey);
        BlockSelection.setModel(new DefaultComboBoxModel<>(new String[] { "Select a Block", "Block J ", "Block I", "Block G", "Block H" }));
        BlockSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BlockSelectionActionPerformed(evt);
            }
        });

        idLbl.setText("ID Number");
        IDNumber.setBackground(mainGrey);
        IDNumber.setPreferredSize(new java.awt.Dimension(170, 28));

        roomLbl.setText("Room Number");
        RoomNumber.setBackground(mainGrey);
        RoomNumber.setModel(new DefaultComboBoxModel<>(new String[] { "Select a Room Number", "101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "111", "112", "113", "114", "115", "116", "117", "118", "119", "120", "121", "122", "123", "124", "201", "202", "203", "204", "205", "206", "207", "208", "209", "210", "211", "212", "213", "214", "215", "216", "217", "218", "219", "220", "221", "222", "223", "224", "301", "302", "303", "304", "305", "306", "307", "308", "309", "310", "311", "312", "313", "314", "315", "316", "317", "318", "319", "320", "321", "322", "323", "324", "401", "402", "403", "404", "405", "406", "407", "408", "409", "410", "411", "412", "413", "414", "415", "416", "417", "418", "419", "420", "421", "422", "423", "424", "501", "502", "503", "504", "505", "506", "507", "508", "509", "510", "511", "512", "513", "514", "515", "516", "517", "518", "519", "520", "521", "522", "523", "524", "601", "602", "603", "604", "605", "606", "607", "608", "609", "610", "611", "612", "613", "614", "615", "616", "617", "618", "619", "620", "621", "622", "623", "624" }));


        emailLbl.setText("Email");
        Email.setBackground(mainGrey);
        Email.setPreferredSize(new Dimension(170, 28));
        Email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmailActionPerformed(evt);
            }
        });

       

        passwordLbl.setText("Password");
        passwordfield.setBackground(mainGrey);

    
        GroupLayout disPnlLayout = new GroupLayout(disPnl);
        disPnl.setLayout(disPnlLayout);
        disPnlLayout.setHorizontalGroup(
            disPnlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(disPnlLayout.createSequentialGroup()
                .addGroup(disPnlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(disPnlLayout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(picLbl, GroupLayout.PREFERRED_SIZE, 587, GroupLayout.PREFERRED_SIZE))
                    .addGroup(disPnlLayout.createSequentialGroup()
                        .addGap(250, 250, 250)
                        .addComponent(UserType, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE))
                    .addGroup(disPnlLayout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addGroup(disPnlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(disPnlLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(nameLbl, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
                                .addComponent(Name, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(idLbl, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                                .addComponent(IDNumber, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(emailLbl, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
                                .addComponent(Email, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGap(102, 102, 102)
                        .addGroup(disPnlLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(BlockSelection, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(blockLbl, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                            .addComponent(roomLbl, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
                            .addComponent(RoomNumber, 0, 170, Short.MAX_VALUE)
                            .addComponent(passwordLbl, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE))))
                            .addComponent(passwordfield, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(58, Short.MAX_VALUE))
        );
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
                    .addComponent(BlockSelection, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(disPnlLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(roomLbl)
                    .addComponent(idLbl))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(disPnlLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(RoomNumber)
                    .addComponent(IDNumber, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(disPnlLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(emailLbl)
                    .addComponent(passwordLbl))
                .addGap(10, 10, 10)
                .addGroup(disPnlLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(passwordfield, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Email, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        inptPnl.setBackground(mainBlue);
        inptPnl.setBorder(BorderFactory.createEmptyBorder(0, 50, 100, 50));
        inptPnl.setPreferredSize(new Dimension(307, 720));

        //FLATLAF Button stylings
        UIManager.put( "Button.arc", 27);

        btnLogin.setFont(new Font(btnLogin.getFont().getFontName(), Font.BOLD, 16)); 
        btnLogin.setForeground(lessBlue);
        btnLogin.setText("Login");
        btnLogin.setPreferredSize(new java.awt.Dimension(40, 32));
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        btnExit.setFont(new Font(btnExit.getFont().getFontName(), Font.BOLD, 16)); 
        btnExit.setForeground(lessBlue);
        btnExit.setText("Quit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        registeredLbl.setForeground(new java.awt.Color(255, 255, 255));
        registeredLbl.setText("Already Registered? ");

        btnSignup.setFont(new Font(btnSignup.getFont().getFontName(), Font.BOLD, 16)); 
        btnSignup.setForeground(lessBlue);
        btnSignup.setText("Create Account");
        btnSignup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
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
                            .addComponent(btnLogin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnExit, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(GroupLayout.Alignment.TRAILING, inptPnlLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(registeredLbl)
                        .addGap(46, 46, 46))))
        );
        inptPnlLayout.setVerticalGroup(
            inptPnlLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, inptPnlLayout.createSequentialGroup()
                .addContainerGap(272, Short.MAX_VALUE)
                .addComponent(btnSignup, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                .addGap(139, 139, 139)
                .addComponent(registeredLbl)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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

    private void UserTypeActionPerformed(java.awt.event.ActionEvent evt) {
        String selectedType = (String) UserType.getSelectedItem();

        if (selectedType.equals("Select User Type")) {
            toggleNoUserType(true);
        } 
        else if (selectedType.equals("Resident")) {
            toggleResidentFields(true);
            
        }
        else if (selectedType.equals("Employee")) {
            toggleResidentFields(false);
            toggleEmployeeFields(true);
            
        }
    }


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

        disPnl.revalidate();
        disPnl.repaint();
        
    }

    private void NameActionPerformed(java.awt.event.ActionEvent evt) {
        // Error handling
    }

    private void BlockSelectionActionPerformed(java.awt.event.ActionEvent evt) {
        // Error Handling
    }

    private void EmailActionPerformed(java.awt.event.ActionEvent evt) {
        // Error handling
    }

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {
        // Error Handling
        
    }

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
    }

    private void btnSignUpActionPerformed(java.awt.event.ActionEvent evt) {
        // Error handling for blank fields
    }

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

    
}