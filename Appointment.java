import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Appointment {
    private LocalDateTime datetime;
    private int numCycles;
    private Resident resident;
    private String name;
    private int machineNumber; // New field for machine number
    private String timeSlot; // New field for time slot
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private static Set<Integer> assignedMachineNumbers = new HashSet<>();

    public Appointment(String name, int numCycles, LocalDateTime aDate, String timeSlot) {
        this.name = name;
        this.numCycles = numCycles;
        this.datetime = aDate;
        this.resident = new Resident();
        this.machineNumber = assignMachineNumber();
        this.timeSlot = timeSlot;
    }

    public String getDateTime() {
        return datetime != null ? datetime.format(FORMAT) : "";
    }

    public int getNumCycles() {
        return numCycles;
    }

    public Resident getResident() {
        return resident;
    }

    public String getName() {
        return name;
    }

    public int getMachineNumber() {
        return machineNumber;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public static DateTimeFormatter getFormat() {
        return FORMAT;
    }

    public void setNumCycles(int numCycles) {
        this.numCycles = numCycles;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public String getDryCycles() {
        return String.valueOf(numCycles);
    }

    public String getDate() {
        return datetime != null ? datetime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return datetime != null ? datetime.getYear() : 0;
    }

    public int getMonth() {
        return datetime != null ? datetime.getMonthValue() : 0;
    }

    public int getDay() {
        return datetime != null ? datetime.getDayOfMonth() : 0;
    }

    public int getHour() {
        return datetime != null ? datetime.getHour() : 0;
    }

    public int getMinute() {
        return datetime != null ? datetime.getMinute() : 0;
    }

    public void setYear(int year) {
        if (datetime != null) {
            datetime = datetime.withYear(year);
        }
    }

    public void setMonth(int month) {
        if (datetime != null) {
            datetime = datetime.withMonth(month);
        }
    }

    public void setDay(int day) {
        if (datetime != null) {
            datetime = datetime.withDayOfMonth(day);
        }
    }

    public void setHour(int hour) {
        if (datetime != null) {
            datetime = datetime.withHour(hour);
        }
    }

    public void setMinute(int minute) {
        if (datetime != null) {
            datetime = datetime.withMinute(minute);
        }
    }


    

    private int assignMachineNumber() {
        int maxMachines = 6;
        int randomMachine;
        do {
            randomMachine = (int) (Math.random() * maxMachines) + 1;
        } while (assignedMachineNumbers.contains(randomMachine));
    
        assignedMachineNumbers.add(randomMachine);
        return randomMachine;
    }
    

    private ArrayList<Appointment> tableData(String file) {
        Scanner ascan = null;
        ArrayList<Appointment> aList = new ArrayList<>();

        try {
            ascan = new Scanner(new File(file));
            while (ascan.hasNext()) {
                String data = ascan.nextLine();
                String[] nextLine = data.split(",");

                String name = nextLine[0];
                int numCycles = Integer.parseInt(nextLine[1]);
                String dateStr = nextLine[2];
                LocalDateTime aDate = LocalDateTime.parse(dateStr, FORMAT);  // Parse the date string
                String timeSlot = nextLine[3];

                Appointment app = new Appointment(name, numCycles, aDate, timeSlot);
                aList.add(app);
            }
            ascan.close();
        } catch (IOException e) {
            System.out.println("An error has occurred with reading the DATABASE");
        }

        return aList;
    }

    @Override
    public String toString() {
        return String.format("%s,%d,%s,%s", name, numCycles, timeSlot, getFormattedDate());
    }

    private String getFormattedDate() {
        return datetime != null ? datetime.format(FORMAT) : "";
    }

    public boolean isEmpty() {
        return false;
    }
}
