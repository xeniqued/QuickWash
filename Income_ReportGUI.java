import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/*Author Akele Benjamin 
 * ID number 620130803
*/
public class Income_ReportGUI extends JFrame {
    // Variables declaration - do not modify                     
    private JButton searchBtn;
    private JButton returnHomeBtn;
    private JLabel greeting;
    private JLabel pageHeader;
    private JLabel currentWeeksReportT;
    private JLabel lastWeekReportT;
    private JLabel TwoWeeksBeforeCurrentT;
    private JLabel currentWeekNum;
    private JLabel lastWeekNum;
    private JLabel twoWeeksNum;
    private JPanel greetingPanel;
    private JPanel reportPanel;
    private JPanel tablePanel;
    private JScrollPane tScrollPane;
    private JTable customeSalesTable;
    private JTextField searchBar;
    //new
    private ArrayList<CustomerSales> customerSalesList;
    private JTable customerSalesTable;
    private DefaultTableModel model;
    private TableRowSorter sorter;



    public Income_ReportGUI() {
        initComponents();
    }                        
    private void initComponents() {

        greetingPanel = new JPanel();
        greeting = new JLabel();
        pageHeader = new JLabel();
        reportPanel = new JPanel();
        currentWeeksReportT = new JLabel();
        lastWeekReportT = new JLabel();
        TwoWeeksBeforeCurrentT = new JLabel();
        currentWeekNum = new JLabel();
        lastWeekNum = new JLabel();
        twoWeeksNum = new JLabel();
        tablePanel = new JPanel();
        searchBar = new JTextField();
        searchBtn = new JButton();
        tScrollPane = new JScrollPane();
        customeSalesTable = new JTable();
        returnHomeBtn = new JButton();

        //set frame settings
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(879, 621));
        setBackground(new java.awt.Color(255, 255, 255));
        
        //color greeting Panel
        greetingPanel.setBackground(new java.awt.Color(51, 102, 255));

        //set JLabel for Greeting Panel
        greeting.setFont(new Font("Times New Roman", 0, 14)); // NOI18N
        greeting.setForeground(new java.awt.Color(255, 255, 255));
        greeting.setText("Welcome User");

        pageHeader.setFont(new Font("Times New Roman", 0, 12)); // NOI18N
        pageHeader.setForeground(new java.awt.Color(255, 255, 255));
        pageHeader.setText("Income Report Generator");

        

        //Greeting Panel Layout
        GroupLayout jPanel1Layout = new GroupLayout(greetingPanel);
        greetingPanel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(greeting, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
                .addGap(156, 156, 156)
                .addComponent(pageHeader, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 39, Short.MAX_VALUE)
                        .addComponent(greeting, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
                    .addComponent(pageHeader, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        customerSalesList=tableData("CustumerSalesData.txt");//reads text file

        //set Jlabel for Report Panel
        currentWeeksReportT.setFont(new Font("Times New Roman", 0, 11)); // NOI18N
        currentWeeksReportT.setText("Current Week's Report:");

        lastWeekReportT.setFont(new Font("Times New Roman", 0, 11)); // NOI18N
        lastWeekReportT.setText("Last Week's Report:");

        TwoWeeksBeforeCurrentT.setFont(new Font("Times New Roman", 0, 11)); // NOI18N
        TwoWeeksBeforeCurrentT.setText("Two Weeks Before Current Week's Report:");

        int currentIncome=calculateCurrentIncome(customerSalesList);
        currentWeekNum.setText(Integer.toString(currentIncome));

        int lastWeekIncome=calculateLastIncome(customerSalesList);
        lastWeekNum.setText(Integer.toString(lastWeekIncome));

         int twoWeeksBeforeIncome=calculate2WeeksBeforeIncome(customerSalesList);
        twoWeeksNum.setText(Integer.toString(twoWeeksBeforeIncome));

        //Report Panel color
        reportPanel.setBackground(new Color(255,255,255));

        //Set layout for Report Panel
        GroupLayout jPanel2Layout = new GroupLayout(reportPanel);
        reportPanel.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(TwoWeeksBeforeCurrentT)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(twoWeeksNum))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(currentWeeksReportT)
                            .addComponent(lastWeekReportT))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(lastWeekNum)
                            .addComponent(currentWeekNum))))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(currentWeeksReportT)
                    .addComponent(currentWeekNum))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lastWeekReportT)
                    .addComponent(lastWeekNum))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(TwoWeeksBeforeCurrentT)
                    .addComponent(twoWeeksNum))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        // Table
        
        String[] columnNames = { "First Name","Last Name","CustomerID","Date","Total Amount Wash","Total Amount Dry","Amount($)" };
        model=new DefaultTableModel(columnNames,0);
        sorter = new TableRowSorter<>(model);
        customerSalesTable= new JTable(model);
        customerSalesTable.setRowSorter(sorter);

        
        showTable(customerSalesList); //display table
        
        customerSalesTable.setPreferredScrollableViewportSize(new Dimension(500, customerSalesList.size()*15 +50));
        customerSalesTable.setFillsViewportHeight(true);

        tScrollPane = new JScrollPane(customerSalesTable);

       
        //Return Home Button
        returnHomeBtn.setText("Return Home");
        returnHomeBtn.setBackground(new java.awt.Color(51, 102, 255));
        returnHomeBtn.setForeground(new java.awt.Color(255, 255, 255));
        returnHomeBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                
            }
        });

        //Table Panel color
        tablePanel.setBackground(new Color(255,255,255));

        //Table Panel Layout
        GroupLayout jPanel3Layout = new GroupLayout(tablePanel);
        tablePanel.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED))
                    .addComponent(tScrollPane, GroupLayout.DEFAULT_SIZE, 677, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(returnHomeBtn, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
                .addGap(273, 273, 273))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE))
                .addGap(4, 4, 4)
                .addComponent(tScrollPane, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(returnHomeBtn)
                .addContainerGap(193, Short.MAX_VALUE))
        );

        //Frame Layout
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(greetingPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(reportPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(tablePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(greetingPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reportPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tablePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>  

    
    
    private ArrayList<CustomerSales> tableData(String file){
        Scanner sscan = null;
        ArrayList<CustomerSales> salesList = new ArrayList<CustomerSales>();

        try{
            sscan  = new Scanner(new File(file));
            while(sscan.hasNext()){
                String data = sscan.nextLine(); 
                String[] nextLine = data.split("@");
                //Output: FirstName Lastname CustomerID Date TotalAmount-Wash TotalAmount-Dry $Amount
                String fName=nextLine[0];
                String lName=nextLine[1];
                int staffID=Integer.parseInt(nextLine[2]);
                String cDate=nextLine[3];
                int totalNumWash=Integer.parseInt(nextLine[4]);
                int totalNumDry=Integer.parseInt(nextLine[5]);
                int totAmount=Integer.parseInt(nextLine[6]);
                
                CustomerSales customer=new CustomerSales(fName,lName,staffID,cDate,totalNumWash,totalNumDry,totAmount);
                salesList.add(customer);
            }
            sscan.close();
        }
        catch(IOException e){
            System.out.println("An error has occured with reading the DATABASE");
        }

        return salesList;


    }
    
    private void showTable(ArrayList<CustomerSales> clist)
    {
        DefaultTableModel model = (DefaultTableModel) customeSalesTable.getModel();
        model.setRowCount(0);
        if (clist.size()>0)
        {
            for(CustomerSales c: clist)
            {
                addToTable(c);
            }
        }
    }

    private void addToTable(CustomerSales c)
    {
        
        String[] cSale={c.getfName(),""+c.getlName(),""+c.getCustomerID(),""+c.getDate(),""+c.getTotalAmountWash(),""+c.getTotalAmountDry(),""+c.getTotalAmount(),""};
        model.addRow(cSale);
    }

    private int calculateCurrentIncome(ArrayList<CustomerSales> cuslist)
    {
        int totCSale=0;
        if (cuslist.size()>0)
        {
            /*Calendar c = Calendar.getInstance();
            c.setTime(yourDate);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) */
            for(CustomerSales c: cuslist)
            {
                try {
                    SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
                    Date cDate= sdf.parse(c.getDate());
                    int weekOfYear= getWeekOfYear(cDate);

                    

        // Get the week of the year
                    Calendar calendar = Calendar.getInstance();
                    int currentWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);


                    if(weekOfYear==currentWeekOfYear){
                        totCSale=((c.getTotalAmountDry() + c.getTotalAmountWash()) *400)+totCSale ;   
                    }                    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Date Format not suitable","Error",JOptionPane.ERROR_MESSAGE);
                }
                
            }
            
        }
        return totCSale;
    }

    private int calculateLastIncome(ArrayList<CustomerSales> cuslist)
    {
        int totCSale=0;
        if (cuslist.size()>0)
        {
            /*Calendar c = Calendar.getInstance();
            c.setTime(yourDate);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) */
            for(CustomerSales c: cuslist)
            {
                try {
                    SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
                    Date cDate= sdf.parse(c.getDate());
                    int weekOfYear= getWeekOfYear(cDate);

                    

        // Get the week of the year
                    Calendar calendar = Calendar.getInstance();
                    int currentWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);

                    if(weekOfYear==(currentWeekOfYear-1)){
                        totCSale=((c.getTotalAmountDry() + c.getTotalAmountWash()) *400)+totCSale ;   
                    }                    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Date Format not suitable","Error",JOptionPane.ERROR_MESSAGE);
                }
                
            }
            
        }
        return totCSale;
    }

    private int calculate2WeeksBeforeIncome(ArrayList<CustomerSales> cuslist)
    {
        int totCSale=0;
        if (cuslist.size()>0)
        {
            /*Calendar c = Calendar.getInstance();
            c.setTime(yourDate);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) */
            for(CustomerSales c: cuslist)
            {
                try {
                    SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
                    Date cDate= sdf.parse(c.getDate());
                    int weekOfYear= getWeekOfYear(cDate);

                    

        // Get the week of the year
                    Calendar calendar = Calendar.getInstance();
                    int currentWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);

                    if(weekOfYear==(currentWeekOfYear-2)){
                        totCSale=((c.getTotalAmountDry() + c.getTotalAmountWash()) *400)+totCSale ;   
                    }                    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,"Date Format not suitable","Error",JOptionPane.ERROR_MESSAGE);
                }
                
            }
            
        }
        return totCSale;
    }

    

    private static int getWeekOfYear(Date date) {
        // Create a Calendar instance and set the given date
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);

        // Get the week of the year
        return calendar.get(java.util.Calendar.WEEK_OF_YEAR);
    }



    private void jTextField1ActionPerformed(ActionEvent evt) {                                            
    }                                           

    private void jButton2ActionPerformed(ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Income_ReportGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Income_ReportGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Income_ReportGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Income_ReportGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Income_ReportGUI().setVisible(true);
            }
        });
    }                  
}
