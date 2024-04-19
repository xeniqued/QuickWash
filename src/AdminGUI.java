import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLightLaf;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.List;

/**
 *
 * @author Dana Clarke(GUI)
 */

/**
 * Main Window upon loading the program. Allows Sign Up & Login of Accounts in system.
*/
public class AdminGUI extends JFrame {

    private JPanel navPnl; // (navigation panel) entire panel on the left  
    // (display panel) entire panel on the right, (input panel) ,
    // (report panel) generated income report at bottom right
    private JPanel disPnl, inptPnl, reptPnl; 

    private JLabel navLbl; //navLbl is where the Residents's first name is inserted
    private JLabel genReptLbl, infoLbl, reptLbl, sumLbl;
    private JLabel toDateLbl, frDateLbl, monLbl, dayLbl, yearLbl;
    private JSpinner frDaySpinner, toDaySpinner; // accepts wash loads, dry loads, day of month
    private JComboBox<String> frMonthDropBox, frYearDropBox; // accepts month, year, time 
    private JComboBox<String> toMonthDropBox, toYearDropBox; // accepts month, year, time 
    private TableRenderer incomeTable;
    private String[] incomeColumnNames;
    private ArrayList<String[]> incomeData;
    private int totWeeklyIncome;
    private int totIncome;
    private int rate;

    // Make Appointment, Edit Appointment, Mark Attend, Logout buttons
    private JButton btnSettings, btnLogout, btnReportGen; 

    // commonly used colors
    private Color mainBlue = new Color(10, 87, 162);
    private Color mainWhite = new Color(255, 255, 255);
    private Color errorRed = new Color(239, 66, 66);    
    private Color successGreen = new Color(68, 218, 103);
    
    private WelcomeScreen thisWS; //previous screen
    private static AdminGUI thisAdminGUI; //current screen instance

    private Database db;

    

    public AdminGUI(WelcomeScreen ws) {
        
        /**
         * This sets initializes attributes
         */ 
        this.totIncome=0;
        this.rate=400;

        /**
         * This sets up attributes to ensure that the window instances are linked
         */        
        thisWS = ws;
        thisAdminGUI = this;

        /**
         * This turns off the welcome screen while the ResidentGUI screen is open.
         */        
        ws.setVisible(false);
       
        //Setting up program icon
        Image icon = ws.getIconImage();    
        setIconImage(icon);     
        
        //Labelling the frame/window 
        setTitle("Administrator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Declaring layout for window
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
        navPnl.setPreferredSize(new Dimension(265,720));


        JPanel navinner1Pnl = new JPanel(new GridLayout(4, 1, 20, 28));        
        navinner1Pnl.setOpaque(false);   
        
        navinner1Pnl.setBorder(new EmptyBorder(80, 20, 0, 20));


        navLbl = new JLabel();  
        try {
            navLbl.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/user2icon.png").getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH)));
        } catch (Exception ioe) {
            System.out.println("User icon not found.");
        }      
        navLbl.setText("Hello, Administrator"); 
        navLbl.setForeground(mainWhite);  
        navLbl.setHorizontalAlignment(JLabel.CENTER);
        navLbl.setFont(new Font(navLbl.getFont().getFontName(), Font.BOLD, 19));
        


        //==============================================//
        //=      CREATING THE BUTTONS AT TOP LEFT      =//
        //==============================================//
        
        
        // CREATING AND ALLIGNING SETTINGS BUTTON // 
        ImageIcon SettingsIcon = null;      
        try {
            SettingsIcon = (new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/settingsicon.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH)));
        } catch (Exception ioe) {
            System.out.println("Create icon not found.");
        }      
        btnSettings = new JButton(SettingsIcon);
        btnSettings.setText(" QuickWash Settings");
        btnSettings.setFont(new Font(btnSettings.getFont().getFontName(), Font.BOLD, 16));
        btnSettings.setForeground(mainWhite);
        btnSettings.setBackground(mainBlue);
        btnSettings.setHorizontalAlignment(SwingConstants.LEFT);
        btnSettings.setBorderPainted(false); 
        btnSettings.setMargin(new Insets(7, 15, 7, 0));
        btnSettings.addActionListener(new SettingsBtnListener());
                        

        navinner1Pnl.add(navLbl);
        navinner1Pnl.add(btnSettings);



        JPanel navinner2Pnl = new JPanel();        
        navinner2Pnl.setOpaque(false);  
        navinner2Pnl.setBorder(new EmptyBorder(50, 50, 70, 50));

        // CREATING AND ALLIGNING LOGOUT BUTTON // 
        btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font(btnLogout.getFont().getFontName(), Font.BOLD, 16));
        btnLogout.setForeground(mainBlue);
        btnLogout.setPreferredSize(new Dimension(190, 32));
        btnLogout.addActionListener(new LogoutBtnListener());

        navinner2Pnl.add(btnLogout);

        
        // Adding inner navigation panels to the navigation panel
        navPnl.add(navinner1Pnl, BorderLayout.NORTH);
        navPnl.add(navinner2Pnl, BorderLayout.SOUTH);

        
        //=====================================================//
        //=   STRUCTURING & CREATING DISPLAY PANEL TO RIGHT   =//
        //=====================================================//
        
        //disPnl = new JPanel(new BorderLayout()); 
        disPnl = new JPanel(); 
        disPnl.setBackground(mainWhite);            
        //disPnl.setBackground(Color.GRAY);         
        disPnl.setBorder(new EmptyBorder(18, 30, 30, 25));     
        disPnl.setPreferredSize(new Dimension(750, 720));



        // CREATING AND ALLIGNING INCOME GENERATOR INPUT PANEL AT TOP RIGHT// 
        inptPnl = new JPanel();   
        inptPnl.setLayout(new BoxLayout (inptPnl, BoxLayout.Y_AXIS));   
        inptPnl.setOpaque(false);            
        //inptPnl.setBackground(Color.GREEN);  
        

        JPanel inptinner1Pnl = new JPanel(new BorderLayout());    
        //inptinner1Pnl.setBackground(errorRed);  
        inptinner1Pnl.setBorder(new EmptyBorder(8, 0, 15, 0));  
        inptinner1Pnl.setOpaque(false);      

        genReptLbl = new JLabel("Income Reports");
        try {
            genReptLbl.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/genreport1icon.png").getImage().getScaledInstance(27, 27, Image.SCALE_SMOOTH)));
        } catch (Exception ioe) {
            System.out.println("Generate Icon 1 icon not found.");
        }      
        genReptLbl.setHorizontalAlignment(JLabel.CENTER);
        genReptLbl.setForeground(mainBlue);
        genReptLbl.setFont(new Font(genReptLbl.getFont().getFontName(), Font.BOLD, 23));        
        inptinner1Pnl.add(genReptLbl, BorderLayout.NORTH);        
        
        inptPnl.add(inptinner1Pnl);


        JPanel inptinner2Pnl = new JPanel(new BorderLayout());      
        inptinner2Pnl.setBorder(BorderFactory.createLineBorder(mainBlue, 1));
        inptinner2Pnl.setOpaque(false);    
        //inptinner2Pnl.setBackground(Color.ORANGE);   
        inptinner2Pnl.setPreferredSize(new Dimension(678, 245));        
        
        infoLbl = new JLabel();    
        infoLbl.setBorder(new EmptyBorder(8, 0, 5, 0)); 
        infoLbl.setText("<html>" + "Enter a time frame for the desired income report" + "</html>");
        infoLbl.setHorizontalAlignment(JLabel.CENTER);
        infoLbl.setForeground(mainBlue);
        infoLbl.setFont(new Font(infoLbl.getFont().getFontName(), Font.PLAIN, 17));        
        inptinner2Pnl.add(infoLbl, BorderLayout.NORTH);


        //=================================================================//
        //=   STRUCTURING & CREATING INPUT AREAS AND FIELDS INSIDE THEM   =//
        //=================================================================//
        JPanel inptinner2i1Pnl = new JPanel();
        inptinner2i1Pnl.setLayout(new BoxLayout(inptinner2i1Pnl, BoxLayout.X_AXIS));     
        //inptinner2i1Pnl.setBackground(errorRed);
        inptinner2i1Pnl.setBorder(new EmptyBorder(0, 12, 5, 12)); 
        inptinner2i1Pnl.setOpaque(false);     
        //inptinner2i1Pnl.setPreferredSize(new Dimension(700, 245));



        JPanel inptinner2i1div1Pnl = new JPanel(new GridLayout(4, 1, 0, 9));
        //inptinner2i1div1Pnl.setBackground(Color.GREEN);
        inptinner2i1div1Pnl.setOpaque(false);
        inptinner2i1div1Pnl.setPreferredSize(new Dimension(220, 216));
        inptinner2i1div1Pnl.setBorder(new EmptyBorder(6, 15, 12, 15)); 

        
        // CREATING AND ALLIGNING START DATE SECTION // 

        frDateLbl = new JLabel("START DATE");  
        frDateLbl.setHorizontalAlignment(JLabel.CENTER);
        frDateLbl.setForeground(mainBlue); 
        frDateLbl.setFont(new Font(frDateLbl.getFont().getFontName(), Font.BOLD, 15));        
        inptinner2i1div1Pnl.add(frDateLbl);   

        JPanel inptinner2i1div1monPnl = new JPanel(new BorderLayout());
        inptinner2i1div1monPnl.setOpaque(false);

        monLbl = new JLabel("Month:");  
        monLbl.setForeground(mainBlue); 
        monLbl.setFont(new Font(monLbl.getFont().getFontName(), Font.BOLD, 15));        
        inptinner2i1div1monPnl.add(monLbl, BorderLayout.WEST);   
        String[] months = {"01 - Jan", "02 - Feb", "03 - Mar", "04 - Apr", 
                            "05 - May", "06 - June", "07 - July", "08 - Aug", "09 - Sept",
                            "10 - Oct", "11 - Nov", "12 - Dec"};        
        frMonthDropBox = new JComboBox<String>(months);
        frMonthDropBox.setFont(new Font(monLbl.getFont().getFontName(), Font.BOLD, 15));        
        frMonthDropBox.setForeground(mainBlue);
        frMonthDropBox.setPreferredSize(new Dimension(120,35));  
        inptinner2i1div1monPnl.add(frMonthDropBox, BorderLayout.EAST);    

        inptinner2i1div1Pnl.add(inptinner2i1div1monPnl); 


        JPanel inptinner2i1div1dayPnl = new JPanel(new BorderLayout());
        inptinner2i1div1dayPnl.setOpaque(false);

        dayLbl = new JLabel("Day:");  
        dayLbl.setForeground(mainBlue); 
        dayLbl.setFont(new Font(dayLbl.getFont().getFontName(), Font.BOLD, 15));        
        inptinner2i1div1dayPnl.add(dayLbl, BorderLayout.WEST);  
        SpinnerModel daySpinModel = new SpinnerNumberModel(1, //initial value
         1, //min
         31, //max
         1);//step
        frDaySpinner = new JSpinner(daySpinModel);
        frDaySpinner.setPreferredSize(new Dimension(120,35));
        ((DefaultEditor)frDaySpinner.getEditor()).getTextField().setEditable(false);
        ((DefaultEditor)frDaySpinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.LEFT);
        ((DefaultEditor)frDaySpinner.getEditor()).getTextField().setForeground(mainBlue);  
        ((DefaultEditor)frDaySpinner.getEditor()).getTextField().setFont(new Font(dayLbl.getFont().getFontName(), Font.PLAIN, 14));   
        inptinner2i1div1dayPnl.add(frDaySpinner, BorderLayout.EAST); 

        inptinner2i1div1Pnl.add(inptinner2i1div1dayPnl); 


        JPanel inptinner2i1div1yearPnl = new JPanel(new BorderLayout());
        inptinner2i1div1yearPnl.setOpaque(false);

        yearLbl = new JLabel("Year:");  
        yearLbl.setForeground(mainBlue); 
        yearLbl.setFont(new Font(yearLbl.getFont().getFontName(), Font.BOLD, 15));        
        inptinner2i1div1yearPnl.add(yearLbl, BorderLayout.WEST);      
        String[] years = new String[7];
        int count = 0;    
        for (int y = 2024; y < 2031; y++) {
            years[count] = Integer.toString(y);  
            //System.out.print(y + "\n"); 
            count++;
        };        
        frYearDropBox = new JComboBox<String>(years); 
        frYearDropBox.setPreferredSize(new Dimension(120,35));
        frYearDropBox.setForeground(mainBlue);
        frYearDropBox.setFont(new Font(yearLbl.getFont().getFontName(), Font.BOLD, 15));        
        inptinner2i1div1yearPnl.add(frYearDropBox, BorderLayout.EAST);  
 
        inptinner2i1div1Pnl.add(inptinner2i1div1yearPnl); 
        
        inptinner2i1Pnl.add(inptinner2i1div1Pnl);



        // CREATING AND ALLIGNING END DATE SECTION // 

        JPanel inptinner2i1div2Pnl = new JPanel(new GridLayout(4, 1, 0, 9));
        //inptinner2i1div2Pnl.setBackground(Color.ORANGE);
        inptinner2i1div2Pnl.setOpaque(false);
        inptinner2i1div2Pnl.setPreferredSize(new Dimension(220, 216));
        inptinner2i1div2Pnl.setBorder(new EmptyBorder(6, 30, 12, 0)); 

        toDateLbl = new JLabel("END DATE");  
        toDateLbl.setHorizontalAlignment(JLabel.CENTER);
        toDateLbl.setBorder(new EmptyBorder(0, 0, 0, 25)); 
        toDateLbl.setForeground(mainBlue); 
        toDateLbl.setFont(new Font(toDateLbl.getFont().getFontName(), Font.BOLD, 15));        
        inptinner2i1div2Pnl.add(toDateLbl);   


        JPanel inptinner2i1div2monPnl = new JPanel(new BorderLayout());
        inptinner2i1div2monPnl.setOpaque(false);
        JLabel mon2Lbl = new JLabel("Month:");
        mon2Lbl.setForeground(mainBlue); 
        mon2Lbl.setFont(new Font(mon2Lbl.getFont().getFontName(), Font.BOLD, 15));        
        inptinner2i1div2monPnl.add(mon2Lbl, BorderLayout.WEST);        
        toMonthDropBox = new JComboBox<String>(months);
        toMonthDropBox.setFont(new Font(monLbl.getFont().getFontName(), Font.BOLD, 15));        
        toMonthDropBox.setForeground(mainBlue);
        toMonthDropBox.setPreferredSize(new Dimension(120,35));  
        inptinner2i1div2monPnl.add(toMonthDropBox, BorderLayout.EAST);    

        inptinner2i1div2Pnl.add(inptinner2i1div2monPnl); 


        JPanel inptinner2i1div2dayPnl = new JPanel(new BorderLayout());
        inptinner2i1div2dayPnl.setOpaque(false);

        JLabel day2Lbl = new JLabel("Day:");
        day2Lbl.setForeground(mainBlue); 
        day2Lbl.setFont(new Font(day2Lbl.getFont().getFontName(), Font.BOLD, 15));        
        inptinner2i1div2dayPnl.add(day2Lbl, BorderLayout.WEST); 
        SpinnerModel daySpinModel2 = new SpinnerNumberModel(1, //initial value
         1, //min
         31, //max
         1);//step
        toDaySpinner = new JSpinner(daySpinModel2);
        toDaySpinner.setPreferredSize(new Dimension(120,35));
        ((DefaultEditor)toDaySpinner.getEditor()).getTextField().setEditable(false);
        ((DefaultEditor)toDaySpinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.LEFT);
        ((DefaultEditor)toDaySpinner.getEditor()).getTextField().setForeground(mainBlue);  
        ((DefaultEditor)toDaySpinner.getEditor()).getTextField().setFont(new Font(dayLbl.getFont().getFontName(), Font.PLAIN, 14));   
        inptinner2i1div2dayPnl.add(toDaySpinner, BorderLayout.EAST); 

        inptinner2i1div2Pnl.add(inptinner2i1div2dayPnl); 


        JPanel inptinner2i1div2yearPnl = new JPanel(new BorderLayout());
        inptinner2i1div2yearPnl.setOpaque(false);

        JLabel year2Lbl = new JLabel("Year: ");
        year2Lbl.setForeground(mainBlue); 
        year2Lbl.setFont(new Font(year2Lbl.getFont().getFontName(), Font.BOLD, 15));        
        inptinner2i1div2yearPnl.add(year2Lbl, BorderLayout.WEST);  
        toYearDropBox = new JComboBox<String>(years); 
        toYearDropBox.setPreferredSize(new Dimension(120,35));
        toYearDropBox.setForeground(mainBlue);
        toYearDropBox.setFont(new Font(yearLbl.getFont().getFontName(), Font.BOLD, 15));        
        inptinner2i1div2yearPnl.add(toYearDropBox, BorderLayout.EAST);  
 
        inptinner2i1div2Pnl.add(inptinner2i1div2yearPnl); 

        inptinner2i1Pnl.add(inptinner2i1div2Pnl);


        
        JPanel inptinner2i1div3Pnl = new JPanel(new BorderLayout());
        //inptinner2i1div3Pnl.setBackground(Color.GREEN);
        inptinner2i1div3Pnl.setOpaque(false);
        inptinner2i1div3Pnl.setPreferredSize(new Dimension(220, 216));
        inptinner2i1div3Pnl.setBorder(new EmptyBorder(103, 25, 63, 17)); 


        // CREATING AND ALLIGNING MARK GENERATE REPORTS BUTTON // 
        ImageIcon GenRptIcon = null;      
        try {
            GenRptIcon = (new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/starticon.png").getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH)));
        } catch (Exception ioe) {
            System.out.println("Generate Report Btn icon not found.");
        }      
        btnReportGen = new JButton(GenRptIcon);
        btnReportGen.setText("Generate Report");
        btnReportGen.setFont(new Font(btnReportGen.getFont().getFontName(), Font.BOLD, 15));
        btnReportGen.setForeground(mainWhite);
        btnReportGen.setBackground(mainBlue);
        btnReportGen.setBorderPainted(false);
        btnReportGen.setPreferredSize(new Dimension(120, 35));
        btnReportGen.setMargin(new Insets(0, 1, 0, 5));
        btnReportGen.addActionListener(new GenReportBtnListener());
        inptinner2i1div3Pnl.add(btnReportGen, BorderLayout.CENTER);

        inptinner2i1Pnl.add(inptinner2i1div3Pnl);

        inptinner2Pnl.add(inptinner2i1Pnl, BorderLayout.CENTER);
        
        inptPnl.add(inptinner2Pnl);




        //==========================================================//
        //=   STRUCTURING & CREATING THE GENERATED REPORT PANELS   =//
        //==========================================================//


        reptPnl = new JPanel(new BorderLayout()); 
        reptPnl.setOpaque(false);    
        //reptPnl.setBackground(Color.GREEN); 
        reptPnl.setPreferredSize(new Dimension(679, 319)); 


        JPanel reptinner1Pnl = new JPanel(new BorderLayout());   
        reptinner1Pnl.setBorder(new EmptyBorder(13, 0, 0, 0)); 
        //reptinner1Pnl.setBackground(Color.magenta); 
        reptinner1Pnl.setOpaque(false);
        
        reptLbl = new JLabel("Generated Report");
        try {
            reptLbl.setIcon(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/genreport2icon.png").getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH)));
        } catch (Exception ioe) {
            System.out.println("Generate Icon 2 not found.");
        }      
        reptLbl.setHorizontalAlignment(JLabel.CENTER);
        reptLbl.setForeground(mainBlue);
        reptLbl.setFont(new Font(reptLbl.getFont().getFontName(), Font.BOLD, 18));        
        reptinner1Pnl.add(reptLbl, BorderLayout.NORTH);               
        
        sumLbl = new JLabel();    
        sumLbl.setBorder(new EmptyBorder(1, 0, 5, 0)); 
        //Update this value, replace [26,000] with the value computed
        sumLbl.setText("<html>" + "Total Income in Range: $" + totIncome + "</html>");
        sumLbl.setHorizontalAlignment(JLabel.CENTER);
        sumLbl.setForeground(mainBlue);
        sumLbl.setFont(new Font(sumLbl.getFont().getFontName(), Font.PLAIN, 18));        
        reptinner1Pnl.add(sumLbl, BorderLayout.SOUTH);
        
        reptPnl.add(reptinner1Pnl, BorderLayout.NORTH);


        JPanel reptinner2Pnl = new JPanel(new BorderLayout());   
        //reptinner2Pnl.setBackground(Color.DARK_GRAY); 
        reptinner2Pnl.setOpaque(false);

        incomeColumnNames = new String[]{ "Weeks", "Total Wash Loads", "Total Dry Loads", "Total Income"};

        //Update this value, should be sorted by earlier weeks to later weeksj
        incomeData = new ArrayList<String[]>(){
            {
                add(new String[]{ "", "", "", ""});
            }
        };


        //Rendering income table with data above
        incomeTable = new TableRenderer(reptinner2Pnl, new Dimension(678, 195), incomeColumnNames, incomeData);
        
        reptPnl.add(reptinner2Pnl, BorderLayout.SOUTH);




        disPnl.add(inptPnl);
        disPnl.add(reptPnl);



        //==============================================//
        //=        ADDING MAIN PANELS TO FRAME         =//
        //==============================================//
        add(navPnl, BorderLayout.WEST);
        add(disPnl, BorderLayout.EAST);        

        //Extra frame/window settings
        pack();
        setSize(1024, 720);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

    }// public AdminGUI() end (constructor)


    
    //=========================================================//
    //=                   FUNCTIONALITIES                     =//
    //=========================================================//

    /**
     *
     * @author Akele Benjamin
     */
   
    // Function to set notification message
    public void setNotification(String msg, String title) {        
        JOptionPane.showMessageDialog(null, msg, 
        title, JOptionPane.INFORMATION_MESSAGE);
    }

    private LocalDate formatter(String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String[]dateParts=dateString.split("-");
        String mth=dateParts[1];
        String day=dateParts[2];
        String year=dateParts[0];
        if(mth.length()<2){
            mth="0"+mth;
            if(Integer.parseInt(day)<10){
                day="0"+day;
            }
        }else if(day.length()<2){
            day="0"+day;
        }
        String updatedDateString=year+"-"+mth+"-"+day;
        // Parse the string into a LocalDate object
        LocalDate dateFormatted = LocalDate.parse(updatedDateString, formatter);
        return dateFormatted;
    }

    public int calculateDaysBetween(String startDate, String endDate) {
        return (int) ChronoUnit.DAYS.between(formatter(startDate), formatter(endDate));
    }
    public boolean isInSameWeek(String date1String, String date2String) {
        // Get the week number of each date
        LocalDate date1= formatter(date1String);
        LocalDate date2=formatter(date2String);
        WeekFields weekFields = WeekFields.ISO;
        int weekNumber1 = date1.get(weekFields.weekOfWeekBasedYear());
        int weekNumber2 = date2.get(weekFields.weekOfWeekBasedYear());

        // Compare the week numbers
        return weekNumber1 == weekNumber2;
    }

    public  int getWeekNumber(String date1) {
        // Get the week number of each date
        WeekFields weekFields = WeekFields.ISO;
        int weekNumber1 = formatter(date1).get(weekFields.weekOfWeekBasedYear());
        // Compare the week numbers
        return weekNumber1;
    }
    
    public void genReport(List<Appointment> appDatesandCycles, String frDate, String toDate){
        List<String[]> weeklyDataList = new ArrayList<>();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        if (appDatesandCycles.size()==0) {
            String[] newWeekData = {"No Appointments" ,"0", "0", "0"};
            weeklyDataList.add(newWeekData);
            getTotalIncomeCal(weeklyDataList);
            incomeTable.populateTable2(weeklyDataList);
        }else{
             // Iterate through appointments
            for (Appointment appointment : appDatesandCycles) {
                LocalDate appointmentDate = formatter(appointment.getDate());
                // Check if appointment date is within the specified date range
                if (!appointmentDate.isBefore(formatter(frDate)) && !appointmentDate.isAfter(formatter(toDate))) {
                    int weekNumber = appointmentDate.get(weekFields.weekOfWeekBasedYear()); // Get week number
                    String[] weekData = getOrCreateWeekData(weeklyDataList, weekNumber); // Get or create week data
                    int washNum = Integer.parseInt(weekData[1]) + appointment.getWashNum();
                    int dryNum = Integer.parseInt(weekData[2]) + appointment.getDryNum();
                    int totalIncome = (washNum + dryNum) * rate;
                    // Update week data
                    weekData[1] = String.valueOf(washNum);
                    weekData[2] = String.valueOf(dryNum);
                    weekData[3] = String.valueOf(totalIncome);
                }
            }
            getTotalIncomeCal(weeklyDataList);
            incomeTable.populateTable2(weeklyDataList);
    
        }
    }

    private static String[] getOrCreateWeekData(List<String[]> weeklyDataList, int weekNumber) {
        for (String[] weekData : weeklyDataList) {
            if (weekData[0].equals("Week " + weekNumber)) {
                return weekData; // Week data already exists
            }
        }
        // Create new week data if not found
        String[] newWeekData = {"Week " + weekNumber, "0", "0", "0"};
        weeklyDataList.add(newWeekData);
        return newWeekData;
    }

    public void getTotalIncomeCal(List<String[]> weeklyIncomeList){
        int tot=0;
        for( String[] wIncome: weeklyIncomeList){
            tot=Integer.parseInt(wIncome[3])+tot;
            System.out.println("Accumulating Total: "+tot);
        }
        this.sumLbl.setText("<html>" + "Total Income in Range: $" + tot + "</html>");
    }

    private  void connectionErrorPanel() {        
        JOptionPane.showMessageDialog(null, 
        "Internet Connection Required. \nPlease Restart and Try Again.", 
        "No Internet Connection", JOptionPane.ERROR_MESSAGE);
    }

    


    //=========================================================//
    //=          BUTTON LISTENING FUNCTIONALITIES             =//
    //=========================================================//

    /**
 *
 * @author Akele Benjamin
 */
    
    
    
    /**
     * This implements QuickWash Settings Button functionalities
     */
    private class SettingsBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        }

    }

    /**
     * This implements Income Report Generation Button functionalities
     */
    private class GenReportBtnListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(!Database.isConnected()){
                connectionErrorPanel();
                System.exit(0);
            }else{
                //List<String> appDatesandCycles = new ArrayList<>();
                setNotification("Generating Report...", null);

                int frDayValue=(int)frDaySpinner.getValue();
                int toDayValue=(int)toDaySpinner.getValue();
                String toMonthValue = (String) toMonthDropBox.getSelectedItem();
                String[] toMonthParts = toMonthValue.split(" -");
                int toMonthInt=Integer.parseInt(toMonthParts[0]);
                String frMonthValue = (String) frMonthDropBox.getSelectedItem();
                String[] frMonthParts = frMonthValue.split(" -");
                int frMonthInt=Integer.parseInt(frMonthParts[0]);
                String toYearValue = (String) toYearDropBox.getSelectedItem();
                String frYearValue = (String) frYearDropBox.getSelectedItem();
                String frDate=frYearValue+"-"+frMonthInt+"-"+frDayValue;
                String toDate=toYearValue+"-"+toMonthInt+"-"+toDayValue;

                try{
                    System.out.println("Admin GUI: "+frDate+" to "+toDate);
                    System.out.println("AdminGUI: Before Method excuted");
                    genReport(Database.selectTotCylesWithinRange(frDate, toDate),frDate,toDate);
                    
                    System.out.println("Report Made!");
                    setNotification("Report Generated.", null);
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Check Inputs and Try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.out.println("Could not make Report");
                    ex.printStackTrace();
                }
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


} // public class AdminGUI() end
