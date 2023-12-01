public class CustomerSales 
{
    private String fName;
    private String lName;
    private String cDate;
    private int customerID;
    private int totalAmountWash;
    private int totalAmountDry;
    private int totalAmount;

    public CustomerSales (String fName, String lName, int customerID, String cDate,int totalAmountWash,int totalAmountDry,int totalAmount)
    {
        this.fName=fName;
        this.lName=lName;
        this.customerID=customerID;
        this.cDate=cDate;
        this.totalAmountWash=totalAmountWash;
        this.totalAmountDry=totalAmountDry;
        this.totalAmount=totalAmount;

    }

    public int getCustomerID()
    {
        return customerID;
    }

    public String getfName()
    {
        return fName;
    }

    public String getlName()
    {
        return lName;
    }

    public String getDate()
    {
        return cDate;
    }

    public int getTotalAmountWash()
    {
        return totalAmountWash;
    }

    public int getTotalAmountDry()
    {
        return totalAmountDry;
    }

    public int getTotalAmount()
    {
        return totalAmount;
    }

    public void increaseUseCountWash(){
        this.totalAmountWash += 1;
    }

    public void increaseUseCountDry(){
        this.totalAmountDry += 1;
    }


    public String toString()
    {
        return getfName()+"\t"+getlName()+"\t"+getCustomerID()+"\t"+getDate()+"\t"+getTotalAmountWash()+"\t"+getTotalAmountDry()+"\t"+getTotalAmount();
    }
    
}