import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String JDBC_URL = "jdbc:mysql://sql3.freesqldatabase.com:3306/sql3694739";
    private static final String USERNAME = "sql3694739";
    private static final String PASSWORD = "sEZ1JFRBF9";

    
    static {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Method to add a user
    public static void addUser(String type_user, String name, int id_num, String email, int room_num, String Block, String password) {
        String sql = "INSERT INTO user_information (type_user, name, id_num, email, room_num, Block, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, type_user);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, id_num);
            preparedStatement.setString(4, email);
            preparedStatement.setInt(5, room_num);
            preparedStatement.setString(6, Block);
            preparedStatement.setString(7, password);
            preparedStatement.executeUpdate();
            System.out.println("User added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the password for a user
    public static void updatePassword(int id_num, String newPassword) {
        String sql = "UPDATE user_information SET password = ? WHERE id_num = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, id_num);
            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Password updated successfully.");
            } else {
                System.out.println("No user found with id_num " + id_num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to search for a user by id_num and return a UserType object
    public static UserType selectUserById(int id_num) {
        String sql = "SELECT * FROM user_information WHERE id_num = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_num);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String type_user = resultSet.getString("type_user");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                int room_num = resultSet.getInt("room_num");
                String Block = resultSet.getString("Block");
                String password = resultSet.getString("password");
                return new UserType(type_user, name, id_num, email, room_num, Block, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to select all appointments for a specific ID number
    public static List<Appointment> getAppointmentsById(int id_num) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE id_num = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_num);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int appointment_num = resultSet.getInt("appointment_num");
                String name = resultSet.getString("name");
                int wash_num = resultSet.getInt("wash_num");
                int dry_num = resultSet.getInt("dry_num");
                int month = resultSet.getInt("month");
                int day = resultSet.getInt("day");
                int year = resultSet.getInt("year");
                int time = resultSet.getInt("time");
                boolean confirmed_by_resident = resultSet.getBoolean("confirmed_by_resident");
                boolean confirmed_by_staff = resultSet.getBoolean("confirmed_by_staff");
                String washer_id = resultSet.getString("washer_id");
                String dryer_id = resultSet.getString("dryer_id");

                // Create an Appointment object and add it to the list
                Appointment appointment = new Appointment(appointment_num, id_num, name, wash_num, dry_num, month, day, year, time, confirmed_by_resident, confirmed_by_staff, washer_id,dryer_id);
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }
    //get appointments for a specific day for staff
    public static List<Appointment> getAppointments(int year,int month, int day) {
        List<Appointment> appointments = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String query = "SELECT * FROM appointments WHERE month = ? AND day = ? AND year = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, month);
            statement.setInt(2, day);
            statement.setInt(3, year);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int appointmentNum = resultSet.getInt("appointment_num");
                int idNum = resultSet.getInt("id_num");
                String name = resultSet.getString("name");
                int washNum = resultSet.getInt("wash_num");
                int dryNum = resultSet.getInt("dry_num");
                boolean confirmedByResident = resultSet.getBoolean("confirmed_by_resident");
                boolean confirmedByStaff = resultSet.getBoolean("confirmed_by_staff");
                String washer_id=resultSet.getString("washer_id");
                String dryer_id=resultSet.getString("dryer_id");
                int time=resultSet.getInt("time");
                Appointment appointment = new Appointment(appointmentNum, idNum, name, washNum, dryNum, month, day,year, time, confirmedByResident, confirmedByStaff,washer_id,dryer_id);
                appointments.add(appointment);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }
    

    // Method to add an appointment
    public static void addAppointment(int id_num, String name, int wash_num, int dry_num, int month, int day, int year, int time, boolean confirmed_by_resident, boolean confirmed_by_staff, String washer_id,String dryer_id) {
        String sql = "INSERT INTO appointments (id_num, name, wash_num, dry_num, month, day, year, time, confirmed_by_resident, confirmed_by_staff,washer_id,dryer_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_num);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, wash_num);
            preparedStatement.setInt(4, dry_num);
            preparedStatement.setInt(5, month);
            preparedStatement.setInt(6, day);
            preparedStatement.setInt(7, year);
            preparedStatement.setInt(8, time);
            preparedStatement.setBoolean(9, confirmed_by_resident);
            preparedStatement.setBoolean(10, confirmed_by_staff);
            preparedStatement.setString(11, washer_id);
            preparedStatement.setString(12, dryer_id);
            preparedStatement.executeUpdate();
            System.out.println("Appointment added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete an appointment
    public static void deleteAppointment(int appointment_num) {
        String sql = "DELETE FROM appointments WHERE appointment_num = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, appointment_num);
            int deletedRows = preparedStatement.executeUpdate();
            if (deletedRows > 0) {
                System.out.println("Appointment deleted successfully.");
            } else {
                System.out.println("No appointment found with appointment_num " + appointment_num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update an appointment
    public static void updateAppointment(int appointment_num, int wash_num, int dry_num, int month, int day, int year, int time,String washer_id,String dryer_id,boolean confirmRes) {
        String sql = "UPDATE appointments SET wash_num = ?, dry_num = ?, month = ?, day = ?, year = ?, time = ?, washer_id = ?, dryer_id = ?, confirmed_by_resident=? WHERE appointment_num = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, wash_num);
            preparedStatement.setInt(2, dry_num);
            preparedStatement.setInt(3, month);
            preparedStatement.setInt(4, day);
            preparedStatement.setInt(5, year);
            preparedStatement.setInt(6, time);
            preparedStatement.setString(7, washer_id);
            preparedStatement.setString(8, dryer_id);
            preparedStatement.setBoolean(9, confirmRes);
            preparedStatement.setInt(10, appointment_num);
            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Appointment updated successfully.");
            } else {
                System.out.println("No appointment found with appointment_num " + appointment_num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the confirmed_by_resident column
    public static void updateConfirmedByResident(int appointment_num, boolean confirmedByResident) {
        String sql = "UPDATE appointments SET confirmed_by_resident = ? WHERE appointment_num = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBoolean(1, confirmedByResident);
            preparedStatement.setInt(2, appointment_num);
            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Confirmed by resident updated successfully.");
            } else {
                System.out.println("No appointment found with appointment_num " + appointment_num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the confirmed_by_staff column
    public static void updateConfirmedByStaff(int appointment_num, boolean confirmedByStaff) {
        String sql = "UPDATE appointments SET confirmed_by_staff = ? WHERE appointment_num = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBoolean(1, confirmedByStaff);
            preparedStatement.setInt(2, appointment_num);
            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Confirmed by staff updated successfully.");
            } else {
                System.out.println("No appointment found with appointment_num " + appointment_num);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Method to search appointments by month and year[Income Report]
    public static void selectAppointmentsByMonthAndYear(int month, int year) {
        String sql = "SELECT * FROM appointments WHERE month = ? AND year = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, month);
            preparedStatement.setInt(2, year);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int appointment_num = resultSet.getInt("appointment_num");
                int id_num = resultSet.getInt("id_num");
                String name = resultSet.getString("name");
                int wash_num = resultSet.getInt("wash_num");
                int dry_num = resultSet.getInt("dry_num");
                int day = resultSet.getInt("day");
                int time = resultSet.getInt("time");
                boolean confirmed_by_resident = resultSet.getBoolean("confirmed_by_resident");
                boolean confirmed_by_staff = resultSet.getBoolean("confirmed_by_staff");
                System.out.println("Appointment Number: " + appointment_num +
                                ", ID Number: " + id_num +
                                ", Name: " + name +
                                ", Wash Number: " + wash_num +
                                ", Dry Number: " + dry_num +
                                ", Day: " + day +
                                ", Time: " + time +
                                ", Confirmed by Resident: " + confirmed_by_resident +
                                ", Confirmed by Staff: " + confirmed_by_staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to select all machine IDs that are not booked during the same year, month, day, and time
    public static List<String> getWasherMachineIds(int year, int month, int day, int time) {
        String sql = "SELECT washer_id FROM appointments WHERE year = ? AND month = ? AND day = ? AND time = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, year);
            preparedStatement.setInt(2, month);
            preparedStatement.setInt(3, day);
            preparedStatement.setInt(4, time);
            ResultSet resultSet = preparedStatement.executeQuery();
            // Get all booked machine IDs
            List<String> bookedMachineIds = new ArrayList<>();
            
            while (resultSet.next()) {
                
                bookedMachineIds.add(resultSet.getString("washer_id"));
                
            } 
            /*MachineList availableMachines= new MachineList();
            List<String> machineIds=availableMachines.getAllMachineIds();
            machineIds.removeAll(bookedMachineIds);*/
            
            return bookedMachineIds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getDryerMachineIds(int year, int month, int day, int time) {
        String sql = "SELECT dryer_id FROM appointments WHERE year = ? AND month = ? AND day = ? AND time = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, year);
            preparedStatement.setInt(2, month);
            preparedStatement.setInt(3, day);
            preparedStatement.setInt(4, time);
            ResultSet resultSet = preparedStatement.executeQuery();
            // Get all booked machine IDs
            List<String> bookedMachineIds = new ArrayList<>();
            while (resultSet.next()) {
                bookedMachineIds.add(resultSet.getString("dryer_id"));
            } 
            /*MachineList availableMachines= new MachineList();
            List<String> machineIds=availableMachines.getAllMachineIds();
            machineIds.removeAll(bookedMachineIds);*/
            
            return bookedMachineIds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getDryerMachineIdsUpdate(int appointment_num,int year, int month, int day, int time) {
        String sql = "SELECT dryer_id FROM appointments WHERE appointment_num <>? AND year = ? AND month = ? AND day = ? AND time = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, appointment_num);
            preparedStatement.setInt(2, year);
            preparedStatement.setInt(3, month);
            preparedStatement.setInt(4, day);
            preparedStatement.setInt(5, time);
            ResultSet resultSet = preparedStatement.executeQuery();
            // Get all booked machine IDs
            List<String> bookedMachineIds = new ArrayList<>();
            while (resultSet.next()) {
                bookedMachineIds.add(resultSet.getString("dryer_id"));
            } 
            /*MachineList availableMachines= new MachineList();
            List<String> machineIds=availableMachines.getAllMachineIds();
            machineIds.removeAll(bookedMachineIds);*/
            
            return bookedMachineIds;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    

    // Method to select all machine IDs that are not booked during the same year, month, day, and time
    public static List<String> getWasherMachineIdsUpdate(int appointment_num,int year, int month, int day, int time) {
        String sql = "SELECT washer_id FROM appointments WHERE appointment_num <>? AND year = ? AND month = ? AND day = ? AND time = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, appointment_num);
            preparedStatement.setInt(2, year);
            preparedStatement.setInt(3, month);
            preparedStatement.setInt(4, day);
            preparedStatement.setInt(5, time);
            ResultSet resultSet = preparedStatement.executeQuery();
            // Get all booked machine IDs
            List<String> bookedMachineIds = new ArrayList<>();
            while (resultSet.next()) {
                bookedMachineIds.add(resultSet.getString("washer_id"));
            } 
            /*MachineList availableMachines= new MachineList();
            List<String> machineIds=availableMachines.getAllMachineIds();
            machineIds.removeAll(bookedMachineIds);*/
            
            return bookedMachineIds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getBookedTimes(int year, int month, int day, int time) {
        String sql="SELECT time FROM appointments WHERE month=? AND day=? GROUP BY month, day,time";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, month);
            preparedStatement.setInt(2, day);
            ResultSet resultSet = preparedStatement.executeQuery();
            // Get all booked machine IDs
            List<String> bookedTimes = new ArrayList<>();
            while (resultSet.next()) {
                bookedTimes.add(resultSet.getString("time"));
            } 
            /*MachineList availableMachines= new MachineList();
            List<String> machineIds=availableMachines.getAllMachineIds();
            machineIds.removeAll(bookedTimes);*/
            for (String str : bookedTimes) {
            }
            return bookedTimes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}









