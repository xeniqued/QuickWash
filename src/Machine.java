public class Machine {
    public enum MachineType {
        WASHER,
        DRYER
    }

    private MachineType machineType;
    private String machineId;
    private int useCount;

    // Constructor
    public Machine(MachineType machineType, String machineId, int useCount) {
        this.machineType = machineType;
        this.machineId = machineId;
        this.useCount = useCount;
    }

    // Getters
    public MachineType getMachineType() {
        return machineType;
    }

    public String getMachineId() {
        return machineId;
    }

    public int getUseCount() {
        return useCount;
    }

    // Setters
    public void setMachineType(MachineType machineType) {
        this.machineType = machineType;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }

    // toString method
    @Override
    public String toString() {
        return "Machine{" +
                "machineType=" + machineType +
                ", machineId='" + machineId + '\'' +
                ", useCount=" + useCount +
                '}';
    }
}