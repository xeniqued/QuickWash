import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.List;

/**
 * This displays the main resident screen of the system where appointments are displayed 
 * and can be edited.
 */
public class ResidentGUI extends JFrame {

    private JPanel navPnl; // (navigate panel) entire panel on the left  
    // (display panel) entire panel on the right, (appointments panel) appointment table at top, 
    // (details panel) appointment info at bottom
    private JPanel disPnl, apptPnl, detailsPnl; 
    private TableRenderer apptTable, detsTable; //uses custom class to display a table for above
    private String[] apptColumnNames, detsColumnNames; //stores column names for each table
    private String[][] apptData, detsData; // stores data for rows in appointment & details tables

    private JLabel navLbl, apptLbl, infoLbl; //navLbl is where the Residents's first name is inserted
    private JLabel detailsLbl;

    // Make Appointment, Edit Appointment, Mark Attend, Logout buttons
    private JButton btnMakeAppt, btnEditAppt, btnAttend, btnLogout; 
    
    // commonly used colors
    private Color mainBlue = new Color(10, 87, 162);
    private Color mainWhite = new Color(255, 255, 255);
    private Color errorRed = new Color(239, 66, 66);    
    private Color successGreen = new Color(68, 218, 103);

    private WelcomeScreen thisWS; //previous screen
    private static ResidentGUI thisResGUI; //current screen instance
    private CreateAppointmentGUI thisMkAptGUI = null; //CreateAppointmentGUI popup screen instance
    private EditAppointmentGUI thisEdAptGUI = null; //EditAppointmentGUI popup screen instance

    private String nameVar;
    private String idStringVar;
    

    public ResidentGUI(WelcomeScreen ws, String idString, String name) {
        //initialize variable
        this.nameVar=name;
        this.idStringVar=idString;
        /**
         * This sets up attributes to ensure that the window instances are linked
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

        
        
        // Declaring layout for frame/window 
        setLayout(new BorderLayout());


        //==================================================//
        //=                                                =//
        //=               SETTING UP PANELS                =//
        //=                                                =//
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
        //Get firstname
        String[]fullname=name.split(" ");
        String fname=fullname[0];      
        // replace <Resident> with the variable storing the the user's name
        navLbl.setText("Hello, " + fname); 
        navLbl.setForeground(mainWhite);  
        navLbl.setHorizontalAlignment(JLabel.CENTER);
        navLbl.setFont(new Font(navLbl.getFont().getFontName(), Font.BOLD, 19));
        


        //==============================================//
        //=      CREATING THE BUTTONS AT TOP LEFT      =//
        //==============================================//
        
        
        // CREATING AND ALLIGNING MAKE APPOINTMENT BUTTON // 
        ImageIcon MkAptIcon = null;      
        try {
            MkAptIcon = (new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/createicon.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
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

        
        // CREATING AND ALLIGNING EDIT APPOINTMENT BUTTON // 
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
        
        
        // CREATING AND ALLIGNING MARK ATTEND APPOINTMENT BUTTON // 
        ImageIcon AttendAptIcon = null;      
        try {
            AttendAptIcon = (new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/confirmicon.png").getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH)));
        } catch (Exception ioe) {
        }      
        btnAttend = new JButton(AttendAptIcon);
        btnAttend.setText(" Mark As Attended");
        btnAttend.setFont(new Font(btnAttend.getFont().getFontName(), Font.BOLD, 16));
        btnAttend.setForeground(mainWhite);
        btnAttend.setBackground(mainBlue);
        btnAttend.setHorizontalAlignment(SwingConstants.LEFT);
        btnAttend.setBorderPainted(false);
        btnAttend.setMargin(new Insets(7, 20, 7, 0));
        btnAttend.addActionListener(new ConfirmBtnListener());

                        
        navinner1Pnl.add(navLbl);
        navinner1Pnl.add(btnMakeAppt);
        navinner1Pnl.add(btnEditAppt);
        navinner1Pnl.add(btnAttend);



        JPanel navinner2Pnl = new JPanel();        
        navinner2Pnl.setOpaque(false);  
        navinner2Pnl.setBorder(new EmptyBorder(50, 50, 70, 50));

        btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font(btnLogout.getFont().getFontName(), Font.BOLD, 16));
        btnLogout.setForeground(mainBlue);
        btnLogout.setPreferredSize(new Dimension(200, 32));
        btnLogout.addActionListener(new LogoutBtnListener());

        navinner2Pnl.add(btnLogout);

        
        // Adding inner navigation panels to the navigation panel
        navPnl.add(navinner1Pnl, BorderLayout.NORTH);
        navPnl.add(navinner2Pnl, BorderLayout.SOUTH);


        
        //=====================================================//
        //=   STRUCTURING & CREATING DISPLAY PANEL TO RIGHT   =//
        //=====================================================//
        
        disPnl = new JPanel(new BorderLayout());    
        disPnl.setBackground(mainWhite);            
        //disPnl.setBackground(Color.GRAY);         
        disPnl.setBorder(new EmptyBorder(18, 27, 27, 25));     
        disPnl.setPreferredSize(new Dimension(905, 768));



        
        // CREATING AND ALLIGNING APPOINTMENT PANEL // 
        apptPnl = new JPanel();   
        apptPnl.setLayout(new BoxLayout (apptPnl, BoxLayout.Y_AXIS));   
        apptPnl.setOpaque(false);            
        //apptPnl.setBackground(Color.GREEN);  


        JPanel apptinner1Pnl = new JPanel(new BorderLayout());      
        apptinner1Pnl.setBorder(new EmptyBorder(8, 0, 6, 0));  
        apptinner1Pnl.setOpaque(false);      

        apptLbl = new JLabel();        
        apptLbl.setText("Your Scheduled Wash Appointments");
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


        
        // PANEL FOR APPOINTMENT TABLE ITSELF // 
        JPanel apptinner2Pnl = new JPanel();        
        apptinner2Pnl.setOpaque(false); 
        apptinner2Pnl.setBorder(new EmptyBorder(0, 0, 5, 0));
            
        apptColumnNames = new String[]{ "Appointment #","Date D/M/Y", "Time", "Wash Load #", "Dry Load #", "Washer ID","Dryer ID", "Attended?", "Confirmed?"};

        //Update this value, should be sorted by upcoming date

        //New Code
        Database db = new Database();
        List<Appointment> appointments=db.getAppointmentsById(Integer.parseInt(idString));
        ArrayList<String[]> tableData = new ArrayList<String[]>();
        for (Appointment appointment : appointments) {
            String appNumString=Integer.toString(appointment.getAppointmentNum());
            String dateString = appointment.getDay() + "/" + appointment.getMonth() + "/" + appointment.getYear();
            String hourString = appointment.getTime() + ":00";
            String washNum = Integer.toString(appointment.getWashNum());
            String dryNum = Integer.toString(appointment.getDryNum());
            String washerID = appointment.getWasherId();
            String dryerID = appointment.getDryerId();
            String confirmedByResident = appointment.isConfirmedByResident() ? "Yes" : "No";
            String confirmedByStaff = appointment.isConfirmedByStaff() ? "Yes" : "No";
           
            String [] row = {appNumString,dateString, hourString, washNum, dryNum, washerID,dryerID, confirmedByResident, confirmedByStaff};
            tableData.add(row);
        }
        


        //Rendering appointment table with data above
        apptTable = new TableRenderer(apptinner2Pnl, new Dimension(826, 425), apptColumnNames, tableData);
        JTable appointmentsTable=apptTable.getTable();
        appointmentsTable.getSelectionModel().addListSelectionListener(new TableSelectionListener());
        apptPnl.add(apptinner2Pnl);


        // PANEL FOR APPOINTMENT DETAILS TABLE ITSELF // 
        detailsPnl = new JPanel();           
        detailsPnl.setOpaque(false);   
        //detailsPnl.setBackground(Color.ORANGE);  
        detailsPnl.setPreferredSize(new Dimension(885, 140));
        
        JPanel detsinner1Pnl = new JPanel(new BorderLayout());   
        detsinner1Pnl.setBorder(new EmptyBorder(10, 0, 5, 0));  
        detsinner1Pnl.setOpaque(false);;
        
        detailsLbl = new JLabel(" Selected Appointment Date & Time Details");      
        try {
            detailsLbl.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/selecticon.png").getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH)));
        } catch (Exception ioe) {
            System.out.println("Select icon not found.");
        }      
        detailsLbl.setForeground(mainBlue);
        detailsLbl.setHorizontalAlignment(JLabel.LEFT);
        detailsLbl.setFont(new Font(detailsLbl.getFont().getFontName(), Font.BOLD, 19));        
        detsinner1Pnl.add(detailsLbl, BorderLayout.NORTH);

        detailsPnl.add(detsinner1Pnl);


        JPanel detsinner2Pnl = new JPanel(new BorderLayout());        
        detsinner2Pnl.setOpaque(false);    
        detsinner2Pnl.setSize(new Dimension(885, 30)); 

        detsColumnNames = new String[]{ "Time", "Weekday", "Month", "Day",  "Year"};

        //Update this value
        ArrayList<String[]> detsData = new ArrayList<String[]>();
        detsData.add(new String[]{ "", "", "", "", ""});

        //Rendering details table with data above
        detsTable = new TableRenderer(detsinner2Pnl, new Dimension(826, 35), detsColumnNames, detsData);

        detailsPnl.add(detsinner2Pnl);


        
        // Adding inner appointment panels to the appointment panel
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
        setSize(1200, 768);
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
            thisMkAptGUI = new CreateAppointmentGUI(thisResGUI,nameVar,idStringVar);
        }

    }

    /**
     * This implements Edit Appointment Button functionalities
     */
    private class EditAptBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (apptTable.getSelectedRow()==-1) {
                JOptionPane.showMessageDialog(null, "Please select an Appointment!", "Error", JOptionPane.ERROR_MESSAGE); 
            }else{
                thisEdAptGUI = new EditAppointmentGUI(thisResGUI,nameVar,idStringVar,getRowSelectedData());
            }
        }

    }


    /**
     * This implements Confirm Appointment Button functionalities
     */
    private class ConfirmBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Database db=new Database();
            try {
                db.updateConfirmedByResident(Integer.parseInt(getRowSelectedData()[0]),true);
                System.out.println("Confirmed by Resident");
                JOptionPane.showMessageDialog(null, "Confirmed Appointment!!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error. Could not confirm Appointment.", "Error", JOptionPane.ERROR_MESSAGE); 
                System.out.println("Error. Could not confirm Appointment.");
            }
        }

    }

    /**
     * This exits the current screen and returns the user to the previous screen
     */
    private class LogoutBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setVisible(false); //stops displaying window/frame
            thisWS.setVisible(true); //makes welcome screen visible again
        }

    }

    private class TableSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                String[] data = apptTable.getSelectedRowData();
                String time = data[2];
                String[] date = data[1].split("/");
                String weekday = getWeekday(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));
                String month = getMonthName(Integer.parseInt(date[1]));
                String day =  date[0];
                String year = date[2];

                detsTable.updateRow(new String[]{time, weekday, month, day, year}, 0, 4);
            }
        }
    }


    
    //======================================================//
    //=                  FUNCTIONALITIES                   =//
    //======================================================//

    /*public Array getSelectedRow(){
        return apptTable.getSelectedRow();
    }*/

    private String getWeekday(int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month, day);
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek.name();
    }

    // Function to get month name from month number
    public static String getMonthName(int monthNumber) {
        if (monthNumber < 1 || monthNumber > 12) {
            throw new IllegalArgumentException("Invalid month number");
        }
        return Month.of(monthNumber).name();
    }

    public String[] getRowSelectedData(){
        return apptTable.getSelectedRowData();
        
    }

} // public class ResidentGUI() end 
