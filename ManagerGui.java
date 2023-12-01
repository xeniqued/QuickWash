import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class ManagerGui extends JFrame {
    private ArrayList<Appointment> appointmentList;
    private JTable appointmentsTable;
    private int selectedRow = -1;

    public ManagerGui() {
        initComponents();
        appointmentList = new ArrayList<>();  // Initialize with an empty list
        populateTable(appointmentList);
    }

    private void populateTable(ArrayList<Appointment> appointmentList2) {
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

        JButton currentScheduleButton = createButton("Current Schedule");
        JButton scheduleAppointmentButton = createButton("Generate Machine Report");
        JButton generateIncomeReportButton = createButton("Generate Income Report");

        JButton logoutButton = new JButton("Log Out");
        logoutButton.setFont(new Font("Microsoft YaHei UI Light", Font.BOLD, 18));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(204, 204, 204));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               new WelcomeScreen().setVisible(true);
            }
        });

        JLabel welcomeLabel = new JLabel("Hello <Manager>");
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
                                        .addComponent(generateIncomeReportButton)
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
                                .addComponent(generateIncomeReportButton)
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
                showMachineReport();
            }
        });

        generateIncomeReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateIncomeReport();
            }
        });

        return sidePanel;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();

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

        JPanel buttonPanel = new JPanel();

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

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
            case "Generate Income Report":
                generateIncomeReport();
                break;
            default:
                break;
        }
    }

    private void showCurrentSchedule() {
        JOptionPane.showMessageDialog(this, "Displaying Current Schedule");
    }

    private void showScheduleAppointmentDialog() {
        // Implementation for scheduling appointment
    }

    private void showMachineReport() {
        new Machine_ReportGUI().setVisible(true);
    }

    private void generateIncomeReport() {
        JOptionPane.showMessageDialog(this, "Income Report Generated");
    }

 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ManagerGui().setVisible(true);
            }
        });
    }
}
