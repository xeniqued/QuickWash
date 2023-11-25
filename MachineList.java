import java.util.ArrayList;

public class MachineList {
    private static ArrayList<Machine> machineList = new ArrayList<Machine>();

    public void addToMachineList(Machine machine){
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

    //We will use the ArrayClasses default toString() method
    //It should return the elements in the format: [1, 2, 3]
    //For extra information on this visit this website: https://www.programiz.com/java-programming/library/arraylist/tostring
}