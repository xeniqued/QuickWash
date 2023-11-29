import java.util.ArrayList;

public abstract class Person {
    private AccessLevel accesslvl;
    private String username;
    private ArrayList<String> name = new ArrayList<String>();
    private String password;

    public Person() {

        accesslvl = AccessLevel.User;
        username = "";
        name.add("");
        name.add("");
        password = "";

    }

    public Person(String accesslvl, String firstname, String lastname,  String username, String password) {

        this.accesslvl = AccessLevel.valueOf(accesslvl);
        name.add(firstname);
        name.add(lastname);
        this.username = username;
        this.password = password;

    }
    

    /**Accessor Methods */
    //Returns accesslvl as a string using .name()
    public String getAccessLevel(){
        return accesslvl.name();
    }
    
    public String getUsername(){
        return username;
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

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
    }


    /**To string method to display values of each instance data */
    public String toString(){
        return "AccessLevel: " + accesslvl.name() + ", " + "First_Name: " + name.get(0) + ", " + "Last_Name: " + name.get(1) + ", " + "Username: " + username + ", " + "Password: " + password;
    }
}