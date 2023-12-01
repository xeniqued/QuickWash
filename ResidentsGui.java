import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ResidentsGui extends JFrame {
    private ArrayList<Appointment> appointmentList;
    private JTable appointmentsTable;
    private int selectedRow = -1;

    public ResidentsGui() {
        initComponents();
        appointmentList = new ArrayList<>();  // Initialize with an empty list
        populateTable(appointmentList);
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel sidePanel = createSidePanel();
        JPanel contentPanel = createContentPanel();

        mainPanel.add(sidePanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createSidePanel() {
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(new Color(51, 51, 255));

        JButton currentScheduleButton = createButton("My Schedule");
        JButton scheduleAppointmentButton = createButton("Schedule Appointment");
        JButton editAppointmentButton = createButton("Edit Appointment"); // New button

        JButton logoutButton = new JButton("Log Out");
        logoutButton.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, 18));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(204, 204, 204));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logOut();
            }
        });

        JLabel welcomeLabel = new JLabel("Hello <Resident>");
        welcomeLabel.setFont(new Font("Microsoft YaHei Light", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.WHITE);

        GroupLayout layout = new GroupLayout(sidePanel);
        sidePanel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(welcomeLabel)
                                        .addComponent(currentScheduleButton)
                                        .addComponent(scheduleAppointmentButton)
                                        .addComponent(editAppointmentButton) // Add Edit button
                                        .addComponent(logoutButton, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(welcomeLabel)
                                .addGap(112, 112, 112)
                                .addComponent(currentScheduleButton)
                                .addGap(18, 18, 18)
                                .addComponent(scheduleAppointmentButton)
                                .addGap(18, 18, 18)
                                .addComponent(editAppointmentButton) // Add Edit button
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                                .addComponent(logoutButton, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                                .addGap(74, 74, 74))
        );

        currentScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCurrentSchedule();
            }
        });

        scheduleAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showScheduleAppointmentDialog();
            }
        });

        editAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editSelectedAppointment();
            }
        });

        return sidePanel;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        //JLabel searchLabel = new JLabel("Search:");
        //JTextField searchField = new JTextField("Name, Date", 15);

        //JLabel sortByLabel = new JLabel("Sort By:");
        //String[] sortByOptions = {"Name", "Date"};
       // JComboBox<String> sortByComboBox = new JComboBox<>(sortByOptions);

        //searchPanel.add(searchLabel);
        //searchPanel.add(searchField);
        //searchPanel.add(sortByLabel);
       // searchPanel.add(sortByComboBox);

        contentPanel.add(searchPanel, BorderLayout.NORTH);

        appointmentsTable = new JTable(new DefaultTableModel(
                new Object[][]{{null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null}},
                new String[]{"FirstName", "LastName", "DryCycles", "Wash Cycles", "Time", "Date"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(appointmentsTable);
        contentPanel.add(tableScrollPane, BorderLayout.CENTER);

        // New buttons for Edit and Delete
        JButton editAppointmentButton = createButton("Edit Appointment");
        JButton deleteAppointmentButton = createButton("Delete Appointment");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(editAppointmentButton);
        buttonPanel.add(deleteAppointmentButton);

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners for Edit and Delete buttons
        editAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editSelectedAppointment();
            }
        });

        deleteAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedAppointment();
            }
        });

        return contentPanel;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Microsoft YaHei Light", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(51, 51, 255));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorderPainted(false);
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
                showCurrentSchedule();
                break;
            case "Schedule Appointment":
                showScheduleAppointmentDialog();
                break;
            case "Edit Appointment":
                editSelectedAppointment();
                break;
            case "Delete Appointment":
                deleteSelectedAppointment();
                break;
            default:
                break;
        }
    }

    private void showCurrentSchedule() {
        JOptionPane.showMessageDialog(this, "Displaying Current Schedule");
    }
    private void showScheduleAppointmentDialog() {
        String name = JOptionPane.showInputDialog(this, "Enter Name:");
        int dryCycles = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Dry Cycles:"));
        int washCycles = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Wash Cycles:"));
        int year = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Year:"));
        int month = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Month:"));
        int day = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Day:"));
        int hour = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Hour:"));
        int minute = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Minute:"));
    
        // Create a new appointment and add it to the list
        Resident resident = new Resident();
        Appointment newAppointment = new Appointment(name, dryCycles, year, month, day, hour, minute, resident);
        appointmentList.add(newAppointment);
    
        // Update the table with the new appointment
        addRowToTable(newAppointment);
    
        // Display machine number
        JOptionPane.showMessageDialog(this, "Appointment Scheduled!\nMachine Number: " + newAppointment.getMachineNumber());
    }

    private void addRowToTable(Appointment appointment) {
        DefaultTableModel model = (DefaultTableModel) appointmentsTable.getModel();
        String[] name = appointment.getName().split(" ");
        String[] rowData = {name[0], name[1], String.valueOf(appointment.getDryCycles()),
                String.valueOf(appointment.getNumCycles()), appointment.getDateTime(), appointment.getDate()};
        model.addRow(rowData);
    }

    private void populateTable(ArrayList<Appointment> appointmentList) {
        DefaultTableModel model = (DefaultTableModel) appointmentsTable.getModel();
        model.setRowCount(0);

        for (Appointment appointment : appointmentList) {
            addRowToTable(appointment);
        }
    }

    private void logOut() {
        new WelcomeScreen().setVisible(true);
        dispose();
    }

    private void editSelectedAppointment() {
        selectedRow = appointmentsTable.getSelectedRow();
        if (selectedRow != -1) {
            // Get the appointment from the selected row
            Appointment selectedAppointment = appointmentList.get(selectedRow);

            // After editing, update the table
            showEditAppointmentDialog(selectedAppointment);
        } else {
            JOptionPane.showMessageDialog(this, "Please select an appointment to edit.");
        }
    }

    private void showEditAppointmentDialog(Appointment appointment) {
        // Use input dialogs to get updated information
        String name = JOptionPane.showInputDialog(this, "Enter Name:", appointment.getName());
        int dryCycles = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Dry Cycles:", appointment.getDryCycles()));
        int washCycles = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Wash Cycles:", appointment.getNumCycles()));
        int year = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Year:", appointment.getYear()));
        int month = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Month:", appointment.getMonth()));
        int day = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Day:", appointment.getDate()));
        int hour = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Hour:", appointment.getHour()));
        int minute = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Minute:", appointment.getMinute()));

        // Update the appointment with the new information
        appointment.setName(name);
        appointment.setNumCycles(dryCycles);
        appointment.setNumCycles(washCycles);
        appointment.setYear(year);
        appointment.setMonth(month);
        appointment.setDay(day);
        appointment.setHour(hour);
        appointment.setMinute(minute);

        // Update the table with the edited appointment
        updateRowInTable(selectedRow, appointment);
    }

    private void deleteSelectedAppointment() {
        int selectedRow = appointmentsTable.getSelectedRow();
        if (selectedRow != -1) {
            // Remove the appointment from the list
            appointmentList.remove(selectedRow);

            // Remove the row from the table
            DefaultTableModel model = (DefaultTableModel) appointmentsTable.getModel();
            model.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Please select an appointment to delete.");
        }
    }

    private void updateRowInTable(int row, Appointment updatedAppointment) {
        DefaultTableModel model = (DefaultTableModel) appointmentsTable.getModel();
        String[] name = updatedAppointment.getName().split(" ");
        String[] rowData = {name[0], name[1], String.valueOf(updatedAppointment.getDryCycles()),
                String.valueOf(updatedAppointment.getNumCycles()), updatedAppointment.getDateTime(), updatedAppointment.getDate()};

        // Update the data in the specified row
        for (int i = 0; i < rowData.length; i++) {
            model.setValueAt(rowData[i], row, i);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ResidentsGui().setVisible(true);
            }
        });
    }
}
