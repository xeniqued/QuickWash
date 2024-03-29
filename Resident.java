public class Resident 
{
    private String fName;
    private String lName;
    private String password;
    private int id;
    private String email;
    private String type;
    private int totalAmount;

    public Resident (String fName, String lName, int id, String password,String email,String type)
    {
        this.fName=fName;
        this.lName=lName;
        this.id=id;
        this.password=password;
        this.email=email;
        this.type=type;
        

    }

    public int getId()
    {
        return id;
    }

    public String getFname()
    {
        return fName;
    }

    public String getLname()
    {
        return lName;
    }

    public String getPassword()
    {
        return password;
    }

    public String getEmail()
    {
        return email;
    }

    public String getType()
    {
        return type;
    }

    public int getTotalAmount()
    {
        return totalAmount;
    }




    public String toString()
    {
        return getType()+"\t"+getFname()+"\t"+getLname()+"\t"+getEmail()+"\t"+"\t"+getId()+"\t"+getPassword();
    }
    
}
