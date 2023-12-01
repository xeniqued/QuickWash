import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Jevon Hayles 620136482
 */
public class MachineList {
    public static ArrayList<Machine> machineList = new ArrayList<Machine>();

    public static void createMachine(String machineid, String machinetype, int usecount, int dailyusecount, boolean maintenancerequest){
        Machine machine = new Machine(machineid, machinetype, usecount, dailyusecount, maintenancerequest);
        machineList.add(machine);
    }

    //This function uses the machineid variable to search through the machine list and return the useCount of the specific machine
    public int getCyclesPerMachine(String machineid){
        int machineUseCount = -1;

        for(int i = 0; i < machineList.size(); i++){
            if (machineList.get(i).getMachineID() == machineid){
                machineUseCount = machineList.get(i).getUseCount();
            }
        }

        //Return the usecount, if the usecount is -1, that means that the machineid could not be found
        return machineUseCount;
    }

    //needs to be created
    public String generateIncomeReport(){
        return "";
    }

    //needs to be created
    public String generateMachineReport(){
        return "";
    }

    public static void addToFile(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("MachineListData.txt"));
            for(int i = 0; i < machineList.size(); i++){
                writer.write(machineList.get(i).toString() + "\n");
            }
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void readFromFile(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("MachineListData.txt"));
            String line;
            while( (line = reader.readLine()) != null){
                line = line.replace(",","");
                String[] lineArr = line.split(" ");

                //Getting the values needed to create a Machine object
                String machineid = lineArr[0];
                String machinetype = lineArr[1];
                int usecount = Integer.parseInt(lineArr[2]);
                int dailyusecount = Integer.parseInt(lineArr[3]);
                boolean maintenancerequest = Boolean.parseBoolean(lineArr[4]);

                createMachine(machineid, machinetype, usecount, dailyusecount, maintenancerequest);
            }
            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    } 


    //The MachineList class uses the ArrayList class' default toString() method
    //It should return the elements in the format: [1, 2, 3]
    //For extra information on this visit this website: https://www.programiz.com/java-programming/library/arraylist/tostring
}