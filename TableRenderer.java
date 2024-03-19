import javax.swing.*;
import javax.swing.table.*;  
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

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

    /**
     * This sets up the table to put in the given panel.
     * @param pnl is the panel where the table will be displayed.
     */
    public TableRenderer(JPanel pnl, Dimension displaySize, String[] columnNames, String[][] tableData) {    
        
        this.tableData = tableData;
        this.columnNames = columnNames;

        model = new DefaultTableModel(this.columnNames, 0);
        for (int i = 0; i < this.tableData.length; i++) {
            model.addRow(this.tableData[i]);  
        }

        UIManager.put( "Table.selectionBackground", new Color(85, 151, 216));

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

    //=========================================================//
    //=          BUTTON LISTENING FUNCTIONALITIES             =//
    //=========================================================//

    /**
     * This implements Sign Up Button functionalities
     */
    
}