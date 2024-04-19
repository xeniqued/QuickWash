import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Database {
    private static final String JDBC_URL = "jdbc:mysql://sql3.freesqldatabase.com:3306/sql3694739";
    private static final String USERNAME = "sql3694739";
    private static final String PASSWORD = "sEZ1JFRBF9";
    public static  Connection connection;

    
    static {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection= DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            System.out.println("No internet Connection");
            //e.printStackTrace();
            //
        }
    }

    // Method to add a user
    public static void addUser(String type_user, String name, int id_num, String email, int room_num, String Block, String password) {
        String sql = "INSERT INTO user_information (type_user, name, id_num, email, room_num, Block, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
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

    // Method to check id number in database
    public static Boolean idNumberPresent(int id_num) {
        Boolean present=false;
        String sql = "SELECT id_num FROM user_information WHERE id_num = ?";
        try (
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id_num);
            ResultSet result = preparedStatement.executeQuery();
            if (!result.next()) {
                System.out.println("No Id number present");
                present=false;
            } else {
                System.out.println("Id number present");
                present=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return present;
    }

    // Method to update the password for a user
    public static void updatePassword(int id_num, String newPassword) {
        String sql = "UPDATE user_information SET password = ? WHERE id_num = ?";
        try (
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
    public static User selectUserById(int id_num) {
        String sql = "SELECT * FROM user_information WHERE id_num = ?";
        try (
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
                return new User(type_user, name, id_num, email, room_num, Block, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to select all appointments for a specific ID number
    public static List<Appointment> getAppointmentsById(int id_num) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE id_num = ? ORDER BY app_date DESC,time";
        try (
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
                String app_date= resultSet.getString("app_date");

                // Create an Appointment object and add it to the list
                Appointment appointment = new Appointment(appointment_num, id_num, name, wash_num, dry_num,app_date, month, day, year, time, confirmed_by_resident, confirmed_by_staff, washer_id,dryer_id);
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

        try  {
            String query = "SELECT * FROM appointments WHERE month = ? AND day = ? AND year = ? ORDER BY app_date DESC,time";
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
                String app_date= resultSet.getString("app_date");
                Appointment appointment = new Appointment(appointmentNum, idNum, name, washNum, dryNum,app_date, month, day,year, time, confirmedByResident, confirmedByStaff,washer_id,dryer_id);
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
    public static void addAppointment(int id_num, String name, int wash_num, int dry_num,String dateString, int month, int day, int year, int time, boolean confirmed_by_resident, boolean confirmed_by_staff, String washer_id,String dryer_id) {
        String sql = "INSERT INTO appointments (id_num, name, wash_num, dry_num, month, day, year, time, confirmed_by_resident, confirmed_by_staff,washer_id,dryer_id,app_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try{
            date = dateFormat.parse(dateString);
            // Convert java.util.Date to java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
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
            preparedStatement.setDate(13, sqlDate);
            preparedStatement.executeUpdate();
            System.out.println("Appointment added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to delete an appointment
    public static void deleteAppointment(int appointment_num) {
        String sql = "DELETE FROM appointments WHERE appointment_num = ?";
        try (
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
    public static void updateAppointment(int appointment_num, int wash_num, int dry_num,String dateString, int month, int day, int year, int time,String washer_id,String dryer_id,boolean confirmRes) {
        // Parse the string into a java.util.Date object
        String sql = "UPDATE appointments SET wash_num = ?, dry_num = ?, month = ?, day = ?, year = ?, time = ?, washer_id = ?, dryer_id = ?, confirmed_by_resident=?,app_date=? WHERE appointment_num = ?";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = dateFormat.parse(dateString);
            // Convert java.util.Date to java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, wash_num);
            preparedStatement.setInt(2, dry_num);
            preparedStatement.setInt(3, month);
            preparedStatement.setInt(4, day);
            preparedStatement.setInt(5, year);
            preparedStatement.setInt(6, time);
            preparedStatement.setString(7, washer_id);
            preparedStatement.setString(8, dryer_id);
            preparedStatement.setBoolean(9, confirmRes);
            preparedStatement.setDate(10, sqlDate);
            preparedStatement.setInt(11, appointment_num);
            int updatedRows = preparedStatement.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Appointment updated successfully.");
            } else {
                System.out.println("No appointment found with appointment_num " + appointment_num);
            }     
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        
    }

    // Method to update the confirmed_by_resident column
    public static void updateConfirmedByResident(int appointment_num, boolean confirmedByResident) {
        String sql = "UPDATE appointments SET confirmed_by_resident = ? WHERE appointment_num = ?";
        try (
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
        try (
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
    public static List<Appointment> selectTotCylesWithinRange(String frDateString, String toDateString) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT wash_num,dry_num,app_date FROM appointments WHERE app_date >= ? AND app_date <= ? AND confirmed_by_staff = 1 ORDER BY app_date;";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date frDateParse,toDateParse;
        try{
            frDateParse = dateFormat.parse(frDateString);
            toDateParse = dateFormat.parse(toDateString);

            // Convert java.util.Date to java.sql.Date
            java.sql.Date frDate = new java.sql.Date(frDateParse.getTime());
            java.sql.Date toDate = new java.sql.Date(toDateParse.getTime());
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, frDate);
            preparedStatement.setDate(2, toDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int wash_num=resultSet.getInt("wash_num");
                int dry_num=resultSet.getInt("dry_num");
                String app_date=resultSet.getDate("app_date").toString();
                Appointment app=new Appointment(wash_num,dry_num,app_date);
                appointments.add(app);
            }
            return appointments;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to select all machine IDs that are not booked during the same year, month, day, and time
    public static List<String> getWasherMachineIds(int year, int month, int day, int time) {
        String sql = "SELECT washer_id FROM appointments WHERE year = ? AND month = ? AND day = ? AND time = ?";
        try (
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
        try (
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
        try (
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
        try (
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
        try (
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

    public static List<String> getLoadNumsForAppts(int month,int day,int year,int time) {
        List<String> bookedTimes=new ArrayList<>();
        String appDateString;
        int washNum,dryNum;
        String sql = "SELECT DATE_FORMAT(app_date, '%Y-%m-%d') AS app_date_string , SUM(wash_num) AS totalWashNum, SUM(dry_num) AS totalDryNum FROM appointments WHERE MONTH(app_date) = ? AND DAY(app_date) = ? AND YEAR(app_date) = ? AND time = ?";
        try (
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, month);
            statement.setInt(2, day);
            statement.setInt(3, year);
            statement.setInt(4, time);
            ResultSet resultSet = statement.executeQuery();
            // Get all booked machine IDs
            while (resultSet.next()) {
                System.out.println("Result Set "+resultSet.getString("app_date_string"));
                if (resultSet.wasNull()) {
                    appDateString="";
                    washNum=0;
                    dryNum=0;

                }else{
                    appDateString = resultSet.getString("app_date_string");
                    washNum = resultSet.getInt("totalWashNum");
                    dryNum = resultSet.getInt("totalDryNum");
                }
                
                bookedTimes.add(appDateString); 
                bookedTimes.add(String.valueOf(washNum)); 
                bookedTimes.add(String.valueOf(dryNum));
            } 
            for (String str : bookedTimes) {
                System.out.println(str);
            }
            return bookedTimes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookedTimes;
    }

    public static Boolean isConnected() {
        try{
            // Ping a well-known server to check for internet connectivity
            InetAddress address = InetAddress.getByName("www.google.com");
            return address.isReachable(5000); // Timeout of 5 seconds
        } catch (IOException exception) {
            return false; // Unable to reach the server, so no internet connection
        }
    }



    


}









