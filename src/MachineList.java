/**
 *
 * @author Akele Benjamin
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MachineList {
    private ArrayList<Machine> machines;

    // Constructor
    public MachineList() {
        ArrayList<Machine> machineList = new ArrayList<Machine>();
        // Add washer machines
        
        for (int i = 1; i <= 4; i++) {
            machineList.add(new Machine(Machine.MachineType.WASHER, "W" + i, 0));
            machineList.add(new Machine(Machine.MachineType.DRYER, "D" + i, 0));
            if(i==4){
                break;
            }else{
                continue;
            }
        }
        this.machines=machineList;
        
    }

    public void createMachineList(){
        ArrayList<Machine> machineList = new ArrayList<Machine>();
        // Add washer machines
        
        for (int i = 1; i <= 4; i++) {
            machineList.add(new Machine(Machine.MachineType.WASHER, "W" + i, 0));
        }
        // Add dryer machines
        for (int i = 1; i <= 4; i++) {
            machineList.add(new Machine(Machine.MachineType.DRYER, "D" + i, 0));
        }
    }


    // Getters
    public ArrayList<Machine> getMachines() {
        return this.machines;
    }

    // Method to get all machine IDs
    public List<String> getAllDryerIds() {
        List<String> machineIds = new ArrayList<>();
        for (Machine machine : this.machines) {
            if(machine.getMachineId().contains("D")){
                machineIds.add(machine.getMachineId());
            }
            
        }
        return machineIds;
    }
    public List<String> getAllWasherIds() {
        List<String> machineIds = new ArrayList<>();
        for (Machine machine : this.machines) {
            if(machine.getMachineId().contains("W")){
                machineIds.add(machine.getMachineId());
            }
            
        }
        return machineIds;
    }

    public String assignWasher(int numLoads,int year, int month, int day, int time) {
        List<String> filteredList = new ArrayList<>();
        Database db=new Database();
        List<String>machinesInUse=db.getWasherMachineIds(year,month,day,time);
        List<String>allMachines=getAllWasherIds();
        for (String machine : allMachines) {
            if (!machinesInUse.contains(machine)) {
                filteredList.add(machine);
            }
        }

        // Choose random machine/s from filteredList
        List<String> randomMachineList = getRandomMachine(filteredList,numLoads);
        //output one string with all
        String result=concatenateList(randomMachineList);
        return result;
        
    }

    public String assignWasherUpdate(int apptNum,int numLoads,int year, int month, int day, int time) {
        List<String> filteredList = new ArrayList<>();
        Database db=new Database();
        List<String>machinesInUse=db.getWasherMachineIdsUpdate(apptNum,year,month,day,time);
        List<String>allMachines=getAllWasherIds();
        for (String machine : allMachines) {
            if (!machinesInUse.contains(machine)) {
                filteredList.add(machine);
            }
        }

        // Choose random machine/s from filteredList
        List<String> randomMachineList = getRandomMachine(filteredList,numLoads);
        //output one string with all
        String result=concatenateList(randomMachineList);
        return result;
        
    }

    // Method to choose a random machine from the filtered list
    private static List<String> getRandomMachine(List<String> filteredList,int number) {
        List<String> randomMachines = new ArrayList<>();
        if (filteredList.isEmpty()) {
            return null; // Return null if filteredList is empty
        }
        Random random = new Random();
        for(int i=0; i<number;i++){
            int randomIndex = random.nextInt(filteredList.size()); // Generate a random index
            randomMachines.add(filteredList.remove(randomIndex));
        }

        
        return randomMachines; // Return the machine at the random index
    }

    public String assignDryer(int numLoads,int year, int month, int day, int time) {
        List<String> filteredList = new ArrayList<>();
        Database db=new Database();
        List<String>machinesInUse=db.getDryerMachineIds(year,month,day,time);
        List<String>allMachines=getAllDryerIds();
        for (String machine : allMachines) {
            if (!machinesInUse.contains(machine)) {
                filteredList.add(machine);
            }
        }

        // Choose random machine/s from filteredList
        List<String> randomMachineList = getRandomMachine(filteredList,numLoads);
        //output one string with all
        String result=concatenateList(randomMachineList);
        return result;
        
    }

    public String assignDryerUpdate(int apptNum,int numLoads,int year, int month, int day, int time) {
        List<String> filteredList = new ArrayList<>();
        Database db=new Database();
        List<String>machinesInUse=db.getDryerMachineIdsUpdate(apptNum,year,month,day,time);
        List<String>allMachines=getAllDryerIds();
        for (String machine : allMachines) {
            if (!machinesInUse.contains(machine)) {
                filteredList.add(machine);
            }
        }

        // Choose random machine/s from filteredList
        List<String> randomMachineList = getRandomMachine(filteredList,numLoads);
        //output one string with all
        String result=concatenateList(randomMachineList);
        return result;
        
    }

    private static String concatenateList(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) { // Add comma and space for all elements except the last one
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    
}
