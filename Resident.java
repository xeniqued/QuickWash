import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Resident {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ResidentFrame frame = new ResidentFrame();
            frame.showFrame();
        });
    }
}

class ResidentFrame {
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JTable weekTable;
    private JPanel buttonPanel;

    public void showFrame() {
        frame = new JFrame("Resident GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
        cardPanel.setBackground(new Color(255, 255, 255));

        buttonPanel = createButtonPanel();
        cardPanel.add(buttonPanel, "buttonPanel");

        weekTable = createWeekTable();
        showNovemberHeading();

        JLabel hiResidentLabel = new JLabel("Hi Resident", SwingConstants.LEFT);
        hiResidentLabel.setBackground(new Color(70, 130, 180));
        hiResidentLabel.setForeground(Color.WHITE);
        hiResidentLabel.setOpaque(true);

        frame.add(hiResidentLabel, BorderLayout.NORTH);
        frame.add(cardPanel);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(255, 255, 255));

        JButton editButton = createButton("My Schedule");
        JButton deleteButton = createButton("Edit an Appointment");
        JButton blahButton = createButton("Schedule an Appointment");
        JButton changeUserButton = createButton("Change User Type");
        JButton logoutButton = createButton("Log Out");

        buttonPanel.add(Box.createVerticalStrut(20)); // Add vertical spacing
        buttonPanel.add(editButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(deleteButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(blahButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(changeUserButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(logoutButton); // Log Out button

        return buttonPanel;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 30));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick(text);
            }
        });

        return button;
    }

    private void handleButtonClick(String buttonText) {
        switch (buttonText) {
            case "My Schedule":
                cardLayout.show(cardPanel, "tablePanel");
                break;
            case "Edit an Appointment":
                int selectedRow = weekTable.getSelectedRow();
                if (selectedRow != -1) {
                    showEditAppointmentDialog(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select an appointment to edit.");
                }
                break;
            case "Schedule an Appointment":
                showScheduleAppointmentDialog();
                break;
            case "Back":
                cardLayout.show(cardPanel, "buttonPanel");
                break;
            case "Change User Type":
                frame.dispose();
                UserType.showUserTypeDialog();
                break;
            case "Log Out":
                
                frame.dispose(); // Close the  frame 
                break;
            default:
                System.out.println(buttonText + " button clicked");
        }
    }

    private JTable createWeekTable() {
        String[] columnNames = {"Name", "Day", "Time", "Wash", "Dry"};

        Object[][] rowData = {
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""},
                {"", "", "", "", ""}
        };

        DefaultTableModel model = new DefaultTableModel(rowData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable weekTable = new JTable(model);
        weekTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        weekTable.setFillsViewportHeight(true);

        return weekTable;
    }

    private void showNovemberHeading() {
        JPanel headingPanel = new JPanel();
        headingPanel.setBackground(new Color(70, 130, 180));
        headingPanel.setLayout(new BorderLayout());

        JLabel headingLabel = new JLabel("Week of November 26- December 3", SwingConstants.CENTER);
        headingLabel.setForeground(Color.WHITE);
        headingLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        headingPanel.add(headingLabel, BorderLayout.CENTER);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(245, 245, 245));
        tablePanel.add(headingPanel, BorderLayout.NORTH);
        tablePanel.add(new JScrollPane(weekTable), BorderLayout.CENTER);
        tablePanel.add(createButton("Back"), BorderLayout.SOUTH);

        cardPanel.add(tablePanel, "tablePanel");
        cardLayout.show(cardPanel, "buttonPanel");
    }

    private void showScheduleAppointmentDialog() {
        ScheduleAppointmentDialog dialog = new ScheduleAppointmentDialog(frame, weekTable);
        dialog.setVisible(true);
    }

    private void showEditAppointmentDialog(int selectedRow) {
        EditAppointmentDialog dialog = new EditAppointmentDialog(frame, weekTable, selectedRow);
        dialog.setVisible(true);
    }
}

class ScheduleAppointmentDialog extends JDialog {
    private JTextField nameField;
    private JComboBox<String> dayComboBox;
    private JComboBox<String> timeComboBox;
    private JComboBox<String> washComboBox;
    private JComboBox<String> dryComboBox;
    private JTable weekTable;

    public ScheduleAppointmentDialog(JFrame parent, JTable table) {
        super(parent, "Schedule Appointment", true);
        this.weekTable = table;

        setLayout(new GridLayout(6, 2));

        nameField = new JTextField();
        dayComboBox = new JComboBox<>(new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"});
        timeComboBox = new JComboBox<>(new String[]{"6 AM", "7 AM", "8 AM", "9 AM", "10 AM", "11 AM", "12 PM"});
        washComboBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        dryComboBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});

        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Day:"));
        add(dayComboBox);
        add(new JLabel("Time:"));
        add(timeComboBox);
        add(new JLabel("Wash:"));
        add(washComboBox);
        add(new JLabel("Dry:"));
        add(dryComboBox);

        JButton addButton = new JButton("Schedule");
        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAppointmentToTable();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(70, 130, 180));
        cancelButton.setForeground(Color.WHITE);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        add(addButton);
        add(cancelButton);

        pack();
        setLocationRelativeTo(parent);
    }

    private void addAppointmentToTable() {
        String name = nameField.getText();
        String day = (String) dayComboBox.getSelectedItem();
        String time = (String) timeComboBox.getSelectedItem();
        String wash = (String) washComboBox.getSelectedItem();
        String dry = (String) dryComboBox.getSelectedItem();

        DefaultTableModel model = (DefaultTableModel) weekTable.getModel();
        model.insertRow(0, new Object[]{name, day, time, wash, dry});

        dispose();
    }
}

class EditAppointmentDialog extends JDialog {
    private JTextField nameField;
    private JComboBox<String> dayComboBox;
    private JComboBox<String> timeComboBox;
    private JComboBox<String> washComboBox;
    private JComboBox<String> dryComboBox;
    private JTable weekTable;
    private int selectedRow;

    public EditAppointmentDialog(JFrame parent, JTable table, int selectedRow) {
        super(parent, "Edit Appointment", true);
        this.weekTable = table;
        this.selectedRow = selectedRow;

        setLayout(new GridLayout(6, 2));

        nameField = new JTextField();
        dayComboBox = new JComboBox<>(new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"});
        timeComboBox = new JComboBox<>(new String[]{" 6 AM", "7 AM", "8 AM", "9 AM", "10 AM", "11 AM", "12 PM"});
        washComboBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        dryComboBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});

        add(new JLabel("Name:"));
        add(nameField);
        add(new JLabel("Day:"));
        add(dayComboBox);
        add(new JLabel("Time:"));
        add(timeComboBox);
        add(new JLabel("Wash:"));
        add(washComboBox);
        add(new JLabel("Dry:"));
        add(dryComboBox);

        populateFields();

        JButton updateButton = new JButton("Update");
        updateButton.setBackground(new Color(70, 130, 180));
        updateButton.setForeground(Color.WHITE);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAppointmentInTable();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(new Color(70, 130, 180));
        cancelButton.setForeground(Color.WHITE);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        add(updateButton);
        add(cancelButton);

        pack();
        setLocationRelativeTo(parent);
    }

    private void populateFields() {
        DefaultTableModel model = (DefaultTableModel) weekTable.getModel();
        nameField.setText((String) model.getValueAt(selectedRow, 0));
        dayComboBox.setSelectedItem(model.getValueAt(selectedRow, 1));
        timeComboBox.setSelectedItem(model.getValueAt(selectedRow, 2));
        washComboBox.setSelectedItem(model.getValueAt(selectedRow, 3));
        dryComboBox.setSelectedItem(model.getValueAt(selectedRow, 4));
    }

    private void updateAppointmentInTable() {
        String name = nameField.getText();
        String day = (String) dayComboBox.getSelectedItem();
        String time = (String) timeComboBox.getSelectedItem();
        String wash = (String) washComboBox.getSelectedItem();
        String dry = (String) dryComboBox.getSelectedItem();

        DefaultTableModel model = (DefaultTableModel) weekTable.getModel();
        model.setValueAt(name, selectedRow, 0);
        model.setValueAt(day, selectedRow, 1);
        model.setValueAt(time, selectedRow, 2);
        model.setValueAt(wash, selectedRow, 3);
        model.setValueAt(dry, selectedRow, 4);

        dispose();
    }
}

class UserType {
    public static void showUserTypeDialog() {
        JOptionPane.showMessageDialog(null, "User type selection dialog will be shown here.");
    }
}
