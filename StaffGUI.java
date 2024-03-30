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
import java.util.*;
import java.util.List;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 * This displays the main resident screen of the system where appointments are displayed 
 * and can be edited.
 */
public class StaffGUI extends JFrame {

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
    private JButton btnConfirm, btnMakeReport, btnLogout; 
    
    // commonly used colors
    private Color mainBlue = new Color(10, 87, 162);
    private Color mainWhite = new Color(255, 255, 255);
    private Color errorRed = new Color(239, 66, 66);    
    private Color successGreen = new Color(68, 218, 103);

    private WelcomeScreen thisWS; //previous screen
    private static StaffGUI thisStaffGUI; //current screen instance

    private String nameVar;
    private String idStringVar;

    public StaffGUI(WelcomeScreen ws,String idString,String name) {
        //initialize variable
        this.nameVar=name;
        this.idStringVar=idString;
        /**
         * This sets up attributes to ensure that the window instances are linked
         */        
        thisWS = ws;
        thisStaffGUI = this;
        
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
    
        
        // CREATING AND ALLIGNING ONFIRM ATTEND APPOINTMENT BUTTON // 
        ImageIcon ConfirmAptIcon = null;      
        try {
            ConfirmAptIcon = (new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/confirmicon.png").getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH)));
        } catch (Exception ioe) {
            System.out.println("Create icon not found.");
        }      
        btnConfirm = new JButton(ConfirmAptIcon);
        btnConfirm.setText(" Confirm Attended");
        btnConfirm.setFont(new Font(btnConfirm.getFont().getFontName(), Font.BOLD, 16));
        btnConfirm.setForeground(mainWhite);
        btnConfirm.setBackground(mainBlue);
        btnConfirm.setHorizontalAlignment(SwingConstants.LEFT);
        btnConfirm.setBorderPainted(false);
        btnConfirm.setMargin(new Insets(7, 20, 7, 0));
        btnConfirm.addActionListener(new ConfirmBtnListener());


        // CREATING AND ALLIGNING EDIT APPOINTMENT BUTTON // 
        ImageIcon MkReportIcon = null;      
        try {
            MkReportIcon = (new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/reporticon.png").getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH)));
        } catch (Exception ioe) {
            System.out.println("Create icon not found.");
        }      
        btnMakeReport = new JButton(MkReportIcon);
        btnMakeReport.setText(" Make A Report");
        btnMakeReport.setFont(new Font(btnMakeReport.getFont().getFontName(), Font.BOLD, 16));
        btnMakeReport.setForeground(mainWhite);
        btnMakeReport.setBackground(mainBlue);
        btnMakeReport.setHorizontalAlignment(SwingConstants.LEFT);
        btnMakeReport.setBorderPainted(false);
        btnMakeReport.setMargin(new Insets(7, 20, 7, 0));
        btnMakeReport.addActionListener(new MakeReportListener());
                        
        navinner1Pnl.add(navLbl);
        navinner1Pnl.add(btnConfirm);
        navinner1Pnl.add(btnMakeReport);



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
        apptLbl.setText("All Wash Appointments for <March 20, 2024>");
        apptLbl.setHorizontalAlignment(JLabel.CENTER);
        apptLbl.setForeground(mainBlue);
        apptLbl.setFont(new Font(apptLbl.getFont().getFontName(), Font.BOLD, 20));        
        apptinner1Pnl.add(apptLbl, BorderLayout.NORTH);        
        
        infoLbl = new JLabel();        
        infoLbl.setText("<html>" + "Click on any Appointment to View" + "</html>");
        infoLbl.setHorizontalAlignment(JLabel.CENTER);
        infoLbl.setForeground(mainBlue);
        infoLbl.setFont(new Font(infoLbl.getFont().getFontName(), Font.PLAIN, 16));        
        apptinner1Pnl.add(infoLbl, BorderLayout.SOUTH);
        
        apptPnl.add(apptinner1Pnl);


        
        // PANEL FOR APPOINTMENT TABLE ITSELF // 
        JPanel apptinner2Pnl = new JPanel();        
        apptinner2Pnl.setOpaque(false); 
        apptinner2Pnl.setBorder(new EmptyBorder(0, 0, 5, 0));

        apptColumnNames = new String[]{ "Appointment #","Resident Name","Time", "Wash Load #", "Dry Load #", "Washer ID","Dryer ID","Attended?", "Confirmed?"};

        //New Code
        Database db = new Database();
        List<Integer> dateListInt=getCurrentDateTimeInfo();
        List<Appointment> appointments=db.getAppointments(dateListInt.get(0),dateListInt.get(1),dateListInt.get(2));
        ArrayList<String[]> tableData = new ArrayList<String[]>();
        for (Appointment appointment : appointments) {
            String appNumString=Integer.toString(appointment.getAppointmentNum());
            String fullNameString=appointment.getName();
            String hourString = appointment.getTime() + ":00";
            String washNum = Integer.toString(appointment.getWashNum());
            String dryNum = Integer.toString(appointment.getDryNum());
            String washerID = appointment.getWasherId();
            String dryerID = appointment.getDryerId();
            String confirmedByResident = appointment.isConfirmedByResident() ? "Yes" : "No";
            String confirmedByStaff = appointment.isConfirmedByStaff() ? "Yes" : "No";
           
            String [] row = {appNumString,fullNameString, hourString, washNum, dryNum, washerID,dryerID, confirmedByResident, confirmedByStaff};
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

        detsColumnNames = new String[]{ "Time", "Resident Name", "Wash #", "Dry #"};

        //Update this value
        ArrayList<String[]> detsData = new ArrayList<String[]>();
        detsData.add(new String[]{ "", "", "", "", "","",""});

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

    }// public StaffGUI() end (constructor)

    public static List<Integer> getCurrentDateTimeInfo() {
        // Create a Calendar instance
        Calendar calendar = Calendar.getInstance();
        
        // Get current year, month, day, and hour
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Months start from 0, so we add 1
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // 24-hour format
        
        // Create a list to store year, month, day, and hour
        List<Integer> dateTimeInfo = new ArrayList<>();
        dateTimeInfo.add(year);
        dateTimeInfo.add(month);
        dateTimeInfo.add(day);
        dateTimeInfo.add(hour);
        
        return dateTimeInfo;
    }

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




    //=========================================================//
    //=          BUTTON LISTENING FUNCTIONALITIES             =//
    //=========================================================//


    /**
     * This implements Edit Appointment Button functionalities
     */
    private class MakeReportListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String url = "https://studentliving.managerpluscloud.com/v16/WorkOrders/WorkRequest/qRRequestPage.aspx?asset_key=p4zqNkLI8A1NVP0ELnXSig==&entity_key=9iuf4dpF3mCUsv2x4R2N4g==";

            int ans = JOptionPane.showConfirmDialog(thisStaffGUI, "You will be redirected to your browser to \nfill out the report. Continue?");  
            
            if(ans == JOptionPane.YES_OPTION){   
                if(Desktop.isDesktopSupported()){
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        desktop.browse(new URI(url));
                    } catch (IOException | URISyntaxException ex) {
                        ex.printStackTrace();
                    }
                }  
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
                db.updateConfirmedByStaff(Integer.parseInt(getRowSelectedData()[0]),true);
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
                String name = data[1];
                String washNum = data[3];
                String dryNum  = data[4];

                detsTable.updateRow(new String[]{time, name, washNum, dryNum}, 0, 3);
            }
        }
    }


} // public class StaffGUI() end 
