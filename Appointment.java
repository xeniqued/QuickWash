import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class Appointment {
    private String name;
    private int numCycles;
    private String datetime;
    private String timeSlot;

    // Constructor
    public Appointment(String name, int numCycles, String datetime,String timeSlot) {
        this.name = name;
        this.numCycles = numCycles;
        this.datetime = datetime;
        this.timeSlot=timeSlot;
    }

    // Getters and setters (if needed)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumCycles() {
        return numCycles;
    }

    public void setNumCycles(int numCycles) {
        this.numCycles = numCycles;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    // Other methods as needed
    @Override
    public String toString() {
        return "Appointment{" +
                "name='" + name + '\'' +
                ", numCycles=" + numCycles +
                ", datetime=" + datetime +
                ", timeSlot=" + timeSlot +
                '}';
    }
}