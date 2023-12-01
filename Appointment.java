import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class Appointment {
    private LocalDateTime datetime;
    private int numCycles;
    private Resident resident;
    private String name;
    private int machineNumber; // New field for machine number
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    private static Set<Integer> assignedMachineNumbers = new HashSet<>();

    public Appointment(String name, int numCycles, LocalDateTime datetime, Resident resident) {
        this.name = name;
        this.numCycles = numCycles;
        this.datetime = datetime;
        this.resident = resident;
        this.machineNumber = assignMachineNumber();
    }

    public Appointment(String name, int numCycles, int year, int month, int day, int hour, int minute, Resident resident) {
        this.name = name;
        this.numCycles = numCycles;
        this.datetime = LocalDateTime.of(year, month, day, hour, minute);
        this.resident = resident;
        this.machineNumber = assignMachineNumber();
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

    @Override
    public String toString() {
        return "Appointment{" +
                "datetime=" + datetime +
                ", numCycles=" + numCycles +
                ", resident=" + resident +
                ", name='" + name + '\'' +
                ", machineNumber=" + machineNumber +
                '}';
    }
}
