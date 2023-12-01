import java.util.ArrayList;

/**
 *
 * @author Jevon Hayles 620136482
 */
public class Person {
    private AccessLevel accesslvl;
    private String idnum;
    private ArrayList<String> name = new ArrayList<String>();
    private String password;

    public Person() {

        accesslvl = AccessLevel.User;
        idnum = "";
        name.add("");
        name.add("");
        password = "";

    }

    public Person(String accesslvl, String firstname, String lastname,  String idnum, String password) {

        this.accesslvl = AccessLevel.valueOf(accesslvl);
        name.add(firstname);
        name.add(lastname);
        this.idnum = idnum;
        this.password = password;

    }
    

    /**Accessor Methods */
    //Returns accesslvl as a string using .name()
    public String getAccessLevel(){
        return accesslvl.name();
    }
    
    public String getIdNum(){
        return idnum;
    }

    public String getPassword(){
        return password;
    }

    public String getName(){
        String stringName = name.get(0) + " " + name.get(1);
        return stringName;
    }


    /**Mutator Methods */
    public void setAccessLevel(String accesslvl){
        this.accesslvl = AccessLevel.valueOf(accesslvl); 
    }

    public void setName(String firstname, String lastname){
        name.set(0, firstname);
        name.set(1, lastname);
    }

    public void setIdNum(String idnum){
        this.idnum = idnum;
    }

    public void setPassword(String password){
        this.password = password;
    }


    /**To string method to display values of each instance data */
    public String toString(){
        return "AccessLevel: " + accesslvl.name() + ", " + "First_Name: " + name.get(0) + ", " + "Last_Name: " + name.get(1) + ", " + "ID_Num: " + idnum + ", " + "Password: " + password;
    }
}