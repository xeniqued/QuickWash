import java.time.*;
import java.time.format.DateTimeFormatter;

public class Appointment {
    private LocalDateTime datetime;
    private int numcycles;
    private Resident resident;
    //This formatter can convert from a String object to a LocalDateTime object
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public Appointment(){
        
        datetime = LocalDateTime.of(2000, 01, 01, 00, 00);
        numcycles = 0;
        resident = new Resident();

    }

    public Appointment(int year, int month, int day, int hour, int minute, int numcycles, Resident resident){
        
        this.datetime = LocalDateTime.of(year, month, day, hour, minute);
        this.numcycles = numcycles;
        this.resident = resident;

    }


    /**Accessor Methods */
    //This formats the LocalDateTime datetime object into a String of format dd-MM-yyyy HH:MM eg. 05-12-2023 14:20
    //made static so that it can be called without an instance of the Appointment class existing
    public String getDateTime(){
        String dt = datetime.format(format);
        return dt;
    }

    public int getNumCycles(){
        return numcycles;
    }

    public Resident getResident(){
        return resident;
    }

    public static DateTimeFormatter getFormat(){
        return format;
    }


    /**Mutator Methods */
    //There is no mutator for the datetime object because LocalDateTime objects are immutable, to change an appointments date/time then a new appointment object needs to be created
    public void setNumCycles(int numcycles){
        this.numcycles = numcycles;
    }

    public void setResident(Resident resident){
        this.resident = resident;
    }
}
