import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Jevon Hayles 620136482
 */
public class PersonList {
    public static ArrayList<Person> personList = new ArrayList<Person>();

    public static void createResident(String firstname, String lastname,  String idnum, String password, String email){
        Resident resident = new Resident("User", firstname, lastname, idnum, password, email);
        personList.add(resident);
    }

    public static void createStaff(String firstname, String lastname,  String idnum, String password){
        Person staff = new Person("Staff", firstname, lastname, idnum, password);
        personList.add(staff);
    }

    public static void createManager(String firstname, String lastname,  String idnum, String password){
        Person manager = new Person("Manager", firstname, lastname, idnum, password);
        personList.add(manager);
    }

    //Uses idnum to return the index location of a person
    //if the function returns -1, then the person was not found
    public static int searchPerson(String idnum){
        int personindex = -1;

        for(int i = 0; i < personList.size(); i++){
            if(idnum.equals(personList.get(i).getIdNum())){
                personindex = i;
            }
        }

        return personindex;
    }

    //Uses searchperson to delete a person
    public void deletePerson(String idnum){
        personList.remove(searchPerson(idnum));
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
                String idnum = lineArr[7];
                String password = lineArr[9];   

                if (acclvl.equals("Staff")){
                    createStaff(fname, lname, idnum, password);
                } else if (acclvl.equals("Manager")){
                    createManager(fname, lname, idnum, password);
                } else if (acclvl.equals("User")){
                    String email = lineArr[11];
                    createResident(fname, lname, idnum, password, email);
                }
            }
            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    } 


    public static boolean idPasswordMatching(String idnum, String password){
        boolean tf = false;

        int index = searchPerson(idnum);

        if (index != -1){
            if ( idnum.equals(personList.get(index).getIdNum()) && password.equals(personList.get(index).getPassword()) ){
                tf = true;
            }
        }

        return tf;
    }

}
