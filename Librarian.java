import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Librarian extends User {
    Librarian(Connection connection, Scanner in) throws SQLException {
        super(connection, in);
    }
    //User
    public void removeUser(User user){

    }
    public void modifyUser(User user){

    }
    //Document
    public void addDocument(Document document){

    }
    public void  removeDocument(Document document){

    }
    public void modifyDocument(Document document){

    }

}
