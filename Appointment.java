import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Jevon Hayles 620136482
 */
public class Appointment {
    private LocalDateTime datetime;
    private int numcycles;
    private String idnum;
    //This formatter can convert from a String object to a LocalDateTime object
    private static final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public Appointment(){
        
        datetime = LocalDateTime.of(2000, 01, 01, 00, 00);
        numcycles = 0;
        idnum = "";

    }

    public Appointment(LocalDateTime dateTime, int numcycles, String idnum){
        
        this.datetime = dateTime;
        this.numcycles = numcycles;
        this.idnum = idnum;

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

    public String getIdNum(){
        return idnum;
    }

    public static DateTimeFormatter getFormat(){
        return format;
    }


    /**Mutator Methods */
    //There is no mutator for the datetime object because LocalDateTime objects are immutable, to change an appointments date/time then a new appointment object needs to be created
    public void setNumCycles(int numcycles){
        this.numcycles = numcycles;
    }

    /**To string method to display values of each instance data */
    public String toString(){
        return "Date&Time: " + datetime.format(format) + ", " + "Number_Of_Cycles: " + numcycles + ", " + "ID_Number: " + idnum;
    }
}
