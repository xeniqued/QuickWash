public class Manager extends Person{

    public Manager(){

        super();

    }

    public Manager(String accesslvl, String firstname, String lastname,  String username, String password){
        
        super(accesslvl, firstname, lastname, username, password);
        
    }
    
    public void createStaff(String accesslvl, String firstname, String lastname,  String username, String password){
        Staff staff = new Staff(accesslvl, firstname, lastname, username, password);

        //more functionality needs to be added before this method can be finished
        //The addToPersonList function needs to be created, as well as a static allUsers arraylist
        //addToPersonList(staff);
    }
}
