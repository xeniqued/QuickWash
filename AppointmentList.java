import java.util.ArrayList;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class AppointmentList {
    private static ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();

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
    
}