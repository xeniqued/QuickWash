import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Jevon Hayles 620136482
 */
public class AppointmentList {
    public static ArrayList<Appointment> appointmentList = tableData("AppointmentListData.txt");

    //createAppointment with a resident
    public void createAppointment(LocalDateTime dateTime, int numcycles, Resident resident){
        //LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);
     //   Appointment appointment = new Appointment(dateTime, numcycles, resident.getIdNum());
     //   appointmentList.add(appointment);
    }

    //createAppointment with an idnum
    public static void createAppointment(LocalDateTime dateTime, int numcycles, String idnum){
        //LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);
     //   Appointment appointment = new Appointment(dateTime, numcycles, idnum);
     //   appointmentList.add(appointment);
    }

    //returns the index location of an appointment within the appointmentlist object using date and time
    //if the function returns -1, then the appointment was not found
    public int searchAppointment(int year, int month, int day, int hour, int minute){
        String search = "";
        int appointmentindex = -1;

        for(int i = 0; i < appointmentList.size(); i++){
            if(appointmentList.get(i).getDatetime() == search){
                appointmentindex = i;
            }
        }

        return appointmentindex;
    }

    public void deleteAppointment(int year, int month, int day, int hour, int minute){
        int index = searchAppointment(year, month, day, hour, minute);
        appointmentList.remove(index);
    }

    public void displayAppointments(){
        String l = appointmentList.get(1).toString();
        l = l.replace("{", "");
        System.out.println(l);
    }

    public static void addToFile(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("AppointmentListData.txt"));
            for(int i = 0; i < appointmentList.size(); i++){
                writer.write(appointmentList.get(i).getName() + "," + appointmentList.get(i).getNumCycles() + "," + appointmentList.get(i).getDatetime() + "," + appointmentList.get(i).getTimeSlot() + "\n");
            }
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public static ArrayList<Appointment> tableData(String file){//The file name is appointments
        Scanner ascan = null;
        ArrayList<Appointment> aList = new ArrayList<Appointment>();

        try{
            ascan  = new Scanner(new File(file));
            while(ascan.hasNext()){
                String data = ascan.nextLine(); 
                String[] nextLine = data.split(",");
                //Output: FirstName Lastname CustomerID Date TotalAmount-Wash TotalAmount-Dry $Amount
                String name=nextLine[0];
                int numCycles=Integer.parseInt(nextLine[1]);
                String aDate=nextLine[2];
                String timeSlot=nextLine[3];
                Appointment app=new Appointment(name,numCycles,aDate,timeSlot);
                aList.add(app);
            }
            ascan.close();
        }
        catch(IOException e){
            System.out.println("An error has occured with reading the DATABASE");
        }

        return aList;
        
    }
}