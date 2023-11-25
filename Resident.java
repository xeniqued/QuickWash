public class Resident extends Person {
    private int idnum;
    private String email;

    public Resident(){

        super();
        idnum = 0;
        email = "";
        
    }

    public Resident(AccessLevel accesslvl, String firstname, String lastname,  String username, String password, int idnum, String email){

        super(accesslvl, firstname, lastname, username, password);
        this.idnum = idnum;
        this.email = email;
        
    }


    /**Accessor Methods */
    public int getIdNum(){
        return idnum;
    }

    public String getEmail(){
        return email;
    }


    /**Mutator Methods */
    public void setIdNum(int idnum){
        this.idnum = idnum;
    }

    public void setEmail(String email){
        this.email = email;
    }

    
    /**To string method to display values of each instance data */
    @Override
    public String toString(){
        return super.toString() + " " + idnum + " " + email;
    }

}
