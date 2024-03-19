import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * This displays the main resident screen of the system where appointments are displayed 
 * and can be edited.
 */

public class ResidentGUI extends JFrame {

    private JPanel navPnl;
    private JPanel disPnl, apptPnl, detailsPnl;
    private TableRenderer apptTable, detsTable;
    private String[] apptColumnNames, detsColumnNames;
    private String[][] apptData, detsData;
    private DefaultTableModel model;

    private JLabel navLbl, apptLbl, infoLbl;
    private JLabel detailsLbl;

    private JButton btnMakeAppt, btnEditAppt, btnConfirm, btnLogout;
    
    private Color mainBlue = new Color(10, 87, 162);
    private Color mainWhite = new Color(255, 255, 255);
    private Color errorRed = new Color(239, 66, 66);    
    private Color successGreen = new Color(68, 218, 103);

    private WelcomeScreen thisWS; //previous screen
    private static ResidentGUI thisResGUI;
    private MakeAppointmentGUI thisMkAptGUI;
    private EditAppointmentGUI thisEdAptGUI;
    

    public ResidentGUI(WelcomeScreen ws) {

        /**
         * This sets up user information to ensure that the windows share same user data.
         */
        thisWS = ws;
        thisResGUI = this;

        /**
         * This turns off the welcome screen while the ResidentGUI screen is open.
         */        
        ws.setVisible(false);
       
        //Setting up program icon
        Image icon = ws.getIconImage();    
        setIconImage(icon);     
        
        //Labelling the frame/window 
        setTitle("QuickWash");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        
        // Declaring layout
        setLayout(new BorderLayout());


        //==================================================//
        //=               SETTING UP PANELS                =//
        //==================================================//


        
        //=======================================================//
        //=   STRUCTURING & CREATING NAVIGATION PANEL TO LEFT   =//
        //=======================================================//
        
        navPnl = new JPanel(new BorderLayout());
        navPnl.setBackground(mainBlue);
        navPnl.setPreferredSize(new Dimension(285,768));


        JPanel navinner1Pnl = new JPanel(new GridLayout(4, 1, 20, 28));        
        navinner1Pnl.setOpaque(false);   
        
        navinner1Pnl.setBorder(new EmptyBorder(80, 15, 0, 15));


        navLbl = new JLabel();  
        try {
            navLbl.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/user2icon.png").getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH)));
        } catch (Exception ioe) {
            System.out.println("User icon not found.");
        }      
        navLbl.setText("Hello, " + "<Resident>"); 
        navLbl.setForeground(mainWhite);  
        navLbl.setHorizontalAlignment(JLabel.CENTER);
        navLbl.setFont(new Font(navLbl.getFont().getFontName(), Font.BOLD, 19));
        

        /*Button set up*/   
        ImageIcon MkAptIcon = null;      
        try {
            MkAptIcon = (new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/createicon.png").getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH)));
        } catch (Exception ioe) {
            System.out.println("Create icon not found.");
        }      
        btnMakeAppt = new JButton(MkAptIcon);
        btnMakeAppt.setText(" Make Appointment");
        btnMakeAppt.setFont(new Font(btnMakeAppt.getFont().getFontName(), Font.BOLD, 16));
        btnMakeAppt.setForeground(mainWhite);
        btnMakeAppt.setBackground(mainBlue);
        btnMakeAppt.setHorizontalAlignment(SwingConstants.LEFT);
        btnMakeAppt.setBorderPainted(false); 
        btnMakeAppt.setMargin(new Insets(7, 20, 7, 0));
        btnMakeAppt.addActionListener(new MkAptBtnListener());

        ImageIcon EditAptIcon = null;      
        try {
            EditAptIcon = (new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/editicon.png").getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH)));
        } catch (Exception ioe) {
            System.out.println("Create icon not found.");
        }      
        btnEditAppt = new JButton(EditAptIcon);
        btnEditAppt.setText(" Edit Appointment");
        btnEditAppt.setFont(new Font(btnEditAppt.getFont().getFontName(), Font.BOLD, 16));
        btnEditAppt.setForeground(mainWhite);
        btnEditAppt.setBackground(mainBlue);
        btnEditAppt.setHorizontalAlignment(SwingConstants.LEFT);
        btnEditAppt.setBorderPainted(false);
        btnEditAppt.setMargin(new Insets(7, 20, 7, 0));
        btnEditAppt.addActionListener(new EditAptBtnListener());
        
        ImageIcon CnfmAptIcon = null;      
        try {
            CnfmAptIcon = (new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/confirmicon.png").getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH)));
        } catch (Exception ioe) {
            System.out.println("Create icon not found.");
        }      
        btnConfirm = new JButton(CnfmAptIcon);
        btnConfirm.setText(" Confirm Appointment");
        btnConfirm.setFont(new Font(btnConfirm.getFont().getFontName(), Font.BOLD, 16));
        btnConfirm.setForeground(mainWhite);
        btnConfirm.setBackground(mainBlue);
        btnConfirm.setHorizontalAlignment(SwingConstants.LEFT);
        btnConfirm.setBorderPainted(false);
        btnConfirm.setMargin(new Insets(7, 20, 7, 0));
        btnConfirm.addActionListener(new ConfirmBtnListener());

                        
        navinner1Pnl.add(navLbl);
        navinner1Pnl.add(btnMakeAppt);
        navinner1Pnl.add(btnEditAppt);
        navinner1Pnl.add(btnConfirm);



        JPanel navinner2Pnl = new JPanel();        
        navinner2Pnl.setOpaque(false);  
        navinner2Pnl.setBorder(new EmptyBorder(50, 50, 70, 50));

        btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font(btnLogout.getFont().getFontName(), Font.BOLD, 16));
        btnLogout.setForeground(mainBlue);
        btnLogout.setPreferredSize(new Dimension(200, 32));
        btnLogout.addActionListener(new LogoutBtnListener());

        navinner2Pnl.add(btnLogout);

        navPnl.add(navinner1Pnl, BorderLayout.NORTH);
        navPnl.add(navinner2Pnl, BorderLayout.SOUTH);


        
        //=====================================================//
        //=   STRUCTURING & CREATING DISPLAY PANEL TO RIGHT   =//
        //=====================================================//
        
        disPnl = new JPanel(new BorderLayout());    
        disPnl.setBorder(new EmptyBorder(5, 0, 23, 0));    
        disPnl.setBackground(mainWhite);           
        disPnl.setSize(new Dimension(900, 768));


        // Panel showing appoiments table
        apptPnl = new JPanel(new FlowLayout());        
        apptPnl.setBackground(mainWhite);
        //apptPnl.setBackground(new Color(200, 34, 23));
        //apptPnl.setBorder(BorderFactory.createLineBorder(mainBlue, 2));   
        
        apptPnl.setPreferredSize(new Dimension(900, 700));


        JPanel apptinner1Pnl = new JPanel(new BorderLayout());        
        apptinner1Pnl.setOpaque(false);   
        apptinner1Pnl.setBorder(new EmptyBorder(18, 0, 0, 0)); 

        apptLbl = new JLabel();        
        apptLbl.setText("Scheduled Wash Appointments");
        apptLbl.setHorizontalAlignment(JLabel.CENTER);
        apptLbl.setForeground(mainBlue);
        apptLbl.setFont(new Font(apptLbl.getFont().getFontName(), Font.BOLD, 20));        
        apptinner1Pnl.add(apptLbl, BorderLayout.NORTH);        
        
        infoLbl = new JLabel();        
        infoLbl.setText("<html>" + "Click on any Appointment to Edit" + "</html>");
        infoLbl.setHorizontalAlignment(JLabel.CENTER);
        infoLbl.setForeground(mainBlue);
        infoLbl.setFont(new Font(infoLbl.getFont().getFontName(), Font.PLAIN, 16));        
        apptinner1Pnl.add(infoLbl, BorderLayout.SOUTH);
        
        apptPnl.add(apptinner1Pnl);


        // Panel to display table itself
        JPanel apptinner2Pnl = new JPanel();        
        apptinner2Pnl.setOpaque(false);
        apptinner2Pnl.setBorder(new EmptyBorder(0, 45, 0, 7)); 
        apptinner2Pnl.setSize(new Dimension(900, 570));
            
        apptColumnNames = new String[]{ "Date (D/M/Y)", "Time", "Wash Load #", "Dry Load #", "Machine #", "Confirmed?" };

        //Update this value, should be sorted by upcoming date
        apptData = new String[][] {
            {"20/03/2024", "16:00", "3", "2", "1", "No"},
            {"11/03/2024", "9:00", "3", "2", "4", "Yes"}
        };

        //Rendering table with data above
        apptTable = new TableRenderer(apptinner2Pnl, new Dimension(780, 415), apptColumnNames, apptData);
        apptPnl.add(apptinner2Pnl);


        detailsPnl = new JPanel();    
        detailsPnl.setBorder(new EmptyBorder(0, 75, 0, 40));    
        detailsPnl.setBackground(mainWhite);;
        detailsPnl.setPreferredSize(new Dimension(800, 132));
        
        JPanel detsinner1Pnl = new JPanel(new BorderLayout());   
        detsinner1Pnl.setPreferredSize(new Dimension(800, 30));
        detsinner1Pnl.setOpaque(false);
        
        detailsLbl = new JLabel();        
        detailsLbl.setText("Selected Appointment Date & Time Details");
        detailsLbl.setForeground(mainBlue);
        detailsLbl.setHorizontalAlignment(JLabel.CENTER);
        detailsLbl.setFont(new Font(detailsLbl.getFont().getFontName(), Font.BOLD, 19));        
        detsinner1Pnl.add(detailsLbl, BorderLayout.NORTH);

        detailsPnl.add(detsinner1Pnl);


        JPanel detsinner2Pnl = new JPanel(new BorderLayout());        
        detsinner2Pnl.setOpaque(false); 
        //detsinner2Pnl.setBackground(new Color(123,23,55));  
        detsinner2Pnl.setBorder(new EmptyBorder(0, 0, 0, 0));   
        detsinner2Pnl.setSize(new Dimension(800, 30)); 

        detsColumnNames = new String[]{ "Time", "Weekday", "Month", "Day",  "Year"};

        //Update this value
        detsData = new String[][] {
            {"12:00", "Wednesday", "March", "13",  "2024", }
        };

        //Rendering details table with data above
        detsTable = new TableRenderer(detsinner2Pnl, new Dimension(780, 35), detsColumnNames, detsData);

        detailsPnl.add(detsinner2Pnl);



        disPnl.add(apptPnl, BorderLayout.NORTH);
        disPnl.add(detailsPnl, BorderLayout.SOUTH);
        disPnl.add(apptPnl);


        //==============================================//
        //=        ADDING MAIN PANELS TO FRAME         =//
        //==============================================//
        add(navPnl, BorderLayout.WEST);
        add(disPnl, BorderLayout.EAST);


        //Extra frame/window settings
        pack();
        setSize(1170, 768);//1152
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

    }// public ResidentGUI() end (constructor)




    //=========================================================//
    //=          BUTTON LISTENING FUNCTIONALITIES             =//
    //=========================================================//

    /**
     * This implements Make Appointment Button functionalities
     */
    private class MkAptBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            thisMkAptGUI = new MakeAppointmentGUI(thisResGUI);
        }

    }

    /**
     * This implements Edit Appointment Button functionalities
     */
    private class EditAptBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            thisEdAptGUI = new EditAppointmentGUI(thisResGUI);
        }

    }


    /**
     * This implements Confirm Appointment Button functionalities
     */
    private class ConfirmBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        }

    }

    /**
     * This exits the current screen and returns the user to the previous screen Close
     */
    private class LogoutBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setVisible(false); //stops displaying window/frame
            thisWS.setVisible(true); //makes welcome screen visible again
        }

    }


} // public class ResidentGUI() end
