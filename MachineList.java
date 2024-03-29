import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MachineList {
    private ArrayList<Machine> machines;

    // Constructor
    public MachineList() {
        machines = new ArrayList<>();
        // Add washer machines
        for (int i = 1; i <= 4; i++) {
            machines.add(new Machine(Machine.MachineType.WASHER, "W" + i, 0));

        }
        // Add dryer machines
        for (int i = 1; i <= 4; i++) {
            machines.add(new Machine(Machine.MachineType.DRYER, "D" + i, 0));
        }
    }

    // Getters
    public ArrayList<Machine> getMachines() {
        return machines;
    }

    // Method to get all machine IDs
    public List<String> getAllMachineIds() {
        List<String> machineIds = new ArrayList<>();
        for (Machine machine : machines) {
            machineIds.add(machine.getMachineId());
        }
        return machineIds;
    }

    public String assignWasher(List<String> availableMachine) {
        List<String> filteredList = new ArrayList<>();
        for (String str : availableMachine) {
            if (str.startsWith("W")) {
                filteredList.add(str);
            }
        }
        // Select a random washer from available washers
        Random random = new Random();
        return filteredList.get(random.nextInt(filteredList.size()));
    }

    public String assignDryer(List<String> availableMachine) {
        List<String> filteredList = new ArrayList<>();
        for (String str : availableMachine) {
            if (str.startsWith("D")) {
                filteredList.add(str);
            }
        }
        // Select a random washer from available washers
        Random random = new Random();
        return filteredList.get(random.nextInt(filteredList.size()));
    }

    
}

