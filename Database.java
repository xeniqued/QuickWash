import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Database {
    
    String userdbName;
    String userdbPass;

    File userFile;
    Scanner ascan = null;

    public Database() {       
        
        userFile = new File(System.getProperty("user.dir") + "/database/" +  "users.txt");
            
        try {
            ascan = new Scanner(userFile);
            while (ascan.hasNext()) {
                // System.out.print(ascan.nextLine());
                String[] nextLine = ascan.nextLine().split(" ");

                for (String x : nextLine) {
                    System.out.print(x + ", ");
                }
                System.out.print("\n");

                if (nextLine[0].equals("Username:")) {
                    userdbName = nextLine[1];
                } else if (nextLine[0].equals("Password:")) {
                    userdbPass = nextLine[1];                    
                }
                System.out.println(userdbName);
                System.out.println(userdbPass);                   

                }

            ascan.close();
        } catch (IOException ex) {
        }
    }

    
    public String getUserUsername(){
        return userdbName;
    }

    public String getUserPassword(){
        return userdbPass;
    }

}



