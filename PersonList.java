import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PersonList {
    public static ArrayList<Person> personList = new ArrayList<Person>();

    public static void createResident(String firstname, String lastname,  String username, String password, int idnum, String email){
        Resident resident = new Resident("User", firstname, lastname, username, password, idnum, email);
        personList.add(resident);
    }

    public static void createStaff(String firstname, String lastname,  String username, String password){
        Person staff = new Person("Staff", firstname, lastname, username, password);
        personList.add(staff);
    }

    public static void createManager(String firstname, String lastname,  String username, String password){
        Person manager = new Person("Manager", firstname, lastname, username, password);
        personList.add(manager);
    }

    //Uses username to return the index location of a person
    //if the function returns -1, then the person was not found
    public int searchPerson(String username){
        int personindex = -1;

        for(int i = 0; i < personList.size(); i++){
            if(personList.get(i).getUsername() == username){
                personindex = i;
            }
        }

        return personindex;
    }

    //Uses searchperson to delete a person
    public void deletePerson(String username){
        personList.remove(searchPerson(username));
    }

    public String displayPersons(){
        String allPersons = personList.toString();
        return allPersons;
    }

    public static void addToFile(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("PersonListData.txt"));
            for(int i = 0; i < personList.size(); i++){
                writer.write(personList.get(i).toString() + "\n");
            }
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public static void readFromFile(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("PersonListData.txt"));
            String line;
            while( (line = reader.readLine()) != null){
                line = line.replace(",","");
                String[] lineArr = line.split(" ");

                //Getting the values needed to create a Person object
                String acclvl = lineArr[1];
                String fname = lineArr[3];
                String lname = lineArr[5];
                String username = lineArr[7];
                String password = lineArr[9];   

                if (acclvl.equals("Staff")){
                    createStaff(fname, lname, username, password);
                } else if (acclvl.equals("Manager")){
                    createManager(fname, lname, username, password);
                } else if (acclvl.equals("User")){
                    int idnum = Integer.parseInt(lineArr[11]);
                    String email = lineArr[13];
                    createResident(fname, lname, username, password, idnum, email);
                }
            }
            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    } 

}
