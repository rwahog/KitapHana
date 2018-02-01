import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Librarian extends User {
    Librarian(Connection connection, Scanner in) throws SQLException {
        super(connection, in);
    }

    @Override
    public void save() throws SQLException {
        super.save();
        statement.executeUpdate("update users set type = 'librarian' where id = '"+id+"'");
    }

    @Override
    public void login() throws SQLException {
        super.login();
        ResultSet resultSet = statement.executeQuery("select * from users where id = '"+id+"'");
        if(resultSet.next()){
            if(resultSet.getString("type").equals("librarian")){
                System.out.println("Successfully");
            }
            else{
                System.out.println("Wrong card number");
            }
        }
    }

    //User
    public void removeUser(User user){

    }
    public void modifyUser(User user){

    }
    //Document
    public void addDocument(Document document) throws SQLException {
        System.out.println("Type: ");
        String s = in.next();
        if(s.equals("Book")){
            Book book = new Book(connection, in);
            book.read();
            book.save();
        }
        else if(s.equals("AV")){
            AVMaterial avMaterial = new AVMaterial(connection, in);
            avMaterial.read();
            avMaterial.save();
        }
        else if(s.equals("JA")){

        }
    }
    public void  removeDocument(Document document){

    }
    public void modifyDocument(Document document){

    }

}
