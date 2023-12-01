/**
 *
 * @author Jevon Hayles 620136482
 */
public class Machine {
    private String machineid;
    private MachineType machinetype;
    private int usecount;
    private int dailyusecount;
    private boolean maintenancerequest;

    public Machine(){

        machineid = "";
        machinetype = MachineType.Washer;
        usecount = 0;
        dailyusecount = 0;
        maintenancerequest = false;

    }

    public Machine(String machineid, String machinetype, int usecount, int dailyusecount, boolean maintenancerequest){

        this.machineid = machineid;
        this.machinetype = MachineType.valueOf(machinetype);
        this.usecount = usecount;
        this.dailyusecount = dailyusecount;
        this.maintenancerequest = maintenancerequest;

    }


    /**Accessor Methods */
    public String getMachineID(){
        return machineid;
    }

    public String getMachineType(){
        return machinetype.name();
    }

    public int getUseCount(){
        return usecount;
    }

    public int getDailyUseCount(){
        return dailyusecount;
    }

    /**Functions as checkAvailability() */
    public boolean getMaintenanceRequest(){
        return maintenancerequest;
    }


    /**Mutator Methods */
    public void setMachineID(String machineid){
        this.machineid = machineid;
    }

    public void setMachineType(String machinetype){
        this.machinetype = MachineType.valueOf(machinetype);
    }

    public void setUseCount(int usecount){
        this.usecount = usecount;
    }

    public void setDailyUseCount(int usecount){
        this.usecount = usecount;
    }

    /**Adds one to usecount */
    public void increaseUseCount(){
        this.usecount += 1;
    }

    /**Adds one to daily usecount */
    public void increaseDailyUseCount(){
        this.dailyusecount += 1;
    }

    /**Functions as updateAvailability() */
    public void setMaintenanceRequest(boolean maintenancerequest){
        this.maintenancerequest = maintenancerequest;
    }


    /**To string method to display values of each instance data */
    public String toString(){
        return machineid + " " + machinetype.name() + " " + usecount + " " + dailyusecount + " " + String.valueOf(maintenancerequest);
    }
}

