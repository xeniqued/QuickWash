public class Machine {
    private String machineid;
    private MachineType machinetype;
    private int usecount;
    private int dailyusecount;
    private boolean maintenancerequest;

    public Machine(){

        machineid = "";
        usecount = 0;
        dailyusecount = 0;

    }

    public Machine(String machineid, int usecount, int dailyusecount){

        this.machineid = machineid;
        this.usecount = usecount;
        this.dailyusecount = dailyusecount;
        

    }


    /**Accessor Methods */
    public String getMachineID(){
        return machineid;
    }


    public int getUseCount(){
        return usecount;
    }

    public int getDailyUseCount(){
        return dailyusecount;
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
