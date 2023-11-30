import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {

        PersonList.readFromFile();

        for(int i =0; i < PersonList.personList.size(); i++){
            System.out.println(PersonList.personList.get(i));
        }

    }
}
