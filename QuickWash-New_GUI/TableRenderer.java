import javax.swing.*;
import javax.swing.table.*;  
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.event.ListSelectionListener;

/**
 * This class generates a table for displaying the trip data.
 */

public class TableRenderer {
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private String[] columnNames;
    private String[][] tableData;

    private Color mainBlue = new Color(10, 87, 162);
    private Color mainWhite = new Color(255, 255, 255);

    private int selectedRow;
    private RowSelectionListener listener;

    /**
     * This sets up the table to put in the given panel.
     * @param pnl is the panel where the table will be displayed.
     * @param displaySize is the size of the table's display.
     * @param columnNames is a string array for the names of the table's columns.
     * @param tableData a 2D string array featuring the table's data.
     */
    public TableRenderer(JPanel pnl, Dimension displaySize, String[] columnNames, List<String[]> tabledata) {    
        
        //storing the data in the class for later
        this.tableData = tableData;
        this.columnNames = columnNames;
        this.selectedRow=selectedRow;

        model = new DefaultTableModel(this.columnNames, 0);
        for (int i = 0; i < this.tableData.length; i++) {
            model.addRow(this.tableData[i]);  
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

        //Table Listener
        // Add list selection listener to the table
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1 && listener != null) {
                Vector<?> selectedRowData = (Vector<?>) model.getDataVector().get(selectedRow);
                listener.onRowSelected(selectedRowData);
            }
        });
        
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
    
    //new
    // Method to extract data from the selected row
    private void extractDataFromSelectedRow(int selectedRow) {
        if (selectedRow != -1) { // If a row is selected
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Vector<?> rowData = (Vector<?>) model.getDataVector().get(selectedRow);
            System.out.println("Selected Row Data: " + rowData);
            
            // Here, you can process or use the rowData as needed
        } else {
            System.out.println("No row is selected.");
        }
    }

    // Inner class for table selection listener
    private class TableSelectionListener implements ListSelectionListener {
        private ResidentGUI residentGUI; // Reference to ResidentGUI class

        public TableSelectionListener(ResidentGUI residentGUI) {
            this.residentGUI = residentGUI; // Initialize reference to ResidentGUI class
        }

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) { // To avoid firing event twice
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    Vector<?> rowData = (Vector<?>) model.getDataVector().get(selectedRow);

                    // Pass selected row data to ResidentGUI class
                    residentGUI.processSelectedRow(rowData);
                } else {
                    System.out.println("No row is selected.");
                }
            }
        }
    }

    public JTable getTable(){
        return this.table;
    }

    public void setRowSelectionListener(RowSelectionListener listener) {
        this.listener = listener;
    }

    interface RowSelectionListener {
        void onRowSelected(Vector<?> rowData);
    }
}