import java.time.LocalDate;

public class Appointment {
    private int appointmentNum;
    private int idNum;
    private String name;
    private int washNum;
    private int dryNum;
    private int month;
    private int day;
    private int year;
    private int time;
    private boolean confirmedByResident;
    private boolean confirmedByStaff;
    private int machineNum;
    private int appointment_num;
    private String washer_id;
    private String dryer_id;
    private String date;

    /**
 *
 * @author Akele Benjamin
 */

    // Constructor
    public Appointment( int appointment_num,int idNum, String name, int washNum, int dryNum, String date, int month, int day, int year, int time, boolean confirmedByResident, boolean confirmedByStaff, String washer_id,String dryer_id) {
        this.idNum = idNum;
        this.name = name;
        this.washNum = washNum;
        this.dryNum = dryNum;
        this.month = month;
        this.day = day;
        this.year = year;
        this.time = time;
        this.confirmedByResident = confirmedByResident;
        this.confirmedByStaff = confirmedByStaff;
        this.washer_id = washer_id;
        this.dryer_id = dryer_id;
        this.appointment_num=appointment_num;
        this.date=date;
    }

    public Appointment(int idNum, String name, int washNum, int dryNum,String date, int month, int day, int year, int time, boolean confirmedByResident, boolean confirmedByStaff, String washer_id,String dryer_id) {
        this.idNum = idNum;
        this.name = name;
        this.washNum = washNum;
        this.dryNum = dryNum;
        this.month = month;
        this.day = day;
        this.year = year;
        this.time = time;
        this.confirmedByResident = confirmedByResident;
        this.confirmedByStaff = confirmedByStaff;
        this.washer_id = washer_id;
        this.dryer_id = dryer_id;
        this.appointmentNum=appointment_num;
        this.date=date;
    }

    public Appointment(int washNum, int dryNum, String date) {
        this.washNum = washNum;
        this.dryNum = dryNum;
        this.date = date;
    }

    // Getters and Setters

    public int getIdNum() {
        return idNum;
    }

    public void setIdNum(int idNum) {
        this.idNum = idNum;
    }

    public int getAppointmentNum() {
        return this.appointment_num;
    }

    public void setAppointmentNum(int idNum) {
        this.appointment_num = appointmentNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWashNum() {
        return this.washNum;
    }

    public void setWashNum(int washNum) {
        this.washNum = washNum;
    }

    public int getDryNum() {
        return this.dryNum;
    }

    public void setDryNum(int dryNum) {
        this.dryNum = dryNum;
    }
    public String getDate() {
        return this.date;
    }

    public void setDate(String newDate) {
        this.date=newDate;
    }   


    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isConfirmedByResident() {
        return confirmedByResident;
    }

    public void setConfirmedByResident(boolean confirmedByResident) {
        this.confirmedByResident = confirmedByResident;
    }

    public boolean isConfirmedByStaff() {
        return confirmedByStaff;
    }

    public void setConfirmedByStaff(boolean confirmedByStaff) {
        this.confirmedByStaff = confirmedByStaff;
    }

    public String getWasherId() {
        return washer_id;
    }

    public String getDryerId() {
        return dryer_id;
    }
    

    // toString method
    @Override
    public String toString() {
        return "Appointment{" +
                ", AppointmentNum=" + appointment_num +
                ", idNum=" + idNum +
                ", name='" + name + '\'' +
                ", washNum=" + washNum +
                ", dryNum=" + dryNum +
                ", month=" + month +
                ", day=" + day +
                ", year=" + year +
                ", time=" + time +
                ", confirmedByResident=" + (confirmedByResident ? "Yes" : "No") +
                ", confirmedByStaff=" + (confirmedByStaff ? "Yes" : "No") +
                ", machineNum=" + machineNum +
                '}';
    }
}
