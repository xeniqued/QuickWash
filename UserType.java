public class UserType {
    private String type_user;
    private String name;
    private int id_num;
    private String email;
    private int room_num;
    private String Block;
    private String password;

    public UserType(String type_user, String name, int id_num, String email, int room_num, String Block, String password) {
        this.type_user = type_user;
        this.name = name;
        this.id_num = id_num;
        this.email = email;
        this.room_num = room_num;
        this.Block = Block;
        this.password = password;
    }

    // Getters for all fields
    public String getType_user() {
        return type_user;
    }

    public String getName() {
        return name;
    }

    public int getId_num() {
        return id_num;
    }

    public String getEmail() {
        return email;
    }

    public int getRoom_num() {
        return room_num;
    }

    public String getBlock() {
        return Block;
    }

    public String getPassword() {
        return password;
    }

    // Setters for all fields
    public void setType_user(String type_user) {
        this.type_user = type_user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId_num(int id_num) {
        this.id_num = id_num;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoom_num(int room_num) {
        this.room_num = room_num;
    }

    public void setBlock(String Block) {
        this.Block = Block;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString method to print user information
    @Override
    public String toString() {
        return "UserType{" +
                "type_user='" + type_user + '\'' +
                ", name='" + name + '\'' +
                ", id_num=" + id_num +
                ", email='" + email + '\'' +
                ", room_num=" + room_num +
                ", Block='" + Block + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
