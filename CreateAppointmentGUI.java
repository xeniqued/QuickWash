import javax.swing.*;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.border.EmptyBorder;

import org.w3c.dom.ranges.Range;

import java.awt.*;
import java.awt.event.*;

public class CreateAppointmentGUI extends JFrame {

    /**
     * This class creates a screen in which the user can add a trip to the arraylist.
     */

    private JPanel disPnl, disinner1Pnl, disinner2Pnl, disinner3Pnl; //display panel and its subpanels at the top
    private JPanel btnPnl; //button panel at the bottom

    private JButton btnSchedule; // Used to handle taking the inputted data and adding it to the appointment database
    private JButton btnCancel; // closes window and returns to the resident gui

    private JLabel  createAptLbl;  // displays Create Appointment at top
    private JLabel washLbl, dryLbl; 
    private JSpinner washSpinner, drySpinner, daySpinner; // accepts wash loads, dry loads, day of month
    private JLabel dateLbl, monLbl, dayLbl, yearLbl, timeLbl;
    private JComboBox<String> monthDropBox,  yearDropBox, timeDropBox; // accepts month, year, time

    // commonly used colors
    private Color mainBlue = new Color(10, 87, 162);
    private Color mainWhite = new Color(255, 255, 255);
    private Color errorRed = new Color(239, 66, 66);    
    private Color successGreen = new Color(68, 218, 103);

    private static ResidentGUI thisRGUI; //previous screen
    private CreateAppointmentGUI thisAddGUI; //current screen instance

    /**
     * This class creates a screen in which the user can schedule an appointment.
     */
    public CreateAppointmentGUI(ResidentGUI res){

        /**
         * This sets up attributes to ensure that the window instances are linked
         */        
        thisRGUI = res;
        thisAddGUI = this;
        setFocusable(true);

        // Additional window/frame settings
        setAlwaysOnTop(true); 

        /*Labelling the frame/window*/
        setTitle("Create Appointment");      
        //Setting up program icon
        Image icon = thisRGUI.getIconImage();    
        setIconImage(icon);
        //Declaring layout
        setLayout(new BorderLayout());


        //==================================================//
        //=                                                =//
        //=               SETTING UP PANELS                =//
        //=                                                =//
        //==================================================//
        
        disPnl = new JPanel(new FlowLayout(FlowLayout.CENTER)); //For inputting area
        btnPnl = new JPanel(new GridBagLayout()); //For buttons


        //============================================//
        //=   STRUCTURING & CREATING DISPLAY PANEL   =//
        //============================================// 

        disPnl.setPreferredSize(new Dimension(430,400));
        disPnl.setBorder(new EmptyBorder(0, 1, 0, 2));
        disPnl.setBackground(mainWhite); 

        //FLATLAF stylings
        UIManager.put( "Spinner.arc", 27); 

        // CREATING AND ALLIGNING CREATE APPOINTMENT LABEL //               
        //(displayinnerpanel1) holds create appt label at top
        disinner1Pnl = new JPanel();
        disinner1Pnl.setBorder(new EmptyBorder(16, 0, 0, 0));
        disinner1Pnl.setPreferredSize(new Dimension(435,70));
        disinner1Pnl.setBackground(mainBlue);
        createAptLbl = new JLabel("Create Appointment");
        createAptLbl.setHorizontalAlignment(JLabel.CENTER);
        createAptLbl.setOpaque(false);
        createAptLbl.setForeground(mainWhite);
        createAptLbl.setFont(new Font(createAptLbl.getFont().getFontName(), Font.BOLD, 19));        
        disinner1Pnl.add(createAptLbl);


        // CREATING AND ALLIGNING WASH AND DRY LOADS INPUT FIELDS //               
        //(displayinnerpanel2) holds both wash, dry labels and input fields inside display panel
        disinner2Pnl = new JPanel(new BorderLayout());      
        disinner2Pnl.setOpaque(false);  
        disinner2Pnl.setPreferredSize(new Dimension(430,95));
        disinner2Pnl.setBorder(new EmptyBorder(10, 55, 15, 65)); 

        JPanel disinner2i1Pnl = new JPanel(new BorderLayout());     
        disinner2i1Pnl.setOpaque(false);  
        washLbl = new JLabel("Wash Load #");
        washLbl.setHorizontalAlignment(JLabel.LEFT); 
        washLbl.setForeground(mainBlue); 
        washLbl.setFont(new Font(washLbl.getFont().getFontName(), Font.BOLD, 15));        
        
        disinner2i1Pnl.add(washLbl, BorderLayout.NORTH);
        SpinnerModel washSpinModel = new SpinnerNumberModel(1, //initial value
         1, //min
         10, //max
         1);//step
        washSpinner = new JSpinner(washSpinModel);
        washSpinner.setPreferredSize(new Dimension(110,35));
        ((DefaultEditor)washSpinner.getEditor()).getTextField().setEditable(false);
        ((DefaultEditor)washSpinner.getEditor()).getTextField().setForeground(mainBlue);  
        ((DefaultEditor)washSpinner.getEditor()).getTextField().setFont(new Font(createAptLbl.getFont().getFontName(), Font.PLAIN, 15));   
        disinner2i1Pnl.add(washSpinner, BorderLayout.SOUTH);

        disinner2Pnl.add(disinner2i1Pnl, BorderLayout.LINE_START);
        
        JPanel disinner2i2Pnl = new JPanel(new BorderLayout());  
        disinner2i2Pnl.setOpaque(false);  
        dryLbl = new JLabel("Dry Load #");
        dryLbl.setHorizontalAlignment(JLabel.LEFT);
        dryLbl.setForeground(mainBlue); 
        dryLbl.setFont(new Font(dryLbl.getFont().getFontName(), Font.BOLD, 15));        
        disinner2Pnl.add(dryLbl);
        disinner2i2Pnl.add(dryLbl, BorderLayout.NORTH);

        SpinnerModel drySpinModel = new SpinnerNumberModel(0, //initial value
         0, //min
         10, //max
         1);//step
        drySpinner = new JSpinner(drySpinModel);
        drySpinner.setPreferredSize(new Dimension(110,35));
        ((DefaultEditor)drySpinner.getEditor()).getTextField().setEditable(false);
        ((DefaultEditor)drySpinner.getEditor()).getTextField().setForeground(mainBlue);  
        ((DefaultEditor)drySpinner.getEditor()).getTextField().setFont(new Font(createAptLbl.getFont().getFontName(), Font.PLAIN, 15));   
         
        disinner2i2Pnl.add(drySpinner, BorderLayout.SOUTH);

        disinner2Pnl.add(disinner2i2Pnl, BorderLayout.LINE_END);       



        // CREATING AND ALLIGNING MONTH, DAY, YEAR, TIME INPUT FIELDS //               
        //(displayinnerpanel3) holds both wash, dry labels and input fields inside display panel
        disinner3Pnl = new JPanel(); 
        disinner3Pnl.setBorder(BorderFactory.createLineBorder(mainBlue, 1));
        disinner3Pnl.setLayout(new BoxLayout(disinner3Pnl, BoxLayout.Y_AXIS));
        disinner3Pnl.setOpaque(false);
        disinner3Pnl.setPreferredSize(new Dimension(371,245));

        JPanel disinner3i1Pnl = new JPanel();
        disinner3i1Pnl.setOpaque(false);
        disinner3i1Pnl.setBorder(new EmptyBorder(7, 10, 20, 10)); 
        dateLbl = new JLabel("Date & Time");  
        dateLbl.setHorizontalAlignment(JLabel.CENTER);
        dateLbl.setOpaque(false);
        dateLbl.setForeground(mainBlue);
        dateLbl.setFont(new Font(createAptLbl.getFont().getFontName(), Font.BOLD, 17));        
        disinner3i1Pnl.add(dateLbl); 

        disinner3Pnl.add(disinner3i1Pnl);    



        JPanel disinner3div1Pnl = new JPanel(new BorderLayout());
        disinner3div1Pnl.setOpaque(false);
        disinner3div1Pnl.setPreferredSize(new Dimension(430,50));
        disinner3div1Pnl.setBorder(new EmptyBorder(0, 25, 25, 33)); 

        JPanel disinner3i2Pnl = new JPanel(new BorderLayout());
        disinner3i2Pnl.setOpaque(false);        
        monLbl = new JLabel("Month");  
        monLbl.setForeground(mainBlue); 
        monLbl.setFont(new Font(monLbl.getFont().getFontName(), Font.BOLD, 15));        
        disinner3i2Pnl.add(monLbl, BorderLayout.NORTH);   
        String[] months = {"1 - Jan", "2 - Feb", "3 - Mar", "4 - Apr", 
                            "5 - May", "6 - June", "7 - July", "8 - Aug", "9 - Sept",
                            "10 - Oct", "11 - Nov", "12 - Dec"};        
        monthDropBox = new JComboBox<String>(months);
        monthDropBox.setFont(new Font(createAptLbl.getFont().getFontName(), Font.BOLD, 15));        
        monthDropBox.setForeground(mainBlue);
        monthDropBox.setPreferredSize(new Dimension(120,35));

        disinner3i2Pnl.add(monthDropBox, BorderLayout.SOUTH);    

        disinner3div1Pnl.add(disinner3i2Pnl, BorderLayout.LINE_START); 


        JPanel disinner3i3Pnl = new JPanel(new BorderLayout());
        disinner3i3Pnl.setOpaque(false);
        dayLbl = new JLabel("Day");  
        dayLbl.setForeground(mainBlue); 
        dayLbl.setFont(new Font(dayLbl.getFont().getFontName(), Font.BOLD, 15));        
        disinner3i3Pnl.add(dayLbl, BorderLayout.NORTH);  
        SpinnerModel daySpinModel = new SpinnerNumberModel(1, //initial value
         1, //min
         31, //max
         1);//step
        daySpinner = new JSpinner(daySpinModel);
        daySpinner.setPreferredSize(new Dimension(119,35));
        ((DefaultEditor)daySpinner.getEditor()).getTextField().setEditable(false);
        ((DefaultEditor)daySpinner.getEditor()).getTextField().setForeground(mainBlue);  
        ((DefaultEditor)daySpinner.getEditor()).getTextField().setFont(new Font(createAptLbl.getFont().getFontName(), Font.PLAIN, 14));   
        
        disinner3i3Pnl.add(daySpinner, BorderLayout.SOUTH); 

        disinner3div1Pnl.add(disinner3i3Pnl, BorderLayout.LINE_END); 

        disinner3Pnl.add(disinner3div1Pnl); 



        JPanel disinner3div2Pnl = new JPanel(new BorderLayout());
        disinner3div2Pnl.setOpaque(false);
        disinner3div2Pnl.setPreferredSize(new Dimension(430,40));
        disinner3div2Pnl.setBorder(new EmptyBorder(0, 25, 15, 33));

        JPanel disinner3i4Pnl = new JPanel(new BorderLayout());
        disinner3i4Pnl.setOpaque(false);
        yearLbl = new JLabel("Year");  
        yearLbl.setForeground(mainBlue); 
        yearLbl.setFont(new Font(yearLbl.getFont().getFontName(), Font.BOLD, 15));        
        disinner3i4Pnl.add(yearLbl, BorderLayout.NORTH);      
        String[] years = new String[7];
        int count = 0;    
        for (int y = 2024; y < 2031; y++) {
            years[count] = Integer.toString(y);  
            //System.out.print(y + "\n"); 
            count++;
        };        
        yearDropBox = new JComboBox<String>(years); 
        yearDropBox.setPreferredSize(new Dimension(119,35));
        yearDropBox.setForeground(mainBlue);
        yearDropBox.setFont(new Font(createAptLbl.getFont().getFontName(), Font.BOLD, 15));        
        disinner3i4Pnl.add(yearDropBox, BorderLayout.SOUTH);  
        
        disinner3div2Pnl.add(disinner3i4Pnl, BorderLayout.LINE_START);   
        
              
        JPanel disinner3i5Pnl = new JPanel(new BorderLayout());
        disinner3i5Pnl.setOpaque(false);
        timeLbl = new JLabel("Time");  
        timeLbl.setForeground(mainBlue); 
        timeLbl.setFont(new Font(timeLbl.getFont().getFontName(), Font.BOLD, 15));        
        disinner3i5Pnl.add(timeLbl, BorderLayout.NORTH);  
        String[] times = new String[10];    
        count = 0;
        for (int t = 9; t < 19; t++) {
            times[count] = Integer.toString(t) + ":00";
            //System.out.print(t + "\n"); 
            count++;
        };               
        timeDropBox = new JComboBox<String>(times); 
        timeDropBox.setFont(new Font(createAptLbl.getFont().getFontName(), Font.BOLD, 15));        
        timeDropBox.setForeground(mainBlue);
        timeDropBox.setPreferredSize(new Dimension(118,35));
        disinner3i5Pnl.add(timeDropBox, BorderLayout.SOUTH);    
        
        disinner3div2Pnl.add(disinner3i5Pnl, BorderLayout.LINE_END);    

        disinner3Pnl.add(disinner3div2Pnl);  
         


        //======================================================//
        //=    ADDING INNER DISPLAY PANELS TO DISPLAY PANEL    =//
        //======================================================//
        add(disinner1Pnl, BorderLayout.NORTH); 
        disPnl.add(disinner2Pnl); 
        disPnl.add(disinner3Pnl); 


        //========================================//
        //=   CREATING THE BUTTONS AT BOTTOM     =//
        //========================================//   

        btnPnl.setBackground(mainWhite);
        //btnPnl.setBackground(new Color(195,195,195));
        btnPnl.setSize(450, 380);
        btnPnl.setBorder(new EmptyBorder(20, 15, 30, 15));

        // CREATING AND ALLIGNING SCHEDULE BUTTON // 
        btnSchedule = new JButton("Schedule");
        btnSchedule.setFont(new Font(btnSchedule.getFont().getFontName(), Font.BOLD, 16));
        btnSchedule.setForeground(mainWhite);
        btnSchedule.setBackground(mainBlue);
        btnSchedule.setPreferredSize(new Dimension(130, 35));
        btnSchedule.addActionListener(new ScheduleBtnListener());
        

        // CREATING AND ALLIGNING CANCEL BUTTON // 
        btnCancel = new JButton("Cancel");
        btnCancel.setFont(new Font(btnCancel.getFont().getFontName(), Font.BOLD, 16));
        btnCancel.setForeground(mainWhite);
        btnCancel.setBackground(mainBlue);
        btnCancel.setPreferredSize(new Dimension(120, 35));
        btnCancel.addActionListener(new CancelBtnListener());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 20, 0, 20);
        btnPnl.add(btnSchedule, gbc);
        btnPnl.add(btnCancel, gbc);



        /**
         * Adding main panels to frame/window
         */
        add(disPnl, BorderLayout.CENTER);
        add(btnPnl, BorderLayout.SOUTH);



        /**
         * Extra frame set up things
         */
        pack();
        setSize(440, 565);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true); 

    } //public CreateAppointmentGUI() end (constructor)


    
    //=========================================================//
    //=           BUTTON LISTENING FUNCTIONALITIES            =//
    //=========================================================//
    
    /**
     * This button listener handles taking the data from the input fields and adding it to the appointment database
     */
    private class ScheduleBtnListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            //open dialog window with appointment details and ask for confirmation before adding to database
        }

    }
    

    /**
     * This button listener exits the current screen and returns to the previous screen.
     */
    private class CancelBtnListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            /**
             * This stops the window/frame from displaying.
             */
            setVisible(false);
        }

    }

} //public class CreateAppointmentGUI() end 
