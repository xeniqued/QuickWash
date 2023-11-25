import java.time.*;

//for now the appointment class uses Strings for date and time, I'll implement the Java date and time objects at a later date
//if you need to use them for your part just @ me in the group and I'll fix it
public class Appointment {
    private String date;
    private String time;
    private int numcycles;
    private Resident resident;

    public Appointment(){
        
        date = "";
        time = "";
        numcycles = 0;
        resident = new Resident();

    }

    public Appointment(String date, String time, int numcycles, Resident resident){
        
        this.date = date;
        this.time = time;
        this.numcycles = numcycles;
        this.resident = resident;

    }


    /**Accessor Methods */
    public String getDate(){
        return date;
    }

    public String getTime(){
        return time;
    }

    public int getNumCycles(){
        return numcycles;
    }

    public Resident getResident(){
        return resident;
    }


    /**Mutator Methods */
    public void setDate(String date){
        this.date = date;
    }

    public void setTime(String time){
        this.time = time;
    }

    public void setNumCycles(int numcycles){
        this.numcycles = numcycles;
    }

    public void setResident(Resident resident){
        this.resident = resident;
    }
}
