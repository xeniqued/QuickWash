import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class AppointmentList {
    public static ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();

    public void createAppointment(int year, int month, int day, int hour, int minute, int numcycles, Resident resident){
        Appointment appointment = new Appointment(year, month, day, hour, minute, numcycles, resident);
        appointmentList.add(appointment);
    }

    //returns the index location of an appointment within the appointmentlist object using date and time
    //if the function returns -1, then the appointment was not found
    public int searchAppointment(int year, int month, int day, int hour, int minute){
        DateTimeFormatter format = Appointment.getFormat();
        LocalDateTime search = LocalDateTime.of(year, month, day, hour, minute);
        int appointmentindex = -1;

        for(int i = 0; i < appointmentList.size(); i++){
            if(appointmentList.get(i).getDateTime() == search.format(format)){
                appointmentindex = i;
            }
        }

        return appointmentindex;
    }

    public void deleteAppointment(int year, int month, int day, int hour, int minute){
        int index = searchAppointment(year, month, day, hour, minute);
        appointmentList.remove(index);
    }

    public String displayAppointments(){
        String allAppointments = appointmentList.toString();
        return allAppointments;
    }

    public static void addToFile(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("AppointmentListData.txt"));
            for(int i = 0; i < AppointmentList.appointmentList.size(); i++){
                writer.write(AppointmentList.appointmentList.get(i).toString() + "\n");
            }
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public static void readFromFile(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("AppointmentListData.txt"));
            String line;
            while( (line = reader.readLine()) != null){
                line = line.replace(",","");
                String[] lineArr = line.split(" ");

                //used to print the split array
                for (int i = 0; i < lineArr.length; i++){
                    System.out.println(lineArr[i]);
                }

            }
            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}