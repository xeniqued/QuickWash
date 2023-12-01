import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        MachineList.readFromFile();

        for(int i = 0; i < MachineList.machineList.size(); i++){
            System.out.println(MachineList.machineList.get(i));
        }
    }
}


/**
    public static void main(String[] args) {

        PersonList.readFromFile();

        for(int i =0; i < PersonList.personList.size(); i++){
            System.out.println(PersonList.personList.get(i));
        }

    } 
* 
*/