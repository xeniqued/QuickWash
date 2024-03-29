import javax.swing.*;
import javax.swing.table.*;  
import javax.swing.border.*;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.event.ListSelectionListener;

/**
 * This class generates a table for displaying the trip data.
 */

public class TableRenderer {
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private String[] columnNames;
    private ArrayList<String[]> tableData;

    private Color mainBlue = new Color(10, 87, 162);
    private Color mainWhite = new Color(255, 255, 255);

    
    /**
     * This sets up the table to put in the given panel.
     * @param pnl is the panel where the table will be displayed.
     * @param displaySize is the size of the table's display.
     * @param columnNames is a string array for the names of the table's columns.
     * @param tableData a 2D string array featuring the table's data.
     */
    public TableRenderer(JPanel pnl, Dimension displaySize, String[] columnNames, ArrayList<String[]> tableData) {    
        
        //storing the data in the class for later
        this.tableData = tableData;
        this.columnNames = columnNames;

        model = new DefaultTableModel(this.columnNames, 0);
        for (int i = 0; i < this.tableData.size(); i++) {
            model.addRow(this.tableData.get(i));  
        }

        //FLATLAF table styling
        UIManager.put( "Table.selectionBackground", new Color(85, 151, 216));

        
        // CREATING AND SETTING UP TABLE APPEARANCE // 
        table = new JTable(model){
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
               return false;
            }
        };     
        table.setGridColor(mainBlue);
        table.setShowGrid(true);   
        table.setBorder(new MatteBorder(0, 1, 1, 1, mainBlue));

        table.setRowHeight(35);
        table.setFont(new Font(table.getFont().getFontName(), Font.BOLD, 15));
        table.setForeground(mainBlue);

        //UIManager.getDefaults().put("TableHeader.cellBorder", BorderFactory.createLineBorder(mainBlue));

        table.getTableHeader().setPreferredSize(new Dimension(0, 45));
        table.getTableHeader().setBorder(new MatteBorder(1, 1, 1, 1, mainBlue));
        table.getTableHeader().setFont(new Font(table.getTableHeader().getFont().getFontName(), Font.BOLD, 16));
        table.getTableHeader().setForeground(mainWhite);
        table.getTableHeader().setBackground(mainBlue);

        
        DefaultTableCellRenderer timesRenderer = new DefaultTableCellRenderer();
        timesRenderer.setFont(new Font(table.getFont().getFontName(), Font.BOLD, 50));
        timesRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(timesRenderer);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < this.columnNames.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        
        // ADDITIONAL SETUP AND ADDING TABLE TO GIVEN JPANEL // 
        table.setPreferredScrollableViewportSize(displaySize);
        table.setFillsViewportHeight(true);
        scrollPane = new JScrollPane(table);
        pnl.add(scrollPane, BorderLayout.SOUTH);
        
    }

    /**
     * This returns the table's model. 
     */
    public DefaultTableModel getModel(){
        return model;
    }    

    public JTable getTable(){
        return table;
    }    
    
    public int getSelectedRow(){
        //System.out.print(table.getSelectedRow());
        return table.getSelectedRow();
    }

    public String[] getTimeDateData(){
        int rowNum = getSelectedRow();
        String[] tableTimeandDate = new String[2];
        
        for (int i = 0; i <= 1; i++) {
            System.out.println(table.getValueAt(rowNum, i).toString());
            tableTimeandDate[i] = table.getValueAt(rowNum, i).toString();
        }
        
        return tableTimeandDate;
        
    }

    public void updateRow(String[] data, int row, int columnNum){        
        for (int i = 0; i <= columnNum; i++) {
            model.setValueAt(data[i], row, i);            
        }
    }

}