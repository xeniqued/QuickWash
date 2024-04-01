import javax.swing.*;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.border.EmptyBorder;

import org.w3c.dom.ranges.Range;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * This class creates a screen in which the user can edit or delete an appointment.
 */
public class EditAppointmentGUI extends JFrame {

    /**
     * This class creates a screen in which the user can add a trip to the arraylist.
     */

    private JPanel disPnl, disinner1Pnl, disinner2Pnl, disinner3Pnl; //display panel and its subpanels at the top
    private JPanel btnPnl; //button panel at the bottom

    private JButton btnDelete; // Used to handle deleting the appointment from the appointment database
    private JButton btnApply; // Used to handle saving edits to the appointment database
    private JButton btnCancel; // closes window and returns to the resident gui

    private JLabel  editAptLbl; // displays Edit Appointment at top
    private JLabel washLbl, dryLbl;
    private JSpinner washSpinner, drySpinner, daySpinner; // accepts wash loads, dry loads, day of month
    private JLabel dateLbl, monLbl, dayLbl, yearLbl, timeLbl;
    private JComboBox<String> monthDropBox,  yearDropBox, timeDropBox; // accepts month, year, time
    private JCheckBox attendedCheck; // accepts a boolean for whether an appointment was attended

    // commonly used colors
    private Color mainBlue = new Color(10, 87, 162);
    private Color mainWhite = new Color(255, 255, 255);
    private Color errorRed = new Color(239, 66, 66);    
    private Color successGreen = new Color(68, 218, 103);

    private static ResidentGUI thisRGUI; //previous screen
    private EditAppointmentGUI thisEditGUI; //current screen instance

    private String idStringVar;
    private String nameVar;
    private ArrayList<String> selectedRowDataArray;


    public EditAppointmentGUI(ResidentGUI res,String name,String idString,ArrayList<String>selectedRowData){

        this.nameVar=name;
        this.idStringVar=idString;
        this.selectedRowDataArray=selectedRowData;

        /**
         * This sets up attributes to ensure that the window instances are linked
         */            
        thisRGUI = res;
        thisEditGUI = this;
                
        // Additional window/frame settings
        setAlwaysOnTop(true); 

        /*Labelling the frame/window*/
        setTitle("Edit Appointment");      
        //Setting up program icon
        Image icon = thisRGUI.getIconImage();    
        setIconImage(icon);
        //Declaring layout
        setLayout(new BorderLayout());


        //=================================================//
        //=                SETTING UP PANELS              =//
        //=================================================//

        disPnl = new JPanel(new FlowLayout(FlowLayout.CENTER)); //For inputting area
        btnPnl = new JPanel(new GridBagLayout()); //For buttons


        //============================================//
        //=   STRUCTURING & CREATING DISPLAY PANEL   =//
        //============================================// 

        //Data should be loaded and the fields autofilled woth the appointment data in this section
        disPnl.setPreferredSize(new Dimension(430,400));
        disPnl.setBorder(new EmptyBorder(0, 1, 0, 2));
        disPnl.setBackground(mainWhite); 

        //FLATLAF stylings
        UIManager.put( "Spinner.arc", 27); 

        // CREATING AND ALLIGNING EDIT APPOINTMENT LABEL //               
        //(displayinnerpanel1) holds create appt label at top
        disinner1Pnl = new JPanel();
        disinner1Pnl.setPreferredSize(new Dimension(435,70));
        disinner1Pnl.setBackground(mainBlue);
        disinner1Pnl.setBorder(new EmptyBorder(16, 0, 0, 0));
        editAptLbl = new JLabel("Edit Appointment");
        editAptLbl.setHorizontalAlignment(JLabel.CENTER);
        editAptLbl.setOpaque(false);
        editAptLbl.setForeground(mainWhite);
        editAptLbl.setFont(new Font(editAptLbl.getFont().getFontName(), Font.BOLD, 19));        
        disinner1Pnl.add(editAptLbl);


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
        SpinnerModel washSpinModel = new SpinnerNumberModel(Integer.parseInt(selectedRowDataArray.get(3)), //initial value
         0, //min
         4, //max
         1);//step
        washSpinner = new JSpinner(washSpinModel);
        washSpinner.setPreferredSize(new Dimension(110,35));
        ((DefaultEditor)washSpinner.getEditor()).getTextField().setEditable(false);
        ((DefaultEditor)washSpinner.getEditor()).getTextField().setForeground(mainBlue);  
        ((DefaultEditor)washSpinner.getEditor()).getTextField().setFont(new Font(editAptLbl.getFont().getFontName(), Font.PLAIN, 15));   
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

        SpinnerModel drySpinModel = new SpinnerNumberModel(Integer.parseInt(selectedRowDataArray.get(4)), //initial value
         0, //min
         4, //max
         1);//step
        drySpinner = new JSpinner(drySpinModel);
        drySpinner.setPreferredSize(new Dimension(110,35));
        ((DefaultEditor)drySpinner.getEditor()).getTextField().setEditable(false);
        ((DefaultEditor)drySpinner.getEditor()).getTextField().setForeground(mainBlue);  
        ((DefaultEditor)drySpinner.getEditor()).getTextField().setFont(new Font(editAptLbl.getFont().getFontName(), Font.PLAIN, 15));   
         
        disinner2i2Pnl.add(drySpinner, BorderLayout.SOUTH);

        disinner2Pnl.add(disinner2i2Pnl, BorderLayout.LINE_END);       



        // CREATING AND ALLIGNING MONTH, DAY, YEAR, TIME, ATTENDANCE INPUT FIELDS //               
        //(displayinnerpanel3) holds both wash, dry labels and input fields inside display panel
        disinner3Pnl = new JPanel(); 
        disinner3Pnl.setBorder(BorderFactory.createLineBorder(mainBlue, 1));
        disinner3Pnl.setLayout(new BoxLayout(disinner3Pnl, BoxLayout.Y_AXIS));
        disinner3Pnl.setOpaque(false);
        disinner3Pnl.setPreferredSize(new Dimension(371,286));

        JPanel disinner3i1Pnl = new JPanel();
        disinner3i1Pnl.setOpaque(false);
        disinner3i1Pnl.setBorder(new EmptyBorder(7, 10, 20, 10)); 
        dateLbl = new JLabel("Date & Time");  
        dateLbl.setHorizontalAlignment(JLabel.CENTER);
        dateLbl.setOpaque(false);
        dateLbl.setForeground(mainBlue);
        dateLbl.setFont(new Font(dateLbl.getFont().getFontName(), Font.BOLD, 17));        
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
        String[] months = {"1 -Jan", "2 -Feb", "3 -Mar", "4 -Apr", 
                            "5 -May", "6 -June", "7 -July", "8 -Aug", "9 -Sept",
                            "10 -Oct", "11 -Nov", "12 -Dec"};        
        monthDropBox = new JComboBox<String>(months);
        //new code
        String[] date=selectedRowDataArray.get(1).split("/");
        String mth=date[1];
        String apptMth="";
        for (String m : months) {
            // Check if the element contains the search string
            if (m.contains(mth)) {
                apptMth=m;
                break; // Break the loop if found
            }
        }
        monthDropBox.setSelectedItem(apptMth);


        //Set selected month
        monthDropBox.setFont(new Font(monthDropBox.getFont().getFontName(), Font.BOLD, 15));        
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
        SpinnerModel daySpinModel = new SpinnerNumberModel(Integer.parseInt(date[0]), //initial value
         1, //min
         31, //max
         1);//step
        daySpinner = new JSpinner(daySpinModel);
        daySpinner.setPreferredSize(new Dimension(119,35));
        ((DefaultEditor)daySpinner.getEditor()).getTextField().setEditable(false);
        ((DefaultEditor)daySpinner.getEditor()).getTextField().setForeground(mainBlue);  
        ((DefaultEditor)daySpinner.getEditor()).getTextField().setFont(new Font(dayLbl.getFont().getFontName(), Font.PLAIN, 14));   
        
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
            count++;
        };        
        yearDropBox = new JComboBox<String>(years); 
        yearDropBox.setPreferredSize(new Dimension(119,35));
        yearDropBox.setForeground(mainBlue);
        yearDropBox.setFont(new Font(yearDropBox.getFont().getFontName(), Font.BOLD, 15));        
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
            count++;
        };               
        timeDropBox = new JComboBox<String>(times); 
        //new Code
        timeDropBox.setSelectedItem(selectedRowDataArray.get(2));
        timeDropBox.setFont(new Font(timeDropBox.getFont().getFontName(), Font.BOLD, 15));        
        timeDropBox.setForeground(mainBlue);
        timeDropBox.setPreferredSize(new Dimension(118,35));
        disinner3i5Pnl.add(timeDropBox, BorderLayout.SOUTH);    
        
        disinner3div2Pnl.add(disinner3i5Pnl, BorderLayout.LINE_END);    

        disinner3Pnl.add(disinner3div2Pnl);    



        JPanel disinner3div3Pnl = new JPanel();
        disinner3div3Pnl.setOpaque(false);
        disinner3div3Pnl.setPreferredSize(new Dimension(430,40));
        disinner3div3Pnl.setBorder(new EmptyBorder(0, 0, 40, 0));   

        attendedCheck = new JCheckBox(" Attended?");
        attendedCheck.setHorizontalAlignment(SwingConstants.CENTER);
        attendedCheck.setForeground(mainBlue);
        attendedCheck.setPreferredSize(new Dimension(118,35));
        attendedCheck.setFont(new Font(dayLbl.getFont().getFontName(), Font.BOLD, 16)); 
        disinner3div3Pnl.add(attendedCheck);  

        disinner3Pnl.add(disinner3div3Pnl);  
         


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


        // CREATING AND ALLIGNING DELETE BUTTON // 
        ImageIcon DeleteIcon = null;      
        try {
            DeleteIcon = (new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/pics/deleteicon.png").getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH)));
        } catch (Exception ioe) {
            System.out.println("Delete icon not found.");
        }  
        btnDelete = new JButton(DeleteIcon);
        btnDelete.setForeground(mainWhite);
        btnDelete.setBackground(mainBlue);
        btnDelete.setPreferredSize(new Dimension(50, 35));
        btnDelete.addActionListener(new DeleteBtnListener());
        
        // CREATING AND ALLIGNING APPLY EDITS BUTTON // 
        btnApply = new JButton("Apply Edits");
        btnApply.setFont(new Font(btnApply.getFont().getFontName(), Font.BOLD, 16));
        btnApply.setForeground(mainWhite);
        btnApply.setBackground(mainBlue);
        btnApply.setPreferredSize(new Dimension(135, 35));
        btnApply.addActionListener(new ApplyBtnListener());
        
        // CREATING AND ALLIGNING CANCEL BUTTON // 
        btnCancel = new JButton("Cancel");
        btnCancel.setFont(new Font(btnCancel.getFont().getFontName(), Font.BOLD, 16));
        btnCancel.setForeground(mainWhite);
        btnCancel.setBackground(mainBlue);
        btnCancel.setPreferredSize(new Dimension(110, 35));
        btnCancel.addActionListener(new CancelBtnListener());

        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 9, 0, 9);
        btnPnl.add(btnDelete, gbc);
        btnPnl.add(btnApply, gbc);
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
        setSize(440, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true); 

    } //public EditAppointmentGUI() end (constructor)



    
    //=========================================================//
    //=           BUTTON LISTENING FUNCTIONALITIES            =//
    //=========================================================//
    
    /**
     * This button listener handles taking the data from the input fields and updating it to the appointment database
     */
    private class ApplyBtnListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            //open dialog window with appointment details and ask for confirmation before updating database
            int washValueInt = (int) washSpinner.getValue();
            int dryValueInt = (int) drySpinner.getValue();
            int dayValueInt = (int) daySpinner.getValue();
            String monthValue = (String) monthDropBox.getSelectedItem();
            String[] monthParts = monthValue.split(" -");
            int monthInt=Integer.parseInt(monthParts[0]);

            String yearValue = (String) yearDropBox.getSelectedItem();
            int yearInt=Integer.parseInt(yearValue);
            String timeValue = (String) timeDropBox.getSelectedItem();
            String[] timeParts = timeValue.split(":");
            int hourInt=Integer.parseInt(timeParts[0]);
            MachineList mList=new MachineList();
            String washer_id=mList.assignWasherUpdate(Integer.parseInt(selectedRowDataArray.get(0)),washValueInt,yearInt,monthInt,dayValueInt,hourInt);
            String dryer_id=mList.assignDryerUpdate(Integer.parseInt(selectedRowDataArray.get(0)),washValueInt,yearInt,monthInt,dayValueInt,hourInt);
            Boolean attend =attendedCheck.isSelected();
            String app_date=yearValue+"-"+monthInt+"-"+dayValueInt;

            try{
                Database.updateAppointment(Integer.parseInt(selectedRowDataArray.get(0)),washValueInt, dryValueInt,app_date, monthInt, dayValueInt, yearInt, hourInt,washer_id,dryer_id,attend);
                setVisible(false);
                System.out.println("Appointment Edited");
            }catch(Exception ex){
                ex.printStackTrace();

                System.out.println("Could not add to Appoinment to the database");
                setVisible(false);
            }
        }

    }

    /**
     * This button listener handles removing the appointment from the appointment database
     */
    private class DeleteBtnListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            //ask for confirmation here before deleting
            try {
                Database.deleteAppointment(Integer.parseInt(selectedRowDataArray.get(0)));
                System.out.println("Appointment Deleted!");
                setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "An Error occured.", "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println("Could not delete appointment");
                ex.printStackTrace();
                setVisible(false);

            }
        }

    }
    

    /**
     * This button listener exits the current display screen and returns to the previous screen.
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

    

} //public class EditAppointmentGUI() end 
