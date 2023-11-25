public class Manager extends Person{

    public Manager(AccessLevel accesslvl, String firstname, String lastname,  String username, String password){
        
        super(accesslvl, firstname, lastname, username, password);
        
    }
    
    public void createStaff(AccessLevel accesslvl, String firstname, String lastname,  String username, String password){
        Staff staff = new Staff(accesslvl, firstname, lastname, username, password);

        /**The addToPersonList needs to be created in the LoginGUI class, as well as an abstract allUsers arraylist */
        //addToPersonList(staff);
    }
}
