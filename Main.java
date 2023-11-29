import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        //*adding info to arraylist to test */
        Resident res = new Resident("User", "A", "B", "ba", "123", 12, "zz@gmail.com");
        Appointment app = new Appointment(2009, 12, 5, 11, 30, 2, res);
        AppointmentList.appointmentList.add(app);

        res = new Resident("User", "CCC", "XXX", "CCCXXX", "123443", 5452, "SDSDAASD@gmail.com");
        app = new Appointment(1999, 11, 2, 17, 55, 3, res);
        AppointmentList.appointmentList.add(app);
        //* */
        AppointmentList.readFromFile();
    }
}
