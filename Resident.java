/**
 *
 * @author Jevon Hayles 620136482
 */
public class Resident extends Person {
    private String email;

    public Resident(){

        super();
        email = "";
        
    }

    public Resident(String accesslvl, String firstname, String lastname,  String idnum, String password, String email){

        super(accesslvl, firstname, lastname, idnum, password);
        this.email = email;
        
    }


    /**Accessor Methods */
    public String getEmail(){
        return email;
    }


    /**Mutator Methods */

    public void setEmail(String email){
        this.email = email;
    }

    
    /**To string method to display values of instance data */
    @Override
    public String toString(){
        return super.toString() + ", " + "Email: " + email;
    }

}
